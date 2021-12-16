package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application Class to start the program. IT loads the main fxml file and shows it.
 * @author Beatricia
 * @version 1.0
 */
public class StartMainTab extends Application
{
  /**
   * Initializes the main window.
   * @param window The window to show the tab
   * @throws IOException Throws IOException when the MainTab.fxml cannot be accessed.
   */
  public void start(Stage window) throws IOException
  {
    window.setTitle("Schedule");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("MainTab.fxml"));
    Scene scene = new Scene(loader.load());
    window.setScene(scene);
    window.setResizable(false);
    window.show();
  }
 }
