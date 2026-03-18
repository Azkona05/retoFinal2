package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class VConsultaAlbum extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	private Clip clipActual;

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
				return false;
			}
		};
		table = new JTable(model);
		table.setRowHeight(35); 
		table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton btnPlay = new JButton("▶ Play");
				return btnPlay;
			}
		});

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
		this.addWindowListener(new java.awt.event.WindowAdapter() {
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

	private void cargarDatos(int idArtista) throws LoginException {
		Artista a = new Artista();
		a.setId(idArtista);
		Object[][] datos = Principal.devolverCancionesArtista(a);
		actualizarDatos(datos);
	}

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

	private void detenerAudio() {
		if (clipActual != null && clipActual.isRunning()) {
			clipActual.stop();
			clipActual.close();
		}
	}
}