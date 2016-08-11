package MainFrame.Main;

import MainFrame.NewProject.NewProjectController;
import MainFrame.NewProject.NewProjectFrame;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Author       :       Dheeraj
 * Created on   :       1/08/2016
 * Description  :       This class is mainly intended to add and control all the components of a Main Frame.
 *                      The Main components in this class are menu bar, tree table view, status bar of the Main Frame.
 *
 * @author Dheeraj
 */
public class MainController implements Initializable {

    @FXML
    private TabPane tabPane;
    private Tab tab;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TreeTableView<File> treeTableView;

    /*
     * All the Components and variables initiator method.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createFileBrowserTreeTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * ============ MenuBar ===========
     *
     * Initializing all the Components of a MenuBar.
     */
    @FXML
    public void newUniversity(ActionEvent actionEvent) throws IOException {
        tab = FXMLLoader.load(this.getClass().getResource("/MainFrame/Tabs/University.fxml"));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @FXML
    public void newCourse(ActionEvent actionEvent) throws IOException {
        tab = FXMLLoader.load(this.getClass().getResource("/MainFrame/Tabs/Course.fxml"));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @FXML
    public void newSubject(ActionEvent actionEvent) throws IOException {
        tab = FXMLLoader.load(this.getClass().getResource("/MainFrame/Tabs/Subject.fxml"));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @FXML
    public void newExamList(ActionEvent actionEvent) throws IOException {
    }

    @FXML
    public void newProjectAction(ActionEvent actionEvent) throws IOException {
        NewProjectFrame newFrame = new NewProjectFrame();
        newFrame.secondStage();
    }

    @FXML
    public void closeWindow(ActionEvent actionEvent) {
        System.exit(0);
    }

    // Ending all the Components of a MenuBar

    /*
     * ========== Tree Table View ===========
     *
     * Adding all the components of a Tree Table View.
     *
     * Source copied from
     * https://wiki.openjdk.java.net/display/OpenJFX/TreeTableView+API+Examples
     */
    private TreeTableView<File> createFileBrowserTreeTableView() throws Exception {

        File file = new File(NewProjectController.getWorkingDirectory());
        if(file.exists()) {
            FileTreeItem root = new FileTreeItem(file);
            treeTableView.setShowRoot(true);
            treeTableView.setRoot(root);
            root.setExpanded(true);
            treeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

            TreeTableColumn<File, FileTreeItem> nameColumn = new TreeTableColumn<>("Project Explorer");

            nameColumn.setCellValueFactory(cellData ->
                    new ReadOnlyObjectWrapper<>((FileTreeItem) cellData.getValue())
            );

            Image image1 = getImageResource("/img/unknown-file-16x16.png");
            Image image2 = getImageResource("/img/folder-open-16x16.png");
            Image image3 = getImageResource("/img/folder-close-16x16.png");

            nameColumn.setCellFactory(column -> {
                TreeTableCell<File, FileTreeItem> cell = new TreeTableCell<File, FileTreeItem>() {

                    ImageView imageView1 = new ImageView(image1);
                    ImageView imageView2 = new ImageView(image2);
                    ImageView imageView3 = new ImageView(image3);

                    @Override
                    protected void updateItem(FileTreeItem item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty || item.getValue() == null) {
                            setText(null);
                            setGraphic(null);
                            setStyle("");
                        } else {
                            File f = item.getValue();
                            String text = f.getParentFile() == null ? File.separator : f.getName();
                            setText(text);
                            String style = item.isHidden() && f.getParentFile() != null ? "-fx-accent" : "-fx-text-base-color";
                            setStyle("-fx-text-fill: " + style);
                            if (item.isLeaf()) {
                                setGraphic(imageView1);
                            } else {
                                setGraphic(item.isExpanded() ? imageView2 : imageView3);
                            }
                        }
                    }
                };
                return cell;
            });

            nameColumn.setSortable(false);
            treeTableView.getColumns().add(nameColumn);


            treeTableView.getSelectionModel().selectFirst();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Not Found");
            alert.setHeaderText("Project Files Not Found");
            alert.setContentText("Click ok to exit the dialogue box");
        }
        return treeTableView;
    }

    private Image getImageResource(String name) {
        Image img = null;
        try { img = new Image(getClass().getResourceAsStream(name)); } catch (Exception e) { e.printStackTrace(); }
        return img;
    }

    private class FileTreeItem extends TreeItem<File> {
        private boolean expanded = false;
        private boolean directory;
        private boolean hidden;

        FileTreeItem(File file) {
            super(file);
            EventHandler<TreeModificationEvent<File>> eventHandler = event -> changeExpand();
            addEventHandler(TreeItem.branchExpandedEvent(), eventHandler);
            addEventHandler(TreeItem.branchCollapsedEvent(), eventHandler);

            directory = getValue().isDirectory();
            hidden = getValue().isHidden();
        }

        private void changeExpand() {
            if (expanded != isExpanded()) {
                expanded = isExpanded();
                if (expanded) {
                    createChildren();
                } else {
                    getChildren().clear();
                }
                if (getChildren().size() == 0)
                    Event.fireEvent(this, new TreeItem.TreeModificationEvent<>(TreeItem.valueChangedEvent(), this, getValue()));
            }
        }

        @Override
        public boolean isLeaf() {
            return !isDirectory();
        }

        boolean isDirectory() { return directory; }
        boolean isHidden() { return hidden; }

        private void createChildren() {
            if (isDirectory() && getValue() != null) {
                File[] files = getValue().listFiles();
                if (files != null && files.length > 0) {
                    getChildren().clear();
                    for (File childFile : files) {
                        getChildren().add(new FileTreeItem(childFile));
                    }
                    getChildren().sort((ti1, ti2) -> {
                        String s1 = (((FileTreeItem)ti1).isDirectory() ? "0" : "1").concat(ti1.getValue().getName());
                        String s2 = (((FileTreeItem)ti2).isDirectory() ? "0" : "1").concat(ti2.getValue().getName());
                        return s1.compareToIgnoreCase(s2);
                    });
                }
            }
        }
    }
    //Ending Tree Table View

}
