package com.annotationtool.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import com.annotationtool.model.Process;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class DataDistillationController implements Initializable {

    @FXML
    private TextField tbThreshold;
    
    @FXML
    private AnchorPane pane;
    
    private Process process;
    
    
//    private ArrayList<String> transformationsSelected;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initProcess(Process process)
    {
        this.process=process;
        List<String> transformations=this.process.getTransformations();
        for(Node node:pane.getChildren())
        {
            if(node instanceof CheckBox)
            {
                CheckBox cb=(CheckBox)node;
                if(transformations.contains(cb.getText()))
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
                this.process.addTransformation(cb.getText());
            }else{
                this.process.removeTransformation(cb.getText());
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
        return this.process;
    }
    
}
