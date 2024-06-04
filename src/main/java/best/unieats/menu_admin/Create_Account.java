package best.unieats.menu_admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import best.unieats.Login.DatabaseConnector;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class Create_Account {

    @FXML
    private Button signUpButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private RadioButton rbtnadmin;

    @FXML
    private RadioButton rbtncuisto;

    private ToggleGroup role = new ToggleGroup();

    @FXML
    private void initialize() {
        rbtncuisto.setToggleGroup(role);
        rbtnadmin.setToggleGroup(role);
    }

    @FXML
    private void handleSignUpButtonAction() {
        System.out.println("Sign up button clicked");

        // Retrieve user data from UI fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        int sold = 0;

        // Retrieve the selected role (Admin or Cuisinier)
        String selectedRole = null;
        if (rbtnadmin.isSelected()) {
            selectedRole = "2";
        } else if (rbtncuisto.isSelected()) {
            selectedRole = "3";
        }

        try {
            DatabaseConnector connector = new DatabaseConnector();
            connector.connect(); // Connect to the database

            // Call the method in DatabaseConnector to insert the user data
            connector.insertUserData(firstName, lastName, email, phone, address, username, password, selectedRole, sold);

            // Display an alert that the user has been added
            showAlert("User Added", "The user has been added successfully.");

            // Close the current window
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database connection or query errors
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCloseButtonAction() throws SQLException {
        // Close the current window
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();

        // Refresh the table from the database
        MenuGestionCompteController controller = new MenuGestionCompteController();
        controller.populateTableWithData();
    }
}
