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
import ar.edu.uade.exception.ClienteNoEncontradoException;
import ar.edu.uade.exception.ProductoNoExistenteException;

public class VistaAdministradorProducto extends JFrame {

	private static final long serialVersionUID = 4855414156746531583L;
	
	private JTextField fieldCodigo;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	
	public static void main(String[] args) {
		VistaAdministradorProducto vista = new VistaAdministradorProducto();
		vista.setVisible(true);
	}
	
	public VistaAdministradorProducto() {
		initGUI();
	}
	
	private void initGUI() {
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setTitle("Administrador Productos");
		this.setBounds(100, 100, 265, 160);
		this.getContentPane().setLayout(null);
		
		JLabel codigoLbl = new JLabel("CODIGO:");
		codigoLbl.setBounds(20, 10, 50, 17);
		codigoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(codigoLbl);
		
		fieldCodigo = new JTextField();
		fieldCodigo.setBounds(100, 10, 145, 20);
		this.getContentPane().add(fieldCodigo);
		fieldCodigo.setColumns(10);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(20, 50, 100, 20);
		this.getContentPane().add(btnModificar);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if ( fieldCodigo.getText().equals(""))
						throw new CampoObligatorioException();

					int codigo = Integer.parseInt(fieldCodigo.getText());
					GestionReclamos.getInstancia().verificarProducto(codigo);
					JFrame vista = new VistaAltaModificacionProducto(codigo);
					vista.setVisible(true);
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "El CODIGO debe ser numerico.");
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (ProductoNoExistenteException e) {
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
					if ( fieldCodigo.getText().equals(""))
						throw new CampoObligatorioException();

					int codigo = Integer.parseInt(fieldCodigo.getText());
					ProductoDTO dto = new ProductoDTO(codigo);
					GestionReclamos.getInstancia().eliminarProducto(dto);
					JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el producto.");
					fieldCodigo.setText("");
					
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "El Codigo debe ser numerico.");
				} catch (ProductoNoExistenteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnAgregar = new JButton("Agregar Producto");
		btnAgregar.setBounds(20, 90, 225, 20);
		this.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame v = new VistaAltaModificacionProducto();
				v.setVisible(true);
			}
		});
		
	}
}
