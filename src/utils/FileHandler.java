package utils;

import java.io.*;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Scanner;

import Model.*;

public class FileHandler
{
  public static ArrayList<String> readFromTextFile(String filePath)
      throws FileNotFoundException
  {
    ArrayList<String> list = new ArrayList<>();
    Scanner scanner = new Scanner(new FileInputStream(filePath));

    while(scanner.hasNext())
      list.add(scanner.nextLine());

    scanner.close();

    return list;
  }

  public static void writeToTextFile(String filePath, String content)
      throws FileNotFoundException
  {
    PrintWriter writer = new PrintWriter(new FileOutputStream(filePath));
    writer.write(content);

    writer.close();
  }

  public static Schedule readScheduleFromBinaryFile(String filePath)
      throws IOException
  {
    ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath));
    Schedule schedule = null;

    try
    {
      schedule = (Schedule) input.readObject();
    }catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      input.close();
    }

    return schedule;
  }

  public static void writeScheduleToBinaryFile(String filePath, Schedule schedule)
      throws IOException
  {
    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));

    output.writeObject(schedule);

    output.close();
  }
}
