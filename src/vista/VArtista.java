package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import exception.AltaException;
import main.Principal;
import modelo.Artista;
import modelo.Tipo;

public class VArtista extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton volverButton, btnAnadir;
	private JTextField textFieldID;
	private JTextField textFieldNombre;
	private ButtonGroup soloGrupo = new ButtonGroup();

	/**
	 * Launch the application.
	 *
	 * public static void main(String[] args) { try { VArtista dialog = new
	 * VArtista(); dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * /** Create the dialog
	 * 
	 * @param vAlta
	 * @param vAlta
	 */

	public VArtista(boolean modal) {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Introduce el nombre del artista o el grupo");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 82, 349, 25);
		contentPanel.add(lblNewLabel);

		JRadioButton rdbtnSolo = new JRadioButton("Solo");
		rdbtnSolo.setBounds(10, 205, 103, 21);
		contentPanel.add(rdbtnSolo);
		soloGrupo.add(rdbtnSolo);

		JRadioButton rdbtnGrupo = new JRadioButton("Grupo");
		rdbtnGrupo.setBounds(190, 205, 103, 21);
		contentPanel.add(rdbtnGrupo);
		soloGrupo.add(rdbtnGrupo);

		JLabel lblIntroduceElId = new JLabel("Introduce el ID");
		lblIntroduceElId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIntroduceElId.setBounds(10, 10, 349, 25);
		contentPanel.add(lblIntroduceElId);

		textFieldID = new JTextField();
		textFieldID.setBounds(10, 39, 96, 19);
		contentPanel.add(textFieldID);
		textFieldID.setColumns(10);

		JLabel lblIntroduceElTipo = new JLabel("Introduce el tipo");
		lblIntroduceElTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIntroduceElTipo.setBounds(10, 159, 349, 25);
		contentPanel.add(lblIntroduceElTipo);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(10, 117, 96, 19);
		contentPanel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				volverButton = new JButton("Volver");
				volverButton.addActionListener(this);

				btnAnadir = new JButton("Añadir");
				btnAnadir.addActionListener(this);
				buttonPane.add(btnAnadir);
				volverButton.setActionCommand("Cancel");
				buttonPane.add(volverButton);

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(volverButton)) {
			this.dispose();

		} else if (e.getSource() == btnAnadir) {
			// COMPROBAR QUE LOS CAMPOS A RELLENAR NO ESTEN VACIOS
			if (textFieldID.getText().isEmpty() || textFieldNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Campos incompletos",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			// VERIFICAMOS QUE EL ID NO SE REPITA Y QUE SEA UN NUMERO
			String txt = textFieldID.getText().trim();
			int id = 0;
			if (esNumero(txt)) {
				id = Integer.parseInt(txt);
			} else {

				JOptionPane.showMessageDialog(this, "Error", "El ID debe ser un número",
						JOptionPane.INFORMATION_MESSAGE);
			}

			try {
				if (comprobar(id)) {
					JOptionPane.showMessageDialog(this, "El id introducido ya existe", "Error en ID",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (AltaException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Fallo al comprobar id", "Error", JOptionPane.INFORMATION_MESSAGE);
			}

			// COGEMOS EL NOMBRE QUE HAYA PUESTO
			String nom = textFieldNombre.getText();
			try {
				if (comprobarNombre(nom)) {
					JOptionPane.showMessageDialog(this, "Ya existe un artista con ese nombre", "Error en el nombre",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (AltaException e1) {
				JOptionPane.showMessageDialog(this, "Fallo al comprobar nombre", "Error en el nombre",
						JOptionPane.INFORMATION_MESSAGE);

			}
			String sOg = null;
			// MIRAMOS SI HA SLEECCIONADO SOLO O GRUPO
			Enumeration<AbstractButton> elementos = soloGrupo.getElements();
			
			boolean tipoSele=false;
			while (elementos.hasMoreElements()) {
				AbstractButton boton = elementos.nextElement();
				if (boton.isSelected()) {
					sOg = boton.getText().toUpperCase();
					tipoSele=true;

				}

				// System.out.println("Botón: " + boton.getText() + ", Seleccionado: " +
				// boton.isSelected());

			}
			
			if(!tipoSele){
				JOptionPane.showMessageDialog(this, "Debes seleccionar su tipo", "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
				
			}
			Tipo t = Tipo.valueOf(sOg);
			Artista arti = new Artista();
			arti.setId(id);
			arti.setNombre(nom);
			arti.setTipo(t);

			boolean exito = false;
			// AÑADIMSO A LA BASE DE DATOS
			try {
				exito = Principal.alta(arti);
			} catch (AltaException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Fallo al añadir artista", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}

			if (exito) {
				JOptionPane.showMessageDialog(this, "Se ha añadido exitosamente este artista", "Exito",
						JOptionPane.INFORMATION_MESSAGE);
				 textFieldID.setText("");
				    textFieldNombre.setText("");
				    soloGrupo.clearSelection();

			} else {
				JOptionPane.showMessageDialog(this, "Fallo al añadir artista", "Error",
						JOptionPane.INFORMATION_MESSAGE);

			}
			

		}

	}

	private boolean comprobarNombre(String nombre) throws AltaException {
		ArrayList<String> nombres = Principal.leerNombreArti();

		for (int i = 0; i < nombres.size(); i++) {
			if (nombres.get(i).equals(nombre)) {
				return true;
			}

		}

		return false;
	}

	private boolean comprobar(int id) throws AltaException {
		ArrayList<Integer> idArtista = Principal.leerIds();
		for (int i = 0; i < idArtista.size(); i++) {
			if (idArtista.get(i).equals(id)) {
				return true;
			}

		}

		return false;

	}

	private boolean esNumero(String texto) {
		try {
			Integer.parseInt(texto.trim());
			return true; // Es un número válido
		} catch (NumberFormatException e) {
			return false; // No es un número
		}
	}
}
