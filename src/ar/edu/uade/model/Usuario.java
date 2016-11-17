package ar.edu.uade.model;

import java.util.Collection;
import java.util.List;

import ar.edu.uade.dao.UsuarioDAO;
import ar.edu.uade.enums.EnumRoles;

public class Usuario {
	
	private String nombre;
	private String apellido;
	private Collection <EnumRoles> roles; 	
	private int codigo;
	private String usuario;
	private String clave;
	
	public Usuario(String nombre, String apellido, int codigo, String usuario, String clave) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.codigo = codigo;
		this.usuario = usuario;
		this.clave = clave;
	}
	

	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Collection<EnumRoles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<EnumRoles> roles) {
		this.roles = roles;
	}
	public static Usuario buscarPorId(int numUsuario){
		return UsuarioDAO.getInstancia().buscarUsuario(numUsuario);
		
	}
	public static Usuario buscarPorIdyPassword(String user, String pass){
		return UsuarioDAO.getInstancia().buscarUsuario(user, pass);
		
	}


	public static List<Usuario> obtenerResponsables(String tipo) {
		return UsuarioDAO.getInstancia().obtenerResponsables(tipo);
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public static Usuario buscarPorUsuario(String responsable) {
		return UsuarioDAO.getInstancia().obtenerUsuarioPorUsuario(responsable);
	}




}
