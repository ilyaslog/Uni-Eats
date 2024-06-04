package best.unieats.menu_admin;

import best.unieats.Login.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class Edit_meal {
    
    private Meal meal;
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

    public void setMeal(Meal meal) {
        this.meal = meal;
        if (meal != null) {
            NameField.setText(meal.getNomPlat());
            PriceField.setText(String.valueOf(meal.getPrix()));
            DescriptionField.setText(meal.getDescription());
            QuantiterField.setText(String.valueOf(meal.getQuantiter()));
            if (meal.isPlatDuJour()) {
                rbtnplat_du_jour.setSelected(true);
            } else {
                rbtnplat_normal.setSelected(true);
            }
            // Display meal image if available
            if (meal.getImageData() != null) {
                Photo.setImage(new Image(new java.io.ByteArrayInputStream(meal.getImageData())));
            }
        }
    }

    @FXML
    private void handleSelectImageButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Meal Image");
        selectedImage = fileChooser.showOpenDialog(Photo.getScene().getWindow());

        if (selectedImage != null) {
            try {
                // Read the selected image file
                FileInputStream inputStream = new FileInputStream(selectedImage);
                Image image = new Image(inputStream);

                // Display the image in the ImageView
                Photo.setImage(image);

                // Close the input stream
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleAddMealButtonAction() {
        // Retrieve meal data from UI fields
        String name = NameField.getText();
        String description = DescriptionField.getText();
        double price = Double.parseDouble(PriceField.getText());
        int quantity = Integer.parseInt(QuantiterField.getText());

        // Determine if it's plat du jour or plat normal based on radio button selection
        boolean isPlatDuJour = rbtnplat_du_jour.isSelected();

        // Prepare meal object with updated data
        meal.setNomPlat(name);
        meal.setDescription(description);
        meal.setPrix(price);
        meal.setQuantiter(quantity);
        meal.setPlatDuJour(isPlatDuJour);
        // Update meal image if selected
        if (selectedImage != null) {
            try {
                byte[] imageData = Files.readAllBytes(selectedImage.toPath());
                meal.setImageData(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Update meal data in the database
            DatabaseConnector connector = new DatabaseConnector();
            connector.connect(); // Connect to the database
            connector.updateMeal(meal); // Update meal data

            showAlert("Success", "Meal updated successfully.");

            // Close the current window
            Stage stage = (Stage) NameField.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database connection or query errors
            showAlert("Error", "Failed to update meal.");
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
