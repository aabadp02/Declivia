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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion 
{
    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/declivia1.0";
    private static final String USER = "Alex";
    private static final String PASSWORD = "K0U7r@z3NIamysql";

    static
    {
        try
        {
            Class.forName(CONTROLADOR);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();
        }
    }

    public Connection conectar()
    {
        Connection cn = null;

        try
        {
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(SQLException e)
        {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }

        return cn;
    }
}
