package ar.edu.uade.model;

import java.util.Collection;

public class ReclamoProducto  extends Reclamo{

	private Collection<ItemProductoReclamo> items;

	
	
	public ReclamoProducto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReclamoProducto(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamo> getItems() {
		return items;
	}

	public void setItems(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}
	
	
}
