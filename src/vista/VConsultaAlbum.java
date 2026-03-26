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
import java.awt.event.WindowAdapter;
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

public class VConsultaAlbum extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;
	private Clip clipActual;
	private JButton btnCerrar;

	public VConsultaAlbum(VConsultar padre, boolean modal, int idArtista) {
		super(padre, modal);

		setTitle("Lista de canciones");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(52, 120, 246);
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
		lblPanel.setForeground(colorPrimario);
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
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JButton btnPlay = new JButton("▶ Play");
				btnPlay.setFont(new Font("Segoe UI", Font.BOLD, 12));
				btnPlay.setFocusPainted(false);
				btnPlay.setBorderPainted(false);
				btnPlay.setOpaque(true);
				btnPlay.setBackground(colorPrimario);
				btnPlay.setForeground(Color.WHITE);

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
					reproducirAudio(nombreCancion);
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
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
			Object[][] datos = Principal.devolverCancionesArtista(a);

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

		} catch (LoginException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar las canciones", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			detenerAudio();
			dispose();
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

	private void detenerAudio() {
		if (clipActual != null) {
			if (clipActual.isRunning()) {
				clipActual.stop();
			}
			clipActual.close();
		}
	}
}