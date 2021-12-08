package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController
{
  @FXML private Tab scheduleTab;
  @FXML private Tab studentsTab;
  @FXML private Tab courseTab;
  @FXML private Tab roomsTab;
  @FXML private Tab classTab;


  public void initialize()
  {
    Pane pane;

    try{
      pane = getPaneFromFile("StudentsTab.fxml");
      studentsTab.setContent(pane);

      pane = getPaneFromFile("VIAClassTab.fxml");
      classTab.setContent(pane);

      pane = getPaneFromFile("Course.fxml");
      courseTab.setContent(pane);

      pane = getPaneFromFile("Room.fxml");
      roomsTab.setContent(pane);

      pane = getPaneFromFile("Schedule.fxml");
      scheduleTab.setContent(pane);

    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public Pane getPaneFromFile(String fileName) throws IOException
  {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource(fileName));
    return loader.load();
  }
}