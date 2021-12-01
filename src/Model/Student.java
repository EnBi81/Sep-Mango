package Model;

import java.util.ArrayList;

/**
 * A class containing a Student object
 * @author Agata
 * @version 1.0
 */

public class Student
{
  private String firstName;
  private String lastName;
  private int id;

  private ArrayList<Course> courses;

  private VIAClass viaClass;

  /**
   * Create an instance of the class ExchangeStudent
   * @param firstName object's first name
   * @param lastName object's last name
   * @param id object's id
   * @param viaClass the class to which the object is enrolled
   */

  public Student(String firstName, String lastName,int id, VIAClass viaClass)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
    this.viaClass = viaClass;
    courses = new ArrayList<Course>();
  }

  /**
   * Gets a Student object's first name
   * @return Student object's first name
   */

  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Gets a Student object's last name
   * @return Student object's last name
   */

  public String getLastName()
  {
    return lastName;
  }

  /**
   * Gets a Student object's id
   * @return Student object's id
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
    if(courses.size()>0)
    {
      return courses;
    }
    else
    {
      return null;
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
   * Adds a course to the list of all courses
   * @param course the course to add to the list
   */

  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * Removes a course from the list of all courses
   * @param course the course to remove from the list
   */

  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  /**
   * Check if the Student object is an instance of the ExchangeStudent class
   * @param student the student who is checked
   * @return true if the Student object is an instance of the ExchangeStudent class
   */

  public boolean isExchange(Student student)
  {
      return student instanceof ExchangeStudent;
  }

  /**
   * Gets a String representation of the Student object
   * @return a String containing information about Student object
   */

  public String toString()
  {
    return "First name: " + firstName + ", Last name: " + lastName + ", id: " + id + ", " + viaClass.toString();
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

    return firstName.equals(one.firstName) && lastName.equals(one.lastName) && id == one.id && viaClass.equals(one.viaClass);
  }

}
