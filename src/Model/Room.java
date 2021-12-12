package Model;

import ScheduleManager.Manager;

import java.io.Serializable;
import java.time.LocalDateTime;
/*
 * A class representing a room with a name and a capacity.
 * @author Beatricia
 * @version 1.0
 */

public class Room implements Serializable {
    private String roomName;
    private int capacity;

    /***
     * Two-argument constructor.
     * @param roomName the room's name
     * @param capacity the capacity of the room
     */

    public Room(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
    }

    /***
     * Gets the room name.
     * @return the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /***
     * Gets the room capacity.
     * @return the room capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /***
     * Gets the connected room
     * @return the connected room as a Room object
     */
    public Room getConnectedRoom() {
        if(!hasConnectedRoom())
        {
            return null;
        }
        String connectedRoomName;
        if(roomName.endsWith("a"))
        {
            connectedRoomName = roomName.substring(0,roomName.length()-1)+"b";
        }
        else
        {
            connectedRoomName = roomName.substring(0,roomName.length()-1)+"a";
        }
        return Manager.getSchedule().getRoomList().getByRoomName(connectedRoomName);
    }

    /***
     * Checks if the room has a connected room.
     * @return true if the room has a connected room, false if it does not
     */
    public boolean hasConnectedRoom() {
        return roomName.charAt(roomName.length() - 1) == 'a' || roomName.charAt(roomName.length() - 1) == 'b';
    }

    /***
     * Checks if the room is available for a specific period of time(from and to).
     * @param from the start period
     * @param to the end period
     * @return true if it is available, false if it is not
     */
    public boolean isAvailable(LocalDateTime from, LocalDateTime to) {
        return true;//i need all lessons for this
    }

    /***
     * Returns a string representation of the room.
     * @return a string representation of the room in the format: "roomName-capacity"
     */
    public String toString() {
        return roomName + "-" + capacity;
    }

    /***
     * Compares room names of two rooms.
     * @param obj the object to compare with
     * @return true if the given object is equal to this room
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        Room other = (Room) obj;
        return roomName.equals(other.roomName);
    }
}
