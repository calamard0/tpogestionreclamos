package ar.edu.uade.view;

import javax.swing.*;

import ar.edu.uade.controller.GestionReclamos;
import ar.edu.uade.dto.ClienteDTO;
import ar.edu.uade.dto.UsuarioDTO;
import ar.edu.uade.enums.TipoReclamo;
import ar.edu.uade.exception.UsuarioNoEncontradoException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaNuevoReclamoZona extends JFrame {

	private static final long serialVersionUID = 2679709586176230119L;
	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textField;

    private JComboBox<String> comboBoxResp;
    private JComboBox<String> comboBoxClientes;
    private JTextArea textAreaDescripcion;
    
    public VistaNuevoReclamoZona(Integer codigoUsuario) {
    	this.setBounds(0, 0, 342, 195);
    	setTitle("Reclamo Zona");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);
        
        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(76, 13, 90, 20);
        for (ClienteDTO clienteView : GestionReclamos.getInstancia().getClientes()) {
            comboBoxClientes.addItem(clienteView.getDni());
        }
        getContentPane().add(comboBoxClientes);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCliente.setBounds(10, 14, 56, 14);
        getContentPane().add(lblCliente);
        
        /*Combo responsables */
        JLabel lblResponsable = new JLabel("Responsable:");
        lblResponsable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblResponsable.setBounds(10, 40, 81, 14);
        getContentPane().add(lblResponsable);   
        
        comboBoxResp = new JComboBox<>();
        comboBoxResp.setBounds(101, 39, 90, 20);
        
        List<UsuarioDTO> lista = GestionReclamos.getInstancia().getUsuariosResponsables(TipoReclamo.ZONA.toString());
        for (UsuarioDTO usuarioView : lista) {
        	comboBoxResp.addItem(usuarioView.getUsuario());
        }
        getContentPane().add(comboBoxResp);
        
        /*Fin combo responsables*/
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 122, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
	                GestionReclamos.getInstancia().crearReclamoZona(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())), 
	                		textField.getText(), textAreaDescripcion.getText(), String.valueOf(comboBoxResp.getSelectedItem()));
	                JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
	                textField.setText("");
	                setVisible(false);
	            } catch (UsuarioNoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
            }
        });
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textField.setText("");
                setVisible(false);
            }
        });
        btnCancelar.setBounds(227, 122, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(176, 16, 44, 14);
        getContentPane().add(lblZona);
        
        textField = new JTextField();
        textField.setBounds(230, 13, 86, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDescripcion.setBounds(10, 63, 75, 14);
        getContentPane().add(lblDescripcion);
        
        JScrollPane scrollPaneDescripcion = new JScrollPane();
        scrollPaneDescripcion.setBounds(95, 68, 221, 43);
        getContentPane().add(scrollPaneDescripcion);
        
        textAreaDescripcion = new JTextArea();
        scrollPaneDescripcion.setViewportView(textAreaDescripcion);

    }

}
