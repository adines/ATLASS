package com.annotationtool.presentation;

import com.annotationtool.logic.Logic;
import com.annotationtool.model.Category;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.annotationtool.model.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ImagesController implements Initializable {
    
    private List<Image> selectedIm=new ArrayList<>();
    
    private Map<javafx.scene.image.Image,Image> displayImages=new HashMap<>();

    private static Logic logic = new Logic();

    @FXML
    private ListView<String> lCategories;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane paneImages;

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

        paneImages.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    resizeImages();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        lCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    changeImages(newValue);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void changeImages(String category) throws FileNotFoundException {
        try {
            paneImages.getChildren().clear();
            displayImages.clear();
            selectedIm.clear();
            List<Image> images = null;
            if (!category.equalsIgnoreCase("Unassigned")) {
                images = logic.getImagesCategory(new Category(category));
            } else {
                images = logic.getUnassignedImages();
            }

            if (!images.isEmpty()) {
                int i = 0;
                double w = paneImages.getWidth();
                int tam = (int) ((w - 21) / 8);

                for (Image image : images) {

                    final ImageView imview = new ImageView();
                    imview.setFitWidth(tam);
                    imview.setFitHeight(tam);

                    javaxt.io.Image imag = new javaxt.io.Image(image.getPath());
                    if (imag.getHeight() > imag.getWidth()) {
                        imag.setWidth(224);
                        imag.crop(0, (imag.getHeight() - 224) / 2, 224, 224);
                    } else {
                        imag.setHeight(224);
                        imag.crop((imag.getWidth() - 224) / 2, 0, 224, 224);
                    }
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(imag.getBufferedImage(), "jpg", os);
                    InputStream fis = new ByteArrayInputStream(os.toByteArray());
                    javafx.scene.image.Image im = new javafx.scene.image.Image(fis, tam, tam, true, false);
                    displayImages.put(im, image);
                    imview.setImage(im);

                    imview.setLayoutX((tam + 3) * (i % 8));
                    imview.setLayoutY((tam + 3) * (i / 8));

                    imview.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(!selectedIm.contains(displayImages.get(imview.getImage())))
                            {
                                imview.setOpacity(0.5);
                            }
                            
                        }
                    });

                    imview.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(!selectedIm.contains(displayImages.get(imview.getImage())))
                            {
                                imview.setOpacity(1);
                            }
                        }
                    });

                    imview.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(selectedIm.contains(displayImages.get(imview.getImage())))
                            {
                                imview.setOpacity(1);
                                selectedIm.remove(displayImages.get(imview.getImage()));
                            }else{
                                imview.setOpacity(0.5);
                                selectedIm.add(displayImages.get(imview.getImage()));
                            }
                        }
                    });

                    paneImages.getChildren().add(imview);

                    i++;
                    os.close();
                    fis.close();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void resizeImages() throws FileNotFoundException {

        int i = 0;
        double w = paneImages.getWidth();
        int tam = (int) ((w - 21) / 8);

        for (Node n : paneImages.getChildren()) {
            ((ImageView) n).setFitWidth(tam);
            ((ImageView) n).setFitHeight(tam);

            ((ImageView) n).setLayoutX((tam + 3) * (i % 8));
            ((ImageView) n).setLayoutY((tam + 3) * (i / 8));
            i++;
        }
    }

    @FXML
    void addCategory(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Category");
//        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter the name of the new category:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String category = result.get();
            logic.addCategory(new Category(category));
            lCategories.getItems().add(category);
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
            logic.modifyCategory(new Category(category), cat);
            lCategories.getItems().set(index, cat);
        }
    }
}
