package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class VIAClass implements Serializable
{
  private final int semester;
  public final String className;
  private final ArrayList<Student> students;
  private final ArrayList<Course> courses;
  private Room preferredRoom;

  /**
   * Create an instance of the class VIAClass
   * @param semester Semester of the class
   * @param className Name of the class
   */
  public VIAClass(int semester, String className)
  {
    this.semester = semester;
    this.className = className;
    students = new ArrayList<>();
    courses = new ArrayList<>();
    preferredRoom = null;
  }



  /**
   * Gets the semester of the class
   * @return The semester of the class
   */
  public int getSemester()
  {
    return semester;
  }

  /**
   * Gets the name of the class
   * @return The name of the class
   */
  public String getName()
  {
    return className;
  }

  /**
   * Get the preferred room assigned to the current object
   * @return The preferred room if it is assigned, else null
   */
  public Room getPreferredRoom()
  {
    return preferredRoom;
  }

  /**
   * Set the preferred room for the current instance
   * @param room Room to be set
   */
  public void setPreferredRoom(Room room)
  {
    preferredRoom = room;
  }

  /**
   * Add a student to the class' list
   * @param student The student that should be added
   */
  public void addStudent(Student student)
  {
    students.add(student);
  }

  /**
   * Get all the students associated with the class
   * @return List of all students in the class
   */
  public ArrayList<Student> getAllStudents()
  {
    return students;
  }

  /**
   * Assign a course with the class
   * @param course The course that should be added
   */
  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * Get all the courses associated with the class
   * @return List of all courses in the class
   */
  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }

  public String toString()
  {
    return getName();
  }

  /**
   * Compares this VIACLass to the specified object.
   * The result is true if and only if the argument is not null and is a VIAClass object that represents the same course name and semester as this object.
   * @param obj Object to compare this Class against.
   * @return True if the given object represents a VIAClass equivalent to this VIAClass, false otherwise
   */
  public boolean equals(Object obj)
  {
    if(obj instanceof VIAClass)
    {
      VIAClass c = (VIAClass) obj;

      return className.equals(c.className) && semester == c.semester;
    }
    else return false;
  }
}
