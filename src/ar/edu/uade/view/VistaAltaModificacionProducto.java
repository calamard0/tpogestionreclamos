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
import ar.edu.uade.exception.ProductoExistenteException;
import ar.edu.uade.exception.ProductoNoExistenteException;


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
		this.setTitle("Alta Producto");
		this.setBounds(200, 200, 260, 300);
		this.getContentPane().setLayout(null);
		
		JLabel codigoLbl = new JLabel("CODIGO:");
		codigoLbl.setBounds(20, 10, 70, 17);
		codigoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(codigoLbl);
		
		fieldCodigo = new JTextField();
		fieldCodigo.setBounds(100, 10, 139, 20);
		this.getContentPane().add(fieldCodigo);
		fieldCodigo.setColumns(10);
		
		JLabel tituloLbl = new JLabel("TITULO:");
		tituloLbl.setBounds(20, 50, 50, 17);
		tituloLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(tituloLbl);
		
		fieldTitulo = new JTextField();
		fieldTitulo.setBounds(100, 50, 139, 20);
		this.getContentPane().add(fieldTitulo);
		fieldTitulo.setColumns(10);
		
		JLabel descripcionLbl = new JLabel("DESCRIPCION:");
		descripcionLbl.setBounds(20, 90, 70, 17);
		descripcionLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(descripcionLbl);
		
		fieldDescripcion = new JTextField();
		fieldDescripcion.setBounds(100, 90, 139, 20);
		this.getContentPane().add(fieldDescripcion);
		fieldDescripcion.setColumns(10);
		
		JLabel precioLbl = new JLabel("Precio:");
		precioLbl.setBounds(20, 130, 70, 17);
		precioLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(precioLbl);
		
		fieldPrecio = new JTextField();
		fieldPrecio.setBounds(100, 130, 139, 20);
		this.getContentPane().add(fieldPrecio);
		fieldPrecio.setColumns(10);
		
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios();
					ProductoDTO dto = new ProductoDTO(Integer.parseInt(fieldCodigo.getText()), fieldTitulo.getText(),fieldDescripcion.getText(), Float.parseFloat(fieldPrecio.getText()));
					GestionReclamos.getInstancia().altaProducto(dto);
					JOptionPane.showMessageDialog(null, "Se ha agregado correctamente el producto.");
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (ProductoExistenteException  e) {
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
					ProductoDTO dto = new ProductoDTO(Integer.parseInt(fieldCodigo.getText()), fieldTitulo.getText(),fieldDescripcion.getText(), Float.parseFloat(fieldPrecio.getText()));
					
					GestionReclamos.getInstancia().modificarProducto(dto);
					JOptionPane.showMessageDialog(null, "Se han modificado correctamente los datos del producto.");
					
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (ProductoNoExistenteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnModificar.setBounds(130, 210, 110, 23);
		this.getContentPane().add(btnModificar);
		btnModificar.setEnabled(false);
	}
	
	private void cargarDatosProducto(int codigo) {
		try {
			ProductoDTO dto = GestionReclamos.getInstancia().getProducto(codigo);
	        
			fieldCodigo.setText(Integer.valueOf(dto.getCodigo()).toString());
			fieldTitulo.setText(dto.getTitulo());
			fieldDescripcion.setText(dto.getDescripcion());
			fieldPrecio.setText(Float.valueOf(dto.getPrecio()).toString());
            
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
