package Presentation;

import BusinessLogic.ClientBusiness;
import BusinessLogic.OrderBusiness;
import BusinessLogic.ProductBusiness;
import BusinessLogic.Validators;
import Model.Client;
import Model.Order;
import Model.Product;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Preia datele introduce de utilizator
 * si le trimite claselor din BusinessLogic
 */
public class Controller {
    private Interface anInterface;
    private ClientTable clientTable;
    private ProductTable productTable;
    private Validators validators;


    public Controller() {
        anInterface = new Interface();
        clientTable = new ClientTable();
        productTable=new ProductTable();
        validators=new Validators();

        anInterface.pack();
        anInterface.setLocationRelativeTo(null);
        anInterface.setVisible(true);

        anInterface.setTabsChangeListener(new TabsListener());
        anInterface.setInsertButtonActionListener(new InsertButtonListener());
        anInterface.setSearchByIdButtonActionListener(new SearchByIdListener());
        anInterface.setUpdateClientButtonActionListener(new UpdateButtonClientListener());
        anInterface.setDeleteByIdButtonActionListener(new DeleteButtonListener());

        anInterface.setInsertButtonProductActionListener(new InsertProductActionListener());
        anInterface.setSearchByIdProductActionListener(new SearchByIdProductListener());
        anInterface.setUpdateProductButtonActionListener(new UpdateButtonProductListener());
        anInterface.setDeleteByIdProductActionListener(new DeleteProductListener());
        anInterface.setPlaceTheOrderButtonActionListener(new PlaceTheOrderListener());

    }

    private class TabsListener implements ChangeListener
    {

        @Override
        public void stateChanged(ChangeEvent e) {

            JTabbedPane t= (JTabbedPane) e.getSource();

            if(t.getSelectedIndex()==0) //tabul pentru client
            {//daca e selectat,se face update la tabelul client
                ClientBusiness clientBusiness=new ClientBusiness();
                clientTable.generateTableReflection(clientBusiness.findAllClients());
                anInterface.setClientTableModel(clientTable.getTableModel());
            }

            if(t.getSelectedIndex()==1) //tabul pentru produs
            {//daca e selectat,se face update la tabelul produselor
                ProductBusiness productBusiness=new ProductBusiness();
                productTable.generateTableReflection(productBusiness.findAllProducts());
                anInterface.setProductTableModel(productTable.getTableModel());

            }

            if(t.getSelectedIndex()==2) //tabul pentru order
            {//daca e selectat,se face update la tabelul clientilor si al produselor
                ClientBusiness clientBusiness=new ClientBusiness();
                clientTable.generateTableReflection(clientBusiness.findAllClients());
                ProductBusiness productBusiness=new ProductBusiness();
                productTable.generateTableReflection(productBusiness.findAllProducts());
                anInterface.setOrderClientTableModel(clientTable.getTableModel());
                anInterface.setOrderProductTableModel(productTable.getTableModel());

            }

        }
    }

    private class InsertButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name=anInterface.getClientName().getText();
            String address=anInterface.getAddress().getText();
            String email=anInterface.getEmail().getText();
            String birth_date=anInterface.getBirth_date().getText();
            int id=0;//id e auto incrementat setat in baza
                                // de date,deci nu conteaza
            if(validators.validateEmailAndBirthDate(email,birth_date))
            {
                Client client = null;
                try {
                    client = new Client(id, name, address, email,
                            new SimpleDateFormat("dd-MM-yyyy").parse(birth_date));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                ClientBusiness clientBusiness = new ClientBusiness();
                clientBusiness.insertClient(client);
                clientTable.generateTableReflection(clientBusiness.findAllClients());
                anInterface.setClientTableModel(clientTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Email-ul sau data nasterii sunt incorect introduse!\n" +
                        "Format email:something@something.something\n" +
                        "Format data nasterii:dd-MM-yyyy");


        }
    }

    private class SearchByIdListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id=anInterface.getId_client().getText();
            ClientBusiness clientBusiness=new ClientBusiness();
            clientTable.generateTableReflection(clientBusiness.findAllClients());

