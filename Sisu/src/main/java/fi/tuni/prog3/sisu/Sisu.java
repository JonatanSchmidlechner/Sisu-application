package fi.tuni.prog3.sisu;


import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * JavaFX Sisu
 */
public class Sisu extends Application {

    // Constants 
    private final int SCENE_WIDTH = 900;
    private final int SCENE_HEIGHT = 700;

    private final int LEFT_VBOX_WIDTH = SCENE_WIDTH / 2;
    private final int RIGHT_VBOX_WIDTH = SCENE_WIDTH / 2;

    private final int INSET = 10;

    private final String STUDENT_NAME_LABEL_TEXT = "Etunimi:   ";
    private final String STUDENT_SURNAME_LABEL_TEXT = "Sukunimi:";

    private final String STUDENT_NAME_FIELD_PROMPT_TEXT = "anna etunimesi";
    private final String STUDENT_SURNAME_FIELD_PROMPT_TEXT = "anna sukunimesi";
    
    private final String SAVE_STUDENT_FILE_EXTENSION = ".ser";
    private final Image ACHIEVEMENT_ICON = new Image(getClass().getResourceAsStream("/achievement.png"));
    // Source: https://www.aalto.fi/sites/g/files/flghsv161/files/styles/2_3_819w_356h_d/public/2022-08/Sisu_box.jpg
    private final Image SISU_LOGO_IMAGE = new Image(getClass().getResourceAsStream("/Sisu_box.jpg"));
    // Source: https://upload.wikimedia.org/wikipedia/fi/c/c6/Tampereen_yliopiston_logo_2020.png
    private final Image TUNI_LOGO_IMAGE = new Image(getClass().getResourceAsStream("/Tampereen_yliopiston_logo_2020.png"));
    // Source: https://content-webapi.tuni.fi/image-style/w1440_h480_manual_crop/proxy/public/2021-09/telme-b-002.jpg?itok=AgwOeCi6
    private final Image LOGIN_VIEW_BACKGROUND_IMAGE = new Image(getClass().getResourceAsStream("/telme-b-002.jpg"));

    private final String INFO_LABEL_STYLE = 
        "-fx-border-radius: 10 10 10 10;\n" +
        "-fx-border-color: black;\n" +
        "-fx-background-radius: 10;\n" + 
        "-fx-background-color: #FFFF00;\n" + 
        "-fx-padding: 10;";

    private final String REGISTER_BUTTON_LABEL_TEXT = "Rekisteröidy";
    private final String REGISTER_USER_FEEDBACK_ALREADY_EXISTS = "Tämän niminen opiskelija on jo olemassa!";
    private final String REGISTER_NAME_VALID_REGEX = "[a-zA-Z]+";
    private final String REGISTER_USER_FEEDBACK_SUCCESS = "Rekisteröityminen onnistui! Uusi käyttäjätunnuksesi on: ";
    private final String REGISTER_USER_FEEDBACK_INVALID_NAME = "Nimessäsi on kiellettyjä merkkejä! Käytä vain aakkosia (a-z).";
    private final String REGISTER_INFO_LABEL_TEXT = 
        "Tervetuloa rekisteröitymään SISU-palveluun!\n" + 
        "Anna etu- ja sukunimesi ja paina rekisteröidy.\n" +
        "Nimestäsi luodaan käyttäjätunnus, jolla pääset kirjautumaan sisään.\n" +
        "Käyttäjätunnus on muotoa etunimi+sukunimi.\n" + 
        "Jos nimelläsi on jo olemassa opiskelija, et voi tehdä uutta käyttäjää.\n" +
        "Nimessä tulee olla vain englanninkielisiä, isoja ja pieniä kirjaimia (a-z).";

