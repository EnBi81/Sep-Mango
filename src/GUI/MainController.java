package GUI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class MainController
{
  @FXML private Tab scheduleTab;
  @FXML private Tab studentsTab;
  @FXML private Tab courseTab;
  @FXML private Tab roomsTab;
  @FXML private Tab classTab;

  private ArrayList<AbstractController> controllers = new ArrayList<>();

  public void initialize()
  {
    Pane pane;

    try
    {
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
    Pane p = loader.load(getClass().getResource(fileName).openStream());
    if(fileName.equals("VIAClassTab.fxml"))
    controllers.add(loader.getController());
    return p;
  }

  public void refreshTabs(Event event)
  {
    for (int i = 0; i < controllers.size(); i++)
    {
      controllers.get(i).refresh();
    }
  }
}