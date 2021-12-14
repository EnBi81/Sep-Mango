package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.OverlappingCheck;

import java.util.ArrayList;
import java.util.Locale;

/**
 * a class which enables the functionality of the GUI
 *
 * @author Simon Mayer
 * @version 1.0
 */
public class ControllerCourse extends AbstractController
{
  @FXML private ComboBox<Course> selectCourseCourse;
  @FXML private ComboBox<Teacher> selectTeacherCourse;
  @FXML private Button addTeacherCourse;
  @FXML private Button removeTeacherCourse;
  @FXML private TableView<Course> tableViewCourse;
  @FXML private TableColumn<Course, String> tableColumnNameCourse;
  @FXML private TableColumn<Course, String> tableColumnECTSCourse;
  @FXML private TableColumn<Course, String> tableColumnClassCourse;
  @FXML private TableColumn<Course, String> tableColumnTeachersCourse;
  @FXML private TextField textFieldFilterByNameCourse;
  @FXML private TextField textFieldFilterByTeacherCourse;
  @FXML private ComboBox<Student> selectStudentCourse;
  @FXML private Button addStudentCourse;
  @FXML private Button removeStudentCourse;
  @FXML private ComboBox<String> chooseECTSCourse;
  @FXML private ComboBox<VIAClass> chooseClassCourse;
  @FXML private ListView<Student> listOfStudents;

  // methods

  // main initialization
  /**
   * loads all the data about courses into the table, and combo boxes
   */
  public void initialize()
  {
    if(Manager.getSchedule() == null)
      return;

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
    initializeTeacherPane();
    initializeStudentPane();
    comboBoxSelectInitialization();
  }

