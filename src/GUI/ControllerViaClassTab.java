package GUI;

import Model.Course;
import Model.Room;
import Model.Student;
import Model.VIAClass;

import ScheduleManager.Manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Locale;

public class ControllerViaClassTab extends AbstractController
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

  public void initialize()
  {
    //region tableInitialization
    classTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

    initializeFilterSide();
    initializePreferredRoomPane();
    eventsInitialization();
    refreshTableData();
  }

  //region Other Initialization
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

  private void initializePreferredRoomPane() //Basic initialization of the preferredRoom tab
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    preferredClassCombo.getItems().clear();
    preferredClassCombo.getItems().addAll(classes);

    setPreferredRoomButton.setDisable(true);
    preferredRoomCombo.setDisable(true);
  }

  private void eventsInitialization() //Set the events for setPreferredRoom tab objects
  {
    classTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldClass, newClass) -> setSelectedClass(newClass));

    preferredClassCombo.valueProperty().addListener(obj -> {
      VIAClass selected = preferredClassCombo.getSelectionModel()
          .getSelectedItem();
      setSelectedClass(selected);
    });

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
  public void refresh() //Refresh everything
  {
    if (selectedClass == null)
      return;
    refreshTableData();
    updateList();
    updateRoomCombo();
  }

  //region UpdateTable
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

  private void updateRoomCombo() //Refreshes the room combobox items
  {
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(null);
    rooms.addAll(Manager.getSchedule().getRoomList().getAllRooms());
    ArrayList<VIAClass> viaClasses = Manager.getSchedule().getVIAClassList()
        .getAllClasses();

    for (int i = 0; i < rooms.size(); i++)
    {
      Room room = rooms.get(i);
      if (room != null && room.getCapacity() < selectedClass.getAllStudents()
          .size())
        rooms.remove(i--);
    }
    for (VIAClass viaClass : viaClasses)
    {
      if (viaClass != selectedClass && viaClass.getPreferredRoom() != null)
        rooms.remove(viaClass.getPreferredRoom());
    }

    preferredRoomCombo.getItems().clear();
    preferredRoomCombo.getItems().addAll(rooms);

    if (selectedClass.getPreferredRoom() != null)
      preferredRoomCombo.getSelectionModel()
          .select(selectedClass.getPreferredRoom());
  }
  //endregion
  //endregion

  private void setSelectedClass(
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
