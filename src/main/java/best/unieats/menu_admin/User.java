package best.unieats.menu_admin;



import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String adress;
    private double tel;
    private String username;

    public User(int id, String nom, String prenom, String email, String adress, double tel, String username) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adress = adress;
        this.tel = tel;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public double getTel() {
        return tel;
    }

    public String getUsername() {
        return username;
    }
}
