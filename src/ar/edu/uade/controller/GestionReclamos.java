package ar.edu.uade.controller;

import java.util.*;

import ar.edu.uade.dao.ProductoDAO;
import ar.edu.uade.dto.*;
import ar.edu.uade.enums.EnumEstado;
import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.enums.TipoReclamo;
import ar.edu.uade.exception.UsuarioExistenteException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;
import ar.edu.uade.model.*;

public class GestionReclamos {

	private Collection<Cliente> clientes;
	private Collection<Reclamo> reclamos;
	private Collection<Usuario> usuarios;
	private Usuario user;

	private static GestionReclamos instancia = null;

	private GestionReclamos() {
	}

	public static GestionReclamos getInstancia() {
		if (instancia == null) {
			instancia = new GestionReclamos();
		}
		return instancia;
	}


	public Collection<ClienteDTO> getClientes() {
		Collection<ClienteDTO> clientes =  new ArrayList<>();
		for (Cliente cliente : Cliente.obtenerTodos()) {
			ClienteDTO cV = new ClienteDTO(String.valueOf(cliente.getDni()), cliente.getNombre(), String.valueOf(cliente.getCantReclamos()), cliente.getMail());
			clientes.add(cV);
		}
		return clientes;
	}
	
	public Collection<EnumRoles> getRoles() {
		Collection<EnumRoles> roles = new ArrayList<>();
		roles.add(EnumRoles.ADMINISTRACION);
		roles.add(EnumRoles.CALL_CENTER);
		roles.add(EnumRoles.CONSULTA);
		roles.add(EnumRoles.DISTRIBUCION);
		roles.add(EnumRoles.FACTURACION);
		roles.add(EnumRoles.ZONA_ENTREGA);
		return roles;
	}
	
	public List<UsuarioDTO> getUsuariosResponsables(String tipo) {
		List<UsuarioDTO> usuarios =  new ArrayList<>();
		for (Usuario usu : Usuario.obtenerResponsables(tipo)) {
			UsuarioDTO cV = new UsuarioDTO(usu.getNombre(),usu.getApellido(),usu.getCodigo(),usu.getUsuario(), usu.getRoles(), usu.getClave());
			usuarios.add(cV);
		}
		return usuarios;
	}


	public Collection<String> getCodigoProductos() {
		Collection<String> productos = new ArrayList<>();
		for (Producto producto : Producto.obtenerTodos()) {
			String codigo = String.valueOf(producto.getCodigo()) + "-" + producto.getTitulo();
			productos.add(codigo);
		}
		return productos;
	}
	
	public Collection<String> getCodigoFactura() {
		Collection<String> facturas = new ArrayList<>();
		for (Factura factura : Factura.obtenerAll()) {
			String codigo = String.valueOf(factura.getIdFactura());
			facturas.add(codigo);
		}
		return facturas;
	}
	
	public UsuarioDTO getUsuario(String usuario) throws UsuarioNoEncontradoException {
		Usuario usu = Usuario.buscarPorUsuario(usuario);
		UsuarioDTO dto = new UsuarioDTO(usu.getNombre(),usu.getApellido(),usu.getCodigo(),usu.getUsuario(), usu.getRoles(), usu.getClave());
		return dto;
	}


	/**
	 * Solo pueden crear eventos aquelos que posean los roles: Administrador, Facturacion, Distribucion, o Zona de Entrega
	 * Los de Call Center y  Consulta no pueden
	 * @return true si puede crear eventos, y false en caso contrario
	 * */
	public boolean puedeCrearEventos(int codigoUsuario) {
		Usuario usuario = buscarUsuario(codigoUsuario);
		Collection<EnumRoles> roles = usuario.getRoles();
		boolean puede = false;
		if(roles.contains(EnumRoles.ADMINISTRACION) || roles.contains(EnumRoles.FACTURACION) || roles.contains(EnumRoles.DISTRIBUCION) || roles.contains(EnumRoles.ZONA_ENTREGA)){
			puede = true;
		}
		return puede;
	}

