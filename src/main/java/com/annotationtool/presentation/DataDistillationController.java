package com.annotationtool.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import com.annotationtool.model.Process;
import java.util.ArrayList;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class DataDistillationController implements Initializable {

    @FXML
    private TextField tbThresField;
    
    
    private ArrayList<String> transformationsSelected;
    
    
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
                transformationsSelected.add(cb.getText());
            }else{
                transformationsSelected.remove(cb.getText());
            }
        }
    }
    
    public void setNoIterative()
    {
        tbThresField.setDisable(true);
    }
    
    public Process getProcess()
    {
        Process process=new Process("Data Distillation");
        
        if(tbThresField.getText()==null)
        {
            process.setThreshold(0);
        }else{
            process.setThreshold(Float.valueOf(tbThresField.getText()));
        } 
        process.setTransformations(transformationsSelected);
        
        
        return process;
    }
    
}
