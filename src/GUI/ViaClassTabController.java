package GUI;

import Model.Course;
import Model.Room;
import Model.Student;
import Model.VIAClass;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Locale;

public class ViaClassTabController
{

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

  public void initialize()
  {
    //region tableInitialization
    classTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
    refreshTableData();
    initializePreferredRoomPane();
    eventsInitialization();
  }

  //region Other Initialization
  public void initializeFilterSide()
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

  public void initializePreferredRoomPane()
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    preferredClassCombo.getItems().clear();
    preferredClassCombo.getItems().addAll(classes);
  }

  public void eventsInitialization()
  {
    classTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldClass, newClass) -> {
          setSelectedClass(newClass);
        });

    preferredClassCombo.valueProperty().addListener(obj -> {
      VIAClass selected = preferredClassCombo.getSelectionModel()
          .getSelectedItem();
      setSelectedClass(selected);
    });

    setPreferredRoomButton.setOnAction(obj ->{
      if(selectedClass != null)
      {
        selectedClass.setPreferredRoom(preferredRoomCombo.getValue());
        refreshTableData();
      }
    });

  }
  //endregion

  //region UpdateTable
  public boolean checkFilter(VIAClass viaClass)
  {
    String nameFilter = filterByName.getText().toLowerCase(Locale.ROOT);

    if (viaClass == null/* || viaClass == selectedClass*/)
      return true;

    if (!viaClass.getName().toLowerCase(Locale.ROOT).contains(nameFilter))
      return false;

    String semesterFilter = filterBySemester.getValue();
    if (semesterFilter == null)
      return true;

    return (viaClass.getSemester() + "").equals(semesterFilter);
  }

  public void refreshTableData()
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    for (int i = 0; i < classes.size(); i++)
    {
      if (!checkFilter(classes.get(i)))
        classes.remove(i--);
    }

    classTableView.getItems().clear();
    classTableView.getItems().addAll(classes);

    if (classes.contains(selectedClass))
      classTableView.getSelectionModel().select(selectedClass);
  }
  //endregion

  //region Update Lists and Room Combobox
  public void updateList()
  {
    VIAClass viaClass = selectedClass;

    studentsListView.getItems().clear();
    courseListView.getItems().clear();

    if (viaClass != null)
    {
      ArrayList<Student> students = viaClass.getAllStudents();
      ArrayList<Course> courses = viaClass.getAllCourses();

      studentsListView.getItems().clear();
      students.forEach(s -> studentsListView.getItems().add(s.getName()));
      courseListView.getItems().clear();
      courses.forEach(c -> courseListView.getItems().add(c.getCourseName()));
    }
  }

  public void updateRoomCombo()
  {
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(null);
    rooms.addAll(Manager.getSchedule().getRoomList().getAllRooms());

    for (VIAClass viaClass : Manager.getSchedule().getVIAClassList().getAllClasses())
    {
      if(viaClass != selectedClass && viaClass.getPreferredRoom() != null)
        rooms.remove(viaClass.getPreferredRoom());
    }
    rooms.removeIf(room -> room != null && room.getCapacity() < selectedClass.getAllStudents().size());

    preferredRoomCombo.getItems().clear();
    preferredRoomCombo.getItems().addAll(rooms);

    if(selectedClass.getPreferredRoom() != null)
      preferredRoomCombo.getSelectionModel().select(selectedClass.getPreferredRoom());
  }
  //endregion

  public void setSelectedClass(VIAClass viaClass)
  {
    if (viaClass == null || selectedClass == viaClass)
      return;

    System.out.println("ClassName: " + viaClass.getName());
    selectedClass = viaClass;
    refreshTableData();
    updateList();
    updateRoomCombo();
    preferredClassCombo.getSelectionModel().select(viaClass);
  }
}
