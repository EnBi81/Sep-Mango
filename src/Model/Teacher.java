package Model;

import utils.CollisionException;

import java.util.ArrayList;

public class Teacher
{

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

  public void addCourse(Course course)
  {
    courses.add(course);
  }

  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }

  public ArrayList<Lesson> getAllLessons(){
    ArrayList<Lesson> lessons = new ArrayList<>();
    for (Course course: courses
         )
    {
      if (!(course.getAllLessons() == null)){
        lessons.addAll(course.getAllLessons());
      }
    }
    return lessons;
  }

  public String toString(){
    String str = "";

    str += "Teacher " + getName() + "\nteaches these courses:\n";

    for (Course course: courses
         )
    {
      str += course.getCourseName() + "\n";
    }



    return str;
  }

  public boolean equals(Object obj){
    if (!(obj instanceof Teacher)){
      return false;
    }

    Teacher temp = (Teacher) obj;

      return this.name.equals(temp.name) && this.courses.equals(temp.courses);
  }
}


