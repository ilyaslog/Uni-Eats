package best.unieats.Login;
import static best.unieats.Login.LoginController.username;
import best.unieats.menu_admin.Meal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8701905";
    private static final String USERNAME = "sql8701905";
    private static final String PASSWORD = "EhBFwQIzaY";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC Driver", e);
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUserData(String firstName, String lastName, String email, String phone, String address, String username, String password, String selectedRole, int sold) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastname, email, numero_de_telephone, address, username, password, role, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, address);
            statement.setString(6, username);
            statement.setString(7, password);
            statement.setString(8, selectedRole);
            statement.setInt(9, sold);
            statement.executeUpdate();
            System.out.println("User data inserted successfully!");
        } catch (SQLException e) {
            System.err.println("Error inserting user data: " + e.getMessage());
        }
    }

    public void insertMealData(Meal meal) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO dish (name, description, price, quantity, is_plat_du_jour, image) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, meal.getNomPlat());
            statement.setString(2, meal.getDescription());
            statement.setDouble(3, meal.getPrix());
            statement.setInt(4, meal.getQuantiter());
            statement.setBoolean(5, meal.isPlatDuJour());
            statement.setBytes(6, meal.getImageData());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting meal data: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            return resultSet;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void closeStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      public int getBasketStatus(String username) {
        int status = -1; // Default status if no match found
        
        try {
            Connection connection = connect(); // Connect to the database

            // SQL query to retrieve status from the basket table
            String sql = "SELECT status FROM basket Natural Join users WHERE username = ?  AND status != 4";

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the value for the placeholder in the SQL statement
            statement.setString(1, username);

            // Execute the SQL statement
            ResultSet resultSet = statement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                status = resultSet.getInt("status");
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            closeConnection(connection);
        } catch (SQLException e) {
            // Log or handle the SQLException appropriately
            System.err.println("Error retrieving basket status: " + e.getMessage());
        }

        return status;
    }
