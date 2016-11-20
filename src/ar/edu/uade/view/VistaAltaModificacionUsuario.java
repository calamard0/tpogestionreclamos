package ar.edu.uade.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ar.edu.uade.controller.GestionReclamos;
import ar.edu.uade.dto.UsuarioDTO;
import ar.edu.uade.enums.EnumRoles;
import ar.edu.uade.exception.CampoObligatorioException;
import ar.edu.uade.exception.UsuarioExistenteException;
import ar.edu.uade.exception.UsuarioNoEncontradoException;

public class VistaAltaModificacionUsuario extends JFrame {
	
	private static final long serialVersionUID = 5812760100472581803L;
	private JTextField fieldUsuario;
	private JLabel lblContrasenia;
	private JPasswordField passFieldContrasenia;
	private JTextField fieldNombre;
	private JTextField fieldApellido;
	private JComboBox<String> comboBoxRoles;
	private JTable tableRoles;
	private TableModel model;
	private JButton btnEliminar;
	private JButton btnAceptar;
	private JButton btnModificar;
	private int codigoUsuarioModificando;
	
	private Vector<String> dataRoles = new Vector<>();
	private Vector<Vector<String>> data =  new Vector<>();
	
	public VistaAltaModificacionUsuario() {
		initGUI();
	}
	
	public VistaAltaModificacionUsuario(String usuario) {
		initGUI();
		cargarDatosUsuario(usuario);
	}
	
