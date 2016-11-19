package ar.edu.uade.exception;

public class ClienteNoEncontradoException extends Exception {

	private static final long serialVersionUID = 1343368162575493067L;

	public ClienteNoEncontradoException(String msj) {
		super(msj);
	}
}
