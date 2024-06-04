package best.unieats.sign_up;

import best.unieats.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class SecondaryController {
    
    private String verificationCode;
    private String email;
    
    private static final String DB_URL = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8701905";
    private static final String DB_USER = "sql8701905";
    private static final String DB_PASSWORD = "EhBFwQIzaY";   
    
    @Autowired
    public static EmailVerificationService verificationService;
    
    @FXML
    private Button btnVerification;
    
    @FXML
    private Label verificationStatusLabel;
    
    @FXML
    private TextField txtfInputCode;
    
    @FXML
    public void initialize() {
        // You can initialize UI components or add event handlers here
    }
    
    @FXML
    public void handlerVerificationCode(){
        verificationStatusLabel = new Label();
        if(verificationCode==null){
            if(PrimaryController.VerificationCode!=null){
                verificationCode = PrimaryController.VerificationCode;
            }
            else{
                handleResendCode();
            }
        }

        String txtInputCode = txtfInputCode.getText();
        if(!txtInputCode.equals(this.verificationCode)){
            verificationStatusLabel.setText("Verification code is not correct.");
            verificationStatusLabel.setLayoutX(100);
            verificationStatusLabel.setLayoutY(100);
            verificationStatusLabel.setTextFill(Color.color(0,0,0));
        }
        else{
            System.out.println("code verified.");
            Stage stage = (Stage) btnVerification.getScene().getWindow();
            addUserToDatabase(PrimaryController.newUser);
            
            stage.hide();
            
         navigateToLoginPage();
        }
    }
      private void navigateToLoginPage() {
        try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/login/hello-view.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the new window
            stage.show();

            // Close the current window if needed
            Stage currentStage = (Stage) btnVerification.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String generateVerificationCode() {
        // Generate a random verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    
    @FXML
    public void handleResendCode(){
        if (verificationService != null) {
            email = PrimaryController.email;
            this.verificationCode = generateVerificationCode();
            verificationService.sendVerificationCode(email, verificationCode);
        } else {
            System.err.println("EmailVerificationService is not injected properly.");
        }
    }
    
    public void setSpringContext(ConfigurableApplicationContext springContext) {
        this.verificationService = springContext.getBean(EmailVerificationService.class);
    }
    
    private static void addUserToDatabase(NewUser newUser) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users ( name, lastname, email, address, username, password, role) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            /*
             *
             *
                userid auto-incriments
             *
             *
            */
           
                    pstmt.setString(1, newUser.firstName);
                    pstmt.setString(2, newUser.lastName);
                    pstmt.setString(3, newUser.email);
                    pstmt.setString(4, newUser.address);
                    pstmt.setString(5, newUser.username);
                    pstmt.setString(6, newUser.password);
                    pstmt.setInt(7, 1);  // Setting role = 1
    

            pstmt.executeUpdate();
            System.out.println("User added successfully.");
         

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}