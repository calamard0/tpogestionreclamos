package ar.edu.uade.model;

import ar.edu.uade.enums.TipoReclamo;

/**
 * Clase encargada de la creacion de Reclamos.
 * @author yairlisa
 *
 */

public class ReclamosFactory {
	
	public static ReclamoZona crearReclamoZona(Cliente cliente, String descripcion, 
											   Usuario operador, Usuario responsable,
											   String zona) {
		
		ReclamoZona reclamoPorZona = new ReclamoZona();
		reclamoPorZona.setCliente(cliente);
		reclamoPorZona.setDescripcion(descripcion);
		reclamoPorZona.setOperador(operador);
		reclamoPorZona.setResponsable(responsable);
		reclamoPorZona.setZona(zona);
		reclamoPorZona.setTiempoRespuesta(-1f);
		reclamoPorZona.setTipoReclamo(TipoReclamo.ZONA);
		
		return reclamoPorZona;
	}
	
	public static ReclamoFacturacion crearReclamoFacturacion(Cliente cliente, String descripcion,
													        Usuario operador, Usuario responsable) {
		
		ReclamoFacturacion reclamoFacturacion = new ReclamoFacturacion();
		reclamoFacturacion.setCliente(cliente);
		reclamoFacturacion.setDescripcion(descripcion);
		reclamoFacturacion.setOperador(operador);
		reclamoFacturacion.setResponsable(responsable);
		reclamoFacturacion.setTiempoRespuesta(-1f);
		reclamoFacturacion.setTipoReclamo(TipoReclamo.FACTURACION);
		
		return reclamoFacturacion;
		
	}
	
	public static ReclamoFaltantes crearReclamoFaltantes(Cliente cliente, String descripcion,
	        Usuario operador, Usuario responsable) {

		ReclamoFaltantes reclamoFaltantes = new ReclamoFaltantes();
		reclamoFaltantes.setCliente(cliente);
		reclamoFaltantes.setDescripcion(descripcion);
		reclamoFaltantes.setOperador(operador);
		reclamoFaltantes.setResponsable(responsable);
		reclamoFaltantes.setTiempoRespuesta(-1f);
		reclamoFaltantes.setTipoReclamo(TipoReclamo.FALTANTES);
		
		return reclamoFaltantes;
		
		}
	
	
	public static ReclamoCantidad crearReclamoCantidad(Cliente cliente, String descripcion,
													   Usuario operador, Usuario responsable) {
		
		ReclamoCantidad reclamoCantidad = new ReclamoCantidad();
		reclamoCantidad.setCliente(cliente);
		reclamoCantidad.setDescripcion(descripcion);
		reclamoCantidad.setOperador(operador);
		reclamoCantidad.setResponsable(responsable);
		reclamoCantidad.setTiempoRespuesta(-1f);
		reclamoCantidad.setTipoReclamo(TipoReclamo.CANTIDAD);

		return reclamoCantidad;
	
	}

	public static ReclamoCompuesto crearReclamoCompuesto(Cliente cliente,Usuario operador,
														 Usuario responsable) {

		ReclamoCompuesto reclamoCompuesto = new ReclamoCompuesto();
		reclamoCompuesto.setCliente(cliente);
		reclamoCompuesto.setDescripcion("Reclamo compuesto");
		reclamoCompuesto.setEstaSolucionado(false);
		reclamoCompuesto.setOperador(operador);
		reclamoCompuesto.setResponsable(responsable);
		reclamoCompuesto.setTiempoRespuesta(-1f);
		reclamoCompuesto.setTipoReclamo(TipoReclamo.COMPUESTO);
		reclamoCompuesto.setZona(null);

		return reclamoCompuesto;
	}
	
}
