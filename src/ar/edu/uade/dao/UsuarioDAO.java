package ar.edu.uade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.exception.UsuarioIncorrectoException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;
import ar.edu.uade.model.ItemFacturaReclamo;
import ar.edu.uade.model.ItemProductoReclamo;
import ar.edu.uade.model.ItemProductoReclamoFaltantes;
import ar.edu.uade.model.Reclamo;
import ar.edu.uade.model.ReclamoCantidad;
import ar.edu.uade.model.ReclamoCompuesto;
import ar.edu.uade.model.ReclamoFacturacion;
import ar.edu.uade.model.ReclamoFaltantes;
import ar.edu.uade.model.ReclamoProducto;
import ar.edu.uade.model.Usuario;

public class UsuarioDAO extends BaseDAO 
{
	private static UsuarioDAO instancia;
	
	private UsuarioDAO()
	{
		
	}
	public static UsuarioDAO getInstancia()
	{
		if (instancia == null)
			instancia = new UsuarioDAO();
		return instancia;
	}

	@Override
	public void delete(Object d) {

		Connection con;
		try {
			Usuario u = (Usuario) d;
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("DELETE FROM Usuario WHERE usuario = ? AND clave = ?");
			s.setString(1, u.getUsuario());
			s.execute();
		} catch (Exception e) {
			System.out.println();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}
	}

	@Override
	public void insert(Object o) {
		Connection con;

		try {
			Usuario u = (Usuario) o;
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con
					.prepareStatement("INSERT INTO Usuario (nombre, apellido, usuario, clave) VALUES (?, ?, ?, ?)");
			s.setString(1, u.getNombre());
			s.setString(2, u.getApellido());
			s.setString(3, u.getUsuario());
			s.setString(4, u.getClave());

			s.execute();
		} catch (Exception e) {
			System.out.println();
		} finally {
			ConnectionFactory.getInstancia().closeCon();
		}

	}

	@Override
	public Vector<Object> select(Object o) {
		Vector usuariosVector = new Vector();
		Connection con;
		try {
			con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("SELECT * FROM Usuario");
			ResultSet result = s.executeQuery();
			while (result.next()) {
				int codigo  = result.getInt(1);
				String usuario  = result.getString(2);
				String nombre = result.getString(3);
				String apellido = result.getString(4);
				String clave = result.getString(5);
				
				Usuario u = new Usuario(nombre, apellido,codigo, usuario, clave);
				usuariosVector.add(u);
			}
		} catch (Exception e) {
			System.out.println();
		}finally {
			ConnectionFactory.getInstancia().closeCon();
		}
		
		return usuariosVector;
	}

	@Override
	public void update(Object o) {
		Connection con;
		try {
			con = ConnectionFactory.getInstancia().getConexion();
			Usuario u = (Usuario) o;
			PreparedStatement s = con.prepareStatement("UPDATE Usuario " +
					"set clave = ? WHERE usuario = ?");
			
			s.setString(1, u.getClave());
			s.setString(2, u.getUsuario());
			s.execute();
		} catch (Exception e) {
			System.out.println();
		}finally {
			ConnectionFactory.getInstancia().closeCon();
		}
		
	}
	
