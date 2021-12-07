package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class courseControler
{
  @FXML private AnchorPane mainAnchorPaneCourse;
  @FXML private Pane addTeacherPaneCourse;
  @FXML private ComboBox selectCourseInAddTeacherPaneCourse;
  @FXML private ComboBox selectTeacherInAddTeacherPaneCourse;
  @FXML private Button addTeacherCourse;
  @FXML private TableView tableViewCourse;
  @FXML private TableColumn tableColumnNameCourse;
  @FXML private TableColumn tableColumnECTSCourse;
  @FXML private TableColumn tableColumnClassCourse;
  @FXML private TableColumn tableColumnTeachersCourse;
  @FXML private TextField textFieldFilterByNameCourse;
  @FXML private TextField textFieldFilterByTeacherCourse;
  @FXML private Pane addStudentPaneCourse;
  @FXML private ComboBox selectCourseInAddStudentPaneCourse;
  @FXML private ComboBox selectStudentInAddStudentPaneCourse;
  @FXML private Button addStudentCourse;
  @FXML private Pane removeStudentPaneCourse;
  @FXML private ComboBox selectCourseInRemoveStudentPaneCourse;
  @FXML private ComboBox selectStudentInRemoveStudentPaneCourse;
  @FXML private Button removeStudentCourse;
  @FXML private ChoiceBox chooseECTSCourse;
  @FXML private ChoiceBox chooseClassCourse;


}
