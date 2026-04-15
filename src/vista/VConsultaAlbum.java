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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import exception.LoginException;
import main.Principal;
import modelo.Artista;

/**
 * Ventana de consulta de canciones de un artista.
 * 
 * Muestra una tabla con las canciones asociadas al artista seleccionado.
 * Además, permite reproducir una canción pulsando sobre la columna
 * "Reproducir" y detener la reproducción al cerrar la ventana.
 * 
 * La ventana:
 * - carga las canciones del artista recibido por parámetro
 * - muestra sus datos en una tabla
 * - simula un botón "Play" en la última columna
 * - reproduce un archivo .wav cuyo nombre coincide con el nombre de la canción
 * 
 * @author An Azkona
 * @version 1.0
 */
public class VConsultaAlbum extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** Tabla donde se muestran las canciones del artista. */
	private JTable table;

	/** Modelo de datos asociado a la tabla. */
	private DefaultTableModel model;

	/**
	 * Clip de audio actualmente en reproducción.
	 * 
	 * Se guarda para poder detenerlo antes de reproducir otro
	 * o al cerrar la ventana.
	 */
	private Clip clipActual;

	/** Botón para cerrar la ventana. */
	private JButton btnCerrar;

	/**
	 * Constructor de la ventana de consulta de canciones.
	 * 
	 * Recibe el identificador del artista y carga todas sus canciones
	 * en una tabla. Cada fila incluye un acceso visual para reproducir
	 * el archivo de audio asociado a la canción.
	 * 
	 * @param padre ventana padre desde la que se abre este diálogo
	 * @param modal indica si la ventana será modal o no
	 * @param idArtista identificador del artista cuyas canciones se desean mostrar
	 */
	public VConsultaAlbum(VConsultar padre, boolean modal, int idArtista) {
		super(padre, modal);

		setTitle("Lista de canciones");
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

		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		JLabel lblPanel = new JLabel("Consulta");
		lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPanel.setForeground(naranjaPalo);
		lblPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("Listado de canciones");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Seleccione una canción para reproducirla");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		panelCabecera.add(lblPanel);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		String[] columnNames = { "ID Canción", "Nombre", "Género", "Álbum", "Reproducir" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			/**
			 * En esta tabla no se permite editar ninguna celda.
			 * 
			 * Aunque la última columna se vea como un botón, realmente
			 * no es un botón editable de tabla; se dibuja con un renderer
			 * y el clic se controla con un MouseListener.
			 * 
			 * @param row fila consultada
			 * @param column columna consultada
			 * @return false siempre, porque la tabla es solo de visualización
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(32);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(235, 235, 235));
		table.setSelectionBackground(colorSeleccion);
		table.setSelectionForeground(Color.BLACK);
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setFillsViewportHeight(true);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(colorTexto);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);

		table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
			/**
			 * Dibuja un botón visual en la columna "Reproducir".
			 * 
			 * Importante: esto no convierte la celda en un JButton real
			 * funcional por sí mismo. Solo hace que la celda se vea como
			 * un botón. La acción del clic se controla aparte con el
			 * MouseListener de la tabla.
			 * 
			 * @param table tabla que solicita el renderizado
			 * @param value valor de la celda
			 * @param isSelected indica si la fila está seleccionada
			 * @param hasFocus indica si la celda tiene foco
			 * @param row fila actual
			 * @param column columna actual
			 * @return componente visual que representa la celda
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JButton btnPlay = new JButton("▶ Play");
				btnPlay.setFont(new Font("Segoe UI", Font.BOLD, 12));
				btnPlay.setFocusPainted(false);
				btnPlay.setBorderPainted(false);
				btnPlay.setOpaque(true);
				btnPlay.setBackground(naranjaPalo);
				btnPlay.setForeground(Color.WHITE);

				return btnPlay;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			/**
			 * Detecta clics sobre la tabla.
			 * 
			 * Si el usuario pulsa en la columna "Reproducir", se obtiene
			 * el nombre de la canción de esa fila y se intenta reproducir
			 * su archivo de audio correspondiente.
			 * 
			 * @param e evento de ratón producido sobre la tabla
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int column = table.columnAtPoint(e.getPoint());

				// Solo actuamos si se ha pulsado una fila válida
				// y concretamente la columna 4, que es la de reproducción.
				if (row != -1 && column == 4) {
					String nombreCancion = table.getValueAt(row, 1).toString();
					reproducirAudio(nombreCancion);
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			/**
			 * Se ejecuta cuando la ventana se está cerrando.
			 * 
			 * Se detiene el audio para evitar que siga sonando
			 * después de cerrar el diálogo.
			 * 
			 * @param windowEvent evento de cierre de ventana
			 */
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				detenerAudio();
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);

		table.setPreferredScrollableViewportSize(new Dimension(760, 280));

		panelTarjeta.add(scrollPane, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelInferior.setBackground(fondoTarjeta);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnCerrar.setFocusPainted(false);
		btnCerrar.setBackground(new Color(240, 240, 240));
		btnCerrar.setForeground(colorTexto);
		btnCerrar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnCerrar.addActionListener(this);

		panelInferior.add(btnCerrar);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		try {
			Artista a = new Artista();
			a.setId(idArtista);

			// Se solicitan las canciones del artista a la capa principal.
			// El método devuelve una matriz de objetos con los datos necesarios
			// para rellenar las filas de la tabla.
			Object[][] datos = Principal.devolverCancionesArtista(a);

			model.setRowCount(0);

			for (Object[] fila : datos) {
				Object[] filaCompleta = new Object[5];
				filaCompleta[0] = fila[0];
				filaCompleta[1] = fila[1];
				filaCompleta[2] = fila[2];
				filaCompleta[3] = fila[3];

				// Esta columna no guarda un botón real.
				// Solo guarda un texto de apoyo; el renderer será el que
				// visualmente pinte el botón "Play".
				filaCompleta[4] = "Play";

				model.addRow(filaCompleta);
			}

		} catch (LoginException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar las canciones", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		pack();
		setLocationRelativeTo(padre);
	}

	/**
	 * Gestiona las acciones de los componentes que usan ActionListener.
	 * 
	 * Actualmente solo se controla el botón de cerrar.
	 * Antes de cerrar la ventana, se detiene el audio en reproducción
	 * para evitar que continúe sonando en segundo plano.
	 * 
	 * @param e evento de acción lanzado por un componente
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			detenerAudio();
			dispose();
		}
	}

	/**
	 * Reproduce el archivo de audio asociado a una canción.
	 * 
	 * El método busca un archivo .wav dentro de la carpeta "canciones"
	 * cuyo nombre coincida exactamente con el nombre de la canción.
	 * 
	 * Antes de reproducir una nueva canción, se detiene cualquier audio
	 * que estuviera sonando anteriormente.
	 * 
	 * Ejemplo de ruta esperada:
	 * canciones/NombreCancion.wav
	 * 
	 * @param nombreCancion nombre de la canción que se desea reproducir
	 */
	private void reproducirAudio(String nombreCancion) {
		// Antes de iniciar una nueva reproducción, detenemos la anterior
		// para que no se solapen dos audios a la vez.
		detenerAudio();

		try {
			File archivoMusica = new File("canciones/" + nombreCancion + ".wav");

			if (archivoMusica.exists()) {
				// Se abre el flujo de audio desde el archivo .wav
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(archivoMusica);

				// Se crea un Clip, que permite cargar y reproducir audio corto en memoria
				clipActual = AudioSystem.getClip();

				// Se carga el contenido del audio en el clip
				clipActual.open(audioInput);

				// Se inicia la reproducción
				clipActual.start();
			} else {
				JOptionPane.showMessageDialog(this,
						"No se encontró el archivo de audio:\n" + archivoMusica.getAbsolutePath(),
						"Archivo no encontrado",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error al reproducir el audio.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Detiene la reproducción actual, si existe.
	 * 
	 * Si hay un clip cargado:
	 * - se detiene si está sonando
	 * - se cierra para liberar recursos del sistema
	 * 
	 * Este método se llama antes de reproducir una nueva canción
	 * y también al cerrar la ventana.
	 */
	private void detenerAudio() {
		if (clipActual != null) {
			if (clipActual.isRunning()) {
				clipActual.stop();
			}
			clipActual.close();
		}
	}
}