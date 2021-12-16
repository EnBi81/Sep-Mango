package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.FileHandler;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller of our main Window in GUI
 * @version 1
 * @author Agata, Beatricia, Greg, Simon, Uafa
 */
public class ControllerMain {
    @FXML public MenuItem extendMenu;
    @FXML public MenuItem exportButton;
    @FXML
    private Tab scheduleTab;
    @FXML
    private Tab studentsTab;
    @FXML
    private Tab courseTab;
    @FXML
    private Tab roomsTab;
    @FXML
    private Tab classTab;

    @FXML private MenuItem importRooms;
    @FXML private MenuItem importCourses;
    @FXML private MenuItem importStudents;

    @FXML private MenuItem aboutButton;

    @FXML private TabPane tabPane;

    private String roomsFile;
    private String coursesFile;
    private String studentsFile;
    private File lastSelected;

    private ArrayList<AbstractController> controllers = new ArrayList<>();
    private ControllerExtendTestWeek controllerExtendTestWeekTab;

    /**
     * Loads all tabs into main panel
     * Setting disabled for the function until a schedule is created
     */
    public void initialize() {

        Pane pane;

        //Check if the user has already imported the data, if not then
        //Disable the base functionalities
        if(Manager.getSchedule() == null)
        {
            tabPane.setDisable(true);
            exportButton.setDisable(true);
            extendMenu.setDisable(true);
        }

        try {
            pane = getPaneFromFile("StudentsTab.fxml");
            studentsTab.setContent(pane);

            pane = getPaneFromFile("VIAClassTab.fxml");
            classTab.setContent(pane);

            pane = getPaneFromFile("Course.fxml");
            courseTab.setContent(pane);

            pane = getPaneFromFile("Room.fxml");
            roomsTab.setContent(pane);

            pane = getPaneFromFile("ScheduleTab.fxml");
            scheduleTab.setContent(pane);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * Loads a pane from a single file, gets it is controller
     * and save in the controllers arraylist
     * @param fileName a name of file with pane
     * @return panel
     * @throws IOException
     */

    public Pane getPaneFromFile(String fileName) throws IOException {
        //Initialize a new fxmlloader, to be able to load the content from an fxml file
        FXMLLoader loader = new FXMLLoader();
        // Get/Load the pane from the fxml file
        Pane p = loader.load(getClass().getResource(fileName).openStream());
        //Get the controller from the fxml file and add it to the arraylist
        controllers.add(loader.getController());
        //return the loaded pane
        return p;
    }

    /**
     * Data refresh for all controllers
     */
    public void refreshTabs() {
        for (int i = 0; i < controllers.size(); i++) {
            controllers.get(i).refresh();
        }
    }

    /**
     * Closing the program
     */
    public void closeProgram() {
        System.exit(0);
    }

    /**
     * Writes the content from our program to the specified text file in XML format
     */
    public void export() {
        //Base xml file starting
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><courses>";
        ArrayList<Course> courses = Manager.getSchedule().getCourseList().getAllCourses();
        //Convert all the courses to xml and add it to the xml variable
        for (int i = 0; i < courses.size(); i++) {
            xml += courseToXML(courses.get(i));
        }
        xml += "</courses>";

        try {
            //Save the xml file
            String filePath = new File("Files/schedule.xml").getAbsolutePath();
            FileHandler.writeToTextFile(filePath, xml);
            //Open file explorer where we saved the exported xml file
            //So the user can see where did we save it for him/her
            Runtime.getRuntime().exec("explorer.exe /select," + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts student's information to XML format
     * @param student a Student object
     * @return student's information in XML format
     */
    public String studentToXML(Student student) {
        return "<student id=\"" + student.getId() + "\" name=\"" + student.getName() + "\"/>";
    }

    /**
     * Converts teacher's information to XML format
     * @param teacher a Teacher object
     * @return teacher's information in XML format
     */
    public String teacherToXML(Teacher teacher) {
        return "<teacher name=\"" + teacher.getName() + "\"/>";
    }

    /**
     * Converts lesson's information to XML format
     * @param lesson a Lesson object
     * @return lesson's information in XML format
     */
    public String lessonToXML(Lesson lesson) {
        return "<lesson startTime=\"" + lesson.getStartTime() + "\" endTime=\"" + lesson.getEndTime() + "\""
                + " primaryRoom=\"" + lesson.getFirstRoom().getRoomName() + "\""
                + " secondaryRoom=\"" + (lesson.getSecondRoom() == null ? null : lesson.getSecondRoom().getRoomName()) + "\"/>";
    }

    /**
     * Converts course's information to XML format
     * @param course a Course object
     * @return courses's information in XML format
     */
    public String courseToXML(Course course) {
        String xml = "<course name=\"" + course.getCourseName() + "\">";
        xml += "<teachers>";
        for (int i = 0; i < course.getAllTeachers().size(); i++) {
            xml += teacherToXML(course.getAllTeachers().get(i));
        }
        xml += "</teachers><students>";
        for (int i = 0; i < course.getAllStudents().size(); i++) {
            xml += studentToXML(course.getAllStudents().get(i));
        }
        xml += "</students><lessons>";
        for (int i = 0; i < course.getAllLessons().size(); i++) {
            xml += lessonToXML(course.getAllLessons().get(i));
        }
        xml += "</lessons></course>";

        return xml;
    }

    /**
     * Takes user input from text files and imports the data into the program
     * @param actionEvent
     */
    public void importButtons(ActionEvent actionEvent)
    {
        //Create new file chooser
        FileChooser chooser = new FileChooser();
        //Add extension filter, so the user can only select text files
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        MenuItem item = (MenuItem) actionEvent.getSource();
        //Set the title of the chooser according the menu button he/she pressed
        //For example if he pressed the menu item Courses, the title will be "Select Courses"
        chooser.setTitle("Select " + item.getText());

        if(lastSelected != null)
        {
            //Make the user's life better by starting the chooser
            //In the last selected file's directory
            chooser.setInitialDirectory(lastSelected);
        }

        //Run the chooser
        File file = chooser.showOpenDialog(new Stage());
        //If the user did not choose anything
        if(file == null)
            return;

        //Make the user's life better part 2:
        //Saving the directory (folder) of the chosen file
        lastSelected = file.getParentFile();

        if(actionEvent.getSource() == importRooms)
        {
            roomsFile = file.getAbsolutePath();
        }
        else if(actionEvent.getSource() == importCourses)
        {
            coursesFile = file.getAbsolutePath();
        }
        else studentsFile = file.getAbsolutePath();

        //If the user has read all the files, then import and enable some base functionalities
        if(roomsFile != null && studentsFile != null && coursesFile != null)
        {
            Manager.importData(coursesFile, roomsFile, studentsFile);
            tabPane.setDisable(false);
            extendMenu.setDisable(false);
            exportButton.setDisable(false);

            for (int i = 0; i < controllers.size(); i++) {
                controllers.get(i).initialize();
            }

            refreshTabs();
        }
    }

    /**
     * Loads and shows the extend window
     * @param actionEvent
     */
  public void showExtendPanel(ActionEvent actionEvent)
  {
      try{
          //Load the Pane and the controller from the ExtendTestWeekTab file
          FXMLLoader loader = new FXMLLoader();
          Pane content = loader.load(getClass().getResource("ExtendTestWeekTab.fxml").openStream());
          controllerExtendTestWeekTab = loader.getController();

          Stage stage = new Stage();
          Scene scene = new Scene(content);
          stage.setScene(scene);
          stage.setTitle("Extend Test Week");
          //Assign the stage to the controller, so it can be programmatically closed from that controller
          //(The program closes the pop-up window when the user presses the extend button
          controllerExtendTestWeekTab.stage = stage;

          stage.showAndWait();
          refreshTabs();
          Manager.saveSchedule();
      }
      catch (IOException e)
      {
          System.out.println(e.getMessage());
      }

  }

  public void helpButton ()
  {
      int choice = JOptionPane.showConfirmDialog(null,"Do you like our program ?", "", JOptionPane.YES_NO_OPTION);
      if (choice == JOptionPane.NO_OPTION)
      {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("WRONG ANSWER");

          alert.showAndWait();
          System.exit(1);
      }
  }

}