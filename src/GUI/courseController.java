package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class courseController
{
  @FXML private AnchorPane mainAnchorPaneCourse;
  @FXML private Pane addTeacherPaneCourse;
  @FXML private ComboBox<String> selectCourseInAddTeacherPaneCourse;
  @FXML private ComboBox<String> selectTeacherInAddTeacherPaneCourse;
  @FXML private Button addTeacherCourse;
  @FXML private TableView<Course> tableViewCourse;
  @FXML private TableColumn<Course, String> tableColumnNameCourse;
  @FXML private TableColumn<Course, String> tableColumnECTSCourse;
  @FXML private TableColumn<Course, String> tableColumnClassCourse;
  @FXML private TableColumn<Course, String> tableColumnTeachersCourse;
  @FXML private TextField textFieldFilterByNameCourse;
  @FXML private TextField textFieldFilterByTeacherCourse;
  @FXML private Pane addStudentPaneCourse;
  @FXML private ComboBox<String> selectCourseInAddStudentPaneCourse;
  @FXML private ComboBox<String> selectStudentInAddStudentPaneCourse;
  @FXML private Button addStudentCourse;
  @FXML private Pane removeStudentPaneCourse;
  @FXML private ComboBox<String> selectCourseInRemoveStudentPaneCourse;
  @FXML private ComboBox<String> selectStudentInRemoveStudentPaneCourse;
  @FXML private Button removeStudentCourse;
  @FXML private ComboBox<String> chooseECTSCourse;
  @FXML private ComboBox<String> chooseClassCourse;

  Course selectedCourse;

  // methods

  //tryout - delete afterwards
  public void handleClickMe(ActionEvent e)
  {
    System.out.println("Hi");
  }

  // main initialization
  public void initialize()
  {
    tableViewCourse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    //set up the columns
    tableColumnNameCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getCourseName()));
    tableColumnECTSCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getEcts() + ""));
    //VIAClass produces nullPointer bad method in Course;
    tableColumnClassCourse.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getVIAClass().getName()));
    // get teachers from ArrayList to String

    tableColumnTeachersCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(
            teachersToString(obj.getValue().getAllTeachers())));

    //load data
    ArrayList<Course> courses = new ArrayList<>();
    courses = Manager.getSchedule().getCourseList().getAllCourses();
    tableViewCourse.getItems().addAll(courses);

    initializeFilterSide();
    initializeAddTeacherPane();
    initializeAddStudentPane();
    initializeRemoveStudentPane();
  }

  //dont judge me this is the easiest way I was able to come with lol
  public String teachersToString(ArrayList<Model.Teacher> teachers)
  {
    String str = "";

    for (int i = 0; i < teachers.size(); i++)
    {
      str += teachers.get(i).getName();
      if (i != teachers.size() - 1)
      {
        str += ", ";
      }
    }
    return str;
  }

  // filter initialization
  private void initializeFilterSide()
  {
    chooseECTSCourse.getItems().add(null);
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!chooseECTSCourse.getItems().contains(course.getEcts() + ""))
        chooseECTSCourse.getItems().add(course.getEcts() + "");
    }
    chooseClassCourse.getItems().add(null);
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!chooseClassCourse.getItems().contains(course.getVIAClass().getName()))
        chooseClassCourse.getItems().add(course.getVIAClass().getName());
    }
    /*chooseClassCourse.valueProperty().addListener(obj -> refreshTableData());
    chooseECTSCourse.valueProperty().addListener(obj -> refreshTableData());
    textFieldFilterByNameCourse.textProperty().addListener(obj -> refreshTableData());
    textFieldFilterByTeacherCourse.textProperty().addListener(obj -> refreshTableData());*/
  }

  //pane initialization
  private void initializeAddTeacherPane(){
    selectCourseInAddTeacherPaneCourse.getItems().add(null);
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!selectCourseInAddTeacherPaneCourse.getItems().contains(course.getCourseName()))
        selectCourseInAddTeacherPaneCourse.getItems().add(course.getCourseName());
    }
    selectTeacherInAddTeacherPaneCourse.getItems().add(null);
    for (Teacher teacher : Manager.getSchedule().getTeacherList()
        .getAllTeachers())
    {
      if (!selectTeacherInAddTeacherPaneCourse.getItems().contains(teacher.getName()))
        selectTeacherInAddTeacherPaneCourse.getItems().add(teacher.getName());
    }

    addTeacherCourse.setDisable(true);
    //refresh missing
  }

  public void initializeAddStudentPane(){
    selectCourseInAddStudentPaneCourse.getItems().add(null);
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!selectCourseInAddStudentPaneCourse.getItems().contains(course.getCourseName()))
        selectCourseInAddStudentPaneCourse.getItems().add(course.getCourseName());
    }
    selectStudentInAddStudentPaneCourse.getItems().add(null);
    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentInAddStudentPaneCourse.getItems().contains(student.getName()))
        selectStudentInAddStudentPaneCourse.getItems().add(student.getName());
    }

    addStudentCourse.setDisable(true);
    //refresh missing
  }

  public void initializeRemoveStudentPane(){
    selectCourseInRemoveStudentPaneCourse.getItems().add(null);
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!selectCourseInRemoveStudentPaneCourse.getItems().contains(course.getCourseName()))
        selectCourseInRemoveStudentPaneCourse.getItems().add(course.getCourseName());
    }
    selectStudentInRemoveStudentPaneCourse.getItems().add(null);
    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentInRemoveStudentPaneCourse.getItems().contains(student.getName()))
        selectStudentInRemoveStudentPaneCourse.getItems().add(student.getName());
    }

    removeStudentCourse.setDisable(true);
    //refresh missing
  }

}
