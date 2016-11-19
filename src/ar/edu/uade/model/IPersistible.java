package ar.edu.uade.model;

import ar.edu.uade.exception.ClienteExistenteException;
import ar.edu.uade.exception.ClienteNoEncontradoException;

public interface IPersistible {

	public abstract void insert () throws Exception;
	public abstract void update ();
	public abstract void delete () throws Exception;
	
}
