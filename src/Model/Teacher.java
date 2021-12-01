package Model;

import utils.CollisionException;

import java.util.ArrayList;

public class Teacher
{

  private String name;
  private ArrayList<Course> courses;
  private  ArrayList<Lesson> lessons;

  public Teacher(String name){
    this.name = name;
    courses = new ArrayList<>();
    lessons = new ArrayList<>();
  }

  public String getName()
  {
    return name;
  }

  public void addCourse(Course course) throws CollisionException
  {
    courses.add(course);
  }

  // do we really need this? If computer assigns it auto from the spreadsheet. Maybe if teacher would die during the semester, and they needed replacement...
  public void removeCourse(Course course){
    if (courses.contains(course)){
      courses.remove(course);

    }
    else {
      System.out.println("Teacher " + getName() + " does not teach course " + course.getCourseName() + ".");
    }
  }

  public ArrayList<Course> getAllCourses()
  {
    if (courses == null){
      System.out.println("Teacher " + getName() + " does not teach any courses.");
    }
    return courses;
  }

  public ArrayList<Lesson> getAllLessons(){
    for (Course course: courses
         )
    {
      if (!(course.getAllLessons() == null)){
        lessons.addAll(course.getAllLessons());
      }
    }
    if (lessons == null){
      System.out.println("Teacher " + getName() + " does not teach any lessons.");
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

    str += "and has these lessons:\n";

    for (Lesson lesson: lessons
         )
    {
      str += lesson.toString() + "\n";
    }
    return str;
  }

  public boolean equals(Object obj){
    if (!(obj instanceof Teacher)){
      return false;
    }

    Teacher temp = (Teacher) obj;

    if (this.courses == null){
      return this.name.equals(temp.name) && temp.courses == null && this.lessons.equals(temp.lessons);
    }

    if (this.lessons == null){
      return this.name.equals(temp.name) && temp.courses == null && temp.lessons == null;
    }

    else {
      return this.name.equals(temp.name) && this.courses.equals(temp.courses) && this.lessons.equals(temp.lessons);

    }
  }
}


