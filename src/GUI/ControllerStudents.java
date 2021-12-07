package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;

public class ControllerStudents
{
@FXML private TextField nameToCreateStudent;
@FXML private TextField idToCreateStudent;
@FXML private ComboBox classToCreateStudent;
@FXML private CheckBox isExchangeToCreateStudent;
@FXML private Button buttonToAddStudent;

@FXML private TextField nameToFindStudent;
@FXML private TextField idToFindStudent;
@FXML private ComboBox classToFindStudent;
@FXML private ComboBox semesterToFindStudent;
@FXML private CheckBox isExchangeToFindStudent;

@FXML private TableColumn nameStudent;
@FXML private TableColumn idStudent;
@FXML private TableColumn classStudent;
@FXML private TableColumn semesterStudent;
@FXML private TableColumn isExchangeStudent;

@FXML private Button removeStudent;

public void creatingStudent (ActionEvent e)
{
  if(e.getSource() == buttonToAddStudent)
  {
    String name = nameToCreateStudent.getText();
    String idString = idToCreateStudent.getText();
    int id = Integer.parseInt(idString);
    
  }
}
}
