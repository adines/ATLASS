package com.annotationtool.model;

import java.util.Arrays;

/**
 * Class that defines the images of the system.
 * @author adines
 */
public class Image {
    
    //**************ATTRIBUTES*************************
    private String name;
    private String path;
    private double[] descriptors;
    private Category category;
    
    
    //**************BUILDERS*************************
    /**
     * Builder of image.
     * @param name Name of the image.
     * @param path Path where the image is stored.
     * @param desc Descriptors of the image.
     * @param c Category of the image.
     */
    public Image(String name, String path, double [] desc,Category c)
    {
        this.name=name;
        this.path=path;
        this.descriptors=Arrays.copyOf(desc, desc.length);
        this.category=c;
        
    }
    
    /**
     * Builder of image.
     * @param name Name of the image.
     * @param path Path where the image is stored.
     * @param c Category of the image.
     */
    public Image(String name, String path,Category c)
    {
        this.name=name;
        this.path=path;
        this.descriptors=null;
        this.category=c;
    }
    
    /**
     * Builder of image.
     * @param name Name of the image.
     * @param path Path where the image is stored.
     */
    public Image(String name, String path)
    {
        this.name=name;
        this.path=path;
        this.descriptors=null;
        this.category=null;
    }
    
    
    //**************GETTERS-SETTERS*************************
    /**
     * Method that returns the name of the image.
     * @return The name of the image.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Method that modifies the name of the image.
     * @param name New name of the image.
     */
    public void setName(String name)
    {
        this.name=name;
    }
    
    /**
     * Method that returns the path of the image.
     * @return The path of the image.
     */
    public String getPath()
    {
        return this.path;
    }
    
    /**
     * Method that modifies the path of the image.
     * @param path New path of the image.
     */
    public void setPath(String path)
    {
        this.path=path;
    }
    
    /**
     * Method that returns the descriptors of the image.
     * @return An array with the descriptors of the image.
     */
    public double[] getDescriptors()
    {
        return Arrays.copyOf(descriptors, descriptors.length);
    }
    
    /**
     * Method that modifies the descriptors of the image.
     * @param desc List with the new descriptors of the image.
     */
    public void setDescriptors(double [] desc)
    {
        this.descriptors=Arrays.copyOf(desc, desc.length);
    }
    
    /**
     *Method that returns the category of the image.
     * @return The category of the image.
     */
    public Category getCategory()
    {
        return this.category;
    }
    
    /**
     * Method that modifies the category of the image.
     * @param c New category of the image.
     */
    public void setCategory(Category c)
    {
        this.category=c;
    }
    
}