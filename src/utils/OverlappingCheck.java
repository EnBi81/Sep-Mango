package utils;

import Model.Course;
import Model.Lesson;
import Model.*;
import ScheduleManager.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class OverlappingCheck
{

  public static ArrayList<Lesson> lessonCheck(Course course)
  {
    ArrayList<Course> courses = new ArrayList<>();

    for (int i = 0; i < course.getAllStudents().size(); i++)
    {
      //get all Students' courses
      ArrayList<Course> studentsCourses =  course.getAllStudents().get(i).getAllCourses();

      //if the course is not already in add it
      for (int j = 0; j < studentsCourses.size() ; j++)
      {
        if(!courses.contains(studentsCourses.get(j)))
        {
          courses.add(studentsCourses.get(j));
        }
      }

    }

    for (int i = 0; i < course.getAllTeachers().size(); i++)
    {
      //get all teachers' courses
      ArrayList<Course> teachersCourses =  course.getAllTeachers().get(i).getAllCourses();

      //if the course is not already in add it
      for (int j = 0; j < teachersCourses.size() ; j++)
      {
        if(!courses.contains(teachersCourses.get(j)))
        {
          courses.add(teachersCourses.get(j));
        }
      }
    }

    //get all lessons from the courses that have been added above
    ArrayList<Lesson> lessons = new ArrayList<>();

    for (int i = 0; i < courses.size(); i++)
    {
      lessons.addAll(courses.get(i).getAllLessons());
    }

    return lessons;
  }


  public static boolean isOverlappingLesson(Course course, LocalDateTime from, LocalDateTime to)
  {
    ArrayList<Lesson> lessons = lessonCheck(course);

    for (int i = 0; i < lessons.size(); i++)
    {

      Lesson lesson = lessons.get(i);

      if(!(lesson.getEndTime().isBefore(from) || lesson.getStartTime().isAfter(to)))
      {
        return true;
      }
    }
    return false;
  }
  public static boolean isOverLapping(Course course, ArrayList<Lesson> lessons) //course, teachers/students lessons
  {
    ArrayList<Lesson> courseLessons = course.getAllLessons();

    for (int i = 0; i <lessons.size() ; i++)
    {
      for (int j = 0; j <courseLessons.size() ; j++)
      {
        if(!(lessons.get(i).getEndTime().isBefore(courseLessons.get(j).getStartTime()) || lessons.get(i).getStartTime().isAfter(courseLessons.get(i).getEndTime())))
        {
          return true;
        }
      }
    }
    return  false;
  }
  public static boolean isOverlapping(Course course, Student student)
  {
    ArrayList<Lesson> studentLessons = new ArrayList<>();
    ArrayList<Course> studentCourses = student.getAllCourses();
    for (int i = 0; i <studentCourses.size() ; i++)
    {
      studentLessons.addAll(studentCourses.get(i).getAllLessons());
    }

     return isOverLapping(course, studentLessons);

  }

  public static boolean isOverlapping(Course course, Teacher teacher)
  {
    ArrayList<Lesson> teacherLessons = new ArrayList<>();
    ArrayList<Course> teacherCourses = teacher.getAllCourses();
    for (int i = 0; i <teacherCourses.size() ; i++)
    {
      teacherLessons.addAll(teacherCourses.get(i).getAllLessons());
    }

    return isOverLapping(course, teacherLessons);

  }


}
