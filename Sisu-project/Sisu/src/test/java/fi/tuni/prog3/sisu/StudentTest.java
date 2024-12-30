/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class StudentTest {
    
    /**
     * Test of getCourses method, of class Student.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        Student instance = new Student();
        ArrayList<String> expResult = new ArrayList<>();
        ArrayList<String> result = instance.getCourses();
        assertEquals(expResult, result);
        ArrayList<String> result2 = instance.getCourses();
        result2.add("testcourse");
        ArrayList<String> expResult2 = new ArrayList<>();
        expResult2.add("testcourse");
        assertEquals(expResult2,result2);
    }

    /**
     * Test of getDegree method, of class Student.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Student instance = new Student();
        String expResult = "";
        String result = instance.getDegree();
        assertEquals(expResult, result);
        String degree = "test";
        instance.setDegree(degree);
        assertEquals(instance.getDegree(),degree);
    }

    /**
     * Test of getDegreeSpecialisation method, of class Student.
     */
    @Test
    public void testGetDegreeSpecialisation() {
        System.out.println("getDegreeSpecialisation");
        Student instance = new Student();
        String expResult = "";
        String result = instance.getDegreeSpecialisation();
        assertEquals(expResult, result);
        String degreeSpecialisation = "test";
        instance.setDegreeSpecialisation(degreeSpecialisation);
        assertEquals(instance.getDegreeSpecialisation(),degreeSpecialisation);
    }

    /**
     * Test of setDegree method, of class Student.
     */
    @Test
    public void testSetDegree() {
        System.out.println("setDegree");
        String degree = "test";
        Student instance = new Student();
        instance.setDegree(degree);
        assertEquals(instance.getDegree(),degree);
    }

    /**
     * Test of addCourse method, of class Student.
     */
    @Test
    public void testAddCourse() {
        System.out.println("addCourse");
        String course = "test";
        Student instance = new Student();
        instance.addCourse(course);
        assertEquals(instance.getCourses().get(0),course);
    }

    /**
     * Test of clearCourses method, of class Student.
     */
    @Test
    public void testClearCourses() {
        System.out.println("clearCourses");
        String course = "test";
        Student instance = new Student();
        instance.addCourse(course);
        assertEquals(instance.getCourses().get(0),course);
        instance.clearCourses();
        assertEquals(instance.getCourses(),new ArrayList<>());

    }

    /**
     * Test of setDegreeSpecialisation method, of class Student.
     */
    @Test
    public void testSetDegreeSpecialisation() {
        System.out.println("setDegreeSpecialisation");
        String specialisation = "test";
        Student instance = new Student();
        instance.setDegreeSpecialisation(specialisation);
        assertEquals(instance.getDegreeSpecialisation(),specialisation);

    }

    /**
     * Test of saveStudent method, of class Student.
     */
    @Test
    public void testSaveStudent() throws Exception {
        System.out.println("saveStudent");
        Student student = new Student("testname","testsurname");
        String filename = "testnametestsurname.ser";
        try{
        Student.saveStudent(student, filename);    
        }catch(Exception e)
                {
            
        }
        
        
    Set<String> fileList = Stream.of(new File("users/").listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());
    
    assertEquals(fileList.contains(filename),true);

    }

    /**
     * Test of loadStudentFromFile method, of class Student.
     */
    @Test
    public void testLoadStudentFromFile() throws Exception {
        System.out.println("loadStudentFromFile");
        
        Student testStudent = new Student("testname","testsurname");
                String filename = "testnametestsurname.ser";
        try{
        Student.saveStudent(testStudent, filename);    
        }catch(Exception e)
                {
            
        }
        
        
        Student result = Student.loadStudentFromFile(filename);
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getSurname(), result.getSurname());
        assertEquals(testStudent.getUsername(), result.getUsername());

    }
    
}
