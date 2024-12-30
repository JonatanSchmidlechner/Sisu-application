/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

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
public class UserTest {
    
    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = new User("testname","testsurname");
        String expResult = "testname";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getSurname method, of class User.
     */
    @Test
    public void testGetSurname() {
        System.out.println("getSurname");
        User instance = new User("testname","testsurname");
        String expResult = "testsurname";
        String result = instance.getSurname();
        assertEquals(expResult, result);

    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = new User("testname","testsurname");;
        String expResult = "testnametestsurname";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }
    
}
