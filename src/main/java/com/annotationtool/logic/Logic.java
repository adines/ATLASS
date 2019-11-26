package com.annotationtool.logic;

import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import com.annotationtool.model.Image;
import com.annotationtool.persistence.FolderManager;
import com.annotationtool.persistence.Persistence;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.annotationtool.model.Process;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        persistence.generateDataset();
    }

    private void createProcessND(Process process) throws ExcepcionDeAplicacion {
        FileWriter file = null;
        try {
            JSONObject root = new JSONObject();
            
            root.put("nbformat", 4);
            root.put("nbformat_minor", 0);
            
            JSONObject metadata = new JSONObject();
            JSONObject colab = new JSONObject();
            colab.put("name", "No Distillation.ipynb");
            colab.put("version", "0.3.2");
            colab.put("provenance", new JSONArray());
            
            JSONObject kernelspec = new JSONObject();
            kernelspec.put("name", "python3");
            kernelspec.put("display_name", "Python 3");
            metadata.put("colab", colab);
            metadata.put("kernelspec", kernelspec);
            metadata.put("accelerator", "GPU");
            root.put("metadata", metadata);
            
            JSONArray cells = new JSONArray();
            JSONObject cell1 = new JSONObject();
            cell1.put("cell_type", "markdown");
            
            JSONObject cell1Meta = new JSONObject();
            cell1Meta.put("id", "gqLtwdNgOadF");
            cell1Meta.put("colab_type", "text");
            cell1.put("metadata", cell1Meta);
            
            JSONArray cell1Source = new JSONArray();
            cell1Source.add("## No distillation\n");
            cell1Source.add("\n");
            cell1.put("source", cell1Source);
            cells.add(cell1);
            
            JSONObject cell2 = new JSONObject();
            cell2.put("cell_type", "markdown");
            
            JSONObject cell2Meta = new JSONObject();
            cell2Meta.put("id", "dh5y3QhpO5MP");
            cell2Meta.put("colab_type", "text");
            cell2.put("metadata", cell2Meta);
            
            JSONArray cell2Source = new JSONArray();
            cell2Source.add("In this notebook we train models without applying any distillation or transfer learning technique.");
            cell2.put("source", cell2Source);
            cells.add(cell2);
            
            JSONObject cell3 = new JSONObject();
            cell3.put("cell_type", "code");
            
            JSONObject cell3Metadata = new JSONObject();
            cell3Metadata.put("id", "-iwz5io7PYsd");
            cell3Metadata.put("colab_type", "code");
            cell3Metadata.put("colab", new JSONObject());
            cell3.put("metadata", cell3Metadata);
            
            JSONArray cell3Source = new JSONArray();
            cell3Source.add("from google.colab import drive\n");
            cell3Source.add("drive.mount('/content/drive')");
            cell3.put("source", cell3Source);
            cell3.put("execution_count", 0);
            cell3.put("outputs", new JSONArray());
            cells.add(cell3);
            
            JSONObject cell3_5 = new JSONObject();
            cell3_5.put("cell_type", "code");
            JSONObject cell3_5Metadata = new JSONObject();
            cell3_5Metadata.put("id", "-iwz5io7PYsd");
            cell3_5Metadata.put("colab_type", "code");
            cell3_5Metadata.put("colab", new JSONObject());
            cell3_5.put("metadata", cell3_5Metadata);
            
            JSONArray cell3_5Source = new JSONArray();
            cell3_5Source.add("from google.colab import files\n");
            cell3_5Source.add("uploaded = files.upload()\n");
            cell3_5Source.add("!unzip dataset.zip -d dataset");
            cell3_5.put("source", cell3_5Source);
            cell3_5.put("execution_count", 0);
            cell3_5.put("outputs", new JSONArray());
            cells.add(cell3_5);
            
            JSONObject cell4 = new JSONObject();
            cell4.put("cell_type", "code");
            
            JSONObject cell4Metadata = new JSONObject();
            cell4Metadata.put("id", "h75IjZlhwL9H");
            cell4Metadata.put("colab_type", "code");
            cell4Metadata.put("colab", new JSONObject());
            cell4.put("metadata", cell4Metadata);
            JSONArray cell4Source = new JSONArray();
            cell4Source.add("import warnings\n");
            cell4Source.add("import os\n");
            cell4Source.add("import shutil\n");
            cell4Source.add("import glob\n");
            cell4Source.add("import random\n");
            cell4Source.add("import random\n");
            cell4Source.add("from fastai.vision import *\n");
            cell4Source.add("\n");
            cell4Source.add("warnings.filterwarnings(\"ignore\", category=UserWarning, module=\"torch.nn.functional\")\n");
            cell4Source.add("\n");
            cell4Source.add("dataset=\"dataset\"");
            cell4Source.add("\n");
            cell4Source.add("os.makedirs('kvasir/train')\n");
            cell4Source.add("os.makedirs('kvasir/valid')\n");
            cell4Source.add("os.makedirs('kvasir/images')\n");
            cell4Source.add("\n");
            cell4Source.add("classesPaths=sorted(glob.glob(dataset+\"/images/*\"))\n");
            cell4Source.add("classes=[pt.split(os.sep)[-1] for pt in classesPaths if os.path.isdir(pt)]\n");
            cell4Source.add("images=[pt for pt in classesPaths if not os.path.isdir(pt)]\n");
            cell4Source.add("\n");
            cell4Source.add("for im in images:\n");
            cell4Source.add("  shutil.move(images,dataset+'/images/')\n");
            cell4Source.add("\n");
            cell4Source.add("for cl in classes:\n");
            cell4Source.add("  os.mkdir(dataset+'/train/'+cl)\n");
            cell4Source.add("  images=sorted(glob.glob(dataset+'/images/'+cl+'/*'))\n");
            cell4Source.add("  for i in range(len(images)*0.75):\n");
            cell4Source.add("    images=sorted(glob.glob(dataset+'/images/'+cl+'/*'))\n");
            cell4Source.add("    j=random.randint(0,len(images)-1)\n");
            cell4Source.add("    shutil.move(images[j],dataset+'/train/'+cl)");
            cell4Source.add("\n");
            cell4Source.add("def learn_with_model(dataset,model):\n");
            cell4Source.add("  data=ImageDataBunch.from_folder(dataset,\n");
            cell4Source.add("        ds_tfms=get_transforms(), size=224,bs=32).normalize(imagenet_stats)\n");
            cell4Source.add("  learn = cnn_learner(data, model, metrics=accuracy)\n");
            cell4Source.add("  learn.fit_one_cycle(2)\n");
            cell4Source.add("  learn.unfreeze()\n");
            cell4Source.add("  learn.lr_find()\n");
            cell4Source.add("  lr=learn.recorder.lrs[np.argmin(learn.recorder.losses)]\n");
            cell4Source.add("  if lr<1e-05:\n");
            cell4Source.add("    lr=1e-03\n");
            cell4Source.add("  learn.fit_one_cycle(8,max_lr=slice(lr/100,lr))\n");
            cell4Source.add("  return learn,data\n");
            cell4Source.add("\n");
            for (String model : process.getModels()) {
                cell4Source.add("learner_" + model.toLowerCase() + ",data=learn_with_model(dataset,models." + model.toLowerCase() + ")\n");
                cell4Source.add("learner_" + model.toLowerCase() + ".export('/content/drive/My Drive/learner_" + model.toLowerCase() + ".pkl')\n");
            }   cell4.put("source", cell4Source);
            cell4.put("execution_count", 0);
            cell4.put("outputs", new JSONArray());
            cells.add(cell4);
            
            root.put("cells", cells);
            
            file = new FileWriter("NoDistillation.ipynb");
            file.write(root.toJSONString());
            file.flush();
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionDeAplicacion(ex);
        } finally {
            try {
                if(file!=null)
                {
                    file.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionDeAplicacion(ex);
            }
        }
    }

    private void createProcessDD(Process process) {

    }

    private void createProcessIDD(Process process) {

    }

    private void createProcessMD(Process process) {

    }

    private void createProcessIMD(Process process) {

    }

    private void createProcessMDD(Process process) {

    }

    private void createProcessIMDD(Process process) {

    }

    public void generateProcessIpynb(List<Process> processes) throws ExcepcionDeAplicacion{
        for (Process process : processes) {
            switch (process.getName()) {
                case "ND":
                    createProcessND(process);
                    break;
                case "DD":
                    createProcessDD(process);
                    break;
                case "IDD":
                    createProcessIDD(process);
                    break;
                case "MD":
                    createProcessMD(process);
                    break;
                case "IMD":
                    createProcessIMD(process);
                    break;
                case "MDD":
                    createProcessMDD(process);
                    break;
                case "IMDD":
                    createProcessIMDD(process);
                    break;
            }
        }
    }

}
