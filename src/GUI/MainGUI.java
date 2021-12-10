package GUI;

import Model.Course;
import Model.Lesson;
import Model.Room;
import ScheduleManager.Manager;
import javafx.application.Application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGUI
{
  public static void main(String[] args)
  {
    // just testing
    Course course = Manager.getSchedule().getCourseList().getAllCourses()
        .get(2);
    Course course1 = Manager.getSchedule().getCourseList().getAllCourses()
        .get(3);
    Room room = Manager.getSchedule().getRoomList().getAllRooms().get(1);
    Room room1 = Manager.getSchedule().getRoomList().getAllRooms().get(2);
    LocalDateTime start = LocalDateTime.of(2000, 12, 11, 23, 12);
    LocalDateTime end = LocalDateTime.of(2002, 12, 11, 23, 12);
    LocalDateTime start2 = LocalDateTime.of(2003, 12, 11, 23, 12);
    LocalDateTime end2 = LocalDateTime.of(2004, 12, 11, 23, 12);
    LocalDateTime end3 = LocalDateTime.of(2005, 12, 11, 23, 12);
    Lesson lesson = new Lesson(course, room, start, end);
    Lesson lesson2 = new Lesson(course1, room, start2, end2);
    Lesson lesson3 = new Lesson(course, room1, start, end3);

    Manager.getSchedule().getLessonList().addLesson(lesson);
    Manager.getSchedule().getLessonList().addLesson(lesson2);
    Manager.getSchedule().getLessonList().addLesson(lesson3);
    //

    Application.launch(StartMainTab.class);
  }

}
