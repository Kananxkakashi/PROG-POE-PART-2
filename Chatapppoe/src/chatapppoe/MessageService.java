
package chatapppoe;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.*;

public class MessageService {
    
    private static int counter = 0;
    
    public static String generateMessageID() {
        
        Random random = new Random();
        
        long num = 1000000000L +
                (long)(random.nextDouble() * 9000000000L);
        
        return String.valueOf(num);
    }
    
    public static String createHash(String id,int messageNum,String text) {
        
        String[] words = text.split(" ");
        
        String first = words[0];
        String last = words[words.length - 1];
        
        return id.substring(0,2)
                + ":" + messageNum
                + ":" + (first + last).toUpperCase();
    }
    
    public static Message createMessage(String recipient,String text) {
        
        counter++;
        
        String id = generateMessageID();
        
        String hash =
                createHash(id, counter, text);
        
        return new Message(id,counter,recipient,text,hash);
    }
    
    //I HAD TO STORE MESSAGE IN TEXT FILE MY GSON WAS NOT WORKING
    public static void storeMessage(Message msg) {
        
        try (FileWriter writer =
                new FileWriter("message.txt", true)) {
          
            writer.write(msg.toString());
            writer.write("\n");
            
            System.out.println("Message successfully stored");
            
        } catch (IOException e) {
            
            System.out.println("Error storing message");
        }
    }
    
    public static void viewMessages() {
        
        try (BufferedReader reader = new BufferedReader (new FileReader("messages.txt"))) {
            
            String line;
            
            while ((line = reader.readLine()) != null) {
            
                System.out.println(line);
        }
            
        } catch (IOException e) {
            
            System.out.println("No stored messages found.");
        }
  
       }   
    
     public static void deleteMessage(String messageID) {
            
            File inputFile = new File("messages.txt");
            File tempFile = new File("temp.txt");
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    
                    FileWriter writer = new FileWriter(tempFile)) {
                        
                        String line;
                        boolean skip = false;
                        
                        while ((line = reader.readLine()) != null) {
                        
                        //deleting
                        if (line.contains("Message ID: " + messageID)) {
                    }
                        //stop
                        if (skip && line.contains("------------------------")) {
                    }
                        
                        if (!skip) {
                            writer.write(line + "\n");
                        }
                    }                       
     } catch (IOException e) {
         
         System.out.println("Error deleting message.");
     }
      
       inputFile.delete();
       tempFile.renameTo(inputFile);
       
       System.out.println("Message deleted.");
     }
     
}
     
