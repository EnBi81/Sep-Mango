package Model;

import java.util.ArrayList;

/**
 * a class containing a list of Teacher objects
 * @author Simon Mayer
 * @version 1.0
 */
public class TeacherList
{
  private ArrayList<Teacher> teachers;

  /**
   * creates an instance of the class TeacherList
   * @version 1.0
   */
  public TeacherList(){
    teachers = new ArrayList<>();
  }

  /**
   * adds a teacher to the list
   * @version 1.0
   * @param teacher teacher to be added
   */
  public void addTeacher(Teacher teacher){
    teachers.add(teacher);
  }

  /**
   * removes a teacher from the list
   * @version 1.0
   * @param teacher teacher to be removed
   */
  public void removeTeacher(Teacher teacher){
    teachers.remove(teacher);
  }

  /**
   * returns a teacher with specific name
   * @version 1.0
   * @return a teacher
   * @param name the name of the returned teacher
   */
  public Teacher getTeacherByName(String name){
    Teacher returnTeacher = null;
    for (Teacher teacher: teachers
         )
    {
      if (teacher.getName().equalsIgnoreCase(name)){
        returnTeacher = teacher;
      }
      else{
        System.out.println("No teacher with this name.");
      }
    }
    return returnTeacher;
  }

  /**
   * returns a list of Teacher objects
   * @version 1.0
   * @return ArrayList<Teacher>
   */
  public ArrayList<Teacher> getAllTeachers(){
    return teachers;
  }
}