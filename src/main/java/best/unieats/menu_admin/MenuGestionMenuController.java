
package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author saad3
 */
public class MenuGestionMenuController {
     @FXML
    private Pane ProductPane;
     @FXML
    private ImageView Photo;
    @FXML
    private Label nom1;
    @FXML
    private Label desc;
    @FXML
    private Label prix1;
    @FXML
    private Label desc1;
    @FXML
    private Label Quantiter;
    //Table des plats
    @FXML
    private TableView<Meal> tableplat;

    @FXML
    private TableColumn<Meal, Integer> IDPLAT;
    
    @FXML
    private TableColumn<Meal, String> Nomplat;
    
    @FXML
    private TableColumn<Meal, String> Desplat;
    
    @FXML
    private TableColumn<Meal, Double> prixplat;
    
    @FXML
    private TableColumn<Meal, Integer> quantiplat;
    //Table des Menue
      @FXML
    private TableView<Meal> tablemenuquoti;

    @FXML
    private TableColumn<Meal, Integer> idmenu;
    
    @FXML
    private TableColumn<Meal, String> nommenuplat;
    
    @FXML
    private TableColumn<Meal, String> desmenuplat;
    
    @FXML
    private TableColumn<Meal, Double> prixmenuplat;
    
    @FXML
    private TableColumn<Meal, Integer> quantimenuplat;
        @FXML
    private Pane SideMenu1;
  
    @FXML
    private Pane SideMenu2;
    
     @FXML
    private Pane SideMenu3;
     
    @FXML
    private Button BtnPanier;
    // Bouton a gauche de la page
    @FXML
    private Button btn3Bars;
// Bouton a droite de la page
    @FXML
    private Button btnProfile;
    // Boutton qui push vers la table plats
     @FXML
    private Button outbtn;
    // Boutton qui push vers la table Menu
     @FXML
    private Button inbtn;
//Les boutons du side menu 1 actioner par bt3Bars
    @FXML
    private Button btnMenuQuotidien;
    
    @FXML
    private Button btnGestion_Menu;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnPlats;
    @FXML
    private Button btnSaveDishofday;
    @FXML
    private Button btnCommande;
    
    @FXML
    private Button btnCompte;
    
    @FXML
    private Button btnStats;
    
    //Les boutons du side menu 2 actioner par btnProfile
    @FXML
    private Button btnDetails;
    
    @FXML
    private Button btnDeconnecter;
    
    @FXML
    private Pane MainMenu;
    
    @FXML
    private Label Datedujour;

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
    private boolean isMenu3Open = false;
    
