package com.annotationtool.logic;

import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import com.annotationtool.model.Image;
import com.annotationtool.persistence.FolderManager;
import com.annotationtool.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.NormalizedEuclideanSimilarity;
import org.datavec.image.loader.NativeImageLoader;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.deeplearning4j.zoo.*;
import org.deeplearning4j.zoo.model.VGG16;
import org.deeplearning4j.nn.graph.*;
import org.json.simple.parser.JSONParser;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;

/**
 * Class that represents the logic layer of the application.
 *
 * @author adines
 */
public class Logic {

    private static final Persistence persistence = new FolderManager();

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
        List<Image> images=getImagesCategory(category);
        for(Image image:images)
        {
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
     * Method that load all the images stored in a path and assign them a
     * category automatically.
     *
     * @param path A valid path to the folder that has the images to load.
     * @return A list that contanins all the classified images.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> loadImagesAutomatically(String path) throws ExcepcionDeAplicacion {
        List<Image> images=persistence.loadImages(path);
        if(getUnassignedImages().isEmpty())
        {
            return images;
        }else{
            return classifyImages(getUnassignedImages(), getCategories());
        }
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
     * Method that initialize the dataset, load all the images stored in the
     * origin path and classifies them.
     *
     * @param pathOrigin A valid path to the folder that has the images to load.
     * @param pathDest A valid path to the folder used to save the information.
     * @param categories A list with dataset categories.
     * @return A list that contains all the classified images.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> initializeDatasetAutomatically(String pathOrigin, String pathDest, List<Category> categories) throws ExcepcionDeAplicacion {
        List<Image>images=persistence.initializeDataset(pathOrigin, pathDest, categories);
        if(getUnassignedImages().isEmpty())
        {
            return images;
        }else{
            return classifyImages(getUnassignedImages(),categories);
        }
    }

    
    /**
     * Method that classifies a list of images.
     * @param images A list of images to classify.
     * @param categories A list with dataset categories.
     * @return A list that contains all the classified images.
     * @throws ExcepcionDeAplicacion 
     */
    private List<Image> classifyImages(List<Image> images,List<Category> categories) throws ExcepcionDeAplicacion{
        int empty = 0;
        int knn = 0;
        Set<Category> cate=new HashSet<>();
        for (Category cat : categories) {
            if (persistence.getImagesCategory(cat).isEmpty()) {
                empty++;
            } else {
                cate.add(cat);
//                if (persistence.getImagesCategory(cat).size() >= 3) {
                    knn++;
//                }
            }
        }
        List<Image> classifiedImages = null;
        if (empty == categories.size()) {
            //KMeans
            List<Image> imagesDesc = extractDescriptors(images);
            classifiedImages = classifyImagesKmeans(imagesDesc, categories.size());

        } else if (knn == categories.size()) {
            //Knn
            List<Image> dataset=new ArrayList<>();
            for(Category c:getCategories())
            {
                dataset.addAll(getImagesCategory(c));
            }
            classifiedImages=classifyImagesKnn(extractDescriptors(dataset), extractDescriptors(images));

        } else {
            //Similarity 
            classifiedImages=classifyImagesSimilarity(extractDescriptors(images), new ArrayList<>(cate), 0.75, categories.size()-cate.size());
        }

        //Organizar imagenes
        for(Image im:classifiedImages)
        {
            addImageToCategory(im, im.getCategory());
        }
        ArrayList<Image>result=new ArrayList<>();
        for(Category cat:categories)
        {
            result.addAll(getImagesCategory(cat));
        }
        return result;
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

    /**
     * Method that given a list of images, extract their descriptors.
     *
     * @param images List of images to extract its descriptors.
     * @return A list with the images containing the corresponding descriptors.
     * @throws com.annotationtool.model.ExcepcionDeAplicacion
     */
    public List<Image> extractDescriptors(List<Image> images) throws ExcepcionDeAplicacion {
        try {
            ZooModel zooModel = new VGG16();
            ComputationGraph vgg16 = (ComputationGraph) zooModel.initPretrained(PretrainedType.IMAGENET);

            for (Image im : images) {
                String path = im.getPath();
                File file = new File(path);
                NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
                INDArray image = loader.asMatrix(file);
                DataNormalization scaler = new VGG16ImagePreProcessor();
                scaler.transform(image);

                Map<String, INDArray> stringINDArrayMap = vgg16.feedForward(image, false);

                INDArray resultfc7 = stringINDArrayMap.get("fc2");
                double descriptores[] = resultfc7.toDoubleVector();
                im.setDescriptors(descriptores);
            }

        } catch (IOException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionDeAplicacion(ex);
        }
        return images;
    }

    /**
     * Method that given a list of images, classify them using Kmeans algorithm.
     *
     * @param images List of images to classify.
     * @param numCat Number of categories to classify.
     * @return A list of images classified.
     */
    private List<Image> classifyImagesKmeans(List<Image> images, int numCat) {
        KMeans kmeans = new KMeans(numCat);
        Dataset data = new DefaultDataset();
        for (Image image : images) {
            data.add(new DenseInstance(image.getDescriptors()));
        }

        Dataset clusters[] = kmeans.cluster(data);
        int cl = 0;
        for (Dataset d : clusters) {
            for (Instance i : d) {
                for (int j = 0; j < data.size(); j++) {
                    if (data.instance(j).getID() == i.getID()) {
                        images.get(j).setCategory(new Category("Cluster " + cl));
                        break;
                    }
                }
            }
            cl++;
        }

        return images;
    }

    /**
     * Method that given a list of images and a dataset, classify them using Knn
     * algorithm.
     *
     * @param dataset List of images that represents the dataset.
     * @param images List of images to classify.
     * @return A list of images classified.
     */
    private List<Image> classifyImagesKnn(List<Image> dataset, List<Image> images) {
        List<Image> result = new ArrayList<>();
        KNearestNeighbors knn = new KNearestNeighbors(3);

        Dataset data = new DefaultDataset();

        for (Image image : dataset) {
            data.add(new DenseInstance(image.getDescriptors(), image.getCategory()));
        }

        knn.buildClassifier(data);

        for (Image im : images) {
            Map<Object, Double> prediction = knn.classDistribution(new DenseInstance(im.getDescriptors()));
            Set<Object> categories = prediction.keySet();
            double max = 0;
            Category maxCat = null;
            for (Object o : categories) {
                Category cat = (Category) o;
                double d = prediction.get(o).doubleValue();
                if (d > max) {
                    max = d;
                    maxCat = cat;
                }
            }
            im.setCategory(maxCat);
            result.add(im);

        }
        return result;
    }

    /**
     * Method that given a list of images, a list of categories, a threshold and
     * the total number of categories, classify them using a similarity algorithm.
     * 
     * @param images List of images to classify.
     * @param categories A list of categories to classify the images.
     * @param threshold Similarity threshold to belong to a class. 
     * @param numCat Total number of categories.
     * @return A list of images classified.
     */
    private List<Image> classifyImagesSimilarity(List<Image> images, List<Category> categories, double threshold,int numCat) {
        Dataset data = new DefaultDataset();
        List<Image>result=new ArrayList();
        List<Image> unclassified=new ArrayList<>();
        for (Category cat : categories) {
            List<Image> imagesCat = getImagesCategory(cat);
            for (Image i : imagesCat) {
                data.add(new DenseInstance(i.getDescriptors(), i.getCategory()));
            }
        }
        NormalizedEuclideanSimilarity similarity = new NormalizedEuclideanSimilarity(data);
        for (Image im : images) {
            for (Category cat : categories) {
                int num=0;
                double sim=0;
                List<Image> imagesCat = getImagesCategory(cat);
                for (Image i : imagesCat) {
                    num++;
                    sim = similarity.measure(new DenseInstance(i.getDescriptors()), new DenseInstance(im.getDescriptors()));
                }
                sim=sim/num;
                if(sim>threshold)
                {
                    im.setCategory(cat);
                    break;
                }
            }
            if(im.getCategory()==null)
            {
                unclassified.add(im);
            }
        }

        result.addAll(classifyImagesKmeans(unclassified,numCat-categories.size()));
        
        return result;
    }
}
