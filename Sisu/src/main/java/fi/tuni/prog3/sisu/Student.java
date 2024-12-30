package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class that implements a student object, which handles the information that 
 * needs to be stored per student. Also handles saving and loading student 
 * information
 */
public class Student extends User implements Serializable{

   private ArrayList<String> courses = new ArrayList<>();
   private String degree = "";
   private String degreeSpecialisation = "";
   
   /**
    * Empty constructor for student, needed to make the class serialisable
    */
   public Student(){
   }
   
   /*
    * Constructor for student
    * @param name - first name
    * @param surname
    */
   public Student(String name, String surname){
       super(name, surname);
       
   }
   
   

   /**
    * Returns the list of courses the student has chosen
    * @return - A list of course ids 
    */
   public ArrayList<String> getCourses(){
       return this.courses;
   }
   
   /**
    * Returns the id of the degree the student has chosen
    * @return - the id of the chosen degree 
    */
   public String getDegree(){
       return this.degree;
   }
   
   /**
    * Returns the chosen degree specialisation
    * @return - the chosen degree specialisation
    */
   public String getDegreeSpecialisation(){
       return this.degreeSpecialisation;
   }
   
   /**
    * Sets the degree for the student
    * @param degree - id of the degree the student has chosen
    */
   public void setDegree(String degree){
       this.degree = degree;
   }
   

   /**
    * Adds a course by its id to the list of courses the student has chosen
    * @param course 
    */
   public void addCourse(String course){
       this.courses.add(course);
   }

   
   /**
    * Clears the chosen courses list
    */
   public void clearCourses() {
        this.courses.clear();
   }
   
   /**
    * Sets the degree specialisation program (used after student has chosen it)
    * @param specialisation - the id of the program
    */
   public void setDegreeSpecialisation(String specialisation){
       this.degreeSpecialisation = specialisation;
   }
   
   /**
    * Saves student as a serialised file, ready for later use
    * @param student - Student object to be saved
    * @param filename - destination at which file is saved
    * @throws IOException 
    */	
   public static void saveStudent(Student student, String filename) throws IOException {
    FileOutputStream fileOut = new FileOutputStream("users/" + filename);
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(student);
    out.close();
    fileOut.close();
}

/*
 * Loads student from file and returns it as Student object 
 * 
 * @return Student, student loaded from file
 * @throws IOException, if file not found
 * @throws ClassNotFoundException, if class not found
 */
public static Student loadStudentFromFile(String filename) throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream("users/" + filename);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    //Student student = new Student();
    Student student = (Student) in.readObject();
    in.close();
    fileIn.close();
    return student;
}
}
