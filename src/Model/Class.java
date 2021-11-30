package Model;

import java.util.ArrayList;

public class Class
{
  public static Class getClassByName(String name)
  {
    return null;
  }
  public static ArrayList<Class> getClassesBySemester(int semester)
  {
    return null;
  }


  private final int semester;
  private final String className;
  private final ArrayList<Student> students;
  private final ArrayList<Course> courses;

  /**
   * Create an instance of the class Class
   * @param semester Semester of the class
   * @param className Name of the class
   */
  public Class(int semester, String className)
  {
    this.semester = semester;
    this.className = className;
    students = new ArrayList<Student>();
    courses = new ArrayList<Course>();
  }

  /**
   * Gets the semester of the class
   * @return The semester of the class
   */
  public int getSemester()
  {
    return semester;
  }

  /**
   * Gets the name of the class
   * @return The name of the class
   */
  public String getClassName()
  {
    return className;
  }

  /**
   * Add a student to the class' list
   * @param student The student that should be added
   */
  public void addStudent(Student student)
  {
    students.add(student);
  }

  /**
   * Get all the students associated with the class
   * @return List of all students in the class
   */
  public ArrayList<Student> getAllStudents()
  {
    return students;
  }

  /**
   * Assign a course with the class
   * @param course The course that should be added
   */
  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * Get all the courses associated with the class
   * @return List of all courses in the class
   */
  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }
}
