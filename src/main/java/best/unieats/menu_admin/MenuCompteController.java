
    package best.unieats.menu_admin;

    import best.unieats.Login.DatabaseConnector;
    import best.unieats.Login.LoginController;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import javafx.animation.TranslateTransition;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.Pane;
    import javafx.util.Duration;

    public class MenuCompteController {

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

         @FXML
         private Button updatepassword;
         @FXML
         private Button updatetéléphone;
         @FXML
         private Button updateusername;
         @FXML
         private Button updateadresse;
         @FXML
         private Button updatemail;
         @FXML
         private Button updateprenom;
         @FXML
         private Button updatenom;     


         int idUser;
        @FXML
        private Pane SideMenu1;

        @FXML
        private Pane SideMenu2;

         @FXML
        private Pane SideMenu3;

       
        // Bouton a gauche de la page
        @FXML
        private Button btn3Bars;
    // Bouton a droite de la page
        @FXML
        private Button btnProfile;

    //Les boutons du side menu 1 actioner par bt3Bars
        @FXML
        private Button btnMenuQuotidien;

        @FXML
        private Button btnGestion_Menu;

        @FXML
        private Button btnPlats;

        @FXML
        private Button btnCommande;

        @FXML
        private Button btnCompte;

        @FXML
        private Button btnStats;

        //Les boutons du side menu 2 actioner par btnProfile
        @FXML
        private Button btnDetails;

        @FXML
        private Button btnDeconnecter;

        @FXML
        private Pane MainMenu;

        private boolean isMenu1Open = false;
        private boolean isMenu2Open = false;
   

        @FXML
        private void initialize() throws SQLException{
            this.getUser();
            // Déplacer les panneaux en dehors du panneau principal
            SideMenu1.setTranslateX(-SideMenu1.getWidth());
            SideMenu2.setTranslateX(SideMenu2.getWidth());
            

            btn3Bars.setOnAction(event -> {
                if (isMenu1Open) {
                    closeMenu(SideMenu1);
                } else {
                    openMenu(SideMenu1);
                }
                isMenu1Open = !isMenu1Open;
            });

            btnProfile.setOnAction(event -> {
                if (isMenu2Open) {
                    closeMenu2(SideMenu2);
                 
                } else {
                    openMenu2(SideMenu2);
                     
                }
                isMenu2Open = !isMenu2Open;
            });

       

        }


        private void openMenu(Pane menu) {
            TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.5), menu);
            openTransition.setToX(0);
            menu.setVisible(true);
            openTransition.play();
        }

        private void closeMenu(Pane menu) {
            TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.5), menu);
            closeTransition.setToX(-menu.getWidth());
            closeTransition.play();
            closeTransition.setOnFinished(e -> menu.setVisible(false));
        }

        private void openMenu2(Pane menu) {
            TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.5), menu);
            openTransition.setToX(0);
            menu.setVisible(true);
            openTransition.play();
        }

        private void closeMenu2(Pane menu) {
            TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.5), menu);
            closeTransition.setToX(menu.getWidth());
            closeTransition.play();
            closeTransition.setOnFinished(e -> menu.setVisible(false));
        }
       @FXML
        private void launchMenu_Plat() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_plat.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);


        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }

           @FXML
        private void launchMenu_Stats() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/MenuPrincipaleVide.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);


        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }


        @FXML
        private void launchMenu_Commande() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Commande.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        }
        @FXML
        private void launchMenu_Compte() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Compte.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        }
        @FXML
        private void launchMenu_DetailCompte() throws SQLException {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_DetailCompte.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        }
         @FXML
        private void launchMenu_MenuQuotidien() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Menu_Quotidien.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        }
          @FXML
        private void launchMenu_MenuGestionMenuQuotidien() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/menu/Gestion_Menu.fxml"));
            Parent newContent = loader.load();

            // Replace the content of the main Pane with the new content
            MainMenu.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        }

         public void updateMenuPlatVisibility(Pane menu) {
        if (isMenu1Open || isMenu2Open) {
                menu.setDisable(true);
            } else {
                menu.setDisable(false);
            }
        }

            @FXML
    private void handleDeconnection() {
        try {
            System.out.println("test");
            // Load the FXML file for the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/best/unieats/login/hello-view.fxml"));
            Parent loginPage = loader.load();

            // Replace the content of the main Pane with the login page
            MainMenu.getChildren().setAll(loginPage);

            // Close any open side menus
            if (isMenu1Open) {
                closeMenu(SideMenu1);
                isMenu1Open = false;
            }
            if (isMenu2Open) {
                closeMenu2(SideMenu2);
                isMenu2Open = false;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }}
         // ******** Menu_ DetailCompte.fxml *********



  private void getUser() throws SQLException {
    if (LoginController.username == null) {
        System.out.println("User not specified");
        return;
    }
    txtfusername.setText(LoginController.username);
    Connection connection = DatabaseConnector.connect();
    String query = "SELECT * FROM users WHERE users.username=?";
    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, LoginController.username);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        int id = rs.getInt("userid");
        String name = rs.getString("name");
        String prenom = rs.getString("lastname");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String password = rs.getString("password");
        String tel = rs.getString("numero_de_telephone");
        txtfnom.setText(name);
        txtfprenom.setText(prenom);
        txtfmail.setText(email);
        txtfadresse.setText(address);
        txtftéléphone.setText(tel);
        txtfpassword.setText(password);
        // Set fields to gray and uneditable initially
        setFieldsToGrayAndUneditable();
        idUser = id;
    } else {
        System.out.println("Error: User not found.");
    }
}

