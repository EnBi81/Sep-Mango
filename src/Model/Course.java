package Model;

import ScheduleManager.Manager;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    Schedule schedule = Manager.getSchedule();

    for (VIAClass viaClass: schedule.getVIAClassList().getAllClasses()
         )
    {
      if (courseName.endsWith(viaClass.getName())){
        return viaClass;
      }
    }
    return null;
  }

  /**
   * adds a student to the ArrayList of students, if the list already contains the student, does nothing
   * @param student student to be added
   */
  public void addStudent(Student student)
  {
    if (!students.contains(student)){
      students.add(student);
    }
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
   * adds a teacher to the ArrayList of teachers, if the list already contains the teacher, does nothing
   * @param teacher teacher to be added
   */
  public void addTeacher(Teacher teacher)
  {
    if (!teachers.contains(teacher)){
      teachers.add(teacher);
    }
  }

  /**
   * removes a teacher from the ArrayList of teachers
   * @param teacher teacher to be removed
   */
  public void removeTeacher(Teacher teacher){
    teachers.remove(teacher);
  }

  /**
   * creates a lesson, which is added to the lessonList and assigned to the course
   * @param course course in which the lesson is created and to which the lesson is assigned
   * @param room a room in which the lesson will take place
   * @param startTime a time the lesson starts
   * @param endTime a time the lesson ends
   * @return the lesson object which was created
   */
  public Lesson createLesson(Course course, Room room, LocalDateTime startTime, LocalDateTime endTime){
    Lesson lesson = new Lesson(course, room, startTime, endTime);

    course.addLesson(lesson);

    Manager.getSchedule().getLessonList().addLesson(lesson);

    return lesson;
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
    return getCourseName();
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