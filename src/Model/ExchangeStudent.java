package Model;

/**
 * A class containing a ExchangeStudent object
 * @author Agata
 * @version 1.0
 */

public class ExchangeStudent extends Student
{
  /**
   * Create an instance of the class ExchangeStudent
   * @param firstName object's first name
   * @param lastName object's last name
   * @param id object's id
   * @param viaClass the class to which the object is enrolled
   */
  public ExchangeStudent(String firstName, String lastName, int id, Class viaClass)
  {
    super(firstName,lastName,id,viaClass);
  }
}
