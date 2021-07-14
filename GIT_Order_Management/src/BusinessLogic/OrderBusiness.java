package BusinessLogic;

import DataAccess.OrderDAO;
import Model.Order;

import java.util.List;

/**
 * Aceasta clasa face legatura intre Controller si OrderDAO.
 * Mai exact,ceea ce se intampla in interfata,in tabul Order,este preluat de Controller
 * si transformat in obiect/lista de obiecte de tip Order de catre orderDAO.
 * Toate metodele din OrderBusiness doar apeleaza metodele din orderDAO.
 * @author SIRBU OANA-BIANCA
 */
public class OrderBusiness {
    /**
     *Atribut necesar pentru a transmite clasei AbstractDAO tipul Order prin intermediul clasei OrderDAO
     */
    private OrderDAO orderDAO;

    public OrderBusiness() {
        orderDAO=new OrderDAO();
    }

    /**
     * Metoda insereaza o comanda in baza de date(in tabelul Order)
     * prin intermediul metodei insert(Order o) din OrderDAO.
     * @param o Parametru ce reprezinta un obiect de tip Order.
     */
    public void insertOrder(Order o)
    {
        orderDAO.insert(o);
    }

    /**
     * Metoda gaseste o comanda dupa id-ul clientului in baza de date(in tabelul Order)
     * prin intermediul metodei findOrdersByClientId(int id) din OrderDAO.
     * @param id Parametru ce reprezinta id-ul clientului ce trebuie cautat.
     * @return  Returneaza o lista de obiecte de tip Order.
     */
    public List<Order> findOrdersByClientId(int id)
    {
        return orderDAO.findOrdersByClientId(id);
    }
}