private void setFieldsToGrayAndUneditable() {
    txtfnom.setEditable(false);
    txtfprenom.setEditable(false);
    txtfmail.setEditable(false);
    txtfadresse.setEditable(false);
    txtftéléphone.setEditable(false);
    txtfpassword.setEditable(false);
    txtfusername.setEditable(false); 
    txtfnom.setStyle("-fx-control-inner-background: gray");
    txtfprenom.setStyle("-fx-control-inner-background: gray");
    txtfmail.setStyle("-fx-control-inner-background: gray");
    txtfadresse.setStyle("-fx-control-inner-background: gray");
    txtftéléphone.setStyle("-fx-control-inner-background: gray");
    txtfpassword.setStyle("-fx-control-inner-background: gray");
    txtfusername.setStyle("-fx-control-inner-background: gray"); // Add this line to set username background to gray
}


@FXML
private void handleUpdateButtons(ActionEvent event) {
    if (idUser <= 0) {
        return;
    }
    if (event.getSource() == updatenom) {
        toggleFieldEditability(txtfnom);
    } else if (event.getSource() == updateprenom) {
        toggleFieldEditability(txtfprenom);
    } else if (event.getSource() == updatemail) {
        toggleFieldEditability(txtfmail);
    } else if (event.getSource() == updateadresse) {
        toggleFieldEditability(txtfadresse);
    } else if (event.getSource() == updatepassword) {
        toggleFieldEditability(txtfpassword);
    } else if (event.getSource() == updatetéléphone) {
        toggleFieldEditability(txtftéléphone);
    } else if (event.getSource() == updateusername) { // Add this condition to handle updating username
        toggleFieldEditability(txtfusername);
    }
}

private void toggleFieldEditability(TextField field) {
    if (field.isEditable()) {
        field.setEditable(false);
        field.setStyle("-fx-control-inner-background: gray");
    } else {
        field.setEditable(true);
        field.setStyle("-fx-control-inner-background: white");
    }
}


         @FXML
         private void handleSave() throws SQLException{
            if(idUser<=0){
                return;
            }
            Connection connection = DatabaseConnector.connect();
            String query = "UPDATE users SET name=?, lastname=?, email=?, address=?, numero_de_telephone=?, password=?, username=? WHERE userid=?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(7, LoginController.username);
            stmt.setString(1, txtfnom.getText());
            stmt.setString(2, txtfprenom.getText());
            stmt.setString(3, txtfmail.getText());
            stmt.setString(4, txtfadresse.getText());
            stmt.setString(5, txtftéléphone.getText());
            stmt.setString(6, txtfpassword.getText());
            stmt.setInt(8, idUser);

            stmt.executeUpdate();
         }

    }
