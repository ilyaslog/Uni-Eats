package best.unieats.menu_cui;

import best.unieats.Login.DatabaseConnector;
import best.unieats.Login.DishesEnCours;
import static best.unieats.Login.LoginController.username;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MenuComandController {

    @FXML
    private Pane MainMenu;

     @FXML
    private TableView<DishesEnCours> panier;

    @FXML
    private TableColumn<DishesEnCours, String> nom;

    @FXML
    private TableColumn<DishesEnCours, Integer> quantiter;

    private DatabaseConnector dbConnector;

    @FXML
    private Pane rien;
    
    @FXML
    private Label EnAttent;
    
      @FXML
    private Label Numerodecommande;

public void initialize() {
     nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantiter.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        populateTable();
        rien.setVisible(false);
}

public void populateTable() {
    this.dbConnector = new DatabaseConnector();
    int basketId = dbConnector.getLatestBasketId();
        if (basketId != -1) {
            List<DishesEnCours> dishesList = dbConnector.getDishesInBasket(basketId);
            ObservableList<DishesEnCours> dishesObservableList = FXCollections.observableArrayList(dishesList);
            panier.setItems(dishesObservableList);
            Numerodecommande.setText("Numero De Commande:"+String.valueOf(basketId));
            int count = dbConnector.countBasketsWithStatus1();
            EnAttent.setText(String.valueOf(count));
        } else {
             System.out.println("No basket found.");
             panier.getItems().clear();  // Clear the table
             Numerodecommande.setText("no commands left");
             EnAttent.setText(String.valueOf(0));
        }
    
    
    }

@FXML
    private void Confirmerresep(ActionEvent event) {
        int basketId = dbConnector.getLatestBasketId2();
        if (basketId != -1) {
            dbConnector.updateBasketStatusTo3(basketId);
            populateTable();
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
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}