    private static final String SELECT_DISH_QUERY = "SELECT * FROM dish";
    
@FXML
private void initialize() throws SQLException {
      LocalDate currentDate = LocalDate.now();
      populateMenuTable();
        // Format the date as DD/MM/YYYY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        // Set the formatted date as the text of the label
        Datedujour.setText(formattedDate);
    // Clear the menu items list first
    menuItems.clear();

    // Populate the menu table with data for the current date
  
   displayDishOfTheDay();
    // Initialize columns for the table of plats
    IDPLAT.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleIntegerProperty(meal.getId()).asObject() : null;
    });
    Nomplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleStringProperty(meal.getNomPlat()) : null;
    });
    Desplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleStringProperty(meal.getDescription()) : null;
    });
    prixplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleDoubleProperty(meal.getPrix()).asObject() : null;
    });
    quantiplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleIntegerProperty(meal.getQuantiter()).asObject() : null;
    });

    // Populate the table of plats with data
    populateTableWithDishData();

    // Initialize columns for the table of menu
    idmenu.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleIntegerProperty(meal.getId()).asObject() : null;
    });
    nommenuplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleStringProperty(meal.getNomPlat()) : null;
    });
    desmenuplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleStringProperty(meal.getDescription()) : null;
    });
    prixmenuplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleDoubleProperty(meal.getPrix()).asObject() : null;
    });
    quantimenuplat.setCellValueFactory(cellData -> {
        Meal meal = cellData.getValue();
        return meal != null ? new SimpleIntegerProperty(meal.getQuantiter()).asObject() : null;
    });



    // Initialize side menu panels
    SideMenu1.setTranslateX(-SideMenu1.getWidth());
    SideMenu2.setTranslateX(SideMenu2.getWidth());
    SideMenu3.setTranslateX(SideMenu3.getWidth());

    // Define actions for buttons
    btn3Bars.setOnAction(event -> {
        if (isMenu1Open) {
            closeMenu(SideMenu1);
        } else {
            openMenu(SideMenu1);
        }
        isMenu1Open = !isMenu1Open;
    });

    btnProfile.setOnAction(event -> {
        if (isMenu2Open) {
            closeMenu2(SideMenu2);
            BtnPanier.setDisable(false);
        } else {
            openMenu2(SideMenu2);
            BtnPanier.setDisable(true);
        }
        isMenu2Open = !isMenu2Open;
    });

    BtnPanier.setOnAction(event -> {
        if (isMenu3Open) {
            closeMenu2(SideMenu3);
            btnProfile.setDisable(false);
        } else {
            openMenu2(SideMenu3);
            btnProfile.setDisable(true);
        }
        isMenu3Open = !isMenu3Open;
    });
}
@FXML
public void printDishesToFile() {
    // Obtenir la date actuelle et la formater
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    // Obtenir le chemin du dossier Downloads
    String userHome = System.getProperty("user.home");
    Path downloadsPath = Paths.get(userHome, "Downloads");

    // Générer le nom du fichier avec une version si nécessaire
    String baseFileName = "dishes_" + formattedDate;
    String fileName = baseFileName + ".doc";
    int version = 1;

    // Vérifier si le fichier existe déjà et incrémenter la version si nécessaire
    while (Files.exists(downloadsPath.resolve(fileName))) {
        version++;
        fileName = baseFileName + "_v" + version + ".doc";
    }

    // Créer le chemin complet du fichier
    Path filePath = downloadsPath.resolve(fileName);

    try (Connection connection = DatabaseConnector.connect();
         PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), false))) { // false pour remplacer le contenu existant
        // Requête SQL pour sélectionner tous les plats
        String selectQuery1 = "SELECT * FROM dish WHERE is_plat_du_jour=1";
        String selectQuery2 = "SELECT * FROM dish WHERE is_fixed_menu=1";
        writer.println();
        writer.println();
        writer.println();
        writer.println();
        writer.println();

        writer.println("\t\t\t----------------Menu-----------------");
        writer.println("\t\t\t----------- " + formattedDate+"--------");
        writer.println("\t\t\t-------------Plat du jour------------");

        writer.println(); // Ajouter une ligne vide pour séparer la date des plats

        try (PreparedStatement statement = connection.prepareStatement(selectQuery1);
             ResultSet resultSet = statement.executeQuery()) {
            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                // Récupérer les détails du plat
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                // Écrire les détails du plat dans le fichier texte
                writer.println("\tNom: " + name);
                writer.println("Description: " + description);
                writer.println("\tPrix: " + price);
                writer.println(); // Ajouter une ligne vide pour séparer les plats
            }
        }

        writer.println("\t\t\t----------Menu Quotidien------------");
        writer.println(); // Ajouter une ligne vide pour séparer la date des plats

        try (PreparedStatement statement = connection.prepareStatement(selectQuery2);
             ResultSet resultSet = statement.executeQuery()) {
            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                // Récupérer les détails du plat
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                // Écrire les détails du plat dans le fichier texte
                writer.println("\tNom: " + name);
                writer.println("Description: " + description);
                writer.println("\tPrix: " + price);
                writer.println(); // Ajouter une ligne vide pour séparer les plats
            }
        }

        System.out.println("Les plats ont été écrits dans le fichier " + filePath.toString() + " avec succès.");
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}
@FXML
private void saveDishOfDay(ActionEvent event) {
    // Get the selected meal from the product panel
    Meal selectedMeal = tableplat.getSelectionModel().getSelectedItem();
    
    if (selectedMeal != null) {
        try (Connection connection = DatabaseConnector.connect()) {
            // Prepare the UPDATE statement to mark the previous dish of the day as not the dish of the day
            String updatePreviousDishQuery = "UPDATE dish SET is_plat_du_jour = 0";
            PreparedStatement clearPreviousDishStatement = connection.prepareStatement(updatePreviousDishQuery);
            
            // Execute the UPDATE statement to clear the previous dish of the day
            clearPreviousDishStatement.executeUpdate();
            
            // Prepare the UPDATE statement to mark the selected meal as the dish of the day
            String updateSelectedDishQuery = "UPDATE dish SET is_plat_du_jour = 1 WHERE dishid = ?";
            PreparedStatement saveDishStatement = connection.prepareStatement(updateSelectedDishQuery);
            
            // Set the parameters for the UPDATE statement
            saveDishStatement.setInt(1, selectedMeal.getId()); // Assuming dishid corresponds to the ID of the meal
            
            // Execute the UPDATE statement to mark the selected meal as the dish of the day
            saveDishStatement.executeUpdate();
            
            // Insert the selected dish into the menu table
            String insertMenuQuery = "INSERT INTO menu (dishid, date) VALUES (?, ?)";
            PreparedStatement insertMenuStatement = connection.prepareStatement(insertMenuQuery);
            insertMenuStatement.setInt(1, selectedMeal.getId());
            insertMenuStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            insertMenuStatement.executeUpdate();
            
            // Update the UI to reflect the new dish of the day
            displayDishOfTheDay();
            
            System.out.println("Dish of the day saved successfully");
            
            // Schedule a task to revert is_plat_du_jour to 0 after 24 hours
            scheduleDishOfDayReversion(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("No meal selected.");
    }
}

private void scheduleDishOfDayReversion(Connection connection) {
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    
    // Calculate the date after 24 hours
    LocalDate nextDay = currentDate.plusDays(1);
    
    // Create a LocalDateTime for 24 hours from now
    LocalDateTime twentyFourHoursLater = nextDay.atStartOfDay();
    
    // Schedule a task to run after 24 hours
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            try {
                // Prepare the UPDATE statement to revert is_plat_du_jour to 0
                String updateQuery = "UPDATE dish SET is_plat_du_jour = 0";
                PreparedStatement revertDishOfDayStatement = connection.prepareStatement(updateQuery);
                
                // Execute the UPDATE statement to revert is_plat_du_jour to 0
                revertDishOfDayStatement.executeUpdate();
                
                System.out.println("Dish of the day reverted successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }, Date.from(twentyFourHoursLater.atZone(ZoneId.systemDefault()).toInstant()));
}
private static final String SELECT_DISH_OF_THE_DAY_QUERY = 
        "SELECT * FROM menu Natural Join dish WHERE is_plat_du_jour = 1 AND date = ?";

private void displayDishOfTheDayFor24Hr() {
    try (Connection connection = DatabaseConnector.connect()) {
        PreparedStatement statement = connection.prepareStatement(SELECT_DISH_OF_THE_DAY_QUERY);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Retrieve dish details
            int id = resultSet.getInt("dishid");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            Blob imageBlob = resultSet.getBlob("image");
            byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

            // Create a Meal object
            Meal meal = new Meal(id, name, description, price, quantity, true);

            // Display the meal details
            displayMealDetails(meal, imageData);
        } else {
            // If no dish of the day found, clear the display
            clearMealDetails();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void displayMealDetails(Meal meal, byte[] imageData) {
    // Set the retrieved data to the FXML elements
    // Assuming you have FXML elements corresponding to the meal details (name, description, price)
    nom1.setText(meal.getNomPlat());
    desc.setText(meal.getDescription());
    prix1.setText(String.valueOf(meal.getPrix()));
    Quantiter.setText(String.valueOf(meal.getQuantiter()));
    // Set the image for the ImageView
    Image image = new Image(new ByteArrayInputStream(imageData));
    Photo.setImage(image);
}


private void displayDishOfTheDay() {
    try (Connection connection = DatabaseConnector.connect()) {
        PreparedStatement statement = connection.prepareStatement(SELECT_DISH_OF_THE_DAY_QUERY);
        statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Retrieve dish details
            int id = resultSet.getInt("dishid");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            
            // Create a Meal object
            Meal meal = new Meal(id, name, description, price, quantity, true);

            // Display the meal details
            displayMealDetails(meal);
        } else {
            // If no dish of the day found, clear the display
            clearMealDetails();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

     private void displayMealDetails(Meal meal) {
    nom1.setText(meal.getNomPlat());
    desc.setText(meal.getDescription());
    prix1.setText(String.valueOf(meal.getPrix()));
    Quantiter.setText(String.valueOf(meal.getQuantiter()));
    
    try (Connection connection = DatabaseConnector.connect()) {
        String selectQuery = "SELECT image FROM dish WHERE dishid = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, meal.getId());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Blob imageBlob = resultSet.getBlob("image");
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    
                    // Convert byte array to Image and set it in an ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo.setImage(image);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Method to clear meal details
    private void clearMealDetails() {
        nom1.setText("");
        desc.setText("");
        prix1.setText("");
        Quantiter.setText("");
    }
    private void moveDishToProductPanel(Meal meal) {
    if (meal != null) {
        // Populate the labels with meal details
        nom1.setText(meal.getNomPlat());
        desc.setText(meal.getDescription());
        prix1.setText(String.valueOf(meal.getPrix()));
        Quantiter.setText(String.valueOf(meal.getQuantiter()));
    } else {
        // Clear the labels if no meal is selected
        nom1.setText("");
        desc.setText("");
        prix1.setText("");
        Quantiter.setText("");
    }
}
@FXML
private void moveDishToProductPanel(ActionEvent event) {
    Meal selectedMeal = tableplat.getSelectionModel().getSelectedItem();
    moveDishToProductPanel(selectedMeal);
}
@FXML
private void removeDishFromProductPanel(ActionEvent event) {
    // Clear the labels
    nom1.setText("");
    desc.setText("");
    prix1.setText("");
    Quantiter.setText("");
}
@FXML
private void removeFromProductPanel(ActionEvent event) {
    removeDishFromProductPanel(event);
}


    
private void populateTableWithDishData() throws SQLException {
    tableplat.refresh();
    try (Connection connection = DatabaseConnector.connect();
         PreparedStatement statement = connection.prepareStatement(SELECT_DISH_QUERY);
         ResultSet resultSet = statement.executeQuery()) {

        ObservableList<Meal> meals = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int id = resultSet.getInt("dishid");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");

            // Since we're displaying all dishes, isPlatDuJour can be set to false
            boolean isPlatDuJour = false;

            // Add the retrieved meal to the list
            meals.add(new Meal(id, name, description, price, quantity, isPlatDuJour));
        }

        // Set the items in the menu table
        tableplat.setItems(meals);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void updateFixedMenuStatus(int dishId, boolean isFixedMenu) {
    try (Connection connection = DatabaseConnector.connect()) {
        String updateQuery = "UPDATE dish SET is_fixed_menu = ? WHERE dishid = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setBoolean(1, isFixedMenu);
            statement.setInt(2, dishId);
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private ObservableList<Meal> menuItems = FXCollections.observableArrayList();
@FXML
private void moveDishToMenu(ActionEvent event) {
    Meal selectedMeal = tableplat.getSelectionModel().getSelectedItem();

    if (selectedMeal != null) {
        if (selectedMeal.isFixedMenu()) {
            System.out.println("Cannot move a fixed menu dish.");
            return;
        }

        // Set is_fixed_menu to 1 for the selected dish in the database
        updateFixedMenuStatus(selectedMeal.getId(), true);
        
        // Update the local Meal object
        selectedMeal.setFixedMenu(true);
        
        // Add the selected dish to the menuItems list
        menuItems.add(selectedMeal);

        // Populate the menu table with data from the menuItems list
        populateMenuTable();

        System.out.println("Plat Selectionné: " + selectedMeal);
    } else {
        System.out.println("Aucun plat sélectionné.");
    }
}

@FXML
private void removeFromMenu(ActionEvent event) {
    Meal selectedMeal = tablemenuquoti.getSelectionModel().getSelectedItem();

    if (selectedMeal != null) {
        // Set is_fixed_menu to 0 for the selected dish in the database
        updateFixedMenuStatus(selectedMeal.getId(), false);

        // Update the local Meal object
        selectedMeal.setFixedMenu(false);

        // Remove the selected dish from the menuItems list
        menuItems.remove(selectedMeal);

        // Update the menu table with the modified menuItems list
        populateMenuTable();
    } else {
        System.out.println("Aucun plat sélectionné dans le menu.");
    }
}

@FXML
private void populateMenuTable() {
    try (Connection connection = DatabaseConnector.connect()) {
        String query = "SELECT * FROM dish WHERE is_fixed_menu = 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            // Create a list to store the fetched meals
            List<Meal> menuItems = new ArrayList<>();
            
            // Iterate over the ResultSet and create Meal objects
            while (resultSet.next()) {
                int id = resultSet.getInt("dishid");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                
                // Create a Meal object and add it to the list
                Meal meal = new Meal(id, name, description, price, quantity, true);
                menuItems.add(meal);
            }
            
            // Convert the list to an observable list
            ObservableList<Meal> observableMenuItems = FXCollections.observableArrayList(menuItems);
            
            // Set the items of the TableView
            tablemenuquoti.setItems(observableMenuItems);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



@FXML
private void insertMenuItemsIntoDatabase(ActionEvent event) {
    try (Connection connection = DatabaseConnector.connect()) {
        // Start a transaction
        connection.setAutoCommit(false);

        // Iterate over the menuItems list and insert each dish into the menu table
        for (Meal meal : menuItems) {
            // Prepare the INSERT statement for adding the meal to the menu
            String insertMenuQuery = "INSERT INTO menu (dishid, date) VALUES (?, ?)";
            PreparedStatement menuStatement = connection.prepareStatement(insertMenuQuery);
            menuStatement.setInt(1, meal.getId());
            menuStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            menuStatement.executeUpdate();

            // Prepare the UPDATE statement for setting is_fixed_menu to 1
            String updateMealQuery = "UPDATE meal SET is_fixed_menu = 1 WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateMealQuery);
            updateStatement.setInt(1, meal.getId());
            updateStatement.executeUpdate();
        }

        // Commit the transaction
        connection.commit();

        System.out.println("Data inserted into menu table successfully");
    } catch (SQLException e) {
        e.printStackTrace();
        // Rollback the transaction in case of an error
      
    }
}




private Meal fetchMealDetails(Connection connection, int dishId) throws SQLException {
    Meal meal = null;
    String selectQuery = "SELECT * FROM dish WHERE dishid = ?";
    try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
        statement.setInt(1, dishId);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("dishid");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                // Assuming "is_plat_du_jour" field is present in the database table
                boolean isPlatDuJour = resultSet.getInt("is_plat_du_jour") == 1; // Assuming 1 represents true, 0 represents false
                meal = new Meal(id, name, description, price, quantity, isPlatDuJour);
            }
        }
    }
    return meal;
}



    private void openMenu(Pane menu) {
        TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.5), menu);
        openTransition.setToX(0);
        menu.setVisible(true);
        openTransition.play();
    }

    private void closeMenu(Pane menu) {
        TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.5), menu);
        closeTransition.setToX(-menu.getWidth());
        closeTransition.play();
        closeTransition.setOnFinished(e -> menu.setVisible(false));
    }

    private void openMenu2(Pane menu) {
        TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.5), menu);
        openTransition.setToX(0);
        menu.setVisible(true);
        openTransition.play();
    }

    private void closeMenu2(Pane menu) {
        TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.5), menu);
        closeTransition.setToX(menu.getWidth());
        closeTransition.play();
        closeTransition.setOnFinished(e -> menu.setVisible(false));
    }
     
   @FXML
    private void launchMenu_Plat() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_plat.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);
        

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    
}
      
       @FXML
    private void launchMenu_Stats() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/MenuPrincipaleVide.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);
        

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    
}
      

    @FXML
    private void launchMenu_Commande() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Commande.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    }
    @FXML
    private void launchMenu_Compte() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Compte.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    }
    @FXML
    private void launchMenu_DetailCompte() throws SQLException {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_DetailCompte.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    }
    @FXML
    private void launchMenu_MenuQuotidien() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Quotidien.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    }
@FXML
private void launchMenu_MenuGestionMenuQuotidien() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Gestion_Menu.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}

    
     public void updateMenuPlatVisibility(Pane menu) {
    if (isMenu1Open || isMenu2Open) {
            menu.setDisable(true);
        } else {
            menu.setDisable(false);
        }
    }
   
        @FXML
private void handleDeconnection() {
    try {
        System.out.println("test");
        // Load the FXML file for the login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/login/hello-view.fxml"));
        Parent loginPage = loader.load();

        // Replace the content of the main Pane with the login page
        MainMenu.getChildren().setAll(loginPage);

        // Close any open side menus
        if (isMenu1Open) {
            closeMenu(SideMenu1);
            isMenu1Open = false;
        }
        if (isMenu2Open) {
            closeMenu2(SideMenu2);
            isMenu2Open = false;
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }}
}
     