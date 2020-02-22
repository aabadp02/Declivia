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

package declivia.modelo.dao;

import declivia.modelo.vo.CourseVo;
import declivia.modelo.vo.SubjectVo;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao 
{
    private CourseVo course;
    private SQLSentences sentence;
    private int courseLoadCounter;
    private final String DATABASENAME = "`declivia1.0`.";
    
    public CourseDao(CourseVo course)
    {
        this.course = course;
        sentence = new SQLSentences();
        courseLoadCounter = 0;
    }
    
    public void loadSubjects()
    {
        if(courseLoadCounter == 0)
        {
            ResultSet rs = null;

            String query = "SELECT subjectname, subjectid, favouritesubject, subjectcredits, teacheremail, Dificulty, subjectsaccesses, filecounter FROM " + DATABASENAME + "subjects WHERE courseid = '" + course.getCourseID() + "'";

            rs = sentence.getData(query);

            try
            {
                while(rs.next())
                {
                    SubjectVo subject = new SubjectVo(rs.getString(1), Integer.parseInt(rs.getString(3)));
                    subject.setSubjectId(Integer.parseInt(rs.getString(2)));
                    
                    subject.setSubjectCredits(rs.getString(4));
                    
                    if(!rs.getString(5).equals(""))
                    {
                        subject.setCorreProfesor(rs.getString(5));
                    }
                    
                    if(rs.getString(6).equals(""))
                    {
                        subject.setDifficulty(5);
                    }
                    
                    subject.setDifficulty(Integer.parseInt(rs.getString(6)));
                    
                    subject.setSubjectAccesses(Integer.parseInt(rs.getString(7)));
                    
                    subject.setFileCounter(Integer.parseInt(rs.getString(8)));
                    
                    System.out.println(rs.getString(1));
                    course.addSubject(subject);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
                
            courseLoadCounter++;
        }
    }
    
    public void crearAsignatura(SubjectVo subject)
    {
        String query = "INSERT INTO " + DATABASENAME + "subjects(subjectname, favouritesubject, courseid, teacheremail, subjectcredits, Dificulty) VALUES('" + subject.getName() + "', '" + subject.getFavourite() + "', '" + course.getCourseID() + "', '" + subject.getCorreoProfesor() + "', '" + subject.getNumCredits() + "', '" + subject.getDifficulty() + "')";
        
        sentence.insertData(query);
        
        query = "SELECT subjectid FROM " + DATABASENAME + "subjects WHERE courseid = '" + course.getCourseID() + "'";

        ResultSet rs = sentence.getData(query);

        try
        {
            while(rs.next()) //Siempre cogeremos el valor del id de la última asignatura añadida
            {
                subject.setSubjectId(Integer.parseInt(rs.getString(1)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void eliminarAsignatura(int id)
    {
        String query = "DELETE FROM " + DATABASENAME + "subjects WHERE subjectid = " + id; 
        
        sentence.insertData(query);
    }
    
}

