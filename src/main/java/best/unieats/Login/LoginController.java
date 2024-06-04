package best.unieats.Login;

import best.unieats.sign_up.*;
import static best.unieats.sign_up.MainClass.springContext;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
     @FXML
    private Label CreateOne;
     
    public static String username;
     
 @FXML
private void handleLogin(ActionEvent event) {
    // Get the username and password from the fields
    String enteredUsername = usernameField.getText();
    String enteredPassword = passwordField.getText();
    //set the login window to currentStage for better Handling
    Stage currentStage = (Stage) CreateOne.getScene().getWindow();
    // Connect to the database
    try (Connection connection = DatabaseConnector.connect()) {
        // Check if the user exists and the password matches
        String userRole = checkUserCredentials(connection, enteredUsername, enteredPassword);
        if (userRole != null) {
            username = usernameField.getText();
            if (userRole.equals("2")) {
                // Open the page for role 2
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/MenuPrincipaleVide.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                currentStage.close();
            } else if (userRole.equals("3"))  {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_cui/Menu_Commande.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                currentStage.close();
            }else if (userRole.equals("1"))  {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu_user/Menu_Quotidien.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                currentStage.close();
            }
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password!");
            showAlert("Invalid Credentials", "Please check your username and password.");
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
        showAlert("Error", "An error occurred.");
    }
}


    @FXML
    private void handleLabelClick(MouseEvent event) {
        try {
        springContext = SpringApplication.run(MainClass.class);
        // Load the FXML file for the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/sign_up/Sign_up.fxml"));

        Parent root = loader.load();
        PrimaryController controller = loader.getController();
        controller.setSpringContext(springContext);
        // Create a new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        // Show the new window
        stage.show();

        // Close the current window if needed
        Stage currentStage = (Stage) CreateOne.getScene().getWindow();
        PrimaryController.setPrimaryStage(stage);

        // Launch VerificationPage when sign-up button is clicked
        controller.setVerificationPageLauncher(new MainClass()::launchVerificationPage);
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


   private String checkUserCredentials(Connection connection, String username, String password) throws SQLException {
    String query = "SELECT role FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                // Return the user's role if credentials are valid
                return resultSet.getString("role");
            }
        }
    }
    return null; // Return null if user with provided credentials doesn't exist or credentials are invalid
}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

