package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class containing a list of students
 * @author Agata
 * @version 1.0
 */
public class StudentList implements Serializable
{
  private ArrayList<Student> students;

  /**
   * Creates a new list of students
   */
  public StudentList(){
    students = new ArrayList<>();
  }

  /**
   * Gets student with specified name
   * @param name that the returned student should have
   * @return A student object which matches the name
   */
  public Student getStudentByName(String name)
  {
    for (int i = 0; i < students.size(); i++)
    {
      if(students.get(i).getName().equals(name))
      {
        return students.get(i);
      }
    }
    return null;
  }

  /**
   * Gets student with specified id
   * @param id that the returned student should have
   * @return A student object that matches the id given in the parameter
   */
  public Student getStudentById(int id)
  {
    for (int i = 0; i < students.size(); i++)
    {
      if(students.get(i).getId()==id)
      {
        return students.get(i);
      }
    }
    return null;
  }

  /**
   * Adds student to the students' list
   * @param student which should be added
   */
  public void addStudent(Student student){
    students.add(student);
  }

  /**
   * Removes student from the students' list
   * @param student which should be removed
   */
  public void removeStudent(Student student)
  {
    students.remove(student);
  }

  /**
   * Gets a list of all students
   * @return list of all students
   */
  public ArrayList<Student> getAllStudents()
  {
    return students;
  }
}
