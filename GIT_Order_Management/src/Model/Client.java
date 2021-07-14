package Model;

import java.util.Date;

/** Clasa retine datele despre un client*/
public class Client{
    /**id-ul clientului*/
    private int id;
    /**nume client*/
    private String name;
    /** adresa la care locuieste clientul*/
    private String address;
    /**email client*/
    private String email;
    /**Data nasterii clientului*/
    private Date birth_date;

    public Client(int id, String name, String address, String email, Date birth_date) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.birth_date = birth_date;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", birth_date=" + birth_date +
                '}';
    }
}
