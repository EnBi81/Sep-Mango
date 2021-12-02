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

  public void addTeacher(Teacher teacher) throws CollisionException{
    teachers.add(teacher);
  }

  public void removeTeacher(Teacher teacher){
    if (teachers.contains(teacher)){
      teachers.remove(teacher);

    }
    else {
      System.out.println(teacher.getName() + " does not teach the course " + getCourseName());
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

  public boolean equals(Object obj){
    if (!(obj instanceof Course)){
      return false;
    }

    Course temp = (Course) obj;

    if (this.teachers == null){
      return this.courseName.equals(temp.courseName) && this.ects == temp.ects && this.viaClass.equals(temp.viaClass) && temp.teachers == null && this.lessons.equals(temp.lessons) && this.students.equals(temp.students);
    }

    if (this.lessons == null){
      return this.courseName.equals(temp.courseName) && this.ects == temp.ects && this.viaClass.equals(temp.viaClass) && this.teachers.equals(temp.teachers) && temp.lessons == null && this.students.equals(temp.students);
    }

    if (this.students == null){
      return this.courseName.equals(temp.courseName) && this.ects == temp.ects && this.viaClass.equals(temp.viaClass) && this.teachers.equals(temp.teachers) && this.lessons.equals(temp.lessons) && temp.students == null;
    }

    else {
      return this.courseName.equals(temp.courseName) && this.ects == temp.ects && this.viaClass.equals(temp.viaClass) && this.teachers.equals(temp.teachers) && this.lessons.equals(temp.lessons) && this.students.equals(temp.students);

    }
    }

    public String toString(){
    String str= "";

    str += getCourseName() + " has " + getEcts() + " ECTS points.\nTeachers: ";

      for (Teacher teacher: teachers
      )
      {
        str += teacher.getName() + ", ";
      }
      str+= "\nClass: " + getViaClass().getClassName();
      str += "\nLessons: ";
      for (Lesson lesson: lessons
      )
      {
        str += lesson.toString() + "\n";
      }
      return str;
    }
  }