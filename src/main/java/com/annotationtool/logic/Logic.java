package com.annotationtool.logic;

import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import com.annotationtool.model.Image;
import com.annotationtool.persistence.FolderManager;
import com.annotationtool.persistence.Persistence;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class that represents the logic layer of the application.
 *
 * @author adines
 */
public class Logic {

    private static final Persistence persistence = new FolderManager();

    /**
     * Method that continue in the dataset specified.
     *
     * @param path A valid path to the folder that has the dataset.
    *
     */
    public void continueDataset(String path) {
        persistence.continueDataset(path);
    }

    /**
     * Method that generates a new category in the system.
     *
     * @param category This parameter defines the new category to create. This
     * category should not exists in the system.
     *
     */
    public void addCategory(Category category) {
        persistence.addCategory(category);
    }

    /**
     * Method that deletes the category from the system.
     *
     * @param category This parameter defines the new category to delete. This
     * category should exists in the system.
     *
     */
    public void deleteCategory(Category category) {
        List<Image> images = getImagesCategory(category);
        for (Image image : images) {
            deleteImageCategory(image);
        }
        persistence.deleteCategory(category);
    }

    /**
     * Method that modifies a category (the name of this category) from the
     * system.
     *
     * @param category This parameter defines the category to modify. This
     * category should exists in the system.
     * @param name This parameter deines the new name of the category, this name
     * should not exists in the system.
     *
     */
    public void modifyCategory(Category category, String name) {
        persistence.modifyCategory(category, name);
    }

    /**
     * Method that returns all the existing categories in the system.
     *
     * @return A list with all the existing categories in the system.
     *
     */
    public List<Category> getCategories() {
        return persistence.getCategories();
    }

    /**
     * Method that assigns a category to an image and save this image in the
     * corresponding folder.
     *
     * @param image Image to assign a category. This image must not have an
     * assigned category.
     * @param category Category to assign. This category should exist in the
     * system.
     *
     */
    public void addImageToCategory(Image image, Category category) {
        persistence.addImageToCategory(image, category);
    }

    /**
     * Method that delete the category of an image and save this image in the
     * corresponding folder.
     *
     * @param image Image to delete its category. This image should have an
     * assigned category.
     *
     */
    public void deleteImageCategory(Image image) {
        persistence.deleteImageCategory(image);
    }

    /**
     * Method that changes the category of an image and save this image in the
     * corresponding folder.
     *
     * @param image Image to change the category. This image should have an
     * assigned category.
     * @param category Category to assign. This category should exist in the
     * system.
     *
     */
    public void modifyImageCategory(Image image, Category category) {
        persistence.modifyImageCategory(image, category);
    }

    /**
     * Method that returns all the images assigned to an specific category.
     *
     * @param category Category from which to obtain the images. This category
     * should exist in the system.
     * @return A list that contains all the images assigned to an specific
     * category.
     */
    public List<Image> getImagesCategory(Category category) {
        return persistence.getImagesCategory(category);
    }

    /**
     * Method that return all the unassigned images of the system.
     *
     * @return A list that contains all the unassigned images of the system.
     */
    public List<Image> getUnassignedImages() {
        return persistence.getUnassignedImages();
    }

    /**
     * Method that load all the images stored in a path.
     *
     * @param path A valid path to the folder that has the images to load.
     * @return A list that contanins all the images stored in the path.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> loadImagesManually(String path) throws ExcepcionDeAplicacion {
        return persistence.loadImages(path);
    }

    /**
     * Method that initialize the dataset and load all the images stored in the
     * origin path.
     *
     * @param pathOrigin A valid path to the folder that has the images to load.
     * @param pathDest A valid path to the folder used to save the information.
     * @param categories A list with dataset categories.
     * @return A list that contains all the images loaded.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> initializeDatasetManually(String pathOrigin, String pathDest, List<Category> categories) throws ExcepcionDeAplicacion {
        return persistence.initializeDataset(pathOrigin, pathDest, categories);
    }

    /**
     * Method that generates a zip file with the dataset organized. Also, an
     * ipynb file with the necessary steps to train a model with this dataset is
     * generated.
     *
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public void generateDataset() throws ExcepcionDeAplicacion {
        JSONParser parser = new JSONParser();

        persistence.generateDataset();
//        try (Reader reader = new FileReader("")) {
//            JSONObject jsonobject = (JSONObject) parser.parse(reader);

        //Generar archivo ipynb
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
//            throw new ExcepcionDeAplicacion(ex);
//        } catch (IOException | ParseException ex) {
//            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
//            throw new ExcepcionDeAplicacion(ex);
//        }
    }
    
    private void createIpynb()
    {
        JSONObject root=new JSONObject();
        root.put("nbformat", 4);
        root.put("nbformat_minor", 0);
        
        JSONObject metadata=new JSONObject();
        JSONObject colab=new JSONObject();
        colab.put("name", "Name.ipynb");
        colab.put("version", "0.3.2");
        colab.put("provenance", new JSONArray());
        
        JSONObject kernelspec=new JSONObject();
        kernelspec.put("name", "python3");
        kernelspec.put("display_name", "Python 3");
        
        metadata.put("colab", colab);
        metadata.put("kernelspec", kernelspec);
        metadata.put("accelerator", "GPU");
        
        root.put("metadata", metadata);
        
        JSONArray cells=new JSONArray();
        
        JSONObject cell1=new JSONObject();
        cell1.put("cell_type", "markdown");
        
        JSONObject cell1Meta=new JSONObject();
        cell1Meta.put("id", "gqLtwdNgOadF");
        cell1Meta.put("colab_type", "text");
        cell1.put("metadata", cell1Meta);
        
        JSONArray cell1Source=new JSONArray();
        cell1Source.add("## Training model\n");
        cell1Source.add("\n");
        cell1.put("source", cell1Source);
        
        cells.add(cell1);
        
        
        JSONObject cell2=new JSONObject();
        cell2.put("cell_type", "markdown");
        
        JSONObject cell2Meta=new JSONObject();
        cell2Meta.put("id", "dh5y3QhpO5MP");
        cell2Meta.put("colab_type", "text");
        cell2.put("metadata", cell2Meta);
        
        JSONArray cell2Source=new JSONArray();
        cell2Source.add("First of all, we upload the dataset that we will use.");
        cell2.put("source", cell2Source);
        
        cells.add(cell2);
        
        JSONObject cell3=new JSONObject();
        
        
        
        root.put("cells", cells);
        
        
    }


}
