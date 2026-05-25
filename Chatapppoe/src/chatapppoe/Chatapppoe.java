
package chatapppoe;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Chatapppoe {
    
    private static User registeredUser = null;
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== CHAT APP REGISTRATION ===");
        
        // USERNAME
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        if (validator.isValidUsername(username)) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted; "
                    + "please ensure that your username contains an underscore and "
                    + "is no more than five characters in legnth.");
            return;
        }
        
        // PASSWORD
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (validator.isValidPassword(password)) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted; "
                    + "please ensure that the password contains at least eight "
                    + "characters, a capital letter, a number, and a special character. ");
            return;
        }
        
        //CELLPHONE
        System.out.print("Enter South African cell number: ");
        String cellphone = scanner.nextLine();
        
        if (!validator.isValidSouthAfricanNumber(cellphone)) {
            System.out.println("Invalid South African cellphone number format. ");
            return;
        }
        
        // SAVE USER
        registeredUser = new User(username, password, cellphone);
        System.out.println("\nRegistration complete!");
        
        // LOGIN
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String loginpass = scanner.nextLine();
        
        if (registeredUser.getUsername().equals(loginUser)&&
            registeredUser.getPassword().equals(loginpass)) {
            
            System.out.println("Login successful - welcome!");
            
            // ========================
            // QUICKCHAT starts here
            // =========================
            
            System.out.println("\nWelcome to Quickchat");
            
            System.out.print("How many messages would you like to send? ");
            
            int totalMessages =
                    Integer.parseInt(scanner.nextLine());
            
            int sentMessages = 0;
            
            while (true) {
                
                System.out.println("""
                                   ===== MENU =====
                                   1. Send Messages
                                   2. View Stored Messages
                                   3. delete message
                                   4. Quit
                                   """);
                
                int option =
                        Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    
                    case 1:
                        
                        if (sentMessages >= totalMessages) {
                            
                            System.out.println("Message limit reached.");
                            
                            System.out.println("Total messages sent: " + sentMessages);
                            break;
                        }
                        
                        System.out.print("Recipient (+27...): ");
                        
                        String recipient =
                                scanner.nextLine();
                        
                        if (!validator.isValidSouthAfricanNumber(recipient)) {
                            
                            System.out.println("Invalid recipient number.");
                            break;
                        }
                        
                        System.out.print("Enter message: ");
                        
                        String text = scanner.nextLine();
                        
                        if (text.length()> 250) {
                            
                            System.out.println(
                                    "Please enter a message of less than 250 characters.");
                            
                            break;
                        }
                        
                        Message msg =
                                MessageService.createMessage(recipient, text);
                        
                        System.out.println(
                                "Message Hash: "
                                        + msg.getmessageHash());
                        
                        System.out.println("""
                                           
                                1. Send Message
                                2. Disregard Message
                                3. Store Message
                                """);
                        
                        int action =
                                Integer.parseInt(scanner.nextLine());
                        
                        switch (action) {
                            
                            case 1:
                                System.out.println(
                                        "Message sucessfully sent");
                                
                                sentMessages++;
                                break;
                                
                            case 2:
                                System.out.println(
                                        "Press 0 to delete the message");
                                break;
                                
                            case 3:
                                MessageService.storeMessage(msg);
                                
                                sentMessages++;
                                break;
                        }
                        
                        break;
                        
                    case 2:
                        MessageService.viewMessages();
                        break;
                    
                    case 3:
                        
                        System.out.print("Error Message ID to delete: ");
                        
                        String deleteID = scanner.nextLine();
                        
                        MessageService.deleteMessage(deleteID);
                        
                        break;
                        
                    case 4:
                        
                        System.out.println("Total messages sent: " + sentMessages);
                        
                        System.out.println("Goodbye!");
                        return;
                        
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Login failed - username or password incorrect.");
        }
        
        scanner.close();
    }
}