	public Collection<EventoReclamoDTO> getTratamientoReclamo(int numeroReclamo) {
		Reclamo reclamo = buscarReclamo(numeroReclamo);
	
		Collection<EventoReclamoDTO> eventosReclamoView = new ArrayList<>();
		if (reclamo != null) {
			for (EventoReclamo eventoReclamo : reclamo.getAllEventos()) {
				EventoReclamoDTO evento = new EventoReclamoDTO(eventoReclamo.getEstado().getTexto(), eventoReclamo.getFecha(), eventoReclamo.getDetalle());
				eventosReclamoView.add(evento);
				
			}
		}
		return eventosReclamoView;
	}

	/**
	 * Devuelve solo los reclamos que puede ver cada usuario (dependiendo de sus roles)
	 * Ejemplo: los que posean de rol zona_entrega solo podran ver los reclamos de tipo zona
	 * */
	public Collection<ReclamoDTO> getReclamosParaUsuario(int numUsuario){
		Collection<String> tiposDeReclamos = new ArrayList<>();
		Collection<EnumRoles> roles = GestionReclamos.getInstancia().rolesUsuario(numUsuario);
		if(roles.contains(EnumRoles.ADMINISTRACION) || roles.contains(EnumRoles.CALL_CENTER) || roles.contains(EnumRoles.CONSULTA)){
			tiposDeReclamos.add(TipoReclamo.PRODUCTO.toString());
			tiposDeReclamos.add(TipoReclamo.FALTANTES.toString());
			tiposDeReclamos.add(TipoReclamo.CANTIDAD.toString());
			tiposDeReclamos.add(TipoReclamo.FACTURACION.toString());
			tiposDeReclamos.add(TipoReclamo.ZONA.toString());
			tiposDeReclamos.add(TipoReclamo.COMPUESTO.toString());
		}
		if(roles.contains(EnumRoles.DISTRIBUCION)){
			tiposDeReclamos.add(TipoReclamo.PRODUCTO.toString());
			tiposDeReclamos.add(TipoReclamo.FALTANTES.toString());
			tiposDeReclamos.add(TipoReclamo.CANTIDAD.toString());
		}
		if(roles.contains(EnumRoles.ZONA_ENTREGA)){
			tiposDeReclamos.add(TipoReclamo.ZONA.toString());
		}
		if(roles.contains(EnumRoles.FACTURACION)){
			tiposDeReclamos.add(TipoReclamo.FACTURACION.toString());
		}

		Collection<ReclamoDTO> reclamosDto = new ArrayList<>();
		List<Reclamo> reclamos = (List<Reclamo>) Reclamo.obtenerTodos(); 
		for (Reclamo reclamo : reclamos ) {
			if(reclamo.getTipoReclamo()!=null){
				if(tiposDeReclamos.contains(reclamo.getTipoReclamo().toString())){
					ReclamoDTO reclamoV = new ReclamoDTO(reclamo.getNumero(), reclamo.getDescripcion(),
							reclamo.getTipoReclamo().getDescripcionTipo(), reclamo.isEstaSolucionado());
					reclamosDto.add(reclamoV);
				}
			}
		}
		return reclamosDto;
	}
	
	public void iniciarReclamo(String desc, int codigo_cliente, int operador, int cod_responsable, boolean es_compuesto){
	if (!es_compuesto){
		Reclamo reclamoNuevo = new Reclamo(reclamos.size()+1, desc);
		reclamoNuevo.setOperador(buscarUsuario(operador));
		reclamoNuevo.setCliente(buscarCliente(codigo_cliente));
		reclamoNuevo.setResponsable(buscarUsuario(cod_responsable));
		reclamoNuevo.guardarCambios();
		}
	}

	public void crearReclamoProducto(int dni,HashMap<Integer, Integer> mapCodigoCantidad, String descripcion, String responsable) throws UsuarioNoEncontradoException {
		Cliente c = Cliente.buscarPorDni(dni);
		Usuario resp = Usuario.buscarPorUsuario(responsable);
		ReclamoProducto r = new ReclamoProducto();
		r.setCliente(c);
		r.setDescripcion(descripcion);
		r.setEstaSolucionado(false);
		r.setOperador(user);
		r.setResponsable(resp);
		r.setTiempoRespuesta(-1f);
		r.setTipoReclamo(TipoReclamo.PRODUCTO);

		List<ItemProductoReclamo> listaItems = new ArrayList<ItemProductoReclamo>();

		for (Map.Entry<Integer, Integer> item : mapCodigoCantidad.entrySet()) {
			ItemProductoReclamo ipr = new ItemProductoReclamo();
			ipr.setProducto(Producto.buscarProducto(item.getKey()));
			ipr.setCantidad(item.getValue());
			listaItems.add(ipr);
		}

		r.setItems(listaItems);

		List<EventoReclamo> listaEventos = new ArrayList<EventoReclamo>();
		EventoReclamo er = new EventoReclamo();
		er.setDetalle("");
		er.setEstado(EnumEstado.INGRESADO);
		er.setFecha(new Date());
		listaEventos.add(er);

		r.setEventos(listaEventos);

		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(r);
		this.setReclamos(listaReclamos);

		r.guardarCambios();
	}

