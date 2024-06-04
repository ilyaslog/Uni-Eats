
package best.unieats.Login;

/**
 *
 * @author ammar
 */
public class DishesEnCours {
    private String name;
    private int quantity;

    public DishesEnCours(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // Getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}