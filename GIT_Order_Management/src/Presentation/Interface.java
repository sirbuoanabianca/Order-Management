package Presentation;

import BusinessLogic.ClientBusiness;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Aceasta clasa se ocupa de construirea interfetei grafice
 */
public class Interface extends JFrame{
    /**Cele 3 taburi:Client,Product,Order*/
    private JTabbedPane tabs;
    /**Panoul unde vor fi plasate taburile*/
    private JPanel panel1;
    /**Campul de text pentru nume client*/
    private JTextField client_name;
    /**Campul de text pentru adresa clientului*/
    private JTextField address;
    /**Campul de text pentru email-ul clientului*/
    private JTextField email;
    /**Campul de text pentru data nasterii clientului*/
    private JTextField birth_date;
    /**Buton pentru update client*/
    private JButton updateClientButton;
    /**Buton pentru inserare client nou*/
    private JButton insertClientButton;
    /**Camp de text pentru id client*/
    private JTextField id_client;
    /**Buton pentru cautare client dupa id*/
    private JButton searchByIdButton;
    /**Buton pentru stergere client dupa id*/
    private JButton deleteByIdButton;
    /**Tabelul clientilor*/
    private JTable clientTable;
    /**Camp de text pentru nume produs*/
    private JTextField productName;
    /**Camp de text pentru stocul produsului*/
    private JTextField stock;
    /**Campul de text pentru pretul produsului*/
    private JTextField price;
    /**Campul de text pentru id-ul produsului*/
    private JTextField id_product;
    /**Buton pentru inserare produs*/
    private JButton insertProductButton;
    /**Buton pentru update produs*/
    private JButton updateProductButton;
    /**Buton pentru stergere produs dupa id*/
    private JButton deleteByIdButtonProduct;
    /**Buton pentru cautare produs dupa id*/
    private JButton searchByIdButtonProduct;
    /**Tabelul produselor*/
    private JTable productTable;
    /**Tabelul clientilor din tabul Order*/
    private JTable orderClientTable;
    /**Tabelul produselor din tabul Order*/
    private JTable orderProductTable;
    /**Zona de text pentru afisare istoricului comenzilor al unui client*/
    private JTextArea orderHistory;
    /**Campul de text pentru cantitatea unui produs*/
    private JTextField orderProductQuantity;
    /**Buton pentru plasare comanda*/
    private JButton placeTheOrderButton;
    /**Lista de comenzi al unui client*/
    private StringBuilder orderList;
    /**Factura unei comenzi cu detalii:id comanda,nume si adresa client,nume produs comandat si total plata*/
    private StringBuilder generateBill;

    public Interface()  {
        add(panel1);
        //aparitia tabelelor la deschiderea initiala a interfetei(nu doar cand se schimba tabul)
        ClientTable client = new ClientTable();
        ClientBusiness clientBusiness=new ClientBusiness();
        client.generateTableReflection(clientBusiness.findAllClients());
        setClientTableModel(client.getTableModel());
        orderList=new StringBuilder();
        generateBill=new StringBuilder();
    }

    /**Setare JTable al clientului cu modelul dat ca parametru.
    Actualizare a tabelului client din interfata(din tabul Client)*/
    public void setClientTableModel(TableModel tableModel) {
        clientTable.setModel(tableModel);
    }
    /**Setare JTable al produselor cu modelul dat ca parametru.
     Actualizare a tabelului product din interfata(din tabul Product)*/
    public void setProductTableModel(TableModel tableModel)
    {
        productTable.setModel(tableModel);
    }
    /**Setare JTable al clientului cu modelul dat ca parametru.
     Actualizare a tabelului client din interfata(din tabul Order)*/
    public void setOrderClientTableModel(TableModel tableModel)
    {
        orderClientTable.setModel(tableModel);
    }
    /**Setare JTable al produselor cu modelul dat ca parametru.
     Actualizare a tabelului product din interfata(din tabul Order)*/
    public void setOrderProductTableModel(TableModel tableModel)
    {
        orderProductTable.setModel(tableModel);
    }
    /**changeListener pentru tabs*/
    public void setTabsChangeListener(ChangeListener changeListener)
    {
        tabs.addChangeListener(changeListener);
    }


