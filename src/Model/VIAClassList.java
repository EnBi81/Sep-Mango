package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An object to hold multiple VIAClass objects.
 *
 * @author Gergo Nador
 * @version 1.0
 */
public class VIAClassList implements Serializable
{
  private ArrayList<VIAClass> classes;

  /**
   * Creates an instance of VIAClassList
   */
  public VIAClassList()
  {
    classes = new ArrayList<>();
  }

  /**
   * Gets the VIAClass object which has the same name as the argument
   * @param name The VIAClass to search for
   * @return The VIAClass object with the same name if it exists; else null.
   */
  public VIAClass getClassByName(String name)
  {
    for (VIAClass viaClass : classes)
    {
      if(viaClass.getName().equals(name))
        return viaClass;
    }

    return null;
  }

  /**
   * Adds one VIAClass instance to the list
   * @param viaClass The instance which should be added
   */
  public void addVIAClass(VIAClass viaClass)
  {
    classes.add(viaClass);
  }

  /**
   * Removes a VIAClass instance from the list
   * @param viaClass the instance which should be removed
   */
  public void removeClass(VIAClass viaClass)
  {
    classes.remove(viaClass);
  }

  /**
   * Gets an ArrayList object with all the VIAClasses object inside
   * @return An ArrayList object with the stored VIAClass instances
   */
  public ArrayList<VIAClass> getAllClasses()
  {
    return classes;
  }
}
