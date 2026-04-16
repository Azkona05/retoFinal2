package vista;



// Importaciones necesarias para construir la interfaz gráfica con Swing
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * @author Nora Yakoubi
 */
public class VBaja extends JDialog implements ActionListener {

	// Serial version para serialización (Swing lo usa internamente)
	private static final long serialVersionUID = 1L;

	// Botones de la ventana
	private JButton btnCancion;
	private JButton btnAlbum;
	private JButton bntArtista;
	private JButton bntVolver;

	/**
	 * Constructor de la ventana VBaja.
	 * Inicializa la interfaz gráfica que permite al administrador seleccionar
	 * qué tipo de elemento desea eliminar (artista, canción o álbum).
	 * 
	 * @param padre Ventana padre desde la que se abre este diálogo.
	 * @param modal Indica si la ventana es modal (bloquea la interacción con otras ventanas).
	 */
	// Constructor de la ventana modal de "Dar de baja"
	public VBaja(VMenuAdmin padre, boolean modal) {
		super(padre, modal); // indica ventana padre y si es modal

		setTitle("Dar de baja"); // título de la ventana
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // cerrar sin terminar el programa
		setResizable(false); // no permitir redimensionar

		// icono de la ventana
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));
		
		// Definición de colores del diseño (tema visual)
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(220, 53, 69); // rojo para acciones de eliminar
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);

		// Panel principal de la ventana
		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// Panel tipo tarjeta (contenedor visual principal)
		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		// Panel de cabecera (títulos)
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		// Texto pequeño de sección
		JLabel lblSeccion = new JLabel("Administrador");
		lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblSeccion.setForeground(colorPrimario);
		lblSeccion.setAlignmentX(CENTER_ALIGNMENT);

		// Título principal
		JLabel lblTitulo = new JLabel("Eliminar elementos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		// Subtítulo explicativo
		JLabel lblSubtitulo = new JLabel("Selecciona qué tipo de elemento quieres dar de baja");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		// Añadir labels al panel de cabecera
		panelCabecera.add(lblSeccion);
		panelCabecera.add(Box.createVerticalStrut(8)); // espacio entre elementos
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		// Panel central con botones (3 opciones)
		JPanel panelBotones = new JPanel(new GridLayout(1, 3, 18, 18));
		panelBotones.setBackground(fondoTarjeta);
		panelBotones.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Botón eliminar artista
		bntArtista = new JButton("Artista");
		bntArtista.setFont(new Font("Segoe UI", Font.BOLD, 16));
		bntArtista.setPreferredSize(new Dimension(150, 80));
		bntArtista.setFocusPainted(false);
		bntArtista.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bntArtista.setBackground(Color.WHITE);
		bntArtista.setForeground(colorTexto);
		bntArtista.setBorder(new LineBorder(colorBorde, 1, true));
		bntArtista.addActionListener(this);

		// Botón eliminar canción
		btnCancion = new JButton("Canción");
		btnCancion.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancion.setPreferredSize(new Dimension(150, 80));
		btnCancion.setFocusPainted(false);
		btnCancion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancion.setBackground(Color.WHITE);
		btnCancion.setForeground(colorTexto);
		btnCancion.setBorder(new LineBorder(colorBorde, 1, true));
		btnCancion.addActionListener(this);

		// Botón eliminar álbum
		btnAlbum = new JButton("Álbum");
		btnAlbum.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAlbum.setPreferredSize(new Dimension(150, 80));
		btnAlbum.setFocusPainted(false);
		btnAlbum.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAlbum.setBackground(Color.WHITE);
		btnAlbum.setForeground(colorTexto);
		btnAlbum.setBorder(new LineBorder(colorBorde, 1, true));
		btnAlbum.addActionListener(this);

		// Añadir botones al panel
		panelBotones.add(bntArtista);
		panelBotones.add(btnCancion);
		panelBotones.add(btnAlbum);

		panelTarjeta.add(panelBotones, BorderLayout.CENTER);

		// Panel inferior (botón volver)
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelInferior.setBackground(fondoTarjeta);

		bntVolver = new JButton("Volver");
		bntVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		bntVolver.setFocusPainted(false);
		bntVolver.setBackground(new Color(240, 240, 240));
		bntVolver.setForeground(colorTexto);
		bntVolver.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		bntVolver.addActionListener(this);

		panelInferior.add(bntVolver);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		// Ajusta tamaño automáticamente según componentes
		pack();

		// Centra la ventana respecto al padre
		setLocationRelativeTo(padre);
	}

	/**
	 * Gestiona los eventos producidos por la interacción del usuario con los botones de la interfaz.
	 * Dependiendo del botón pulsado, abre la ventana correspondiente para eliminar
	 * un artista, una canción o un álbum. También permite cerrar la ventana actual.
	 * 
	 * @param e Evento de acción generado al hacer clic en un botón.
	 */
	// Método que gestiona los clicks de los botones
	@Override
	public void actionPerformed(ActionEvent e) {

		// Si se pulsa "Canción"
		if (e.getSource().equals(btnCancion)) {
			VEliminarCancion vCancion = new VEliminarCancion(this, true);
			vCancion.setVisible(true);

		// Si se pulsa "Álbum"
		} else if (e.getSource().equals(btnAlbum)) {
			VEliminarAlbum vAlbum = new VEliminarAlbum(this, true);
			vAlbum.setVisible(true);

		// Si se pulsa "Artista"
		} else if (e.getSource().equals(bntArtista)) {
			VEliminarArtista vArtista = new VEliminarArtista(this, true);
			vArtista.setVisible(true);

		// Botón volver: cierra la ventana actual
		} else if (e.getSource().equals(bntVolver)) {
			dispose();
		}
	}
}