package ar.edu.uade.model;

import java.util.Collection;

public class ReclamoFacturacion extends Reclamo{
	private Collection<ItemFacturaReclamo> items;

	
	public ReclamoFacturacion() {
		super();
	}

	public ReclamoFacturacion(Collection<ItemFacturaReclamo> items) {
		this.items = items;
	}

	public Collection<ItemFacturaReclamo> getItems() {
		return items;
	}

	public void setItems(Collection<ItemFacturaReclamo> items) {
		this.items = items;
	}
	
	
}
