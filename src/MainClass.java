import Model.Teacher;
import Model.VIAClass;
import ScheduleManager.Manager;
import utils.ImportData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainClass
{
  public static void main(String[] args) throws FileNotFoundException
  {
    Manager manager = new Manager();

    manager.loadData();

    ArrayList<VIAClass> classes = manager.getAllClasses();
    ArrayList<Teacher> teachers = manager.getAllTeachers();



    for (int i = 0; i < classes.size(); i++)
    {
      System.out.println(classes.get(i).getClassName());
      System.out.println(classes.get(i).getAllStudents().size());

    }

    for (int i = 0; i < teachers.size(); i++)
    {
      System.out.println(teachers.get(i).getName());
      for (int j = 0; j < teachers.get(i).getAllCourses().size() ; j++)
    {
      System.out.println("\t" + teachers.get(i).getAllCourses().get(j).getCourseName());

    }
    }

    for (int i = 0; i < s; i++)
    {

    }




  }
}
