package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.stage.FileChooser;

public class Add_meal {
    @FXML
    private Button Add_meal;
   
    private File selectedImage;

    @FXML
    private ImageView Photo;
    @FXML
    private TextField NameField;

    @FXML
    private TextField PriceField;

    @FXML
    private TextField DescriptionField;
    @FXML
    private TextField QuantiterField;
    @FXML
    private RadioButton rbtnplat_normal;

    @FXML
    private RadioButton rbtnplat_du_jour;
    
    private ToggleGroup is_plat = new ToggleGroup();

    @FXML
    private void initialize() {
        rbtnplat_normal.setToggleGroup(is_plat);
        rbtnplat_du_jour.setToggleGroup(is_plat);
    }

@FXML
private void handleSelectImageButtonAction() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Meal Image");
    selectedImage = fileChooser.showOpenDialog(Add_meal.getScene().getWindow());

    if (selectedImage != null) {
        Image image = new Image(selectedImage.toURI().toString());
        Photo.setImage(image);
    }
}

@FXML
private void handleAddMealButtonAction() {
    System.out.println("Add meal button clicked");

    // Retrieve meal data from UI fields
    String name = NameField.getText();
    String description = DescriptionField.getText();
    double price = Double.parseDouble(PriceField.getText());

    // Determine if it's plat du jour or plat normal based on radio button selection
    boolean isPlatDuJour = rbtnplat_du_jour.isSelected();

    try {
        DatabaseConnector connector = new DatabaseConnector();
        connector.connect(); // Connect to the database

        // Create a Meal object with the retrieved data
        Meal meal = new Meal(0, name, description, price, 0, isPlatDuJour);

        // If an image is selected, read the image data
        if (selectedImage != null) {
            byte[] imageData = Files.readAllBytes(selectedImage.toPath());
            meal.setImageData(imageData);

            // Call the method in DatabaseConnector to insert the meal data
            connector.insertMealData(meal);

            // Display an alert that the meal has been added
            showAlert("Meal Added", "The meal has been added successfully.");

            // Close the current window
            Stage stage = (Stage) NameField.getScene().getWindow();
            stage.close();
        } else {
            showAlert("No Image Selected", "Please select a meal image.");
        }

    } catch (SQLException | IOException e) {
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
}
