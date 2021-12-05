package Model;

import java.util.ArrayList;

public class StudentList
{
  private ArrayList<Student> students;

  public StudentList(){
    students = new ArrayList<>();
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public ArrayList<Student> getAllStudents(){ // hahahaha
    return students;
  }
}
