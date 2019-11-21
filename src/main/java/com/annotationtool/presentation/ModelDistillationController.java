package com.annotationtool.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.annotationtool.model.Process;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ModelDistillationController implements Initializable {

    @FXML
    private TextField tbThreshold;
    
    @FXML
    private AnchorPane pane;
    
    private Process process;
    
    private boolean accepted;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initProcess(Process process)
    {
        this.accepted=false;
        this.process=(Process)process.clone();
        List<String> models=this.process.getModels();
        for(Node node:pane.getChildren())
        {
            if(node instanceof CheckBox)
            {
                CheckBox cb=(CheckBox)node;
                if(models.contains(cb.getText()))
                {
                    cb.setSelected(true);
                }else{
                    cb.setSelected(false);
                }
            }
        }
        
        this.tbThreshold.setText(String.valueOf(this.process.getThreshold()));
    }
    
    @FXML
    void selectCb(ActionEvent event)
    {
        if(event.getSource() instanceof CheckBox)
        {
            CheckBox cb=(CheckBox) event.getSource();
            if(cb.isSelected())
            {
                this.process.addModel(cb.getText());
            }else{
                this.process.removeModel(cb.getText());
            }
        }
    }
    
    public void setNoIterative()
    {
        tbThreshold.setDisable(true);
    }
    
    public Process getProcess()
    {
        process.setThreshold(Double.valueOf(tbThreshold.getText()));
        return process;
    } 
    
    @FXML
    void accept(ActionEvent event)
    {
        try{
        if (this.process.getModels().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid selection");
            alert.setContentText("You must select at least one option.");
            alert.showAndWait();
        } else if(Double.valueOf(tbThreshold.getText())>=0 && Double.valueOf(tbThreshold.getText())<=1){
            this.accepted = true;
            Stage stage=(Stage)this.pane.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Threshold");
            alert.setContentText("You must select a threshold betwen [0, 1].");
            alert.showAndWait();
        }
        }catch (NumberFormatException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Threshold");
            alert.setContentText("You must select a threshold betwen [0, 1].");
            alert.showAndWait();
        }
    }
    
    public boolean getAccepted()
    {
        return this.accepted;
    }
    
}
