package MainFrame.NewProject;

import MainFrame.Main.MainFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.util.*;

/*
 * Author       :   Dheeraj
 * Created on   :   9/08/2016
 * Description  :   This class in intended to controll the components of creating a new project frame.
 */
public class NewProjectController implements Initializable{
    @FXML
    private TextField projectName, projectPath;

    @FXML
    private Button pathButton;

    @FXML
    public void pathButtonAction(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Directory Path");
        File file = chooser.showDialog(NewProjectFrame.stage);
        if (file!=null) {
            projectPath.setText(file.getPath()+"\\"+projectName.getText());
        }
    }

    @FXML
    public void finishButtonAction(ActionEvent actionEvent) throws Exception {
        String dirName = projectPath.getText();
        File file = new File(dirName+"//src");
        if(!file.exists())
        {
            if(!file.mkdirs()){
                //dialogue to be created as some error has been occurred.
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setHeaderText("Please select a valid path");
                alert.setContentText("click ok button to close the dialogue box");
                alert.showAndWait();
            }
        }
        setWorkingDirectory();
        NewProjectFrame.stage.close();
        MainFrame mainFrame=new MainFrame();
        mainFrame.start(new Stage());
    }

    @FXML
    public void cancelButtonAction(ActionEvent actionEvent) {
        NewProjectFrame.stage.close();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    /**
     * This method is to get the current working directory of the project.
     * This method reads from a file "../lib/CurrentWorking.XML"
     * @return
     */
    public static String getWorkingDirectory() throws Exception{
        File file = new File("lib/CurrentWorking.XML");

        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList list = document.getElementsByTagName("Item");
        Node node = list.item(0);

        Element element = (Element) node;
        String path = element.getElementsByTagName("path").item(0).getTextContent();

        return path;
    }

    /**
     * This method is to set the current working directory of the project.
     * This method creates or updates a file "../lib/CurrentWorking.XML".
     */
    public void setWorkingDirectory() throws Exception{

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element element = document.createElement("List");
        document.appendChild(element);

        Element item = document.createElement("Item");
        element.appendChild(item);

        Attr attr = document.createAttribute("id");
        attr.setValue("1");
        item.setAttributeNode(attr);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(projectName.getText()));
        item.appendChild(name);

        Element path = document.createElement("path");
        path.appendChild(document.createTextNode(projectPath.getText()));
        item.appendChild(path);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        File file = new File("lib");
        file.mkdir();
        StreamResult streamResult = new StreamResult(new File("lib/CurrentWorking.XML"));

        transformer.transform(source,streamResult);
    }

}
