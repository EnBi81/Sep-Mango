package Model;

import java.util.ArrayList;

/**
 * A class containing a Student object
 * @author Agata
 * @version 1.0
 */

public class Student
{
  private String name;
  private int id;

  /**
   * Create an instance of the class ExchangeStudent
   * @param name student's name
   * @param id object's id
   */

  public Student(String name, int id)
  {
    this.name = name;
    this.id = id;
  }

  /**
   * Gets a student's first name
   * @return student's first name
   */

  public String getFirstName()
  {
    return name.split(" ")[0];
  }

  /**
   * Gets a student's last name
   * @return student's last name
   */
  public String getLastName()
  {
    String[] splitName = name.split(" ");
    return splitName[splitName.length - 1];
  }
  /**
   * Gets a student's name
   * @return student's name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets a student's id
   * @return student's id
   */
  public int getId()
  {
    return id;
  }

  /**
   * Gets a list of courses to which Student object is enrolled
   * @return the list of courses if exits
   */
  public ArrayList<Course> getAllCourses()
  {
    ArrayList<Course> allCourses = new ArrayList<Course>();

    ArrayList<Course> allStudentCourses = new ArrayList<Course>();
    for (int i = 0; i < allCourses.size(); i++)
    {
      if(allCourses.get)
    }
    {

    }
  }

  /**
   * Gets a class to which Student object is enrolled
   * @return the class to which Student object is enrolled
   */
  public VIAClass getViaClass()
  {
    return viaClass;
  }


  /**
   * Check if the Student object is an instance of the ExchangeStudent class
   * @return true if the Student object is an instance of the ExchangeStudent class
   */
  public boolean isExchange()
  {
      return this   instanceof ExchangeStudent;
  }

  /**
   * Gets a String representation of the Student object
   * @return a String containing information about Student object
   */
  public String toString()
  {
    return name + ", id: " + id + ", " + VIAClass().toString;
  }

  /**
   * Check if the object is equal to the Student object
   * @param obj object which is compared
   * @return true if the object is equal to the Student object
   */
  public boolean equals(Object obj)
  {
    if(!(obj instanceof Student))
    {
      return false;
    }

    Student one = (Student) obj;

    return id == one.id;
  }

}
