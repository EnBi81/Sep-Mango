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
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

public class ViaClassTabController
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
    classTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    nameColumn.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getName()));
    semesterColumn.setCellValueFactory(
        obj -> new SimpleStringProperty(obj.getValue().getSemester() + ""));
    numberOfStudentsColumn.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getAllStudents().size() + ""));
    preferredRoomColumn.setCellValueFactory(obj -> new SimpleStringProperty(
        obj.getValue().getPreferredRoom() == null ?
            "" : obj.getValue().getPreferredRoom().getRoomName()));
    //endregion

    initializeFilterSide();
    initializePreferredRoomPane();
    eventsInitialization();
    refreshTableData();
  }

  //region Other Initialization
  private void initializeFilterSide()
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

  private void initializePreferredRoomPane()
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());

    preferredClassCombo.getItems().clear();
    preferredClassCombo.getItems().addAll(classes);

    setPreferredRoomButton.setDisable(true);
    preferredRoomCombo.setDisable(true);
  }

  private void eventsInitialization()
  {
    classTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldClass, newClass) -> setSelectedClass(newClass));

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

  //region Update
  private void refreshPane()
  {
    refreshTableData();
    updateList();
    updateRoomCombo();
  }

  //region UpdateTable
  private boolean checkRemoveData(VIAClass viaClass)
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

  private void refreshTableData()
  {
    ArrayList<VIAClass> classes = new ArrayList<>(
        Manager.getSchedule().getVIAClassList().getAllClasses());
    classes.removeIf(this::checkRemoveData);


    classTableView.getItems().clear();
    classTableView.getItems().addAll(classes);

    if (classes.contains(selectedClass))
      classTableView.getSelectionModel().select(selectedClass);
  }
  //endregion

  //region Update Lists and Room Combobox
  private void updateList()
  {
    VIAClass viaClass = selectedClass;

    studentsListView.getItems().clear();
    courseListView.getItems().clear();

    if (viaClass != null)
    {
      ArrayList<Student> students = viaClass.getAllStudents();
      ArrayList<Course> courses = viaClass.getAllCourses();

      students.forEach(student -> studentsListView.getItems().add(student.getName()));
      courses.forEach(course -> courseListView.getItems().add(course.getCourseName()));
    }
  }

  private void updateRoomCombo()
  {
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(null);

    rooms.addAll(Manager.getSchedule().getRoomList().getAllRooms().stream()
        .sorted(Comparator.comparing(Room::getCapacity))
        .collect(Collectors.toList()));

    Manager.getSchedule().getVIAClassList().getAllClasses().stream()
        .filter(viaClass -> viaClass != selectedClass && viaClass.getPreferredRoom() != null)
        .forEach(viaClass -> rooms.remove(viaClass.getPreferredRoom()));

    rooms.removeIf(room -> room != null && room.getCapacity() < selectedClass.getAllStudents().size());

    preferredRoomCombo.getItems().clear();
    preferredRoomCombo.getItems().addAll(rooms);

    if(selectedClass.getPreferredRoom() != null)
      preferredRoomCombo.getSelectionModel().select(selectedClass.getPreferredRoom());
  }
  //endregion
  //endregion

  private void setSelectedClass(VIAClass viaClass)
  {
    if (viaClass == null || selectedClass == viaClass)
      return;

    setPreferredRoomButton.setDisable(false);
    preferredRoomCombo.setDisable(false);

    selectedClass = viaClass;
    refreshPane();
    preferredClassCombo.getSelectionModel().select(viaClass);
  }
}
