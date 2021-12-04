package Model;

import java.util.ArrayList;

public class TeacherList
{
  private ArrayList<Teacher> teachers;

  public TeacherList(){
    teachers = new ArrayList<>();
  }

  public void addTeacher(Teacher teacher){
    teachers.add(teacher);
  }

  public void removeTeacher(Teacher teacher){
    teachers.remove(teacher);
  }

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

  public ArrayList<Teacher> getAllTeachers(){
    return teachers;
  }
}