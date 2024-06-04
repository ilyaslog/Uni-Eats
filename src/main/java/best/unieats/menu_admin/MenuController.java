 package best.unieats.menu_admin;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import best.unieats.menu_admin.clients;
import best.unieats.Login.DatabaseConnector;
import best.unieats.Login.LoginController;
import java.sql.*;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class MenuController {
    @FXML
private Label clients;
@FXML
private Label revenujours;
@FXML
private Label revenutotale;
@FXML
private Label platvendu;
@FXML
    private LineChart<String, Number> RevenuGlobale;
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

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;

    
    @FXML
    private void initialize() throws SQLException{
        populateLabelsFromDatabase();
        populateRevenueChartData();
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

    private void populateRevenueChartData() {
        try {
            // Connect to the database
            Connection connection = DatabaseConnector.connect();

            // Define your SQL query to retrieve the necessary data
            String revenueByDayQuery = "SELECT date, SUM(price) AS totalRevenue " +
                                       "FROM basket " +
                                       "GROUP BY date " +
                                       "ORDER BY date";

            // Create PreparedStatement object for the query
            PreparedStatement revenueByDayStatement = connection.prepareStatement(revenueByDayQuery);

            // Execute the query
            ResultSet revenueByDayResultSet = revenueByDayStatement.executeQuery();

            // Create a new XYChart.Series to store the data
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            // Process the results and add data to the series
            while (revenueByDayResultSet.next()) {
                String date = revenueByDayResultSet.getString("date");
                double totalRevenue = revenueByDayResultSet.getDouble("totalRevenue");
                series.getData().add(new XYChart.Data<>(date, totalRevenue));
            }

            // Close the ResultSet and Statement
            revenueByDayResultSet.close();
            revenueByDayStatement.close();
            DatabaseConnector.closeConnection(connection);

            // Add the series to the LineChart
            RevenuGlobale.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

private void populateLabelsFromDatabase() {
    try {
        // Connect to the database
        Connection connection = DatabaseConnector.connect();

        // Define your SQL queries to retrieve the necessary data
        String clientsQuery = "SELECT COUNT(*) AS totalClients FROM users WHERE role = 1";
        String revenujoursQuery = "SELECT SUM(price) AS totalRevenue FROM basket WHERE date = CURDATE()";
        String revenuToujoursQuery = "SELECT SUM(price) AS totalToujoursRevenue FROM basket ";
        String DishesSoldQuery = "SELECT COUNT(*) AS totalBaskets FROM basket";

        // Create PreparedStatement objects for each query
        PreparedStatement clientsStatement = connection.prepareStatement(clientsQuery);
        PreparedStatement revenujoursStatement = connection.prepareStatement(revenujoursQuery);
        PreparedStatement revenuToujoursStatement = connection.prepareStatement(revenuToujoursQuery);
        PreparedStatement dishesSoldStatement = connection.prepareStatement(DishesSoldQuery);

        // Execute the queries
        ResultSet clientsResultSet = clientsStatement.executeQuery();
        ResultSet revenujoursResultSet = revenujoursStatement.executeQuery();
        ResultSet revenuToujoursResultSet = revenuToujoursStatement.executeQuery();
        ResultSet dishesSoldResultSet = dishesSoldStatement.executeQuery();

        // Process the results
        if (clientsResultSet.next()) {
            int totalClients = clientsResultSet.getInt("totalClients");
            clients.setText("" + totalClients);
        }

        if (revenujoursResultSet.next()) {
            double totalRevenue = revenujoursResultSet.getDouble("totalRevenue");
            revenujours.setText("" + totalRevenue);
        }

        if (revenuToujoursResultSet.next()) {
            double totalToujoursRevenue = revenuToujoursResultSet.getDouble("totalToujoursRevenue");
            revenutotale.setText("" + totalToujoursRevenue);
        }

        if (dishesSoldResultSet.next()) {
            int totalDishesSold = dishesSoldResultSet.getInt("totalBaskets");
            platvendu.setText("" + totalDishesSold);
        }

        // Close the ResultSet, Statement, and Connection
        clientsResultSet.close();
        revenujoursResultSet.close();
        revenuToujoursResultSet.close();
        dishesSoldResultSet.close();
        clientsStatement.close();
        revenujoursStatement.close();
        revenuToujoursStatement.close();
        dishesSoldStatement.close();
        DatabaseConnector.closeConnection(connection);

    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions appropriately
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
