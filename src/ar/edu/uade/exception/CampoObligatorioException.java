package ar.edu.uade.exception;

public class CampoObligatorioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4904160603011115531L;

	public CampoObligatorioException() {
		super("Uno o mas campos obligatorios no fueron completados.");
	}
	
	public CampoObligatorioException(String msj) {
		super(msj);
	}
}
