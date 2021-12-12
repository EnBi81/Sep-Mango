package GUI;

import Model.Lesson;
import Model.Room;
import Model.VIAClass;
import ScheduleManager.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;

/***
 * A user interface that allows for displaying and modifying information about rooms in room tab.
 * @author Beatricia Magit
 * @version 1.0
 */

public class ControllerRoom extends AbstractController {

    //add room
    @FXML
    private TextField roomNameTextField;
    @FXML
    private TextField roomCapacityTextField;
    @FXML
    private Button addRoomButton;

    //the table for rooms with remove button and filter text fields
    @FXML
    private TextField filterRoomNameTextField;
    @FXML
    private TextField filterRoomCapacityTextField;
    @FXML
    private CheckBox connectedRoomCheckBox;
    @FXML
    private TableColumn<Room, String> roomNameColumn;
    @FXML
    private TableColumn<Room, String> roomCapacityColumn;
    @FXML
    private TableColumn<Room, String> roomConnectedColumn;
    @FXML
    private Button removeRoomButton;
    @FXML
    private TableView<Room> roomTableView;

    /***
     * Base initialize method for the controller
     */
    public void initialize() {
        //Sets the columns in the table
        //Single property allows for only one item to be selected at a time

        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomNameColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getRoomName()));
        roomCapacityColumn.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getCapacity() + ""));
        roomConnectedColumn.setCellValueFactory(obj ->
        {
            if (obj.getValue().getConnectedRoom() == null)// if there is no connected room
                return new SimpleStringProperty();// it will display an empty string
            else// if there is a connected room
                return new SimpleStringProperty(obj.getValue().getConnectedRoom().getRoomName() + "");// it will display the connected room s name
        });

        filterRoomNameTextField.textProperty().addListener(obj -> refresh());//adds the listener so everytime I am typing something it refreshes the table automatically, i don t have to press enter when i am looking for a rooom name
        filterRoomCapacityTextField.textProperty().addListener(obj -> refresh());

        ArrayList<Room> rooms;
        rooms = Manager.getSchedule().getRoomList().getAllRooms();
        roomTableView.getItems().addAll(rooms);

    }

    /***
     * Handler method for buttons when pressed
     * @param e the action event
     */
    public void handle(ActionEvent e) {
        if (e.getSource() == removeRoomButton) {
            Room selectedItem = roomTableView.getSelectionModel().getSelectedItem();
            ArrayList<VIAClass> classes = Manager.getSchedule().getVIAClassList().getAllClasses();
            ArrayList<Lesson> lessons = Manager.getSchedule().getLessonList().getAllLessons();

            VIAClass viaClass = null;

            for (VIAClass aClass : classes) {
                if (selectedItem.equals(aClass.getPreferredRoom())) {
                    int choice = JOptionPane.showConfirmDialog(null, "This room is set as a preferred room for class " + aClass.getName()
                            + ". \nDo you really want to remove it?");
                    if (choice == JOptionPane.NO_OPTION)
                        return;//it will stop the method if the user press no
                    if (choice == JOptionPane.YES_OPTION) {
                        viaClass = aClass;//if the user press yes, so he wants to remove the preferred room from this via class
                        break;
                    }
                }
            }

            int lessonCount = 0;
            for (Lesson lesson : lessons) {
                if (selectedItem.equals(lesson.getFirstRoom()) || selectedItem.equals(lesson.getSecondRoom())) {
                    lessonCount++;

                }
            }
            if (lessonCount > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This room is assign to " + lessonCount + " lessons. To remove this room \nplease change the assigned room for the lessons.");
                alert.show();
                return;
            }
            if (viaClass != null) {
                viaClass.setPreferredRoom(null);//i am setting the preferred room to null
            }
            Manager.getSchedule().getRoomList().removeRoom(selectedItem);//here i am removing the room from the actual schedule, this is only in gui for now
        }
        if (e.getSource() == addRoomButton) {
            ArrayList<Room> rooms = Manager.getSchedule().getRoomList().getAllRooms();
            if (roomNameTextField.getText().isEmpty()) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please write a name for the new room.");
                alert.show();
                return;
            }
            try {
                for (Room room : rooms) {
                    if (room.getRoomName().equals(roomNameTextField.getText())) {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("This room already exists");
                        alert.show();
                        return;
                    }

                }
                if (Integer.parseInt(roomCapacityTextField.getText()) <= 0) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please write a positive number for capacity.");
                    alert.show();
                    return;
                }
                Room newRoom = new Room(roomNameTextField.getText(), Integer.parseInt(roomCapacityTextField.getText()));
                Manager.getSchedule().getRoomList().addRoom(newRoom);//here I am adding the room
            } catch (NumberFormatException ex) {
                {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please write a number for capacity.");
                    alert.show();
                    return;
                }
            }
        }
        Manager.saveSchedule();
        refresh();
    }

    /***
     * Refresh the table items and shows the searching ones.
     */
    public void refresh() {
        ArrayList<Room> rooms = new ArrayList<>(Manager.getSchedule().getRoomList().getAllRooms());
        for (int i = 0; i < rooms.size(); i++) {
            if (!filterRoom(rooms.get(i)))
                rooms.remove(i--);//I am removing the rooms that we don't t want from the room array list
        }
        roomTableView.getItems().clear();// I am removing all the rooms from the table
        roomTableView.getItems().addAll(rooms);// I am adding the filtered rooms from the array list

    }

    /***
     * Filters rooms by the name and capacity inserted by the user and by the connected room if the checkbox
     * is selected
     * @param room the room object
     * @return false if the filter does not apply for the room object, true if applies.
     */
    public boolean filterRoom(Room room) {
        String inputName = filterRoomNameTextField.getText().toLowerCase(Locale.ROOT);
        String inputCapacity = filterRoomCapacityTextField.getText();

        if (!room.getRoomName().toLowerCase(Locale.ROOT).contains(inputName)) {
            return false;
        }

        //the is empty is for refreshing the table everytime I am not writing anything there
        if (!inputCapacity.isEmpty() && !(room.getCapacity() + "").startsWith(inputCapacity))// I did the "" thing because i want to use startsWith method that is only for string, so i am converting the int capacity to a string
        {
            return false;//false in this case is deleting the room from the table
        }

        if (connectedRoomCheckBox.isSelected()) {
            if (room.hasConnectedRoom())// if it ends with a or b then there is a possibility of connected room
            {
                return room.getConnectedRoom() != null;//this will give true or false, we are checking if there is actually the second connected room in the system
            } else return false;
        }
        return true;
    }

    /***
     * Refreshes the table.
     */
    public void filterHandler()// the event handler for filters
    {
        refresh(); // when we are searching for a room name for example it goes to refresh table method
    }

    /***
     * When user enters ”Enter” the program adds a room.
     * @param keyEvent the key event
     */
    public void createRoomKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            addRoomButton.fire();//it will simulate a button press when we press enter
        }
    }

    /***
     * When user enters ”Enter” the program jumps to capacity text field.
     * @param keyEvent keyEvent the key event
     */
    public void jumpToCapacity(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            roomCapacityTextField.requestFocus();//it goes to the capacity text field then we press enter, request on focus, request a focus on room capacity text field
        }
    }
}

