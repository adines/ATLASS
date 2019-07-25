package com.annotationtool.persistence;

import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import com.annotationtool.model.Image;
import java.util.List;

/**
 * Class that represents the persistence layer of the application.
 * @author adines
 */
public interface Persistence {
    
    
    /**
    * Method that generates a new category in the system.
    * @param category This parameter defines the new category to create. This category should not exists in the system.
    **/
    public void addCategory(Category category);
    
    /**
    * Method that deletes the category from the system.
    * @param category This parameter defines the category to delete. This category should exists in the system.
    **/
    public void deleteCategory(Category category);
    
    /**
    * Method that modifies a category (the name of this category) from the system.
    * @param category This parameter defines the category to modify. This category should exists in the system.
    * @param name This parameter deines the new name of the category, this name should not exists in the system.
    **/
    public void modifyCategory(Category category, String name);
    
    /**
    * Method that returns all the existing categories in the system.
    * @return A list with all the existing categories in the system.
    **/
    public List<Category> getCategories();
    
    /**
    * Method that assigns a category to an image and save this image in the corresponding folder.
    * @param image Image to assign a category. This image must not have an assigned category.
    * @param category Category to assign. This category should exist in the system.
    **/
    public void addImageToCategory(Image image, Category category);
    
    /**
    * Method that delete the category of an image and save this image in the corresponding folder.
    * @param image Image to delete its category. This image should have an assigned category.
    **/
    public void deleteImageCategory(Image image);
    
    /**
    * Method that changes the category of an image and save this image in the corresponding folder.
    * @param image Image to change the category. This image should have an assigned category.
    * @param category Category to assign. This category should exist in the system.
    **/
    public void modifyImageCategory(Image image, Category category);
    
    /**
     * Method that returns all the images assigned to an specific category.
     * @param category Category from which to obtain the images. This category should exist in the system.
     * @return A list that contains all the images assigned to an specific category.
     */
    public List<Image> getImagesCategory(Category category);
    
    /**
     * Method that return all the unassigned images of the system.
     * @return A list that contains all the unassigned images of the system.
     */
    public List<Image> getUnassignedImages();
    
    
    /**
     * Method that load all the images stored in a path.
     * @param path A valid path to the folder that has the images to load.
     * @return A list that contanins all the images stored in the path.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> loadImages(String path) throws ExcepcionDeAplicacion;
    
    /**
     * Method that initialize the dataset and load all the images stored in the origin path.
     * @param pathOrigin A valid path to the folder that has the images to load. 
     * @param pathDest A valid path to the folder used to save the information.
     * @param categories A list with dataset categories.
     * @return A list that contains all the images loaded.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> initializeDataset(String pathOrigin, String pathDest, List<Category> categories) throws ExcepcionDeAplicacion;
    
    /**
     * Method that generates a zip file with the dataset organized. Also, an ipynb 
     * file with the necessary steps to train a model with this dataset is generated.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public void generateDataset() throws ExcepcionDeAplicacion;
    
}