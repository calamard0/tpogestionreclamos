package ar.edu.uade.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import ar.edu.uade.dao.ClienteDAO;
import ar.edu.uade.dao.UsuarioDAO;
import ar.edu.uade.dto.ClienteDTO;
import ar.edu.uade.exception.ClienteExistenteException;
import ar.edu.uade.exception.ClienteNoEncontradoException;
import ar.edu.uade.exception.UsuarioExistenteException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;


public class Cliente implements IPersistible {
    private int codigo_cliente;
    private String nombre;
    private int dni;
    private String domicilio;
    private String telefono;
    private String mail;
    private Collection<Reclamo> reclamos;
    private Usuario usuarioCreador;

    public Cliente(int codigo, String nombre, int dni, String dmicilio, String telefono, String mail) {
        codigo_cliente = codigo;
        this.nombre = nombre;
        this.dni = dni;
        this.domicilio = dmicilio;
        this.telefono = telefono;
        this.mail = mail;
        reclamos = new ArrayList<>();
    }
    
    public Cliente(ClienteDTO dto) {
        this.nombre = dto.getNombre();
        this.dni = Integer.parseInt(dto.getDni());
        this.domicilio = dto.getDomicilio();
        this.telefono = dto.getTelefono();
        this.mail = dto.getMail();
    }
    
    public Cliente() {
    	
    }

	public int getCantReclamos() {
        return reclamos.size();
    }

    private void agregarReclamo(Reclamo reclamo) {
        reclamos.add(reclamo);
    }

    private void removerReclamo(Reclamo reclamo) {
        reclamos.remove(reclamo);
    }

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Collection<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(Collection<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    
    public static Cliente buscarPorCodigo(int codigoCliente) {
    	Cliente clienteEncontrado = ClienteDAO.getInstancia().buscarCliente(codigoCliente);
    	return clienteEncontrado;
    }
    
    public static Vector<Cliente> obtenerTodos() {
    	Vector<Cliente> clientes = ClienteDAO.getInstancia().selectAll();
    	return clientes;
    }

	public static Cliente buscarPorDni(int dni) {
		Cliente clienteEncontrado = ClienteDAO.getInstancia().buscarPorDni(dni);
    	return clienteEncontrado;
	}
	
	/*ranking de clientes con mayor reclamos*/
	public static List<ClienteDTO> getRankingClientesPorCantidadReclamos(){
		
		List<Vector> lista = ClienteDAO.getInstancia().getClientesOrdenadosPorCantidadReclamoDesc();
		List<ClienteDTO> listaView = new ArrayList<ClienteDTO>();
		for(Vector obj : lista){
			String dni = String.valueOf(obj.get(0));
			String nombre = (String) obj.get(1);
			int cant = Integer.valueOf(String.valueOf(obj.get(2)));
			String mail = (String) obj.get(3);
			ClienteDTO view = new ClienteDTO(dni, nombre, String.valueOf(cant), mail);
			listaView.add(view);
		}
		return listaView;
	}
	

	@Override
	public void insert() throws ClienteExistenteException {
		Cliente cl = ClienteDAO.getInstancia().buscarPorDni(this.getDni());
		if (cl != null)
			throw new ClienteExistenteException();
		
		ClienteDAO.getInstancia().insert(this);
	}
	
	@Override
	public void update() {
		ClienteDAO.getInstancia().update(this);
	}
	
	@Override
	public void delete() throws ClienteNoEncontradoException {
		Cliente cl = ClienteDAO.getInstancia().buscarPorDni(this.getDni());
		if (cl != null)
			ClienteDAO.getInstancia().delete(this);
		else
			throw new ClienteNoEncontradoException("No existe el cliente con el dni: " + this.getDni());
	}

}
