package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import exception.LoginException;
import main.Principal;
import modelo.Album;
import modelo.Artista;
import utilidades.ExportadorXML;

/**
 * Ventana de consulta que permite visualizar, filtrar y exportar artistas.
 * Implementa múltiples interfaces para gestionar clics, búsqueda en tiempo real y personalización de celdas.
 * 
 * @author An Azkona
 */
public class VConsultar extends JDialog implements ActionListener, MouseListener, DocumentListener, TableCellRenderer {

	private static final long serialVersionUID = 1L;

	// Componentes principales
	private JTable table; // La tabla de datos
	private DefaultTableModel model; // El modelo que contiene los datos de la tabla
	private JComboBox<Album> cbAlbumes; // Combo para el editor de celdas
	private List<Album> albumes; // Lista de todos los álbumes cargados en memoria
	private JButton btnExportar;
	private JButton btnCerrar;
	private JTextField txtBuscador; // Campo de texto para filtrar
	private TableRowSorter<DefaultTableModel> sorter; // Objeto que permite el filtrado dinámico
	private JLabel lblContador; // Etiqueta que indica cuántos registros hay visibles
	
	// Paleta de colores para la interfaz
	private Color colorTablaSeleccion, colorBorde, colorTexto, colorSecundario, naranjaPalo, fondoTarjeta,
			fondoVentana;

	public VConsultar(VMenuAdmin padre, boolean modal) {
		super(padre, modal); // Llamada al constructor del padre (JDialog)

		// Configuración inicial de la ventana
		setTitle("Consulta de artistas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		// Inicialización de la paleta de colores 
		fondoVentana = new Color(245, 247, 250);
		fondoTarjeta = Color.WHITE;
		naranjaPalo = new Color(244, 162, 97);
		colorTexto = new Color(40, 40, 40);
		colorSecundario = new Color(120, 120, 120);
		colorBorde = new Color(220, 224, 230);
		colorTablaSeleccion = new Color(232, 242, 252);

		// Contenedor principal con BorderLayout y márgenes
		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// Panel central estilo "tarjeta" con fondo blanco y bordes redondeados
		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		// --- SECCIÓN SUPERIOR: CABECERA ---
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		JLabel lblPanel = new JLabel("Administrador");
		lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPanel.setForeground(naranjaPalo);
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

		// --- SECCIÓN CENTRAL: BUSCADOR Y TABLA ---
		JPanel panelCentro = new JPanel(new BorderLayout(0, 15));
		panelCentro.setBackground(fondoTarjeta);

		// Panel para la barra de búsqueda
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

		// Configuración del modelo de la tabla (solo la columna de álbumes será editable)
		String[] columnNames = { "ID", "Nombre", "Tipo", "Álbumes" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3; // Solo la columna de álbumes se puede "editar" (para abrir el combo)
			}
		};

		// Configuración visual de la tabla
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(32); // Altura de las filas para mejor lectura
		table.setShowVerticalLines(false); // Estilo moderno sin líneas verticales
		table.setGridColor(new Color(235, 235, 235));
		table.setSelectionBackground(colorTablaSeleccion);
		table.setSelectionForeground(Color.BLACK);
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setFillsViewportHeight(true);

		// Estilo de la cabecera de la tabla
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(colorTexto);
		table.getTableHeader().setReorderingAllowed(false); // Impedir mover columnas

		// Anchos preferidos de columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(220);

		// Asignar el sorter para permitir el filtrado por texto
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		// Cargar álbumes desde la lógica de negocio
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

		// --- CONFIGURACIÓN DEL EDITOR DE CELDA (COMBOBOX DENTRO DE TABLA) ---
		TableColumn colAlbumes = table.getColumnModel().getColumn(3);
		colAlbumes.setCellEditor(new DefaultCellEditor(new JComboBox<Album>()) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				@SuppressWarnings("unchecked")
				JComboBox<Album> combo = (JComboBox<Album>) super.getTableCellEditorComponent(table, value, isSelected,
						row, column);

				combo.removeAllItems(); // Limpiamos el combo antes de mostrarlo

				// Obtenemos el ID del artista de la fila seleccionada
				int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());

				// Llenamos el combo solo con álbumes que pertenezcan a ese artista
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

		// Asignar el Renderer personalizado para dibujar el combo en la celda
		colAlbumes.setCellRenderer(this);

		// Escuchador de ratón para el doble clic
		table.addMouseListener(this);

		// ScrollPane para contener la tabla
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);

		panelCentro.add(scrollPane, BorderLayout.CENTER);
		panelTarjeta.add(panelCentro, BorderLayout.CENTER);

		// --- SECCIÓN INFERIOR: CONTADOR Y BOTONES ---
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
		btnExportar.setBackground(naranjaPalo);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setBorderPainted(false);
		btnExportar.setOpaque(true);
		btnExportar.addActionListener(this);

