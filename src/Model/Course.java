package Model;

import java.util.ArrayList;

public class Course {

  private String courseName;
  private int ects;
  private ArrayList<Teacher> teachers;
  private ArrayList<Lesson> lessons;
  private ArrayList<Student> students;
  private Class viaClass;

  public Course(String courseName, int ects, ArrayList<Teacher> teachers){
    this.courseName = courseName;
    this.ects = ects;
    this.teachers = teachers;
    lessons = new ArrayList<>();
  }

  public String getCourseName()
  {
    return courseName;
  }

  public int getEcts()
  {
    return ects;
  }

  public void addStudent(){
    //add attributes according to the Student constructor
    students.add(new Student());
  }


}
