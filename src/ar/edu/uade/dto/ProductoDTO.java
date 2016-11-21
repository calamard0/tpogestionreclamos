package ar.edu.uade.dto;

import java.util.Vector;

import ar.edu.uade.dao.ProductoDAO;
import ar.edu.uade.model.Producto;

public class ProductoDTO {

	private int codigo;
	private String titulo;
	private String descripcion;
	private float precio;
	
	
	public ProductoDTO(int codigo, String titulo, String descripcion, float precio) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public ProductoDTO(int codigo) {
		super();
		this.codigo = codigo;
	}
	public ProductoDTO() {
		super();
	}




	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}

	
	public static Producto buscarPorId(int codigoProducto) {
		return ProductoDAO.getInstancia().buscarProducto(codigoProducto);
	}
	
	public static Vector<Producto> obtenerTodos() {
		Vector<Producto> productos = ProductoDAO.getInstancia().selectAll();
		return productos;
	}
	
	public static Producto buscarProducto(int idProducto){
		return ProductoDAO.getInstancia().buscarProducto(idProducto);
	}

	

}
