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

import declivia.modelo.dao.StandardUserDao;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Alex
 */
public class StandardUserVo extends Label
{
    private String username;
    private String email;
    int adminMode;
    int applicationAccesses;
    public ArrayList<CourseVo> courses;
    public ArrayList<Integer> statistics;
    private StandardUserDao userDao;
    private int numClicks;
    private int clickSesion;
    private Image image;
    public boolean alreadyAdded;
    
    public StandardUserVo(String name)
    {
        image = new Image("Imagenes/account.png");
        
        ImageView icono = new ImageView(image);
        icono.setScaleX(0.25);
        icono.setScaleY(0.25);
        this.setGraphic(icono);
        this.setGraphicTextGap(-30);
        this.setCursor(Cursor.HAND);
        this.setText(name);
        
        userDao = new StandardUserDao();
        userDao.setStandardUserVo(this);
        
        alreadyAdded = false;
        
        adminMode = 1;
        numClicks = 0;
        applicationAccesses = 0;
        username = name;
        email = "";
        courses = new ArrayList<CourseVo>();
        statistics = new ArrayList<Integer>();
    }
    
    public void addCourse(CourseVo course)
    {
        courses.add(course);
    }
    
    public void addStatistic(int value)
    {
        statistics.add(value);
    }
    
    public void loadCourses()
    {
        userDao.loadCourses();     
    }
    
    public String getName()
    {
        return username;
    }
    
    public void setStandardUserDao(StandardUserDao userDao)
    {
        this.userDao = userDao;
    }
    
    public void setAdminMode(int mode)
    {
        adminMode = mode;
    }
    
    public void setApplicationAccesses(int num)
    {
        applicationAccesses = num;
    }
       
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public int changeName(String newName, String passwd)
    {
        return userDao.changeName(newName, passwd);
    }
    
    public int changeEmail(String newEmail, String passwd)
    {
        return userDao.changeEmail(newEmail, passwd);
    }
    
    public int changePassword(String newPasswd, String passwd)
    {
        return userDao.changePassword(newPasswd, passwd);
    }
    
    public void changePasswordLogin(String username, String newPassword)
    {
        userDao.changePasswordLogin(username, newPassword);
    }
    
    public void createCourse(String courseName, String description)
    {
        CourseVo newCourse = new CourseVo(courseName);
        newCourse.setDescription(description);
        addCourse(newCourse);
        
        userDao.createCourse(newCourse);
    }
    
    public void deleteCourse(CourseVo course)
    {
        courses.remove(course);
        
        userDao.deleteCourse(course.getCourseID());
    }
    
    public void loadStatistics()
    {
        userDao.loadStatistics();
    }
    
    public void insertNewStatistic()
    {
        addStatistic(numClicks);
        userDao.insertNewStatistic();
    }
    
    public void addClick()
    {
        numClicks ++;
        
        statistics.set(statistics.size() - 1, numClicks);
        
        userDao.updateStatistics(numClicks);
    }
    
    public void setClickSesion(int id)
    {
        this.clickSesion = id;
    }
    
    public int getClickSesion()
    {
        return clickSesion;
    }
    
    public void backup(String passwd) throws Exception
    {
        userDao.backup(passwd);
    }
}
