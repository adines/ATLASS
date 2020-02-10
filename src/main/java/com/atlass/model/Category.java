package com.atlass.model;

/**
 * Class that defines a category of an image.
 * @author adines
 */
public class Category implements Comparable<Category>{
    //**************ATTRIBUTES*************************
    private String name; 
    
    //**************BUILDERS*************************
    /**
     * Builder of category.
     * @param name Name of the category.
     */
    public Category(String name)
    {
        this.name=name;
    }
    
    
    //**************GETTERS-SETTERS*************************
    /**
     * Method that returns the name of the category.
     * @return The name of the category.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Method that modifies the name of the category.
     * @param name New name of the category.
     */
    public void setName(String name)
    {
        this.name=name;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this==o)
        {
            return true;
        }else{
            if(o instanceof Category)
            {
                Category cat=(Category)o;
                return cat.getName().equalsIgnoreCase(this.getName());
            }else{
                return false;
            }
        }
    }

    @Override
    public int compareTo(Category o) {
        if(o.getName().equalsIgnoreCase(this.getName()))
        {
            return 0;
        }else{
            return 1;
        }
    }
}
