package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
public class MenuPlatController {

    @FXML
    private Pane SideMenu1;
  
    @FXML
    private Pane SideMenu2;
    
     @FXML
    private Pane SideMenu3;
    
    @FXML
    private Pane MenuPlat;
    
    
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
    //--TableMeals--
    private boolean isPlatDuJour; 
@FXML
    private TableView<Meal> TableMeals;

    @FXML
    private TableColumn<Meal, Integer> Id;
    
    @FXML
    private TableColumn<Meal, String> NomPlat;
    
    @FXML
    private TableColumn<Meal, String> Description;
    
    @FXML
    private TableColumn<Meal, Double> Prix;
    
    @FXML
    private TableColumn<Meal, Integer> Quantiter;

    private static final String SELECT_DISH_QUERY = "SELECT * FROM dish WHERE is_plat_du_jour = ?";


    
    
    @FXML
    private void initialize() throws SQLException {
        // Déplacer les panneaux en dehors du panneau principal
        SideMenu1.setTranslateX(-SideMenu1.getWidth());
        SideMenu2.setTranslateX(SideMenu2.getWidth());
        SideMenu3.setTranslateX(SideMenu3.getWidth());
        MenuPlat.setDisable(false);
        
        btn3Bars.setOnAction(event -> {
            if (isMenu1Open) {
                closeMenu(SideMenu1);
            } else {
                openMenu(SideMenu1);
            }
            isMenu1Open = !isMenu1Open;
            updateMenuPlatVisibility(MenuPlat);
        });

        btnProfile.setOnAction(event -> {
            if (isMenu2Open) {
                closeMenu2(SideMenu2);
              
            } else {
                openMenu2(SideMenu2);
               
            }
            isMenu2Open = !isMenu2Open;
            updateMenuPlatVisibility(MenuPlat);
        });
    

        
        //--TableMeals--
         // Populate TableView with random data
        // Initialize columns
        Id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        NomPlat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomPlat()));
        Description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        Prix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        Quantiter.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiter()).asObject());

        populateTableWithDishData();
        
    }
// Method to generate meal data
private void populateTableWithDishData() throws SQLException {
    TableMeals.refresh();
    try (Connection connection = DatabaseConnector.connect();
         PreparedStatement statement = connection.prepareStatement(SELECT_DISH_QUERY)) {
        
        // Set the parameter for the is_plat_du_jour column
        statement.setInt(1, isPlatDuJour ? 1 : 0);
        
        try (ResultSet resultSet = statement.executeQuery()) {
            ObservableList<Meal> meals = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("dishid");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                statement.setInt(1, isPlatDuJour ? 1 : 0);

                // Add the retrieved dish to the meals list
                meals.add(new Meal(id, name, description, price, quantity, isPlatDuJour));
            }

            // Set the items in the table
            TableMeals.setItems(meals);
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

         @FXML
private void ajouterPlat() {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Add_meal.fxml"));
        Parent newContent = loader.load();

        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(newContent);

        // Create a new stage for the new window
        Stage newStage = new Stage();
        newStage.setScene(scene);

        // Show the new window
        newStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}



          
    

@FXML
private void supprimerPlat() {
    // Get the selected item from the TableView
    Meal selectedDish = TableMeals.getSelectionModel().getSelectedItem();

     // Vérifiez si un utilisateur a été sélectionné
    if (selectedDish != null) {
        try {
            Connection connection = DatabaseConnector.connect();

            // Requête SQL pour supprimer l'utilisateur sélectionné de la base de données
            String sql = "DELETE FROM dish WHERE dishid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedDish.getId());

            // Exécuter la requête SQL pour supprimer l'utilisateur
            int lignesSupprimees = statement.executeUpdate();

            if (lignesSupprimees > 0) {
                // Afficher un message de succès si la suppression a réussi
                Alert alerteSucces = new Alert(Alert.AlertType.INFORMATION);
                alerteSucces.setTitle("Succès");
                alerteSucces.setHeaderText(null);
                alerteSucces.setContentText("Le plats a été supprimé avec succès.");
                alerteSucces.showAndWait();

                // Rafraîchir la TableView pour refléter les changements
                populateTableWithDishData();
            } else {
                // Afficher un message d'erreur si la suppression a échoué
                Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
                alerteErreur.setTitle("Erreur");
                alerteErreur.setHeaderText(null);
                alerteErreur.setContentText("Impossible de supprimer le plats. Veuillez réessayer.");
                alerteErreur.showAndWait();
            }

        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setHeaderText(null);
            alerteErreur.setContentText("Une erreur est survenue lors de la suppression du plats.");
            alerteErreur.showAndWait();
        }
    } else {
        // Afficher un message indiquant qu'aucun utilisateur n'a été sélectionné
        Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
        alerteErreur.setTitle("Erreur");
        alerteErreur.setHeaderText(null);
        alerteErreur.setContentText("Veuillez sélectionner un plats à supprimer.");
        alerteErreur.showAndWait();
    }
}



           

@FXML
private void Edit_meal(ActionEvent event) throws SQLException {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Modify_meal.fxml"));
        Parent newContent = loader.load();

        // Get the controller instance
        Edit_meal editMealController = loader.getController();

        // Get the selected meal from the table
        Meal selectedMeal = TableMeals.getSelectionModel().getSelectedItem();

        // Set the selected meal in the Edit_meal controller
        editMealController.setMeal(selectedMeal);

        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(newContent);

        // Create a new stage for the new window
        Stage newStage = new Stage();
        newStage.setScene(scene);

        // Show the new window
        newStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}

   private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}

}
