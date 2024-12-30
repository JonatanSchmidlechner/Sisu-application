# SISU - STUDY PLANNING APPLICATION

## Overview
##### SISU is a study planning application that allows students to plan and track their studies.
##### The project was part of the university course Programming 3, and it was done in Spring 2023 in a 3 persons project.
###### Even though I created this project during my first study year (note that Programming 3 is a course for 2nd year students), it is the only relatively big project that I can show and I think it demonstrates basic object oriented coding skills and group project work skills.

## Which files did I write?
I was responsible for implementing functionality to fetch data from an external API and create class instances (Course, Degree etc.) from the data and finally store them.

From Sisu/src/main/java/fi/tuni/prog3/sisu I wrote:
1. Assessment.java
2. Course.java
3. DegreeModule.java
4. JsonParser.java
5. StudyModule.java
6. iAPI.java

Additionally I wrote the unit tests for those files.
   
## How to run?
1. Clone the project https://github.com/JonatanSchmidlechner/Sisu-application.git
2. Install JDK and Maven.
3. In terminal, move to "Sisu" folder (Sisu-application/Sisu/).
4. Type the following command: mvn clean javafx:run


More documentation of the project and how to use the application can be found in Sisu-application/Documentation/OHJ3-dokumentaatio.pdf. The mentioned documentation is only in Finish. Also the application is only in Finish. Below is short summary of the instructions in English:

## How to use the application?
1. Top-left is a register button (Rekisteröidy). Click it.
2. Type first name (etunimi) and last name (sukunimi) and click the button below the forms (Rekisteröidy). Your username is shown above the forms: it is first name + last name, for example John Doe would be johndoe.
3. Go back to login screen (Kirjaudu sisään) from top-left, type your username and press login button.
4. Choose your degree (Tutkinto) and major (Pääaine) and press the button bottom-left corner. Based on the chosen degree the application might freeze for tens of seconds.
5. Move to study planning screen from top of the screen (Opintojen suunnittelu)
6. You will see the degree program. You can traverse the hierarchy to individual courses, which you can mark as done or undone.
