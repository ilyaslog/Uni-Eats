
package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import best.unieats.sign_up.NewUser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuGestionCompteController {
    @FXML
    private TableView<User> tablecompte;

    @FXML
    private TableColumn<User, Integer> Id;

    @FXML
    private TableColumn<User, String> Nom;

    @FXML
    private TableColumn<User, String> Prenom;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> adress;

    @FXML
    private TableColumn<User, Double> tel;

    @FXML
    private TableColumn<User, String> username;
    @FXML
    private Pane MainMenu;
    @FXML
    private Pane SideMenu1;

    @FXML
    private Pane SideMenu2;

    @FXML
    private Pane SideMenu3;

 

    @FXML
    private Button btn3Bars;

    @FXML
    private Button btnProfile;

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
    

    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE role IN (2, 3)";

    @FXML
    private void initialize() throws SQLException {
        tablecompte.refresh();
        Id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        Prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        adress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdress()));
        tel.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTel()).asObject());
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        populateTableWithData();

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

    public void populateTableWithData() throws SQLException {
        tablecompte.refresh();
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            ObservableList<User> users = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("userid");
                String nom = resultSet.getString("name");
                String prenom = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                double tel = resultSet.getDouble("numero_de_telephone");
                String username = resultSet.getString("username");

                users.add(new User(id, nom, prenom, email, address, tel, username));
            }

            tablecompte.setItems(users);
        }
    }
 @FXML
private void supprimerUtilisateur() {
    // Obtenez l'utilisateur sélectionné dans TableView
    User utilisateurSelectionne = tablecompte.getSelectionModel().getSelectedItem();

    // Vérifiez si un utilisateur a été sélectionné
    if (utilisateurSelectionne != null) {
        try {
            Connection connection = DatabaseConnector.connect();

            // Requête SQL pour supprimer l'utilisateur sélectionné de la base de données
            String sql = "DELETE FROM users WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, utilisateurSelectionne.getId());

            // Exécuter la requête SQL pour supprimer l'utilisateur
            int lignesSupprimees = statement.executeUpdate();

            if (lignesSupprimees > 0) {
                // Afficher un message de succès si la suppression a réussi
                Alert alerteSucces = new Alert(Alert.AlertType.INFORMATION);
                alerteSucces.setTitle("Succès");
                alerteSucces.setHeaderText(null);
                alerteSucces.setContentText("L'utilisateur a été supprimé avec succès.");
                alerteSucces.showAndWait();

                // Rafraîchir la TableView pour refléter les changements
                populateTableWithData();
            } else {
                // Afficher un message d'erreur si la suppression a échoué
                Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
                alerteErreur.setTitle("Erreur");
                alerteErreur.setHeaderText(null);
                alerteErreur.setContentText("Impossible de supprimer l'utilisateur. Veuillez réessayer.");
                alerteErreur.showAndWait();
            }

        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setHeaderText(null);
            alerteErreur.setContentText("Une erreur est survenue lors de la suppression de l'utilisateur.");
            alerteErreur.showAndWait();
        }
    } else {
        // Afficher un message indiquant qu'aucun utilisateur n'a été sélectionné
        Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
        alerteErreur.setTitle("Erreur");
        alerteErreur.setHeaderText(null);
        alerteErreur.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
        alerteErreur.showAndWait();
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
private Button addCompteButton;

@FXML
private void launchcreate_compte(ActionEvent event) throws SQLException {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Create_compte.fxml"));
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
private void Edit_compte(ActionEvent event) throws SQLException {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Edit_compte.fxml"));
        Parent newContent = loader.load();

        // Get the controller instance
        Edit_compte editCompteController = loader.getController();

        // Get the selected user from the table
        User selectedUser = tablecompte.getSelectionModel().getSelectedItem();

        // Set the selected user ID in the Edit_compte controller
        editCompteController.setSelectedUserId(selectedUser.getId());

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
