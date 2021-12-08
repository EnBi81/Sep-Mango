package GUI;

import Model.ExchangeStudent;
import Model.Schedule;
import Model.Student;
import Model.VIAClass;
import ScheduleManager.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

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

@FXML private TableColumn nameStudent;
@FXML private TableColumn idStudent;
@FXML private TableColumn classStudent;
@FXML private TableColumn semesterStudent;
@FXML private TableColumn isExchangeStudent;
@FXML private TableView<Student> tableStudent;

@FXML private Button removeStudent;

Schedule schedule = Manager.getSchedule();



public void initialize()
{
  ArrayList<VIAClass> classes = schedule.getVIAClassList().getAllClasses();
  classToCreateStudent.getItems().addAll(classes);
  classToFindStudent.getItems().addAll(classes);

  String[] semester = {"1","2","3","4","5","6","7"};
  semesterToFindStudent.getItems().addAll(semester);
}

public void creatingStudent (ActionEvent e)
{
  if(nameToCreateStudent.getText().equals(null) || idToCreateStudent.getText().equals(null)|| classToCreateStudent.getSelectionModel().isEmpty())
  {
    buttonToAddStudent.setDisable(false);
  }
  else if(e.getSource() == buttonToAddStudent)
  {
    String name = nameToCreateStudent.getText();
    String idString = idToCreateStudent.getText();
    int id = Integer.parseInt(idString);

    if(isExchangeToCreateStudent.isSelected())
    {
      ExchangeStudent one = new ExchangeStudent(name,id);
      schedule.getStudentList().addStudent(one);
    }
    else
    {
      Student one = new Student(name,id);
      schedule.getStudentList().addStudent(one);
    }

    nameToCreateStudent = null;
    idToCreateStudent = null;
    classToCreateStudent.setSelectionModel(null);
  }
}
}