    private final String DEGREE_SELECTION_TAB_LABEL = "Valitse opinto-ohjelma";
    private final String DEGREE_SELECTION_TAB_WELCOME_TEXT = "Tervetuloa käyttämään opintosuunnitelmaohjelmaa ";
    private final String DEGREE_SELECTION_TAB_INFO_TEXT = 
        "\"Valitse opinto-ohjelma\" välilehdellä voit valita haluamasi tutkintorakenteen.\n" +
        "Valitse opinto-ohjelma, sekä pääaine. Valinnan jälkeen paina \"Valitse opinto-ohjelma\"-näppäintä.\n" +
        "Että oikea opintorakenne saadaan ladattua, täytyy molemmissa kentissä olla tehty valinta.\n" +
        "Voit vaihtaa opinto-ohjelmaa tai pääainetta milloin tahansa, mutta se nollaa kaikki aiemmat valintasi.\n" +
        "Välilehteä vaihtamalla voit tarkastella opinto-ohjelman rakennetta tai palata takaisin tähän ruutuun.\n\n" + 
        "\"Opintojen suunnittelu\" välilehdellä voit suunnitella opintojasi.\n" +
        "Opintojakson valitsemalla näytetään opintojaksoon liittyvät tiedot, kuten \n" + 
        "   opintojakson nimi\n" +
        "   opintojakson kuvaus\n" + 
        "   opintojakson laajuus\n" +
        "   suoritustavat ja arvostelu.\n" +
        "Kursseja voi suorittaa \"Suorita kurssi\"-valintaruudulla.\n" +
        "Ohjelma muistaa valitsemasi opinto-ohjelman, sekä suorittamasi kurssit.";
    private final String DEGREE_SELECTION_BUTTON_TEXT = "Valitse opinto-ohjelma";
    private final String STUDY_PLAN_TAB_LABEL = "Opintojen suunnittelu";

    private final String FX_BACKGROUND_COLOR_TANBLUE = "-fx-background-color: #b1c2d4;";
    private final String FX_BORDER_COLOR_BLACK = "-fx-border-color: black;";
    private final String FX_BACKGROUND_COLOR_GREY = "-fx-background-color: #f2f2f2;";

    private final String LOGIN_INFO_LABEL_WELCOME = "Tervetuloa SISU-palveluun!";
    private final String LOGIN_INFO_USER_NOT_FOUND = "Käyttäjätunnusta ei löytynyt!";
    private final String LOGIN_USER_NAME_LABEL = "Käyttäjätunnus:";
    private final String LOGIN_USER_NAME_FIELD_PROMPT = "anna käyttäjätunnuksesi";
    private final String LOGIN_BUTTON_TEXT = "Kirjaudu sisään";

    private final String LOGIN_TAB_LABEL = "Kirjaudu sisään";
    private final String REGISTER_TAB_LABEL = "Rekisteröidy";

    // Member variables 
    private TreeView<DegreeItem> tree;
    private VBox degreeSelectionVBox;
    private VBox leftVBox;
    private VBox rightVBox;
    private TreeItem<DegreeItem> selectedItem;
    private List<StudyModule> degrees;
    private StudyModule currentDegree;
    private DegreeModule currentDegreeSpecialisation;
    private JsonParser jsonParser;
    private Student student;

    @Override
    public void start(final Stage stage) {
        jsonParser = new JsonParser();
        degrees = jsonParser.parseDegrees();

        stage.setResizable(false);
        stage.setScene(loginAndRegisterScene());
        stage.setTitle("SisuGUI");
        stage.show();
    }

    @Override
    public void stop() {
        // Clear and add new courses to the student object and save
          if (student != null) {
          student.clearCourses();
          ArrayList<DegreeItem> completedCourses = new ArrayList<>();
          getDegreeItems(tree.getRoot(), completedCourses);
          for (var i : completedCourses) {
              student.addCourse(i.getDegreeModule().getId());
          }
          try {
              Student.saveStudent(student, student.getUsername() + SAVE_STUDENT_FILE_EXTENSION);
          } catch (Exception e) {
              e.printStackTrace();
          }
        }
        System.out.println("Closing the application");
    }

    public static void main(String[] args) {        
        launch();
    }
    
    /**
     * Returns a HBox for the middle of the study planning view.
     * 
     * @return a HBox for the middle of the study planning view.
     */
    private HBox getStudyPlanCenterHbox() {
        HBox centerHBox = new HBox(10);
        
        leftVBox = getStudyPlanLeftVBox();
        rightVBox = getStudyPlanRightVBox();
        
        centerHBox.getChildren().addAll(leftVBox, rightVBox);
        
        return centerHBox;
    }

