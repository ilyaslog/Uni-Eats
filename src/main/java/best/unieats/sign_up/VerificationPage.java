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
public class VerificationPage extends Application {

    private ConfigurableApplicationContext springContext;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up_verif.fxml"));
        Parent root = fxmlLoader.load();
        SecondaryController controller = fxmlLoader.getController();

        // Set Spring context in the controller
        controller.setSpringContext(springContext);
        
        //SecondaryController.verificationService = springContext.getBean(EmailVerificationService.class);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