	private void initGUI() {
		//frmLogin = new JFrame();
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setTitle("Alta Usuario");
		this.setBounds(200, 200, 333, 425);
		this.getContentPane().setLayout(null);
		
		JLabel usuarioLbl = new JLabel("Usuario:");
		usuarioLbl.setBounds(23, 12, 50, 17);
		usuarioLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(usuarioLbl);
		
		fieldUsuario = new JTextField();
		fieldUsuario.setBounds(108, 11, 139, 20);
		this.getContentPane().add(fieldUsuario);
		fieldUsuario.setColumns(10);
		
		lblContrasenia = new JLabel("Contrase\u00F1a:");
		lblContrasenia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasenia.setBounds(23, 52, 75, 14);
		this.getContentPane().add(lblContrasenia);
		
		passFieldContrasenia = new JPasswordField();
		passFieldContrasenia.setBounds(108, 51, 139, 20);
		this.getContentPane().add(passFieldContrasenia);
		passFieldContrasenia.setColumns(10);
		
		JLabel nombreLbl = new JLabel("Nombre:");
		nombreLbl.setBounds(23, 91, 75, 17);
		nombreLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(nombreLbl);
		
		fieldNombre = new JTextField();
		fieldNombre.setBounds(108, 91, 139, 20);
		this.getContentPane().add(fieldNombre);
		fieldNombre.setColumns(10);
		
		JLabel apellidoLbl = new JLabel("Apellido:");
		apellidoLbl.setBounds(23, 130, 50, 17);
		apellidoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(apellidoLbl);
		
		fieldApellido = new JTextField();
		fieldApellido.setBounds(108, 130, 139, 20);
		this.getContentPane().add(fieldApellido);
		fieldApellido.setColumns(10);
		
		JLabel rolesLbl = new JLabel("Roles:");
		rolesLbl.setBounds(23, 170, 50, 17);
		rolesLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(rolesLbl);
		
		comboBoxRoles = new JComboBox<>();
		comboBoxRoles.setBounds(108, 170, 139, 20);
        for (EnumRoles rol : GestionReclamos.getInstancia().getRoles()) {
        	comboBoxRoles.addItem(rol.toString());
        }
		this.getContentPane().add(comboBoxRoles);
		
		//Columnas de la tabla
        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("Rol");
		
		JButton btnAgregar = new JButton("+");
		btnAgregar.setBounds(260, 170, 50, 20);
		this.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
            	boolean error = false;
                for(int i=0;i<tableRoles.getModel().getRowCount();i++){
                    if(tableRoles.getModel().getValueAt(i, 0) == String.valueOf(comboBoxRoles.getSelectedItem())){
                  	  JOptionPane.showMessageDialog(null, "Ya se ha agregado ese rol.");
                  	  error = true;
                    }
                  }
                if(!error){
                	dataRoles.add(String.valueOf(comboBoxRoles.getSelectedItem()));
	                data.add(dataRoles);
	                dataRoles = new Vector<>();
	                model = new DefaultTableModel(data, nombresColumnas);
	                TableModelEvent tableModelEvent = new TableModelEvent(model);
	                tableRoles.tableChanged(tableModelEvent);
                }
			}
		});
		
		btnEliminar = new JButton("-");
        btnEliminar.setBounds(260, 210, 50, 20);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DefaultTableModel modelo = (DefaultTableModel)tableRoles.getModel(); 
            	modelo.removeRow(tableRoles.getSelectedRow()); 
            }
        });
        getContentPane().add(btnEliminar);
		
		JScrollPane scrollPaneFacturas = new JScrollPane();
        scrollPaneFacturas.setBounds(23, 210, 225, 70);
        getContentPane().add(scrollPaneFacturas);
		
		model = new DefaultTableModel(data, nombresColumnas);
		tableRoles = new JTable(model);
		tableRoles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int c = 0; c < tableRoles.getColumnCount(); c++)
        {
            Class<?> col_class = tableRoles.getColumnClass(c);
            tableRoles.setDefaultEditor(col_class, null);        // remove editor
        }
        scrollPaneFacturas.setViewportView(tableRoles);
        ListSelectionModel listSelectionModel = tableRoles.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                btnEliminar.setEnabled(!lsm.isSelectionEmpty());
            }
        });
		
		btnAceptar = new JButton("Grabar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios(false);
					ArrayList<EnumRoles> roles = obtenerRoles();
					
					UsuarioDTO dto = new UsuarioDTO(fieldNombre.getText(), fieldApellido.getText(), -1, fieldUsuario.getText(), roles, passFieldContrasenia.getText());
					GestionReclamos.getInstancia().altaUsuario(dto);
					JOptionPane.showMessageDialog(null, "Se ha agregado correctamente el usuario.");
				} catch (CampoObligatorioException | UsuarioExistenteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnAceptar.setBounds(23, 300, 110, 23);
		this.getContentPane().add(btnAceptar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					validarCamposObligatorios(true);
					ArrayList<EnumRoles> roles = obtenerRoles();
					
					UsuarioDTO dto = new UsuarioDTO(fieldNombre.getText(), fieldApellido.getText(), codigoUsuarioModificando, fieldUsuario.getText(), roles, passFieldContrasenia.getText());
					GestionReclamos.getInstancia().modificarUsuario(dto);
					JOptionPane.showMessageDialog(null, "Se han modificado correctamente los datos del usuario.");
					
				} catch (CampoObligatorioException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnModificar.setBounds(200, 300, 110, 23);
		this.getContentPane().add(btnModificar);
		btnModificar.setEnabled(false);
	}
	
	private void cargarDatosUsuario(String usuario) {
		try {
			UsuarioDTO dto = GestionReclamos.getInstancia().getUsuario(usuario);
			
			Vector<String> nombresColumnas = new Vector<>();
	        nombresColumnas.add("Rol");
	        
	        this.setTitle("Modificacion Usuario");
			fieldUsuario.setText(dto.getUsuario());
			fieldUsuario.setEnabled(false);
			passFieldContrasenia.setText(dto.getClave());
			passFieldContrasenia.setEnabled(false);
			fieldNombre.setText(dto.getNombre());
			fieldApellido.setText(dto.getApellido());
			
			for (EnumRoles rol : dto.getRoles()) {
				dataRoles.add(String.valueOf(rol.toString()));
				data.add(dataRoles);
				dataRoles = new Vector<>();
			}
            
            //dataRoles = new Vector<>();
            model = new DefaultTableModel(data, nombresColumnas);
            TableModelEvent tableModelEvent = new TableModelEvent(model);
            tableRoles.tableChanged(tableModelEvent);
            
            btnAceptar.setEnabled(false);
            btnModificar.setEnabled(true);
            codigoUsuarioModificando = dto.getCodigo();
			
		} catch (UsuarioNoEncontradoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void validarCamposObligatorios(boolean esModificacion ) throws CampoObligatorioException {
		if (! esModificacion) {
			if ( fieldUsuario.getText().equals("") )
				throw new CampoObligatorioException();
			
			if ( passFieldContrasenia.getText().equals("") )
				throw new CampoObligatorioException();
		}
		
		if ( fieldNombre.getText().equals("") )
			throw new CampoObligatorioException();
		
		if ( fieldApellido.getText().equals("") )
			throw new CampoObligatorioException();
		
		if ( data.size() == 0 )
			throw new CampoObligatorioException("No ha seleccionado ningpun rol para el usuario.");
	}
	
	private ArrayList<EnumRoles> obtenerRoles() {
		ArrayList<EnumRoles> roles = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			roles.add(EnumRoles.valueOf(data.elementAt(i).elementAt(0)));
		}
		return roles;
	}
}
