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

package declivia.modelo.conexion;

import declivia.modelo.dao.SQLSentences;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;


public class back {

    private SQLSentences sentence;    
   
    public back()
    {
    	System.out.println("constructor");
        sentence = new SQLSentences();
    }
    
    
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";

    // Database credentials
    static final String USER = "Alex";
    static final String PASS = "K0U7r@z3NIamysql";
    
    public void createDatabase(){
    	
	    Connection conn = null;
	    Statement stmt = null;
         
	    try{
		       Class.forName("com.mysql.jdbc.Driver");
		
		       System.out.println("Connecting to database...");
		       conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		       System.out.println("Creating database...");
		       stmt = conn.createStatement();
		       
		       String sql = "CREATE DATABASE if not exists `declivia1.0`";
		       String sql2 = "use `declivia1.0`";
		       stmt.executeUpdate(sql);
		       stmt.executeUpdate(sql2);
		       
		       ResultSet rs = null;
		       String query =  "CREATE TABLE if not exists SUPERUSER(" + 
						       "SuperUserName varchar (30) not null unique, " + 
						       "PRIMARY KEY (SuperUserName), " + 
						       "Passwd varchar (100) not null " + 
						       ");\n";
						                 
				String query2 ="CREATE TABLE if not exists STANDARDUSER(" + 
						       "StandardUserName varchar (30) not null unique, " + 
						       "PRIMARY KEY (StandardUserName), " + 
						       "Passwd varchar (100) not null , " + 
						       "Email varchar (1500) not null , " + 
						       "AdminMode int default 1, " + 
						       "ApplicationAccesses int default 0, " + 
						       "numberStatistics int " + 
						       ");\n";
						       
					String query3 ="CREATE TABLE if not exists COURSES(" + 
						       "CourseID int not null auto_increment, " + 
						       "CourseName varchar (30), " + 
						       "PRIMARY KEY(CourseID), " + 
						       "/*subjectCounter int, no hace falta porque ya tenemos lo de contar las palabras iguales*/ " + 
						       "UserName varchar(30), " + 
						       "Dscription varchar(200), " + 
						       "FOREIGN KEY (UserName) REFERENCES STANDARDUSER(StandardUserName) ON UPDATE CASCADE ON DELETE CASCADE,  " + 
						       "SubjectCounter int default 0 , " + 
						       "FileCounter int default 0 , " + 
						       "CoursesAccesses int default 0 " + 
						       ");\n"; 
						        
					String query4 ="CREATE TABLE  if not exists SUBJECTS( " + 
						       "SubjectID int not null auto_increment, " + 
						       "SubjectName varchar (30) , " + 
						       "PRIMARY KEY (SubjectID), " + 
						       "FavouriteSubject int, /*1=true, 0=false*/ " + 
						       "/*fileCounter int, no hace falta porque ya tenemos lo de contar las palabras iguales*/ " + 
						       "CourseID int not null, " + 
						       "/*key CourseID (CourseID),*/ " + 
						       "TeacherEmail varchar(50), " + 
						       "FOREIGN KEY (CourseID) REFERENCES COURSES(CourseID) ON UPDATE CASCADE  ON DELETE CASCADE, " + 
						       "SubjectCredits int default 6, " + 
						       "SubjectsAccesses int default 0, " + 
						       "FileCounter int default 0,  " + 
						       "Dificulty int DEFAULT 5 " + 
						       ");\n" ;
						       
					String query5 ="CREATE TABLE if not exists FILES( " + 
						       "FileID int not null auto_increment, " + 
						       "NameFile varchar (30) , " + 
						       "PRIMARY KEY (FileID), " + 
						       "URI varchar(1000) not null, " + 
						       "SubjectID int, " + 
						       "/*key SubjectIDNew (SubjectID),*/ " + 
						       "Description varchar(200), " + 
						       "FOREIGN KEY (SubjectID) REFERENCES SUBJECTS(SubjectID) ON UPDATE CASCADE ON DELETE CASCADE " + 
						       ");\n"; 
						       
					String query6 ="CREATE TABLE if not exists DIAGRAM ( " + 
						       "ID int auto_increment, " + 
						       "PRIMARY KEY (ID), " + 
						       "StandardUserName varchar (30), " + 
						       "FOREIGN KEY (StandardUserName) REFERENCES STANDARDUSER(StandardUserName) ON UPDATE CASCADE ON DELETE CASCADE, " + 
						       "valor int " + 
						       ");\n";
						        
					String query7 =	"INSERT ignore INTO SUPERUSER(SuperUserName, Passwd)  " + 
						       		"VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3');";
		       
		       System.out.println("primero: "+sentence);
		       sentence.insertData(query);
		       sentence.insertData(query2);
		       sentence.insertData(query3);
		       sentence.insertData(query4);
		       sentence.insertData(query5);
		       sentence.insertData(query6);
		       sentence.insertData(query7);
		       System.out.println("segundo: " + sentence);

		       
	    } catch(SQLException se){
		       se.printStackTrace();
	    } catch(Exception e){
		       e.printStackTrace();
	    } finally{
		       try{
		          if(stmt!=null)
		             stmt.close();
		       }catch(SQLException se2){
		       }
		       try{
		          if(conn!=null)
		             conn.close();
		       }catch(SQLException se){
		          se.printStackTrace();
		       }
	    }
    }
    
}


	
