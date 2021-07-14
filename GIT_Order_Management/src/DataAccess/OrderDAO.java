package DataAccess;

import Model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import Connection.DatabaseConnection;
/**
 * Aceasta clasa mosteneste AbstractDAO si precizeaza parametrul pentru clasa parinte ca fiind Order.
 */
public class OrderDAO extends AbstractDAO<Order>{

    /**
     * Creeaza interogarea utilizata la afisarea istoricului de comenzi pentru un client.
     * @return String Returneaza sirul ce contine interogarea formata.
     */
    private String createSelectAllByIdClientQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("`order` WHERE id_client=?");
        return sb.toString();
    }
    /**Metoda gaseste toate comenzile facute de un client anume.
     * @param id Id-ul clientului.
     * @return Returneaza niste tuple din tabel Order sub forma de lista de obiecte de tip Order.
     */
    public List<Order> findOrdersByClientId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllByIdClientQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Order " + "DAO:findOrderByClientId " + e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }
}
