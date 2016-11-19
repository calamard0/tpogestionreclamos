package ar.edu.uade.dto;

import java.util.Collection;

import ar.edu.uade.dao.UsuarioDAO;
import ar.edu.uade.enums.EnumRoles;

public class UsuarioDTO {
	
	private String nombre;
	private String apellido;
	private int codigo;
	private Collection <EnumRoles> roles; 
	private String usuario;
	private String clave;
	
	public UsuarioDTO(String nombre, String apellido, int codigo, String usuario, Collection<EnumRoles> roles, String clave) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.codigo = codigo;
		this.usuario = usuario;
		this.roles = roles;
		this.clave = clave;
	}
	
	public UsuarioDTO(String usuario) {
		this.usuario = usuario;
	}

	public UsuarioDTO() {
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


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public Collection<EnumRoles> getRoles() {
		return roles;
	}


	public void setRoles(Collection<EnumRoles> roles) {
		this.roles = roles;
	}

}
