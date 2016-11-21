package ar.edu.uade.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ar.edu.uade.controller.GestionReclamos;
import ar.edu.uade.dto.ClienteDTO;
import ar.edu.uade.dto.ProductoDTO;
import ar.edu.uade.exception.CampoObligatorioException;
import ar.edu.uade.exception.ClienteExistenteException;


public class VistaAltaModificacionProducto extends JFrame {

	private static final long serialVersionUID = 5609999422627929103L;
	private JTextField fieldCodigo;
	private JTextField fieldTitulo;
	private JTextField fieldDescripcion;
	private JTextField fieldPrecio;
	
	private JButton btnGrabar;
	private JButton btnModificar;
	
	public static void main(String[] args) {
		VistaAltaModificacionProducto vista = new VistaAltaModificacionProducto();
		vista.setVisible(true);
	}
	
	public VistaAltaModificacionProducto() {
		initGUI();
	}
	
	public VistaAltaModificacionProducto(int codigo) {
		initGUI();
		cargarDatosProducto(codigo);
	}
	
	private void initGUI() {
		//frmLogin = new JFrame();
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setTitle("Alta Cliente");
		this.setBounds(200, 200, 260, 300);
		this.getContentPane().setLayout(null);
		
		JLabel nombreLbl = new JLabel("Nombre:");
		nombreLbl.setBounds(20, 10, 70, 17);
		nombreLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(nombreLbl);
		
		fieldCodigo = new JTextField();
		fieldCodigo.setBounds(100, 10, 139, 20);
		this.getContentPane().add(fieldCodigo);
		fieldCodigo.setColumns(10);
		
		JLabel dniLbl = new JLabel("DNI:");
		dniLbl.setBounds(20, 50, 50, 17);
		dniLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(dniLbl);
		
		fieldTitulo = new JTextField();
		fieldTitulo.setBounds(100, 50, 139, 20);
		this.getContentPane().add(fieldTitulo);
		fieldTitulo.setColumns(10);
		
		JLabel domicilioLbl = new JLabel("Domicilio:");
		domicilioLbl.setBounds(20, 90, 70, 17);
		domicilioLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(domicilioLbl);
		
		fieldDescripcion = new JTextField();
		fieldDescripcion.setBounds(100, 90, 139, 20);
		this.getContentPane().add(fieldDescripcion);
		fieldDescripcion.setColumns(10);
		
		JLabel telefonoLbl = new JLabel("Telefono:");
		telefonoLbl.setBounds(20, 130, 70, 17);
		telefonoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(telefonoLbl);
		
		fieldPrecio = new JTextField();
		fieldPrecio.setBounds(100, 130, 139, 20);
		this.getContentPane().add(fieldPrecio);
		fieldPrecio.setColumns(10);
		
		JLabel mailLbl = new JLabel("Mail:");
		mailLbl.setBounds(20, 170, 50, 17);
		mailLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(mailLbl);
		
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios();
					
					ClienteDTO dto = new ClienteDTO(fieldTitulo.getText(), fieldCodigo.getText(), fieldDescripcion.getText(), fieldPrecio.getText());
					GestionReclamos.getInstancia().altaCliente(dto);
					JOptionPane.showMessageDialog(null, "Se ha agregado correctamente el cliente.");
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (ClienteExistenteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnGrabar.setBounds(20, 210, 110, 23);
		this.getContentPane().add(btnGrabar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios();
					ProductoDTO dto = new ProductoDTO(Integer.parseInt(fieldCodigo.getText(), fieldTitulo.getText(),fieldDescripcion.getText(), Float.parseFloat(fieldPrecio.getText());
					GestionReclamos.getInstancia().modificarCliente(dto);
					JOptionPane.showMessageDialog(null, "Se han modificado correctamente los datos del cliente.");
					
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnModificar.setBounds(130, 210, 110, 23);
		this.getContentPane().add(btnModificar);
		btnModificar.setEnabled(false);
	}
	
	private void cargarDatosProducto(int dni) {
		try {
			ClienteDTO dto = GestionReclamos.getInstancia().getCliente(dni);
	        
			fieldCodigo.setText(dto.getNombre());
			fieldTitulo.setText(dto.getDni());
			fieldDescripcion.setText(dto.getDomicilio());
			fieldPrecio.setText(dto.getTelefono());
			fieldMail.setText(dto.getMail());
            
            btnGrabar.setEnabled(false);
            btnModificar.setEnabled(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void validarCamposObligatorios() throws CampoObligatorioException {
		if ( fieldCodigo.getText().equals("") )
			throw new CampoObligatorioException();
		
		try {
			Integer.parseInt(fieldCodigo.getText());
		} catch (NumberFormatException e) {
			throw new CampoObligatorioException("El Codigo debe ser numerico.");
		}
		
		if ( fieldDescripcion.getText().equals("") )
			throw new CampoObligatorioException();
		
		
		if ( fieldPrecio.getText().equals("") )
			throw new CampoObligatorioException();
	}
	
	
}
