package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class VIAClassList implements Serializable
{
  private ArrayList<VIAClass> classes;

  public VIAClassList()
  {
    classes = new ArrayList<>();
  }

  public VIAClass getClassByName(String name)
  {
    for (VIAClass viaClass : classes)
    {
      if(viaClass.getName().equals(name))
        return viaClass;
    }

    return null;
  }

  public void addVIAClass(VIAClass viaClass)
  {
    classes.add(viaClass);
  }

  public void removeClass(VIAClass viaClass)
  {
    classes.remove(viaClass);
  }

  public ArrayList<VIAClass> getAllClasses()
  {
    return classes;
  }
}
