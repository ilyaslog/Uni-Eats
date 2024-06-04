package best.unieats.menu_admin;

public class Meal {
    private int id;
    private String nomPlat;
    private String description;
    private double prix;
    private int quantiter;
    private boolean isPlatDuJour;
    private byte[] imageData; // New field for storing image data
    private boolean isFixedMenu;


    public Meal(int id, String nomPlat, String description, double prix, int quantiter, boolean isPlatDuJour) {
        this.id = id;
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
        this.quantiter = quantiter;
        this.isPlatDuJour = isPlatDuJour;
        this.isFixedMenu = isFixedMenu;
    }
    
    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for nomPlat
    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for prix
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Getter and setter for quantiter
    public int getQuantiter() {
        return quantiter;
    }

    public void setQuantiter(int quantiter) {
        this.quantiter = quantiter;
    }

    // Getter and setter for isPlatDuJour
    public boolean isPlatDuJour() {
        return isPlatDuJour;
    }

    public void setPlatDuJour(boolean platDuJour) {
        isPlatDuJour = platDuJour;
    }

    // Getter and setter for imageData
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
       public boolean isFixedMenu() {
        return isFixedMenu;
    }

    public void setFixedMenu(boolean fixedMenu) {
        isFixedMenu = fixedMenu;
    }
      public void update(String nomPlat, String description, double prix, int quantiter, boolean isPlatDuJour, byte[] imageData, boolean isFixedMenu) {
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
        this.quantiter = quantiter;
        this.isPlatDuJour = isPlatDuJour;
        this.imageData = imageData;
        this.isFixedMenu = isFixedMenu;
    }
}
