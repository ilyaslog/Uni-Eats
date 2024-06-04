package best.unieats.menu_user;

import best.unieats.Login.DatabaseConnector;
import best.unieats.Login.DishDetails;
import best.unieats.Login.LoginController;
import static best.unieats.Login.LoginController.username;
import best.unieats.menu_admin.Basket;
import best.unieats.menu_cui.BasketItem;
import best.unieats.menu_cui.DishItem;
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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu_QuotidienController  {
     @FXML
    private Button plus1;

    @FXML
    private Button minus1;
       @FXML
    private Button plus11;

    @FXML
    private Button minus11;
        @FXML
    private Button plus12;

    @FXML
    private Button minus12;
           @FXML
    private Button plus13;

    @FXML
    private Button minus13;
             @FXML
    private Button plus14;

    @FXML
    private Button minus14;
              @FXML
    private Button plus15;

    @FXML
    private Button minus15;
          @FXML
    private Pane ProductPane15;

    @FXML
    private ImageView Photo15;

    @FXML
    private Label nom15;

    @FXML
    private Label desc15;

    @FXML
    private Label prix15;

    @FXML
    private ImageView moin15;

    @FXML
    private ImageView SS15;

    @FXML
    private TextField quantiter15;

    @FXML
    private Button ajouter15;
          @FXML
    private Pane ProductPane14;

    @FXML
    private ImageView Photo14;

    @FXML
    private Label nom14;

    @FXML
    private Label desc14;

    @FXML
    private Label prix14;

    @FXML
    private ImageView moin14;

    @FXML
    private ImageView SS14;

    @FXML
    private TextField quantiter14;

    @FXML
    private Button ajouter14;
         @FXML
    private Pane ProductPane13;

    @FXML
    private ImageView Photo13;

    @FXML
    private Label nom13;

    @FXML
    private Label desc13;

    @FXML
    private Label prix13;

    @FXML
    private ImageView moin13;

    @FXML
    private ImageView SS13;

    @FXML
    private TextField quantiter13;

    @FXML
    private Button ajouter13;
  
       @FXML
    private Pane ProductPane12;

    @FXML
    private ImageView Photo12;

    @FXML
    private Label nom12;

    @FXML
    private Label desc12;
  @FXML
    private Button ajouter;
    @FXML
    private Label prix12;

    @FXML
    private ImageView moin12;

    @FXML
    private ImageView SS12;

    @FXML
    private TextField quantiter12;

    @FXML
    private Button ajouter12;
       @FXML
    private Pane ProductPane11;

    @FXML
    private ImageView Photo11;

    @FXML
    private Label nom11;

    @FXML
    private Label desc11;

    @FXML
    private Label prix11;

    @FXML
    private ImageView moin11;

    @FXML
    private ImageView SS11;

    @FXML
    private TextField quantiter11;

    @FXML
    private Button ajouter11;
        @FXML
    private Pane ProductPane1;
    
    @FXML
    private ImageView Photo1;

    @FXML
    private Label nom1;

    @FXML
    private Label desc1;

    @FXML
    private Label prix1;

    @FXML
    private Button ajouter1;
    
    @FXML
    private ImageView moin1;

    @FXML
    private ImageView SS1;

    @FXML
    private TextField quantiter1;
    @FXML
    private Label Datedujour;
    
    @FXML
    private Pane ProductPane;

    @FXML
    private ImageView Photo;

    @FXML
    private Label nom;

    @FXML
    private Label prix;

    @FXML
    private Label desc;

    @FXML
    private TextField quantiter;

    @FXML
    private ImageView moin;
      @FXML
    private Button plus0;

    @FXML
    private Button minus0;
    @FXML
    private ImageView SS;
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
    
//Les boutons du side menu 1 actioner par bt3Bars
    @FXML
    private Button btnMenuQuotidien;
    
    @FXML
    private Button btnGestion_Menu;
    
    @FXML
    private Button btnPlats;
    
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

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
    private boolean isMenu3Open = false;

    @FXML
    private TableView<DishDetails> panier;
    
    @FXML
    private TableColumn<DishDetails, String> nom2;
    
    @FXML
    private TableColumn<DishDetails, Double> prix2;
    
    @FXML
    private TableColumn<DishDetails, Integer> quantiter2;
    
    @FXML
    private Label prixTotal1;
    
    DatabaseConnector connector = new DatabaseConnector();
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
private void initialize() throws SQLException{
     
    ajouter15.setOnAction(event -> ajouterAuPanier(nom15, quantiter15));
    ajouter14.setOnAction(event -> ajouterAuPanier(nom14, quantiter14));
    ajouter13.setOnAction(event -> ajouterAuPanier(nom13, quantiter13));
    ajouter12.setOnAction(event -> ajouterAuPanier(nom12, quantiter12));
    ajouter11.setOnAction(event -> ajouterAuPanier(nom11, quantiter11));
    ajouter1.setOnAction(event -> ajouterAuPanier(nom1, quantiter1));
    ajouter.setOnAction(event -> ajouterAuPanier(nom, quantiter));
    
    displayUserBalance();
    showMenuItems6(ProductPane15);
    showMenuItems5(ProductPane14);
    showMenuItems4(ProductPane13);
    showMenuItems3(ProductPane12);
    showMenuItems2(ProductPane11);
    showMenuItems1(ProductPane1);
    showMenuItems0(ProductPane);
    
    List<DishDetails> dishes = connector.getDishesInBasketByUsername(username);
    populateTable();
    // Get today's date
    LocalDate currentDate = LocalDate.now();
    // Format the date as desired (e.g., "dd/MM/yyyy")
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = currentDate.format(formatter);
    // Set the formatted date as the text of the label
    Datedujour.setText(formattedDate);
    
    plus0.setOnAction(event -> increaseQuantity(quantiter));
    minus0.setOnAction(event -> decreaseQuantity(quantiter));
    plus1.setOnAction(event -> increaseQuantity(quantiter1));
    minus1.setOnAction(event -> decreaseQuantity(quantiter1));
    plus11.setOnAction(event -> increaseQuantity(quantiter11));
    minus11.setOnAction(event -> decreaseQuantity(quantiter11));
    plus12.setOnAction(event -> increaseQuantity(quantiter12));
    minus12.setOnAction(event -> decreaseQuantity(quantiter12));
    plus13.setOnAction(event -> increaseQuantity(quantiter13));
    minus13.setOnAction(event -> decreaseQuantity(quantiter13));
    plus14.setOnAction(event -> increaseQuantity(quantiter14));
    minus14.setOnAction(event -> decreaseQuantity(quantiter14));
    plus15.setOnAction(event -> increaseQuantity(quantiter15));
    minus15.setOnAction(event -> decreaseQuantity(quantiter15));
    
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
            closeMenu(SideMenu3);
            btnProfile.setDisable(false); 
        } else {
            openMenu(SideMenu3);
            btnProfile.setDisable(true); 
        }
        isMenu3Open = !isMenu3Open;
    });
}


