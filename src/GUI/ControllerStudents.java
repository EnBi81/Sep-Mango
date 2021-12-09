package GUI;

import Model.ExchangeStudent;
import Model.Schedule;
import Model.Student;
import Model.VIAClass;
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

public class ControllerStudents
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

@FXML private TableColumn<Student,String> nameStudent;
@FXML private TableColumn<Student,String> idStudent;
@FXML private TableColumn<Student,String> classStudent;
@FXML private TableColumn<Student,String> semesterStudent;
@FXML private TableColumn<Student,String> isExchangeStudent;
@FXML private TableView<Student> tableStudent;

@FXML private Button removeStudent;

Schedule schedule = Manager.getSchedule();
VIAClass selectedClass;
String selectedSemester;


public void initialize()
{
  ArrayList<VIAClass> classes = schedule.getVIAClassList().getAllClasses();
  classToCreateStudent.getItems().add(null);
  classToCreateStudent.getItems().addAll(classes);
  classToFindStudent.getItems().add(null);
  classToFindStudent.getItems().addAll(classes);

  ArrayList<String> semester = new ArrayList<>();
  for (int i = 0; i < schedule.getVIAClassList().getAllClasses().size(); i++)
  {
    if(!(semester.contains(schedule.getVIAClassList().getAllClasses().get(i).getSemester() + "")))
    {
      semester.add(schedule.getVIAClassList().getAllClasses().get(i).getSemester() + "");
    }
  }
  semesterToFindStudent.getItems().add(null);
  semesterToFindStudent.getItems().addAll(semester);
  tableView();
}


public void creatingStudent (ActionEvent e)
{
  if(e.getSource() == buttonToAddStudent)
  {
    if(nameToCreateStudent.getText().isEmpty() || idToCreateStudent.getText().isEmpty() ||
        classToCreateStudent.getValue() == null)
    {
      buttonToAddStudent.setDisable(false);
    }
    else
    {
      String name = nameToCreateStudent.getText();
      String idString = idToCreateStudent.getText();
      int id = Integer.parseInt(idString);
      VIAClass viaClass = classToCreateStudent.getValue();
      Student one;

      if(isExchangeToCreateStudent.isSelected())
      {
        one = new ExchangeStudent(name,id);
        schedule.getStudentList().addStudent(one);
      }
      else
      {
        one = new Student(name,id);
        schedule.getStudentList().addStudent(one);
      }

      viaClass.addStudent(one);

      nameToCreateStudent.setText("");
      idToCreateStudent.setText("");
      classToCreateStudent.getSelectionModel().clearSelection();
      isExchangeToCreateStudent.setSelected(false);
      refreshTable();
    }
  }
}

public void tableView()
{
  nameStudent.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getName()));
  idStudent.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getId()+""));
  classStudent.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getViaClass()+""));
  semesterStudent.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getViaClass().getSemester()+""));
  isExchangeStudent.setCellValueFactory(obj ->
  {
    if (obj.getValue().isExchange())
    {
      return new SimpleStringProperty("Exchange");
    }
    else
    {
      return new SimpleStringProperty("");
    }
  });

  refreshTable();
}

public void refreshTable()
{
    ArrayList<Student> students = new ArrayList<>(Manager.getSchedule().getStudentList().getAllStudents());

    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
}

public void removingStudent(ActionEvent e)
{
  if(e.getSource().equals(removeStudent))
  {
    schedule.getStudentList().getAllStudents().remove(tableStudent.getSelectionModel().getSelectedItem());
    refreshTable();
  }
}

public void filterStudentName(ActionEvent e)
{
  String name = nameToFindStudent.getText();
  ArrayList<Student> students = new ArrayList<>();

  if(!(name.equals(" ")))
  {
    for (int i = 0; i < schedule.getStudentList().getAllStudents().size(); i++)
    {
      if(schedule.getStudentList().getAllStudents().get(i).getName().startsWith(name))
      {
        students.add(schedule.getStudentList().getAllStudents().get(i));
      }
    }
    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }
  else
  {
    refreshTable();
  }
}

  public void filterStudentId(ActionEvent e)
  {
    String id = idToFindStudent.getText();
    ArrayList<Student> students = new ArrayList<>();

    if(!(id.equals("")))
    {
      for (int i = 0; i < schedule.getStudentList().getAllStudents().size(); i++)
      {
        if((schedule.getStudentList().getAllStudents().get(i).getId()+"").startsWith(id))
        {
          students.add(schedule.getStudentList().getAllStudents().get(i));
        }
      }
      tableStudent.getItems().clear();
      tableStudent.getItems().addAll(students);
    }
    else
    {
      refreshTable();
    }
  }

public void filterStudentsClass (ActionEvent e)
{
  SingleSelectionModel<VIAClass> model = classToFindStudent.getSelectionModel();
  selectedClass = model.getSelectedItem();

  ArrayList<Student> students = new ArrayList<>();

  if (selectedClass != null)
  {
    for (int i = 0; i < schedule.getStudentList().getAllStudents().size(); i++)
    {
      if(schedule.getStudentList().getAllStudents().get(i).getViaClass().equals(selectedClass))
      {
        students.add(schedule.getStudentList().getAllStudents().get(i));
      }
    }
    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }
  else
  {
    refreshTable();
  }
}

public void filterStudentSemester(ActionEvent e)
{
  SingleSelectionModel<String> model = semesterToFindStudent.getSelectionModel();
  selectedSemester = model.getSelectedItem();

  ArrayList<Student> students = new ArrayList<>();

  if(selectedSemester != null)
  {
    for (int i = 0; i < schedule.getStudentList().getAllStudents().size(); i++)
    {
      if((schedule.getStudentList().getAllStudents().get(i).getViaClass().getSemester()+"").equals(selectedSemester))
      {
        students.add(schedule.getStudentList().getAllStudents().get(i));
      }
    }
    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }
  else
  {
    refreshTable();
  }
}

public void isExchange(ActionEvent e)
{
  ArrayList<Student> students = new ArrayList<>();

  if(isExchangeToFindStudent.isSelected())
  {
    for (int i = 0; i < schedule.getStudentList().getAllStudents().size(); i++)
    {
      if (schedule.getStudentList().getAllStudents().get(i).isExchange())
      {
        students.add(schedule.getStudentList().getAllStudents().get(i));
      }
    }
    tableStudent.getItems().clear();
    tableStudent.getItems().addAll(students);
  }
  else
  {
    refreshTable();
  }
}

}
