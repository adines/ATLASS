package com.annotationtool;

import com.annotationtool.logic.Logic;
import com.annotationtool.presentation.DatasetController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Logic logic = new Logic();

    @Override
    public void start(Stage stage) throws Exception {

//        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dataset.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Dataset.css");

        stage.setTitle("Annotation Tool");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        DatasetController datasetController = (DatasetController) fxmlLoader.getController();

        if (datasetController.getAccepted()) {
            int numCat = datasetController.getNumCategories();
            String dataset = datasetController.getDataset();
            String savePath = datasetController.getSavePath();
            boolean automatically = datasetController.getAutomatically();

//            if (automatically) {
//                logic.initializeDatasetAutomatically(dataset, savePath);
//            } else {
//                logic.initializeDatasetManually(dataset, savePath);
//            }

        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
