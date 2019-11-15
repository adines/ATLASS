package com.annotationtool.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ModelDistillationController implements Initializable {

    @FXML
    private TextField tbThresField;
    
    private ArrayList<String> modelsSelected;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void selectCb(ActionEvent event)
    {
        if(event.getSource() instanceof CheckBox)
        {
            CheckBox cb=(CheckBox) event.getSource();
            if(cb.isSelected())
            {
                modelsSelected.add(cb.getText());
            }else{
                modelsSelected.remove(cb.getText());
            }
        }
    }
    
    public void setNoIterative()
    {
        tbThresField.setDisable(true);
    }
    
    public com.annotationtool.model.Process getProcess()
    {
        com.annotationtool.model.Process process=new com.annotationtool.model.Process("Model Distillation");
        if(tbThresField.getText()==null)
        {
            process.setThreshold(0);
        }else{
            process.setThreshold(Float.valueOf(tbThresField.getText()));
        }
        
        process.setTransformations(modelsSelected);
        
        
        return process;
    } 
    
}
