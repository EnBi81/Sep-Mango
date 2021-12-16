package Model;

import ScheduleManager.Manager;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class containing a list of Room objects.
 * @author Beatricia
 * @version 1.0;
 */
public class RoomList implements Serializable {
    ArrayList<Room> rooms;

    /***
     * No-argument constructor initializing the RoomList.
     */
    public RoomList()
    {
        rooms = new ArrayList<>();
    }

    /***
     * Adds a Room to the list.
     * @param room the room to add to the list
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /***
     * Removes a Room from the list
     * @param room the room to be removed
     */
    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    /***
     * Gets a room by a specific name.
     * @param name the name of the searching room
     * @return the room with a name and a capacity
     */
    public Room getByRoomName(String name) {

        for (Room room : rooms) {
            if (room.getRoomName().equals(name))
                return room;
        }
        return null;
    }

    /***
     * Gets all the available rooms for a specific period of time (from and to).
     * @param from
     * @param to
     * @return
     */
    public ArrayList<Room> getAllAvailableRooms(LocalDateTime from, LocalDateTime to)
    {
        ArrayList<Room> rooms = new ArrayList<>(Manager.getSchedule().getRoomList().getAllRooms());
        for (int i = 0; i < rooms.size(); i++)
        {
            if(!rooms.get(i).isAvailable(from,to))
            {
              rooms.remove(i--);
            }
        }
        return rooms;
    }

    /***
     * Gets all rooms in an array list.
     * @return all rooms
     */
    public ArrayList<Room> getAllRooms() {
   return rooms;
    }
}
