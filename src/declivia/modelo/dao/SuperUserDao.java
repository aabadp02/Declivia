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

import declivia.modelo.vo.StandardUserVo;
import declivia.modelo.vo.SuperUserVo;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Alex
 */
public class SuperUserDao 
{
    private SuperUserVo superUserVo;
    private SQLSentences sentences;
    private final String DATABASENAME = "`declivia1.0`.";
    
    public SuperUserDao(SuperUserVo suv)
    {
        this.superUserVo = suv;
        sentences = new SQLSentences();
    }
    
    public void loadUsers()
    {
        ResultSet rs = null;
        
        String query = "SELECT standardusername, email FROM " + DATABASENAME + "standarduser";
        
        rs = sentences.getData(query);
        
        try
        {
            while(rs.next())
            {
                StandardUserVo newUser = new StandardUserVo(rs.getString(1));
                newUser.setEmail(rs.getString(2));
                superUserVo.users.add(newUser);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public int changePassword(String newPassword, String password)
    {
        ResultSet rs = null;
        
        String newHash = generateHash(newPassword);
        String hash = generateHash(password);
        
        String query = "SELECT passwd FROM " + DATABASENAME + "superuser WHERE superusername = '" + superUserVo.getName() + "'";
        
        rs = sentences.getData(query);
        
        try
        {
            while(rs.next())
            {
                if(!hash.equals(rs.getString(1)))
                {
                    return 0; //Salió mal
                }
                else
                {
                    rs = null;
                    query = "UPDATE `declivia1.0`.superuser SET passwd='" + newHash + "' WHERE superusername='" + superUserVo.getName() + "'";
                    
                    sentences.insertData(query);
                    
                    return 1;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return 1;
    }
    
    public void deleteUser(String name)
    {
        String query = "DELETE FROM " + DATABASENAME + "standarduser WHERE standardusername = '" + name + "'"; 
        
        sentences.insertData(query);
    }
    
    private String generateHash(String passwordToHash)
    {
        String generatedPassword = null;
        
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
                    
        return generatedPassword;
    }
    
    public void backup() throws Exception
    {
        Process p = Runtime.getRuntime().exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u Alex -pK0U7r@z3NIamysql declivia1.0");
	
        InputStream is = p.getInputStream();

        FileOutputStream fos = new FileOutputStream("backup.sql");

        byte [] buffer = new byte[1000];

        int leido = is.read(buffer);

        while(leido > 0) {

                fos.write(buffer, 0, leido);
                leido = is.read(buffer);

        }
		
        int processComplete1 = p.waitFor();

       if(processComplete1 == 0) 
       {
            System.out.println("Correcto");
       }
       else 
       {
            System.out.println("Algo ha fallado");
       }


       fos.close();
    }
}
