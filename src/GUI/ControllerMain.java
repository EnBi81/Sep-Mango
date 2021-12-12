package GUI;

import Model.*;
import ScheduleManager.Manager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerMain {
    @FXML
    private Tab scheduleTab;
    @FXML
    private Tab studentsTab;
    @FXML
    private Tab courseTab;
    @FXML
    private Tab roomsTab;
    @FXML
    private Tab classTab;

    @FXML
    private MenuItem exportButton;
    @FXML
    private MenuItem closeButton;

    private ArrayList<AbstractController> controllers = new ArrayList<>();

    public void initialize() {
        Pane pane;

        try {
            pane = getPaneFromFile("StudentsTab.fxml");
            studentsTab.setContent(pane);

            pane = getPaneFromFile("VIAClassTab.fxml");
            classTab.setContent(pane);

            pane = getPaneFromFile("Course.fxml");
            courseTab.setContent(pane);

            pane = getPaneFromFile("Room.fxml");
            roomsTab.setContent(pane);

            pane = getPaneFromFile("ScheduleTab.fxml");
            scheduleTab.setContent(pane);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public Pane getPaneFromFile(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane p = loader.load(getClass().getResource(fileName).openStream());
        controllers.add(loader.getController());
        return p;
    }

    public void refreshTabs(Event event) {
        for (int i = 0; i < controllers.size(); i++) {
            controllers.get(i).refresh();
        }
    }

    public void closeProgram() {
        System.exit(0);
    }

    public void export() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><courses>";
        ArrayList<Course> courses = Manager.getSchedule().getCourseList().getAllCourses();
        for (int i = 0; i < courses.size(); i++) {
            xml += courseToXML(courses.get(i));
        }
        xml += "</courses>";

        try {
            FileHandler.writeToTextFile("Files/schedule.xml", xml);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String studentToXML(Student student) {
        return "<student id=\"" + student.getId() + "\" name=\"" + student.getName() + "\"/>";
    }

    public String teacherToXML(Teacher teacher) {
        return "<teacher name=\"" + teacher.getName() + "\"/>";
    }

    public String lessonToXML(Lesson lesson) {
        return "<lesson startTime=\"" + lesson.getStartTime() + "\" endTime=\"" + lesson.getEndTime() + "\""
                + " primaryRoom=\"" + lesson.getFirstRoom().getRoomName() + "\""
                + " secondaryRoom=\"" + (lesson.getSecondRoom() == null ? null : lesson.getSecondRoom().getRoomName()) + "\"/>";
    }

    public String courseToXML(Course course) {
        String xml = "<course name=\"" + course.getCourseName() + "\">";
        xml += "<teachers>";
        for (int i = 0; i < course.getAllTeachers().size(); i++) {
            xml += teacherToXML(course.getAllTeachers().get(i));
        }
        xml += "</teachers><students>";
        for (int i = 0; i < course.getAllStudents().size(); i++) {
            xml += studentToXML(course.getAllStudents().get(i));
        }
        xml += "</students><lessons>";
        for (int i = 0; i < course.getAllLessons().size(); i++) {
            xml += lessonToXML(course.getAllLessons().get(i));
        }
        xml += "</lessons></course>";

        return xml;
    }
}