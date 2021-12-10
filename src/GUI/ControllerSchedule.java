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

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerSchedule
{
  //Filter Lesson
  @FXML private HBox hBoxFilterLessonSchedule;
  @FXML private ComboBox<Course> courseFilterLessonSchedule;
  @FXML private TextField startTimeFilterLessonSchedule;
  @FXML private TextField endTimeFilterLessonSchedule;
  @FXML private ComboBox<Room> roomFilterLessonSchedule;
  @FXML private ComboBox<VIAClass> classFilterLessonSchedule;

  //TableView
  @FXML private TableView<Lesson> tableViewSchedule;
  @FXML private TableColumn<Lesson, String> tableCourseSchedule;
  @FXML private TableColumn<Lesson, String> tableStartTimeSchedule;
  @FXML private TableColumn<Lesson, String> tableEndTimeSchedule;
  @FXML private TableColumn<Lesson, String> tableRoomSchedule;
  @FXML private TableColumn<Lesson, String> tableClassSchedule;

  //Add Lesson
  @FXML private VBox vBoxAddLessonSchedule;
  @FXML private ComboBox<Course> selectCourseToAddLessonSchedule;
  @FXML private ComboBox<Room> selectRoomToAddLessonSchedule;
  @FXML private TextField startTimeToAddLessonSchedule;
  @FXML private TextField endTimeToAddLessonSchedule;
  @FXML private Button buttonToAddLessonSchedule;

  //Remove Lesson
  @FXML private HBox hBoxRemoveLessonSchedule;
  @FXML private Button buttonRemoveLessonSchedule;

  //Schedule Tab
  @FXML private AnchorPane anchorPaneSchedule;
  @FXML private GridPane gridPaneSchedule;

  Schedule schedule = Manager.getSchedule();

  private Course selectedCourse;
  private Lesson lesson;
  private Room selectedRoom;
  private VIAClass selectedClass;

  //initialize every data except the table data
  public void initialize()
  {
    //Add course names to the dropdown menus
    ArrayList<Course> courses = new ArrayList<>(
        Manager.getSchedule().getCourseList().getAllCourses());

    selectCourseToAddLessonSchedule.getItems().clear();
    selectCourseToAddLessonSchedule.getItems().addAll(courses);

    courseFilterLessonSchedule.getItems().clear();
    courseFilterLessonSchedule.getItems().add(null);
    courseFilterLessonSchedule.getItems().addAll(courses);

    //Add rooms to the dropdown menus
    ArrayList<Room> rooms = new ArrayList<>(
        Manager.getSchedule().getRoomList().getAllRooms());

    selectRoomToAddLessonSchedule.getItems().clear();
    selectRoomToAddLessonSchedule.getItems().addAll(rooms);

    roomFilterLessonSchedule.getItems().clear();
    roomFilterLessonSchedule.getItems().add(null);
    roomFilterLessonSchedule.getItems().addAll(rooms);

    //Add classes to the dropdown menu

    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    classFilterLessonSchedule.getItems().clear();
    classFilterLessonSchedule.getItems().add(null);
    classFilterLessonSchedule.getItems().addAll(classes);

    selectRoomToAddLessonSchedule.setDisable(true);
    startTimeToAddLessonSchedule.setDisable(true);
    endTimeToAddLessonSchedule.setDisable(true);
    buttonToAddLessonSchedule.setDisable(true);
    buttonRemoveLessonSchedule.setDisable(true);

    initializeTableData();
  }

  public void initializeTableData()
  {

    //has to be tested!!!

    tableViewSchedule.getSelectionModel()
        .setSelectionMode(SelectionMode.SINGLE);

    tableCourseSchedule.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getCourse().getCourseName()));

    tableRoomSchedule.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getFirstRoom().getRoomName()) ////come back here
    );

    tableClassSchedule.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getCourse().getVIAClass().getName()));

    tableStartTimeSchedule.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getStartTime().toString()));
    tableEndTimeSchedule.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getEndTime().toString()));
    ArrayList<Lesson> lessons = Manager.getSchedule().getLessonList()
        .getAllLessons();
    tableViewSchedule.getItems().addAll(lessons);

  }
