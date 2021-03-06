package ar.edu.uade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ar.edu.uade.dao.FacturaDAO;
import ar.edu.uade.enums.EnumEstado;

/**
 * Clase especializada en ayudar al Sistema en la creacion de los reclamos.
 * @author yairlisa
 *
 */
public class ReclamosHelper {
	
	
	public static List<EventoReclamo> crearEventoReclamoInicio() {
		
		List<EventoReclamo> listaEventos = new ArrayList<EventoReclamo>();
		EventoReclamo eventoReclamo = new EventoReclamo();
		eventoReclamo.setDetalle("");
		eventoReclamo.setEstado(EnumEstado.INGRESADO);
		eventoReclamo.setFecha(new Date());
		listaEventos.add(eventoReclamo);
		
		return listaEventos;
	}
	
	public static List<ItemFacturaReclamo> convertirMapaEnListaItemFacturaReclamos(Map<Integer, Date> mapIdFecha) {
		
		List<ItemFacturaReclamo> listaItemsFacturaReclamos = new ArrayList<ItemFacturaReclamo>();
		
		for (Map.Entry<Integer, Date> item : mapIdFecha.entrySet())
		{
			ItemFacturaReclamo itemFacturaReclamo = new ItemFacturaReclamo();
			itemFacturaReclamo.setFactura(FacturaDAO.getInstancia().buscarPorId(item.getKey()));
			itemFacturaReclamo.setFechaFactura(item.getValue().getTime());
			listaItemsFacturaReclamos.add(itemFacturaReclamo);
		}

		return listaItemsFacturaReclamos;
	}
	
	
	public static List<ItemProductoReclamo> convertirMapaEnListaItemProductoReclamo(Map<Integer,Integer> mapCodigoCantidad) {
		
		List<ItemProductoReclamo> listaItemsProductoReclamos = new ArrayList<ItemProductoReclamo>();
		
		for (Map.Entry<Integer, Integer> item : mapCodigoCantidad.entrySet())
		{
			ItemProductoReclamo itemProductoReclamo = new ItemProductoReclamo();
			itemProductoReclamo.setProducto(Producto.buscarPorId(item.getKey()));
			itemProductoReclamo.setCantidad(item.getValue());
			listaItemsProductoReclamos.add(itemProductoReclamo);
		}

		return listaItemsProductoReclamos;
	}

	public static List<Reclamo> convertirIdReclamosEnListaReclamos(List<Integer> ids_reclamos) {

		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		for(int i=0; i<ids_reclamos.size();i++){
			listaReclamos.add(Reclamo.buscarReclamo(ids_reclamos.get(i)));
		}
		return listaReclamos;
	}
}
