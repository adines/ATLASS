package com.annotationtool.presentation;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private int numCategories = 2;
    private String dataset="";
    private String savePath="";
    private boolean accepted=false;
    private boolean automatically=false;
    
    private int minCategories=2;

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
    private CheckBox cbAuto;

    @FXML
    void selecDataset(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && selectedDirectory.exists()) {
            dataset=selectedDirectory.getAbsolutePath();
            lDataset.setText(dataset);
            File direcory=new File(dataset);
            File files[]=direcory.listFiles();
            int numCat=0;
            for(File f: files)
            {
                if(f.isDirectory())
                {
                    numCat++;
                }
            }
            
            if(numCat>=2)
            {
                this.minCategories=numCat;
                this.numCategories=numCat;
                this.lCategory.setText(String.valueOf(numCat));
                this.bMinus.setDisable(true);
            }
        }
    }

    @FXML
    void selecSavePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && selectedDirectory.exists()) {
            if (selectedDirectory.list().length == 0) {
                savePath=selectedDirectory.getAbsolutePath();
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
            if(numCategories==minCategories)
            {
                bMinus.setDisable(true);
            }
        }
    }

    @FXML
    void accept(ActionEvent event) {
        if (lDataset.getText().length() != 0) {
            if (lLocation.getText().length() != 0) {
                Stage stage = (Stage) lLocation.getScene().getWindow();
                accepted=true;
                automatically=cbAuto.isSelected();
                stage.close();
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
    
    public int getNumCategories()
    {
        return numCategories;
    }
    
    public String getDataset()
    {
        return dataset;
    }
    
    public String getSavePath()
    {
        return savePath;
    }
    
    public boolean getAccepted()
    {
        return accepted;
    }
    
    public boolean getAutomatically()
    {
        return automatically;
    }
}
