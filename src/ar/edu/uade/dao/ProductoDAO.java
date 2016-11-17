package ar.edu.uade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.model.Producto;

public class ProductoDAO extends BaseDAO 
{
	private static ProductoDAO instancia;
	
	private ProductoDAO()
	{
		
	}
	public static ProductoDAO getInstancia()
	{
		if (instancia == null)
			instancia = new ProductoDAO();
		return instancia;
	}

	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub

	}
	public Vector<Producto> selectAll()
	{
		try
		{
			Vector <Producto>rta = new Vector<Producto>();
			Connection c = ConnectionFactory.getInstancia().getConexion();
			Statement s = c.createStatement();
			String senten = "Select * from Producto";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String titulo = result.getString(2);
				String descripcion = result.getString(3);
				float precio = result.getFloat(4);
				Producto prod = new  Producto(codigo,titulo,descripcion,precio);
				rta.add(prod);
				
			}
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Producto buscarProducto(int numero)
	{
		try
		{
			Producto cli = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select c.* from Producto c where codigo=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String titulo = result.getString(2);
				String descripcion = result.getString(3);
				float precio = result.getFloat(4);
				cli = new  Producto(codigo,titulo,descripcion,precio);
			}
			return cli;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
