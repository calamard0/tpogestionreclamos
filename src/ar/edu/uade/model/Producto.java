package ar.edu.uade.model;

import java.util.Vector;

import ar.edu.uade.dao.ProductoDAO;
import ar.edu.uade.dto.ProductoDTO;
import ar.edu.uade.exception.ProductoExistenteException;
import ar.edu.uade.exception.ProductoNoExistenteException;

public class Producto {
	private int codigo;
	private String titulo;
	private String descripcion;
	private float precio;
	
	public Producto(int codigo, String titulo, String descripcion, float precio) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	
	public Producto(ProductoDTO dto) {
		this.codigo = dto.getCodigo();
		this.titulo = dto.getTitulo();
		this.descripcion = dto.getDescripcion();
		this.precio = dto.getPrecio();
	}

	public Producto() {
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

	public void insert() throws ProductoExistenteException {
		Producto prod = ProductoDAO.getInstancia().buscarProducto(this.codigo);
		if(prod != null)
			throw new ProductoExistenteException();
		
		ProductoDAO.getInstancia().insert(this);
	}

	public void update() throws ProductoNoExistenteException {
		Producto prod = ProductoDAO.getInstancia().buscarProducto(this.codigo);
		if(prod == null)
			throw new ProductoNoExistenteException();
		
		ProductoDAO.getInstancia().update(this);
	}

	public void delete() throws ProductoNoExistenteException {
		Producto prod = ProductoDAO.getInstancia().buscarProducto(this.codigo);
		if(prod != null)
			throw new ProductoNoExistenteException();
		
		ProductoDAO.getInstancia().delete(this);
		
	}
}
