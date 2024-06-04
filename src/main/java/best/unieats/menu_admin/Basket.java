package best.unieats.menu_admin;

public class Basket {
    private int basketId;
    private int userId;
    private String nomClient;
    private String prenomClient;
    private String date;
    private int status;
    private String statusLabel;

    public Basket() {
        // Default constructor
    }

    public Basket(int basketId, int userId, String nomClient, String prenomClient, String date, int status, String statusLabel) {
        this.basketId = basketId;
        this.userId = userId;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.date = date;
        this.status = status;
        this.statusLabel = statusLabel;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusAsString() {
        switch (status) {
            case 0:
                return "En attente";
            case 1:
                return "En cours";
            case 2:
                return "En train de se faire livre";
            case 3:
                return "Livree";
            default:
                return "Unknown";
        }
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}
