package Model;

import java.util.ArrayList;

public class Teacher {

  private String name;
  private ArrayList<Course> courses;

  public Teacher(String name){
    this.name = name;
    courses = new ArrayList<>();
  }

  public String getName()
  {
    return name;
  }

  public ArrayList<Course> getAllCourses(){
    return new ArrayList<>();
  }

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
}
