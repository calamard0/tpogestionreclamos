package ar.edu.uade.dao;

import java.util.Vector;

public abstract class BaseDAO 
{
	public abstract void insert (Object o);
	public abstract void update (Object o);
	public abstract void delete (Object d);
	public abstract Vector<Object> select (Object o);
}
