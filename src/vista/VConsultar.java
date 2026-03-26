package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
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

/**
 * Ventana de diálogo para la consulta y gestión de artistas. Permite visualizar
 * una lista de artistas, filtrar por nombre, consultar sus álbumes mediante un
 * desplegable integrado en la tabla o una ventana detalle, y exportar la
 * información a XML.
 * 
 * @author An Azkona
 * 
 * @version 1.0
 */
public class VConsultar extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** Panel principal que contiene los componentes de la interfaz. */
	private final JPanel contentPanel = new JPanel();

	/** Tabla que muestra los datos de los artistas. */
	private JTable table;

	/** Modelo de datos para la tabla de artistas. */
	private DefaultTableModel model;

	/** ComboBox utilizado para mostrar y seleccionar álbumes. */
	private JComboBox<Album> cbAlbumes;

	/** Lista que almacena todos los álbumes disponibles en el sistema. */
	private List<Album> albumes;

	/** Botón para exportar los datos de los artistas a un archivo XML. */
	private JButton btnExportar;

	/** Campo de texto para realizar búsquedas y filtrados en la tabla. */
	private JTextField txtBuscador;

	/**
	 * Objeto encargado de gestionar el filtrado y ordenación de las filas de la
	 * tabla.
	 */
	private TableRowSorter<DefaultTableModel> sorter;

	/** Etiqueta que muestra el conteo de artistas visibles tras el filtrado. */
	private JLabel lblContador;

	/**
	 * Constructor de la ventana de consulta. Configura la interfaz gráfica,
	 * inicializa los componentes, los eventos de escucha y carga los datos
	 * iniciales desde la lógica de negocio.
	 * 
	 * @param padre Ventana principal (VMenuAdmin) desde la que se invoca este
	 *              diálogo.
	 * @param modal Indica si el diálogo debe bloquear la interacción con otras
	 *              ventanas.
	 */
	public VConsultar(VMenuAdmin padre, boolean modal) {
		super(padre);
		this.setModal(modal);
		setTitle("Lista de Artistas");
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		// Configuración del buscador
		txtBuscador = new JTextField(20);
		txtBuscador.setToolTipText("Escribe para filtrar por nombre...");

		String[] columnNames = { "ID", "Nombre", "Tipo", "Albumes" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Solo la columna de álbumes es editable (para activar el combo)
				return column == 3;
			}
		};

		table = new JTable(model);
		table.setRowHeight(35);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.setSelectionBackground(new Color(232, 242, 252));
		table.setSelectionForeground(Color.BLACK);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		table.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Panel superior de búsqueda
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelBusqueda.setBackground(Color.WHITE);

		JLabel lblLupa = new JLabel("Buscar Artista: ");
		lblLupa.setFont(new Font("Segoe UI", Font.BOLD, 13));

		txtBuscador.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtBuscador.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		panelBusqueda.add(lblLupa);
		panelBusqueda.add(txtBuscador);
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		// Carga inicial de álbumes para el ComboBox
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

		// Configuración de la columna de álbumes con Editor y Renderer personalizado
		TableColumn statusColumn = table.getColumnModel().getColumn(3);
		statusColumn.setCellEditor(new DefaultCellEditor(new JComboBox<Album>()) {
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
				return combo;
			}
		});

		statusColumn.setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JComboBox<Album> renderCombo = new JComboBox<>();
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
				return renderCombo;
			}
		});

		// Evento para detectar doble clic en una fila
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int column = table.columnAtPoint(e.getPoint());
					if (row != -1 && column != 3) {
						int modelRow = table.convertRowIndexToModel(row);
						int idArtista = Integer.parseInt(table.getModel().getValueAt(modelRow, 0).toString());
						String nombreArtista = table.getModel().getValueAt(modelRow, 1).toString();

						System.out.println(
								"Has hecho doble clic en el artista: " + nombreArtista + " (ID: " + idArtista + ")");

						// Abrir ventana detalle del artista
						VConsultaAlbum vCA = new VConsultaAlbum(VConsultar.this, true, idArtista);
						vCA.setVisible(true);
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		// Panel inferior (Footer)
		JPanel panelFooter = new JPanel(new BorderLayout());
		panelFooter.setBorder(new EmptyBorder(5, 15, 5, 15));

		lblContador = new JLabel("Artistas encontrados: 0");
		lblContador.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		panelFooter.add(lblContador, BorderLayout.WEST);

		btnExportar = new JButton("Generar XML");
		btnExportar.setFocusPainted(false);
		btnExportar.addActionListener(this);
		panelFooter.add(btnExportar, BorderLayout.EAST);

		getContentPane().add(panelFooter, BorderLayout.SOUTH);

		try {
			cargarDatos();
		} catch (LoginException e1) {
			e1.printStackTrace();
		}

		// Listener para el filtrado en tiempo real
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
					// Filtrado por columna 1 (Nombre) ignorando mayúsculas/minúsculas
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
				}
				actualizarContador();
			}
		});
	}

	/**
	 * Solicita a la capa lógica la lista de artistas y procede a cargar la tabla.
	 * * @throws LoginException Si ocurre un error de acceso a los datos.
	 */
	private void cargarDatos() throws LoginException {
		Artista a = new Artista();
		Object[][] datos = Principal.devolverArtistas(a);
		actualizarDatos(datos);
	}

	/**
	 * Actualiza el modelo de la tabla con la matriz de datos proporcionada.
	 * * @param datos Matriz de objetos que representa las filas y columnas de los
	 * artistas.
	 */
	private void actualizarDatos(Object[][] datos) {
		model.setRowCount(0);
		for (Object[] fila : datos) {
			Object[] filaCompleta = new Object[4];
			filaCompleta[0] = fila[0];
			filaCompleta[1] = fila[1];
			filaCompleta[2] = fila[2];
			filaCompleta[3] = null; // Espacio para el renderizado del combo de álbumes
			model.addRow(filaCompleta);
		}
		actualizarContador();
	}

	/**
	 * Gestiona las acciones de los botones de la interfaz. En este caso, procesa la
	 * exportación de datos a un archivo XML.
	 * 
	 * @param e El evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnExportar)) {
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

	/**
	 * Actualiza el texto de la etiqueta contador basándose en el número de filas
	 * visibles actualmente en la tabla (tras aplicar filtros).
	 */
	private void actualizarContador() {
		int total = table.getRowCount();
		lblContador.setText("Artistas visibles: " + total);
	}
}