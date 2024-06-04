package best.unieats.menu_admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class menu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/best/unieats/menu/MenuPrincipaleVide.fxml")); 

        Scene scene = new Scene(root, 1280, 720); 
        primaryStage.setTitle("Uni Eats Menu"); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