    /**
     * Returns a VBox for the left side of the study plannin view
     * 
     * @return a VBox for the left side of the study planning view
     */
    private VBox getStudyPlanLeftVBox() {
        //Creating a VBox for the left side.
        VBox leftVBox = new VBox();
        leftVBox.setPrefWidth(LEFT_VBOX_WIDTH);
        leftVBox.setStyle("-fx-background-color: #ffffff;");

        leftVBox.getChildren().add(getTreeView());
        updateTree(currentDegreeSpecialisation, tree.getRoot());
       
        return leftVBox;
    }

    /**
     * Returns a VBox for the right side of the study planning view
     * 
     * @return a VBox for the right side of the study planning view
     */
    private VBox getStudyPlanRightVBox() {
        //Creating a VBox for the right side.
        VBox rightVBox = new VBox();
        rightVBox.setSpacing(INSET);
        rightVBox.setPrefWidth(RIGHT_VBOX_WIDTH);
        rightVBox.setStyle(FX_BACKGROUND_COLOR_TANBLUE);
        Label nameLabel = new Label("");
        nameLabel.setWrapText(true);
        
        Label descriptionLabel = new Label("");
        descriptionLabel.setStyle(FX_BORDER_COLOR_BLACK);
        descriptionLabel.setWrapText(true);

        Label creditsLabel = new Label("");

        nameLabel.setOnMouseClicked(null);

        // Checkbox for completing a course
        CheckBox completeCourseCheckBox = new CheckBox("Suorita kurssi");
        completeCourseCheckBox.setVisible(false);
        completeCourseCheckBox.setOnAction(e -> {
            selectedItem.getValue().flipIsCompleted();
            // Update treeitem graphic to match course completion state
            if (completeCourseCheckBox.isSelected()) {        
                selectedItem.setGraphic(new ImageView(ACHIEVEMENT_ICON));
            } else {
                selectedItem.setGraphic(null);
            }          
        });
        
        rightVBox
        .getChildren()
        .addAll(
            nameLabel, 
            descriptionLabel, 
            creditsLabel, 
            completeCourseCheckBox
        );
        
        return rightVBox;
    }

    /*
     * Returns a VBox containing the treeview for the left side of the study planning view.
     * 
     * @return a VBox for the left side of the study planning view.
     */
    private VBox getDegreeSelectionCenterVBox() {
        VBox centerVBox = new VBox(10);

        HBox degreeHBox = new HBox(10);
        HBox degreeSpecialisationHBox = new HBox(10);

        centerVBox.getChildren().addAll(degreeHBox, degreeSpecialisationHBox);

        ComboBox<DegreeModule> degreeComboBox = new ComboBox<>();
        ComboBox<DegreeModule> degreeSpecialisationComboBox = new ComboBox<>();

        Text degreeText = new Text("Tutkinto");
        Text degreeSpecialisationText = new Text("Pääaine");

        degreeHBox.getChildren().addAll(degreeText, degreeComboBox);
        degreeSpecialisationHBox.getChildren().addAll(degreeSpecialisationText, degreeSpecialisationComboBox);

        degreeComboBox.setPrefWidth(500);
        degreeSpecialisationComboBox.setPrefWidth(500);

        // Degree selection combobox
        degreeComboBox.getItems().addAll(
            degrees
        );

        // Update current degree based on the selection in the comboBox.
        degreeComboBox.setOnAction(e -> {
            currentDegree = jsonParser.parseDegreeContent(degreeComboBox.getValue().getName());

            // Clear and add the degree options to the degreeOptionComboBox.
            degreeSpecialisationComboBox.getItems().removeAll(degreeSpecialisationComboBox.getItems());
            degreeSpecialisationComboBox.getItems().addAll(currentDegree.getModuleAndCourseList());

            System.out.println("\nChosen degree: " + currentDegree.getName());
        });

        // Degree specialisation selection combobox
        degreeSpecialisationComboBox.setOnAction(e -> {
            currentDegreeSpecialisation = degreeSpecialisationComboBox.getValue();

            if (currentDegreeSpecialisation != null) {
                System.out.println("\nChosen degree specialisation: " + currentDegreeSpecialisation.getName());
            }
            
        });
        
        if (!student.getDegree().equals("")) {
            System.out.println("Student has a degree " + student.getDegree() + 
                " with specialisation " + student.getDegreeSpecialisation());
            for (var i : degreeComboBox.getItems()) {
                if (i.getId().equals(student.getDegree())) {
                    currentDegree = degrees.get(degrees.indexOf(i));                
                    degreeComboBox.setValue(i);
                    degreeComboBox.fireEvent(new ActionEvent());
                    break;
                }
            }
            for (var i : degreeSpecialisationComboBox.getItems()) {
                if (i.getId().equals(student.getDegreeSpecialisation())) {
                    currentDegreeSpecialisation = currentDegree.getModuleAndCourseList().get(currentDegree.getModuleAndCourseList().indexOf(i));
                    System.out.println(currentDegreeSpecialisation);
                    degreeSpecialisationComboBox.setValue(i);
                    break;
                }
            }
        } else {
            System.out.println("Student has no previous degree");
        }

        return centerVBox;
    }

