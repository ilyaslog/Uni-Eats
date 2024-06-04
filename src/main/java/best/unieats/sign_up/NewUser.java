/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package best.unieats.sign_up;

/**
 *
 * @author Moaad
 */
public class NewUser {
    public String firstName;
    public String lastName;
    public String email; 
    public String phone;
    public String address;
    public String username;
    public String password;
    
    public NewUser(String firstName, String lastName, String email, String phone, String address, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.username = username;
        this.password = password;
    }
}
