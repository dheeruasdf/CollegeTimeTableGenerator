package MainFrame.Main;

import MainFrame.Initial.InitialFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Author       :       Dheeraj
 * Created on   :       1/08/2016
 * Description  :       This class is main class of the project which consists the main method.
 *                      The execution of the project starts from this class.
 *
 * @author Dheeraj
 */
public class MainFrame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //To be used static to use all the methods from other windows such as close.
    public static Stage stage;

    /**
     * This is the main method to initialize all the necessary components of the main Frame.
     * @param primaryStage
     *                      All the components to be loaded to the scene and
     *                      the scene is to be loaded on to the stage.
     */
    @Override
    public void start(Stage primaryStage) {
        try{
            /**
             * Load Main.fxml
             * This is the basic UI for Main Frame and its Components.
             * The size of this main window is 800, 500.
             */
            File file = new File("lib/CurrentWorking.XML");
            if(file.exists() && file.length()!=0) {
                stage = primaryStage;
                BorderPane root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(root, 800, 500);
                stage.getIcons().add(new Image("/img/favicon-32x32.png"));

                /**
                 * Load styles.css
                 * This is the basic stylesheet for the MainFrame and its Components.
                 */
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                primaryStage.setScene(scene);

                /**
                 * Add Title and ICON to the Main Frame.
                 */
                primaryStage.setTitle("University TimeTable Generator V0.01");
                primaryStage.show();
                primaryStage.setMaximized(true);
            }
            else {
                InitialFrame initialFrame = new InitialFrame();
                initialFrame.initialStage();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
