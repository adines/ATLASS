package com.atlass.presentation;

import com.atlass.logic.Logic;
import com.atlass.model.Category;
import com.atlass.model.ExcepcionDeAplicacion;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.atlass.model.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import com.atlass.model.Process;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ImagesController implements Initializable {

    private List<Image> selectedIm = new ArrayList<>();

    private Map<javafx.scene.image.Image, Image> displayImages = new HashMap<>();

    private static Logic logic = new Logic();

    @FXML
    private ListView<String> lCategories;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane paneImages;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private MenuItem editMenu;

    @FXML
    private MenuItem deleteMenu;

    @FXML
    private MenuItem uncategorizeMenu;

    @FXML
    private MenuItem changeMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneImages.prefWidthProperty().bind(scrollPane.widthProperty());
        List<Category> categories = logic.getCategories();
        for (Category cat : categories) {
            lCategories.getItems().add(cat.getName());
        }
        lCategories.getItems().add("Unassigned");

        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                paneImages.prefWidth(newValue.doubleValue() - 50);
                if (!displayImages.values().isEmpty()) {
                    try {
                        changeImages(lCategories.getSelectionModel().getSelectedItems().get(0), newValue.doubleValue() - 20, false);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);

                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("An unexpected error ocurred.");
                        alert.setContentText("Unexpected error try to reset the application.");
                        alert.showAndWait();
                    }
                }
            }
        });

        lCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    changeImages(newValue, scrollPane.getWidth() - 20, true);
                    changeMenu.setDisable(true);
                    uncategorizeMenu.setDisable(true);
                    if (newValue.equalsIgnoreCase("Unassigned")) {
                        modifyButton.setDisable(true);
                        deleteButton.setDisable(true);
                        editMenu.setDisable(true);
                        deleteMenu.setDisable(true);
                    } else {
                        modifyButton.setDisable(false);
                        deleteButton.setDisable(false);
                        editMenu.setDisable(false);
                        deleteMenu.setDisable(false);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An unexpected error ocurred.");
                    alert.setContentText("Unexpected error try to reset the application.");
                    alert.showAndWait();
                }
            }
        });
    }

    private void changeImages(String category, double tamPane, boolean clear) throws FileNotFoundException {

        if (category == null) {
            return;
        }

        paneImages.getChildren().clear();
        displayImages.clear();
        if (clear) {
            selectedIm.clear();
        }
        List<Image> images = null;
        if (!category.equalsIgnoreCase("Unassigned")) {
            images = logic.getImagesCategory(new Category(category));
        } else {
            images = logic.getUnassignedImages();
        }

        if (!images.isEmpty()) {
            int i = 0;
            int tam = (int) ((tamPane - 24) / 8);

            for (Image image : images) {

                final ImageView imview = new ImageView();
                imview.setFitWidth(tam);
                imview.setFitHeight(tam);

                javafx.scene.image.Image im2 = new javafx.scene.image.Image("file:" + image.getPath(), tam, tam, true, false, true);
                displayImages.put(im2, image);
                imview.setImage(im2);

                imview.setLayoutX((tam + 3) * (i % 8));
                imview.setLayoutY((tam + 3) * (i / 8));
                if (selectedIm.contains(image)) {
                    imview.setOpacity(0.5);
                }

                imview.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!selectedIm.contains(displayImages.get(imview.getImage()))) {
                            imview.setOpacity(0.5);
                        }

                    }
                });

                imview.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!selectedIm.contains(displayImages.get(imview.getImage()))) {
                            imview.setOpacity(1);
                        }
                    }
                });

                imview.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (selectedIm.contains(displayImages.get(imview.getImage()))) {
                            imview.setOpacity(1);
                            selectedIm.remove(displayImages.get(imview.getImage()));
                        } else {
                            imview.setOpacity(0.5);
                            selectedIm.add(displayImages.get(imview.getImage()));
                        }
                        if (selectedIm.isEmpty()) {
                            changeMenu.setDisable(true);
                            uncategorizeMenu.setDisable(true);
                        } else {
                            changeMenu.setDisable(false);
                            uncategorizeMenu.setDisable(false);
                        }
                    }
                });

                paneImages.getChildren().add(imview);

                i++;
            }
        }

    }

    @FXML
    void addCategory(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Category");
        dialog.setContentText("Please enter the name of the new category:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String category = result.get();
            if (StringUtils.isAlphanumeric(category)) {
                if (!logic.getCategories().contains(new Category(category))) {
                    logic.addCategory(new Category(category));
                    lCategories.getItems().add(category);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Category not allowed");
                    alert.setContentText("The category " + category + " already exists.");

                    alert.showAndWait();
                    addCategory(event);
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Category not allowed");
                alert.setContentText("The name of the category contains characters not allowed.");

                alert.showAndWait();
                addCategory(event);
            }

        }
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        String category = lCategories.getSelectionModel().getSelectedItems().get(0);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Category");
        alert.setContentText("Are you sure to delete this category?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            logic.deleteCategory(new Category(category));
            lCategories.getItems().remove(category);
        }
    }

    @FXML
    void modifyCategory(ActionEvent event) {
        String category = lCategories.getSelectionModel().getSelectedItems().get(0);
        int index = lCategories.getSelectionModel().getSelectedIndex();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Category");
        dialog.setContentText("Please enter the new name of the category:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String cat = result.get();

            if (StringUtils.isAlphanumeric(category)) {
                if (!logic.getCategories().contains(new Category(category))) {
                    logic.modifyCategory(new Category(category), cat);
                    lCategories.getItems().set(index, cat);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Category not allowed");
                    alert.setContentText("The category " + category + " already exists.");

                    alert.showAndWait();
                    modifyCategory(event);
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Category not allowed");
                alert.setContentText("The name of the category contains characters not allowed.");

                alert.showAndWait();
                modifyCategory(event);
            }

        }
    }

    @FXML
    void changeCategory(ActionEvent event) {

        List<String> options = new ArrayList<>();
        String selected = lCategories.getSelectionModel().getSelectedItem();
        List<Category> categories = logic.getCategories();
        for (Category cat : categories) {
            if (!cat.getName().equalsIgnoreCase(selected)) {
                options.add(cat.getName());
            }

        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("New Category");
        dialog.setHeaderText("Select the new category");
        dialog.setContentText("Categories:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String newCat = result.get();

            for (Image im : selectedIm) {
                logic.modifyImageCategory(im, new Category(newCat));
            }
            lCategories.getSelectionModel().select(newCat);
        }
    }

    @FXML
    void deleteImagesCategory(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Images Category");
        alert.setContentText("Are you sure to delete these images from the category?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (Image im : selectedIm) {
                logic.deleteImageCategory(im);
            }
            lCategories.getSelectionModel().select("Unassigned");
        }

    }

    @FXML
    void createDataset(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Create dataset");
        alert.setContentText("Are you sure to create the dataset?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(ProcessesController.class.getResource("/fxml/Processes.fxml"));
                Parent root = loader.load();

                ProcessesController controller = loader.getController();

                if (logic.getUnassignedImages().isEmpty()) {
                    controller.setNoUnlabelledImages();
                }

                stage.setScene(new Scene(root));
                stage.setTitle("Processes");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(this.deleteButton.getScene().getWindow());
                stage.showAndWait();
                if (controller.getAccepted()) {
                    logic.generateDataset();
                    List<Process> processes = controller.getProcesses();
                    logic.generateProcessIpynb(processes);
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Dataset created");
                    alert2.setHeaderText("The dataset has been createad.");
                    alert2.setContentText("The dataset and the corresponding ipynb files have been created.");
                    alert2.showAndWait();
                }

            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);

                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("An unexpected error ocurred.");
                alert2.setContentText("Unexpected error generating the dataset. Try it again.");
                alert2.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    void loadImages(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoadImages.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/LoadImages.css");

            Stage stage = new Stage();
            stage.setTitle("Annotation Tool");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            lCategories.getItems().clear();
            for (Category cat : logic.getCategories()) {
                lCategories.getItems().add(cat.getName());
            }
            lCategories.getItems().add("Unassigned");

            lCategories.getSelectionModel().selectFirst();

        } catch (IOException ex) {
            Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An unexpected error ocurred.");
            alert.setContentText("Unexpected error try to reset the application.");
            alert.showAndWait();
        }

    }

    @FXML
    void changeDataset(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Change dataset");
        alert.setContentText("Are you sure to change the dataset?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dataset.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Dataset.css");

                Stage stage = (Stage) lCategories.getScene().getWindow();
                stage.hide();
                stage.setMaxWidth(589);
                stage.setMinWidth(589);
                stage.setMinHeight(420);
                stage.setMaxHeight(420);
                stage.setTitle("Annotation Tool");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("An unexpected error ocurred.");
                alert2.setContentText("Unexpected error try to reset the application.");
                alert2.showAndWait();
            }
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        Stage stage = (Stage) lCategories.getScene().getWindow();
        stage.close();
    }

}