    /**
     * Returns a TreeView of the degree structure.
     * 
     * @return a TreeView of the degree structure.
     */
    private TreeView<DegreeItem> getTreeView() {
        tree = new TreeView<>();     

        tree.setPrefHeight(LEFT_VBOX_WIDTH);
        tree.setPrefHeight(SCENE_HEIGHT);

        // React to selection changes, and show the selected items properties: name, description, credits. 
        // Additionally show the checkbox if the selected item is a cource.
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<DegreeItem>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<DegreeItem>> arg0, TreeItem<DegreeItem> arg1,
                    TreeItem<DegreeItem> arg2) {
                
                selectedItem = arg0.getValue();
                Label nameLabel = (Label) rightVBox.getChildren().get(0);
                Label descriptionLabel = (Label) rightVBox.getChildren().get(1);
                Label creditsLabel = (Label) rightVBox.getChildren().get(2);
                CheckBox completeCheckBox = (CheckBox) rightVBox.getChildren().get(3);

                if (selectedItem == null) {
                    nameLabel.setText("");
                    return;
                }

                DegreeModule selectedModule = selectedItem.getValue().getDegreeModule();

                // Fill the labels with the selected items properties.
                nameLabel.setText(selectedModule.getName());
                descriptionLabel.setText(selectedModule.getDescription());

                // Display credits as a range if both min and max credits are defined. If only a lower limit is defined, display it as a minimum.
                creditsLabel.setText("Opintopisteet: " + 
                    (selectedModule.getMinCredits() == null ? "" : selectedModule.getMinCredits()) +
                    (selectedModule.getMaxCredits() == null ? "" : "-" + selectedModule.getMaxCredits())
                );
                // Additionally, if the selected item is a course, display the completion methods.
                if (selectedItem.getValue().getDegreeModule() instanceof Course) {
                    creditsLabel.setText(creditsLabel.getText() + "\nSuoritustavat:");
                    for (var i : ((Course) selectedModule).getCompletionMethods()) {
                        for (var j : i)
                        creditsLabel.setText(creditsLabel.getText() + "\n" + j.getName() + ": " + j.getGrading());
                    }
                }
                
                // Make checkboxes visible, and set them to completed if the selected item is a course.
                if (selectedItem.getValue().getIsCompleted()) {
                    completeCheckBox.setSelected(true);
                } else {
                    completeCheckBox.setSelected(false);
                }
                if (selectedItem.getValue().getDegreeModule() instanceof Course) {
                    completeCheckBox.setVisible(true);
                } else {
                    completeCheckBox.setVisible(false);
                }

                if (selectedItem.getValue().getDegreeModule().getDescription().equals("")) {
                    descriptionLabel.setText("Ei kuvausta");
                }

                System.out.println("Selected item: " + selectedItem.getValue());;
            }
        });

        return tree;
    }
    /**
     * Updates the treeview based on the current degree and degree specialization recursively.
     * 
     * @param module the module to update the treeview with.
     * @param parent the parent of the module.
     */
    private void updateTree(DegreeModule module, TreeItem<DegreeItem> parent) {
        if (module == null) {
            return;
        }
        
        if (parent == null) {
            parent = new TreeItem<>(new DegreeItem(module));
            tree.setRoot(parent);
        }
        
        for (var innerModule : ((StudyModule) module).getModuleAndCourseList()) {
            TreeItem<DegreeItem> item = null;
            
            if (innerModule instanceof Course) {
                if (student.getCourses().contains(innerModule.getId())) {
                    item = new TreeItem<>(new DegreeItem(innerModule, true), new ImageView(ACHIEVEMENT_ICON));
                } else {
                    item = new TreeItem<>(new DegreeItem(innerModule, false));
                }
            } else {
                item = new TreeItem<>(new DegreeItem(innerModule));
            }
            
            parent.getChildren().add(item);
            
            if (innerModule instanceof StudyModule) {
                updateTree(innerModule, item);
            }
        }
    }

    /*
     * Gets all the degree items from the treeview and stores them in the supplied array.
     * 
     * @param item the current item in the treeview.
     * @param items the array to store the degree items in.
     */
    private void getDegreeItems(TreeItem<DegreeItem> item, ArrayList<DegreeItem> items) {
        if (item == null) {
            return;
        }
        if (item.getValue().getIsCompleted()) {
            items.add(item.getValue());
        }      
        for (var i : item.getChildren()) {
            getDegreeItems(i, items);
        }
    }

    /*
     * Returns the login pane of the application.
     * 
     * @return the login pane of the application.
     */
    private BorderPane loginPane() {
        BorderPane loginPane = new BorderPane();

         // Add background
         Background rootBackground = new Background(new BackgroundImage(
            LOGIN_VIEW_BACKGROUND_IMAGE,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT
         ));
         loginPane.setBackground(rootBackground);
         loginPane.setPadding(new Insets(INSET, INSET, INSET, INSET));
         
         // Center VBox
         VBox centerVbox = new VBox(10);
         centerVbox.setMaxWidth(300);
         centerVbox.setStyle(FX_BACKGROUND_COLOR_GREY);
         centerVbox.setAlignment(Pos.CENTER);
         loginPane.setCenter(centerVbox);
 
         // Information label
         Label infoLabel = new Label(LOGIN_INFO_LABEL_WELCOME);
         infoLabel.setAlignment(Pos.CENTER);
 
         // Images
         ImageView tuniLogoImageView = new ImageView(TUNI_LOGO_IMAGE);
         tuniLogoImageView.setFitWidth(250);
         tuniLogoImageView.setPreserveRatio(true);
 
         ImageView sisuLogoImageView = new ImageView(SISU_LOGO_IMAGE);
         sisuLogoImageView.setFitWidth(300);
         sisuLogoImageView.setPreserveRatio(true);
        
         // Username field with label
         HBox usernameHbox = new HBox(INSET);
         usernameHbox.setAlignment(Pos.CENTER);
         Label usernameLabel = new Label(LOGIN_USER_NAME_LABEL);
         TextField usernameField = new TextField();
         usernameField.setPromptText(LOGIN_USER_NAME_FIELD_PROMPT);
         usernameField.setMaxWidth(300);
 
         usernameHbox.getChildren().addAll(usernameLabel, usernameField);
 
         // Button for login
         Button loginButton = new Button(LOGIN_BUTTON_TEXT);
         loginButton.setOnAction(e -> {
             System.out.println("Username: " + usernameField.getText());
             Stage stage = (Stage) centerVbox.getScene().getWindow();
             if (loginUtils.studentExists(usernameField.getText())) {
                 try {
                    System.out.println("Student found");
                    student = Student.loadStudentFromFile(usernameField.getText() + SAVE_STUDENT_FILE_EXTENSION);
                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
                 stage.setScene(mainScene());
                 return;
             } else {
                 infoLabel.setText(LOGIN_INFO_USER_NOT_FOUND);
                 System.out.println("User not found");
             }
         });
 
 
         HBox buttonHbox = new HBox(INSET);
         buttonHbox.setAlignment(Pos.CENTER);
         buttonHbox.getChildren().addAll(loginButton);
         
         centerVbox.getChildren().addAll(
            tuniLogoImageView,
            sisuLogoImageView,
            usernameHbox,
            buttonHbox,
            infoLabel
         );

         return loginPane;
    }

    /**
     * Returns the login and register scene of the application.
     * 
     * @return scene that is used as the login and register scene of the application.
     */
    private Scene loginAndRegisterScene() {
        // Root borderpane
        TabPane tabPane = new TabPane();
        
        // Add panes to tabs
        Tab loginTab = new Tab(LOGIN_TAB_LABEL, loginPane());
        loginTab.setClosable(false);
        Tab registerTab = new Tab(REGISTER_TAB_LABEL, registerPane());
        registerTab.setClosable(false);
        
        tabPane.getTabs().addAll(loginTab, registerTab);

        Scene scene = new Scene(tabPane, SCENE_WIDTH, SCENE_HEIGHT);

        return scene;
    }

    /*
     * Returns the register pane of the application.
     * 
     * @return pane that is used as the register scene of the application.
     */
    private BorderPane registerPane() {
        VBox centerVBox = new VBox(INSET);
        centerVBox.setAlignment(Pos.CENTER);

        // Create a HBox for student name label and field
        HBox studentNameHbox = new HBox(INSET);
        studentNameHbox.setAlignment(Pos.CENTER);
        Label studentNameLabel = new Label(STUDENT_NAME_LABEL_TEXT);
        TextField studentNameField = new TextField();
        studentNameField.setPromptText(STUDENT_NAME_FIELD_PROMPT_TEXT);
        studentNameField.setMaxWidth(300);
        studentNameHbox.getChildren().addAll(studentNameLabel, studentNameField);

        // Create a HBox for student surname label and field
        HBox studentSurnameHbox = new HBox(10);
        studentSurnameHbox.setAlignment(Pos.CENTER);
        Label studentSurnameLabel = new Label(STUDENT_SURNAME_LABEL_TEXT);    
        TextField studentSurnameField = new TextField();
        studentSurnameField.setPromptText(STUDENT_SURNAME_FIELD_PROMPT_TEXT);
        studentNameField.setMaxWidth(300);
        studentSurnameHbox.getChildren().addAll(studentSurnameLabel, studentSurnameField);

        // Create a HBox for register button
        HBox registerHBox = new HBox(10);
        registerHBox.setAlignment(Pos.CENTER);
        Button registerButton = new Button(REGISTER_BUTTON_LABEL_TEXT);

        Label userFeedBackLabel = new Label();
        
        registerButton.setOnAction(e -> {
            String name = studentNameField.getText().toLowerCase();
            String surname = studentSurnameField.getText().toLowerCase();

            // Check if student of the same name already exists
            if (loginUtils.studentExists(name + surname)) {
                userFeedBackLabel.setText(REGISTER_USER_FEEDBACK_ALREADY_EXISTS);
            // Check if name and surname are valid
            } else if(name.matches(REGISTER_NAME_VALID_REGEX) && surname.matches(REGISTER_NAME_VALID_REGEX)) {
                // Create and store new student
                loginUtils.storeNewStudent(name, surname);
                student = new Student(name, surname);
                try {
                    Student.saveStudent(student, student.getUsername() + SAVE_STUDENT_FILE_EXTENSION);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                userFeedBackLabel.setText(REGISTER_USER_FEEDBACK_SUCCESS + student.getUsername());
            // Name and surname are not valid
            } else {
                userFeedBackLabel.setText(REGISTER_USER_FEEDBACK_INVALID_NAME);
            }
        }); 
        registerHBox.getChildren().add(registerButton);

        centerVBox.getChildren().addAll(studentNameHbox, studentSurnameHbox, registerHBox);

        Label infoLabel = new Label(REGISTER_INFO_LABEL_TEXT);
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setStyle(INFO_LABEL_STYLE);

        VBox topVBox = new VBox(INSET);
        topVBox.setAlignment(Pos.CENTER);
        topVBox.getChildren().addAll(infoLabel, userFeedBackLabel);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(INSET, INSET, INSET, INSET));
        root.setTop(topVBox);
        root.setCenter(centerVBox);

        return root;
    }

    /**
     * Returns the main scene of the application.
     * 
     * @return scene that is used as the main scene of the application.
     */
    private Scene mainScene() {
        TabPane tabPane = new TabPane();
        
        // Make panes for tabs
        BorderPane degreeSelectionPane = new BorderPane();
        degreeSelectionPane.setPadding(new Insets(INSET, INSET, INSET, INSET));
        degreeSelectionVBox = getDegreeSelectionCenterVBox();
        degreeSelectionPane.setTop(degreeSelectionVBox);

        Button confirmDegreeSelectionButton = new Button(DEGREE_SELECTION_BUTTON_TEXT);
        confirmDegreeSelectionButton.setOnAction(e -> {
            if (currentDegreeSpecialisation != null && currentDegree != null) {
                System.out.println("Degree selected " + currentDegree.getName() 
                    + " selected with specialisation " + currentDegreeSpecialisation.getName());
                student.setDegree(currentDegree.getId());
                student.setDegreeSpecialisation(currentDegreeSpecialisation.getId());
                resetOnNewDegreeSelection();
                updateTree(currentDegreeSpecialisation, tree.getRoot());
            } else {
                System.out.println("No degree or spesialication selected");
            }
        });

        Label infoLabel = new Label(
            DEGREE_SELECTION_TAB_WELCOME_TEXT
            + student.getName() + " " + student.getSurname() + "!\n\n" + 
            DEGREE_SELECTION_TAB_INFO_TEXT
        );
        infoLabel.setStyle(INFO_LABEL_STYLE);
        degreeSelectionPane.setCenter(infoLabel);
        degreeSelectionPane.setBottom(confirmDegreeSelectionButton);

        BorderPane studyPlanPane = new BorderPane();
        studyPlanPane.setPadding(new Insets(INSET, INSET, INSET, INSET));
        studyPlanPane.setCenter(getStudyPlanCenterHbox());

        // Add panes to tabs
        Tab degreeSelectionTab = new Tab(DEGREE_SELECTION_TAB_LABEL, degreeSelectionPane);
        degreeSelectionTab.setClosable(false);
        tabPane.getTabs().add(degreeSelectionTab);

        Tab studyPlanTab = new Tab(STUDY_PLAN_TAB_LABEL, studyPlanPane);
        studyPlanTab.setClosable(false);
        tabPane.getTabs().add(studyPlanTab);
        
        
        Scene scene = new Scene(tabPane, SCENE_WIDTH, SCENE_HEIGHT);

        return scene;
    }

    /**
     * Cleans up the degree selection tab when a new degree is selected.
     * Resets the tree, the right side of the screen and the student object.
     * Also clears the selected item and student courses.
     */
    private void resetOnNewDegreeSelection() {
        // Clear the selected item
        selectedItem = null;

        // Fetch the labels and checkbox from VBox
        Label nameLabel = (Label) rightVBox.getChildren().get(0);
        Label descriptionLabel = (Label) rightVBox.getChildren().get(1);
        Label creditsLabel = (Label) rightVBox.getChildren().get(2);
        CheckBox checkBox = (CheckBox) rightVBox.getChildren().get(3);

        // Clear the labels and hide the checkbox
        nameLabel.setText("");
        descriptionLabel.setText("");
        creditsLabel.setText("");
        checkBox.setVisible(false);
        
        // Clear the tree and student courses
        tree.setRoot(null);
        student.clearCourses();
    }

    /**
     * Class that represents a degree module item and the linked checkbox.
     * Used to display the degree module items in the tree.
     * 
     * @see DegreeModule
     */
    private class DegreeItem {
        private DegreeModule degreeModule;
        private boolean isCompleted;

        /*
         * Constructor for the class.
         * 
         * @param degreemodule DegreeModule object that is linked to the item.
         * @param isCompleted boolean that tells if the item is completed.
         */
        public DegreeItem(DegreeModule degreemodule, boolean isCompleted) {
            this.degreeModule = degreemodule;
            this.isCompleted = isCompleted;
        }

        /*
         * Constructor for the class.
         * 
         * @param degreemodule DegreeModule object that is linked to the item.
         */
        public DegreeItem(DegreeModule degreeModule) {
            this(degreeModule, false);
        }

        /*
         * Returns the linked DegreeModule object.
         * 
         * @return DegreeModule object that is linked to the item.
         */
        public DegreeModule getDegreeModule() {
            return degreeModule;
        }

        /*
         * Returns the completion state of the item.
         * 
         * @return boolean that tells if the item is completed.
         */
        public boolean getIsCompleted() {
            return isCompleted;
        }

        /*
         * Flips the completion state of the item.
         */
        public void flipIsCompleted() {
             isCompleted = !isCompleted;
        }

        /*
         * Returns the name of the linked DegreeModule object.
         * 
         * @return String that is the name of the linked DegreeModule object.
         */
        public String toString() {
            return degreeModule.getName();
        }

    }
    

    
}