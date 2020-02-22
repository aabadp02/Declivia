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

import declivia.modelo.conexion.Conexion;
import java.sql.*;

public class SQLSentences 
{
    private Conexion conexion;
    private Connection cn;
    private Statement stm;
    private ResultSet rs;

    public SQLSentences()
    {
        conexion = new Conexion();
        cn = null;
        stm = null;
        rs = null;
    }

    public ResultSet getData(String query)
    {
        try
        {
            cn = conexion.conectar();
            stm = cn.createStatement();

            rs = stm.executeQuery(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

            return rs;
    }

    public void insertData(String query)
    {
        try
        {
            cn = conexion.conectar();
            stm = cn.createStatement();

            stm.executeUpdate(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}