package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class LessonList implements Serializable
{
  /**
   * LessonList class which stores all the Lessons
   */

  private ArrayList<Lesson> lessons;

  /**
   * No arguments constructor
   */
  public LessonList()
  {

    lessons = new ArrayList<>();
  }

  /**
   * Add a lesson to an ArrayList which holds all lessons
   * @param lesson the lesson to be added
   */

  public void addLesson(Lesson lesson)
  {
    lessons.add(lesson);
  }

  /**
   * Remove a lesson from an the ArrayList which holds all lessons
   * @param lesson the lesson to be removed
   */

  public void removeLesson(Lesson lesson)
  {
    lessons.remove(lesson);
  }

  /**
   * Returns all the lessons held in the ArrayList
   * @return an ArrayList with all Lesson objects inside it
   */
  public ArrayList<Lesson> getAllLessons()
  {
    return lessons;
  }
}
