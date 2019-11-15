
package com.annotationtool.model;

import java.util.ArrayList;

/**
 *
 * @author adines
 */
public class Process {
    
    private String name;
    
    private ArrayList<String> transformations;
    
    private ArrayList<String> models;
    
    private float threshold;
    
    public Process(String name)
    {
        this.name=name;
        this.transformations=new ArrayList<>();
        this.models=new ArrayList<>();
        this.threshold=0;
    }
    
    public Process(String name, ArrayList<String> transformations, ArrayList<String> models,float threshold)
    {
        this.name=name;
        this.transformations=transformations;
        this.models=models;
        this.threshold=threshold;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public ArrayList<String> getTransformations()
    {
        return this.transformations;
    }
    
    public ArrayList<String> getModels()
    {
        return this.models;
    }
    
    public float getThreshold()
    {
        return this.threshold;
    }
    
    public void setTransformations(ArrayList<String> transformations)
    {
        this.transformations=transformations;
    }
    
    public void addTransformation(String transformation)
    {
        this.transformations.add(transformation);
    }
    
    public void setModels(ArrayList<String> models)
    {
        this.models=models;
    }
    
    public void addModel(String model)
    {
        this.models.add(model);
    }
    
    public void setThreshold(float threshold)
    {
        this.threshold=threshold;
    }
    
}
