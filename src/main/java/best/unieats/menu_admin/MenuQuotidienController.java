package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuQuotidienController  {
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
    private ImageView SS;
     @FXML
    private Pane SideMenu1;
  
    @FXML
    private Pane SideMenu2;
    
     @FXML
    private Pane SideMenu3;
     
   
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
    @FXML
    private Stage stage;
    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
   
    
    @FXML
    
    private void initialize() throws SQLException{
        
        showMenuItems6(ProductPane15);
        showMenuItems5(ProductPane14);
        showMenuItems4(ProductPane13);
       showMenuItems3(ProductPane12);
        showMenuItems2(ProductPane11);
       showMenuItems1(ProductPane1);
        showMenuItems0(ProductPane);
          // Get today's date
 
    LocalDate currentDate = LocalDate.now();
         
        // Set stage properties


    // Format the date as desired (e.g., "dd/MM/yyyy")
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = currentDate.format(formatter);

    // Set the formatted date as the text of the label
    Datedujour.setText(formattedDate);
        // Déplacer les panneaux en dehors du panneau principal
        SideMenu1.setTranslateX(-SideMenu1.getWidth());
        SideMenu2.setTranslateX(SideMenu2.getWidth());
        
        
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
               
            } else {
                openMenu2(SideMenu2);
                 
            }
            isMenu2Open = !isMenu2Open;
        });
        
 


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
            byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
            // Set the retrieved data to the FXML elements
            nom.setText(name);
            desc.setText(description);
            prix.setText(String.valueOf(price));

            Image image = new Image(new ByteArrayInputStream(imageData));

            // Set the image for the ImageView
            Photo.setImage(image);
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
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom1.setText(name);
                desc1.setText(description);
                prix1.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo1.setImage(image);
            } else {
                pane.setVisible(false);
            }
        }
    }
}

private void showMenuItems2(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 1")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom11.setText(name);
                desc11.setText(description);
                prix11.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo11.setImage(image);
            } else {
                pane.setVisible(false);
            }
        }
    }
}

// Similarly update the rest of your methods (showMenuItems3, showMenuItems4, showMenuItems5, showMenuItems6) with the appropriate SQL queries.
private void showMenuItems3(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 2")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom12.setText(name);
                desc12.setText(description);
                prix12.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo12.setImage(image);
            } else {
                pane.setVisible(false);
            }
        }
    }
}

private void showMenuItems4(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 3")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom13.setText(name);
                desc13.setText(description);
                prix13.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo13.setImage(image);
            } else {
                pane.setVisible(false);
            }
        }
    }
}

private void showMenuItems5(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 4")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom14.setText(name);
                desc14.setText(description);
                prix14.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo14.setImage(image);
            } else {
                pane.setVisible(false);
            }
        }
    }
}

private void showMenuItems6(Pane pane) throws SQLException {
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT name, description, price, image FROM menu Natural Join dish WHERE is_fixed_menu = 1 LIMIT 1 OFFSET 5")) {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                pane.setVisible(true);
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                Blob imageBlob = resultSet.getBlob("image");
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                nom15.setText(name);
                desc15.setText(description);
                prix15.setText(String.valueOf(price));
                Image image = new Image(new ByteArrayInputStream(imageData));
                Photo15.setImage(image);
            } else {
                pane.setVisible(false);
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
