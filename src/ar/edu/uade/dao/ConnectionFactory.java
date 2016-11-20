package ar.edu.uade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory
{
	 private static ConnectionFactory instancia;
		private Connection con;
	   
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
	        String userName = "tpo";
	        String password = "tpo";
	        String url = "jdbc:sqlserver://localhost:1433;databaseName=api2";
			try {
				con = DriverManager.getConnection (url, userName, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return con;
	    }
	    
	    public void closeCon() {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}}
