/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.io.PrintWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Kavan
 */
public class loginUtilsTest {
    
 
    /**
     * Test of studentExists method, of class loginUtils.
     */
    @Test
    public void testStudentExists() {
        // clear testfile
        try{
        PrintWriter writer = new PrintWriter("students");
        writer.print("");
        writer.close();
        }
        catch(Exception e){
            
        }
        System.out.println("studentExists");
        String username = "TestUser";
        boolean expResult = false;
        boolean result = loginUtils.studentExists(username);
        assertEquals(expResult, result);
        loginUtils.storeNewStudent("Test","User");
        boolean result2 = loginUtils.studentExists(username);
        boolean expResult2 = true;
        assertEquals(expResult2, result2);
        
    }
    
    /**
     * Test of storeNewStudent method, of class loginUtils.
     */
    @Test
    public void testStoreNewStudent() {
        //clear testfile
        try{
        PrintWriter writer = new PrintWriter("students");
        writer.print("");
        writer.close();
        }
        catch(Exception e){
            
        }
        System.out.println("storeNewStudent");
        String name = "";
        String surname = "";
        boolean expResult = true;
        boolean result = loginUtils.storeNewStudent(name, surname);
        assertEquals(expResult, result);

    }

    
}
