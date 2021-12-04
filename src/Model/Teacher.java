package Model;

import java.util.ArrayList;

/**
 * a class containing information about a Teacher object
 * @author Simon Mayer
 * @version 1.0
 */
public class Teacher {

  private String name;
  private ArrayList<Course> courses;

  /**
   * one parameter constructor which creates an instance of a class Teacher
   * @version 1.0
   * @param name name of a teacher
   */
  public Teacher(String name){
    this.name = name;
    courses = new ArrayList<>();
  }

  /**
   * return the name of a Teacher object
   * @version 1.0
   * @return name of a teacher
   */
  public String getName()
  {
    return name;
  }

  /**
   *
   * @version 1.0
   * @return
   */
  public ArrayList<Course> getAllCourses(){
    return new ArrayList<>();
  }

  /**
   * writes out the information about a Teacher object
   * @version 1.0
   * @return String containing information about a teacher
   */
  public String toString(){
    String str = "";

    str += getName() + " teaches these courses:\n";

    for (Course course: courses
         )
    {
      str += course.getCourseName() + "\n";
    }

    return str;
  }

  /**
   * compares two objects and returns true if they are identical
   * @version 1.0
   * @param obj object that is compared
   * @return true (if the objects are the exact same) or false
   */
  public boolean equals(Object obj){
    if (!(obj instanceof Teacher)){
      return false;
    }

    Teacher temp = (Teacher) obj;

    return this.name.equals(temp.name);
  }
}
