package com.annotationtool.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.annotationtool.model.Process;
import java.util.List;
import javafx.scene.Node;

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

    public void initProcess(Process process) {
        this.process = process;
        List<String> transformations = this.process.getTransformations();
        List<String> models = this.process.getModels();
        for (Node node : pane.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (transformations.contains(cb.getText())) {
                    cb.setSelected(true);
                } else if(models.contains(cb.getText())){
                    cb.setSelected(true);
                }else{
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

    public com.annotationtool.model.Process getProcess() {

        process.setThreshold(Double.valueOf(tbThreshold.getText()));
        return process;
    }
}
