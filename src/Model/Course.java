package Model;

import utils.CollisionException;

import java.util.ArrayList;

public class Course {

  private String courseName;
  private int ects;
  private ArrayList<Teacher> teachers;
  private ArrayList<Lesson> lessons;
  private ArrayList<Student> students;
  private VIAClass viaClass;

  public Course(String courseName, int ects, ArrayList<Teacher> teachers, VIAClass viaClass){
    this.courseName = courseName;
    this.ects = ects;
    this.teachers = teachers;
    lessons = new ArrayList<>();
    this.viaClass = viaClass;
    students = viaClass.getAllStudents();
    //idk is this legal?? lol
    assignCourseToTeacher();
  }

  public String getCourseName()
  {
    return courseName;
  }

  public int getEcts()
  {
    return ects;
  }

  public VIAClass getViaClass(){
    return viaClass;
  }

  public void addStudent(Student student) throws CollisionException
  {
    //add attributes according to the Student constructor
    students.add(student);
  }

  public void removeStudent(Student student){
    if (students.contains(student)){
      students.remove(student);

    }
    else {
      System.out.println("Course " + getCourseName() + " does not contain this student.");
    }
  }

  public void addLesson(Lesson lesson) throws CollisionException{
    lessons.add(lesson);
  }

  public void removeLesson(Lesson lesson){
    if (lessons.contains(lesson)){
      lessons.remove(lesson);

    }
    else {
      System.out.println("Course " + getCourseName() + " does not contain this lesson.");
    }
  }

  public ArrayList<Teacher> getAllTeachers(){
    if (teachers == null){
      System.out.println("There are no teachers for the course " + getCourseName() + ".");
    }
    return teachers;
  }

  public ArrayList<Student> getAllStudents(){
    if (students == null){
      System.out.println("There are no students for the course " + getCourseName() + ".");
    }
    return students;
  }

  public ArrayList<Lesson> getAllLessons(){
    if (lessons == null){
      System.out.println("There are no lessons for the course " + getCourseName() + ".");
    }
    return lessons;
  }

  public void assignCourseToTeacher(){
    for (Teacher teacher:teachers
         )
    {
      teacher.addCourse(copy());
    }
  }

  public Course copy(){
    return new Course(this.getCourseName(), this.getEcts(),this.getAllTeachers(),this.getViaClass());
  }



}
