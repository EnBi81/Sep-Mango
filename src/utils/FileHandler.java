package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Scanner;

import Model.*;

public class FileHandler
{

  public static ArrayList<String> readFromTextFile(String textFile)
      throws FileNotFoundException
  {
    ArrayList<String> data = new ArrayList<>();

    Scanner scanner = new Scanner(new FileInputStream(textFile));

    while (scanner.hasNext())
    {
      String nextLine = scanner.nextLine();
      if(nextLine.isEmpty())
        continue;
      data.add(nextLine);
    }

    scanner.close();

    return data;
  }

  public static void writeToTextFile(String fileName, String content)
      throws FileNotFoundException
  {
    PrintWriter writeToFile = null;

    try
    {
      FileOutputStream fileOutStream = new FileOutputStream(fileName);
      writeToFile = new PrintWriter(fileOutStream);
      writeToFile.println(content);
    }
    finally
    {
      if (writeToFile != null)
      {
        writeToFile.close();
      }
    }
  }


}
