/**
 ***********************************************************************
 * All rights reserved © Declivia
 * Alejandro Abad Peláez
 * Javier Darío Castrillo Rodríguez
 * Naím González Sánchez
 * 
 *
 *
 * Declivia v1.0.0
 ***********************************************************************
 */

package declivia.modelo.vo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Alex
 */
public class FileDeclivia extends Label
{
    private int fileLoadCounter;
    private String name;
    private String path;
    private Image image;
    public boolean alreadyAdded;
    private String descripcion;
    private File file;
    private int fileId;
    
    public FileDeclivia(String name, File file)
    {
        image = new Image("Imagenes/archivo.png");
        
        descripcion = "Descripción por defecto";
        
        this.name = name;
        fileLoadCounter = 0;
        
        this.file = file;
        
        this.path = file.getAbsolutePath();
        
        this.setText(name);
        setVisible(true);
        ImageView icono = new ImageView(image);
        icono.setScaleX(0.25);
        icono.setScaleY(0.25);
        this.setGraphic(icono);
        this.setGraphicTextGap(-30);
        this.setCursor(Cursor.HAND);
        alreadyAdded = false;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setFileID(String id)
    {
        fileId = Integer.parseInt(id);
    }
    
    public int getFileID()
    {
        return fileId;
    }
    
    public void loadData()
    {
        if(fileLoadCounter == 0)
        {
            
            fileLoadCounter++;
        }
    }
    
    public void open()
    {
        try
        {
            Desktop.getDesktop().open(file);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void setDescription(String descripcion)
    {
        this.descripcion = descripcion;
    }
    
    public String getDescription()
    {
        return this.descripcion;
    }
    
    public String getPath()
    {
        return this.path;
    }
}
