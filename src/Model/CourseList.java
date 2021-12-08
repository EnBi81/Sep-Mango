package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * a class containing a list of Course objects
 *
 * @author Simon Mayer
 * @version 1.0
 */
public class CourseList implements Serializable
{
  private ArrayList<Course> courses;

  /**
   * creates an instance of the class CourseList
   */
  public CourseList()
  {
    courses = new ArrayList<>();
  }

  /**
   * adds a Course to the list
   * @param course course to be added
   */
  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * removes a course from the list
   * @param course course to be removed
   */
  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  /**
   * returns a list of Course objects
   * @return all courses that are taught
   *   */
  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }

  /**
   * returns a course with specific name
   * @param name the name of the returned course
   * @return one course from the course list
   * */
  public Course getCourseByName(String name)
  {

    for (Course course : courses)
    {
      if (course.getCourseName().equalsIgnoreCase(name))
      {
        return course;
      }
    }
    return null;
  }
}
