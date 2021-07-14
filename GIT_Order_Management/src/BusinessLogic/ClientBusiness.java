package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;

/**
 * Aceasta clasa face legatura intre Controller si ClientDAO.
 * Mai exact,ceea ce se insereaza in interfata,in tabul Client,este preluat de Controller
 * si transformat in obiect/lista de obiecte de tip Client de catre clientDAO.
 * Toate metodele din ClientBusiness doar apeleaza metodele din clientDAO.
 * @author SIRBU OANA-BIANCA
 */
public class ClientBusiness {
    /**
     *Atribut necesar pentru a transmite clasei AbstractDAO tipul Client prin intermediul clasei ClientDAO
     */
    private ClientDAO clientDAO;

    public ClientBusiness() {
        clientDAO = new ClientDAO();
    }

    /**
     * <p>Metoda gaseste toti clientii din tabelul Client din baza de date prin
     * intermediul metodei findAll() din ClientDAO.</p>
     * Transforma tabelul client intr-o lista de obiecte de tip Client.
     * @return  Returneaza lista de clienti
     */
    public List<Client> findAllClients()
    {
        return clientDAO.findAll();
    }

    /**
     * Metoda insereaza un client in baza de date(in tabelul Client)
     * prin intermediul metodei insert(Client c) din ClientDAO.
     * @param c Parametru ce reprezinta un obiect de tip Client.
     */
    public void insertClient(Client c)
    {
        clientDAO.insert(c);
    }

    /**
     * Metoda gaseste un client dupa id in baza de date(in tabelul Client)
     * prin intermediul metodei findById(int id) din ClientDAO.
     * @param id Parametru ce reprezinta id-ul clientul ce trebuie cautat.
     * @return  Returneaza un obiect de tip Client.
     */
    public Client findById(int id)
    {
        return clientDAO.findById(id);
    }

    /**
     * Metoda actualizeaza datele unui client din baza de date(in tabelul Client)
     * prin intermediul metodei update(c) din ClientDAO.
     * @param c Parametru ce reprezinta clientul care trebuie updatat.
     */
    public void updateClient(Client c)
    {
        clientDAO.update(c);
    }

    /**
     * Metoda sterge un client cautat dupa id din baza de date(in tabelul Client)
     * prin intermediul metodei delete(id) din ClientDAO.
     * @param id Parametru ce reprezinta id-ul clientului cautat.
     */
    public void deleteClient(int id)
    {
        clientDAO.delete(id);
    }
}
