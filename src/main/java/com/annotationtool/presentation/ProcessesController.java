package com.annotationtool.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import com.annotationtool.model.Process;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ProcessesController implements Initializable {

    private List<Process> processesSelected;

    private List<Process> processesNoSelected;

    @FXML
    private CheckBox cbND;
    
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
        processesNoSelected = new ArrayList<Process>();
        processesSelected = new ArrayList<>();

        //No distillation por defecto
        Process ndProcess = new Process("ND");
        ndProcess.addModel("ResNet34");
        ndProcess.addModel("ResNet50");

        //Data Distillation por defecto
        Process ddProcess = new Process("DD");

        //Iterative data distillation por defecto
        Process iddProcess = new Process("IDD");
        iddProcess.setThreshold(0.8);

        //Model distillation por defecto
        Process mdProcess = new Process("MD");

        //Iterative model distillation por defecto
        Process imdProcess = new Process("IMD");
        imdProcess.setThreshold(0.8);

        //Model data distillation por defecto
        Process mddProcess = new Process("MDD");

        //Iterative model data distillation por defecto
        Process imddProcess = new Process("IMDD");
        imddProcess.setThreshold(0.8);

        processesNoSelected.add(ndProcess);
        processesNoSelected.add(ddProcess);
        processesNoSelected.add(iddProcess);
        processesNoSelected.add(mdProcess);
        processesNoSelected.add(imdProcess);
        processesNoSelected.add(mddProcess);
        processesNoSelected.add(imddProcess);
    }

    public void setNoUnlabelledImages() {
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
    void selectCb(ActionEvent event)
    {
        if(event.getSource() instanceof CheckBox)
        {
            CheckBox cb=(CheckBox) event.getSource();
            if(cb.isSelected())
            {
                
            }else{
                
            }
        }
    }
    
    @FXML
    void configureND(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(DataDistillationController.class.getResource("/fxml/DataDistillation.fxml"));
            Parent root = loader.load();
            DataDistillationController controller = loader.getController();

            controller.setNoIterative();
            
            if (cbND.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("ND"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("ND"))));
            }
            
            stage.setScene(new Scene(root));
            stage.setTitle("No Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbND.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    void configureDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(DataDistillationController.class.getResource("/fxml/DataDistillation.fxml"));
            Parent root = loader.load();
            DataDistillationController controller = loader.getController();

            controller.setNoIterative();
            
            if (cbDD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("DD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("DD"))));
            }
            
            stage.setScene(new Scene(root));
            stage.setTitle("Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbDD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureIDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(DataDistillationController.class.getResource("/fxml/DataDistillation.fxml"));
            Parent root = loader.load();
            DataDistillationController controller = loader.getController();
            
            if (cbDD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("IDD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("IDD"))));
            }
            
            stage.setScene(new Scene(root));
            stage.setTitle("Iterative Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbIDD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureMD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDistillationController.class.getResource("/fxml/ModelDistillation.fxml"));
            Parent root = loader.load();

            ModelDistillationController controller = loader.getController();
            controller.setNoIterative();
            if (cbMD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("MD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("MD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Model Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbMD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureIMD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDistillationController.class.getResource("/fxml/ModelDistillation.fxml"));
            Parent root = loader.load();

            ModelDistillationController controller = loader.getController();
            if (cbIMD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("IMD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("IMD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Iterative Model Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbIMD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureMDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDataDistillationController.class.getResource("/fxml/ModelDataDistillation.fxml"));
            Parent root = loader.load();

            ModelDataDistillationController controller = loader.getController();
            controller.setNoIterative();
            
            if(cbMDD.isSelected())
            {
                 controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("MDD"))));
            }else{
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("MDD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbMDD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureIMDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDataDistillationController.class.getResource("/fxml/ModelDataDistillation.fxml"));
            Parent root = loader.load();

            ModelDataDistillationController controller = loader.getController();
            
            if(cbMDD.isSelected())
            {
                 controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("IMDD"))));
            }else{
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("IMDD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Iterative Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            Process pr=controller.getProcess();
            if (cbIMDD.isSelected()) {
                processesSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                processesNoSelected.remove(pr);
                processesNoSelected.add(pr);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
