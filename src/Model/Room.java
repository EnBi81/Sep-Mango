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
    private ArrayList<Lesson> lessonsInRoom;

    public Room(String roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        connectedRoom = null;
        lessonsInRoom=null;
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

    public void addLesson(Lesson lesson)
    {
        lessonsInRoom.add(lesson);
    }

    public void removeLesson(Lesson lesson)
    {
        for (int i = 0; i < lessonsInRoom.size(); i++) {
            if(lessonsInRoom.get(i).equals(lesson))
                lessonsInRoom.remove(i);
        }
    }


    public String toString() {

        if(hasConnectedRoom())
        {
            return   roomNumber + "-" + capacity + "-" + connectedRoom.roomNumber;

        }
        else return   roomNumber + "-" + capacity + "-" ;


    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof Room))
        {
            return false;
        }
        Room other = (Room)obj;
        return roomNumber.equals(other.roomNumber) && capacity==other.capacity && connectedRoom.equals(other.connectedRoom) && lessonsInRoom.equals(other.lessonsInRoom);
    }


}
