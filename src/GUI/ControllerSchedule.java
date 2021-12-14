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
import utils.OverlappingCheck;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerSchedule extends AbstractController
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
  @FXML private HBox bookSecondAddLessonSchedule;
  @FXML private CheckBox checkBoxToAddLessonSchedule;

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
  private String selectedStart;
  private String selectedEnd;
  private LocalDateTime from;
  private LocalDateTime to;

  Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}");

  /**
   * @author Uafa
   * @version 1.0
   */

  /**
   * Puts the initial data about courses and classes in the Combo boxes used for
   * adding a lesson and filtering trough lessons. Also calls the refreshComboBox
   * and initializeTableData methods.
   */
  public void initialize()
  {
    if (Manager.getSchedule() == null)
      return;

    //Add course names to the dropdown menus
    ArrayList<Course> courses = new ArrayList<>(
        Manager.getSchedule().getCourseList().getAllCourses());

    selectCourseToAddLessonSchedule.getItems().clear();
    selectCourseToAddLessonSchedule.getItems().addAll(courses);

    courseFilterLessonSchedule.getItems().clear();
    courseFilterLessonSchedule.getItems().add(null);
    courseFilterLessonSchedule.getItems().addAll(courses);

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
    bookSecondAddLessonSchedule.setDisable(true);

    refreshRoomComboBox();
    initializeTableData();
  }

  /**
   * Using lambda expressions loads the lesson data into the table. Checks if a
   * lesson has two booked rooms and displays the start and end time in a
   * specified way.
   */
  public void initializeTableData()
  {
    this.tableViewSchedule.getSelectionModel()
        .setSelectionMode(SelectionMode.SINGLE);

    this.tableCourseSchedule.setCellValueFactory((obj) -> {
      return new SimpleStringProperty(
          ((Lesson) obj.getValue()).getCourse().getCourseName());
    });
    this.tableRoomSchedule.setCellValueFactory((obj) -> {
      Lesson lesson = (Lesson) obj.getValue();
      String columnText = lesson.getFirstRoom().getRoomName();
      if (lesson.getSecondRoom() != null)
      {
        columnText = columnText + ", " + lesson.getSecondRoom().getRoomName();
      }

      return new SimpleStringProperty(columnText);
    });
    this.tableClassSchedule.setCellValueFactory((obj) -> {
      return new SimpleStringProperty(
          ((Lesson) obj.getValue()).getCourse().getVIAClass().getName());
    });
    this.tableStartTimeSchedule.setCellValueFactory((obj) -> {
      return new SimpleStringProperty(
          ((Lesson) obj.getValue()).getStartTime().toString()
              .replace("T", " "));
    });
    this.tableEndTimeSchedule.setCellValueFactory((obj) -> {
      return new SimpleStringProperty(
          ((Lesson) obj.getValue()).getEndTime().toString().replace("T", " "));
    });
    ArrayList<Lesson> lessons = Manager.getSchedule().getLessonList()
        .getAllLessons();
    this.tableViewSchedule.getItems().addAll(lessons);
  }

  /**
   * Calls the refreshComboBox and refreshTable methods.
   * (an abstract method from the AbstractController class)
   */
  public void refresh()
  {
    refreshRoomComboBox();
    refreshTable();

  }

  /**
   * Adds rooms data to combo boxes for adding a lesson and filtering through
   * lessons. (Done separately from the initialize method because this method
   * has to be called in the refresh method to update room data)
   */
  public void refreshRoomComboBox()
  {
    //Add rooms to the dropdown menus
    ArrayList<Room> rooms = new ArrayList<>(
        Manager.getSchedule().getRoomList().getAllRooms());

    roomFilterLessonSchedule.getItems().clear();
    roomFilterLessonSchedule.getItems().add(null);
    roomFilterLessonSchedule.getItems().addAll(rooms);

    selectRoomToAddLessonSchedule.getItems().clear();
    selectRoomToAddLessonSchedule.getItems().addAll(rooms);

  }

  public void getAvailableRooms(LocalDateTime from, LocalDateTime to)
  {
    ArrayList<Room> availableRooms = new ArrayList<>(
        Manager.getSchedule().getRoomList().getAllAvailableRooms(from, to));

    selectRoomToAddLessonSchedule.getItems().clear();
    selectRoomToAddLessonSchedule.getItems().addAll(availableRooms);
  }

  /**
   * Firstly, if a value for course is selected enables the room selection
   * combobox. Then checks if this course's class has a preferred room, if yes
   * automatically selects it, if not allows the user can choose from the room
   * list.Afterwards, checks if the selected room has a connected room, if yes
   * then enables a checkbox which allows the booking of two rooms at the same
   * time. Next checks if the room capacity is bigger then the classes size, if
   * not displays an error message. Once a fitting room has been selected the
   * start time text field is enabled. The user input is compared to a specified
   * pattern and if it matches the end time field is enabled. Again checks for
   * the correct pattern and once it is entered the Add Lesson button is enabled.
   */

  public void checkCourseNTime()
  {
    //Enable fields one by one

    if (!selectCourseToAddLessonSchedule.getSelectionModel().isEmpty())
    {
      selectedCourse = selectCourseToAddLessonSchedule.getSelectionModel()
          .getSelectedItem();

      startTimeToAddLessonSchedule.setDisable(false);

    }

    if (checkPattern(startTimeToAddLessonSchedule.getText()) && checkDayNMonth(
        startTimeToAddLessonSchedule.getText()))
    {
      endTimeToAddLessonSchedule.setDisable(false);

      from = timeFilter(startTimeToAddLessonSchedule.getText());

      if (checkPattern(endTimeToAddLessonSchedule.getText()) && checkDayNMonth(endTimeToAddLessonSchedule.getText()))
      {
        to = timeFilter(endTimeToAddLessonSchedule.getText());
        if (from.getHour() < 6 || to.getHour() > 19)
        {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Time");
          alert.setHeaderText("You can only add lessons between 6am and 7pm!");
          alert.setContentText("Please set different hours!");

          alert.showAndWait();
        }
        else if (from.getYear() != to.getYear()
            || from.getMonth() != to.getMonth()
            || from.getDayOfMonth() != to.getDayOfMonth())
        {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Date");
          alert.setHeaderText("Those lessons have different days!");
          alert.setContentText("Please check the dates again!");

          alert.showAndWait();
        }
        else if (from.isAfter(to))
        {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Time");
          alert.setHeaderText("These lessons have invalid start and end time");
          alert.setContentText("Please check again");

          alert.showAndWait();
        }
        else
        {
          selectedCourse = selectCourseToAddLessonSchedule.getSelectionModel()
              .getSelectedItem();
          getAvailableRooms(from, to);

          if (selectedCourse.getVIAClass().getPreferredRoom() == null)
          {
            selectRoomToAddLessonSchedule.setDisable(false);
          }
          else
          {
            selectRoomToAddLessonSchedule.getSelectionModel()
                .select(selectedCourse.getVIAClass().getPreferredRoom());
            selectedRoom = selectRoomToAddLessonSchedule.getSelectionModel()
                .getSelectedItem();
          }

        }
      }
    }

  }

  public void checkRooms()
  {

    selectedCourse = selectCourseToAddLessonSchedule.getSelectionModel()
        .getSelectedItem();

    selectedRoom = selectRoomToAddLessonSchedule.getSelectionModel()
        .getSelectedItem();

    if (selectedRoom != null && selectedRoom.hasConnectedRoom())
    {
      bookSecondAddLessonSchedule.setDisable(false);
    }
    else
    {
      bookSecondAddLessonSchedule.setDisable(true);
    }

    if (selectedRoom != null
        && selectedRoom.getCapacity() < selectedCourse.getAllStudents().size())
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Room Capacity");
      alert.setHeaderText("The room capacity is not enough for this class!");
      alert.setContentText("Choose another room!");

      alert.showAndWait();
    }
    else
    {
      buttonToAddLessonSchedule.setDisable(false);
    }
  }

  public boolean checkPattern(String string)
  {

    //Check if pattern matches to enable next field
    Matcher matcherS = pattern.matcher(string);
    return matcherS.matches();
  }

  //is overlapping runs here

  /**
   * Firstly, checks if any of the values is null and whether the start and end
   * time Strings are entered in the correct format if no displays an error
   * message if yes continues to :
   * First, the selected from the user values for a course, room, start time
   * and end time. Afterwards, creates a lesson by inserting those values in the
   * createLesson method, which is located in the Course class and is called by
   * the selected course value. When the start and end time values are inserted
   * the timeFilterStart and timeFilterEnd methods are called to turn the String
   * into a LocalDateTime object. Next it checks if the checkbox for booking a
   * second room is selected and if it is calls the setSecondRoom method to
   * assign the connected room as a second room. In the end refreshes the whole
   * tab, empties the text fields and deselects the checkbox if necessary.
   */
  public void createLessonButton()
  {

    Matcher start = pattern.matcher(startTimeToAddLessonSchedule.getText());
    boolean matchesStart = start.matches();

    Matcher matcherE = pattern.matcher(endTimeToAddLessonSchedule.getText());
    boolean matchesEnd = matcherE.matches();

    this.selectedCourse = (Course) this.selectCourseToAddLessonSchedule.getSelectionModel()
        .getSelectedItem();
    this.selectedRoom = (Room) this.selectRoomToAddLessonSchedule.getSelectionModel()
        .getSelectedItem();
    this.selectedStart = this.startTimeToAddLessonSchedule.getText();
    this.selectedEnd = this.endTimeToAddLessonSchedule.getText();

    LocalDateTime from = timeFilter(selectedStart);
    LocalDateTime to = timeFilter(selectedEnd);

    if (selectedRoom == null || selectedCourse == null || !matchesStart
        || !matchesEnd)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Invalid data");
      alert.setHeaderText("Data is missing or is invalid");
      alert.setContentText("Please check again!");

      alert.showAndWait();
    }
    else if (OverlappingCheck.isOverlappingLesson(selectedCourse, from, to))
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Overlapping");
      alert.setHeaderText("Lessons at the specified time are overlapping!");
      alert.setContentText("Please check the data again");

      alert.showAndWait();
    }
    else
    {

      Lesson lesson = this.selectedCourse.createLesson(this.selectedCourse,
          this.selectedRoom, from, to);

      if (this.checkBoxToAddLessonSchedule.isSelected())
      {
        Room secondRoom = this.selectedRoom.getConnectedRoom();
        lesson.setSecondRoom(secondRoom);
      }

      this.refresh();
      Manager.saveSchedule();

      this.checkBoxToAddLessonSchedule.setSelected(false);
      this.bookSecondAddLessonSchedule.setDisable(true);
      this.startTimeToAddLessonSchedule.setText("");
      this.endTimeToAddLessonSchedule.setText("");
    }

  }

  /**
   * Used to enable remove lesson button after a lesson has been selected from
   * the table.
   */
  public void selectLesson()
  {
    //On mouse click in SceneBuilder

    if (tableViewSchedule.getSelectionModel().getSelectedItem() != null)
    {
      buttonRemoveLessonSchedule.setDisable(false);
    }

  }

  /**
   * Takes the lesson object selected by the user and removes it from the
   * LessonList stored in the Schedule. Then calls the refreshTable method
   * so only lessons that are in the LessonList will be shown.
   */
  public void removeLesson()
  {

    Lesson lessonToBeRemoved = tableViewSchedule.getSelectionModel()
        .getSelectedItem();

    schedule.getLessonList().removeLesson(lessonToBeRemoved);

    lessonToBeRemoved.getCourse().removeLesson(lessonToBeRemoved);

    refreshTable();
    Manager.saveSchedule();

  }

  /**
   * Goes through the LessonList, calls the checkData method and displays
   * the lessons that correspond to the values selected in the filtering
   * text fields and combo boxes.
   */
  public void refreshTable()
  {
    ArrayList<Lesson> lessons = new ArrayList<>(
        Manager.getSchedule().getLessonList().getAllLessons());

    for (int i = 0; i < lessons.size(); i++)
    {
      if (checkData(lessons.get(i)))
        lessons.remove(i--);
    }

    tableViewSchedule.getItems().clear();
    tableViewSchedule.getItems().addAll(lessons);
  }

  /**
   * Used for filtering lessons. First gets the values for selected course,
   * class and/or room, then takes the text from the start and end time text
   * fields and calls the replace method to ensure the string will match the
   * LocalDateTime format. Afterwards, runs through the lessons parameters and
   * compares them to the ones chosen from the user. if all parameters match
   * returns false, else returns true.
   *
   * @return true if parameters do not match, false if parameters match
   */
  public boolean checkData(Lesson lesson)
  {
    selectedCourse = courseFilterLessonSchedule.getValue();
    selectedRoom = roomFilterLessonSchedule.getValue();
    selectedClass = classFilterLessonSchedule.getValue();

    String startTime = startTimeFilterLessonSchedule.getText()
        .replace(" ", "T");
    String endTime = endTimeFilterLessonSchedule.getText().replace(" ", "T");

    if (selectedCourse != null)
    {
      if (lesson.getCourse() != selectedCourse)
      {
        return true;
      }
    }
    if (selectedRoom != null)
    {
      if (lesson.getFirstRoom() != selectedRoom
          && lesson.getSecondRoom() != selectedRoom)
      {
        return true;
      }
    }

    if (selectedClass != null)
    {
      if (lesson.getCourse().getVIAClass() != selectedClass)
      {
        return true;
      }
    }

    if (!lesson.getStartTime().toString().contains(startTime))
    {
      return true;
    }
    if (!lesson.getEndTime().toString().contains(endTime))
    {
      return true;
    }

    return false;
  }

  /**
   * Returns a LocalDateTime object, which is created from the string input by
   * the user. The returned object is set to be the start time for a lesson.
   *
   * @param string takes user's input
   * @return returns a LocalDateTime object created by converting user's input
   */
  public LocalDateTime timeFilter(String string)
  {
    string = string.replace(" ", "T");

    return LocalDateTime.parse(string);

  }

  public boolean checkDayNMonth(String string)
  {
    String strYear = string.charAt(0) + "" + string.charAt(1) + string.charAt(2)
        + string.charAt(3);
    String strMonth = string.charAt(5) + "" + string.charAt(6);
    String strDay = string.charAt(8) + "" + string.charAt(9);
    String strHour = string.charAt(11) + "" + string.charAt(12);
    String strMinutes = string.charAt(14) + "" + string.charAt(15);

    int month = Integer.parseInt(strMonth);
    int day = Integer.parseInt(strDay);
    int minutes = Integer.parseInt(strMinutes);
    int year = Integer.parseInt(strYear);

    if (month > 12)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Invalid data");
      alert.setHeaderText("Entered date is invalid!");
      alert.setContentText("Please check the month data again");

      alert.showAndWait();
      return false;
    }
    else
    {
      LocalDate date = LocalDate.of(year, month, 1);
      int days = date.lengthOfMonth();
      if (day > days)
      {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Entered date is invalid!");
        alert.setContentText("Please check the day data again");

        alert.showAndWait();
        return false;
      }
      else if (minutes > 59)
      {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Entered date is invalid!");
        alert.setContentText("Please check the minutes data again");

        alert.showAndWait();
        return false;
      }
      else
      {
        return true;
      }
    }
  }
}
