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
import declivia.modelo.vo.StandardUserVo;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSource;


public class StandardUserDao 
{
    private SQLSentences sentence;
    private final String DATABASENAME = "`declivia1.0`.";
    private StandardUserVo mainUser;

/***********************************************************************************************************************************
* Constructor de la clase LoginController
* 1. Inicializará su objeto de tipo SQLSentences para contactar con la base de datos
* 
************************************************************************************************************************************/
    public StandardUserDao()
    {
        sentence = new SQLSentences();
    }
    
    public void setStandardUserVo(StandardUserVo us)
    {
        this.mainUser = us;
    }

/***********************************************************************************************************************************
* Función CreateUser de la clase LoginController
* 1. Creará una query para insertar un nuevo usuario haciendo uso de los parámetros que se le pasan como argumento:
*   -user
*   -password
* 
* 2. Llamará a su objeto de tipoSQLSentences para que ejecute la query
* 
************************************************************************************************************************************/
    public int createUser(String user, String password, String email)
    {
        ResultSet rs = null;
        
        String hash = generateHash(password);
        
        String query = "SELECT StandardUserName FROM " + DATABASENAME + "standarduser WHERE StandardUserName = '" + user + "'";
        
        rs = sentence.getData(query);
        
        int userExists = 0; //Con este contador comprobaremos si ya existe un usario con ese nombre
        
        try
        {
            while(rs.next())
            {
                userExists++; //Si cuando buscamos en la tabla, sale algún resultado, el usuario existirá
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        if(userExists != 0) //Si el usuario existe
        {
            return 0;
        }
        else
        {
            query = "INSERT INTO " + DATABASENAME +"standarduser(StandardUserName, Passwd, Email, AdminMode, ApplicationAccesses) VALUES ('" + user + "', '" + hash + "', '" + email + "', '1', '0')";

            sentence.insertData(query);
            
            return 1; //Se ha creado correctamente el usuario
        }
    }

/***********************************************************************************************************************************
* Función ComprarDatos de la clase LoginController
* 1. Creará una query para sacar un la contraseña de un usuario haciendo uso del parámetro que se le pasan como argumento:
*   -user
* 
* 2. Llamará a su objeto de tipoSQLSentences para que ejecute la query
* 
* 3. El resultado de la query se guardará en un objeto de tipo ResultSet (rs) 
* 
* 4. Mientras que dicho objeto siga teniendo datos, iremos sacándolos. Comprobaremos si la contraseña extraída de la base de datos
* coincide con la contraseña pasada por parámetros:
*   -password
* 
* 5. Si los datos coinciden y es un usuario normal devuelve 1. Si coinciden y es supeusuario devuelve 2, si no coinciden devuelve 0.
* 
************************************************************************************************************************************/
    public int comprobarDatos(StandardUserVo user, String password)
    {
        String username = user.getName();
        ResultSet rs = null;
        
        String hash = generateHash(password);

        String query = "SELECT passwd FROM " + DATABASENAME + "standarduser WHERE StandardUserName = '" + username + "'";

        rs = sentence.getData(query);
        
        int userExists = 0;
        
        try
        {
            while(rs.next())
            {
                userExists++; //Si cuando buscamos en la tabla, sale algún resultado, el usuario existirá
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        if(userExists == 0) //Si no encuentra el nombre de usuario, comprobamos si es un superusuario
        {
            query = "SELECT passwd FROM " + DATABASENAME + "superuser WHERE SuperUserName = '" + username + "'";
            
            rs = sentence.getData(query);
          
            try
            {
                while(rs.next())
                {
                    if(rs.getString(1).equals(hash))
                    {
                        System.out.println("Usuario y contraseña correctos");
                        
                        
                        return 2; //Indicamos que el usuario introducido es un superusuario
                    }

                    System.out.println("Usuario y contraseña INCORRECTOS");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            
            return 0; //El usuario o la contraseña son incorrectos

        }
        else
        {
            //Comprobamos si el usuario y la contraseña existen y son correctos (dentro de los usuarios normales
            rs = null;
            
            query = "SELECT passwd, email, adminmode, applicationaccesses FROM " + DATABASENAME + "standarduser WHERE StandardUserName = '" + username + "'";
            
            rs = sentence.getData(query);
            
            try
            {
                while(rs.next())
                {
                    if(rs.getString(1).equals(hash))
                    {
                        System.out.println("Usuario y contraseña correctos");
                        
                        //Cargamos en el usuario el resto de datos pertinentes
                        user.setEmail(rs.getString(2));
                        user.setAdminMode(Integer.parseInt(rs.getString(3)));
                        user.setApplicationAccesses(Integer.parseInt(rs.getString(4)));
                                              
                        mainUser = user; //Inicializamos nuestro objeto de tipo StandardUserVo
                        
                        return 1;
                    }

                    System.out.println("Usuario y contraseña INCORRECTOS");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            
            return 0;
        }       
    }
    
    public void sendEmail(StandardUserVo mandarCorreo, int code)
    {
        ResultSet rs = null;

        String query = "SELECT email FROM " + DATABASENAME + "standarduser WHERE StandardUserName = '" + mandarCorreo.getName() + "'";

        rs = sentence.getData(query);
        String destinatario = "";
        
        try
        {
            while(rs.next())
            {
                destinatario = rs.getString(1);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        if(!destinatario.equals(""))
        {    
        
            String remitente = "decliviaoficial@gmail.com";
            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
            props.put("mail.smtp.user", remitente);
            props.put("mail.smtp.clave", "K0U7r@z3NIad3kL1V,a");    //La clave de la cuenta
            props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
            props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
            props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(remitente));
                message.setText(Integer.toString(code));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
                message.setSubject("Código para cambiar la contraseña");
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", remitente, "K0U7r@z3NIad3kL1V,a");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }
            catch (MessagingException me) {
                me.printStackTrace();   //Si se produce un error
            }
        }
    }
    
    /*public void changePassword(String user, String newPassword)
    {
        String query = "UPDATE `declivia1.0`.StandardUser SET passwd='" + newPassword +"' WHERE standardusername='" + user + "'";
        
        sentence.insertData(query);
    }*/
    
    public int changeName(String newName, String passwd)
    {
        ResultSet rs = null;
        
        String hash = generateHash(passwd);
        
        String query = "SELECT passwd FROM " + DATABASENAME + "standarduser WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
        
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
                    query = "UPDATE `declivia1.0`.StandardUser SET StandardUserName='" + newName + "' WHERE standardusername='" + mainUser.getName() + "'";
                    
                    sentence.insertData(query);
                    
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
    
    public int changeEmail(String newEmail, String passwd)
    {
        ResultSet rs = null;
        
        String hash = generateHash(passwd);
        
        String query = "SELECT passwd FROM " + DATABASENAME + "standarduser WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
        
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
                    query = "UPDATE `declivia1.0`.StandardUser SET email='" + newEmail + "' WHERE standardusername='" + mainUser.getName() + "'";
                    
                    sentence.insertData(query);
                    
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
    
    public int changePassword(String newPasswd, String passwd)
    {
        ResultSet rs = null;
        
        String newHash = generateHash(newPasswd);
        String hash = generateHash(passwd);
        
        String query = "SELECT passwd FROM " + DATABASENAME + "standarduser WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
        
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
                    query = "UPDATE `declivia1.0`.StandardUser SET passwd='" + newHash + "' WHERE standardusername='" + mainUser.getName() + "'";
                    
                    sentence.insertData(query);
                    
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
    
    public void changePasswordLogin(String username, String passwd)
    {
        String hash = generateHash(passwd);
        
        String query = "UPDATE `declivia1.0`.StandardUser SET passwd='" + hash + "' WHERE standardusername='" + username + "'";

        sentence.insertData(query);
    }
    
    public void loadCourses()
    {
        ResultSet rs = null;
        
        String query = "SELECT coursename, courseid, subjectcounter, dscription FROM " + DATABASENAME + "courses WHERE username = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
           
        try
        {
            while(rs.next())
            {
                CourseVo curso = new CourseVo(rs.getString(1));
                curso.setCourseID(Integer.parseInt(rs.getString(2)));
                curso.setNumSubjects(Integer.parseInt(rs.getString(3)));
                curso.setDescription(rs.getString(4));
                
                mainUser.addCourse(curso);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void createCourse(CourseVo newCourse)
    {
        String query = "INSERT INTO " + DATABASENAME + "courses(coursename, dscription, username) VALUES('" + newCourse.getName() + "', '" + newCourse.getDescription() + "', '" + mainUser.getName() + "')";
        
        sentence.insertData(query);
        
        ResultSet rs = null;
        
        query = "SELECT courseid FROM " + DATABASENAME + "courses WHERE username = '" + mainUser.getName() + "' AND coursename = '" + newCourse.getName() + "'";
        
        rs = sentence.getData(query);
           
        try
        {
            while(rs.next())
            {
                System.out.println("EL ID ES : " + rs.getString(1));
                newCourse.setCourseID(Integer.parseInt(rs.getString(1)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void deleteCourse(int id)
    {
        String query = "DELETE FROM " + DATABASENAME + "courses WHERE courseid = " + id; 
        
        sentence.insertData(query);
        
    }
    
    public void loadStatistics()
    {
        ResultSet rs = null;
        
        String query = "SELECT valor FROM " + DATABASENAME + "diagram WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
           
        try
        {
            while(rs.next())
            {
                int value = Integer.parseInt(rs.getString(1));
                mainUser.addStatistic(value);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void insertNewStatistic()
    {
        String query = "INSERT INTO " + DATABASENAME + "diagram(standardusername, valor) VALUES('" + mainUser.getName() + "', '0')";
        
        sentence.insertData(query);
        
        ResultSet rs = null;
        
        query = "SELECT id FROM " + DATABASENAME + "diagram WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
           
        try
        {
            while(rs.next())
            {
                mainUser.setClickSesion(Integer.parseInt(rs.getString(1)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateStatistics(int num)
    {
        String query = "UPDATE `declivia1.0`.diagram SET valor='" + num + "' WHERE id='" + mainUser.getClickSesion() + "'";
            
        sentence.insertData(query);
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
    
    public int backup(String passwd) throws Exception
    {
        
        ResultSet rs = null;
        
        String hash = generateHash(passwd);
        
        String query = "SELECT passwd FROM " + DATABASENAME + "standarduser WHERE standardusername = '" + mainUser.getName() + "'";
        
        rs = sentence.getData(query);
        
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
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return 1;
        
    }

}
