package GUI;

import Model.Lesson;
import Model.LessonList;
import ScheduleManager.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.*;
import java.util.ArrayList;

public class ControllerExtendTestWeekTab
{
  public Stage stage;
  @FXML public Spinner<Integer> spinner;

  public void initialize()
  {
    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25));
    spinner.setEditable(false);
  }

  public void extend()
  {
    int weeks = spinner.getValue();

    //Loop through all the courses
    //Get the lessons for each course
    //Copy the lessons

    LessonList actualLessonList = Manager.getSchedule().getLessonList(); //Put copied lessons here
    ArrayList<Lesson> lessons = new ArrayList<>(actualLessonList
        .getAllLessons()); //Lessons to copy

    long secondsInOneWeek = 60 * 60 * 24 * 7; // Seconds in one week

    for (int i = 0; i < lessons.size(); i++) // Loop through every lesson which we need to copy
    {
      Lesson lesson = lessons.get(i);

      for (int j = 1; j <= weeks; j++) //Loop through the week to copy the lesson
      {
        //Calculate the new start time for the current week
        long startSeconds = lesson.getStartTime().toEpochSecond(ZoneOffset.UTC) + secondsInOneWeek * j;
        //Calculate the end time for the current week
        long endSeconds = lesson.getEndTime().toEpochSecond(ZoneOffset.UTC) + secondsInOneWeek * j;

        //Create the new Start time and End time
        LocalDateTime newStartTime = LocalDateTime.ofEpochSecond(startSeconds, 0, ZoneOffset.UTC);
        LocalDateTime newEndTime = LocalDateTime.ofEpochSecond(endSeconds, 0, ZoneOffset.UTC);

        //Creating the new lesson
        Lesson newLesson = new Lesson(lesson.getCourse(), lesson.getFirstRoom(), newStartTime, newEndTime);

        //Adding the second room if there was any
        if(lesson.getSecondRoom() != null)
          newLesson.setSecondRoom(lesson.getSecondRoom());

        //Add the lesson to the course
        lesson.getCourse().addLesson(newLesson);
        //Add the lesson to the lesson list in the schedule
        actualLessonList.addLesson(newLesson);
      }
    }

    //Close the window
    stage.close();
  }
}
