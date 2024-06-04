module best.unieats {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.context;
    requires spring.beans; // Add this line to require spring.beans
    requires spring.core; // Add this line to require spring.core
    requires spring.boot; // Add this line if necessary
    requires spring.boot.autoconfigure; // Add this line if necessary

    // Other requires directives as needed

    opens best.unieats to javafx.fxml;
    opens best.unieats.sign_up to javafx.fxml;
    exports best.unieats.sign_up;
    exports best.unieats.Login;
    exports best.unieats.menu_admin;
    exports best.unieats.menu_user;
    exports best.unieats.menu_cui;
    requires jakarta.mail;
}
