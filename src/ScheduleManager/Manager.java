package ScheduleManager;

import Model.*;
import utils.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Manager
{
  private ArrayList<Student> students;
  private ArrayList<Teacher> teachers;
  private ArrayList<Room> rooms;
  private ArrayList<VIAClass> classes;
  private ArrayList<Course> courses;
  private ArrayList<ExchangeStudent> exchangeStudents;
  private String path = "Files\\";



  public void loadData() throws FileNotFoundException
  {
    classes = FileHandler.readClasses(path + "courses.txt");
    students = FileHandler.readStudents(path + "students.txt", classes);
    courses = FileHandler.readCourses(path + "courses.txt", classes);
    rooms = FileHandler.readRooms(path + "rooms.txt");
    teachers = FileHandler.readTeachers(path + "courses.txt");

  }

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

  /*public ArrayList<ExchangeStudent> getAllExchangeStudents()
  {
    ArrayList<ExchangeStudent>
    for (int i = 0; i < students.size(); i++)
    {

    }
  }
*/

  public void makeRoomConnections()
  {
    for (int i = 0; i < rooms.size(); i++)
    {
      String room = rooms.get(i).getRoomNumber();

      if(room.charAt(room.length()-1) == 'a')
      {
        String bRoom = room.substring(0, room.length()-2) + 'b';

        for (int j = 0; j < rooms.size(); j++)
        {
          if(rooms.get(j).getRoomNumber().equals(bRoom))
          {
            rooms.get(j).setConnectedRoom(rooms.get(i));
            rooms.get(i).setConnectedRoom(rooms.get(j));
          }
        }
      }
    }
  }




}
