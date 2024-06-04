package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuCommandeController {

    @FXML
    private Pane SideMenu1;

    @FXML
    private TableView<Basket> tablecompte;

    @FXML
    private TableColumn<Basket, Integer> IdBasket;

    @FXML
    private TableColumn<Basket, Integer> IdClient;

    @FXML
    private TableColumn<Basket, String> NomClient;

    @FXML
    private TableColumn<Basket, String> PrenomClient;

    @FXML
    private TableColumn<Basket, String> date;

    @FXML
    private TableColumn<Basket, Integer> status;

    @FXML
    private Pane SideMenu2;

    @FXML
    private Pane SideMenu3;

  

    @FXML
    private Button btn3Bars;

    @FXML
    private Button btnProfile;

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

    @FXML
    private Button btnDetails;

    @FXML
    private Button btnDeconnecter;

    @FXML
    private Button Confirmerresep;

    @FXML
    private Button deletecommande;

    @FXML
    private Pane MainMenu;

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
   

    private static final String SELECT_BASKET_QUERY = 
    "SELECT b.basketid, b.userid, u.name, u.lastname, b.date, b.status\n" +
"FROM basket b\n" +
"JOIN users u ON b.userid = u.userid\n" +
"WHERE status >= 1;";


  @FXML
private void initialize() {
    try {
        // Initialize the table
        IdBasket.setCellValueFactory(new PropertyValueFactory<>("basketId"));
        IdClient.setCellValueFactory(new PropertyValueFactory<>("userId"));
        NomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        PrenomClient.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("statusLabel")); // Bind to statusLabel property

        // Populate the table with data
        populateTableWithData();

        // Initialize the side menus
        initializeSideMenus();

        // Set button actions
        btn3Bars.setOnAction(event -> toggleMenu(SideMenu1, isMenu1Open));
        btnProfile.setOnAction(event -> toggleMenu(SideMenu2, isMenu2Open));
        
        
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

        
        
        
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exception appropriately
    }
}

    @FXML
private void confirmStatus() {
    Basket selectedBasket = tablecompte.getSelectionModel().getSelectedItem();
    if (selectedBasket != null && selectedBasket.getStatus() == 3) {
        // Update the status in the database
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement("UPDATE basket SET status = 4 WHERE basketid = ?")) {
            statement.setInt(1, selectedBasket.getBasketId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                // Update the status locally
                selectedBasket.setStatus(4);
                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Confirmation", "Status Updated Successfully", "The status has been updated to Livre.");
            } else {
                // Show error message if no rows were affected
                showAlert(Alert.AlertType.ERROR, "Error", "Status Update Failed", "Failed to update the status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error Updating Status", "An error occurred while updating the status.");
        }
    } else {
        // Show error message if no command is selected or status is not En livraison
        showAlert(Alert.AlertType.ERROR, "Error", "Invalid Selection", "Please select a command with status 'En livraison' to confirm.");
    }
}

@FXML
private void deleteCommande() {
    Basket selectedBasket = tablecompte.getSelectionModel().getSelectedItem();
    if (selectedBasket != null) {
        // Show confirmation dialog
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Delete Commande");
        confirmation.setContentText("Are you sure you want to delete this command?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the command from the database
                try (Connection connection = DatabaseConnector.connect();
                     PreparedStatement statement = connection.prepareStatement("DELETE FROM basket WHERE basketid = ?")) {
                    statement.setInt(1, selectedBasket.getBasketId());
                    int affectedRows = statement.executeUpdate();
                    if (affectedRows > 0) {
                        // Remove the command from the TableView
                        tablecompte.getItems().remove(selectedBasket);
                        // Show success message
                        showAlert(Alert.AlertType.INFORMATION, "Confirmation", "Command Deleted Successfully", "The command has been deleted.");
                    } else {
                        // Show error message if no rows were affected
                        showAlert(Alert.AlertType.ERROR, "Error", "Delete Failed", "Failed to delete the command.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle database error
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Error Deleting Command", "An error occurred while deleting the command.");
                }
            }
        });
    } else {
        // Show error message if no command is selected
        showAlert(Alert.AlertType.ERROR, "Error", "No Command Selected", "Please select a command to delete.");
    }
}

private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}
    private void toggleMenu(Pane menu, boolean isOpen) {
        if (isOpen) {
            closeMenu(menu);
        } else {
            openMenu(menu);
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
    private void initializeSideMenus() {
        SideMenu1.setTranslateX(-SideMenu1.getWidth());
        SideMenu2.setTranslateX(SideMenu2.getWidth());
        SideMenu3.setTranslateX(SideMenu3.getWidth());
    }
    
private void populateTableWithData() throws SQLException {
    try (Connection connection = DatabaseConnector.connect();
         PreparedStatement statement = connection.prepareStatement(SELECT_BASKET_QUERY)) {
        ResultSet resultSet = statement.executeQuery();
        // Clear existing items from the table
        tablecompte.getItems().clear();
        // Populate the table with the result set
        while (resultSet.next()) {
            int basketId = resultSet.getInt("basketid");
            int userId = resultSet.getInt("userid");
            String nomClient = resultSet.getString("name");
            String prenomClient = resultSet.getString("lastname");
            String date = resultSet.getString("date");
            int status = resultSet.getInt("status");
            String statusLabel;
            switch (status) {
                case 1:
                    statusLabel = "En attente";
                    break;
                case 2:
                    statusLabel = "En cours";
                    break;
                case 3:
                    statusLabel = "En train de se faire livre";
                    break;
                case 4:
                    statusLabel = "Livree";
                    break;
                default:
                    statusLabel = "Unknown";
            }
            Basket basket = new Basket(basketId, userId, nomClient, prenomClient, date, status, statusLabel);
            // Add basket to the table
            tablecompte.getItems().add(basket);
        }
       
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
        }
    }
}
