package MainFrame.NewProject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author       :       Dheeraj
 * Created on   :       9/08/2016
 * Description  :       This is the Frame for creating a new project.
 *                      This frame is to be appeared on creating a new project
 *                      for the first time or closing an existing project.
 *
 * @author Dheeraj
 */
public class NewProjectFrame{

    static Stage stage;

    public void secondStage() {

        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("NewFrame.fxml"));

            Scene scene = new Scene(root,600,400);
            stage.getIcons().add(new Image("/img/favicon-32x32.png"));
            stage.setScene(scene);
            stage.setTitle("Welcome to University Time Table Generator v0.1");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
