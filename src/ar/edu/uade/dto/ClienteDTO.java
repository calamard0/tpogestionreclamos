package ar.edu.uade.dto;

import java.util.Collection;

import ar.edu.uade.model.Reclamo;
import ar.edu.uade.model.Usuario;

public class ClienteDTO {

    private String dni;
    private String nombre;
    private String cantidad_reclamos;
    private String mail;
    private String domicilio;
    private String telefono;
    private int codigo;

    public ClienteDTO(String dni, String nombre, String cantidad_reclamos, String mail) {
        this.dni = dni;
        this.nombre = nombre;
        this.cantidad_reclamos = cantidad_reclamos;
        this.mail = mail;
    }
    
    public ClienteDTO(String dni) {
    	this.dni = dni;
    }
    
    public ClienteDTO(String dni, String nombre, String mail, String domicilio, String telefono, int codigo) {
        this.dni = dni;
        this.nombre = nombre;
        this.mail = mail;
        this.setDomicilio(domicilio);
        this.setTelefono(telefono);
        this.setCodigo(codigo);
    }

     public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}



	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad_reclamos() {
        return cantidad_reclamos;
    }

    public void setCantidad_reclamos(String cantidad_reclamos) {
        this.cantidad_reclamos = cantidad_reclamos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

	@Override
	public String toString() {
		return "ClienteView [dni=" + dni + ", nombre=" + nombre
				+ ", cantidad_reclamos=" + cantidad_reclamos + ", mail=" + mail
				+ "]";
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
