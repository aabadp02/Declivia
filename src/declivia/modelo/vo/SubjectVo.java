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

import declivia.modelo.dao.SubjectDao;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class SubjectVo extends Label
{
    private String subjectName;
    private int subjectLoadCounter; //Es un contador para cargar los datos de la base de datos sólo la primera vez 
    public boolean alreadyAdded;
    public boolean alreadyAdded_control;
    public ArrayList<FileDeclivia> files;
    private Image image;
    private int id;
    private int numCredits;
    private String correoProfesor;
    private int favourite; //0 Si no es favorita y 1 si sí que lo es
    private SubjectDao subjectDao;
    private int difficulty;  //0 la mínima 10 la máxima
    private int subjectAccesses;
    private int fileCounter;
    
    public SubjectVo(String name, int favourite)
    {
        image = new Image("Imagenes/asignatura.png");
        
        subjectName = name;
        numCredits = 0;  
        fileCounter = 0;//Valor por defecto
        correoProfesor = "example@example.com";
        subjectLoadCounter = 0;
        files = new ArrayList<FileDeclivia>();
        alreadyAdded = false;
        alreadyAdded_control = false;
        subjectDao = new SubjectDao(this);
        difficulty = 0;
        subjectAccesses = 0;
        
        this.favourite = favourite;
        
        this.setText(subjectName); 
        setVisible(true);
        ImageView icono = new ImageView(image);
        icono.setScaleX(0.25);
        icono.setScaleY(0.25);
        this.setGraphic(icono);
        this.setGraphicTextGap(-30);
        this.setCursor(Cursor.HAND);
    }
    
    public String getName()
    {
        return subjectName;
    }
    
    public String getCorreoProfesor()
    {
        return correoProfesor;
    }
    
    public int getNumCredits()
    {
        return numCredits;
    }
    
    public int getFavourite()
    {
        return favourite;
    }
    
    public void setFavourite(int fav)
    {
        this.favourite = fav;
    }
    
    public int getSubjectID()
    {
        return id;
    }
    
    public void setSubjectId(int id)
    {
        this.id = id;
    }
    
    public void incrementCourseLoadCounter()
    {
        subjectLoadCounter++;
    }
    
    public void addFile(FileDeclivia file)
    {
        files.add(file);
    }
    
    public int getCourseLoadCounter()
    {
        return subjectLoadCounter;
    }
    
    public void loadFiles()
    {
        subjectDao.loadFiles();
    }   
    
    public void setSubjectCredits(String num)
    {
        this.numCredits = Integer.parseInt(num);
    }
    
    public void setCorreProfesor(String correo)
    {
        correoProfesor = correo;
    }
    
    public void crearArchivo(String nombre, File file, String descripcion)
    {
        FileDeclivia newFile = new FileDeclivia(nombre, file);
        
        if(!descripcion.equals(""))
        {
            newFile.setDescription(descripcion);
        }
        
        addFile(newFile);
        
        subjectDao.crearArchivo(newFile);
        
        updateFileCounter();
    }
    
    public void eliminarArchivo(FileDeclivia file)
    {
        files.remove(file);
        
        subjectDao.eliminarArchivo(file.getFileID());
    }
    
    public int getDifficulty()
    {
        return difficulty;
    }
    
    public void setDifficulty(int d)
    {
        this.difficulty = d;
    }
    
    public void setSubjectAccesses(int n)
    {
        this.subjectAccesses = n;
    }
    
    public int getSubjectAccesses()
    {
        return subjectAccesses;
    }
    
    public void updateSubjectAccesses()
    {
        subjectAccesses++;
        
        subjectDao.updateSubjectAccesses();
    }
    
    public int getFileCounter()
    {
        return fileCounter;
    }
    
    public void setFileCounter(int f)
    {
        this.fileCounter =  f;
    }
    
    public void updateFileCounter()
    {
        fileCounter ++;
        
        subjectDao.updateSubjectFileCounter();
    }
}
