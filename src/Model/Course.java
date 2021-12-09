package Model;

import ScheduleManager.Manager;
import utils.CollisionException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * a class containing information about a Course object
 * @author Simon Mayer
 * @version 1.0
 */
public class Course implements Serializable
{

  private String courseName;
  private int ects;
  private ArrayList<Teacher> teachers;
  private ArrayList<Lesson> lessons;
  private ArrayList<Student> students;
  private Schedule schedule = Manager.getSchedule();

  /**
   * three parameter constructor which creates an instance of a class Course
   * @param courseName name of a course
   * @param ects number of ects a course has
   */
  public Course(String courseName, int ects)
  {
    this.courseName = courseName;
    this.ects = ects;
    this.teachers = new ArrayList<>();
    this.lessons = new ArrayList<>();
    this.students = new ArrayList<>();
  }

  /**
   * return the name of a Course object
   * @return name of a course
   */
  public String getCourseName()
  {
    return courseName;
  }

  /**
   * return the ects points of a Course object
   * @return number of ects points
   */
  public int getEcts()
  {
    return ects;
  }

  /**
   * return the class of a Course object
   * @return class which has the course
   */
  public VIAClass getVIAClass()
  {
    VIAClass viaClass = null;
    Schedule schedule = Manager.getSchedule();
    String classInCourse = (Integer.toString(getCourseName().charAt(getCourseName().length()-2)) + getCourseName().charAt(getCourseName().length()-1));

    for (VIAClass className: schedule.getVIAClassList().getAllClasses()
         )
    {
      if (className.getName().equals(classInCourse)){
        viaClass = className;
      }
    }
    return viaClass;

  }

  /**
   * adds a student to the ArrayList of students
   * @param student student to be added
   */
  public void addStudent(Student student)
  {
    students.add(student);
  }

  /**
   * removes a student from the ArrayList of students
   * @param student student to be removed
   */
  public void removeStudent(Student student)
  {
    students.remove(student);
  }

  /**
   * adds a lesson to the ArrayList of lessons
   * @param lesson lesson to be added
   */
  public void addLesson(Lesson lesson)
  {
    lessons.add(lesson);
  }

  /**
   * removes a lesson from the ArrayList of lessons
   * @param lesson lesson to be removed
   */
  public void removeLesson(Lesson lesson)
  {
    lessons.remove(lesson);
  }

  /**
   * adds a teacher to the ArrayList of teachers
   * @param teacher teacher to be added
   */
  public void addTeacher(Teacher teacher)
  {
    teachers.add(teacher);
  }

  /**
   * returns a list of Teacher objects
   * @return teachers who teach one particular course
   */
  public ArrayList<Teacher> getAllTeachers()
  {
    return teachers;
  }

  /**
   * returns a list of Student objects
   * @return students who o are enrolled in one particular course
   */
  public ArrayList<Student> getAllStudents()
  {
    return students;
  }

  /**
   * returns a list of Lesson objects
   * @return lesson of one particular course
   */
  public ArrayList<Lesson> getAllLessons()
  {
    return lessons;
  }

  /**
   * writes out the information about a Course object
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
    str += "\nClass: " + getVIAClass().getName() + ",";
    str += "\nLessons: ";
    for (Lesson lesson : lessons)
    {
      str += lesson.toString() + "\n";
    }
    return str;
  }

  /**
   * compares two objects and returns true if they are identical, in this case only name is required since each course has unique name e.g. SEP1X
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