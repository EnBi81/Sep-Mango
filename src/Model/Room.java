package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Room {
    public static ArrayList<Room> getAllAvailableRooms(LocalDateTime from, LocalDateTime to) {
        return null;
    }


    private String roomNumber;
    private int capacity;
    private Room connectedRoom;

    public Room(String roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        connectedRoom = null;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public Room getConnectedRoom() {
        return connectedRoom;
    }

    public void setConnectedRoom(Room room)
    {
        connectedRoom=room;
    }

    public boolean hasConnectedRoom()
    {
        return connectedRoom!=null;
    }

    public boolean isAvailable(LocalDateTime from, LocalDateTime to)
    {
        return true; ///for now
    }


    public String toString() {

        if(hasConnectedRoom())
        {
            return   roomNumber + "-" + capacity + "-" + connectedRoom.roomNumber;

        }
        else return   roomNumber + "-" + capacity + "-" ;


    }


}
