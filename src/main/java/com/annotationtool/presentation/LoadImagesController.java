package com.annotationtool.presentation;

import com.annotationtool.logic.Logic;
import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class LoadImagesController implements Initializable {

    private static Logic logic = new Logic();

    private List<Category> categories;

    private List<Category> newCategories;

    private String dataset = "";
    private boolean accepted = false;
    private boolean automatically = false;

    @FXML
    private CheckBox cbAuto;

    @FXML
    private AnchorPane anchorid;

    @FXML
    private Label lDataset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void selecDataset(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && selectedDirectory.exists()) {
            newCategories = new ArrayList<>();
            categories = logic.getCategories();
            dataset = selectedDirectory.getAbsolutePath();
            lDataset.setText(dataset);
            File direcory = new File(dataset);
            File files[] = direcory.listFiles();
            for (File f : files) {
                if (f.isDirectory() && !categories.contains(f.getName())) {
                    newCategories.add(new Category(f.getName()));
                }
            }
        }
    }

    @FXML
    void accept(ActionEvent event) {

        if (lDataset.getText().length() != 0) {
            try {
                Stage stage = (Stage) lDataset.getScene().getWindow();
                accepted = true;
                automatically = cbAuto.isSelected();

                String dataset = getDataset();
                boolean automatically = getAutomatically();
                List<Category> categories = getCategories();
                for (Category categ : newCategories) {
                    logic.addCategory(categ);
                }

                if (automatically) {
                    logic.loadImagesAutomatically(dataset);
                } else {
                    logic.loadImagesManually(dataset);
                }
                stage.close();
            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(DatasetController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The information is inclompete");
            alert.setContentText("You must select a dataset.");

            alert.showAndWait();
        }

    }

    public String getDataset() {
        return dataset;
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
