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
import ar.edu.uade.exception.ClienteNoEncontradoException;

public class VistaAdministradorCliente extends JFrame {

	private static final long serialVersionUID = 4855414156746531583L;
	
	private JTextField fieldDni;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	
	public static void main(String[] args) {
		VistaAdministradorCliente vista = new VistaAdministradorCliente();
		vista.setVisible(true);
	}
	
	public VistaAdministradorCliente() {
		initGUI();
	}
	
	private void initGUI() {
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setTitle("Administrador Clientes");
		this.setBounds(100, 100, 265, 160);
		this.getContentPane().setLayout(null);
		
		JLabel dniLbl = new JLabel("DNI:");
		dniLbl.setBounds(20, 10, 50, 17);
		dniLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(dniLbl);
		
		fieldDni = new JTextField();
		fieldDni.setBounds(100, 10, 145, 20);
		this.getContentPane().add(fieldDni);
		fieldDni.setColumns(10);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(20, 50, 100, 20);
		this.getContentPane().add(btnModificar);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if ( fieldDni.getText().equals(""))
						throw new CampoObligatorioException();

					int dni = Integer.parseInt(fieldDni.getText());
						
					GestionReclamos.getInstancia().verificarCliente(dni);
					JFrame vista = new VistaAltaModificacionCliente(dni);
					vista.setVisible(true);
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "El DNI debe ser numerico.");
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (ClienteNoEncontradoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(145, 50, 100, 20);
		this.getContentPane().add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if ( fieldDni.getText().equals(""))
						throw new CampoObligatorioException();

					Integer.parseInt(fieldDni.getText());
					
					ClienteDTO dto = new ClienteDTO(fieldDni.getText());
					GestionReclamos.getInstancia().eliminarCliente(dto);
					JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el cliente.");
					fieldDni.setText("");
					
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "El DNI debe ser numerico.");
				} catch (ClienteNoEncontradoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnAgregar = new JButton("Agregar Cliente");
		btnAgregar.setBounds(20, 90, 225, 20);
		this.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame v = new VistaAltaModificacionCliente();
				v.setVisible(true);
			}
		});
		
	}
}