		panelBotones.add(btnCerrar);
		panelBotones.add(btnExportar);

		panelInferior.add(lblContador, BorderLayout.WEST);
		panelInferior.add(panelBotones, BorderLayout.EAST);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		// --- CARGA DE DATOS DE ARTISTAS ---
		try {
			Artista a = new Artista();
			Object[][] datos = Principal.devolverArtistas(a);

			model.setRowCount(0); // Limpiar tabla

			for (Object[] fila : datos) {
				Object[] filaCompleta = new Object[4];
				filaCompleta[0] = fila[0]; // ID
				filaCompleta[1] = fila[1]; // Nombre
				filaCompleta[2] = fila[2]; // Tipo
				filaCompleta[3] = null;    // El combo se renderiza solo
				model.addRow(filaCompleta);
			}

			lblContador.setText("Artistas visibles: " + table.getRowCount());

		} catch (LoginException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar los artistas", "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Listener para detectar cambios en el campo de búsqueda
		txtBuscador.getDocument().addDocumentListener(this);
		
		// Empaquetar y centrar
		table.setPreferredScrollableViewportSize(new Dimension(750, 300));
		pack();
		setLocationRelativeTo(padre);
	}

	/**
	 * Gestiona los eventos de los botones (Cerrar y Exportar XML)
	 * @param e Evento de acción
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			dispose(); // Cerrar ventana
		}

		if (e.getSource() == btnExportar) {
			// Selector de archivos para guardar el XML
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Guardar XML de Artistas");
			fileChooser.setSelectedFile(new File("artistas.xml"));

			int seleccion = fileChooser.showSaveDialog(VConsultar.this);

			if (seleccion == JFileChooser.APPROVE_OPTION) {
				try {
					File archivoSeleccionado = fileChooser.getSelectedFile();
					String ruta = archivoSeleccionado.getAbsolutePath();

					// Añadir extensión si no la tiene
					if (!ruta.toLowerCase().endsWith(".xml")) {
						ruta += ".xml";
					}

					// Lógica de exportación
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

	/**
	 * Detecta el doble clic en la tabla para ver detalles del álbum
	 * @param e Evento de ratón
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			if (e.getClickCount() == 2) { // Doble clic
				int row = table.getSelectedRow();
				int column = table.columnAtPoint(e.getPoint());

				// Si el clic no es en la columna de álbumes, abrimos detalles
				if (row != -1 && column != 3) {
					int modelRow = table.convertRowIndexToModel(row); // Ajustar fila por si está filtrada
					int idArtista = Integer.parseInt(table.getModel().getValueAt(modelRow, 0).toString());

					VConsultaAlbum vCA = new VConsultaAlbum(this, true, idArtista);
					vCA.setVisible(true);
				}
			}
		}
	}

	// Métodos vacíos de MouseListener
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

	/**
	 * Aplica el filtro de texto sobre la tabla utilizando expresiones regulares
	 */
	private void filtrar() {
		String texto = txtBuscador.getText();

		if (texto.trim().length() == 0) {
			sorter.setRowFilter(null); // Sin filtro
		} else {
			// Filtra por la columna 1 (Nombre), ignorando mayúsculas/minúsculas (?i)
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
		}

		lblContador.setText("Artistas visibles: " + table.getRowCount());
	}

	// Métodos de DocumentListener para el buscador
	@Override
	public void changedUpdate(DocumentEvent e) { filtrar(); }
	@Override
	public void insertUpdate(DocumentEvent e) { filtrar(); }
	@Override
	public void removeUpdate(DocumentEvent e) { filtrar(); }

	/**
	 * Define cómo se dibuja la celda de la columna "Álbumes" (Renderizador)
	 * 
	 *  @param table La tabla que se está renderizando
	 *  @param value El valor actual de la celda (puede ser null)
	 *  @param isSelected Si la fila está seleccionada
	 *  @param hasFocus Si la celda tiene el foco
	 *  @param row La fila que se está renderizando
	 *  @param column la columna que se está renderizando
	 *  @return Un componente que representa la celda (en este caso, un JComboBox con los álbumes del artista)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JComboBox<Album> renderCombo = new JComboBox<Album>();
		renderCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		// Identificar artista de la fila
		int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());

		// Llenar combo para visualización
		if (albumes != null) {
			for (Album a : albumes) {
				if (a.getIdArtista() == idArtistaFila) {
					renderCombo.addItem(a);
				}
			}
		}

		// Seleccionar el valor si existe
		if (value != null) {
			renderCombo.setSelectedItem(value);
		} else {
			renderCombo.setSelectedIndex(-1);
		}

		// Color de selección
		if (isSelected) {
			renderCombo.setBackground(colorTablaSeleccion);
		} else {
			renderCombo.setBackground(Color.WHITE);
		}

		return renderCombo;
	}
}