	public Vector<Usuario> selectAll()
	{
		try
		{
			Vector <Usuario>rta = new Vector<Usuario>();
			Connection c = ConnectionFactory.getInstancia().getConexion();
			Statement s = c.createStatement();
			String senten = "Select * from Usuario";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{

				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String usuario = result.getString(4);
				String clave = result.getString(5);
				Usuario usu = new  Usuario(nom, apellido, codigo, usuario, clave);
				rta.add(usu);
				
			}
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario buscarUsuario(String usuario, String password)
	{
		try
		{
			Usuario usu = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where usuario=? and clave=?");			//agregar campos
			s.setString(1,usuario);
			s.setString(2,password);
			ResultSet result = s.executeQuery();			
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
			}
			if(usu!=null){
				List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
				
				usu.setRoles(listaRoles);
			} else {
				throw new UsuarioIncorrectoException();
			}
			
			return usu;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario buscarUsuario(int numero)
	{
		try
		{
			Usuario usu = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where codigo=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
			}
			
			if ( usu == null )
				throw new UsuarioNoEncontradoException("No se ha encontrado el usuario con el número: " + numero);
			
			List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
			usu.setRoles(listaRoles);
			
			return usu;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<EnumRoles> buscarRoles(int idUsuario)
	{
		try
		{
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select r.descripcion from UsuarioRol ur"
					+ " join rol r on r.idRol=ur.idRol"
					+ " where idUsuario=?");			
			s.setInt(1,idUsuario);
			ResultSet result = s.executeQuery();
			List<EnumRoles> lista = new ArrayList<EnumRoles>();
			while (result.next())
			{ 
				switch(result.getString(1)){
				case "Distribucion": lista.add(EnumRoles.DISTRIBUCION);
					break;
				case "Administrador" : lista.add(EnumRoles.ADMINISTRACION);
					break;
				case "CallCenter" : lista.add(EnumRoles.CALL_CENTER);
					break;
				case "Consulta" : lista.add(EnumRoles.CONSULTA);
				 	break;
				case "Facturacion" : lista.add(EnumRoles.FACTURACION);
				 	break;
				case "ZonaEntrega" : lista.add(EnumRoles.ZONA_ENTREGA);
				 	break;
				
				}
			}
		
			return lista;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String obtenerNombreCompleto(Integer idUsuario) {
		String nombre = "";
		try
		{
			Usuario usu = null;
			Connection con = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where codigo=?");
			s.setString(1, String.valueOf(idUsuario));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
				nombre = usu.getNombre() +" "+usu.getApellido();
			}
			
			return nombre;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return nombre;
	}
	
	public List<Usuario> obtenerResponsables(String tipo) {
		try
		{
			String rol = "";
			switch(tipo.toUpperCase()){
			case "PRODUCTO":
				rol = "Distribucion";
				break;
			case "FALTANTES":
				rol = "Distribucion";
				break;
			case "CANTIDAD":
				rol = "Distribucion";
				break;
			case "FACTURACION":
				rol = "Facturacion";
				break;
			case "ZONA":
				rol = "ZonaEntrega";
				break;
			case "COMPUESTO":
				break;
			}

			List <Usuario>rta = new ArrayList<Usuario>();
			Connection c = ConnectionFactory.getInstancia().getConexion();
			PreparedStatement s;
			if(rol.equals("")){ //Si es compuesto traer todos
				s = c.prepareStatement("Select u.* from Usuario u join UsuarioRol ur "
						+ " on ur.idUsuario = u.codigo "
						+ " join Rol r"
						+ " on r.idRol=ur.idRol");
			}else{
				s = c.prepareStatement("Select u.* from Usuario u join UsuarioRol ur "
					+ " on ur.idUsuario = u.codigo "
					+ " join Rol r"
					+ " on r.idRol=ur.idRol"
					+ " where r.descripcion=?");
				s.setString(1,rol);
			}
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String usuario = result.getString(4);
				String clave = result.getString(5);
				Usuario usu = new  Usuario(nom, apellido, codigo, usuario, clave);
				rta.add(usu);
				
			}
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Usuario obtenerUsuarioPorUsuario(String usuario) throws UsuarioNoEncontradoException {
		try
		{
//			Usuario usu = null;
//			Connection con = ConnectionFactory.getInstancia().getConexion();
//			PreparedStatement s = con.prepareStatement("select u.* from usuario u where usuario=?");
//			s.setString(1,usuario);
//			ResultSet result = s.executeQuery();
//			if (result.next())
//			{
//				int codigo = result.getInt(1);
//				String nom = result.getString(2);
//				String apellido = result.getString(3);
//				String u = result.getString(4);
//				String clave = result.getString(5);
//				usu = new  Usuario(nom, apellido, codigo, u, clave);
//				List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
//				usu.setRoles(listaRoles);
//			} else {
//				throw new UsuarioNoEncontradoException("No existe el usuario con el nombre: " + usuario);
//			}
//			return usu;
			
			int codigo = 1;
			String nom = "Juan";
			String apellido = "Perez";
			String u = "jperez";
			String clave = "jperez";
			Usuario usu = new  Usuario(nom, apellido, codigo, u, clave);
			List<EnumRoles> listaRoles = new ArrayList<EnumRoles>() { { add(EnumRoles.ADMINISTRACION); add(EnumRoles.CALL_CENTER); } };
			usu.setRoles(listaRoles);
			
			if (false) {
				throw new SQLException();
			}
			
			return usu;
			
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
