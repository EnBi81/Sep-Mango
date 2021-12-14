package utils;

import java.io.*;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Scanner;

import Model.*;

public class FileHandler
{
  /**
   * Read lines from text file
   * @param filePath The path of the file
   * @return A list of the lines which has been read
   * @throws FileNotFoundException The file was not found
   */
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

  /**
   * Write the content to the specified text file
   * @param filePath The text file.
   * @param content The content.
   * @throws FileNotFoundException  The file was not found
   */
  public static void writeToTextFile(String filePath, String content)
      throws FileNotFoundException
  {
    PrintWriter writer = new PrintWriter(new FileOutputStream(filePath));
    writer.write(content);

    writer.close();
  }

  /**
   * Reads an object from a binary file and converts it to a Schedule object
   * @param filePath The file to read from
   * @return The converted schedule object if the read object can be converted; otherwise null
   * @throws IOException If the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
   */
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

  /**
   * Writes a schedule object to the given file.
   * @param filePath The file path to write to
   * @param schedule The Schedule object to write
   * @throws IOException If the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
   */
  public static void writeScheduleToBinaryFile(String filePath, Schedule schedule)
      throws IOException
  {
    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));

    output.writeObject(schedule);

    output.close();
  }
}
