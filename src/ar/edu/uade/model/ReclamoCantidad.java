package ar.edu.uade.model;

import java.util.Collection;

public class ReclamoCantidad extends Reclamo{

	private Collection<ItemProductoReclamo> items;
	
	public ReclamoCantidad() {
	}

	public ReclamoCantidad(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamo> getItems() {
		return items;
	}

	public void setItems(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}
	
	
}
