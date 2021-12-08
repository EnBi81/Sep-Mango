package GUI;

import Model.Room;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Locale;

public class ControllerRoom {

    @FXML private Pane mainRoomPane;

    //add room
    @FXML private TextField roomNameTextField;
    @FXML private TextField roomCapacityTextField;
    @FXML private Button addRoomButton;

    //the table for rooms with remove button and filter text fields
    @FXML private TextField filterRoomNameTextField;
    @FXML private TextField filterRoomCapacityTextField;
    @FXML private CheckBox connectedRoomCheckBox;
    @FXML private TableColumn<Room, String> roomNameColumn;
    @FXML private TableColumn<Room, String> roomCapacityColumn;
    @FXML private TableColumn<Room, String> roomConnectedColumn;
    @FXML private Button removeRoomButton;
    @FXML private TableView<Room> roomTableView;

    public void initialize()
    {
        //Sets the columns in the table
        //Single property allows for only one item to be selected at a time

        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomNameColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getRoomName()));
        roomCapacityColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getCapacity()+""));
        roomConnectedColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().hasConnectedRoom()+""));

        ArrayList<Room> rooms = new ArrayList<>();
        rooms = Manager.getSchedule().getRoomList().getAllRooms();
        roomTableView.getItems().addAll(rooms);

    }


       public void handle(ActionEvent e)
       {
           if(e.getSource() == removeRoomButton )
           {
               Room selectedItem = roomTableView.getSelectionModel().getSelectedItem();
               roomTableView.getItems().remove(selectedItem);
           }
           if(e.getSource() == addRoomButton)
           {
               Room newRoom = new Room(roomNameTextField.getText(), Integer.parseInt(roomCapacityTextField.getText()));
               roomTableView.getItems().add(newRoom);
           }
       }




}
