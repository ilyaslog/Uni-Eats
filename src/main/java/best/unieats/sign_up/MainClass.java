package best.unieats.sign_up;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import static javafx.application.Application.launch;

@SpringBootApplication
public class MainClass extends Application {

    public static ConfigurableApplicationContext springContext;
    public static String email;
    public static String verificationCode;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_up.fxml"));
        Parent root = fxmlLoader.load();
        PrimaryController controller = fxmlLoader.getController();

        // Set Spring context in the controller
        System.out.println(springContext);
        controller.setSpringContext(springContext);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        PrimaryController.setPrimaryStage(stage);

        // Launch VerificationPage when sign-up button is clicked
        controller.setVerificationPageLauncher(this::launchVerificationPage);
    }
    
    public void launchVerificationPage() {
        Stage verificationStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up_verif.fxml"));
        try {
            Parent root = fxmlLoader.load();
            SecondaryController controller = fxmlLoader.getController();

            // Pass email and verification code to SecondaryController
            email = PrimaryController.email;
            verificationCode = PrimaryController.VerificationCode;

            // Set Spring context in the controller
            controller.setSpringContext(springContext);

            Scene verificationScene = new Scene(root);
            verificationStage.setScene(verificationScene);
            verificationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(MainClass.class);
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
