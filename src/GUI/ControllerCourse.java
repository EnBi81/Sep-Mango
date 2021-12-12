package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Locale;

/**
 * a class enabling the functionality of GUI, that means the table, all the combo boxes, buttons, and text fields
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
   * sets up the table and loads all the course's data to the table, and combo boxes
   */
  public void initialize()
  {
    tableViewCourse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    //set up the table columns
    tableColumnNameCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getCourseName()));
    tableColumnECTSCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getEcts() + ""));
    tableColumnClassCourse.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getVIAClass().getName()));
    tableColumnTeachersCourse.setCellValueFactory(
        obj -> new SimpleStringProperty(
            teachersToString(obj.getValue().getAllTeachers())));

    //load data
    ArrayList<Course> courses = new ArrayList<>();
    courses = Manager.getSchedule().getCourseList().getAllCourses();
    tableViewCourse.getItems().addAll(courses);

    initializeFilterSide();
    initializeActionPane();
    comboBoxSelectInitialization();
  }

  // get teachers from ArrayList to String
  /**
   * converts an array list of teachers into a String, so that it can be displayed in the table
   * @param teachers list containing all the teachers teaching the course
   * @return a String of teachers, which is displayed in the table view in GUI
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
   * loads all the data to the filter combo boxes and calls the refreshTableData() every time value changes in any filter
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

  /**
   * loads teachers and students to combo boxes for adding and removing teacher and students. Also sets combo boxes with teachers and students disabled as well as the add and remove buttons for both options.
   */
  private void initializeActionPane(){
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
    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentCourse.getItems().contains(student))
        selectStudentCourse.getItems().add(student);
    }

    selectTeacherCourse.setDisable(true);
    addTeacherCourse.setDisable(true);
    removeTeacherCourse.setDisable(true);
    selectStudentCourse.setDisable(true);
    addStudentCourse.setDisable(true);
    removeStudentCourse.setDisable(true);
  }

  /**
   * if course selected in table, selects in combo box as well. Enables other options if some value in combo boxes selected.
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
   * checks which button was clicked.
   * If Add Teacher, adds teacher to the course
   * If Remove Teacher, removes teacher from the course
   * If Add Student, adds student to the course
   * If Remove Student, removes student from the course
   * refreshes data in the table view after the changes
   * @param e a button that has been clicked in the GUI
   */
  public void handleClickMe(ActionEvent e)
  {
    Course course = selectCourseCourse.getValue();

    if (e.getSource() == addTeacherCourse){
      Teacher teacher = selectTeacherCourse.getValue();
      course.addTeacher(teacher);
    }

    else if (e.getSource() == removeTeacherCourse){
      Teacher teacher = selectTeacherCourse.getValue();
      course.removeTeacher(teacher);
    }

    else if (e.getSource() == addStudentCourse){
      Student student = selectStudentCourse.getValue();
      course.addStudent(student);
      refreshStudentList();
    }

    else if (e.getSource() == removeStudentCourse){
      Student student = selectStudentCourse.getValue();
      course.removeStudent(student);
      refreshStudentList();
    }

    refreshTableData();
  }

  /**
   * calls all the refresh methods, if null does nothing.
   * is an abstract method in AbstractController and can be called in the ControllerMain, thanks to that all the tabs will be refreshed after every change.
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
   * refreshes combo box with students (needs to be applied because of the add student tab)
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
   * loads all students from the course into the list
   */
  public void refreshStudentList(){
    listOfStudents.getItems().clear();

    if (selectCourseCourse.getValue() == null)
    {
      return;
    }
    ArrayList<Student> students = selectCourseCourse.getValue().getAllStudents();
    for (var s : students)
    listOfStudents.getItems().addAll(students);
  }

  /**
   * first clears the whole table, then gets all the courses and their data and loads them into the table
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
   * checks all filters and removes all the courses, which have not been filtered, from the table
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
