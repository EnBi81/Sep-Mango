package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartMainTab extends Application
{
  public void start(Stage window) throws IOException
  {
    window.setTitle("Schedule");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("MainTab.fxml"));
    Scene scene = new Scene(loader.load());
    window.setScene(scene);
    window.show();
  }
 }
