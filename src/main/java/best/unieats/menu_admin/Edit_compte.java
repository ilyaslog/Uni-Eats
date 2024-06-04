package best.unieats.menu_admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import best.unieats.Login.DatabaseConnector;
import best.unieats.Login.LoginController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Edit_compte {

    @FXML
    private Button updatepassword;

    @FXML
    private Button updateusername;

    @FXML
    private Button updatetéléphone;

    @FXML
    private Button updateadresse;

    @FXML
    private Button updatemail;

    @FXML
    private Button updateprenom;

    @FXML
    private Button updatenom;
    
    
    
    @FXML
    private TextField txtfnom;

    @FXML
    private TextField txtfprenom;

    @FXML
    private TextField txtfmail;

    @FXML
    private TextField txtfadresse;

    @FXML
    private TextField txtftéléphone;

    @FXML
    private TextField txtfusername;
    
    
    @FXML
    private TextField txtfpassword;

    private int selectedUserId; // store the selected user ID

    @FXML
    private void initialize() {
        // Call getUser() with the selected user ID when the FXML is loaded
        try {
            getUser(selectedUserId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getUser(int selectedUserId) throws SQLException {
        Connection connection = DatabaseConnector.connect();
        String query = "SELECT * FROM users WHERE userid=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, selectedUserId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            txtfusername.setText(rs.getString("username"));
            txtfnom.setText(rs.getString("name"));
            txtfprenom.setText(rs.getString("lastname"));
            txtfmail.setText(rs.getString("email"));
            txtfadresse.setText(rs.getString("address"));
            txtftéléphone.setText(rs.getString("numero_de_telephone"));
            txtfpassword.setText(rs.getString("password"));

            setEditableFields(false);
        } else {
            System.out.println("Error: User not found");
        }
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
        try {
            // Call getUser() with the selected user ID when the FXML is loaded
            getUser(selectedUserId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   @FXML
private void handleUpdateButtons(ActionEvent event) {
    Button button = (Button) event.getSource();
    TextField textField = null;

    switch (button.getId()) {
        case "updatenom":
            textField = txtfnom;
            break;
        case "updateprenom":
            textField = txtfprenom;
            break;
        case "updatemail":
            textField = txtfmail;
            break;
        case "updateadresse":
            textField = txtfadresse;
            break;
        case "updatepassword":
            textField = txtfpassword;
            break;
        case "updatetéléphone":
            textField = txtftéléphone;
            break;
        case "updateusername": // Add case for updating username
            textField = txtfusername;
            break;
    }

    if (textField != null) {
        boolean editable = !textField.isEditable();
        textField.setEditable(editable);
        textField.setStyle("-fx-control-inner-background: " + (editable ? "white" : "gray"));
    }
}


   @FXML
    private void handleSave(ActionEvent event) {
        try {
            if (selectedUserId <= 0) {
                return;
            }

            Connection connection = DatabaseConnector.connect();
            String query = "UPDATE users SET name=?, lastname=?, email=?, address=?, numero_de_telephone=?, password=?, username=? WHERE userid=?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, txtfnom.getText());
            stmt.setString(2, txtfprenom.getText());
            stmt.setString(3, txtfmail.getText());
            stmt.setString(4, txtfadresse.getText());
            stmt.setString(5, txtftéléphone.getText());
            stmt.setString(6, txtfpassword.getText());
            stmt.setString(7, txtfusername.getText());
            stmt.setInt(8, selectedUserId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "User updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Failed to update user.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

  private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) updatepassword.getScene().getWindow();
        stage.close();
    }


    private void setEditableFields(boolean editable) {
        txtfusername.setEditable(editable);
        txtfnom.setEditable(editable);
        txtfprenom.setEditable(editable);
        txtfmail.setEditable(editable);
        txtfadresse.setEditable(editable);
        txtftéléphone.setEditable(editable);
        txtfpassword.setEditable(editable);

        String backgroundStyle = "-fx-control-inner-background: " + (editable ? "white" : "gray");
        txtfusername.setStyle(backgroundStyle);
        txtfnom.setStyle(backgroundStyle);
        txtfprenom.setStyle(backgroundStyle);
        txtfmail.setStyle(backgroundStyle);
        txtfadresse.setStyle(backgroundStyle);
        txtftéléphone.setStyle(backgroundStyle);
        txtfpassword.setStyle(backgroundStyle);
    }
}
