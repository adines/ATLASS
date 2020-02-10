package com.atlass.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adines
 */
public class Process implements Cloneable {

    private String name;

    private ArrayList<String> transformations;

    private ArrayList<String> models;

    private double threshold;

    public Process(String name) {
        this.name = name;
        this.transformations = new ArrayList<>();
        this.models = new ArrayList<>();
        this.threshold = 0;
    }

    public Process(String name, ArrayList<String> transformations, ArrayList<String> models, double threshold) {
        this.name = name;
        this.transformations = transformations;
        this.models = models;
        this.threshold = threshold;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getTransformations() {
        return this.transformations;
    }

    public ArrayList<String> getModels() {
        return this.models;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public void setTransformations(ArrayList<String> transformations) {
        this.transformations = transformations;
    }

    public void addTransformation(String transformation) {
        this.transformations.add(transformation);
    }

    public void removeTransformation(String transformation) {
        this.transformations.remove(transformation);
    }

    public void setModels(ArrayList<String> models) {
        this.models = models;
    }

    public void addModel(String model) {
        this.models.add(model);
    }

    public void removeModel(String model) {
        this.models.remove(model);
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            if (o instanceof Process) {
                Process pr = (Process) o;
                return pr.getName().equals(this.getName());
            } else {
                return false;
            }
        }
    }

    @Override
    public Object clone() {
        Process obj = null;
        try {
            obj = (Process) super.clone();
            obj.models = new ArrayList<>(this.models);
            obj.transformations = new ArrayList<>(this.transformations);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

}
