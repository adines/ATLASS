package com.annotationtool.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    
    private com.annotationtool.model.Process process;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initProcess(com.annotationtool.model.Process process)
    {
        this.process=process;
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
    
    public com.annotationtool.model.Process getProcess()
    {
        process.setThreshold(Double.valueOf(tbThreshold.getText()));
        return process;
    } 
    
}
