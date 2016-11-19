package ar.edu.uade.model;

import java.util.Collection;
import java.util.List;

import ar.edu.uade.dao.UsuarioDAO;
import ar.edu.uade.dto.UsuarioDTO;
import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.exception.UsuarioExistenteException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;

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
		this.setClave(clave);
	}
	
	public Usuario(UsuarioDTO dto) {
		this.nombre = dto.getNombre();
		this.apellido = dto.getApellido();
		this.usuario = dto.getUsuario();
		this.setClave(dto.getClave());
		this.roles = dto.getRoles();
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


	public static Usuario buscarPorUsuario(String responsable) throws UsuarioNoEncontradoException {
		return UsuarioDAO.getInstancia().obtenerUsuarioPorUsuario(responsable);
	}

	public void insert() throws UsuarioExistenteException {
		this.validarUsuario(this.getUsuario());
		UsuarioDAO.getInstancia().insert(this);
	}
	
	public void update() {
		UsuarioDAO.getInstancia().update(this);
	}
	
	public void delete() {
		UsuarioDAO.getInstancia().delete(this);
	}

	private void validarUsuario(String usuario) throws UsuarioExistenteException {
		try {
			this.buscarPorUsuario(usuario);
			throw new UsuarioExistenteException();
		} catch (UsuarioNoEncontradoException e) {
		}
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}
