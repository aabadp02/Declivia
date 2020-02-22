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

import declivia.modelo.dao.SuperUserDao;
import java.util.ArrayList;

public class SuperUserVo 
{
    private String name;
    private SuperUserDao superUserDao;
    public ArrayList<StandardUserVo> users;
    
    public SuperUserVo(String name)
    {
        this.name = name;
        superUserDao = new SuperUserDao(this);
        users = new ArrayList<StandardUserVo>();
    }
    
    public void loadUsers()
    {
        superUserDao.loadUsers();
    }
    
    public void addUser(StandardUserVo user)
    {
        users.add(user);
    }
    
    public String getName()
    {
        return name;
    }
    
    public int changePassword(String newPassword, String password)
    {
        return superUserDao.changePassword(newPassword, password);
    }
    
    public void backup() throws Exception
    {
        superUserDao.backup();
    }
    
    public void deleteUser(StandardUserVo us)
    {
        users.remove(us);
        
        superUserDao.deleteUser(us.getName());
    }
}
