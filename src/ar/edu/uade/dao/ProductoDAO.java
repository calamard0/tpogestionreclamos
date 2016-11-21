package ar.edu.uade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.model.Cliente;
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
		Connection con;

		try {
			Producto p = (Producto) d;
			con =ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("DELETE FROM Producto WHERE codigo = ?");
			s.setInt(1, p.getCodigo());
			s.execute();
		} catch (Exception e) {
			System.out.println();
		}finally {
			ConnectionFactory.getInstancia().closeCon();
		}
	}

	@Override
	public void insert(Object o) {
		Connection con;
		try {
			Producto p = (Producto) o;
		    con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("INSERT INTO Producto (titulo, descripcion, precio, creador) VALUES ( ?, ?, ?, ?)");
			s.setString(1, p.getTitulo());
			s.setString(2, p.getDescripcion());
			s.setFloat(3, p.getPrecio());
			s.setFloat(4, 4);
			s.execute();
		} catch (Exception e) {
			System.out.println(e);
		}finally {

			ConnectionFactory.getInstancia().closeCon();
		}
		
	}

	@Override
	public Vector<Object> select(Object o) {
		Vector productosVector = new Vector();
		Connection con;
		try {
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("SELECT * FROM Producto");
			ResultSet result = s.executeQuery();
			while (result.next()) {
				int codigo = result.getInt(1);
				String titulo = result.getString(2);
				String descripcion = result.getString(3);
				float precio = result.getFloat(4);
				
				Producto p = new Producto(codigo, titulo, descripcion, precio);
				productosVector.add(p);
			}
		} catch (Exception e) {
			System.out.println();
		}finally {
			ConnectionFactory.getInstancia().closeCon();
		}
		return productosVector;
	}

	@Override
	public void update(Object o) {

		Connection con = null;
		try {
			Producto p = (Producto) o;
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("UPDATE Producto "  + "set titulo= ?,"
					+ "descripcion = ?," + "precio = ? WHERE codigo = ?");

			s.setString(1, p.getTitulo());
			s.setString(2, p.getDescripcion());
			s.setFloat(3, p.getPrecio());
			s.setLong(4, p.getCodigo());
			s.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}
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
