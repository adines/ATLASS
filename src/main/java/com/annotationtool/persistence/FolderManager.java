package com.annotationtool.persistence;

import com.annotationtool.model.Category;
import com.annotationtool.model.ExcepcionDeAplicacion;
import com.annotationtool.model.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class that implements the persistence of the application.
 * @author adines
 */
public class FolderManager implements Persistence{

    private String outputPath="";
    
    @Override
    public void addCategory(Category category) {
        String sep=System.getProperty("file.separator");
        File folder=new File(outputPath+sep+category.getName());
        folder.mkdir();
    }

    @Override
    public void deleteCategory(Category category) {
        String sep=System.getProperty("file.separator");
        File folder=new File(outputPath+sep+category.getName());
        folder.delete();
    }

    @Override
    public void modifyCategory(Category category, String name) {
        String sep=System.getProperty("file.separator");
        File folder=new File(outputPath+sep+category.getName());
        File newfolder=new File(outputPath+sep+name);
        folder.renameTo(newfolder);
    }

    @Override
    public List<Category> getCategories() {
        File folder=new File(outputPath);
        String files[]=folder.list();
        List<Category> categories=new ArrayList<>();
        if(files!=null)
        {
            for(String f:files)
            {
                File file=new File(f);
                if(file.isDirectory())
                {
                    categories.add(new Category(file.getName()));
                }
            }
        }
        
        return categories;
    }

    @Override
    public void addImageToCategory(Image image, Category category) {
        File origen=new File(image.getPath());
        File destino=new File(outputPath+File.separator+category.getName()+File.separator+image.getName());
        origen.renameTo(destino);
    }

    @Override
    public void deleteImageCategory(Image image) {
        File fichero=new File(image.getPath());
        fichero.delete();
    }

    @Override
    public void modifyImageCategory(Image image, Category category) {
        File origen=new File(image.getPath());
        String path=image.getPath();
        path=path.replaceFirst(image.getCategory().getName(), category.getName());
        File destino=new File(path);
        origen.renameTo(destino);
    }

    @Override
    public List<Image> getImagesCategory(Category category) {
        File dir=new File(outputPath+File.separator+category.getName());
        File[]files=dir.listFiles();
        List<Image> images=new ArrayList<>();
        for(File f:files)
        {
            images.add(new Image(f.getName(), f.getAbsolutePath(), category));
        }
        return images;
    }

    @Override
    public List<Image> getUnassignedImages() {
        List<Image> images=new ArrayList<>();
        File dir=new File(outputPath+File.separator);
        File files[]=dir.listFiles();
        for(File f:files)
        {
            if(f.isFile())
            {
                images.add(new Image(f.getName(), f.getAbsolutePath()));
            }
        }
        return images;
    }

    @Override
    public List<Image> loadImages(String path) throws ExcepcionDeAplicacion{
        List<Image> images=new ArrayList<>();
        File dir=new File(path);
        File files[]=dir.listFiles();
        for(File f:files)
        {
            if(f.isFile())
            {
                try {
                    File file=new File(outputPath+File.separator+f.getName());
                    Files.copy(f.toPath(), file.toPath());
                    images.add(new Image(file.getName(), file.getAbsolutePath()));
                } catch (IOException ex) {
                    Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcepcionDeAplicacion(ex);
                }
                
            }else{
                String name=f.getName();
                File files2[]=f.listFiles();
                for(File f2:files2)
                {
                    try {
                    File file=new File(outputPath+File.separator+name+File.separator+f2.getName());
                    Files.copy(f2.toPath(), file.toPath());
                    images.add(new Image(file.getName(), file.getAbsolutePath(),new Category(name)));
                } catch (IOException ex) {
                    Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcepcionDeAplicacion(ex);
                }
                }
            }
        }
        return images;
    }

    @Override
    public List<Image> initializeDataset(String pathOrigin, String pathDest) throws ExcepcionDeAplicacion{
        this.outputPath=pathDest;
        
        return loadImages(pathOrigin);
    }
    
    
    @Override
    public void generateDataset() throws ExcepcionDeAplicacion
    {
        
        try {
            zipIt("algo");
            
            //¿Crear archivo Ipynb?
        } catch (IOException ex) {
            Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionDeAplicacion(ex);
        }
    }
    
    
    /**************AUXILIAR METHODS****************/
    private void zipIt(String zipFile)throws IOException{

     byte[] buffer = new byte[1024];
    	
    		
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
    		
    	List<String>files=new ArrayList<>();
        generateFileList(new File(outputPath), files);
    		
    	for(String file : files){
    			
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
               
        	FileInputStream in = 
                       new FileInputStream(outputPath + File.separator + file);
       	   
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
               
        	in.close();
    	}
    		
    	zos.closeEntry();
    	zos.close();
          
    	System.out.println("Done");

   }
    
    private void generateFileList(File node,List<String> fileList){

	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
		
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename),fileList);
		}
	}
 
    }
    
    private String generateZipEntry(String file){
    	return file.substring(outputPath.length()+1, file.length());
    }
    
}