            if(validators.validateId(id))
            {
                List<Client> searchedClient = new ArrayList<>();

                try {
                    searchedClient.add(clientBusiness.findById(Integer.parseInt(id)));
                }catch(IndexOutOfBoundsException ex)
                {clientTable.generateTableReflection(clientBusiness.findAllClients());
                    anInterface.setClientTableModel(clientTable.getTableModel());
                    anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");return;
                    }

                clientTable.generateTableReflection(searchedClient);
                anInterface.setClientTableModel(clientTable.getTableModel());

                String client_id = String.valueOf(searchedClient.get(0).getId());
                String name = searchedClient.get(0).getName();
                String address = searchedClient.get(0).getAddress();
                String email = searchedClient.get(0).getEmail();
                String birth_date = new SimpleDateFormat("dd-MM-yyyy").format(searchedClient.get(0).getBirth_date());
                anInterface.setClientTextFields(client_id, name, address, email, birth_date);
            }
            else
                anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");

        }
    }

    private class UpdateButtonClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name=anInterface.getClientName().getText();
            String address=anInterface.getAddress().getText();
            String email=anInterface.getEmail().getText();
            String birth_date=anInterface.getBirth_date().getText();
            String id=anInterface.getId_client().getText();
            if(validators.validateEmailAndBirthDate(email,birth_date)) {
                Client client = null;
                try {
                    client = new Client(Integer.parseInt(id), name, address, email,
                            new SimpleDateFormat("dd-MM-yyyy").parse(birth_date));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                ClientBusiness clientBusiness = new ClientBusiness();
                clientBusiness.updateClient(client);
                clientTable.generateTableReflection(clientBusiness.findAllClients());
                anInterface.setClientTableModel(clientTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Email-ul sau data nasterii sunt incorect introduse!\n" +
                        "Format email:something@something.something\n" +
                        "Format data nasterii:dd-MM-yyyy");


        }
    }

    private class DeleteButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            String id=anInterface.getId_client().getText();
            ClientBusiness clientBusiness=new ClientBusiness();
            clientTable.generateTableReflection(clientBusiness.findAllClients());

            if(validators.validateId(id))
            {
                List<Client> searchedClient = new ArrayList<>();

                try {
                    searchedClient.add(clientBusiness.findById(Integer.parseInt(id)));
                }catch(IndexOutOfBoundsException ex)
                { anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");}

                clientBusiness.deleteClient(Integer.parseInt(id));
                clientTable.generateTableReflection(clientBusiness.findAllClients());
                anInterface.setClientTableModel(clientTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");

        }
    }

    private class InsertProductActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String product_name=anInterface.getProductName().getText();
            String price=anInterface.getPrice().getText();
            String stock=anInterface.getStock().getText();
            if(validators.validateStockAndPriceProduct(stock,price)) {
                int id = 0;//id e auto incrementat setat in baza
                // de date,deci nu conteaza
                Product product = new Product(id, product_name, Integer.parseInt(stock), new BigDecimal(price));

                ProductBusiness productBusiness = new ProductBusiness();
                productBusiness.insertProduct(product);
                productTable.generateTableReflection(productBusiness.findAllProducts());
                anInterface.setProductTableModel(productTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Stocul sau pretul sunt incorect introduse!");

        }
    }

    private class SearchByIdProductListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id=anInterface.getId_product().getText();
            if(validators.validateId(id))
            {
                ProductBusiness productBusiness = new ProductBusiness();
                List<Product> searchedProduct = new ArrayList<>();

                try {
                    searchedProduct.add(productBusiness.findById(Integer.parseInt(id)));
                }catch(IndexOutOfBoundsException ex)
                {productTable.generateTableReflection(productBusiness.findAllProducts());
                    anInterface.setProductTableModel(productTable.getTableModel());
                    anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");return;}

                productTable.generateTableReflection(searchedProduct);
                anInterface.setProductTableModel(productTable.getTableModel());
                String product_id = String.valueOf(searchedProduct.get(0).getId());
                String product_name = searchedProduct.get(0).getName();
                String stock = String.valueOf(searchedProduct.get(0).getStock());
                String price = String.valueOf(searchedProduct.get(0).getPrice());
                anInterface.setProductTextFields(product_id, product_name, stock, price);
            }
            else
                anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");

        }
    }

    private class UpdateButtonProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String product_name=anInterface.getProductName().getText();
            String price=anInterface.getPrice().getText();
            String stock=anInterface.getStock().getText();
            if(validators.validateStockAndPriceProduct(stock,price)) {

                String id = anInterface.getId_product().getText();
                Product product = null;

                product = new Product(Integer.parseInt(id), product_name, Integer.parseInt(stock), new BigDecimal(price));


                ProductBusiness productBusiness = new ProductBusiness();
                productBusiness.updateProduct(product);
                productTable.generateTableReflection(productBusiness.findAllProducts());
                anInterface.setProductTableModel(productTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Stocul sau pretul sunt incorect introduse!");

        }
    }

    private class DeleteProductListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            String id=anInterface.getId_product().getText();
            if(validators.validateId(id))
            {
                ProductBusiness productBusiness = new ProductBusiness();
                List<Product> searchedProduct = new ArrayList<>();
                try {
                    searchedProduct.add(productBusiness.findById(Integer.parseInt(id)));
                }catch(IndexOutOfBoundsException ex)
                { anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");}

                productBusiness.deleteProduct(Integer.parseInt(id));
                productTable.generateTableReflection(productBusiness.findAllProducts());
                anInterface.setProductTableModel(productTable.getTableModel());
            }
            else
                anInterface.showWrongDataMessage("Id-ul cautat nu exista in baza de date!");
        }
    }

    private class PlaceTheOrderListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            //preiau id-ul clientului selectat si al produsului selectat
            String quantity=anInterface.getOrderProductQuantity().getText();

            if(validators.validateProductQuantity(quantity)) {
                int noSelRowClient = anInterface.getOrderClientTable().getSelectedRow();

                int id_client = Integer.parseInt((String) anInterface.getOrderClientTable().getValueAt(noSelRowClient, 0));
                int noSelRowProduct = anInterface.getOrderProductTable().getSelectedRow();

                int id_product = Integer.parseInt((String) anInterface.getOrderProductTable().getValueAt(noSelRowProduct, 0));

                Order order = new Order(0, id_client, id_product, Integer.parseInt(quantity));//id e auto incrementat setat in baza
                // de date,deci nu conteaza
                //inserare comanda in baza de date
                OrderBusiness orderBusiness = new OrderBusiness();
                orderBusiness.insertOrder(order);

                //decrementare stoc produs in baza de date,update pt tabelul produselor
                int initial_stoc = Integer.parseInt((String) anInterface.getOrderProductTable().getValueAt(noSelRowProduct, 2));
                int final_stoc = initial_stoc - Integer.parseInt(quantity);

                System.out.println(final_stoc);
                if (final_stoc < 0)
                    anInterface.setTextArea("UNDER STOCK!\n");
                else {
                    String productName = (String) anInterface.getOrderProductTable().getValueAt(noSelRowProduct, 1);
                    Product product = new Product(id_product, productName, final_stoc, new BigDecimal((String) anInterface.getOrderProductTable().getValueAt(noSelRowProduct, 3)));

                    ProductBusiness productBusiness = new ProductBusiness();
                    productBusiness.updateProduct(product);
                    productTable.generateTableReflection(productBusiness.findAllProducts());
                    anInterface.setOrderProductTableModel(productTable.getTableModel());

                    //actualizare istoric comenzi al clientului,vizibil in textArea
                    List<Order> orders = orderBusiness.findOrdersByClientId(id_client);
                    anInterface.emptyOrderList();   //golire lista de comenzi de la clientul anterior

                    for (Order o : orders) {
                            Product p = productBusiness.findById(o.getId_product());
                            anInterface.addOrderInList(p.getName(), String.valueOf(o.getQuantity()));

                    }
                    anInterface.setTextArea("");//golire text anterior
                    anInterface.setTextArea(anInterface.getOrderList());

                    String clientName = (String) anInterface.getOrderClientTable().getValueAt(noSelRowClient, 1);
                    String clientAddress = (String) anInterface.getOrderClientTable().getValueAt(noSelRowClient, 2);
                    String price = (String) anInterface.getOrderProductTable().getValueAt(noSelRowProduct, 3);
                    String id_order = String.valueOf(orders.get(orders.size() - 1).getId());//id comanda actuala plasata

                    //creare factura,scriere in fisier text
                    anInterface.createBill(id_order, clientName, productName, clientAddress, quantity, price);
                    GenerateBill generateBill = new GenerateBill(anInterface.getGeneratedBill(), "billOrder" + id_order);
                }
            }
            else
                anInterface.showWrongDataMessage("Cantitatea de produs introdusa nu are format corect!\nIntroduceti din nou" +
                        " un numar natural>0");
        }
    }
}