// remove one
  public LocalDateTime startTimeAddLesson()
  {
    String startTime = startTimeToAddLessonSchedule.getText();

    startTime = startTime.replace(" ", "T");
    //Todo check if it works
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    LocalDateTime start = LocalDateTime.parse(startTime);

    return start;
  }

  public LocalDateTime endTimeAddLesson()
  {
    String endTime = endTimeToAddLessonSchedule.getText();

    endTime = endTime.replace(" ", "T");
    LocalDateTime end = LocalDateTime.parse(endTime);

    return end;
  }

  public LocalDateTime startTimeFilter(String string)
  {
    string = startTimeFilterLessonSchedule.getText();

    string = string.replace(" ", "T");
    LocalDateTime startFilter = LocalDateTime.parse(string);

    return startFilter;
  }

  public LocalDateTime endTimeFilter()
  {
    String endTimeFilter = endTimeFilterLessonSchedule.getText();

    endTimeFilter = endTimeFilter.replace(" ", "T");
    LocalDateTime endFilter = LocalDateTime.parse(endTimeFilter);

    return endFilter;
  }

  public void addALesson()
  {
    //Enable fields one by one
    Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}");

    if (!selectCourseToAddLessonSchedule.getSelectionModel().isEmpty())
    {
      selectRoomToAddLessonSchedule.setDisable(false);
    }

    if (!selectRoomToAddLessonSchedule.getSelectionModel().isEmpty())
    {
      startTimeToAddLessonSchedule.setDisable(false);
    }

    //Check if pattern matches to enable next field
    Matcher matcherS = pattern.matcher(startTimeToAddLessonSchedule.getText());
    boolean matchesStart = matcherS.matches();

    if (matchesStart)
    {
      endTimeToAddLessonSchedule.setDisable(false);
    }
    //CHECK AGAIN FOR THE END TIME
    Matcher matcherE = pattern.matcher(endTimeToAddLessonSchedule.getText());
    boolean matchesEnd = matcherE.matches();

    if (matchesEnd)
    {
      buttonToAddLessonSchedule.setDisable(false);
    }

  }

  public void selectLesson()
  {
    //On mouse click in SceneBuilder

    if(tableViewSchedule.getSelectionModel().getSelectedItem() != null)
    {
      buttonRemoveLessonSchedule.setDisable(false);
    }

  }

  public void removeLesson()
  {

    Lesson lessonToBeRemoved = tableViewSchedule.getSelectionModel()
        .getSelectedItem();

    schedule.getLessonList().removeLesson(lessonToBeRemoved);
    refreshTable();

    //todo : remove from LessonList!!!
    startTimeFilterLessonSchedule.setText(
        schedule.getLessonList().getAllLessons().size() + "");

  }

  public void refreshTable()
  {
    ArrayList<Lesson> lessons = new ArrayList<>(
        Manager.getSchedule().getLessonList().getAllLessons());

    for (int i = 0; i < lessons.size(); i++)
    {
      if(checkData(lessons.get(i)))
        lessons.remove(i--);
    }


    tableViewSchedule.getItems().clear();
    tableViewSchedule.getItems().addAll(lessons);
  }

  public boolean checkData(Lesson lesson)
  {
    selectedCourse = courseFilterLessonSchedule.getValue();
    selectedRoom = roomFilterLessonSchedule.getValue();
    selectedClass = classFilterLessonSchedule.getValue();

    String startTime = startTimeFilterLessonSchedule.getText().replace(" ", "T");
    String endTime = endTimeFilterLessonSchedule.getText().replace(" ", "T");

    if(selectedCourse != null)
    {
      if (lesson.getCourse() != selectedCourse)
      {
        return true;
      }
    }
    if( selectedRoom != null)
    {
      if(lesson.getFirstRoom() != selectedRoom && lesson.getSecondRoom() != selectedRoom) ///come back here
      {
        return true;
      }
    }

    if( selectedClass != null)
    {
      if (lesson.getCourse().getVIAClass() != selectedClass)
      {
        return true;
      }
    }

    if(!lesson.getStartTime().toString().contains(startTime))
    {
      return true;
    }
    if(!lesson.getEndTime().toString().contains(endTime))
    {
      return true;
    }

    return false;
  }


}
