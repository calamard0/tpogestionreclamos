package ar.edu.uade.exception;

public class UsuarioExistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75060585363133877L;

	public UsuarioExistenteException() {
		super("Ya hay un usuario con ese identificador.");
	}
}
