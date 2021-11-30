package Model;

////

import java.time.LocalDateTime;

public class Lesson
{
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Room room1;
  private Room room2;
  private Course course;

  /**
   * creates an instance of the class Lesson
   * @author Uafa
   * @version
   * @param course the course assigned to the lesson
   * @param room the room where the lesson will be held
   * @param startTime  the start time of the lesson
   * @param endTime  the end time of the lesson
   *
   */
  public Lesson(Course course, Room room, LocalDateTime startTime, LocalDateTime endTime)
  {
    this.course = course;
    this.endTime = endTime;
    this.startTime = startTime;
    this.room1 = room;
    this.room2 = null;
  }

  /**
   * returns the primary room where the lesson will be held
   * @author Uafa
   * @version
   * @return the primary room in which the lesson will be held
   */

  public Room getFirstRoom()
  {
    return room1;
  }

  /**
   * returns the secondary room if there is one, else returns null
   * @author Uafa
   * @return the secondary room if there is one
   */

  public Room getSecondRoom()
  {
    return room2;
  }

  /**
   * sets the primary room (used for changing the primary room)
   * @author Uafa
   * @param room
   */

  public void setFirstRoom(Room room)
  {
    room1 = room;
  }

  /**
   *using a method from the Room class checks if the primary room has a secondary one and if it has it sets the room2 private variable to equal the room1 secondary room, which is obtained by another method from the Room CLass
   * @author Uafa
   */

  public void setSecondRoom()
  {
    if(room1.hasConnectedRoom())
    {
      room2 = room1.getConnectedRoom();
    }
  }

  public LocalDateTime getStartTime()
  {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime)
  {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime()
  {
    return endTime;
  }

  public Course getCourse()
  {
    return course;
  }

  public void setEndTime(LocalDateTime endTime)
  {
    this.endTime = endTime;
  }

  public String toString()
  {
    String str = "Course: " + course  + "Lesson start time: " + startTime + " Lesson end time: " + endTime + " Room: " + room1;

    if(room2 != null)
    {
      str += " + " + room2.toString();
    }
    return str;
  }

  public boolean equals(Object obj)
  {
    if(!(obj instanceof Lesson))
    {
      return false;
    }

    Lesson other = (Lesson) obj;

    return this.room1.equals(other.room1) && this.startTime.equals(other.startTime) && this.endTime.equals(other.endTime) && this.course.equals(other.course) && this.room2.equals(other.room2);
  }
}
