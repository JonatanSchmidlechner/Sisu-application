package fi.tuni.prog3.sisu;

import java.io.Serializable;

/**
 * General class for User. Was not used to its full extent in this project, 
 * but if the project were to be improved upon, this would have helped with 
 * implementing different types of users, such as teachers and admins.
 */
public class User implements Serializable{
    
    private String name="";
    private String surname="";
    private String username="";
    
    /**
     * Empty constructor, required in the code for the class to be serializable
     */
    public User(){
    }
    
    /**
     * User constructor
     * @param name - first name
     * @param surname - surname
     */
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        // this becomes the name that is tracked within the sisu system
        this.username = name + surname;
    }
    
    /**
     * Getter function for the first name of the user
     * @return - first name of user
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for surname of user
     * @return - surname of user
     */
    public String getSurname() {
        return surname;
    } 
    
    /**
     * Getter for username of the user
     * @return - The username
     */
    public String getUsername() {
        return username;
    }
}

