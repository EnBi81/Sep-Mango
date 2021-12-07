package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller
{
  //Main Pain and menu bar
  @FXML private  VBox mainPane;
  @FXML private MenuBar menuBar;
  @FXML private MenuButton fileMenuButton;
  @FXML private MenuButton editMenuButton;
  @FXML private MenuButton helpMenuButton;
  @FXML private MenuItem importMenuItem;
  @FXML private MenuItem exportMenuItem;
  @FXML private MenuItem extendMenuItem;
  @FXML private MenuItem aboutUsMenuItem;


  // Tab pane with all Tabs
  @FXML private TabPane tabPane;
  @FXML private Tab scheduleTab;
  @FXML private Tab studentsTab;
  @FXML private Tab classesTab;
  @FXML private Tab coursesTab;
  @FXML private Tab roomsTab;

  //Schedule Tab
  @FXML private AnchorPane anchorPaneSchedule;
  @FXML private GridPane gridPaneSchedule;

  //Filter Lesson
  @FXML private HBox hBoxFilterLessonSchedule;
  @FXML private ComboBox courseFilterLessonSchedule;
  @FXML private DatePicker startTimeFilterLessonSchedule;
  @FXML private DatePicker endTimeFilterLessonSchedule;
  @FXML private ComboBox roomFilterLessonSchedule;
  @FXML private ComboBox classFilterLessonSchedule;

  //TableView
  @FXML private TableView tableViewSchedule;
  @FXML private TableColumn tableCourseSchedule;
  @FXML private TableColumn tableStartTimeSchedule;
  @FXML private TableColumn tableEndTimeSchedule;
  @FXML private TableColumn tableRoomSchedule;
  @FXML private TableColumn tableClassSchedule;

  //Add Lesson
  @FXML private VBox vBoxAddLessonSchedule;
  @FXML private ComboBox selectCourseToAddLessonSchedule;
  @FXML private ComboBox selectRoomToAddLessonSchedule;
  @FXML private DatePicker startTimeToAddLessonSchedule;
  @FXML private DatePicker endTimeToAddLessonSchedule;
  @FXML private Button buttonToAddLessonSchedule;

  //Remove Lesson
  @FXML private HBox hBoxRemoveLessonSchedule;
  @FXML private Button buttonRemoveLessonSchedule;

}
