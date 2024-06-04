package best.unieats.menu_cui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketItem {
    private final int basketId;
    private final List<DishItem> dishes;

    public BasketItem(int basketId) {
        this.basketId = basketId;
        this.dishes = new ArrayList<>();
    }

    public int getBasketId() {
        return basketId;
    }

    public List<DishItem> getDishes() {
        return dishes;
    }

    public void addDish(DishItem dish) {
        dishes.add(dish);
    }

    public List<Integer> fetchQuantitiesFromDatabase(Connection connection) throws SQLException {
        List<Integer> quantities = new ArrayList<>();
        String query = "SELECT quantity FROM dishinbasket WHERE basketId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, basketId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    quantities.add(quantity);
                }
            }
        }
        return quantities;
    }
}
