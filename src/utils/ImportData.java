package utils;

import Model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImportData
{
  public static final String separator = ",";

  public static ArrayList<String> readFromTextFile(String file)
      throws FileNotFoundException
  {
    return FileHandler.readFromTextFile(file);
  }

  public static ArrayList<Room> readRooms(String roomFile) throws
      FileNotFoundException
  {
    ArrayList<String> data = readFromTextFile(roomFile);
    ArrayList<Room> rooms = new ArrayList<>();

    for (String line : data)
    {
      String[] info = line.split(separator);
      Room room = new Room(info[0], Integer.parseInt(info[1]));
      rooms.add(room);
    }

    return rooms;
  }

  public static ArrayList<VIAClass> readClasses(String classFile) throws FileNotFoundException
  {
    ArrayList<String> data = readFromTextFile(classFile);
    ArrayList<VIAClass> classes = new ArrayList<>();

    for (String line : data)
    {
      String[] info = line.split(separator);
      VIAClass viaClass = new VIAClass(Integer.parseInt(info[0]), info[0] + info[1]);
      if(!classes.contains(viaClass))
        classes.add(viaClass);
    }

    return classes;
  }
  public static ArrayList<Course> readCourses(String courseFile, ArrayList<VIAClass> classes) throws FileNotFoundException
  {
    ArrayList<String> data = readFromTextFile(courseFile);
    ArrayList<Course> courses = new ArrayList<>();

    for(String line : data)
    {
      String[] info = line.split(separator);
      for (VIAClass viaClass : classes)
      {
        if(viaClass.getClassName().equals(info[0] + info[1]))
        {
          Course course = new Course(info[2] + info[0] + info[1], Integer.parseInt(info[4]), viaClass);

          if (!courses.contains(course))
          {
            courses.add(course);
            break;
          }
        }
      }
    }

    return courses;
  }

  public static ArrayList<Student> readStudents(String courseFile, ArrayList<VIAClass> classes) throws FileNotFoundException
  {
    ArrayList<String> data = readFromTextFile(courseFile);
    ArrayList<Student> students = new ArrayList<>();

    for (String line : data)
    {
      String[] info = line.split(separator);
      for (VIAClass viaClass : classes)
      {
        if(viaClass.getClassName().equals(info[0] + info[1]))
        {
          Student student = new Student(info[3], Integer.parseInt(info[2]), viaClass);
          students.add(student);
          break;
        }
      }
    }

    return students;
  }

  public static ArrayList<Teacher> readTeachers(String courseFile, ArrayList<Course> teachersCourses) throws FileNotFoundException
  {
    ArrayList<String> data = readFromTextFile(courseFile);
    ArrayList<Teacher> teachers  = new ArrayList<>();

    for (String line: data)
    {
      String[] info = line.split(separator);

      Teacher teacher = new Teacher(info[3]);

      if(!(teachers.contains(teacher)))
      {
        teachers.add(teacher);
      }

    }
    return teachers;
  }
}
