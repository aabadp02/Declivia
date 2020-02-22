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

import declivia.modelo.vo.FileDeclivia;
import declivia.modelo.vo.SubjectVo;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDao 
{
    private SubjectVo subject;
    private SQLSentences sentence;
    private final String DATABASENAME = "`declivia1.0`.";
    private int subjectLoadCounter;
    
    public SubjectDao(SubjectVo subject)
    {
        this.subject = subject;
        sentence = new SQLSentences();
        subjectLoadCounter = 0;
    }
    
    public void loadFiles()
    {
        if(subjectLoadCounter == 0)
        {
            ResultSet rs = null;

            String query = "SELECT namefile, uri, fileid FROM " + DATABASENAME + "files WHERE subjectid = '" + subject.getSubjectID() + "'";

            rs = sentence.getData(query);

            try
            {
                while(rs.next())
                {
                    FileDeclivia file = new FileDeclivia(rs.getString(1), new File(rs.getString(2)));
                    file.setFileID(rs.getString(3));
                    System.out.println(rs.getString(1));
                    subject.addFile(file);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }

            subjectLoadCounter++;
        }
    }
    
    public void crearArchivo(FileDeclivia file)
    {
        String path = file.getPath();
        
        String prueba = path.replace("\\", "\\\\");
        
        String query = "INSERT INTO " + DATABASENAME + "files(namefile, description, uri, subjectid) VALUES('" + file.getName() + "', '" + file.getDescription() + "', '" + prueba + "', '" + subject.getSubjectID() + "')";
        
        sentence.insertData(query);
        
        ResultSet rs = null;
        
        query = "SELECT fileid FROM " + DATABASENAME + "files WHERE subjectid = '" + subject.getSubjectID() + "'";
        
        rs = sentence.getData(query);

        try
        {
            while(rs.next())
            {
                file.setFileID(rs.getString(1));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void eliminarArchivo(int id)
    {
        String query = "DELETE FROM " + DATABASENAME + "files WHERE fileid = " + id; 
        
        sentence.insertData(query);
    }
    
    public void updateSubjectAccesses()
    {
        String query = "UPDATE `declivia1.0`.subjects SET subjectsaccesses='" + subject.getSubjectAccesses() + "' WHERE subjectid='" + subject.getSubjectID() + "'";
        
        sentence.insertData(query);
         
    }
    
    public void updateSubjectFileCounter()
    {
        String query = "UPDATE `declivia1.0`.subjects SET filecounter='" + subject.getFileCounter() + "' WHERE subjectid='" + subject.getSubjectID() + "'";
        
        sentence.insertData(query);
         
    }
    
    
}


//#007Fc350