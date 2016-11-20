package ar.edu.uade.exception;

public class ClienteExistenteException extends Exception {

	private static final long serialVersionUID = -531431717743347562L;

	public ClienteExistenteException() {
		super("Ya hay un cliente con ese DNI.");
	}

}
