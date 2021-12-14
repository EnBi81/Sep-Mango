package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * a class containing a list of Teacher objects
 * @author Simon Mayer
 * @version 1.0
 */
public class TeacherList implements Serializable
{
  private ArrayList<Teacher> teachers;

  /**
   * creates an instance of the class TeacherList
   * */
  public TeacherList(){
    teachers = new ArrayList<>();
  }

  /**
   * adds a teacher to the list
   * @param teacher teacher to be added
   */
  public void addTeacher(Teacher teacher){
    teachers.add(teacher);
  }

  /**
   * returns a teacher with specific name
   * @return a teacher
   * @param name the name of the returned teacher
   */
  public Teacher getTeacherByName(String name){
    for (Teacher teacher: teachers
         )
    {
      if (teacher.getName().equalsIgnoreCase(name)){
        return teacher;
      }
    }
    return null;
  }

  /**
   * returns a list of Teacher objects
   * @return list of all teachers who teach a course
   */
  public ArrayList<Teacher> getAllTeachers(){
    return teachers;
  }
}