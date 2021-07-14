package Model;

import java.math.BigDecimal;

/**
 * Clasa retine informatii despre un produs
 */
public class Product {
    /** id produs*/
    private int id;
    /** nume produs*/
    private String name;
    /**stocul produsului*/
    private int stock;
    /**pretul produsului*/
    private BigDecimal price;

    public Product(int id, String name, int stock, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
