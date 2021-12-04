package Model;

import java.util.ArrayList;

public class CourseList
{
  private ArrayList<Course> courses;

  public CourseList()
  {
    courses = new ArrayList<>();
  }

  public void addCourse(Course course)
  {
    courses.add(course);
  }

  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }

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
