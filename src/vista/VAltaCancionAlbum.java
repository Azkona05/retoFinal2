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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import exception.AltaException;
import main.Principal;
import modelo.Artista;
import modelo.Cancion;
import modelo.Genero;

public class VAltaCancionAlbum extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnAnadir;
	private JButton btnCancelar;
	private JTable tablaArtistas;
	private JTable tablaAlbumes;
	private JTextField textFieldNombreCancion;
	private JComboBox<Genero> comboBoxGenero;
	private JLabel lblIdGenerado;
	private JTextField textFieldIdAlbum;
	private JTextField textFieldNombreAlbum;
	private JTextField textFieldIdArtista;
	private JTextField textFieldNombreArtista;

	private int idArtistaSeleccionado = 0;
	private int idAlbumSeleccionado = 0;
	private Set<Integer> idsGenerados = new HashSet<>();
	private int idCancionActual;

	public VAltaCancionAlbum(boolean modal) {
		setModal(modal);
		setTitle("Añadir canción a álbum");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));
		
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color naranjaPalo = new Color(244, 162, 97);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);
		Color colorSeleccion = new Color(232, 242, 252);

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

		JLabel lblTitulo = new JLabel("Añadir canción a álbum");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		JLabel lblSubtitulo = new JLabel("Selecciona artista, álbum y completa los datos de la canción");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblSubtitulo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(fondoTarjeta);
		panelCentro.setLayout(new BorderLayout(0, 20));

		// =========================
		// PANEL TABLAS
		// =========================
		JPanel panelTablas = new JPanel(new GridBagLayout());
		panelTablas.setBackground(fondoTarjeta);

		GridBagConstraints gbcTablas = new GridBagConstraints();
		gbcTablas.gridx = 0;
		gbcTablas.insets = new Insets(6, 0, 6, 0);
		gbcTablas.anchor = GridBagConstraints.WEST;
		gbcTablas.fill = GridBagConstraints.HORIZONTAL;
		gbcTablas.weightx = 1.0;

		JLabel lblArtistas = new JLabel("Artistas");
		lblArtistas.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblArtistas.setForeground(colorTexto);

		gbcTablas.gridy = 0;
		panelTablas.add(lblArtistas, gbcTablas);

		String[] columnasArtistas = { "ID", "NOMBRE", "TIPO" };
		DefaultTableModel modelArtistas = new DefaultTableModel(null, columnasArtistas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			Map<Integer, Artista> artiMap = Principal.listarArti(null);

			if (artiMap != null && !artiMap.isEmpty()) {
				for (Map.Entry<Integer, Artista> entry : artiMap.entrySet()) {
					Artista artista = entry.getValue();
					Object[] fila = { artista.getId(), artista.getNombre(), artista.getTipo().name() };
					modelArtistas.addRow(fila);
				}
			}
		} catch (AltaException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar artistas: " + e.getMessage());
		}

		tablaArtistas = new JTable(modelArtistas);
		tablaArtistas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaArtistas.setRowHeight(28);
		tablaArtistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaArtistas.setShowVerticalLines(false);
		tablaArtistas.setGridColor(new Color(235, 235, 235));
		tablaArtistas.setSelectionBackground(colorSeleccion);
		tablaArtistas.setSelectionForeground(Color.BLACK);
		tablaArtistas.setFillsViewportHeight(true);

		tablaArtistas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tablaArtistas.getTableHeader().setBackground(Color.WHITE);
		tablaArtistas.getTableHeader().setForeground(colorTexto);
		tablaArtistas.getTableHeader().setReorderingAllowed(false);

		tablaArtistas.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int fila = tablaArtistas.getSelectedRow();
				if (fila != -1) {
					idArtistaSeleccionado = Integer.parseInt(tablaArtistas.getValueAt(fila, 0).toString());
					String nombreArtista = tablaArtistas.getValueAt(fila, 1).toString();

					idAlbumSeleccionado = 0;
					textFieldIdAlbum.setText("");
					textFieldNombreAlbum.setText("");
					textFieldIdArtista.setText("");
					textFieldNombreArtista.setText("");

					cargarAlbumesPorArtista(idArtistaSeleccionado, nombreArtista);
				}
			}
		});

		JScrollPane scrollArtistas = new JScrollPane(tablaArtistas);
		scrollArtistas.setBorder(new LineBorder(colorBorde, 1, true));
		scrollArtistas.getViewport().setBackground(Color.WHITE);
		scrollArtistas.setPreferredSize(new Dimension(720, 140));

		gbcTablas.gridy = 1;
		panelTablas.add(scrollArtistas, gbcTablas);

		JLabel lblAlbumes = new JLabel("Álbumes del artista seleccionado");
		lblAlbumes.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAlbumes.setForeground(colorTexto);

		gbcTablas.gridy = 2;
		panelTablas.add(lblAlbumes, gbcTablas);

		String[] columnasAlbumes = { "ID_ALBUM", "NOMBRE_ALBUM", "ID_ARTISTA", "ARTISTA" };
		DefaultTableModel modelAlbumes = new DefaultTableModel(null, columnasAlbumes) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaAlbumes = new JTable(modelAlbumes);
		tablaAlbumes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaAlbumes.setRowHeight(28);
		tablaAlbumes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaAlbumes.setShowVerticalLines(false);
		tablaAlbumes.setGridColor(new Color(235, 235, 235));
		tablaAlbumes.setSelectionBackground(colorSeleccion);
		tablaAlbumes.setSelectionForeground(Color.BLACK);
		tablaAlbumes.setFillsViewportHeight(true);

		tablaAlbumes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tablaAlbumes.getTableHeader().setBackground(Color.WHITE);
		tablaAlbumes.getTableHeader().setForeground(colorTexto);
		tablaAlbumes.getTableHeader().setReorderingAllowed(false);

		tablaAlbumes.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int fila = tablaAlbumes.getSelectedRow();
				if (fila != -1) {
					idAlbumSeleccionado = Integer.parseInt(tablaAlbumes.getValueAt(fila, 0).toString());
					String nombreAlbum = tablaAlbumes.getValueAt(fila, 1).toString();
					int idArtista = Integer.parseInt(tablaAlbumes.getValueAt(fila, 2).toString());
					String nombreArtista = tablaAlbumes.getValueAt(fila, 3).toString();

					textFieldIdAlbum.setText(String.valueOf(idAlbumSeleccionado));
					textFieldNombreAlbum.setText(nombreAlbum);
					textFieldIdArtista.setText(String.valueOf(idArtista));
					textFieldNombreArtista.setText(nombreArtista);
				}
			}
		});

		JScrollPane scrollAlbumes = new JScrollPane(tablaAlbumes);
		scrollAlbumes.setBorder(new LineBorder(colorBorde, 1, true));
		scrollAlbumes.getViewport().setBackground(Color.WHITE);
		scrollAlbumes.setPreferredSize(new Dimension(720, 120));

		gbcTablas.gridy = 3;
		panelTablas.add(scrollAlbumes, gbcTablas);

		panelCentro.add(panelTablas, BorderLayout.NORTH);

		// =========================
		// FORMULARIO
		// =========================
		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(fondoTarjeta);
		panelFormulario.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				new EmptyBorder(15, 15, 15, 15)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblIdAlbum = new JLabel("ID álbum");
		lblIdAlbum.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdAlbum.setForeground(colorTexto);

		textFieldIdAlbum = new JTextField();
		textFieldIdAlbum.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldIdAlbum.setEditable(false);
		textFieldIdAlbum.setPreferredSize(new Dimension(180, 34));
		textFieldIdAlbum.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblNombreAlbum = new JLabel("Nombre álbum");
		lblNombreAlbum.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombreAlbum.setForeground(colorTexto);

		textFieldNombreAlbum = new JTextField();
		textFieldNombreAlbum.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombreAlbum.setEditable(false);
		textFieldNombreAlbum.setPreferredSize(new Dimension(300, 34));
		textFieldNombreAlbum.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblIdArtista = new JLabel("ID artista");
		lblIdArtista.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdArtista.setForeground(colorTexto);

		textFieldIdArtista = new JTextField();
		textFieldIdArtista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldIdArtista.setEditable(false);
		textFieldIdArtista.setPreferredSize(new Dimension(180, 34));
		textFieldIdArtista.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblNombreArtista = new JLabel("Nombre artista");
		lblNombreArtista.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombreArtista.setForeground(colorTexto);

		textFieldNombreArtista = new JTextField();
		textFieldNombreArtista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombreArtista.setEditable(false);
		textFieldNombreArtista.setPreferredSize(new Dimension(300, 34));
		textFieldNombreArtista.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblId = new JLabel("ID generado");
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setForeground(colorTexto);

		lblIdGenerado = new JLabel("");
		lblIdGenerado.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdGenerado.setForeground(naranjaPalo);

		JLabel lblNombre = new JLabel("Nombre canción");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setForeground(colorTexto);

		textFieldNombreCancion = new JTextField();
		textFieldNombreCancion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombreCancion.setPreferredSize(new Dimension(300, 34));
		textFieldNombreCancion.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		JLabel lblGenero = new JLabel("Género");
		lblGenero.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblGenero.setForeground(colorTexto);

		comboBoxGenero = new JComboBox<>(Genero.values());
		comboBoxGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		comboBoxGenero.setBackground(Color.WHITE);
		comboBoxGenero.setPreferredSize(new Dimension(220, 34));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(lblIdAlbum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(textFieldIdAlbum, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(lblNombreAlbum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(textFieldNombreAlbum, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(lblIdArtista, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(textFieldIdArtista, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(lblNombreArtista, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(textFieldNombreArtista, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(lblId, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		panelFormulario.add(lblIdGenerado, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		panelFormulario.add(lblNombre, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		panelFormulario.add(textFieldNombreCancion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		panelFormulario.add(lblGenero, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		panelFormulario.add(comboBoxGenero, gbc);

		panelCentro.add(panelFormulario, BorderLayout.CENTER);
		JScrollPane scrollPrincipal = new JScrollPane(panelCentro);
		scrollPrincipal.setBorder(null);
		scrollPrincipal.getVerticalScrollBar().setUnitIncrement(16);
		scrollPrincipal.getViewport().setBackground(fondoTarjeta);

		panelTarjeta.add(scrollPrincipal, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelInferior.setBackground(fondoTarjeta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(240, 240, 240));
		btnCancelar.setForeground(colorTexto);
		btnCancelar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnCancelar.addActionListener(this);

		btnAnadir = new JButton("Añadir canción");
		btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAnadir.setFocusPainted(false);
		btnAnadir.setBorderPainted(false);
		btnAnadir.setOpaque(true);
		btnAnadir.setBackground(naranjaPalo);
		btnAnadir.setForeground(Color.WHITE);
		btnAnadir.addActionListener(this);

		panelInferior.add(btnCancelar);
		panelInferior.add(btnAnadir);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		getRootPane().setDefaultButton(btnAnadir);

		generarNuevoId();

		setSize(820, 660);
		setLocationRelativeTo(null);
	}

	private void cargarAlbumesPorArtista(int idArtista, String nombreArtista) {
		try {
			java.sql.Connection con = null;
			java.sql.PreparedStatement stmt = null;
			java.sql.ResultSet rs = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = java.sql.DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/TartangaMusic?serverTimezone=Europe/Madrid&useSSL=false",
						"root",
						"abcd*1234");

				String sql = "SELECT ID_AL, NOMBRE FROM ALBUM WHERE ID_A = ?";
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, idArtista);
				rs = stmt.executeQuery();

				DefaultTableModel model = (DefaultTableModel) tablaAlbumes.getModel();
				model.setRowCount(0);

				while (rs.next()) {
					Object[] fila = { rs.getInt("ID_AL"), rs.getString("NOMBRE"), idArtista, nombreArtista };
					model.addRow(fila);
				}

				if (model.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this,
							"El artista " + nombreArtista
									+ " no tiene álbumes.\nPrimero debes crear un álbum para este artista.",
							"Información", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al cargar álbumes: " + ex.getMessage());
			} finally {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void generarNuevoId() {
		int id;
		do {
			id = (int) (Math.random() * 9999) + 1;
		} while (idsGenerados.contains(id) || idYaExisteEnBD(id));

		idCancionActual = id;
		idsGenerados.add(idCancionActual);
		lblIdGenerado.setText(String.valueOf(idCancionActual));
	}

	private boolean idYaExisteEnBD(int id) {
		try {
			return Principal.existeIdCancion(id);
		} catch (AltaException e) {
			return false;
		}
	}

	private boolean validarCampos() {
		if (idAlbumSeleccionado == 0) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona un álbum de la tabla.", "Campo requerido",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		String nombreCancion = textFieldNombreCancion.getText().trim();
		if (nombreCancion.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, introduce el nombre de la canción.", "Campo requerido",
					JOptionPane.WARNING_MESSAGE);
			textFieldNombreCancion.requestFocus();
			return false;
		}

		try {
			if (Principal.existeCancionEnAlbum(nombreCancion, idAlbumSeleccionado)) {
				JOptionPane.showMessageDialog(this,
						"Ya existe una canción con el nombre '" + nombreCancion
								+ "' en el álbum seleccionado.\nPor favor, elige otro nombre.",
						"Nombre duplicado", JOptionPane.WARNING_MESSAGE);
				textFieldNombreCancion.requestFocus();
				return false;
			}
		} catch (AltaException e) {
			JOptionPane.showMessageDialog(this, "Error al comprobar nombre: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void anadirCancion() {
		if (!validarCampos()) {
			return;
		}

		try {
			String nombreCancion = textFieldNombreCancion.getText().trim();
			Genero genero = (Genero) comboBoxGenero.getSelectedItem();

			Cancion cancion = new Cancion();
			cancion.setId(idCancionActual);
			cancion.setNombre(nombreCancion);
			cancion.setGenero(genero);

			boolean exito = Principal.altaCancion(cancion, idAlbumSeleccionado);

			if (exito) {
				JOptionPane.showMessageDialog(this,
						"¡Canción añadida correctamente!\n\n" + "ID: " + idCancionActual + "\n" + "Canción: "
								+ nombreCancion + "\n" + "Género: " + genero + "\n" + "Álbum: "
								+ textFieldNombreAlbum.getText(),
						"Éxito", JOptionPane.INFORMATION_MESSAGE);

				textFieldNombreCancion.setText("");
				comboBoxGenero.setSelectedIndex(0);
				generarNuevoId();

				int respuesta = JOptionPane.showConfirmDialog(this, "¿Quieres añadir otra canción al mismo álbum?",
						"Continuar", JOptionPane.YES_NO_OPTION);

				if (respuesta != JOptionPane.YES_OPTION) {
					dispose();
				}

			} else {
				JOptionPane.showMessageDialog(this, "Error al guardar la canción.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (AltaException ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			dispose();
		} else if (e.getSource() == btnAnadir) {
			anadirCancion();
		}
	}
}