	public void crearReclamoCantidades(int dni, Map<Integer, Integer> mapCodigoCantidad, String descripcion, String responsable) throws UsuarioNoEncontradoException { 
		Cliente cliente = Cliente.buscarPorDni(dni);
		Usuario resp = Usuario.buscarPorUsuario(responsable);
		ReclamoCantidad reclamoCantidad = ReclamosFactory.crearReclamoCantidad(cliente, descripcion,user,resp);
		
		List<ItemProductoReclamo> listaItemProductoReclamo = ReclamosHelper.convertirMapaEnListaItemProductoReclamo(mapCodigoCantidad);
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoCantidad.setItems(listaItemProductoReclamo);
		reclamoCantidad.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoCantidad);
		this.setReclamos(listaReclamos);
		
		reclamoCantidad.guardarCambios();
		

	}

	public void crearReclamoZona(int dni, String zona, String descripcion, String responsable) throws UsuarioNoEncontradoException {
		Cliente cliente = Cliente.buscarPorDni(dni);
		Usuario resp = Usuario.buscarPorUsuario(responsable);		
		ReclamoZona reclamoPorZona = ReclamosFactory.crearReclamoZona(cliente, descripcion, 
																	  user,resp, zona);
		
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoPorZona.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoPorZona);
		this.setReclamos(listaReclamos);

		reclamoPorZona.guardarCambios();
		
	}

	public void crearReclamoFactura(int dni, String descripcion, Map<Integer, Date> mapIdFecha, String responsable) throws UsuarioNoEncontradoException {
		Cliente cliente = Cliente.buscarPorDni(dni);
		Usuario resp = Usuario.buscarPorUsuario(responsable);
		ReclamoFacturacion reclamoFacturacion = ReclamosFactory.crearReclamoFacturacion(cliente, descripcion, 
																			            user, resp);
		
		List<ItemFacturaReclamo> listaItemReclamoFacturas = ReclamosHelper.convertirMapaEnListaItemFacturaReclamos(mapIdFecha);
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoFacturacion.setItems(listaItemReclamoFacturas);
		reclamoFacturacion.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoFacturacion);
		this.setReclamos(listaReclamos);
		
		reclamoFacturacion.guardarCambios();
		
	}

	public void crearReclamoFaltantes(int dni, int cod_producto, int cant_socilitada, int cant_recibidad, String descripcion, String responsable) throws UsuarioNoEncontradoException {
		Cliente cliente = Cliente.buscarPorDni(dni);
		Usuario resp = Usuario.buscarPorUsuario(responsable);
		//Usuario operador = Usuario.buscarPorId(cod_operador);
		
		ReclamoFaltantes reclamoFaltantes = ReclamosFactory.crearReclamoFaltantes(cliente, descripcion, 
																			            user, resp);
		
		
		ItemProductoReclamoFaltantes ipf = new ItemProductoReclamoFaltantes();
		ipf.setCantidadFacturada(cant_recibidad);
		ipf.setCantidadFaltante(cant_socilitada);
		Producto prod = ProductoDAO.getInstancia().buscarProducto(cod_producto);
		ipf.setProducto(prod);
		
		List<ItemProductoReclamoFaltantes> listaItemReclamoFaltantes = new ArrayList<ItemProductoReclamoFaltantes>();
		listaItemReclamoFaltantes.add(ipf);
		
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
				
		reclamoFaltantes.setItems(listaItemReclamoFaltantes);
		reclamoFaltantes.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoFaltantes);
		this.setReclamos(listaReclamos);
		
		reclamoFaltantes.guardarCambios();
	}

	public void crearReclamoCompuesto(int dni, List<Integer> ids_reclamos, String responsable) throws UsuarioNoEncontradoException {
		Cliente cliente = Cliente.buscarPorDni(dni);
		Usuario operador = user;
		Usuario resp = Usuario.buscarPorUsuario(responsable);

		ReclamoCompuesto reclamoCompuesto = ReclamosFactory.crearReclamoCompuesto(cliente, operador, resp);

		List<Reclamo> listaReclamos = ReclamosHelper.convertirIdReclamosEnListaReclamos(ids_reclamos);

		reclamoCompuesto.setReclamos(listaReclamos);
		
		reclamoCompuesto.guardarCambios();
	}

	public void actualizarReclamo(Date fecha, String estado, int codigo_reclamo, String detalle, Integer codigoUsuario) {
		
		Reclamo reclamo = buscarReclamo(codigo_reclamo);
		
		if (reclamo != null) {
			reclamo.generarEvento(new Date(), EnumEstado.valueOf(estado.replace(" ", "_").toUpperCase()), detalle);		
			}
		
		
		}

	/**
	 * Consulta en la base de usuarios si existe el usuario y el password pasados como parametros
	 * @return Devuelve el codigo de usuario si el usuario y password son correctos, y null en caso contrario
	 */
	public Integer login(String usuario, String password) {
		Usuario user = Usuario.buscarPorIdyPassword(usuario, password);
		this.user=user;
		if(user==null){
			return null;
		}else{
			return user.getCodigo();
		}
	}
	
	 
	
	
	/**
	 * @return Devuelve los roles que tiene el usuario con el codigo pasado como parametro
	 */
	public Collection<EnumRoles> rolesUsuario(int numUsuario) {
		return buscarUsuario(numUsuario).getRoles();
	}

	private Reclamo buscarReclamo(int numeroReclamo) {
		return Reclamo.buscarReclamo(numeroReclamo);
	}

	private Usuario buscarUsuario(int numUsuario) {
		return Usuario.buscarPorId(numUsuario);
	}

	private Cliente buscarCliente(int numCliente) {
		return null;
	}

	private Producto buscarProducto(int cod_producto) {
		return null;
	}

	public Collection<ReclamoDTO> getReclamosSimples() {
		Collection<ReclamoDTO> reclamosView = new ArrayList<>();
		for (Reclamo reclamo : Reclamo.buscarReclamos()) {
			if(reclamo.getTipoReclamo()!=null){
				if(!reclamo.getTipoReclamo().equals("compuesto")){
					ReclamoDTO reclamoV = new ReclamoDTO(reclamo.getNumero(), reclamo.getDescripcion(),
							reclamo.getTipoReclamo().getDescripcionTipo(), reclamo.isEstaSolucionado());
					reclamosView.add(reclamoV);
				}
			}
		}
		return reclamosView;
	}

	public void setReclamos(Collection<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void setClientes(Collection<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public int getClienteDeFactura(int idFactura){
		return Factura.obtenerCliente(idFactura);
	}
	
	public int buscarClientePorDni(int dni){
		return Cliente.buscarPorDni(dni).getCodigo_cliente();
	}

	public List<ClienteDTO> getClientesMayorCantReclamos(){
		return Cliente.getRankingClientesPorCantidadReclamos();
	}
	
	public List<ReclamoTPromXOperadorDTO> getTiemposPromedioXResponsable(){
		return Reclamo.getTiempoPromedioRespuestaPorResp();
	}
	
	public int getCantidadReclamosMes(int mes) {
		return Reclamo.getCantidadReclamosPorMes(mes);
	}
	
	public Map<String, Long> getRankingTratamientoReclamos(){
		return Reclamo.getRankingTratamientoReclamos();
	}

	public Reclamo getUltimoReclamo() {
		return Reclamo.getUltimoReclamo();
	}
	
	public void altaUsuario(UsuarioDTO dto) throws UsuarioExistenteException {
		Usuario usu = new Usuario(dto);
		usu.insert();
	}
	
	public void modificarUsuario(UsuarioDTO dto) {
		Usuario usu = new Usuario(dto);
		usu.update();
	}
	
	public void eliminarUsuario(UsuarioDTO dto) {
		Usuario usu = new Usuario(dto);
		usu.delete();
	}
	
}