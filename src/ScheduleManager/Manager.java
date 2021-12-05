package ScheduleManager;

import Model.*;
import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Manager
{
  private String fileName;
  private String path = "Files\\";
  private String separator = ",";
  private Schedule schedule;

  public void loadRoomData(String fileName)
  {

    ArrayList<String> roomData;
    try
    {
      roomData = FileHandler.readFromTextFile(path + fileName);
      for (int i = 0; i < roomData.size(); i++)
      {
        String[] info = roomData.get(i).split(separator);

        schedule.getRoomList()
            .addRoom(new Room(info[0], Integer.parseInt(info[1])));
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  public void loadCourseData(String fileName)
  {

    ArrayList<String> courseData;
    try
    {
      courseData = FileHandler.readFromTextFile(fileName);
      //loading teachers first
      for (int i = 0; i < courseData.size(); i++)
      {
        String[] infoTeachers = courseData.get(i).split(separator);
        Teacher teacher = new Teacher(infoTeachers[3]);

        if (!schedule.getTeacherList().getAllTeachers().contains(teacher))
        {
          schedule.getTeacherList().addTeacher(teacher);
        }
      }

      //load course information
      for (int i = 0; i < courseData.size(); i++)
      {
        String[] info = courseData.get(i).split(separator);
        Course course = new Course(info[2] + info[0] + info[1],
            Integer.parseInt(info[4]));
        if (!schedule.getCourseList().getAllCourses().contains(course))
          schedule.getCourseList().addCourse(course);
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  public void loadStudentData(String fileName)
  {
    ArrayList<String> studentData;
    try
    {
      studentData = FileHandler.readFromTextFile(path + fileName);
      //creating a class
      for (int i = 0; i < studentData.size(); i++)
      {
        String[] infoClass = studentData.get(i).split(separator);
        VIAClass viaClass = new VIAClass(Integer.parseInt(infoClass[0]),
            infoClass[1]);
        if (!schedule.getVIAClassList().getAllClasses().contains(viaClass))
        {
          schedule.getVIAClassList().addVIAClass(viaClass);
        }
      }
      //creating a student and enrolling them into a class
      for (int i = 0; i < studentData.size(); i++)
      {
        String[] infoStudent = studentData.get(i).split(separator);
        Student student = new Student(infoStudent[3], Integer.parseInt(infoStudent[2]));
        schedule.getStudentList().addStudent(student);

        for (int j = 0;
             j < schedule.getVIAClassList().getAllClasses().size(); j++)
        {
          if (schedule.getVIAClassList().getAllClasses().get(j).getSemester()
              == Integer.parseInt(infoStudent[0]) && schedule.getVIAClassList()
              .getAllClasses().get(j).getName().equals(infoStudent[1]))
          {
            schedule.getVIAClassList().getAllClasses().get(j).addStudent(student);
          }
        }
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

}
