package Presentation;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clasa se ocupa de tiparirea facturii,adica scrierea ei in fisier.
 */
public class GenerateBill {
    /**
     * Constructorul deschide un nou fisier pentru fiecare comanda plasata si
     * scrie in el factura comenzii.
     * @param bill Textul ce va aparea in fisier cu toate detaliile de plata.
     * @param fileName Numele fisierului,care va contine id-ul comenzii.
     */
    public GenerateBill(StringBuilder bill,String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(String.valueOf(bill));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
