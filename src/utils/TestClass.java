package utils;

import Model.*;
import ScheduleManager.Manager;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TestClass
{
  public static void main(String[] args)
  {
    String folder = "Files\\",
        coursesFile = folder + "courses.txt",
        roomFile = folder + "rooms.txt",
        studentFile = folder + "students.txt";

    Manager manager = new Manager();



    test(manager.getSchedule());
  }

  public static void test(Schedule schedule)
  {
    try
    {
      writeSeparatorSpace();
      writeRoomList(schedule.getRoomList());
      writeSeparatorSpace();
      writeStudentList(schedule.getStudentList());
      writeSeparatorSpace();
      writeTeacherList(schedule.getTeacherList());
      writeSeparatorSpace();
      writeLessonList(schedule.getLessonList());
      writeSeparatorSpace();
      writeCourseList(schedule.getCourseList());
      writeSeparatorSpace();
      writeClassList(schedule.getVIAClassList(), true);

    }catch (Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  private static void writeRoomList(RoomList roomList)
  {
    write("RoomList" + intToParentheses(roomList.getAllRooms().size()), 0);
    for (var room : roomList.getAllRooms())
      writeRoom(room);
  }
  private static void writeRoom(Room room)
  {
    write(room.getRoomName(), 1);
    write("Capacity: " + room.getCapacity(), 2);
    write("Has connected room: " + room.hasConnectedRoom(), 2);
  }

  private static void writeStudentList(StudentList studentList)
  {
    write("StudentList" + intToParentheses(studentList.getAllStudents().size()), 0);
    for (var student : studentList.getAllStudents())
    {
      writeStudent(student, 0);
    }
  }
  private static void writeStudent(Student student, int level)
  {
    write(student.getName() + intToParentheses(student.getId()), level + 1);
  }

  private static void writeTeacherList(TeacherList teacherList)
  {
    write("TeacherList" + intToParentheses(teacherList.getAllTeachers().size()),0);
    for (var teacher : teacherList.getAllTeachers())
      writeTeacher(teacher, 0);

  }
  private static void writeTeacher(Teacher teacher, int level)
  {
    write(teacher.getName(), level + 1);
  }

  private static void writeLessonList(LessonList lessonList)
  {
    write("LessonList" + intToParentheses(lessonList.getAllLessons().size()), 0);
    write("Lesson count (should be 0): " + lessonList.getAllLessons().size(), 1);
  }

  private static void writeCourseList(CourseList courseList)
  {
    write("CourseList" + intToParentheses(courseList.getAllCourses().size()), 0);
    for (var course : courseList.getAllCourses())
    {
      writeCourse(course, 0);
    }
  }
  private static void writeCourse(Course course, int level)
  {
    write(course.getCourseName(), level + 1);
    write("Ects: " + course.getEcts(), level + 2);
    write("Class: " + (course.getVIAClass() == null ? "null" :
        course.getVIAClass().getName()), level + 2);
    write("Teachers" + intToParentheses(course.getAllTeachers().size()), level + 2);
    for (var teacher : course.getAllTeachers())
      writeTeacher(teacher, level + 2);
    write("Students" + intToParentheses(course.getAllStudents().size()), level + 2);
    for (var student : course.getAllStudents())
      writeStudent(student, level + 2);
  }

  private static void writeClassList(VIAClassList viaClassList, boolean writeFullCourse)
  {
    write("VIAClassList" + intToParentheses(viaClassList.getAllClasses().size()), 0);
    for (var viaClass : viaClassList.getAllClasses())
      writeClass(viaClass, writeFullCourse);
  }
  private static void writeClass(VIAClass viaClass, boolean writeFullCourse)
  {
    write("Class " + viaClass.getName(), 1);
    write("Students" + intToParentheses(viaClass.getAllStudents().size()), 2);
    for (var student : viaClass.getAllStudents())
      writeStudent(student, 2);
    write("Courses" + intToParentheses(viaClass.getAllCourses().size()), 2);
    for (var course : viaClass.getAllCourses())
    {
      if(writeFullCourse)
        writeCourse(course, 2);
      else write(course.getCourseName(), 3);
    }
  }




  private static String intToParentheses(int num)
  {
    return " (" + num + ")";
  }
  private static void writeSeparatorSpace()
  {
    System.out.println();
    System.out.println();
  }
  private static void write(String text, int level)
  {
    System.out.println("\t".repeat(level) + text);
  }

}
