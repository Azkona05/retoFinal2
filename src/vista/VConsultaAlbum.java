package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import exception.LoginException;
import main.Principal;
import modelo.Artista;

/**
 * Ventana de diálogo que muestra la lista de canciones de un artista
 * específico. Proporciona una interfaz para visualizar detalles de las
 * canciones (ID, nombre, género, álbum) y permite la reproducción de archivos
 * de audio en formato .wav asociados a cada canción.
 * 
 * @author An Azkona
 * 
 * @version 1.0
 */
public class VConsultaAlbum extends JDialog {

	private static final long serialVersionUID = 1L;

	/** Panel contenedor principal de la interfaz. */
	private final JPanel contentPanel = new JPanel();

	/** Tabla que despliega la información de las canciones. */
	private JTable table;

	/** Modelo de datos de la tabla. */
	private DefaultTableModel model;

	/** Objeto Clip para controlar la reproducción de audio actual. */
	private Clip clipActual;

	/**
	 * Constructor de la ventana de consulta de álbumes/canciones. Configura el
	 * aspecto visual de la tabla, inicializa los manejadores de eventos para la
	 * reproducción de audio y carga las canciones del artista indicado. * @param
	 * padre Referencia a la ventana de consulta principal.
	 * 
	 * @param modal     Define si el diálogo bloquea el acceso a otras ventanas.
	 * @param idArtista Identificador único del artista cuyas canciones se desean
	 *                  mostrar.
	 */
	public VConsultaAlbum(VConsultar padre, boolean modal, int idArtista) {
		super(padre);
		this.setModal(modal);
		setTitle("Lista de Canciones");
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		String[] columnNames = { "ID Canción", "Nombre", "Género", "Álbum", "Reproducir" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Las celdas no son editables directamente
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

		// Renderizador personalizado para mostrar un botón en la columna de
		// reproducción
		table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton btnPlay = new JButton("▶ Play");
				return btnPlay;
			}
		});

		// Listener para detectar el clic en el "botón" de la tabla
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int column = table.columnAtPoint(e.getPoint());
				if (row != -1 && column == 4) {
					String nombreCancion = table.getValueAt(row, 1).toString();
					System.out.println("Intentando reproducir: " + nombreCancion);
					reproducirAudio(nombreCancion);
				}
			}
		});

		// Listener para asegurar que el audio se detenga al cerrar la ventana
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				detenerAudio();
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		try {
			cargarDatos(idArtista);
		} catch (LoginException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Obtiene de la capa lógica los datos de las canciones filtradas por artista.
	 * 
	 * @param idArtista ID del artista a consultar.
	 * 
	 * @throws LoginException Si ocurre un error en la comunicación con la base de
	 *                        datos.
	 */
	private void cargarDatos(int idArtista) throws LoginException {
		Artista a = new Artista();
		a.setId(idArtista);
		Object[][] datos = Principal.devolverCancionesArtista(a);
		actualizarDatos(datos);
	}

	/**
	 * Limpia y repuebla el modelo de la tabla con la nueva información de
	 * canciones.
	 * 
	 * @param datos Matriz de datos con la información de las canciones.
	 */
	private void actualizarDatos(Object[][] datos) {
		model.setRowCount(0);
		for (Object[] fila : datos) {
			Object[] filaCompleta = new Object[5];
			filaCompleta[0] = fila[0];
			filaCompleta[1] = fila[1];
			filaCompleta[2] = fila[2];
			filaCompleta[3] = fila[3];
			filaCompleta[4] = "Play";
			model.addRow(filaCompleta);
		}
	}

	/**
	 * Gestiona la reproducción de un archivo de sonido. Detiene cualquier audio
	 * previo, busca el archivo .wav en la carpeta "canciones/" y comienza la
	 * reproducción.
	 * 
	 * @param nombreCancion Nombre del archivo de audio (sin extensión) a
	 *                      reproducir.
	 */
	private void reproducirAudio(String nombreCancion) {
		detenerAudio();

		try {
			File archivoMusica = new File("canciones/" + nombreCancion + ".wav");

			if (archivoMusica.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(archivoMusica);
				clipActual = AudioSystem.getClip();
				clipActual.open(audioInput);
				clipActual.start();
				System.out.println("Reproduciendo audio...");
			} else {
				System.out.println("ERROR: No se encontró el archivo en: " + archivoMusica.getAbsolutePath());
			}
		} catch (Exception ex) {
			System.out.println("Error al reproducir el audio.");
			ex.printStackTrace();
		}
	}

	/**
	 * Detiene y libera los recursos del Clip de audio si se encuentra actualmente
	 * en ejecución.
	 */
	private void detenerAudio() {
		if (clipActual != null && clipActual.isRunning()) {
			clipActual.stop();
			clipActual.close();
		}
	}
}