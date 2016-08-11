package MainFrame.Initial;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Author       :       Dheeraj
 * Created on   :       9/08/2016
 * Description  :       This class is intended to create a new stage of Initial Frame.
 */
public class InitialFrame{

    static Stage stage;
    public void initialStage() throws Exception {
        try{
            /*
             * Load Main.fxml
             * This is the basic UI for Main Frame and its Components.
             * The size of this main window is 800, 500.
             */
            stage=new Stage();
            BorderPane root= FXMLLoader.load(getClass().getResource("InitialFrame.fxml"));
            Scene scene=new Scene(root,600,400);
            stage.getIcons().add(new Image("/img/favicon-32x32.png"));

            /*
             * Load styles.css
             * This is the basic stylesheet for the MainFrame and its Components.
             */
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);

            /*
             * Add Title and ICON to the Main Frame.
             */
            stage.setTitle("Welcome to University Time Table Generator v0.1");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
