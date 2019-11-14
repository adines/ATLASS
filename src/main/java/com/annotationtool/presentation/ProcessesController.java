/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.annotationtool.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ProcessesController implements Initializable {

    
    @FXML
    private CheckBox cbDD;
    
    @FXML
    private CheckBox cbIDD;
    
    @FXML
    private CheckBox cbMD;
    
    @FXML
    private CheckBox cbIMD;
    
    @FXML
    private CheckBox cbMDD;
    
    @FXML
    private CheckBox cbIMDD;
    
    @FXML
    private Button bConfDD;
    
    @FXML
    private Button bConfIDD;
    
    @FXML
    private Button bConfMD;
    
    @FXML
    private Button bConfIMD;
    
    @FXML
    private Button bConfMDD;
    
    @FXML
    private Button bConfIMDD;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    public void setNoUnlabelledImages()
    {
        cbDD.setDisable(true);
        cbIDD.setDisable(true);
        cbMD.setDisable(true);
        cbIMD.setDisable(true);
        cbMDD.setDisable(true);
        cbIMDD.setDisable(true);
        
        bConfDD.setDisable(true);
        bConfIDD.setDisable(true);
        bConfMD.setDisable(true);
        bConfIMD.setDisable(true);
        bConfMDD.setDisable(true);
        bConfIMDD.setDisable(true);
    }

    @FXML
    void configureDD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(DataDistillationController.class.getResource("DataDistillation.fxml"));
            Parent root=loader.load();
            DataDistillationController controller=loader.getController();
            
            controller.setNoIterative();
            
            stage.setScene(new Scene(root));
            stage.setTitle("Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void configureIDD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(DataDistillationController.class.getResource("DataDistillation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void configureMD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDistillationController.class.getResource("ModelDistillation.fxml"));
            Parent root=loader.load();
            
            ModelDistillationController controller=loader.getController();
            controller.setNoIterative();
            
            stage.setScene(new Scene(root));
            stage.setTitle("Model Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void configureIMD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(ModelDistillationController.class.getResource("ModelDistillation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Model Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    void configureMDD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDataDistillationController.class.getResource("ModelDataDistillation.fxml"));
            Parent root=loader.load();
            
            ModelDataDistillationController controller=loader.getController();
            controller.setNoIterative();
            
            stage.setScene(new Scene(root));
            stage.setTitle("Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void configureIMDD(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(ModelDataDistillationController.class.getResource("ModelDataDistillation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
