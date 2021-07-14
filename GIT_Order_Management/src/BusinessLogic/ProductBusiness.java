package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;

/**
 * Aceasta clasa face legatura intre Controller si ProductDAO.
 * Mai exact,ceea ce se intampla in interfata,in tabul Product,este preluat de Controller
 * si transformat in obiect/lista de obiecte de tip Product de catre productDAO.
 * Toate metodele din ProductBusiness doar apeleaza metodele din productDAO.
 * @author SIRBU OANA-BIANCA
 */
public class ProductBusiness {

    /**
     *Atribut necesar pentru a transmite clasei AbstractDAO tipul Product prin intermediul clasei ProductDAO
     */
    private ProductDAO productDAO;

    public  ProductBusiness()
     {
         productDAO=new ProductDAO();
     }

    /**
     * Metoda gaseste toate produsele din tabelul Product din baza de date prin
     * intermediul metodei findAll() din ProductDAO.
     * Transforma tabelul product intr-o lista de obiecte de tip Product.
     * @return  Returneaza lista de produse.
     */
     public List<Product> findAllProducts()
    {
        return productDAO.findAll();
    }

    /**
     * Metoda insereaza un produs in baza de date(in tabelul Product)
     * prin intermediul metodei insert(Product p) din ProductDAO.
     * @param p Parametru ce reprezinta un obiect de tip Product.
     */
     public void insertProduct(Product p)
    {
        productDAO.insert(p);
    }

    /**
     * Metoda gaseste un produs dupa id in baza de date(in tabelul Product)
     * prin intermediul metodei findById(int id) din ProductDAO.
     * @param id Parametru ce reprezinta id-ul produsului ce trebuie cautat.
     * @return  Returneaza un obiect de tip Product.
     */
     public Product findById(int id)
    {
        return productDAO.findById(id);
    }

    /**
     * Metoda actualizeaza datele unui produs din baza de date(in tabelul Product)
     * prin intermediul metodei update(p) din ProductDAO.
     * @param p Parametru ce reprezinta produsul care trebuie updatat.
     */
     public void updateProduct(Product p)
    {
        productDAO.update(p);
    }

    /**
     * Metoda sterge un produs cautat dupa id din baza de date(in tabelul Product)
     * prin intermediul metodei delete(id) din ProductDAO.
     * @param id Parametru ce reprezinta id-ul produsului cautat.
     */
     public void deleteProduct(int id)
    {
        productDAO.delete(id);
    }
}
