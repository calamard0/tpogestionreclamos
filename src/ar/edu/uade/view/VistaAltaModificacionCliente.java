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
import ar.edu.uade.exception.CampoObligatorioException;
import ar.edu.uade.exception.ClienteExistenteException;


public class VistaAltaModificacionCliente extends JFrame {

	private static final long serialVersionUID = 5609999422627929103L;
	private JTextField fieldNombre;
	private JTextField fieldDni;
	private JTextField fieldDomicilio;
	private JTextField fieldTelefono;
	private JTextField fieldMail;
	private ClienteDTO clienteAModificar;
	
	private JButton btnGrabar;
	private JButton btnModificar;
	
	public VistaAltaModificacionCliente() {
		initGUI();
	}
	
	public VistaAltaModificacionCliente(int dni) {
		initGUI();
		cargarDatosCliente(dni);
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
		
		fieldNombre = new JTextField();
		fieldNombre.setBounds(100, 10, 139, 20);
		this.getContentPane().add(fieldNombre);
		fieldNombre.setColumns(10);
		
		JLabel dniLbl = new JLabel("DNI:");
		dniLbl.setBounds(20, 50, 50, 17);
		dniLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(dniLbl);
		
		fieldDni = new JTextField();
		fieldDni.setBounds(100, 50, 139, 20);
		this.getContentPane().add(fieldDni);
		fieldDni.setColumns(10);
		
		JLabel domicilioLbl = new JLabel("Domicilio:");
		domicilioLbl.setBounds(20, 90, 70, 17);
		domicilioLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(domicilioLbl);
		
		fieldDomicilio = new JTextField();
		fieldDomicilio.setBounds(100, 90, 139, 20);
		this.getContentPane().add(fieldDomicilio);
		fieldDomicilio.setColumns(10);
		
		JLabel telefonoLbl = new JLabel("Telefono:");
		telefonoLbl.setBounds(20, 130, 70, 17);
		telefonoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(telefonoLbl);
		
		fieldTelefono = new JTextField();
		fieldTelefono.setBounds(100, 130, 139, 20);
		this.getContentPane().add(fieldTelefono);
		fieldTelefono.setColumns(10);
		
		JLabel mailLbl = new JLabel("Mail:");
		mailLbl.setBounds(20, 170, 50, 17);
		mailLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(mailLbl);
		
		fieldMail = new JTextField();
		fieldMail.setBounds(100, 170, 139, 20);
		this.getContentPane().add(fieldMail);
		fieldMail.setColumns(10);
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios();
					
					ClienteDTO dto = new ClienteDTO(fieldDni.getText(), fieldNombre.getText(), fieldMail.getText(), fieldDomicilio.getText(), fieldTelefono.getText(), -1);
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
					
					ClienteDTO dto = new ClienteDTO(fieldDni.getText(), fieldNombre.getText(), fieldMail.getText(), fieldDomicilio.getText(), fieldTelefono.getText(), clienteAModificar.getCodigo());
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
	
	private void cargarDatosCliente(int dni) {
		try {
			ClienteDTO dto = GestionReclamos.getInstancia().getCliente(dni);
			clienteAModificar = dto;
			
			fieldNombre.setText(dto.getNombre());
			fieldDni.setText(dto.getDni());
			fieldDomicilio.setText(dto.getDomicilio());
			fieldTelefono.setText(dto.getTelefono());
			fieldMail.setText(dto.getMail());
            
            btnGrabar.setEnabled(false);
            btnModificar.setEnabled(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void validarCamposObligatorios() throws CampoObligatorioException {
		if ( fieldDni.getText().equals("") )
			throw new CampoObligatorioException();
		
		try {
			Integer.parseInt(fieldDni.getText());
		} catch (NumberFormatException e) {
			throw new CampoObligatorioException("El DNI debe ser numerico.");
		}
		
		if ( fieldDomicilio.getText().equals("") )
			throw new CampoObligatorioException();
		
		if ( fieldMail.getText().equals("") )
			throw new CampoObligatorioException();
		
		if ( fieldNombre.getText().equals("") )
			throw new CampoObligatorioException();
		
		if ( fieldTelefono.getText().equals("") )
			throw new CampoObligatorioException();
	}
	
	
}
