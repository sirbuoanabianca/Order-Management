package Presentation;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
/**Aceasta clasa creaza un tabel cu tipul dat ca parametru*/

public class Tables<T> {
    TableModel tableModel;
    private final Class<T> type;

    public Tables() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
/**Aceasta metoda genereaza tabelul prin tehnica reflexiei,creand intai header-ul cu numele coloanelor
* si apoi il populeaza cu datele din lista data ca parametru */
    public void generateTableReflection(List<T> list)
    {
        int rows=list.size();
        ArrayList<String> columnNames=new ArrayList<>();

        //creez HEADER-ul tabelului
        for (Field field : type.getDeclaredFields())
        {
            String fieldName = field.getName(); //gaseste numele atributului curent
            columnNames.add(fieldName);
        }
        tableModel=new DefaultTableModel(columnNames.toArray(),rows);

        //populez tabelul

        int i=0,j=0; //i linie,j coloana
        for(T object:list)
        {
            for (Field field : type.getDeclaredFields())
            {
                String fieldName = field.getName();
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getReadMethod();
                    Object value = method.invoke(object);
                    tableModel.setValueAt(value.toString(),i,j);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                j++;
            }
            j=0;
            i++;
        }

   }

    public TableModel getTableModel() {
        return tableModel;
    }
}
