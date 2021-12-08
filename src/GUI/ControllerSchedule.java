package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControllerSchedule
{

  /*//Main Pain and menu bar
  @FXML private  VBox mainPane;
  @FXML private MenuBar menuBar;
  @FXML private MenuButton fileMenuButton;
  @FXML private MenuButton editMenuButton;
  @FXML private MenuButton helpMenuButton;
  @FXML private MenuItem importMenuItem;
  @FXML private MenuItem exportMenuItem;
  @FXML private MenuItem extendMenuItem;
  @FXML private MenuItem aboutUsMenuItem;*/


  /*// Tab pane with all Tabs
  @FXML private TabPane tabPane;
  @FXML private Tab scheduleTab;
  @FXML private Tab studentsTab;
  @FXML private Tab classesTab;
  @FXML private Tab coursesTab;
  @FXML private Tab roomsTab;*/

  //Filter Lesson
  @FXML private HBox hBoxFilterLessonSchedule;
  @FXML private ComboBox<Course> courseFilterLessonSchedule;
  @FXML private DatePicker startTimeFilterLessonSchedule;
  @FXML private DatePicker endTimeFilterLessonSchedule;
  @FXML private ComboBox<Room> roomFilterLessonSchedule;
  @FXML private ComboBox<VIAClass> classFilterLessonSchedule;

  //TableView
  @FXML private TableView<Lesson> tableViewSchedule;
  @FXML private TableColumn<Course, String> tableCourseSchedule;
  @FXML private TableColumn<LocalDateTime,String> tableStartTimeSchedule;
  @FXML private TableColumn<LocalDateTime,String> tableEndTimeSchedule;
  @FXML private TableColumn<Room, String> tableRoomSchedule;
  @FXML private TableColumn<Course, String> tableClassSchedule;

  //Add Lesson
  @FXML private VBox vBoxAddLessonSchedule;
  @FXML private ComboBox<Course> selectCourseToAddLessonSchedule;
  @FXML private ComboBox<Room> selectRoomToAddLessonSchedule;
  @FXML private DatePicker startTimeToAddLessonSchedule;
  @FXML private DatePicker endTimeToAddLessonSchedule;
  @FXML private Button buttonToAddLessonSchedule;

  //Remove Lesson
  @FXML private HBox hBoxRemoveLessonSchedule;
  @FXML private Button buttonRemoveLessonSchedule;

  //Schedule Tab
  @FXML private AnchorPane anchorPaneSchedule;
  @FXML private GridPane gridPaneSchedule;

  Schedule schedule = Manager.getSchedule();

  //initialize every data except the table data
  public void initialize()
  {
    //Add course names to the dropdown menus
    ArrayList<Course> courses = new ArrayList<>(
        Manager.getSchedule().getCourseList().getAllCourses());

    selectCourseToAddLessonSchedule.getItems().clear();
    selectCourseToAddLessonSchedule.getItems().addAll(courses);

    courseFilterLessonSchedule.getItems().clear();
    courseFilterLessonSchedule.getItems().addAll(courses);

    //Add rooms to the dropdown menus
    ArrayList<Room> rooms = new ArrayList<>(
        Manager.getSchedule().getRoomList().getAllRooms());

    selectRoomToAddLessonSchedule.getItems().clear();
    selectRoomToAddLessonSchedule.getItems().addAll(rooms);

    roomFilterLessonSchedule.getItems().clear();
    roomFilterLessonSchedule.getItems().addAll(rooms);

    //Add classes to the dropdown menu

    ArrayList<VIAClass> classes = new ArrayList<>(Manager.getSchedule().getVIAClassList().getAllClasses());

    classFilterLessonSchedule.getItems().clear();
    classFilterLessonSchedule.getItems().addAll(classes);


  }

  public void initializeTableData()
  {
    tableViewSchedule.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    tableCourseSchedule.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getCourseName())
    );

    tableRoomSchedule.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getRoomName())
    );

    tableClassSchedule.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getVIAClass().toString())
    );

  }

  public void removeLesson()
  {}


}
