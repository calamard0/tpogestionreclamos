package ar.edu.uade.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ar.edu.uade.controller.GestionReclamos;
import ar.edu.uade.exception.CampoObligatorioException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;

public class VistaAdministradorUsuario extends javax.swing.JFrame {

	
	private JTextField fieldUsuario;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				VistaAdministradorUsuario window = new VistaAdministradorUsuario();
				window.setVisible(true);
			}
		});
	}
	
	public VistaAdministradorUsuario() {
		initialize();
	}
	
	private void initialize() {
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setTitle("Administrador Usuarios");
		this.setBounds(100, 100, 265, 160);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel usuarioLbl = new JLabel("Usuario:");
		usuarioLbl.setBounds(20, 10, 50, 17);
		usuarioLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(usuarioLbl);
		
		fieldUsuario = new JTextField();
		fieldUsuario.setBounds(100, 10, 145, 20);
		this.getContentPane().add(fieldUsuario);
		fieldUsuario.setColumns(10);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(20, 50, 100, 20);
		this.getContentPane().add(btnModificar);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if ( fieldUsuario.getText().equals(""))
						throw new CampoObligatorioException();
					
					GestionReclamos.getInstancia().getUsuario(fieldUsuario.getText());
					JFrame vistaAltaUsuario = new VistaAltaModificacionUsuario(fieldUsuario.getText());
					vistaAltaUsuario.setVisible(true);
				} catch (UsuarioNoEncontradoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(145, 50, 100, 20);
		this.getContentPane().add(btnEliminar);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// eliminar usuario
			}
		});
		
		btnAgregar = new JButton("Agregar Usuario");
		btnAgregar.setBounds(20, 90, 225, 20);
		this.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame vistaAltaUsuario = new VistaAltaModificacionUsuario();
				vistaAltaUsuario.setVisible(true);
			}
		});
		
	}
	
}
