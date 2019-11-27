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
import javafx.scene.control.Alert;
import org.bytedeco.opencv.presets.opencv_core;

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

    private boolean accepted;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        accepted = false;
        processesNoSelected = new ArrayList<Process>();
        processesSelected = new ArrayList<Process>();

        //No distillation por defecto
        Process ndProcess = new Process("ND");
        ndProcess.addModel("ResNet50");

        //Data Distillation por defecto
        Process ddProcess = new Process("DD");
        ddProcess.addTransformation("H Flip");
        ddProcess.addTransformation("V Flip");
        ddProcess.addTransformation("H+V Flip");
        ddProcess.addTransformation("Blurring");
        ddProcess.addTransformation("Gamma");

        //Iterative data distillation por defecto
        Process iddProcess = new Process("IDD");
        iddProcess.addTransformation("H Flip");
        iddProcess.addTransformation("V Flip");
        iddProcess.addTransformation("H+V Flip");
        iddProcess.addTransformation("Blurring");
        iddProcess.addTransformation("Gamma");
        iddProcess.setThreshold(0.8);

        //Model distillation por defecto
        Process mdProcess = new Process("MD");
        mdProcess.addModel("ResNet34");
        mdProcess.addModel("ResNet50");
        mdProcess.addModel("ResNet101");
        mdProcess.addModel("DenseNet121");

        //Iterative model distillation por defecto
        Process imdProcess = new Process("IMD");
        imdProcess.addModel("ResNet34");
        imdProcess.addModel("ResNet50");
        imdProcess.addModel("ResNet101");
        imdProcess.addModel("DenseNet121");
        imdProcess.setThreshold(0.8);

        //Model data distillation por defecto
        Process mddProcess = new Process("MDD");
        mddProcess.addModel("ResNet34");
        mddProcess.addModel("ResNet50");
        mddProcess.addModel("ResNet101");
        mddProcess.addModel("DenseNet121");
        mddProcess.addTransformation("H Flip");
        mddProcess.addTransformation("V Flip");
        mddProcess.addTransformation("H+V Flip");
        mddProcess.addTransformation("Blurring");
        mddProcess.addTransformation("Gamma");

        //Iterative model data distillation por defecto
        Process imddProcess = new Process("IMDD");
        imddProcess.addModel("ResNet34");
        imddProcess.addModel("ResNet50");
        imddProcess.addModel("ResNet101");
        imddProcess.addModel("DenseNet121");
        imddProcess.addTransformation("H Flip");
        imddProcess.addTransformation("V Flip");
        imddProcess.addTransformation("H+V Flip");
        imddProcess.addTransformation("Blurring");
        imddProcess.addTransformation("Gamma");
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
    void selectCb(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox cb = (CheckBox) event.getSource();
            String name = cb.getText();
            name = name.split(":")[0];
            if (cb.isSelected()) {
                int i = processesNoSelected.indexOf(new Process(name));
                Process pr = processesNoSelected.get(i);
                processesNoSelected.remove(pr);
                processesSelected.add(pr);
            } else {
                int i = processesSelected.indexOf(new Process(name));
                Process pr = processesSelected.get(i);
                processesSelected.remove(pr);
                processesNoSelected.add(pr);
            }
        }
    }

    @FXML
    void configureND(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(NoDistillationController.class.getResource("/fxml/NoDistillation.fxml"));
            Parent root = loader.load();
            NoDistillationController controller = loader.getController();

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

            if (controller.getAccepted()) {
                Process pr = controller.getProcess();
                if (cbND.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDataDistillationController.class.getResource("/fxml/ModelDataDistillation.fxml"));
            Parent root = loader.load();
            ModelDataDistillationController controller = loader.getController();

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

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbDD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void configureIDD(ActionEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ModelDataDistillationController.class.getResource("/fxml/ModelDataDistillation.fxml"));
            Parent root = loader.load();
            ModelDataDistillationController controller = loader.getController();

            if (cbIDD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("IDD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("IDD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Iterative Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbIDD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
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

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbMD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
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

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbIMD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
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

            if (cbMDD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("MDD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("MDD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbMDD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
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

            if (cbIMDD.isSelected()) {
                controller.initProcess(processesSelected.get(processesSelected.lastIndexOf(new Process("IMDD"))));
            } else {
                controller.initProcess(processesNoSelected.get(processesNoSelected.lastIndexOf(new Process("IMDD"))));
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Iterative Model + Data Distillation configuration");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();

            Process pr = controller.getProcess();
            if (controller.getAccepted()) {
                if (cbIMDD.isSelected()) {
                    processesSelected.remove(pr);
                    processesSelected.add(pr);
                } else {
                    processesNoSelected.remove(pr);
                    processesNoSelected.add(pr);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ProcessesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void accept(ActionEvent event) {
        if (processesSelected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid selection");
            alert.setContentText("You must select at least one option.");
            alert.showAndWait();
        } else {
            this.accepted = true;
            Stage stage = (Stage) this.bConfDD.getScene().getWindow();
            stage.close();
        }
    }

    public boolean getAccepted() {
        return this.accepted;
    }

    public List<Process> getProcesses() {
        return this.processesSelected;
    }

}
