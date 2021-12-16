package GUI;

import Model.Course;
import Model.Room;
import Model.Student;
import Model.VIAClass;

import ScheduleManager.Manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Controller class for VIAClassTab.fxml
 * @author Gergo Nador
 */
public class ControllerViaClass extends AbstractController
{
  //region Fields
  @FXML private ComboBox<VIAClass> preferredClassCombo;
  @FXML private ComboBox<Room> preferredRoomCombo;
  @FXML private Button setPreferredRoomButton;

  @FXML private ListView<String> studentsListView;
  @FXML private ListView<String> courseListView;

  @FXML private TextField filterByName;
  @FXML private ComboBox<String> filterBySemester;

  @FXML private TableView<VIAClass> classTableView;
  @FXML private TableColumn<VIAClass, String> nameColumn;
  @FXML private TableColumn<VIAClass, String> semesterColumn;
  @FXML private TableColumn<VIAClass, String> numberOfStudentsColumn;
  @FXML private TableColumn<VIAClass, String> preferredRoomColumn;

  private VIAClass selectedClass;
  //endregion

  /**
   * Base initialize method for the controller
   */
  public void initialize()
  {
    //Check if the schedule is already imported, if not then dont do anything
    //(When the user imports the schedule, this method will be called automatically)
    if(Manager.getSchedule() == null)
      return;

    //region tableInitialization
    classTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    //Set the values to be displayed in the tableview
    nameColumn.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getName()));
    semesterColumn.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getSemester() + ""));
    numberOfStudentsColumn.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getAllStudents().size() + ""));
    preferredRoomColumn.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getPreferredRoom() == null ?
            "" :
            obj.getValue().getPreferredRoom().getRoomName()));
    //endregion

    //Initialize all the other stuff, and the load data by refreshing the tab
    initializeFilterSide();
    initializePreferredRoomPane();
    eventsInitialization();
    refreshTableData();
  }

  //region Other Initialization

  /**
   * Initializes the filter textfield and the combobox
   */
  public void initializeFilterSide() //Basic initialization for the filters
  {
    filterBySemester.getItems().add(null);
    for (VIAClass viaClass : Manager.getSchedule().getVIAClassList()
        .getAllClasses())
    {
      if (!filterBySemester.getItems().contains(viaClass.getSemester() + ""))
        filterBySemester.getItems().add(viaClass.getSemester() + "");
    }
    filterBySemester.valueProperty().addListener(obj -> refreshTableData());
    filterByName.textProperty().addListener(obj -> refreshTableData());
  }

  /**
   * Initializes the preferred room pane objects
   */
  private void initializePreferredRoomPane() //Basic initialization of the preferredRoom tab
  {
    ArrayList<VIAClass> classes =
        Manager.getSchedule().getVIAClassList().getAllClasses();

    preferredClassCombo.getItems().clear();
    preferredClassCombo.getItems().addAll(classes);

    setPreferredRoomButton.setDisable(true);
    preferredRoomCombo.setDisable(true);
  }

  /**
   * Initializes the events for the table, for the button to select th preferred room,
   * and for the class selector combo box
   */
  private void eventsInitialization() //Set the events for setPreferredRoom tab objects
  {
    //Run the selectDisplayClass every time the user selects a class from the table
    classTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldClass, newClass) -> selectDisplayClass(newClass));

    //Run the selectDisplayClass every time the user selects a class from the combobox
    preferredClassCombo.valueProperty().addListener(obj -> {
      VIAClass selected = preferredClassCombo.getSelectionModel()
          .getSelectedItem();
      selectDisplayClass(selected);
    });

    //Run that when the user presses the set preferred room button
    setPreferredRoomButton.setOnAction(obj -> {
      if (selectedClass != null)
      {
        selectedClass.setPreferredRoom(preferredRoomCombo.getValue());
        Manager.saveSchedule();
        refreshTableData();
      }
    });

  }
  //endregion

  //region Update

  /**
   * Refresh the whole tab to which the controller class was assigned (VIAClassTab)
   */
  public void refresh() //Refresh everything
  {
    if (selectedClass == null)
      return;

    refreshTableData();
    updateList();
    updateRoomCombo();
  }

  //region UpdateTable

  /**
   * Checks if the filter values apply for one VIAClass instance
   * @param viaClass VIAClass instance to check against the filters
   * @return True if the filter does not apply for the viaClass object (so it should be removed); Otherwise false.
   */
  private boolean checkRemoveData(
      VIAClass viaClass) //check for a VIAClass if the filters apply for it
  {
    String nameFilter = filterByName.getText().toLowerCase(Locale.ROOT);

    if (viaClass == null)
      return false;

    if (!viaClass.getName().toLowerCase(Locale.ROOT).contains(nameFilter))
      return true;

    String semesterFilter = filterBySemester.getValue();
    if (semesterFilter == null)
      return false;

    return !(viaClass.getSemester() + "").equals(semesterFilter);
  }

  /**
   * Refresh the table's items, and automatically selects the previously selected class by the user.
   */
  private void refreshTableData() //refreshes the table data considering the filter values
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    for (int i = 0; i < classes.size(); i++)
    {
      if (checkRemoveData(classes.get(i)))
        classes.remove(i--);
    }

    classTableView.getItems().clear();
    classTableView.getItems().addAll(classes);

    if (classes.contains(selectedClass))
      classTableView.getSelectionModel().select(selectedClass);
  }
  //endregion

  //region Update Lists and Room Combobox

  /**
   * Updates both the list of students and the list of courses.
   */
  private void updateList() //Updates both the student list and the course list in the gui
  {
    VIAClass viaClass = selectedClass;

    studentsListView.getItems().clear();
    courseListView.getItems().clear();

    if (viaClass != null)
    {
      ArrayList<Student> students = viaClass.getAllStudents();
      ArrayList<Course> courses = viaClass.getAllCourses();

      for (Student student : students)
        studentsListView.getItems().add(student.getName());
      for (Course course : courses)
        courseListView.getItems().addAll(course.getCourseName());
    }
  }

  /**
   * Updates the room selector combobox considering the currently selected class' student count and all the other classes' preferred room
   */
  private void updateRoomCombo() //Refreshes the room combobox items
  {
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(null);
    rooms.addAll(Manager.getSchedule().getRoomList().getAllRooms());
    ArrayList<VIAClass> viaClasses = Manager.getSchedule().getVIAClassList()
        .getAllClasses();

    //Check if the room does not have enough capacity for the class
    for (int i = 0; i < rooms.size(); i++)
    {
      Room room = rooms.get(i);
      if (room != null && room.getCapacity() < selectedClass.getAllStudents()
          .size())
        rooms.remove(i--);
    }
    //Remove every item which is assigned as preferred room to other classes
    for (VIAClass viaClass : viaClasses)
    {
      if (viaClass != selectedClass && viaClass.getPreferredRoom() != null)
        rooms.remove(viaClass.getPreferredRoom());
    }

    preferredRoomCombo.getItems().clear();
    preferredRoomCombo.getItems().addAll(rooms);

    //Automatically select the preferred room if it is assigned to the class
    if (selectedClass.getPreferredRoom() != null)
      preferredRoomCombo.getSelectionModel()
          .select(selectedClass.getPreferredRoom());
  }
  //endregion
  //endregion

  /**
   * Connect the selected Via class in the table with the combobox's selected Via class, and back.
   * @param viaClass The via class object to be selected
   */
  private void selectDisplayClass(
      VIAClass viaClass) //This method runs everytime you select a class either in the
  {                                                //table or in the combobox
    if (viaClass == null || selectedClass == viaClass)
      return;

    setPreferredRoomButton.setDisable(false);
    preferredRoomCombo.setDisable(false);

    selectedClass = viaClass;
    refresh();
    preferredClassCombo.getSelectionModel().select(viaClass);
  }
}
