package Model;

import java.util.ArrayList;

public class Teacher
{

  private String name;
  private ArrayList<Course> courses;
  private  ArrayList<Lesson> lessons;

  public Teacher(String name){
    this.name = name;
    courses = new ArrayList<>();
  }

  public String getName()
  {
    return name;
  }

  public ArrayList<Course> getCourses()
  {
    return courses;
  }

  public ArrayList<Lesson> getAllLessons(){

  }

  public boolean equals(Object obj){
    if (!(obj instanceof Teacher)){
      return false;
    }

    Teacher temp = (Teacher) obj;

    if (this.courses == null){
      return this.name.equals(temp.name) && temp.courses == null;

    }

    // null pointer for lessons + lessons
    else {
      return this.name.equals(temp.name) && this.courses.equals(temp.courses);

    }
  }
}
