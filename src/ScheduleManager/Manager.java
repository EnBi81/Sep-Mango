package ScheduleManager;

import Model.Room;
import Model.RoomList;
import Model.Student;
import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Manager
{
  private String fileName;
  private String path="Files\\";
  private String separator=",";
  
  public void LoadRoomData(String fileName) {
    RoomList rooms = new RoomList();
    ArrayList<String> roomData;
    try {
      roomData = FileHandler.readFromTextFile(path+fileName);
      for (int i = 0; i < roomData.size(); i++) {
        String[] info = roomData.get(i).split(separator);
        rooms.addRoom(new Room(info[0],Integer.parseInt(info[1])));
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  public void LoadCourseData(String fileName)
  {

    ArrayList<String> courseData;
    try {
      courseData = FileHandler.readFromTextFile(fileName);
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  public void LoadStudentData(String fileName) {
    ArrayList<String> studentData;
    try {
      studentData = FileHandler.readFromTextFile(path + fileName);
      
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  


}
