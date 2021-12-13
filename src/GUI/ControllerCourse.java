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
import java.util.Locale;

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
      if (!chooseClassCourse.getItems().contains(course.getVIAClass()))
        chooseClassCourse.getItems().add(course.getVIAClass());
    }
    chooseClassCourse.valueProperty().addListener(obj -> refreshTableData());
    chooseECTSCourse.valueProperty().addListener(obj -> refreshTableData());
    textFieldFilterByNameCourse.textProperty().addListener(obj -> refreshTableData());
    textFieldFilterByTeacherCourse.textProperty().addListener(obj -> refreshTableData());
  }

  //pane initialization
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
    //refresh missing
  }

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
    //refresh missing
  }

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

  public void refresh(){

    if (selectCourseCourse == null){
      return;
    }
    refreshStudentList();
    refreshTableData();
    refreshStudentsComboBox();
  }

  public void refreshStudentsComboBox(){
    selectStudentCourse.getItems().clear();

    for (Student student : Manager.getSchedule().getStudentList()
        .getAllStudents())
    {
      if (!selectStudentCourse.getItems().contains(student))
        selectStudentCourse.getItems().add(student);
    }

  }

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
