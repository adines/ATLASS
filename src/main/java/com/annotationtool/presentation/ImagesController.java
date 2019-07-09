/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author adines
 */
public class ImagesController implements Initializable {

    private static Logic logic = new Logic();

    @FXML
    private ListView<String> lCategories;

    @FXML
    private GridPane gridImages;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Category> categories = logic.getCategories();
            for (Category cat : categories) {
                lCategories.getItems().add(cat.getName());
            }

            lCategories.getItems().add("Unassigned");
            List<Image> images;
            if (categories != null) {
                changeImages(categories.get(0).getName());
                lCategories.getSelectionModel().select(0);
            } else {
                changeImages("Unnasigned");
                lCategories.getSelectionModel().select(0);
            }
            
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeImages(String category) throws FileNotFoundException {
        List<Image> images = null;
        if (category != "Unnasigned") {
            images = logic.getImagesCategory(new Category(category));
        } else {
            images = logic.getUnassignedImages();
        }

        int i = 0;
        for (Image image : images) {
            ImageView imview = new ImageView();
            FileInputStream fi = new FileInputStream(image.getPath());
            javafx.scene.image.Image im = new javafx.scene.image.Image(fi);
            imview.setImage(im);
            gridImages.add(imview, i % 2, i / 2);
            i++;
        }

    }
}
