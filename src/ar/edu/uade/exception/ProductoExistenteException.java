package ar.edu.uade.exception;

public class ProductoExistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086318216145292205L;

	public ProductoExistenteException() {
		super("Ya hay un producto con ese codigo.");
	}
}
