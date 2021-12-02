package ScheduleManager;

import Model.*;

import java.util.ArrayList;

public class Manager
{
  private ArrayList<Student> students;
  private ArrayList<Teacher> teachers;
  private ArrayList<Room> rooms;
  private ArrayList<VIAClass> classes;
  private ArrayList<Course> courses;


  public ArrayList<Student> getAllStudent()
  {
    return students;
  }
  public ArrayList<Teacher> getAllTeachers()
  {
    return teachers;
  }
  public ArrayList<Room> getAllRooms()
  {
    return rooms;
  }
  public ArrayList<VIAClass> getAllClasses()
  {
    return classes;
  }
  public ArrayList<Course> getAllCourses()
  {
    return courses;
  }
}
