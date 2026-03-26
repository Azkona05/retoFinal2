package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import exception.AltaException;
import main.Principal;
import modelo.Cancion;
import modelo.Genero;

public class VAlbumCanci extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField textFieldNombre;
	private JComboBox<Genero> comboBoxGenero;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnRegenerarId;
	private JLabel lblInfo;
	private JLabel lblIdGenerado;

	private int idAlbum;
	private int cancionesPendientes;
	private int cancionesAñadidas;
	private ArrayList<Cancion> canciones;
	private String nombreAlbum;
	private Set<Integer> idsGenerados;
	private int idActual;

	public VAlbumCanci(int idAlbum, int cancionesPendientes, String nombreAlbum) {
		this.idAlbum = idAlbum;
		this.cancionesPendientes = cancionesPendientes;
		this.cancionesAñadidas = 0;
		this.canciones = new ArrayList<>();
		this.nombreAlbum = nombreAlbum;
		this.idsGenerados = new HashSet<>();

		setModal(true);
		setTitle("Añadir canciones - " + nombreAlbum);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(52, 120, 246);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		JPanel panelCabecera = new JPanel(new BorderLayout(0, 8));
		panelCabecera.setBackground(fondoTarjeta);

		JLabel lblTitulo = new JLabel("Añadiendo canciones");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		JLabel lblSubtitulo = new JLabel("Completa las canciones del álbum seleccionado");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblSubtitulo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(fondoTarjeta);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblAlbum = new JLabel("Álbum");
		lblAlbum.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAlbum.setForeground(colorTexto);

		JLabel lblAlbumValor = new JLabel(nombreAlbum);
		lblAlbumValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAlbumValor.setForeground(colorSecundario);

		JLabel lblIdAlbum = new JLabel("ID álbum");
		lblIdAlbum.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdAlbum.setForeground(colorTexto);

		JLabel lblIdAlbumValor = new JLabel(String.valueOf(idAlbum));
		lblIdAlbumValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblIdAlbumValor.setForeground(colorSecundario);

		JLabel lblProgreso = new JLabel("Progreso");
		lblProgreso.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblProgreso.setForeground(colorTexto);

		lblInfo = new JLabel("Canción " + (cancionesAñadidas + 1) + " de " + cancionesPendientes);
		lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblInfo.setForeground(colorSecundario);

		JLabel lblId = new JLabel("ID generado");
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setForeground(colorTexto);

		lblIdGenerado = new JLabel("");
		lblIdGenerado.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdGenerado.setForeground(colorPrimario);

		btnRegenerarId = new JButton("Regenerar ID");
		btnRegenerarId.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnRegenerarId.setFocusPainted(false);
		btnRegenerarId.setBackground(new Color(240, 240, 240));
		btnRegenerarId.setForeground(colorTexto);
		btnRegenerarId.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnRegenerarId.addActionListener(this);

		JLabel lblNombre = new JLabel("Nombre de la canción");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setForeground(colorTexto);

		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombre.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblGenero = new JLabel("Género");
		lblGenero.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblGenero.setForeground(colorTexto);

		comboBoxGenero = new JComboBox<>(Genero.values());
		comboBoxGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		comboBoxGenero.setBackground(Color.WHITE);

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(lblAlbum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(lblAlbumValor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(lblIdAlbum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(lblIdAlbumValor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(lblProgreso, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(lblInfo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(lblId, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(lblIdGenerado, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		panelFormulario.add(btnRegenerarId, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(lblNombre, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panelFormulario.add(textFieldNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		panelFormulario.add(lblGenero, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		panelFormulario.add(comboBoxGenero, gbc);

		panelTarjeta.add(panelFormulario, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelInferior.setBackground(fondoTarjeta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(240, 240, 240));
		btnCancelar.setForeground(colorTexto);
		btnCancelar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnCancelar.addActionListener(this);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAceptar.setFocusPainted(false);
		btnAceptar.setBorderPainted(false);
		btnAceptar.setOpaque(true);
		btnAceptar.setBackground(colorPrimario);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.addActionListener(this);

		panelInferior.add(btnCancelar);
		panelInferior.add(btnAceptar);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		generarNuevoId();

		getRootPane().setDefaultButton(btnAceptar);

		pack();
		setLocationRelativeTo(null);
	}

	private void generarNuevoId() {
		int id;
		do {
			id = (int) (Math.random() * 9999) + 1;
		} while (idsGenerados.contains(id));

		idActual = id;
		idsGenerados.add(idActual);
		lblIdGenerado.setText(String.valueOf(idActual));
	}

	private void regenerarId() {
		int nuevoId;
		do {
			nuevoId = (int) (Math.random() * 9999) + 1;
		} while (idsGenerados.contains(nuevoId));

		idsGenerados.remove(idActual);
		idActual = nuevoId;
		idsGenerados.add(idActual);
		lblIdGenerado.setText(String.valueOf(idActual));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			int confirm = JOptionPane.showConfirmDialog(
					this,
					"¿Estás seguro de que quieres cancelar?\nSe perderán las canciones añadidas.",
					"Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
			}

		} else if (e.getSource() == btnRegenerarId) {
			regenerarId();

		} else if (e.getSource() == btnAceptar) {
			try {
				String nombre = textFieldNombre.getText().trim();
				Genero genero = (Genero) comboBoxGenero.getSelectedItem();

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(
							this,
							"Por favor, introduce el nombre de la canción",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (Principal.existeIdCancion(idActual)) {
					JOptionPane.showMessageDialog(
							this,
							"El ID " + idActual + " ya existe en la base de datos.\nSe generará uno nuevo.",
							"ID duplicado",
							JOptionPane.WARNING_MESSAGE);
					regenerarId();
					return;
				}

				Cancion cancion = new Cancion();
				cancion.setId(idActual);
				cancion.setNombre(nombre);
				cancion.setGenero(genero);

				canciones.add(cancion);
				cancionesAñadidas++;

				if (cancionesAñadidas < cancionesPendientes) {
					textFieldNombre.setText("");
					comboBoxGenero.setSelectedIndex(0);
					generarNuevoId();
					lblInfo.setText("Canción " + (cancionesAñadidas + 1) + " de " + cancionesPendientes);

					JOptionPane.showMessageDialog(
							this,
							"Canción añadida correctamente.\nFaltan " + (cancionesPendientes - cancionesAñadidas) + " canciones.",
							"Éxito",
							JOptionPane.INFORMATION_MESSAGE);

				} else {
					guardarCanciones();
				}

			} catch (AltaException ex) {
				JOptionPane.showMessageDialog(
						this,
						"Error de base de datos: " + ex.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void guardarCanciones() {
		try {
			for (Cancion cancion : canciones) {
				boolean exito = Principal.altaCancion(cancion, idAlbum);
				if (!exito) {
					throw new AltaException("Error al guardar canción: " + cancion.getNombre());
				}
			}

			JOptionPane.showMessageDialog(
					this,
					"¡Álbum completado!\n\n"
							+ "Álbum: " + nombreAlbum + "\n"
							+ "ID Álbum: " + idAlbum + "\n"
							+ "Canciones añadidas: " + canciones.size() + "\n\n"
							+ "Todas las canciones se han guardado correctamente.",
					"Éxito",
					JOptionPane.INFORMATION_MESSAGE);

			dispose();

		} catch (AltaException ex) {
			JOptionPane.showMessageDialog(
					this,
					"Error al guardar canciones: " + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}