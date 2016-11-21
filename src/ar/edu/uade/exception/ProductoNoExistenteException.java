package ar.edu.uade.exception;

public class ProductoNoExistenteException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5252322992812709007L;

	public ProductoNoExistenteException() {
		super("No existe un producto con ese codigo.");
	}
}
