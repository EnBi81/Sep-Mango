package Model;

import java.util.ArrayList;

public class LessonList
{

  private ArrayList<Lesson> lessons;

  /**
   * No arguments constructor
   * @author Uafa
   * @version 1.0
   */
  public LessonList()
  {

    lessons = new ArrayList<>();
  }

  /**
   * Add a lesson to an ArrayList which holds all lessons
   * @param lesson
   * @author Uafa
   * @version 1.0
   */

  public void addLesson(Lesson lesson)
  {
    lessons.add(lesson);
  }

  /**
   * Remove a lesson from an the ArrayList which holds all lessons
   * @param lesson
   * @author Uafa
   * @version 1.0
   */

  public void removeLesson(Lesson lesson)
  {
    lessons.remove(lesson);
  }

  /**
   * Returns all the lessons held in the ArrayList
   * @return
   * @author Uafa
   * @version 1.0
   */
  public ArrayList<Lesson> getAllLessons()
  {
    return lessons;
  }
}
