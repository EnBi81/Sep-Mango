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
  /**
   * Gets all lesson from all the students and teachers enrolled to the course
   * @param course The course to get from all the students' and teachers' lessons
   * @return A list of lessons to which the students and teachers are assigned
   */
  public static ArrayList<Lesson> lessonCheck(Course course)
  {
    //We initialize a new ArrayList and assign it to variable
    ArrayList<Course> courses = new ArrayList<>();//Initializing takes 1 and assigning takes 1
    //We loop through all students and put their courses  in a courses variable without duplicates
    for (int i = 0; i < course.getAllStudents().size(); i++)//This takes n
    {
      //We get all Students' courses
      ArrayList<Course> studentsCourses =  course.getAllStudents().get(i).getAllCourses();//This takes 2

     //We loop through student's courses
      for (int j = 0; j < studentsCourses.size() ; j++)//For each iteration this takes n, so combined this take n^2
      {
        //if the course is not already in add it
        if(!courses.contains(studentsCourses.get(j)))//The contains takes n, so in total it takes n^3
        {
          //We add student's course to courses list
          courses.add(studentsCourses.get(j));//This takes 1
        }
      }

    }
    ////We loop through all teacher and put their courses  in a courses variable without duplicates
    for (int i = 0; i < course.getAllTeachers().size(); i++)//Initializing takes 1 and assigning takes 1
    {
      //We get all teachers' courses
      ArrayList<Course> teachersCourses =  course.getAllTeachers().get(i).getAllCourses();//This takes 2

      //We loop through student's courses
      for (int j = 0; j < teachersCourses.size() ; j++)//For each iteration this takes n, so combined this take n^2
      {
        //if the course is not already in add it
        if(!courses.contains(teachersCourses.get(j)))//The contains takes n, so in total it takes n^3
        {
          //We add student's course to courses list
          courses.add(teachersCourses.get(j));//This takes 1
        }
      }
    }

    //We initialize a new ArrayList and assign it to variable
    ArrayList<Lesson> lessons = new ArrayList<>();//This takes 2
    // We add all the lessons from the courses to the lessons variable
    for (int i = 0; i < courses.size(); i++)//this takes n
    {
      //We add all lessons to Arraylist
      lessons.addAll(courses.get(i).getAllLessons());//AddAll method takes n, therefore combined takes n^2
    }

    return lessons;//Return takes 1

    /*
    We didn't feel the need to use recursion, therefore we don't have a base case
    We loop through all the students from specified course, the time complexity of the loop is n^3
    We loop through all the teachers from specified course, the time complexity of the loop is n^3
    We add all the lessons from every course to lessons ArrayList, the time complexity of the loop is n^2
    T(n) = 2 + n^3 + 3 + n^3 + 3 + 2 + n^2 + 1 = 2n^3 + n^2 + 11, so ignoring constants and the coefficient,
    we get T(n) = O(n^3)
    We chose this method to analyze, because we found this method much more complex than the average methods,
    as it contains nested for loops.
     */
  }

  /**
   * Checks if the time period is occupied for the students or the teachers assigned to the course (the time period is overlapping other lessons)
   * @param course The course to check
   * @param from The start of the time period
   * @param to The end of the time period
   * @return True if there is any overlapping; otherwise false.
   */
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

  /**
   * Check is there is an overlapping between a course's lessons and a list of lessons
   * @param course The course to compare its lesson list
   * @param lessons The lesson list to compare the course's lessons against
   * @return True if there is any overlapping; otherwise false.
   */
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

  /**
   * Check is there is an overlapping between a course's lessons and a student's lessons
   * @param course The course to compare its lesson list
   * @param student The student to compare its lessons
   * @return True if there is any overlapping; otherwise false.
   */
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

  /**
   * Check is there is an overlapping between a course's lessons and a teacher's lessons
   * @param course The course to compare its lesson list
   * @param teacher The teacher to compare its lessons
   * @return True if there is any overlapping; otherwise false.
   */
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
