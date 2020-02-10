package com.atlass.presentation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import com.atlass.model.Process;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class NoDistillationController implements Initializable {

    @FXML
    private AnchorPane pane;

    private boolean accepted;

    private Process process;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initProcess(Process process) {
        this.accepted=false;
        this.process = (Process) process.clone();
        List<String> models = this.process.getModels();
        for (Node node : pane.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (models.contains(cb.getText())) {
                    cb.setSelected(true);
                } else {
                    cb.setSelected(false);
                }
            }
        }
    }

    @FXML
    void selectCb(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox cb = (CheckBox) event.getSource();
            if (cb.isSelected()) {
                this.process.addModel(cb.getText());
            } else {
                this.process.removeModel(cb.getText());
            }
        }
    }

    public Process getProcess() {
        return process;
    }

    @FXML
    void accept(ActionEvent event) {
        if (this.process.getModels().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid selection");
            alert.setContentText("You must select at least one option.");
            alert.showAndWait();
        } else {
            this.accepted = true;
            Stage stage=(Stage)this.pane.getScene().getWindow();
            stage.close();
        }
    }
    public boolean getAccepted()
    {
        return this.accepted;
    }

}
