package com.annotationtool.presentation;

import com.annotationtool.logic.Logic;
import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class DatasetController implements Initializable {

    private static Logic logic = new Logic();

    private int numCategories = 2;
    private String dataset = "";
    private String savePath = "";
    private boolean accepted = false;
    private boolean automatically = false;

    private int minCategories = 2;
    private List<Category> categories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lCategory.setText(String.valueOf(numCategories));
        this.bMinus.setDisable(true);
    }

    @FXML
    private Label lCategory;

    @FXML
    private Label lDataset;

    @FXML
    private Label lLocation;

    @FXML
    private AnchorPane anchorid;

    @FXML
    private Button bMinus;

    @FXML
    private Button bPlus;

    @FXML
    private Button bSavePath;

    @FXML
    private CheckBox cbAuto;

    @FXML
    private CheckBox cbContinue;

    @FXML
    void selectDataset(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && selectedDirectory.exists()) {

            dataset = selectedDirectory.getAbsolutePath();
            lDataset.setText(dataset);
            if (cbContinue.isSelected()) {
                lLocation.setText(dataset);
            } else {

                categories = new ArrayList<>();

                File direcory = new File(dataset);
                File files[] = direcory.listFiles();
                int numCat = 0;
                for (File f : files) {
                    if (f.isDirectory()) {
                        numCat++;
                        categories.add(new Category(f.getName()));
                    }
                }

                if (numCat >= 2) {
                    this.minCategories = numCat;
                    this.numCategories = numCat;
                    this.lCategory.setText(String.valueOf(numCat));
                    this.bMinus.setDisable(true);
                }
            }
        }
    }

    @FXML
    void selectSavePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && selectedDirectory.exists()) {
            if (selectedDirectory.list().length == 0) {
                savePath = selectedDirectory.getAbsolutePath();
                lLocation.setText(savePath);
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The direcotory is not empty");
                alert.setContentText("You must select an empty directory.");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void selectContinue(ActionEvent event) {
        if (cbContinue.isSelected()) {
            bSavePath.disableProperty().set(true);
            lLocation.setText(lDataset.getText());
            cbAuto.disableProperty().set(true);
            bMinus.disableProperty().set(true);
            bPlus.disableProperty().set(true);

        } else {
            bSavePath.disableProperty().set(false);
            lLocation.setText("");
            cbAuto.disableProperty().set(false);
            bMinus.disableProperty().set(false);
            bPlus.disableProperty().set(false);

            categories = new ArrayList<>();

            File direcory = new File(dataset);
            File files[] = direcory.listFiles();
            int numCat = 0;
            for (File f : files) {
                if (f.isDirectory()) {
                    numCat++;
                    categories.add(new Category(f.getName()));
                }
            }

            if (numCat >= 2) {
                this.minCategories = numCat;
                this.numCategories = numCat;
                this.lCategory.setText(String.valueOf(numCat));
                this.bMinus.setDisable(true);
            }
        }
    }

    @FXML
    void increaseCategory(ActionEvent event) {
        this.numCategories++;
        lCategory.setText(String.valueOf(numCategories));
        bMinus.setDisable(false);
    }

    @FXML
    void decreaseCategory(ActionEvent event) {
        if (numCategories > minCategories) {
            numCategories--;
            lCategory.setText(String.valueOf(numCategories));
            if (numCategories == minCategories) {
                bMinus.setDisable(true);
            }
        }
    }

    @FXML
    void accept(ActionEvent event) {
        if (lDataset.getText().length() != 0) {
            if (lLocation.getText().length() != 0) {
                try {
                    Stage stage = (Stage) lLocation.getScene().getWindow();
                    accepted = true;

                    if (cbContinue.isSelected()) {
                        logic.continueDataset(savePath);
                    } else {

                        automatically = cbAuto.isSelected();

                        int numCat = getNumCategories();
                        String dataset = getDataset();
                        String savePath = getSavePath();
                        boolean automatically = getAutomatically();
                        List<Category> categories = getCategories();
                        int i = categories.size() + 1;
                        while (numCat > categories.size()) {
                            categories.add(new Category("Cluster " + i));
                            i++;
                        }

                        if (automatically) {
                            logic.initializeDatasetAutomatically(dataset, savePath, categories);
                        } else {
                            logic.initializeDatasetManually(dataset, savePath, categories);
                        }
                    }

                    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/Images.fxml"));
                    Parent root2 = fxmlLoader2.load();

                    Scene scene2 = new Scene(root2);
                    scene2.getStylesheets().add("/styles/Images.css");

                    stage.setScene(scene2);
                    stage.setMaximized(true);
                    stage.setResizable(true);
                    stage.show();
                } catch (ExcepcionDeAplicacion ex) {
                    Logger.getLogger(DatasetController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An unexpected error ocurred.");
                    alert.setContentText("Unexpected error loading the images. Try to load the images again.");
                    alert.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(DatasetController.class.getName()).log(Level.SEVERE, null, ex);

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An unexpected error ocurred.");
                    alert.setContentText("Unexpected error loading the program. Reset the application.");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The information is inclompete");
                alert.setContentText("You must select a save location.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The information is inclompete");
            alert.setContentText("You must select a dataset.");
            alert.showAndWait();
        }

    }

    public int getNumCategories() {
        return numCategories;
    }

    public String getDataset() {
        return dataset;
    }

    public String getSavePath() {
        return savePath;
    }

    public boolean getAccepted() {
        return accepted;
    }

    public boolean getAutomatically() {
        return automatically;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
