package MainFrame.Initial;

import MainFrame.NewProject.NewProjectFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.html.ListView;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


/*
 * Author       :   Dheeraj
 * created on   :   9/08/2016
 * Description  :   This Frame is intended to open initially when no project is created.
 *                  From this Frame we can create, open the project and also change settings.
 */
public class InitialFrameController implements Initializable {

    @FXML
    public Button newProject,importProject,openProject;

    @FXML
    public ListView listView;

    @FXML
    public void newProjectAction(ActionEvent actionEvent) {
        InitialFrame.stage.close();
        NewProjectFrame newProjectFrame=new NewProjectFrame();
        newProjectFrame.secondStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
