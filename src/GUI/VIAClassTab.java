package GUI;

import Model.Room;
import Model.VIAClass;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VIAClassTab
{
  public static Tab getTab()
  {
    ComboBox<VIAClass> preferredClassCombo = new ComboBox<>();
    preferredClassCombo.setPromptText("Class");

    ComboBox<Room> preferredRoomCombo = new ComboBox<>();
    preferredClassCombo.setPromptText("Room");

    Button setPreferredRoom = new Button("Set preferred room");

    GridPane preferredPane = new GridPane();
    preferredPane.setHgap(20);
    preferredPane.setVgap(40);
    preferredPane.add(preferredClassCombo, 0, 0);
    preferredPane.add(preferredRoomCombo, 0, 1);
    preferredPane.add(preferredClassCombo, 1, 1);



    TextField filterByName = new TextField();
    ComboBox<Integer> filterBySemester = new ComboBox<>();
    ComboBox<String> filterByPreferredRoom = new ComboBox<>();

    GridPane filterPane = new GridPane();
    filterPane.add(filterByName, 0, 0);
    filterPane.add(filterBySemester, 1, 0);
    filterPane.add(filterByPreferredRoom, 3, 0);


    TableColumn<VIAClass, String> nameColumn = new TableColumn<>();
    nameColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getName()));
    TableColumn<VIAClass, String> semesterColumn = new TableColumn<>();
    semesterColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getSemester() + ""));
    TableColumn<VIAClass, String> numberOfStudentsColumn = new TableColumn<>();
    numberOfStudentsColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getAllStudents().size() + ""));
    TableColumn<VIAClass, String> preferredRoomColumn = new TableColumn<>();
    preferredRoomColumn.setCellValueFactory(obj -> {
      Room preferredRoom = obj.getValue().getPreferredRoom();
      if(preferredRoom == null)
        return new SimpleStringProperty("");
      return new SimpleStringProperty(preferredRoom.getRoomName());
    });

    TableView<VIAClass> tableView = new TableView<>();

    tableView.getColumns().addAll(nameColumn, semesterColumn, numberOfStudentsColumn, preferredRoomColumn);



    VBox rightPane = new VBox();
    rightPane.getChildren().add(filterPane);
    rightPane.getChildren().add(tableView);

    HBox mainPane = new HBox();

    mainPane.getChildren().addAll(preferredPane, rightPane);

    Tab tab = new Tab();
    tab.setClosable(false);
    tab.setText("Class");
    tab.setContent(mainPane);

    return tab;
  }
}
