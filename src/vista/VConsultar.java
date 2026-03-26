package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import exception.LoginException;
import main.Principal;
import modelo.Album;
import modelo.Artista;
import utilidades.ExportadorXML;

public class VConsultar extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;
	private JComboBox<Album> cbAlbumes;
	private List<Album> albumes;
	private JButton btnExportar;
	private JButton btnCerrar;
	private JTextField txtBuscador;
	private TableRowSorter<DefaultTableModel> sorter;
	private JLabel lblContador;

	public VConsultar(VMenuAdmin padre, boolean modal) {
		super(padre, modal);

		setTitle("Consulta de artistas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(52, 120, 246);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);
		Color colorTablaSeleccion = new Color(232, 242, 252);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		JLabel lblPanel = new JLabel("Administrador");
		lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPanel.setForeground(colorPrimario);
		lblPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("Listado de artistas");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Consulta, filtra y exporta la información");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		panelCabecera.add(lblPanel);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel(new BorderLayout(0, 15));
		panelCentro.setBackground(fondoTarjeta);

		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		panelBusqueda.setBackground(fondoTarjeta);

		JLabel lblBuscar = new JLabel("Buscar artista:");
		lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblBuscar.setForeground(colorTexto);

		txtBuscador = new JTextField(22);
		txtBuscador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtBuscador.setPreferredSize(new Dimension(240, 34));
		txtBuscador.setToolTipText("Escribe para filtrar por nombre...");
		txtBuscador.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true),
				new EmptyBorder(5, 8, 5, 8)));

		panelBusqueda.add(lblBuscar);
		panelBusqueda.add(txtBuscador);

		panelCentro.add(panelBusqueda, BorderLayout.NORTH);

		String[] columnNames = { "ID", "Nombre", "Tipo", "Álbumes" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(32);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(235, 235, 235));
		table.setSelectionBackground(colorTablaSeleccion);
		table.setSelectionForeground(Color.BLACK);
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setFillsViewportHeight(true);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(colorTexto);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(220);

		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		cbAlbumes = new JComboBox<Album>();

		try {
			albumes = Principal.devolverAlbumes();
			for (Album a : albumes) {
				cbAlbumes.addItem(a);
			}
		} catch (LoginException e) {
			e.printStackTrace();
		}

		cbAlbumes.addActionListener(this);

		TableColumn colAlbumes = table.getColumnModel().getColumn(3);
		colAlbumes.setCellEditor(new DefaultCellEditor(new JComboBox<Album>()) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				@SuppressWarnings("unchecked")
				JComboBox<Album> combo = (JComboBox<Album>) super.getTableCellEditorComponent(table, value, isSelected,
						row, column);

				combo.removeAllItems();

				int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());

				if (albumes != null) {
					for (Album a : albumes) {
						if (a.getIdArtista() == idArtistaFila) {
							combo.addItem(a);
						}
					}
				}

				combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				return combo;
			}
		});

		colAlbumes.setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JComboBox<Album> renderCombo = new JComboBox<Album>();
				renderCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

				int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());

				if (albumes != null) {
					for (Album a : albumes) {
						if (a.getIdArtista() == idArtistaFila) {
							renderCombo.addItem(a);
						}
					}
				}

				if (value != null) {
					renderCombo.setSelectedItem(value);
				} else {
					renderCombo.setSelectedIndex(-1);
				}

				if (isSelected) {
					renderCombo.setBackground(colorTablaSeleccion);
				} else {
					renderCombo.setBackground(Color.WHITE);
				}

				return renderCombo;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int column = table.columnAtPoint(e.getPoint());

					if (row != -1 && column != 3) {
						int modelRow = table.convertRowIndexToModel(row);
						int idArtista = Integer.parseInt(table.getModel().getValueAt(modelRow, 0).toString());

						VConsultaAlbum vCA = new VConsultaAlbum(VConsultar.this, true, idArtista);
						vCA.setVisible(true);
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);

		panelCentro.add(scrollPane, BorderLayout.CENTER);
		panelTarjeta.add(panelCentro, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new BorderLayout(15, 0));
		panelInferior.setBackground(fondoTarjeta);

		lblContador = new JLabel("Artistas visibles: 0");
		lblContador.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblContador.setForeground(colorSecundario);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelBotones.setBackground(fondoTarjeta);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnCerrar.setFocusPainted(false);
		btnCerrar.setBackground(new Color(240, 240, 240));
		btnCerrar.setForeground(colorTexto);
		btnCerrar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnCerrar.addActionListener(this);

		btnExportar = new JButton("Generar XML");
		btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnExportar.setFocusPainted(false);
		btnExportar.setBackground(colorPrimario);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setBorderPainted(false);
		btnExportar.setOpaque(true);
		btnExportar.addActionListener(this);

		panelBotones.add(btnCerrar);
		panelBotones.add(btnExportar);

		panelInferior.add(lblContador, BorderLayout.WEST);
		panelInferior.add(panelBotones, BorderLayout.EAST);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		try {
			Artista a = new Artista();
			Object[][] datos = Principal.devolverArtistas(a);

			model.setRowCount(0);

			for (Object[] fila : datos) {
				Object[] filaCompleta = new Object[4];
				filaCompleta[0] = fila[0];
				filaCompleta[1] = fila[1];
				filaCompleta[2] = fila[2];
				filaCompleta[3] = null;
				model.addRow(filaCompleta);
			}

			lblContador.setText("Artistas visibles: " + table.getRowCount());

		} catch (LoginException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar los artistas", "Error", JOptionPane.ERROR_MESSAGE);
		}

		txtBuscador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrar();
			}

			private void filtrar() {
				String texto = txtBuscador.getText();

				if (texto.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
				}

				lblContador.setText("Artistas visibles: " + table.getRowCount());
			}
		});
		table.setPreferredScrollableViewportSize(new Dimension(750, 300));
		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			dispose();
		}

		if (e.getSource() == btnExportar) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Guardar XML de Artistas");
			fileChooser.setSelectedFile(new File("artistas.xml"));

			int seleccion = fileChooser.showSaveDialog(VConsultar.this);

			if (seleccion == JFileChooser.APPROVE_OPTION) {
				try {
					File archivoSeleccionado = fileChooser.getSelectedFile();
					String ruta = archivoSeleccionado.getAbsolutePath();

					if (!ruta.toLowerCase().endsWith(".xml")) {
						ruta += ".xml";
					}

					List<Artista> listaParaExportar = Principal.obtenerTodosLosArtistasCompletos();
					ExportadorXML exportador = new ExportadorXML();
					exportador.exportarArtistas(listaParaExportar, ruta);

					JOptionPane.showMessageDialog(VConsultar.this, "XML generado correctamente en:\n" + ruta);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(VConsultar.this, "Error al exportar: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}