    /**actionListener pentru butonul de inserare client nou din tabul Client*/
    public void setInsertButtonActionListener(ActionListener actionListener)
    {
        insertClientButton.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de cautare client din tabul Client*/
    public void setSearchByIdButtonActionListener(ActionListener actionListener)
    {
        searchByIdButton.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de update client din tabul Client*/
    public void setUpdateClientButtonActionListener(ActionListener actionListener)
    {
        updateClientButton.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de stergere client din tabul Client*/
    public void setDeleteByIdButtonActionListener(ActionListener actionListener)
    {
        deleteByIdButton.addActionListener(actionListener);
    }

    /**actionListener pentru butonul de inserare produs nou din tabul Product*/
    public void setInsertButtonProductActionListener(ActionListener actionListener)
    {
        insertProductButton.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de cautare din tabul Product*/
    public void setSearchByIdProductActionListener(ActionListener actionListener)
    {
        searchByIdButtonProduct.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de update din tabul Product*/
    public void setUpdateProductButtonActionListener(ActionListener actionListener)
    {
        updateProductButton.addActionListener(actionListener);
    }
    /**actionListener pentru butonul de stergere produs din tabul Product*/
    public void setDeleteByIdProductActionListener(ActionListener actionListener)
    {
        deleteByIdButtonProduct.addActionListener(actionListener);
    }

    /**actionListener pentru butonul din tabul Order*/
    public void setPlaceTheOrderButtonActionListener(ActionListener actionListener)
    {
        placeTheOrderButton.addActionListener(actionListener);
    }

    public JTable getOrderClientTable() {
        return orderClientTable;
    }

    public JTable getOrderProductTable() {
        return orderProductTable;
    }

    public JTextField getClientName() {
        return client_name;
    }

    public JTextField getAddress() {
        return address;
    }

    public JTextField getEmail() {
        return email;
    }

    public JTextField getBirth_date() {
        return birth_date;
    }

    public JTextField getId_client() {
        return id_client;
    }

    public JTextField getProductName() {
        return productName;
    }

    public JTextField getStock() {
        return stock;
    }

    public JTextField getPrice() {
        return price;
    }

    public JTextField getId_product() {
        return id_product;
    }

    public JTextField getOrderProductQuantity() {
        return orderProductQuantity;
    }

    /**Metoda adauga in lista istoricului de comenzi al unui client produsele anterioare comandate si cantitatea.*/
    public void addOrderInList(String productName, String quantity)
    {
        orderList.append("Product name: "+productName+"  quantity: "+quantity+"\n");
    }

    public void setTextArea(String o) {
        orderHistory.setText(o);
    }

    public void emptyOrderList()
    {
        orderList.setLength(0);
    }
    public String getOrderList() {
        return String.valueOf(orderList);
    }

    /**
     * Metoda creeaza factura pentru comanda plasata de client.
     * @param id_order Factura prezinta printre detalii si id-ul comenzii.
     * @param clName Factura prezinta printre detalii si numele clientului care a facut comanda.
     * @param prName Factura prezinta printre detalii si numele produsului comandat.
     * @param addr Factura prezinta printre detalii si adresa clientului.
     * @param quant Factura prezinta printre detalii si cantitatea produsului comandat.
     * @param price Factura prezinta printre detalii si pretul produsului comandat.
     */
    public void createBill(String id_order,String clName,String prName,String addr,String quant,String price)
    {   generateBill.setLength(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        generateBill.append("COMANDA CU NUMARUL "+id_order+" a fost plasata la data de "+dtf.format(now)+"\n");
        generateBill.append("CLIENTUL/CLIENTA "+clName+" DIN\n");
        generateBill.append(addr+"\n");
        generateBill.append("A ACHIZITIONAT "+quant+" x "+prName+"("+price+" RON)"+"\n");
        BigDecimal total=new BigDecimal(price).multiply(new BigDecimal(Integer.parseInt(quant)));
        generateBill.append("TOTAL PLATA="+ total+" RON");
    }

    public StringBuilder getGeneratedBill() {
        return generateBill;
    }
/**Metoda afiseaza o casuta de dialog cu mesajul corespunzator cand se introduc gresit datele de catre utilizator.*/
    public void showWrongDataMessage(String s)
    {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, s);
    }

    /**Metoda utilizata la umplerea automata a textfield-urilor dupa cautarea dupa id a clientului*/
    public void setClientTextFields(String i, String n, String a, String e, String b)
    {
        id_client.setText(i);
        client_name.setText(n);
        address.setText(a);
        email.setText(e);
        birth_date.setText(b);
    }

    /**Metoda utilizata la umplerea automata a textfield-urilor dupa cautarea dupa id a produsului*/
    public void setProductTextFields(String i, String n, String s, String p)
    {
        productName.setText(n);
        stock.setText(s);
        price.setText(p);
    }
}
