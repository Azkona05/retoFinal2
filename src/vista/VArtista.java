package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import exception.AltaException;
import main.Principal;
import modelo.Artista;
import modelo.Tipo;

public class VArtista extends JDialog implements ActionListener {
	/**
	 * @author Ricardo Soza
	 */

	private static final long serialVersionUID = 1L;

	private JButton volverButton;
	private JButton btnAnadir;
	private JTextField textFieldID;
	private JTextField textFieldNombre;
	private ButtonGroup soloGrupo = new ButtonGroup();
	private JRadioButton rdbtnSolo;
	private JRadioButton rdbtnGrupo;

	/**
	 * @param modal indica si el diálogo es modal (true) o no modal (false). Si es
	 *              modal, bloquea la interacción con la ventana padre mientras está
	 *              abierto.
	 */
	public VArtista(VAlta padre, boolean modal) {
		super(padre, modal);

		setTitle("Alta de artista");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);// Cuando se cierra la ventana, se cierra solo la ventana y
															// no termian el programa.
		setResizable(false); // evita que el usuario redimencione la ventana (no le cambie el tamaño)
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));// Establece una
																										// imagen
																										// siguiendo la
																										// ruta asignada

		// Elige los colores en base a los numeros
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color naranjaPalo = new Color(244, 162, 97);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// Este es para dar efecto de tarjeta flotante sobre el fondo
		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true), // color,grosor y
																										// que las
																										// esquinas
																										// esten
																										// redondeadas
				new EmptyBorder(20, 20, 20, 20)));// deje espacios en blanco

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		JPanel panelCabecera = new JPanel(new BorderLayout(0, 8));
		panelCabecera.setBackground(fondoTarjeta);

		JLabel lblTitulo = new JLabel("Alta de artista");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		JLabel lblSubtitulo = new JLabel("Introduce los datos del artista o grupo");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblSubtitulo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelFormulario = new JPanel(new GridBagLayout());// GridBagLayout layout mas flexible
		panelFormulario.setBackground(fondoTarjeta);

		GridBagConstraints gbc = new GridBagConstraints(); // es un layout mas flexible que permite controlar con mas
															// precision el tamaño
		gbc.insets = new Insets(8, 8, 8, 8);// Margen de 8px alrededor de cada componente
		gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
		gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente

		// Etiqueta del ID
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setForeground(colorTexto);

		// Campo del ID
		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldID.setPreferredSize(new Dimension(220, 34));// el componente tendra preferencia por este tamaño
		textFieldID.setBorder(BorderFactory.createCompoundBorder( // createCompoundBorder junta dos bordes para que el
																	// texto no este pegado al borde
				new LineBorder(new Color(210, 214, 220), 1, true), // color,grosor y que las esquinas esten redondeadas
				new EmptyBorder(5, 8, 5, 8))); // deje espacios en blanco

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setForeground(colorTexto);

		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombre.setPreferredSize(new Dimension(220, 34));
		textFieldNombre.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTipo.setForeground(colorTexto);

		JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); // Alineado a la izquierda
		panelRadios.setBackground(fondoTarjeta);

		rdbtnSolo = new JRadioButton("Solo");
		rdbtnSolo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rdbtnSolo.setBackground(fondoTarjeta);
		rdbtnSolo.setForeground(colorTexto);

		rdbtnGrupo = new JRadioButton("Grupo");
		rdbtnGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rdbtnGrupo.setBackground(fondoTarjeta);
		rdbtnGrupo.setForeground(colorTexto);

		soloGrupo.add(rdbtnSolo);
		soloGrupo.add(rdbtnGrupo);

		panelRadios.add(rdbtnSolo);
		panelRadios.add(rdbtnGrupo);

		gbc.gridx = 0;// columna 0
		gbc.gridy = 0;// fila 0
		panelFormulario.add(lblId, gbc);

		gbc.gridx = 1;// columna 1
		gbc.gridy = 0;// fila 0
		panelFormulario.add(textFieldID, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(lblNombre, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(textFieldNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(lblTipo, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(panelRadios, gbc);

		panelTarjeta.add(panelFormulario, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelInferior.setBackground(fondoTarjeta);

		volverButton = new JButton("Volver");
		volverButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		volverButton.setFocusPainted(false);
		volverButton.setBackground(new Color(240, 240, 240));
		volverButton.setForeground(colorTexto);
		volverButton.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		volverButton.addActionListener(this);

		btnAnadir = new JButton("Añadir");
		btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAnadir.setFocusPainted(false);// el boton se vera mas limpio
		btnAnadir.setBorderPainted(false);// el borde no se ve
		btnAnadir.setOpaque(true);// pinta fondo el boton si es true
		btnAnadir.setBackground(naranjaPalo);
		btnAnadir.setForeground(Color.WHITE);
		btnAnadir.addActionListener(this);

		panelInferior.add(volverButton);
		panelInferior.add(btnAnadir);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		pack();// se ajusta el tamaño de la ventana al tamaño de los componentes
				// (Jlabels,botones...)
		setLocationRelativeTo(padre);// pone la ventena encima de la anterior o padre (centrada)
	}

	/**
	 * 
	 * @param e  hace referencia al ActionEvent y dependiendo de la accion que
	 *           realize el usuario hará algo
	 * @param id el identificador del artista a comprobar
	 * @return {true} si el ID ya existe, { false} en caso contrario
	 * @throws AltaException si ocurre un error al acceder a la base de datos
	 * 
	 * 
	 * @param nombre el nombre del artista a comprobar
	 * @return {@code true} si el nombre ya existe, {@code false} en caso contrario
	 * @throws AltaException si ocurre un error al acceder a la base de datos
	 * 
	 * 
	 * @param texto la cadena de texto a validar
	 * @return {@code true} si el texto es un número entero válido, {@code false} en
	 *         caso contrario
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(volverButton)) {
			this.dispose();

		} else if (e.getSource() == btnAnadir) {
			if (textFieldID.getText().isEmpty() || textFieldNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Campos incompletos",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			String txt = textFieldID.getText().trim();
			int id = 0;

			if (esNumero(txt)) {
				id = Integer.parseInt(txt);
			} else {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número", "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			try {
				if (comprobar(id)) {
					JOptionPane.showMessageDialog(this, "El id introducido ya existe", "Error en ID",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (AltaException e1) {
				JOptionPane.showMessageDialog(this, "Fallo al comprobar id", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			String nom = textFieldNombre.getText().trim();

			try {
				if (comprobarNombre(nom)) {
					JOptionPane.showMessageDialog(this, "Ya existe un artista con ese nombre", "Error en el nombre",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (AltaException e1) {
				JOptionPane.showMessageDialog(this, "Fallo al comprobar nombre", "Error en el nombre",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			String sOg = null;
			Enumeration<AbstractButton> elementos = soloGrupo.getElements();
			boolean tipoSele = false;

			while (elementos.hasMoreElements()) {
				AbstractButton boton = elementos.nextElement();
				if (boton.isSelected()) {
					sOg = boton.getText().toUpperCase();
					tipoSele = true;
				}
			}

			if (!tipoSele) {
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

			try {
				exito = Principal.alta(arti);
			} catch (AltaException e1) {
				JOptionPane.showMessageDialog(this, "Fallo al añadir artista", "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if (exito) {
				JOptionPane.showMessageDialog(this, "Se ha añadido exitosamente este artista", "Éxito",
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
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}