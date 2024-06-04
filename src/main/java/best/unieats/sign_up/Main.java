/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package best.unieats.sign_up;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author Moaad
 */
public class Main {
    
    public static void main(String[] args) {
        // Start the Spring Boot application
        
        Thread springAppThread = new Thread(() -> SpringApplication.run(App.class, args));
        springAppThread.start();

        // Launch the JavaFX application
        Application.launch(VerificationPage.class, args);
    }
}