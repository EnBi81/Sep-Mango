package ScheduleManager;

import Model.*;
import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A manager providing an access point to the schedule, with reading and writing from/ti file support.
 * @author
 * @version 1.1
 */
public class Manager
{
  private static String fileName;
  private static String separator = ",";
  private static Schedule schedule = null;
  private static String savedScheduleFile = "Files\\schedule.bin";

  /**
   * Gets the global schedule object
   * @return the schedule Object
   */
  public static Schedule getSchedule()
  {

    if(schedule == null)
    {
      try
      {
        schedule = FileHandler.readScheduleFromBinaryFile(savedScheduleFile);
      }
      catch (IOException e)
      {
        importData();
      }
    }
    return schedule;
  }

  /***
   * Saves the schedule to binary file.
   */
  public static void saveSchedule()
  {
    try
    {
      FileHandler.writeScheduleToBinaryFile(savedScheduleFile,schedule);
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }

  }

  /***
   * Imports data from the files to schedule object.
   */
 public static void importData()
  {
    schedule = new Schedule();

    String folder = "Files\\",
        coursesFile = folder + "courses.txt",
        roomFile = folder + "rooms.txt",
        studentFile = folder + "students.txt";

    loadRoomData(roomFile);
    loadStudentData(studentFile);
    loadCourseData(coursesFile);

    try
    {
      FileHandler.writeScheduleToBinaryFile(savedScheduleFile, schedule);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  /**
   * Calls the readFromTextFile method to read the file containing all rooms' data. It reads one row at a time, creates
   * a Room object with its parameters, and then adds the new Instance of the Room class to the RoomList, which is
   * stored in the Schedule class.
   *
   * @param fileName the name of the file
   */
  public static void loadRoomData(String fileName)
  {

    ArrayList<String> roomData;
    try
    {
      roomData = FileHandler.readFromTextFile(fileName);
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
   * creates a ViaClass object with its parameters if the viaClass list it is not already containing that specific
   * class. Then it reads one row at a time again from the courses file, and it creates a Course object with its
   * parameters if the course list is not already containing that specific course, also it assigns courses for classes.
   * In the third for loop Teacher object are made if the teacher is not already containing it, and then teachers
   * instances are assigned to the course.
   * @param fileName the name of the file
   */
  public static void loadCourseData(String fileName)
  {

    ArrayList<String> courseData;
    try
    {
      courseData = FileHandler.readFromTextFile(fileName);
      //loading viaClass without duplicating

      for (int i = 0; i < courseData.size(); i++)
      {
        String[] infoClass = courseData.get(i).split(separator);
        VIAClass viaClass = new VIAClass(Integer.parseInt(infoClass[0]),
            infoClass[0] + infoClass[1]);
        if (!schedule.getVIAClassList().getAllClasses().contains(viaClass))
        {
          schedule.getVIAClassList().addVIAClass(viaClass);
        }
      }

      //load course info to a classes
      for (int i = 0; i < courseData.size(); i++)
      {
        String[] info = courseData.get(i).split(separator);
        VIAClass viaClass = schedule.getVIAClassList()
            .getClassByName(info[0] + info[1]);
        Course course = new Course(info[2] + info[0] + info[1],
            Integer.parseInt(info[4]));
        if (!schedule.getCourseList().getAllCourses().contains(course))
        {
          schedule.getCourseList().addCourse(course);
          viaClass.addCourse(course);
          course.getAllStudents().addAll(viaClass.getAllStudents());
        }
      }
      //load teacher info without duplicating
      for (int i = 0; i < courseData.size(); i++)
      {
        String[] infoTeachers = courseData.get(i).split(separator);
        Course course = schedule.getCourseList().getCourseByName(infoTeachers[2] + infoTeachers[0] + infoTeachers[1]);
        Teacher teacher = new Teacher(infoTeachers[3]);

        if (!schedule.getTeacherList().getAllTeachers().contains(teacher))
        {
          schedule.getTeacherList().addTeacher(teacher);
        }
        else
        {
          teacher = schedule.getTeacherList().getTeacherByName(infoTeachers[3]);
        }

        course.addTeacher(teacher);//assign a teacher to a course
      }

    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  /**
   * Calls the readFromTextFile method to read the file containing all students'
   * data. Firstly, by reading the first rows of the file creates the class
   * objects without duplicates. Afterwards, reads all the rest data and creates
   * the student objects with it. Finally, the students are being enrolled into
   * the corresponding classes.
   *
   * @param fileName the name of the file
   */
  public static void loadStudentData(String fileName)
  {
    ArrayList<String> studentData;
    try
    {
      studentData = FileHandler.readFromTextFile(fileName);
      //creating a class
      for (int i = 0; i < studentData.size(); i++)
      {
        String[] infoClass = studentData.get(i).split(separator);
        VIAClass viaClass = new VIAClass(Integer.parseInt(infoClass[0]),
            infoClass[0] + infoClass[1]);
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

        VIAClass viaClass = schedule.getVIAClassList().getClassByName(infoStudent[0] + infoStudent[1]);
        viaClass.addStudent(student);
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }


  }

}
