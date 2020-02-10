package com.atlass.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.atlass.model.Process;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ModelDataDistillationController implements Initializable {

    @FXML
    private TextField tbThreshold;

    @FXML
    private AnchorPane pane;

    private Process process;

    private boolean accepted;

    public void initProcess(Process process) {
        this.accepted = false;
        this.process = (Process) process.clone();
        List<String> transformations = this.process.getTransformations();
        List<String> models = this.process.getModels();
        for (Node node : pane.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (transformations.contains(cb.getText())) {
                    cb.setSelected(true);
                } else if (models.contains(cb.getText())) {
                    cb.setSelected(true);
                } else {
                    cb.setSelected(false);
                }
            }
        }

        this.tbThreshold.setText(String.valueOf(this.process.getThreshold()));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void selectCbModel(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox cb = (CheckBox) event.getSource();
            if (cb.isSelected()) {
                this.process.addModel(cb.getText());
            } else {
                this.process.removeModel(cb.getText());
            }
        }
    }

    @FXML
    void selectCbTransformation(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox cb = (CheckBox) event.getSource();
            if (cb.isSelected()) {
                this.process.addTransformation(cb.getText());
            } else {
                this.process.removeTransformation(cb.getText());
            }
        }
    }

    public void setNoIterative() {
        tbThreshold.setDisable(true);
    }

    public Process getProcess() {

        process.setThreshold(Double.valueOf(tbThreshold.getText()));
        return process;
    }

    @FXML
    void accept(ActionEvent event) {
        try {
            if (this.process.getModels().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid selection");
                alert.setContentText("You must select at least one model.");
                alert.showAndWait();
            } else if (this.process.getTransformations().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid selection");
                alert.setContentText("You must select at least one transformation.");
                alert.showAndWait();
            } else if (Double.valueOf(tbThreshold.getText()) >= 0 && Double.valueOf(tbThreshold.getText()) <= 1) {
                this.accepted = true;
                Stage stage = (Stage) this.pane.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Threshold");
                alert.setContentText("You must select a threshold betwen [0, 1].");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Threshold");
            alert.setContentText("You must select a threshold betwen [0, 1].");
            alert.showAndWait();
        }
    }

    public boolean getAccepted() {
        return this.accepted;
    }
}
