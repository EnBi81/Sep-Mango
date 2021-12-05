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

  /**
   * Calls the readFromTextFile method to read the file containing all rooms' data. It reads one row at a time, creates a Room object with its parameters, and then adds the new Instance of the Room class to the RoomList, which is stored in the Schedule class.
   * @param fileName the name of the file
   */
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

  /***
   * Calls the readFromTextFile method to read the file containing all courses' data. It reads one row at a time,
   * creates a Teacher object with its parameters if the teacher list it is not already containing that specific teacher.
   * Then it reads one row at a time again from the courses file, and it creates a Course object with its parameters if
   * the course list is not already containing that specific course.
   * @param fileName the name of the file
   */
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

  /**
   * Calls the readFromTextFile method to read the file containing all students' data. Firstly, by reading the first rows of the file creates the class objects without duplicates. Afterwards, reads all the rest data and creates the student objects with it. Finally, the students are being enrolled into the corresponding classes.
   * @param fileName the name of the file
   */
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
        Student student = new Student(infoStudent[3],
            Integer.parseInt(infoStudent[2]));
        schedule.getStudentList().addStudent(student);

        for (int j = 0;
             j < schedule.getVIAClassList().getAllClasses().size(); j++)
        {
          if (schedule.getVIAClassList().getAllClasses().get(j).getSemester()
              == Integer.parseInt(infoStudent[0]) && schedule.getVIAClassList()
              .getAllClasses().get(j).getName().equals(infoStudent[1]))
          {
            schedule.getVIAClassList().getAllClasses().get(j)
                .addStudent(student);
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
