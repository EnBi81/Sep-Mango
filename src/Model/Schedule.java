package Model;

import java.io.Serializable;

/**
 * A class storing a list of rooms, teachers, lessons, courses, students and VIA's classes
 * @author Agata
 * @version 1.0
 */

public class Schedule implements Serializable
{
private RoomList roomList;
private TeacherList teacherList;
private LessonList lessonList;
private CourseList courseList;
private StudentList studentList;
private VIAClassList viaClassList;

  /**
   * Creates a new instance of schedule object
   * @param lessonList list of lessons
   * @param roomList list of rooms
   * @param viaClassList list of via's classes
   * @param courseList list of courses
   * @param studentList list of student
   * @param teacherList list of teachers
   */
  public Schedule(LessonList lessonList, RoomList roomList, VIAClassList viaClassList,CourseList courseList, StudentList studentList, TeacherList teacherList)
{
  this.lessonList = lessonList;
  this.roomList = roomList;
  this.viaClassList = viaClassList;
  this.courseList = courseList;
  this.studentList = studentList;
  this.teacherList = teacherList;
}
  /**
   * Gets a list of lessons
   * @return list of lessons
   */

  public LessonList getLessonList()
{
  return lessonList;
}
  /**
   * Gets a list of rooms
   * @return list of rooms
   */
public RoomList getRoomList()
{
  return roomList;
}
  /**
   * Gets a list of via's classes
   * @return list of via's classes
   */
public VIAClassList getVIAClassList(){
  return viaClassList;
}
  /**
   * Gets a list of courses
   * @return list of courses
   */
public CourseList getCourseList()
{
  return courseList;
}
  /**
   * Gets a list of students
   * @return list of students
   */
public StudentList getStudentList()
{
  return studentList;
}
  /**
   * Gets a list of teachers
   * @return list of teachers
   */
public TeacherList getTeacherList()
{
  return teacherList;
}





}
