package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A Lesson class
 * @author Uafa
 * @version 1.0
 */

public class Lesson implements Serializable
{
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Room room1;
  private Room room2;
  private Course course;

  /**
   * creates an instance of the Lesson class
   *
   * @param course    the course assigned to the lesson
   * @param room      the room where the lesson will be held
   * @param startTime the start time of the lesson
   * @param endTime   the end time of the lesson
   */
  public Lesson(Course course, Room room, LocalDateTime startTime,
      LocalDateTime endTime)
  {
    this.course = course;
    this.endTime = endTime;
    this.startTime = startTime;
    this.room1 = room;
    this.room2 = null;
  }

  ///***///
  public boolean timeOverlapping()
  {
    //return true if lesson can not be added, false if there is no problem

    //Getting all the students in this Course
    ArrayList<Student> students = course.getAllStudents();
    students.addAll(course.getAllStudents());

    ArrayList<Course> otherCourses = new ArrayList<>();
    ArrayList<Course> temp = new ArrayList<>();
    boolean overlapping = false;

    //Putting all the courses the student from this course have in an Arraylist (without duplicates)

    for (int i = 0; i < students.size(); i++)
    {
      temp.addAll(students.get(i).getAllCourses());

      for (int j = 0; j < temp.size(); j++)
      {
        if (!(otherCourses.contains(temp.get(j))))
        {
          otherCourses.add(temp.get(j));
        }
      }
    }

    ArrayList<Lesson> tempLesson = new ArrayList<>();

    //Getting all the classes the other courses have
    //Checking for overlapping in start and end time

    for (int i = 0; i < otherCourses.size(); i++)
    {
      tempLesson.addAll(otherCourses.get(i).getAllLessons());

      for (int j = 0; j < tempLesson.size(); j++)
      {
        if (tempLesson.get(j).getStartTime().equals(getStartTime())
            || tempLesson.get(j).getEndTime().equals(getEndTime()))
        {
          overlapping = true;
        }

      }

    }
    return overlapping;
  }

  /**
   * returns the curse of the lesson
   * @return course The course this lesson has

   */
  public Course getCourse()
  {
    return course;
  }

  /**
   * returns the primary room where the lesson will be held
   * @return the primary room in which the lesson will be held
   */

  public Room getFirstRoom()
  {
    return room1;
  }

  /**
   * returns the secondary room if there is one, else returns null
   *
   * @return the secondary room if there is one
   */
//Discuss This!!!//
 /* public Room getSecondRoom()
  {

    if(room1.hasConnectedRoom())
    {

    }

  }*/

  /**
   * sets the primary room (used for changing the primary room)
   *
   * @param room the room that should be assigned
   */

  public void setFirstRoom(Room room)
  {
    room1 = room;
  }


  /**
   * returns the starting time of a lesson
   * @return start time of the lesson
   */

  public LocalDateTime getStartTime()
  {
    return startTime;
  }

  /**
   * returns the end time of a lesson
   * @return end time of the lesson

   */
  public LocalDateTime getEndTime()
  {
    return endTime;
  }

  /**
   * sets the start time of a lesson (used for making changes)
   * @param from indicates the start Time
   */

  public void setStartTime(LocalDateTime from)
  {
    if (!(timeOverlapping()))
    {
      this.startTime = from;
    }

  }

  /**
   * sets the end time of the lesson (used for making changes)
   *
   * @param endTime a LocalDateTime object
   */
  public void setEndTime(LocalDateTime endTime)
  {
    if (!(timeOverlapping()))
    {
      this.endTime = endTime;
    }

  }

  /**
   * returns the information about the lesson in a String if the room2 variable is null, it is not returned
   * @return string that hold the information about the lesson
   */

  public String toString()
  {
    String str = "Course: " + course + "Lesson start time: " + startTime
        + " Lesson end time: " + endTime + " Room: " + room1;

    if (room2 != null)
    {
      str += " + " + room2.toString();
    }
    return str;
  }

  /**
   * compares two objects and if they are the same returns true else returns false
   *
   * @param obj
   * @return true if the objects are the same, false if they aren't
   */
  public boolean equals(Object obj)
  {

    if (!(obj instanceof Lesson))
    {
      return false;
    }

    Lesson other = (Lesson) obj;

    return this.room1.equals(other.room1) && this.startTime.equals(
        other.startTime) && this.endTime.equals(other.endTime)
        && this.course.equals(other.course) && this.room2.equals(other.room2);
  }
}
