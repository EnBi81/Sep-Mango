package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * a class containing information about a Teacher object
 *
 * @author Simon Mayer
 * @version 1.0
 */
public class Teacher implements Serializable
{

  private String name;
  private ArrayList<Course> courses;

  /**
   * one parameter constructor which creates an instance of a class Teacher
   *
   * @param name name of a teacher
   * @version 1.0
   */
  public Teacher(String name)
  {
    this.name = name;
    courses = new ArrayList<>();
  }

  /**
   * return the name of a Teacher object
   *
   * @return name of a teacher
   * @version 1.0
   */
  public String getName()
  {
    return name;
  }

  /**
   * Compares the name of the teacher in each course with the name of the Teacher object. Return a list of courses which are taught by this teacher.
   * @return ArrayList of Teacher objects (the ones which comply the conditions)
   * @version 1.0
   */
  public ArrayList<Course> getAllCourses()
  {
    //made this into null//
    Schedule schedule = null;
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Course> allCourses = schedule.getCourseList().getAllCourses();
    for (Course course:allCourses
         )
    {
      for (Teacher teacher:course.getAllTeachers()
           )
      {
        if (teacher.getName().equals(name)){
          courses.add(course);
          break;
        }
      }
    }
    return courses;
  }

  /**
   * writes out the information about a Teacher object
   *
   * @return String containing information about a teacher
   * @version 1.0
   */
  public String toString()
  {
    String str = "";

    str += getName() + " teaches these courses:\n";

    for (Course course : courses)
    {
      str += course.getCourseName() + "\n";
    }

    return str;
  }

  /**
   * compares two objects and returns true if they are identical
   *
   * @param obj object that is compared
   * @return true (if the objects are the exact same) or false
   * @version 1.0
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Teacher))
    {
      return false;
    }

    Teacher temp = (Teacher) obj;

    return this.name.equals(temp.name);
  }
}
