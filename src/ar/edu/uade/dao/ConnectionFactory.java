package ar.edu.uade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory
{
    private static ConnectionFactory instancia;
   
    private ConnectionFactory(){
    	
    	try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error de configuracion");
			e.printStackTrace();
		}
    }
    
    public static ConnectionFactory getInstancia()
    {
    	if(instancia == null)
    		instancia = new ConnectionFactory();
        return instancia;
    }
    
    public Connection getConexion()
    {  
        String userName = "AI_2974_09";
        String password = "AI_2974_09";
        String url = "jdbc:sqlserver://" + "BROKER-PC\\SQL2014";
        Connection con = null;
		try {
			con = DriverManager.getConnection (url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return con;
    }
    
    public void closeCon()
    {  
        
    }
}
