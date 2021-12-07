package GUI;

import Model.Course;
import Model.Room;
import Model.Student;
import Model.VIAClass;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class VIAClassTab
{
  public static Tab getTab()
  {
    ComboBox<VIAClass> preferredClassCombo = new ComboBox<>();
    preferredClassCombo.setPromptText("Class");

    ComboBox<Room> preferredRoomCombo = new ComboBox<>();
    preferredRoomCombo.setPromptText("Room");

    Button setPreferredRoom = new Button("Set preferred room");

    GridPane preferredPane = new GridPane();
    preferredPane.setHgap(40);
    preferredPane.setVgap(20);
    preferredPane.add(preferredClassCombo, 0, 0);
    preferredPane.add(preferredRoomCombo, 0, 1);
    preferredPane.add(setPreferredRoom, 1, 1);

    ListView<Student> studentsList = new ListView<>();
    studentsList.setEditable(false);
    ListView<Course> courseListView = new ListView<>();
    studentsList.setEditable(false);

    GridPane lists = new GridPane();
    lists.setVgap(5);
    lists.setHgap(10);
    lists.setAlignment(Pos.CENTER);
    lists.add(new Label("Students"), 0, 0);
    lists.add(new Label("Courses"), 1, 0);
    lists.add(studentsList, 0, 1);
    lists.add(courseListView, 1, 1);

    VBox leftPane = new VBox();
    leftPane.setSpacing(50);
    leftPane.getChildren().addAll(preferredPane, lists);



    TextField filterByName = new TextField();
    filterByName.setPrefWidth(100);
    ComboBox<Integer> filterBySemester = new ComboBox<>();
    filterBySemester.setPromptText("Choose Semester");
    ComboBox<String> filterByPreferredRoom = new ComboBox<>();

    GridPane filterPane = new GridPane();
    filterPane.setHgap(5);
    filterPane.add(filterByName, 0, 0);
    filterPane.add(filterBySemester, 1, 0);
    /*filterPane.add(filterByPreferredRoom, 3, 0);*/


    TableColumn<VIAClass, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getName()));
    TableColumn<VIAClass, String> semesterColumn = new TableColumn<>("Semester");
    semesterColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getSemester() + ""));
    TableColumn<VIAClass, String> numberOfStudentsColumn = new TableColumn<>("Number of Students");
    numberOfStudentsColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getAllStudents().size() + ""));
    TableColumn<VIAClass, String> preferredRoomColumn = new TableColumn<>("Preferred Room");
    preferredRoomColumn.setCellValueFactory(obj -> {
      Room preferredRoom = obj.getValue().getPreferredRoom();
      if(preferredRoom == null)
        return new SimpleStringProperty("");
      return new SimpleStringProperty(preferredRoom.getRoomName());
    });

    TableView<VIAClass> tableView = new TableView<>();
    tableView.setMinHeight(500);
    tableView.setMinWidth(350);
    tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    tableView.getColumns().addAll(nameColumn, semesterColumn, numberOfStudentsColumn, preferredRoomColumn);
    tableView.getItems().addAll(Manager.getSchedule().getVIAClassList().getAllClasses());



    VBox rightPane = new VBox();
    rightPane.setSpacing(20);
    rightPane.getChildren().add(filterPane);
    rightPane.getChildren().add(tableView);

    BorderPane mainPane = new BorderPane();
    mainPane.setPadding(new Insets(20));
    mainPane.setLeft(leftPane);
    mainPane.setRight(rightPane);

    Tab tab = new Tab();
    tab.setClosable(false);
    tab.setText("Class");
    tab.setContent(mainPane);

    return tab;
  }
}
