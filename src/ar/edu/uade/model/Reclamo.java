package ar.edu.uade.model;

import java.util.*;

import ar.edu.uade.dao.ReclamoDAO;
import ar.edu.uade.dao.UsuarioDAO;
import ar.edu.uade.dto.ReclamoTPromXOperadorDTO;
import ar.edu.uade.enums.EnumEstado;
import ar.edu.uade.enums.TipoReclamo;

public class Reclamo {

	private int numero;
	private Cliente cliente;
	private String descripcion;
	private Usuario operador;
	private List<EventoReclamo> eventos;
	private Usuario responsable;
	private boolean estaSolucionado;
	private double tiempoRespuesta;
	private TipoReclamo tipoReclamo;
	private String zona;

	public Reclamo(int numero, String descripcion, TipoReclamo tipoReclamo) {
		this.numero = numero;
		this.descripcion = descripcion;
		this.tipoReclamo=tipoReclamo;
		this.eventos = new ArrayList<>();
		this.estaSolucionado = false;
	}
	
	public Reclamo() {
		this.eventos = new ArrayList<>();
	}
	
	public Reclamo(int numero, String desc) {
		this.numero=numero;
		descripcion=desc;
	}

	/**
	* Cantidad de reclamos en el mes pasado por parametro sobre el a�o actual
	* */
	public int getCantReclamosMensual(int mes) {
		Date anioActual = new Date();
		int cant = 0 ;
		for (EventoReclamo evento : eventos) {
			if((anioActual.getYear() == evento.getFecha().getYear()) && (evento.getFecha().getMonth() == mes)){
				cant++;
			}
		}
		return cant;
	}

	public double getTiempoRespuesta() {
		return tiempoRespuesta;
	}
	public static Collection<Reclamo> buscarReclamos(){
		return ReclamoDAO.getInstancia().buscarReclamos();
	}

	public void generarEvento(Date fecha, EnumEstado estado, String detalle) {
		EventoReclamo er = new EventoReclamo();
		er.setDetalle(detalle);
		er.setEstado(estado);
		er.setFecha(fecha);
		ReclamoDAO.getInstancia().insertEventoReclamo(this.getNumero(), er);
	}

	public void guardarCambios() {
		ReclamoDAO reclamoMapper=ReclamoDAO.getInstancia();
		reclamoMapper.insert(this);	
		
	}

	public void agregarDetalle(Date fecha, String detalle) {
		eventos.add(new EventoReclamo(fecha, detalle));
	}

	public void cambiarEstado(Date fecha, EnumEstado estado, String detalle) {
		eventos.add(new EventoReclamo(estado, fecha, detalle));
	}

	public void cambiarEstado(Date fecha, EnumEstado estado) {
		eventos.add(new EventoReclamo(estado, fecha));
	}
	
	
	//Getters y setters
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getOperador() {
		return operador;
	}

	public void setOperador(Usuario operador) {
		this.operador = operador;
	}

	public List<EventoReclamo> getEventos() {
		return eventos;
	}

	public List<EventoReclamo> getAllEventos() {
		
		List<EventoReclamo> eventos = ReclamoDAO.getInstancia().buscarEventosXReclamo(this.getNumero());
		return eventos;
	}

	public void setEventos(List<EventoReclamo> eventos) {
		this.eventos = eventos;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public boolean isEstaSolucionado() {
		return estaSolucionado;
	}

	public void setEstaSolucionado(boolean estaSolucionado) {
		this.estaSolucionado = estaSolucionado;
	}

	public void setTiempoRespuesta(double tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}
	
	public TipoReclamo getTipoReclamo() {
		return tipoReclamo;
	}

	public void setTipoReclamo(TipoReclamo tipoReclamo) {
		this.tipoReclamo = tipoReclamo;
	}
	
	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}
	
	public static Collection<Reclamo> obtenerTodos() {
		Collection<Reclamo> reclamos = ReclamoDAO.getInstancia().buscarReclamos();
		return reclamos;
	}

	public static Reclamo buscarReclamo(long idReclamo){
		return ReclamoDAO.getInstancia().buscarReclamo(idReclamo);
	}
	
	/*cantidad de reclamos tratados por mes*/
	public  static int getCantidadReclamosPorMes(int mes){
		return ReclamoDAO.getInstancia().getCantReclamosPorMes(mes).size();
	}
	
	/*ranking tratamiento de reclamos*/
	public static Map<String, Long> getRankingTratamientoReclamos(){
		return ReclamoDAO.getInstancia().getRankingTratamientoReclamos();
	}
	
	/*tiempo promedio de respuesta de los reclamos por responsable*/
	public static List<ReclamoTPromXOperadorDTO> getTiempoPromedioRespuestaPorResp(){
		List<Vector> lista = ReclamoDAO.getInstancia().getTiempoPromedioRespuestaPorResp();
		List<ReclamoTPromXOperadorDTO> listaView = new ArrayList<>();
		for(Vector obj : lista){
			String usuarioOperador = UsuarioDAO.getInstancia().obtenerNombreCompleto(Integer.valueOf(String.valueOf(obj.get(1))));
			String tiempoPromedio = String.valueOf(obj.get(0));
			ReclamoTPromXOperadorDTO view = new ReclamoTPromXOperadorDTO(usuarioOperador,tiempoPromedio);
			listaView.add(view);
		}
		return listaView;
	}

	public static Reclamo getUltimoReclamo() {
		return ReclamoDAO.getInstancia().getUltimoReclamo();
	}
	
	
}