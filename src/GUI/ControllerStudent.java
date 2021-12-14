package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A user interface that allows for displaying and modifying information about students in Student tab.
 * @author Agata
 * @version 1.0
 */

public class ControllerStudent extends AbstractController
{

  @FXML private TextField nameToCreateStudent;
  @FXML private TextField idToCreateStudent;
  @FXML private ComboBox<VIAClass> classToCreateStudent;
  @FXML private CheckBox isExchangeToCreateStudent;
  @FXML private Button buttonToAddStudent;

  @FXML private TextField nameToFindStudent;
  @FXML private TextField idToFindStudent;
  @FXML private ComboBox<VIAClass> classToFindStudent;
  @FXML private ComboBox<String> semesterToFindStudent;
  @FXML private CheckBox isExchangeToFindStudent;

  @FXML private TableColumn<Student, String> nameStudent;
  @FXML private TableColumn<Student, String> idStudent;
  @FXML private TableColumn<Student, String> classStudent;
  @FXML private TableColumn<Student, String> semesterStudent;
  @FXML private TableColumn<Student, String> isExchangeStudent;
  @FXML private TableView<Student> tableStudent;

  @FXML private Button removeStudent;

  Schedule schedule = Manager.getSchedule();
  VIAClass selectedClass;
  String selectedSemester;

  /**
   *Initializing ComboBoxes and textFields
   */
  public void initialize()
  {
    if(Manager.getSchedule() == null)
      return;

    schedule = Manager.getSchedule();

    ArrayList<VIAClass> classes = schedule.getVIAClassList().getAllClasses();
    classToCreateStudent.getItems().add(null);
    classToCreateStudent.getItems().addAll(classes);
    classToFindStudent.getItems().add(null);
    classToFindStudent.getItems().addAll(classes);

    ArrayList<String> semester = new ArrayList<>();
    for (int i = 0; i < schedule.getVIAClassList().getAllClasses().size(); i++)
    {
      if (!(semester.contains(
          schedule.getVIAClassList().getAllClasses().get(i).getSemester() + "")))
      {
        semester.add(
            schedule.getVIAClassList().getAllClasses().get(i).getSemester() + "");
      }
    }
    semesterToFindStudent.getItems().add(null);
    semesterToFindStudent.getItems().addAll(semester);
    tableView();

    nameToFindStudent.textProperty().addListener(obj -> refreshTable());
    idToFindStudent.textProperty().addListener(obj -> refreshTable());
    classToFindStudent.valueProperty().addListener(obj->refreshTable());
    semesterToFindStudent.valueProperty().addListener(obj -> refreshTable());
  }

  /**
   * Creating a new Student object with the button
   * @param e event which creating a student
   */
  public void creatingStudent(ActionEvent e)
  {
    if (e.getSource() == buttonToAddStudent)
    {
      if (nameToCreateStudent.getText().isEmpty() || idToCreateStudent.getText()
          .isEmpty() || classToCreateStudent.getValue() == null)
      {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please, fill in all the information");
        alert.show();
        return;
      }
      else
      {
        String name = nameToCreateStudent.getText();
        String idString = idToCreateStudent.getText();
        int id = Integer.parseInt(idString);
        VIAClass viaClass = classToCreateStudent.getValue();
        Student one;

        if (isExchangeToCreateStudent.isSelected())
        {
          one = new ExchangeStudent(name, id);
          schedule.getStudentList().addStudent(one);
        }
        else
        {
          one = new Student(name, id);
          schedule.getStudentList().addStudent(one);
        }

        viaClass.addStudent(one);

        nameToCreateStudent.setText("");
        idToCreateStudent.setText("");
        classToCreateStudent.getSelectionModel().clearSelection();
        isExchangeToCreateStudent.setSelected(false);
        refresh();
      }
    }
    Manager.saveSchedule();
  }

  /**
   * Implementing a table view
   */
  public void tableView()
  {
    nameStudent.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getName()));
    idStudent.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getId() + ""));
    classStudent.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getViaClass() + ""));
    semesterStudent.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getViaClass().getSemester() + ""));
    isExchangeStudent.setCellValueFactory(obj -> {
      if (obj.getValue().isExchange())
      {
        return new SimpleStringProperty("Exchange");
      }
      else
      {
        return new SimpleStringProperty("");
      }
    });

    refresh();
  }

  /**
   * Loading and refreshing a table view
   */
  public void refresh()
  {
    ArrayList<Student> students = new ArrayList<>(
        Manager.getSchedule().getStudentList().getAllStudents());

    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }

  /**
   * Reamoving a Student object from the list of students
   * @param e event which removing a student
   */
  public void removingStudent(ActionEvent e)
  {
    if (e.getSource().equals(removeStudent))
    {
      schedule.getStudentList().getAllStudents()
          .remove(tableStudent.getSelectionModel().getSelectedItem());
      VIAClass viaClass = tableStudent.getSelectionModel().getSelectedItem().getViaClass();
      viaClass.getAllStudents().remove(tableStudent.getSelectionModel().getSelectedItem());

      ArrayList<Course> courses = tableStudent.getSelectionModel().getSelectedItem().getAllCourses();
      for (int i = 0; i < courses.size(); i++)
      {
        courses.get(i).getAllStudents().remove(tableStudent.getSelectionModel().getSelectedItem());
      }

      refresh();
    }
    Manager.saveSchedule();
  }

  /**
   * Updating table view due to filters
   */
  public void refreshTable()
  {
    ArrayList<Student> students = new ArrayList<>(schedule.getStudentList().getAllStudents());

    for (int i = 0; i < students.size(); i++)
    {
      if(!filterStudent(students.get(i)))
      {
        students.remove(i--);
      }
    }
    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }

  /**
   * Checking a filters' condition
   * @param student student object which is checked
   * @return
   */
  public boolean filterStudent(Student student)
  {
    String inputName = nameToFindStudent.getText().toLowerCase(Locale.ROOT);
    String inputId = idToFindStudent.getText();
    VIAClass viaClass = classToFindStudent.getSelectionModel().getSelectedItem();
    String semester = semesterToFindStudent.getValue();

    if(!student.getName().toLowerCase(Locale.ROOT).contains(inputName))
    {
      return false;
    }

    if(!inputId.isEmpty() && !(student.getId()+"").startsWith(inputId))
    {
      return false;
    }

    if(viaClass != null && !(student.getViaClass().equals(viaClass)))
    {
      return false;
    }

    if(semester != null && !(student.getViaClass().getSemester() == Integer.parseInt(semester)))
    {
      return false;
    }

    if(isExchangeToFindStudent.isSelected())
    {
      return student.isExchange();
    }
    return true;
  }
}