  /**
   * converts an array list of teachers into string so that it is possible to display them in the table
   * @param teachers array list of teachers in the selected course
   * @return string with teacher's name
   */
  public String teachersToString(ArrayList<Teacher> teachers)
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
  /**
   * loads course's data into combo boxes in the filter section
   * sets listeners for refreshing table whenever there is a change in the filters (both text fields and combo boxes)
   */
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
      if (!chooseClassCourse.getItems().contains(course.getVIAClass()))
        chooseClassCourse.getItems().add(course.getVIAClass());
    }
    chooseClassCourse.valueProperty().addListener(obj -> refreshTableData());
    chooseECTSCourse.valueProperty().addListener(obj -> refreshTableData());
    textFieldFilterByNameCourse.textProperty().addListener(obj -> refreshTableData());
    textFieldFilterByTeacherCourse.textProperty().addListener(obj -> refreshTableData());
  }

  //pane initialization
  /**
   * loads all courses and teachers into combo boxes on the left side of GUI
   * that means the add/remove section
   * disables select/add/remove teacher
   */
  private void initializeTeacherPane(){
    for (Course course : Manager.getSchedule().getCourseList()
        .getAllCourses())
    {
      if (!selectCourseCourse.getItems().contains(course))
        selectCourseCourse.getItems().add(course);
    }
    for (Teacher teacher : Manager.getSchedule().getTeacherList()
        .getAllTeachers())
    {
      if (!selectTeacherCourse.getItems().contains(teacher))
        selectTeacherCourse.getItems().add(teacher);
    }

    selectTeacherCourse.setDisable(true);
    addTeacherCourse.setDisable(true);
    removeTeacherCourse.setDisable(true);
  }

  /**
   * loads all the students into combo box on the left side of GUI
   * that means the add/remove section
   * disables select/add/remove student
   */
  public void initializeStudentPane(){
    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentCourse.getItems().contains(student))
        selectStudentCourse.getItems().add(student);
    }

    selectStudentCourse.setDisable(true);
    addStudentCourse.setDisable(true);
    removeStudentCourse.setDisable(true);
  }

  /**
   * selects course in combo box if selected in table
   * enables select teacher and select student when course selected
   * enables add/remove student when student selected
   * enables add/remove teacher when teacher selected
   */
  public void comboBoxSelectInitialization(){
    tableViewCourse.getSelectionModel().selectedItemProperty()
        .addListener((obs,oldCourse, newCourse) ->
          selectCourseCourse.setValue(newCourse)
        );

    selectCourseCourse.valueProperty().addListener(obj -> {
      selectStudentCourse.setDisable(false);
      selectTeacherCourse.setDisable(false);
      refreshStudentList();
    });

    selectTeacherCourse.valueProperty().addListener(obj -> {
      addTeacherCourse.setDisable(false);
      removeTeacherCourse.setDisable(false);
    });

    selectStudentCourse.valueProperty().addListener(obj -> {
      addStudentCourse.setDisable(false);
      removeStudentCourse.setDisable(false);
    });

  }

  /**
   * checks if course is selected, if not and other action is possible, displays a warning alert
   * else check which button was clicked, gets the value from the corresponding combo box
   * adds or removes corresponding object to/from course
   * when adding checks for overlapping
   * if overlapping returns a warning alert, else executes the function
   * refreshes table after executing function
   * @param e button which has been clicked
   */
  public void handleClickMe(ActionEvent e)
  {
    Course course = selectCourseCourse.getValue();
    if (course == null)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No course selected.");
      alert.setHeaderText("Please select a course in order to proceed.");
      alert.setContentText("Have a nice day");

      alert.showAndWait();
    }
    else
    {

      if (e.getSource() == addTeacherCourse)
      {
        Teacher teacher = selectTeacherCourse.getValue();
        if (OverlappingCheck.isOverlapping(course, teacher))
        {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Collision of lessons");
          alert.setHeaderText(
              "Lessons of the selected teacher are colliding with the lessons of the selected course");
          alert.setContentText("Have a nice day");

          alert.showAndWait();
        }
        else
        {
          course.addTeacher(teacher);
          Manager.saveSchedule();
        }
      }

      else if (e.getSource() == removeTeacherCourse)
      {
        Teacher teacher = selectTeacherCourse.getValue();
        course.removeTeacher(teacher);
        Manager.saveSchedule();
      }

      else if (e.getSource() == addStudentCourse)
      {
        Student student = selectStudentCourse.getValue();
        if (OverlappingCheck.isOverlapping(course, student))
        {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Collision of lessons");
          alert.setHeaderText(
              "Lessons of the selected student are colliding with the lessons of the selected course");
          alert.setContentText("Have a nice day");

          alert.showAndWait();
        }
        else
        {
          course.addStudent(student);
          Manager.saveSchedule();
        }

        refreshStudentList();
      }

      else if (e.getSource() == removeStudentCourse)
      {
        Student student = selectStudentCourse.getValue();
        course.removeStudent(student);
        refreshStudentList();
        Manager.saveSchedule();
      }

      refreshTableData();
    }
  }

  /**
   * if course not selected does not do anything
   * else updates table, combo box with students and list of students
   */
  public void refresh(){

    if (selectCourseCourse == null){
      return;
    }
    refreshStudentList();
    refreshTableData();
    refreshStudentsComboBox();
  }

  /**
   * clears the combo box with students and loads the new student list
   * if student already in the list, skips the adding of this student
   */
  public void refreshStudentsComboBox(){
    selectStudentCourse.getItems().clear();

    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentCourse.getItems().contains(student))
        selectStudentCourse.getItems().add(student);
    }

  }

  /**
   * clears the list of students and loads the new student list
   * if course not selected does not do anything
   */
  public void refreshStudentList(){
    listOfStudents.getItems().clear();

    if (selectCourseCourse.getValue() == null)
    {
      return;
    }
    ArrayList<Student> students = selectCourseCourse.getValue().getAllStudents();
    listOfStudents.getItems().addAll(students);
  }

  /**
   * keeps the table updated
   * gets a copy of the courses from course list
   * checks for filters and removes all courses that does not correspond
   * clears the whole table and adds the new list of courses
   */
  private void refreshTableData(){
    ArrayList<Course> courses = new ArrayList<>(Manager.getSchedule().getCourseList().getAllCourses());

    for (int i = 0; i < courses.size(); i++)
    {
      if (checkRemoveData(courses.get(i))){
        courses.remove(i--);
      }
    }

    tableViewCourse.getItems().clear();
    tableViewCourse.getItems().addAll(courses);
  }

  /**
   * gets all values from filters
   * if course is null does not do anything
   * removes all courses which does not correspond with the filter values
   * @param course course that is compared with the filter values
   * @return true if course should be removed, false if not
   */
  private boolean checkRemoveData(Course course){
    String valueFilterName = textFieldFilterByNameCourse.getText().toLowerCase(
        Locale.ROOT);
    String valueFilterECTS = chooseECTSCourse.getValue();
    VIAClass valueFilterClass = chooseClassCourse.getValue();
    String valueFilterTeacher = textFieldFilterByTeacherCourse.getText().toLowerCase(
        Locale.ROOT);

    if (course == null){
      return false;
    }

    if (!course.getCourseName().toLowerCase(Locale.ROOT).contains(valueFilterName)){
      return true;
    }

    try{
      //is necessary, otherwise nullPointer and Course tab cannot be clicked
      valueFilterECTS = valueFilterECTS.toLowerCase(Locale.ROOT);
      if (!(Integer.parseInt(valueFilterECTS) == course.getEcts())){
        return true;
      }
    }
    catch (NullPointerException e){}



    if (valueFilterClass != null){
      if (!(valueFilterClass.equals(course.getVIAClass()))){
        return true;
      }
    }


    if (valueFilterTeacher.isEmpty() && course.getAllTeachers().size() == 0){
      return false;
    }

    for (Teacher teacher: course.getAllTeachers()
         )
    {
      if (teacher.getName().toLowerCase(Locale.ROOT).contains(valueFilterTeacher)){
        return false;
      }
    }
    return true;
  }


}
