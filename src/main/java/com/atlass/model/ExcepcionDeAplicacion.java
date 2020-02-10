package com.atlass.model;

/**
 * General exception for any type of error in the application.
 * @author adines
 */
public class ExcepcionDeAplicacion extends Exception{
    
    private Throwable anidado;
    
    public ExcepcionDeAplicacion()
    {
        this.anidado=new Exception();
    }
    
    /**
     * 
     * @param msg Error message.
     */
    public ExcepcionDeAplicacion(String msg)
    {
        this.anidado=new Exception(msg);
    }
    
    /**
     * 
     * @param e Nested exceptionon which the new exception is built.
     */
    public ExcepcionDeAplicacion(Exception e)
    {
        this.anidado=new Exception(e);
    }
    
    /**
     * 
     * @param msg Error message.
     * @param e Nested exceptionon which the new exception is built.
     */
    public ExcepcionDeAplicacion(String msg,Exception e)
    {
        this.anidado=new Exception(msg, e);
    }
    
    
    /**
     * This method return the exception message.
     * @return The exception message.
     */
    @Override
    public String getMessage()
    {
        return this.anidado.getMessage();
    }
}
