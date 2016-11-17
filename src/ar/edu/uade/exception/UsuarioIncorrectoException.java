package ar.edu.uade.exception;

public class UsuarioIncorrectoException extends Exception {

	private static final long serialVersionUID = 6341003256093547052L;

	public UsuarioIncorrectoException() {
		super("Usuario o contraseña incorrectos.");
	}
	
}
