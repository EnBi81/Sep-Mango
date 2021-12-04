package Model;

import java.util.ArrayList;

/**
 * a class containing a list of Course objects
 * @author Simon Mayer
 * @version 1.0
 */
public class CourseList
{
  private ArrayList<Course> courses;

  /**
   * creates an instance of the class CourseList
   * @version 1.0
   */
  public CourseList()
  {
    courses = new ArrayList<>();
  }

  /**
   * adds a Course to the list
   * @version 1.0
   * @param course course to be added
   */
  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * removes a course from the list
   * @version 1.0
   * @param course course to be removed
   */
  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  /**
   * returns a list of Course objects
   * @version 1.0
   * @return ArrayList<Course>
   */
  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }

  /**
   * returns a course with specific name
   * @version 1.0
   * @return a course
   * @param name the name of the returned course
   */
  public Course getCourseByName(String name)
  {
    Course returnCourse = null;
    for (Course course:courses
         )
    {
      if (course.getCourseName().equalsIgnoreCase(name)){
        returnCourse = course;
      }
      else{
        System.out.println("No course with name " + name);
      }
    }
    return returnCourse;
  }
}
