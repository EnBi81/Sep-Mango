package Model;

import utils.CollisionException;

import java.util.ArrayList;

/**
 * a class containing information about a Course object
 * @author Simon Mayer
 * @version 1.0
 */
public class Course
{

  private String courseName;
  private int ects;
  private ArrayList<Teacher> teachers;
  private ArrayList<Lesson> lessons;
  private ArrayList<Student> students;
  private VIAClass viaClass;

  /**
   * three parameter constructor which creates an instance of a class Course
   * @version 1.0
   * @param courseName name of a course
   * @param ects number of ects a course has
   * @param viaClass class which has the course
   */
  public Course(String courseName, int ects, VIAClass viaClass)
  {
    this.courseName = courseName;
    this.ects = ects;
    this.teachers = new ArrayList<>();
    this.lessons = new ArrayList<>();
    this.students = new ArrayList<>();
    this.viaClass = viaClass;
  }

  /**
   * return the name of a Course object
   * @version 1.0
   * @return name of a course
   */
  public String getCourseName()
  {
    return courseName;
  }

  /**
   * return the ects points of a Course object
   * @version 1.0
   * @return number of ects points
   */
  public int getEcts()
  {
    return ects;
  }

  /**
   * return the class of a Course object
   * @version 1.0
   * @return class which has the course
   */
  public VIAClass getVIAClass()
  {
    return viaClass;
  }

  /**
   * adds a student to the ArrayList of students
   * @version 1.0
   * @param student student to be added
   */
  public void addStudent(Student student)
  {
    students.add(student);
  }

  /**
   * removes a student from the ArrayList of students
   * @version 1.0
   * @param student student to be removed
   */
  public void removeStudent(Student student)
  {
    students.remove(student);
  }

  /**
   * adds a lesson to the ArrayList of lessons
   * @version 1.0
   * @param lesson lesson to be added
   */
  public void addLesson(Lesson lesson)
  {
    lessons.add(lesson);
  }

  /**
   * removes a lesson from the ArrayList of lessons
   * @version 1.0
   * @param lesson lesson to be removed
   */
  public void removeLesson(Lesson lesson)
  {
    lessons.remove(lesson);
  }

  /**
   * returns a list of Teacher objects, if null prints out a message
   * @version 1.0
   * @return ArrayList<Teacher>
   */
  public ArrayList<Teacher> getAllTeachers()
  {
    if (teachers == null)
    {
      System.out.println(
          "There are no teachers for the course " + getCourseName() + ".");
    }
    return teachers;
  }

  /**
   * returns a list of Student objects, if null prints out a message
   * @version 1.0
   * @return ArrayList<Student>
   */
  public ArrayList<Student> getAllStudents()
  {
    if (students == null)
    {
      System.out.println(
          "There are no students for the course " + getCourseName() + ".");
    }
    return students;
  }

  /**
   * returns a list of Lesson objects, if null prints out a message
   * @version 1.0
   * @return ArrayList<Lesson>
   */
  public ArrayList<Lesson> getAllLessons()
  {
    if (lessons == null)
    {
      System.out.println(
          "There are no lessons for the course " + getCourseName() + ".");
    }
    return lessons;
  }

  /**
   * writes out the information about a Course object
   * @version 1.0
   * @return String containing information about a course
   */
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

  /**
   * compares two objects and returns true if they are identical, in this case only name is required since each course has unique name e.g. SEP1X
   * @version 1.0
   * @param obj object that is compared
   * @return true (if the objects are the exact same) or false
   */
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