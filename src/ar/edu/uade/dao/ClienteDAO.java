package ar.edu.uade.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.model.Cliente;

public class ClienteDAO extends BaseDAO 
{
	private static ClienteDAO instancia;
	
	private ClienteDAO()
	{
		
	}
	public static ClienteDAO getInstancia()
	{
		if (instancia == null)
			instancia = new ClienteDAO();
		return instancia;
	}

	@Override
	public void delete(Object o) {
		Connection con;
		try {
			Cliente c = (Cliente) o;
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("DELETE FROM Cliente WHERE dni = ?");
			s.setInt(1, c.getDni());
			s.execute();
		} catch (Exception e) {
			System.out.println();
		}finally {
			ConnectionFactory.getInstancia().closeCon();
		}

	}

	@Override
	public void insert(Object o) {
		Connection con = null;
		try {
			con = ConnectionFactory.getInstancia().getConexion();
			Cliente c = (Cliente) o;
			PreparedStatement s = con.prepareStatement(
					"INSERT INTO Cliente (nombre, dni, domicilio, email, telefono) VALUES (?, ?, ?, ?, ?)");
			s.setString(1, c.getNombre());
			s.setInt(2, c.getDni());
			s.setString(3, c.getDomicilio());
			s.setString(4, c.getMail());
			s.setString(5, c.getTelefono());
			s.execute();
		} catch (Exception e) {
			System.out.println();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}

	}

	@Override
	public Vector<Object> select(Object o) {

		Vector clientesVector = new Vector();
		Connection con;
		try {
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("SELECT * FROM Cliente");
			ResultSet result = s.executeQuery();
			while (result.next()) {
				int cod = result.getInt(1);
				int dni = result.getInt(2);
				String nombre = result.getString(3);
				String domicilio = result.getString(4);
				String telefono = result.getString(5);
				String email = result.getString(6);
				Cliente c = new Cliente(cod, nombre, dni, domicilio, telefono, email);
				clientesVector.add(c);
			}
		} catch (Exception e) {
			System.out.println();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}
		return clientesVector;
	}

	@Override
	public void update(Object o) {
		Connection con = null;
		try {
			Cliente c = (Cliente) o;
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("UPDATE Cliente " + "set nombre = ?," + "set domicilio = ?,"
					+ "set telefono = ?," + "set email = ? WHERE dni = ?");

			s.setString(1, c.getNombre());
			s.setString(2, c.getDomicilio());
			s.setString(3, c.getTelefono());
			s.setString(4, c.getMail());
			s.setLong(5, c.getDni());
			s.execute();

		} catch (Exception e) {
			System.out.println();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}
	}
	public Vector<Cliente> selectAll()
	{
		try
		{
			
	
			Vector <Cliente>rta = new Vector<Cliente>();
			Connection c = ConnectionFactory.getInstancia().getConexion();
			Statement s = c.createStatement();
			String senten = "Select * from Cliente";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dni = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				Cliente cli = new  Cliente(codigo,nom,dni, domicilio,telefono,mail);
				rta.add(cli);
				
			}
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Cliente buscarCliente(int numero)
	{
		try
		{
			Cliente cli = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select c.* from Cliente c where codigo_cliente=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dni = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				cli = new  Cliente(codigo,nom,dni, domicilio,telefono,mail);
			}
			return cli;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Cliente buscarPorDni(int dni) {
		try
		{
			Cliente cli = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select c.* from Cliente c where dni=?");
			s.setString(1, String.valueOf(dni));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dnif = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				cli = new  Cliente(codigo,nom,dnif, domicilio,telefono,mail);
			}
			return cli;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Vector> getClientesOrdenadosPorCantidadReclamoDesc()
	{
		try
		{
			List <Vector>rta = new ArrayList<Vector>();
			Connection c = ConnectionFactory.getInstancia().getConexion();
			Statement s = c.createStatement();
			String senten = "select  dni, c.nombre, count(r.numero) as cantidad, c.mail "
					+ " from cliente c "
					+ "	left join reclamo r"
					+ " on c.codigo_cliente=r.cliente"
					+ " group by c.dni, c.nombre, c.mail"
					+ " order by count(r.numero) DESC, dni ASC, nombre ASC";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String dni = result.getString(2);
				long cantidad = result.getLong(3);
				String mail = result.getString(4);
				Vector obj = new Vector(4);
				obj.add(codigo);
				obj.add(dni);
				obj.add(cantidad);
				obj.add(mail);
				rta.add(obj);
			}
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
