package utils;

import Model.Room;

import java.time.LocalDateTime;

public class RoomCollisionException extends CollisionException
{
  Room collisionRoom;

  public RoomCollisionException(Room room, LocalDateTime from, LocalDateTime to)
  {
    super(from, to);
    this.collisionRoom = room;
  }
  public RoomCollisionException(String message)
  {
    super(message);
  }
  public RoomCollisionException()
  {
    this("Collision happened for a room");
  }

  public Room getCollisionRoom()
  {
    return collisionRoom;
  }
}
