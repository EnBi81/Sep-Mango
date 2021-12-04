package Model;

import utils.CollisionException;

import java.util.ArrayList;

public class Course
{

  private String courseName;
  private int ects;
  private ArrayList<Teacher> teachers;
  private ArrayList<Lesson> lessons;
  private ArrayList<Student> students;
  private VIAClass viaClass;

  public Course(String courseName, int ects, VIAClass viaClass)
  {
    this.courseName = courseName;
    this.ects = ects;
    this.teachers = new ArrayList<>();
    this.lessons = new ArrayList<>();
    this.students = new ArrayList<>();
    this.viaClass = viaClass;
  }

  public String getCourseName()
  {
    return courseName;
  }

  public int getEcts()
  {
    return ects;
  }

  public VIAClass getVIAClass()
  {
    return viaClass;
  }

  public void addStudent(Student student)
  {
    students.add(student);
  }

  public void removeStudent(Student student)
  {
    students.remove(student);
  }

  public void addLesson(Lesson lesson)
  {
    lessons.add(lesson);
  }

  public void removeLesson(Lesson lesson)
  {
    lessons.remove(lesson);
  }

  public ArrayList<Teacher> getAllTeachers()
  {
    if (teachers == null)
    {
      System.out.println(
          "There are no teachers for the course " + getCourseName() + ".");
    }
    return teachers;
  }

  public ArrayList<Student> getAllStudents()
  {
    if (students == null)
    {
      System.out.println(
          "There are no students for the course " + getCourseName() + ".");
    }
    return students;
  }

  public ArrayList<Lesson> getAllLessons()
  {
    if (lessons == null)
    {
      System.out.println(
          "There are no lessons for the course " + getCourseName() + ".");
    }
    return lessons;
  }

  public String toString()
  {
    String str = "";

    str += getCourseName() + " has " + getEcts() + " ECTS points.\nTeachers: ";

    for (Teacher teacher : teachers)
    {
      str += teacher.getName() + ", ";
    }
    str += "\nClass: " + getVIAClass().getClassName() + ",";
    str += "\nLessons: ";
    for (Lesson lesson : lessons)
    {
      str += lesson.toString() + "\n";
    }
    return str;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Course))
    {
      return false;
    }

    Course temp = (Course) obj;

    return this.courseName.equals(temp.courseName);
  }
}