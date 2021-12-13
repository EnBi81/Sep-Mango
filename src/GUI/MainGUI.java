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
    Application.launch(StartMainTab.class);
  }

}