private void populateTable() {
    List<DishDetails> basket = connector.getDishesInBasket(username);
    panier.getItems().clear();

    double totalSum = 0.0;

    for (DishDetails dish : basket) {
        panier.getItems().add(dish);
        totalSum += dish.getTotalPrice();
    }

    nom2.setCellValueFactory(new PropertyValueFactory<>("name"));
    quantiter2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    prix2.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    
    System.out.println("Total sum of prices: " + totalSum);
    
    prixTotal1.setText(String.valueOf(totalSum) + " DH");
}

@FXML
private void ajouterHandler(Label nomLabel, TextField quantiteField) {
    ajouterAuPanier(nomLabel, quantiteField);
    populateTable();
}

@FXML
private void ajouter() {
    ajouterHandler(nom, quantiter);
}

@FXML
private void ajouter1() {
    ajouterHandler(nom1, quantiter1);
}

@FXML
private void ajouter11() {
    ajouterHandler(nom11, quantiter11);
}

@FXML
private void ajouter12() {
    ajouterHandler(nom12, quantiter12);
}

@FXML
private void ajouter13() {
    ajouterHandler(nom13, quantiter13);
}

@FXML
private void ajouter14() {
    ajouterHandler(nom14, quantiter14);
}

@FXML
private void ajouter15() {
    ajouterHandler(nom15, quantiter15);
}

