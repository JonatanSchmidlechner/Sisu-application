package fi.tuni.prog3.sisu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class has functions that help in the login phase. These functions help track
 * and store students in a file that tracks who is in the system.
 */
public class loginUtils {
    
    /**
     * Stores student in students file as new entry, if id does not exist
     * in file already. Requires students file to exist already.
     * @param id Student id
     * @return True: storing student worked, false: didn't work 
     */
    public static boolean storeNewStudent(String name, String surname){
        String username = name + surname;
        if(!studentExists(username)){
            try (FileWriter writer = new FileWriter("students",true);
                BufferedWriter bw = new BufferedWriter(writer)) {
                    bw.write(username);
                    bw.newLine();
            } 
            catch (IOException e) {
                    e.printStackTrace();
            }
            System.out.println("Adding succesful");
            return true;
        }
	return false;
    }
	
    /**
     * Function checks whether the student who is trying to log in exists in the
     * system. 
     * @param username - the username of the student
     * @return True - the student is stored, False - the student is not stored
     */
    public static boolean studentExists(String username){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("students"));
            String line = reader.readLine();
            // check whether student is already present in database
            while (line != null) {
                 if (line.equals(username)){
                    reader.close();
                    return true;
                 }
            line = reader.readLine();
            }
            reader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