public List<DishDetails> getDishesInBasket(String username) {
    List<DishDetails> dishesInBasket = new ArrayList<>();
    int basketId = -1;

    try {
        Connection connection = connect(); // Connect to the database

        // SQL query to retrieve the latest basket ID for the given username
        String basketIdQuery = "SELECT b.basketid " +
                               "FROM basket b " +
                               "INNER JOIN users u ON b.userid = u.userid " +
                               "WHERE u.username = ? AND b.status != 4 " +
                               "ORDER BY b.basketid DESC " +
                               "LIMIT 1";

        // Prepare the SQL statement for basket ID retrieval
        PreparedStatement basketIdStatement = connection.prepareStatement(basketIdQuery);
        basketIdStatement.setString(1, username);

        // Execute the SQL statement
        ResultSet basketIdResultSet = basketIdStatement.executeQuery();

        // Check if a result is found
        if (basketIdResultSet.next()) {
            basketId = basketIdResultSet.getInt("basketid");
        }

        // Close the result set and statement for basket ID retrieval
        basketIdResultSet.close();
        basketIdStatement.close();

        // If a valid basket ID is found, retrieve the dishes in that basket
        if (basketId != -1) {
            String dishesQuery = "SELECT d.name, dib.quantity, (d.price * dib.quantity) AS total_price " +
                                 "FROM dish d " +
                                 "INNER JOIN dishinbasket dib ON d.dishid = dib.dishid " +
                                 "INNER JOIN basket b ON dib.basketid = b.basketid " +
                                 "WHERE b.basketid = ?";

            // Prepare the SQL statement for dish retrieval
            PreparedStatement dishesStatement = connection.prepareStatement(dishesQuery);
            dishesStatement.setInt(1, basketId);

            // Execute the SQL statement
            ResultSet dishesResultSet = dishesStatement.executeQuery();

            // Iterate through the result set and populate the list with dish details
            while (dishesResultSet.next()) {
                String name = dishesResultSet.getString("name");
                int quantity = dishesResultSet.getInt("quantity");
                double totalPrice = dishesResultSet.getDouble("total_price");
                DishDetails dish = new DishDetails(name, quantity, totalPrice);
                dishesInBasket.add(dish);
            }

            // Close the result set and statement for dish retrieval
            dishesResultSet.close();
            dishesStatement.close();
        }

        // Close the connection
        closeConnection(connection);
    } catch (SQLException e) {
        // Log or handle the SQLException appropriately
        System.err.println("Error retrieving dishes in basket: " + e.getMessage());
    }

    return dishesInBasket;
}
     public int getNumCommande(String username) {
        int numCommande = -1; // Default numCommande if no match found

        try {
            Connection connection = connect(); // Connect to the database

            // SQL query to retrieve num_commande from the basket table
            String sql = "SELECT num_comannde FROM basket Natural Join users WHERE username = ?";

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the value for the placeholder in the SQL statement
            statement.setString(1, username);

            // Execute the SQL statement
            ResultSet resultSet = statement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                numCommande = resultSet.getInt("num_comannde");
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            closeConnection(connection);
        } catch (SQLException e) {
            // Log or handle the SQLException appropriately
            System.err.println("Error retrieving num_commande: " + e.getMessage());
        }

        return numCommande;
    }
     
     public List<DishDetails> getDishesInBasketByUsername(String username) {
        List<DishDetails> dishesInBasket = new ArrayList<>();

        String query = "SELECT d.name, db.quantity, (d.price * db.quantity) AS total_price " +
                       "FROM users u " +
                       "JOIN basket b ON u.userid = b.userid " +
                       "JOIN dishinbasket db ON b.basketid = db.basketid " +
                       "JOIN dish d ON db.dishid = d.dishid " +
                       "WHERE u.username = ? AND b.status = 0";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int quantity = resultSet.getInt("quantity");
                    double totalPrice = resultSet.getDouble("total_price");
                    dishesInBasket.add(new DishDetails(name, quantity, totalPrice));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving dishes in basket: " + e.getMessage());
        }

        return dishesInBasket;
    }
     
    public void updateBasketStatus(String username) {
    try {
        Connection connection = connect(); // Connect to the database

        // SQL query to update status from the basket table
        String sql = "UPDATE basket b " +
                     "JOIN users u ON b.userid = u.userid " +
                     "SET b.status = 1 " +
                     "WHERE u.username = ? AND b.status = 0";

        // Prepare the SQL statement
        PreparedStatement statement = connection.prepareStatement(sql);

        // Set the value for the placeholder in the SQL statement
        statement.setString(1, username);

        // Execute the SQL statement
        int rowsUpdated = statement.executeUpdate();

        // Check if any row is updated
        if (rowsUpdated > 0) {
            System.out.println("Basket status updated successfully for user: " + username);
        } else {
            System.out.println("No basket with status 0 found for user: " + username);
        }

        // Close the statement and connection
        statement.close();
        closeConnection(connection);
    } catch (SQLException e) {
        // Log or handle the SQLException appropriately
        System.err.println("Error updating basket status: " + e.getMessage());
    }
}
    
    public void updateBasketStatus2(String username) {
    try {
        Connection connection = connect(); // Connect to the database

        // SQL query to update status from the basket table
        String sql = "UPDATE basket b " +
                     "JOIN users u ON b.userid = u.userid " +
                     "SET b.status = 4 " +
                     "WHERE u.username = ? AND b.status = 3";

        // Prepare the SQL statement
        PreparedStatement statement = connection.prepareStatement(sql);

        // Set the value for the placeholder in the SQL statement
        statement.setString(1, username);

        // Execute the SQL statement
        int rowsUpdated = statement.executeUpdate();

        // Check if any row is updated
        if (rowsUpdated > 0) {
            System.out.println("Basket status updated successfully for user: " + username);
        } else {
            System.out.println("No basket with status 3 found for user: " + username);
        }

        // Close the statement and connection
        statement.close();
        closeConnection(connection);
    } catch (SQLException e) {
        // Log or handle the SQLException appropriately
        System.err.println("Error updating basket status: " + e.getMessage());
    }
}
    public void deleteDishFromBasket(String username, String dishName) {
    String query = "DELETE db " +
                   "FROM dishinbasket db " +
                   "JOIN basket b ON db.basketid = b.basketid " +
                   "JOIN users u ON b.userid = u.userid " +
                   "JOIN dish d ON db.dishid = d.dishid " +
                   "WHERE u.username = ? AND d.name = ? AND b.status = 0";

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, username);
        statement.setString(2, dishName);

        // Execute the deletion
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Dish deleted successfully.");
        } else {
            System.out.println("No dish found with the given name for deletion.");
        }
    } catch (SQLException e) {
        System.err.println("Error deleting dish from basket: " + e.getMessage());
    }
}

    public void updateMeal(Meal meal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     public List<Meal> getFixedMenuDishes() {
        List<Meal> fixedMenuDishes = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish WHERE is_fixed_menu = 1")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomPlat = resultSet.getString("name");
                String description = resultSet.getString("description");
                double prix = resultSet.getDouble("price");
                int quantiter = resultSet.getInt("quantity");
                boolean isPlatDuJour = resultSet.getBoolean("is_plat_du_jour");
                byte[] imageData = resultSet.getBytes("image");
                boolean isFixedMenu = resultSet.getBoolean("is_fixed_menu");

                Meal meal = new Meal(id, nomPlat, description, prix, quantiter, isPlatDuJour);
                meal.setImageData(imageData);
                meal.setFixedMenu(isFixedMenu);

                fixedMenuDishes.add(meal);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving fixed menu dishes: " + e.getMessage());
        }

        return fixedMenuDishes;
    }
   public int getLatestBasketId() {
        int basketId = -1;
        String basketIdQuery = "SELECT b.basketid " +
                       "FROM basket b " +
                       "JOIN users u ON b.userid = u.userid " +
                       "WHERE b.status = 1 or b.status = 2 " +
                       "ORDER BY b.basketid ASC " +
                       "LIMIT 1";

        try (Connection connection = connect();
             PreparedStatement basketIdStatement = connection.prepareStatement(basketIdQuery)) {
            try (ResultSet basketIdResultSet = basketIdStatement.executeQuery()) {
                if (basketIdResultSet.next()) {
                    basketId = basketIdResultSet.getInt("basketid");
                    System.out.println("Retrieved basket ID: " + basketId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving latest basket ID: " + e.getMessage());
        }

        return basketId;
    }
   
   public int getLatestBasketId2() {
        int basketId = -1;
        String basketIdQuery = "SELECT b.basketid " +
                       "FROM basket b " +
                       "JOIN users u ON b.userid = u.userid " +
                       "WHERE b.status = 2 " +
                       "ORDER BY b.basketid ASC " +
                       "LIMIT 1";

        try (Connection connection = connect();
             PreparedStatement basketIdStatement = connection.prepareStatement(basketIdQuery)) {
            try (ResultSet basketIdResultSet = basketIdStatement.executeQuery()) {
                if (basketIdResultSet.next()) {
                    basketId = basketIdResultSet.getInt("basketid");
                    System.out.println("Retrieved basket ID: " + basketId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving latest basket ID: " + e.getMessage());
        }

        return basketId;
    }

    public List<DishesEnCours> getDishesInBasket(int basketId) {
        List<DishesEnCours> dishesInBasket = new ArrayList<>();

        String dishesQuery = "SELECT d.name, db.quantity " +
                             "FROM dish d " +
                             "JOIN dishinbasket db ON d.dishid = db.dishid " +
                             "WHERE db.basketid = ?";

        try (Connection connection = connect();
             PreparedStatement dishesStatement = connection.prepareStatement(dishesQuery)) {
            dishesStatement.setInt(1, basketId);
            try (ResultSet dishesResultSet = dishesStatement.executeQuery()) {
                while (dishesResultSet.next()) {
                    String name = dishesResultSet.getString("name");
                    int quantity = dishesResultSet.getInt("quantity");
                    dishesInBasket.add(new DishesEnCours(name, quantity));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving dishes in basket: " + e.getMessage());
        }
        
        updateBasketStatusTo2(basketId);

        return dishesInBasket;
    }
    
    public int countBasketsWithStatus1() {
    int count = 0;
    String countQuery = "SELECT COUNT(*) AS total FROM basket WHERE status = 1";

    try (Connection connection = connect();
         PreparedStatement countStatement = connection.prepareStatement(countQuery);
         ResultSet resultSet = countStatement.executeQuery()) {
        if (resultSet.next()) {
            count = resultSet.getInt("total");
        }
    } catch (SQLException e) {
        System.err.println("Error counting baskets with status 1: " + e.getMessage());
    }

    return count;
}
    public void updateBasketStatusTo2(int basketId) {
    String updateStatusQuery = "UPDATE basket SET status = 2 WHERE basketid = ?";

    try (Connection connection = connect();
         PreparedStatement updateStatusStatement = connection.prepareStatement(updateStatusQuery)) {
        updateStatusStatement.setInt(1, basketId);
        int rowsUpdated = updateStatusStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Basket status updated to 2 for basket ID: " + basketId);
        } else {
            System.out.println("No basket found with ID: " + basketId);
        }
    } catch (SQLException e) {
        System.err.println("Error updating basket status to 2: " + e.getMessage());
    }
}
    public void updateBasketStatusTo3(int basketId) {
    String updateStatusQuery = "UPDATE basket SET status = 3 WHERE basketid = ?";

    try (Connection connection = connect();
         PreparedStatement updateStatusStatement = connection.prepareStatement(updateStatusQuery)) {
        updateStatusStatement.setInt(1, basketId);
        int rowsUpdated = updateStatusStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Basket status updated to 3 for basket ID: " + basketId);
        } else {
            System.out.println("No basket found with ID: " + basketId);
        }
    } catch (SQLException e) {
        System.err.println("Error updating basket status to 3: " + e.getMessage());
    }
}
public List<DishDetails> getLatestBasketWithStatus(int userId, int status) {
    List<DishDetails> basket = new ArrayList<>();
    
    try {
        // Assuming you have a database connection established
        Connection connection = connect(); // Adjust this line if your connection method is different
        
        // Construct the SQL query to get the latest basket with the specified status for the given user ID
        String query = "SELECT * FROM basket WHERE userid = ? AND status = ? ORDER BY basket_id DESC LIMIT 1";
        
        // Create the prepared statement
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, status);
        
        // Execute the query
        ResultSet resultSet = statement.executeQuery();
        
        // Iterate through the result set and populate the basket list
        while (resultSet.next()) {
            // Assuming DishDetails has a constructor that takes the necessary parameters
            DishDetails dish = new DishDetails(resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getDouble("total_price"));
            basket.add(dish);
        }
        
        // Close the resources
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
    
    return basket;
}
}