private void ajouterAuPanier(Label nomPlat, TextField quantiteField) {
    String nom = nomPlat.getText();
    int quantite;

    try {
        quantite = Integer.parseInt(quantiteField.getText());
    } catch (NumberFormatException e) {
        quantite = 0;
    }

    if (quantite <= 0) {
        return;
    }

    try (Connection conn = DatabaseConnector.connect()) {
        String userIdQuery = "SELECT userid FROM users WHERE username = ?";
        PreparedStatement userIdStmt = conn.prepareStatement(userIdQuery);
        userIdStmt.setString(1, LoginController.username);
        ResultSet userIdRs = userIdStmt.executeQuery();
        
        if (userIdRs.next()) {
            int userId = userIdRs.getInt("userid");

            String dishQuery = "SELECT dishid, price FROM dish WHERE name = ?";
            PreparedStatement dishStmt = conn.prepareStatement(dishQuery);
            dishStmt.setString(1, nom);
            ResultSet dishRs = dishStmt.executeQuery();

            if (dishRs.next()) {
                int dishId = dishRs.getInt("dishid");
                double price = dishRs.getDouble("price") * quantite;

                int basketId = 0;
                String basketIdQuery = "SELECT basketid FROM basket WHERE userid = ? AND status = 0";
                PreparedStatement basketIdStmt = conn.prepareStatement(basketIdQuery);
                basketIdStmt.setInt(1, userId);
                ResultSet basketIdRs = basketIdStmt.executeQuery();
                
                if (basketIdRs.next()) {
                    basketId = basketIdRs.getInt("basketid");
                } else {
                    String insertBasketQuery = "INSERT INTO basket (userid, price, status, date, num_comannde) VALUES (?, 0, 0, CURDATE(), 1)";
                    PreparedStatement insertBasketStmt = conn.prepareStatement(insertBasketQuery, Statement.RETURN_GENERATED_KEYS);
                    insertBasketStmt.setInt(1, userId);
                    insertBasketStmt.executeUpdate();

                    ResultSet generatedKeys = insertBasketStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        basketId = generatedKeys.getInt(1);
                    }
                }

                String checkDishQuery = "SELECT quantity FROM dishinbasket WHERE basketid = ? AND dishid = ?";
                PreparedStatement checkDishStmt = conn.prepareStatement(checkDishQuery);
                checkDishStmt.setInt(1, basketId);
                checkDishStmt.setInt(2, dishId);
                ResultSet checkDishRs = checkDishStmt.executeQuery();

                if (checkDishRs.next()) {
                    int existingQuantity = checkDishRs.getInt("quantity");
                    int newQuantity = existingQuantity + quantite;
                    String updateQuantityQuery = "UPDATE dishinbasket SET quantity = ? WHERE basketid = ? AND dishid = ?";
                    PreparedStatement updateQuantityStmt = conn.prepareStatement(updateQuantityQuery);
                    updateQuantityStmt.setInt(1, newQuantity);
                    updateQuantityStmt.setInt(2, basketId);
                    updateQuantityStmt.setInt(3, dishId);
                    updateQuantityStmt.executeUpdate();
                } else {
                    String insertDishInBasketQuery = "INSERT INTO dishinbasket (basketid, dishid, quantity) VALUES (?, ?, ?)";
                    PreparedStatement insertDishInBasketStmt = conn.prepareStatement(insertDishInBasketQuery);
                    insertDishInBasketStmt.setInt(1, basketId);
                    insertDishInBasketStmt.setInt(2, dishId);
                    insertDishInBasketStmt.setInt(3, quantite);
                    insertDishInBasketStmt.executeUpdate();
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void increaseQuantity(TextField textField) {
        try {
            int currentQuantity = Integer.parseInt(textField.getText());
            textField.setText(String.valueOf(currentQuantity + 1));
        } catch (NumberFormatException e) {
            textField.setText("1"); // If the text field is empty or invalid, set it to 1
        }
    }

    private void decreaseQuantity(TextField textField) {
        try {
            int currentQuantity = Integer.parseInt(textField.getText());
            if (currentQuantity > 1) {
                textField.setText(String.valueOf(currentQuantity - 1));
            } else {
                textField.setText("0"); // Ensure quantity doesn't go below 0
            }
        } catch (NumberFormatException e) {
            textField.setText("0"); // If the text field is empty or invalid, set it to 0
        }
    }

private void showMenuItems0(Pane pane) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    try {
        // Get the database connection
        conn = DatabaseConnector.connect();

        // Prepare the statement with the query
        String query = "SELECT * FROM menu Natural Join dish WHERE is_plat_du_jour = 1 AND date = ?";
        stmt = conn.prepareStatement(query);

        // Set the current date as parameter in the query
        stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

        // Execute the query and get the result
        resultSet = stmt.executeQuery();

        // Iterate through the result set to process each row
        while (resultSet.next()) {
            // Retrieve dish details from the result set
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            Blob imageBlob = resultSet.getBlob("image");
            if (imageBlob != null) {
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                // Set the retrieved data to the FXML elements
                nom.setText(name);
                desc.setText(description);
                prix.setText(String.valueOf(price));

                Image image = new Image(new ByteArrayInputStream(imageData));

                // Set the image for the ImageView
                Photo.setImage(image);
            } else {
                // Handle the case where imageBlob is null
                System.out.println("Image data is null for this record.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close the ResultSet and Connection in the finally block
        // to ensure they're closed regardless of exceptions
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            // PreparedStatement is closed automatically due to try-with-resources
            DatabaseConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void showMenuItems1(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 0")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom1.setText(name);
                    desc1.setText(description);
                    prix1.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo1.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
}

private void showMenuItems2(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 1")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom11.setText(name);
                    desc11.setText(description);
                    prix11.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo11.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
}


private void showMenuItems3(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 2")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom12.setText(name);
                    desc12.setText(description);
                    prix12.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo12.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
}
private void showMenuItems4(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 3")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom13.setText(name);
                    desc13.setText(description);
                    prix13.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo13.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
}

private void showMenuItems5(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 4")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom14.setText(name);
                    desc14.setText(description);
                    prix14.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo14.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
}

private void showMenuItems6(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 5")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    nom15.setText(name);
                    desc15.setText(description);
                    prix15.setText(String.valueOf(price));
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    Photo15.setImage(image);
                } else {
                    // Handle the case where imageBlob is null
                    System.out.println("Image data is null for this record.");
                }
            }
        }
    }
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
private Label Sold;

private void displayUserBalance() throws SQLException {
    if (LoginController.username == null) {
        System.out.println("User not specified");
        return;
    }
    
    Connection connection = DatabaseConnector.connect();
    String query = "SELECT sold FROM users WHERE username=?";
    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, LoginController.username);
    ResultSet rs = stmt.executeQuery();
    
    if (rs.next()) {
        int balance = rs.getInt("sold");
        Sold.setText(String.valueOf(balance));
    } else {
        System.out.println("Error: User balance not found.");
    }
}





    @FXML
    private void launchMenu_Commande() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_Commande.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_DetailCompte.fxml"));
        Parent newContent = loader.load();

        // Replace the content of the main Pane with the new content
        MainMenu.getChildren().setAll(newContent);

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    }
          @FXML
    private void lanchMenuQuotidien() {
        try {
            // Load the FXML file for the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_Quotidien.fxml"));
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
            // Handle the exception appropriately, e.g., show an error message to the user
            e.printStackTrace();
        }
    }
         @FXML
    private void lanchCommande() {
        try {
            // Load the FXML file for the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_Commande.fxml"));
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
            // Handle the exception appropriately, e.g., show an error message to the user
            e.printStackTrace();
        }
    }
     @FXML
    private void lanchDetailCompte() {
        try {
            // Load the FXML file for the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_DetailCompte.fxml"));
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
            // Handle the exception appropriately, e.g., show an error message to the user
            e.printStackTrace();
        }
    }
    @FXML
    private void launchMenu_MenuQuotidien() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_Quotidien.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Gestion_Menu.fxml"));
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
 





//onclicks of basket
@FXML
private void confirmerPanier(ActionEvent event) {
    System.out.println("confirmerPanier is activated");
    connector.updateBasketStatus(username);
    // Commande refresh
    int status = connector.getBasketStatus(username);
    // Basket refresh
    populateTable();  // No need to pass the list, as populateTable fetches the data itself
    System.out.println("Basket refreshed");
    List<DishDetails> dishesInBasket = connector.getDishesInBasket(username);
}

@FXML
private void clear(ActionEvent event) {
    System.out.println("clear is activated");
    // Get the selected item
    DishDetails selectedDish = panier.getSelectionModel().getSelectedItem();

    if (selectedDish != null) {
        // Call a method to delete the item from the database
        DatabaseConnector connector = new DatabaseConnector();
        connector.deleteDishFromBasket(username, selectedDish.getName());

        // Refresh the table
        populateTable();  // No need to pass the list, as populateTable fetches the data itself
    } else {
        // No item is selected, handle appropriately (e.g., show an alert)
        System.out.println("No item selected for deletion.");
    }}

}
