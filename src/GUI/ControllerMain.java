package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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

    @FXML private TabPane tabPane;

    private String roomsFile;
    private String coursesFile;
    private String studentsFile;
    private File lastSelected;

    private ArrayList<AbstractController> controllers = new ArrayList<>();

    public void initialize() {

        Pane pane;

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

    public Pane getPaneFromFile(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane p = loader.load(getClass().getResource(fileName).openStream());
        controllers.add(loader.getController());
        return p;
    }

    public void refreshTabs() {
        for (int i = 0; i < controllers.size(); i++) {
            controllers.get(i).refresh();
        }
    }

    public void closeProgram() {
        System.exit(0);
    }

    public void export() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><courses>";
        ArrayList<Course> courses = Manager.getSchedule().getCourseList().getAllCourses();
        for (int i = 0; i < courses.size(); i++) {
            xml += courseToXML(courses.get(i));
        }
        xml += "</courses>";

        try {
            FileHandler.writeToTextFile("Files/schedule.xml", xml);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String studentToXML(Student student) {
        return "<student id=\"" + student.getId() + "\" name=\"" + student.getName() + "\"/>";
    }

    public String teacherToXML(Teacher teacher) {
        return "<teacher name=\"" + teacher.getName() + "\"/>";
    }

    public String lessonToXML(Lesson lesson) {
        return "<lesson startTime=\"" + lesson.getStartTime() + "\" endTime=\"" + lesson.getEndTime() + "\""
                + " primaryRoom=\"" + lesson.getFirstRoom().getRoomName() + "\""
                + " secondaryRoom=\"" + (lesson.getSecondRoom() == null ? null : lesson.getSecondRoom().getRoomName()) + "\"/>";
    }

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

    public void importButtons(ActionEvent actionEvent)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        MenuItem item = (MenuItem) actionEvent.getSource();
        chooser.setTitle("Select " + item.getText());

        if(lastSelected != null)
        {
            chooser.setInitialDirectory(lastSelected);
        }

        File file = chooser.showOpenDialog(new Stage());
        if(file == null)
            return;

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

  public void showExtendPanel(ActionEvent actionEvent)
  {
      try{

          FXMLLoader loader = new FXMLLoader();
          Pane content = loader.load(getClass().getResource("ExtendTestWeekTab.fxml").openStream());
          ControllerExtendTestWeekTab controllerExtendTestWeekTab = loader.getController();

          Stage stage = new Stage();
          Scene scene = new Scene(content);
          stage.setScene(scene);
          stage.setTitle("Extend Test Week");
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
}