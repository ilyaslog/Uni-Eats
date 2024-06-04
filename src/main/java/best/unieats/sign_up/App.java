package best.unieats.sign_up;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {
       ;
        scene = new Scene(loadFXML("Sign_up"), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(context::getBean); // Inject Spring beans into controllers
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        context.close();
        super.stop();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
