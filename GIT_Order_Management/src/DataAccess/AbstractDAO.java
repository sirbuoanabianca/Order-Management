package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.DatabaseConnection;

/**
 * Clasa generica care contine metode pentru accesarea bazei de date:
 * creare obiect,update obiect,stergere obiect,gasire obiect.
 * @param <T> Una dintre clasele Client,Product sau Order
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    /**
     * tipul clasei date ca parametru
     */
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creeaza interogarea pentru afisarea tuturor obiectelor dintr-un tabel dupa un camp
     * @param field Campul dupa care se face cautarea.
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creeaza interogarea pentru afisarea tuturor obiectelor dintr-un tabel.
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Creeaza interogarea pentru actualizarea unui rand dintr-un tabel dupa un id
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createUpdateQuery() {
        //UPDATE tablename SET name=?,addr=?,email=?,birth_date=? WHERE id=?
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("`" + type.getSimpleName() + "`");
        sb.append(" SET ");

        for (Field field : type.getDeclaredFields())

        { if (field.getName().equals("id"))
            continue;

            sb.append("`" + field.getName() + "`=?" + ",");
        }

        sb.setLength(sb.length()-1);
        sb.append(" WHERE id=?");
        return sb.toString();
    }

    /**
     * Creeaza interogarea pentru inserarea unui rand intr-un tabel.
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder(), values = new StringBuilder();
        String fields;

        sb.append("INSERT INTO ");
        sb.append("`" + type.getSimpleName() + "`");
        sb.append("(");
        values.append(" VALUES(");

        for (Field f : type.getDeclaredFields()) {
            if (f.getName().equals("id"))
                continue;

            sb.append("`" + f.getName() + "`,");
            values.append("?,");
        }
       //sterg ultima virgula si o voi inlocui cu ")"
       sb.setLength(sb.length()-1);
        sb.append(")");
        //sterg ultima virgula si o voi inlocui cu ")"
        values.setLength(values.length()-1);
        values.append(")");
        return sb.toString() + values.toString();
    }

    /**
     * Creeaza interogarea pentru stergerea unui rand dintr-un tabel dupa un id
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createDeleteByIDQuery()
    {//DELETE FROM tableName WHERE id = ?;
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append("`" + type.getSimpleName() + "`");
        sb.append(" WHERE id=?");

        return sb.toString();
    }

    /**
     * @return  Returneaza elementele unui tabel sub forma de lista de obiecte de tip T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }
        return null;

    }

    /**
     * @param id Id-ul obiectului care trebuie cautat
     * @return  Returneaza un rand dintr-un tabel sub forma de obiect de tip T
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    /**Transforma elementele unui tabel sub forma de lista de obiecte de tip T
     * @param resultSet Interogarea de pe urma careia se vor crea obiectele.
     * @return  Returneaza elementele unui tabel sub forma de lista de obiecte de tip T
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName(); //gaseste numele variabilei curente

                    Object value = resultSet.getObject(fieldName);//primeste ca obiect valoarea citita din
                    //bd de pe coloana fieldName

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    //primeste detalii despre variabile selectata


                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                    //gaseste setter si apeleaza-l cu valoarea din bd

                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**Insereaza in baza de date un obiect de tip T
     * @param t Obiectul care trebuie inserat in tabel.
     */
    public void insert(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);

            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName(); //gaseste numele atributului curent

                if (fieldName.equals("id"))
                    continue;

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(t); //gaseste getter si apeleaza-l cu valoarea din bd

                statement.setObject(i, value);//inlocuiesc ? cu valoarea din obiect de la campul curent

                i++;
            }

            statement.executeUpdate();

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }

    }

    /**Actualizeaza o tupla din tabel.
     * @param t Obiectul care trebuie actualizat(interogarea il va cauta in tabel dupa id).
     */
    public void update(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);

            int i = 1;//al catelea ? din statement
            int ID = 0;
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName(); //gaseste numele variabilei curente
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();

                Object value = method.invoke(t); //gaseste setter si apeleaza-l cu valoarea din bd
                if (fieldName.equals("id")) {
                    ID = (int) value;
                    continue;
                }

                statement.setObject(i, value);
                i++;
            }
            statement.setObject(i, ID);//inlocuiesc ? cu valoarea din obiect de la campul curent
            statement.executeUpdate();


        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }
    }
    /**Sterge o tupla din tabel dupa un id.
     * @param id Id-ul dupa care se face cautarea si apoi stergerea.
     */
    public void delete(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteByIDQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            statement.execute();

            } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }
    }
}


