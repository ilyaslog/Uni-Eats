package best.unieats.menu_user;

import best.unieats.Login.DatabaseConnector;
import best.unieats.Login.DishDetails;
import best.unieats.Login.LoginController;
import static best.unieats.Login.LoginController.username;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CommandeController implements Initializable {
  
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

    @FXML
    private Pane MainMenu;

    @FXML
    private Button btnPlats;

    private boolean isMenu1Open = false;
    private boolean isMenu2Open = false;
    private boolean isMenu3Open = false;
    @FXML
    private Label Datedujour1;
    
    @FXML
    private ProgressBar baredeproretiondetat;
    
    @FXML
    private TableView Commandeencours;
    
    @FXML
    private TableColumn nom1;
    
    @FXML
    private TableColumn quantiter1;
    
    @FXML
    private TableColumn prix1;
    
    @FXML
    private Pane nocommande;
      @FXML
    private Label EtatCommande;

    @FXML
    private Label NumeroCommande;
    
    @FXML
    private TableView<DishDetails> panier;
    @FXML
    private TableColumn<DishDetails, String> nom;
    @FXML
    private TableColumn<DishDetails, Integer> quantiter;
    @FXML
    private TableColumn<DishDetails, Double> prix;
      @FXML
    private Label prixTotal;
      
    DatabaseConnector connector = new DatabaseConnector();
        @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources){
      
         DatabaseConnector connector = new DatabaseConnector();
        System.out.println("current user is :" + username);
        int status = connector.getBasketStatus(username);
        //show the commande table
        showcommande(status);
          System.out.println("status is :" + status);
        //retrieve the basket data
        List<DishDetails> basket = connector.getDishesInBasket(username);
        //populate the table
        populateTable();
        //show status
        showStatus(status);
        //get num_commande
        int numcommande = connector.getNumCommande(username);
        try {
            displayUserBalance();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //show numcommande
        shownumcommande(numcommande);
        
       //basket side
       List<DishDetails> dishes = connector.getDishesInBasketByUsername(username);
       System.out.println("basket side");
       populateTable(dishes);
       

        
        // DÃ©placer les panneaux en dehors du panneau principal
        SideMenu1.setTranslateX(-SideMenu1.getWidth());
        SideMenu2.setTranslateX(SideMenu2.getWidth());
        SideMenu3.setTranslateX(SideMenu3.getWidth());
        
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
        System.out.println("here");

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
private Label Sold;

private void displayUserBalance() throws SQLException {
    if (LoginController.username == null) {
       
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


    
      private void showStatus(int status){
        switch (status) {
                case 1:
                    EtatCommande.setText("En attente");
                    baredeproretiondetat.setProgress(0.25);
                    
                    break;
                case 2:
                    EtatCommande.setText("En cours");
                    baredeproretiondetat.setProgress(0.5);
                    break;
                case 3:
                    EtatCommande.setText("En train de se faire livre");
                    baredeproretiondetat.setProgress(0.75);
                    break;
                case 4:
                    EtatCommande.setText("Livree");
                    baredeproretiondetat.setProgress(1);
                    break;
        }
    }
      
      private void shownumcommande(int numCommande){
          if(numCommande != -1){
          NumeroCommande.setText("numcommande is :"+String.valueOf(numCommande));
          }
          else{
              NumeroCommande.setText("no commande");
          }
      }
    
    
    
    private void populateTable() {
        DatabaseConnector connector = new DatabaseConnector();
        List<DishDetails> basket = connector.getDishesInBasket(username);

        // Convert the list to an ObservableList
        ObservableList<DishDetails> data = FXCollections.observableArrayList(basket);

        // Set the data to the table view
        Commandeencours.setItems(data);

        // Bind table columns with data properties
        nom1.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantiter1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prix1.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }
    
    private void showcommande(int status){
        boolean blnstatus;
        if (status <= 0 ) {
        blnstatus=true;
        nocommande.setVisible(blnstatus);
         System.out.println("User not specified");
        }else if( status == 4){
           blnstatus=true;
        nocommande.setVisible(blnstatus); 
         System.out.println("User not specified");
        }
        else {
        blnstatus=false;
        nocommande.setVisible(blnstatus);
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
     private void getUser() throws SQLException {
    if (LoginController.username == null) {
        System.out.println("User not specified");
        return;
    }}
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
    private void handleDeconnection() {
        try {
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
            // Handle the exception appropriately, e.g., show an error message to the user
            e.printStackTrace();
        }
    }
    
    private int populateTable(List<DishDetails> basket) {
    // Clear any existing items in the table
    panier.getItems().clear();

    // Variable to accumulate the total price
    double totalSum = 0.0;

    // Add each item from the list to the table and accumulate the total price
    for (DishDetails dish : basket) {
        panier.getItems().add(dish);
        totalSum += dish.getTotalPrice();
        System.out.println(" TOTALSUM: "+totalSum);
    }

    // Bind table columns with data properties
    nom.setCellValueFactory(new PropertyValueFactory<>("name"));
    quantiter.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    prix.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

    // Print the sum of the total prices
    System.out.println("Total sum of prices: " + totalSum);
    
    prixTotal.setText(String.valueOf(totalSum)+" DH");
    return (int) totalSum;
}
   //on clicks 
    @FXML
    private void confirmerPanier(ActionEvent event) throws SQLException {
        System.out.println("confirmerPanier is activated");
        connector.updateBasketStatus(username);
        //commande refresh
        int status = connector.getBasketStatus(username);
         System.out.println("status is :"+status);
        //show the commande table
        showcommande(status);
        //populate the table
        int totalSum = 0;
        List<DishDetails> basket = connector.getDishesInBasket(username);
        totalSum = populateTable(basket); 
       showStatus(status);
        //basket refresh
        List<DishDetails> dishes = connector.getDishesInBasketByUsername(username);
       System.out.println("basket side");
       populateTable(dishes);
        
        // Subtract the total price of the basket from the user's balance
    double newBalance = getUpdatedBalance(totalSum);
    
    // Update the balance in the database
    updateBalanceInDatabase(username, newBalance);
    
    System.out.println("Basket refreshed");
    
    // Update the balance displayed in the UI
    displayUserBalance();
    }
    
    private double getUpdatedBalance(double totalSum) {
    // Subtract the total price of the basket from the user's balance
    double currentBalance = Double.parseDouble(Sold.getText());
    double newBalance = currentBalance - totalSum;
     System.out.println("currentbalance :"+currentBalance+" TOTALSUM: "+totalSum);
    return newBalance;
}
    private void updateBalanceInDatabase(String username, double newBalance) {
    try {
        Connection connection = DatabaseConnector.connect();
        String query = "UPDATE users SET sold = ? WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDouble(1, newBalance);
        stmt.setString(2, username);
        stmt.executeUpdate();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the SQLException appropriately
    }
}
    
    @FXML
    private void confirmerres(ActionEvent event) {
        
        System.out.println("confirmerres is activated");
        connector.updateBasketStatus2(username);
        populateTable();
         int status = connector.getBasketStatus(username);
        //show the commande table
        showcommande(status);
       
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
        List<DishDetails> basket = connector.getDishesInBasketByUsername(username);
        populateTable(basket);
    } else {
        // No item is selected, handle appropriately (e.g., show an alert)
        System.out.println("No item selected for deletion.");
    }
      
    }
    
    
}
