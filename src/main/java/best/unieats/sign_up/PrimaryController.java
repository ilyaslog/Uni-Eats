package best.unieats.sign_up;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

@Controller
public class PrimaryController {

    public static String email;
    public static String VerificationCode;

    public static NewUser newUser;

    @Autowired
    private EmailVerificationService verificationService;

    @FXML
    private Rectangle mainRectangle;
    @FXML
    private Label LoginClick;
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
    private Label champs_non_saisie;

    @FXML
    private Label E_mail_utilise;

    @FXML
    private Label Pseudo_utilise;

    @FXML
    private Label mdp_matchent_pas;

    @FXML
    private Label termes_conditions;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField retypePasswordField;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    public void initialize() {
        // You can initialize UI components or add event handlers here
    }

    private static Stage primaryStage;
    private Runnable verificationPageLauncher;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void setVerificationPageLauncher(Runnable verificationPageLauncher) {
        this.verificationPageLauncher = verificationPageLauncher;
    }

    public void setSpringContext(ConfigurableApplicationContext springContext) {
        this.verificationService = springContext.getBean(EmailVerificationService.class);
    }

    @FXML
    void handleLoginClick(MouseEvent event) throws IOException {
        // Load the FXML file for the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/login/hello-view.fxml"));
        Parent root = loader.load();
        // Create a new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        // Show the new window
        stage.show();
        // Close the current window if needed
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleSignUpButtonAction() {
        champs_non_saisie.setVisible(false);
          E_mail_utilise.setVisible(false);
           mdp_matchent_pas.setVisible(false);
               termes_conditions.setVisible(false);
           Pseudo_utilise.setVisible(false );
        System.out.println("Sign up button clicked");

        // Retrieve user data from UI fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String retypePassword = retypePasswordField.getText();
        boolean agreeTerms = termsCheckBox.isSelected();

        // Check if all fields are filled
       // Check if any required field is empty
if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
        address.isEmpty() || username.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
    System.out.println("All fields are required");
    champs_non_saisie.setVisible(true);
    return;
}

// Check if the email is already used
if (isEmailAlreadyUsed(email)) {
    // Email is already used, display error message or handle accordingly
    System.out.println("Email is already used");
    E_mail_utilise.setVisible(true);
    return;
}

// Check if the passwords match
if (!password.equals(retypePassword)) {
    // Passwords don't match, display error message or handle accordingly
    System.out.println("Passwords do not match");
    mdp_matchent_pas.setVisible(true);
    return;
}

// Check if the terms checkbox is selected
if (!agreeTerms) {
    // Terms checkbox is not selected, display error message or handle accordingly
    System.out.println("Please agree to the terms");
    termes_conditions.setVisible(true);
    return;
}

// Check if the username is already used
if (isUsernameAlreadyUsed(username)) {
    // Username is already used, display error message or handle accordingly
    System.out.println("Username is already used");
    Pseudo_utilise.setVisible(true);
    return;
}

        // If all validations pass, proceed with sign up and send verification email
        newUser = new NewUser(firstName, lastName, email, phone, address, username, password);

        String verificationCode = generateVerificationCode();
        System.out.println(verificationService);

        // Send verification email
        verificationService.sendVerificationCode(email, verificationCode); // Pass email to verificationService
        PrimaryController.VerificationCode = verificationCode;
        PrimaryController.email = email;
        System.out.println("Verification email sent successfully!");
        primaryStage.hide();

        // Launch VerificationPage
        launchVerificationPage();
    }

    private boolean isEmailAlreadyUsed(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8701905", "sql8701905", "EhBFwQIzaY");

            // Prepare SQL query to check if the username exists
            String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            // Execute the query
            rs = stmt.executeQuery();

            // Retrieve the result
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, username is already used
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC resources in reverse order of opening
            try { rs.close(); } catch (Exception e) {}
            try { stmt.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }

        // Return false if an error occurred or if username is not used
        return false;

    }

    private boolean isUsernameAlreadyUsed(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8701905", "sql8701905", "EhBFwQIzaY");

            // Prepare SQL query to check if the username exists
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // Execute the query
            rs = stmt.executeQuery();

            // Retrieve the result
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, username is already used
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC resources in reverse order of opening
            try { rs.close(); } catch (Exception e) {}
            try { stmt.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }

        // Return false if an error occurred or if username is not used
        return false;
    }

    private String generateVerificationCode() {
        // Generate a random verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void launchVerificationPage() {
        if (verificationPageLauncher != null) {
            verificationPageLauncher.run();
        }
    }
}
