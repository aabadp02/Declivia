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

import declivia.modelo.dao.CourseDao;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CourseVo extends Label
{
    private String courseName;
    private int courseLoadCounter; //Es un contador para cargar los datos de la base de datos sólo la primera vez
    public boolean alreadyAdded; //Una vez añadido al pane, no lo volvemos a añadir
    private int id;
    public ArrayList<SubjectVo> subjects;
    private Image image;
    private int numSubjects;
    private String description;
    private CourseDao courseDao;
    
    public CourseVo(String name)
    {
        image = new Image("Imagenes/curso.png"); //Le damos valor al icono que acompaña al curso
        
        //Inicializamos los atributos más "importantes"
        courseName = name;
        courseLoadCounter = 0;
        subjects = new ArrayList<SubjectVo>();
        alreadyAdded = false;
        
        description="";
        //Le damos un texto y una imagen, lo escalamos y lo alineamos correctamente
        this.setText(courseName);
        setVisible(true);
        ImageView icono = new ImageView(image);
        icono.setScaleX(0.25);
        icono.setScaleY(0.25);
        this.setGraphic(icono);
        this.setGraphicTextGap(-30);
        this.setCursor(Cursor.HAND);    
        courseDao = new CourseDao(this);
    }
    
    public String getName()
    {
        return courseName;
    }
    
    public void setCourseID(int id) //Lo llamamos así y no "setId" a secas porque ya debe haber un método intrínseco de java con ese nombre y se peta
    {
        this.id = id;
    }
    
    public int getCourseID()
    {
        return id;
    }
    
    public int getCourseLoadCounter()
    {
        return courseLoadCounter;
    }
    
    public void addSubject(SubjectVo subject)
    {
        subjects.add(subject);
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void loadSubjects()
    {
        courseDao.loadSubjects();
    }
    
    public int getNumSubjects()
    {
        return numSubjects;
    }
    
    public void setNumSubjects(int num)
    {
        numSubjects = num;
    }
    
    public void crearAsignatura(String nombre, String correoProfesor, String anotaciones, String creditos, int favourite, int dif)
    {
        SubjectVo newSubject = new SubjectVo(nombre, favourite);
                
        if(!correoProfesor.equals(""))
        {
            newSubject.setCorreProfesor(correoProfesor);
        }
                
        if(!creditos.equals(""))
        {
            System.out.println("Entro");
            newSubject.setSubjectCredits(creditos);
        }
        
        newSubject.setDifficulty(dif);
        
        addSubject(newSubject);
        
        courseDao.crearAsignatura(newSubject);
    }
    
    public void deleteSubject(SubjectVo subject)
    {
        subjects.remove(subject);
        
        courseDao.eliminarAsignatura(subject.getSubjectID());
    }
    
    public SubjectVo calculateMaximumPoints() {

	int numAsignaturas = subjects.size();
	int max = -100;
	
	SubjectVo SubjectMaxima = subjects.get(0);
        
	for(int i = 0; i < numAsignaturas; i++)
        {
            SubjectVo s1 = subjects.get(i);
            int dificulty = s1.getDifficulty();
            int clicksOnSubject = s1.getSubjectAccesses();
            int points = 5 * dificulty - clicksOnSubject;
            
            if(points > max)
            {
                    max = points;
                    SubjectMaxima = s1;
            }
	}

	return SubjectMaxima;
    }
    
    public int[] calculateAllPoints() 
    {
	int arrayPuntuaciones[] = new int[subjects.size()];
	
	SubjectVo SubjectMaxima = subjects.get(0);
        
	for(int i = 0; i < subjects.size(); i++)
        {
		SubjectVo s1 = subjects.get(i);
		int dificulty = s1.getDifficulty();
		int clicksOnSubject = s1.getSubjectAccesses();
		int points = 5 * dificulty - clicksOnSubject;
                
		arrayPuntuaciones[i] = points;
	}

	return arrayPuntuaciones;
    }
    
}
