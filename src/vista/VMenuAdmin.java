package vista;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Clase que representa el menú del administrador. Hereda de JDialog para ser
 * una ventana modal que aparece sobre el Login.
 * 
 * @author An Azkona, Nora Yakoubi, Ricardo Soza, Jon Ander Varela 
 * 
 */
public class VMenuAdmin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	// Componentes de la interfaz
	private JButton btnAlta, btnBaja, btnModificar, btnConsultar, btnCerrar;
	private JLabel lblFooter;
	private Timer timerReloj; // Temporizador para actualizar la hora cada segundo

	/**
	 * Constructor de la ventana de Administración
	 * 
	 * @param padre Referencia a la ventana VLogin que la invoca
	 * @param modal Si es true, bloquea la interacción con la ventana padre
	 */
	public VMenuAdmin(VLogin padre, boolean modal) {
		super(padre, modal);

		// Configuración de la ventana (título, cierre y estética)
		setTitle("Panel de Administración - Tartanga Music");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Libera memoria al cerrar
		setResizable(false); // Evita que el usuario pueda modificar el tamaño de la ventana
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png"))); // Imagen de la aplicacion 

		// Definición de la paleta de colores (Estilo limpio/claro con toques naranja)
		Color fondoVentana = new Color(245, 247, 250); // Gris muy claro
		Color fondoTarjeta = Color.WHITE; // Blanco puro para el panel central
		Color naranjaPalo = new Color(244, 162, 97); // Color corporativo
		Color colorTexto = new Color(40, 40, 40); // Gris oscuro para lectura
		Color colorSecundario = new Color(120, 120, 120); // Gris para subtítulos
		Color colorBorde = new Color(220, 224, 230); // Gris suave para bordes

		// Panel principal con márgenes de 25px
		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		setContentPane(contentPane);

		// Panel central que simula una "tarjeta" (con borde y fondo blanco)
		JPanel panelCentral = new JPanel(new BorderLayout(0, 20));
		panelCentral.setBackground(fondoTarjeta);
		panelCentral.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true), // Borde
																										// redondeado
																										// suave
				BorderFactory.createEmptyBorder(25, 25, 25, 25))); // Margen interno

		// --- SECCIÓN CABECERA ---
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		JLabel lblPanel = new JLabel("Administrador");
		lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPanel.setForeground(naranjaPalo);
		lblPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("Gestión de los datos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Seleccione una operación para continuar");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		// Añadimos textos a la cabecera con espacios verticales (Struts)
		panelCabecera.add(lblPanel);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelCentral.add(panelCabecera, BorderLayout.NORTH);

		// --- SECCIÓN DE ACCIONES (GRID DE BOTONES) ---
		// GridLayout(2, 2) crea una cuadrícula de 2 filas y 2 columnas
		JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 18, 18));
		panelAcciones.setBackground(fondoTarjeta);
		panelAcciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Inicialización y estilo de botones (Alta, Baja, Modificar, Consultar)
		// Todos siguen un patrón de borde suave y cursor de mano
		btnAlta = crearBotonMenu("Dar de alta", colorTexto, colorBorde);
		btnAlta.setToolTipText("Añadir nuevos artistas");
		btnAlta.addActionListener(this);

		btnBaja = crearBotonMenu("Dar de baja", colorTexto, colorBorde);
		btnBaja.setToolTipText("Eliminar registros");
		btnBaja.addActionListener(this);

		btnModificar = crearBotonMenu("Modificar", colorTexto, colorBorde);
		btnModificar.setToolTipText("Editar información");
		btnModificar.addActionListener(this);

		btnConsultar = crearBotonMenu("Consultar", colorTexto, colorBorde);
		btnConsultar.setToolTipText("Ver lista completa");
		btnConsultar.addActionListener(this);

		panelAcciones.add(btnAlta);
		panelAcciones.add(btnBaja);
		panelAcciones.add(btnModificar);
		panelAcciones.add(btnConsultar);

		panelCentral.add(panelAcciones, BorderLayout.CENTER);
		contentPane.add(panelCentral, BorderLayout.CENTER);

		// --- SECCIÓN INFERIOR (FOOTER) ---
		JPanel panelInferior = new JPanel(new BorderLayout(15, 0));
		panelInferior.setBackground(fondoVentana);
		panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		lblFooter = new JLabel("Sesión de Administrador activa");
		lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblFooter.setForeground(colorSecundario);

		btnCerrar = new JButton("Cerrar sesión");
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnCerrar.setFocusPainted(false);
		btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCerrar.setBackground(naranjaPalo);
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setBorderPainted(false);
		btnCerrar.setOpaque(true);
		btnCerrar.addActionListener(this);

		panelInferior.add(lblFooter, BorderLayout.WEST);
		panelInferior.add(btnCerrar, BorderLayout.EAST);

		contentPane.add(panelInferior, BorderLayout.SOUTH);

		// --- RELOJ EN TIEMPO REAL ---
		// Crea un Timer que se ejecuta cada 1000ms (1 segundo) y llama a
		// actionPerformed
		timerReloj = new Timer(1000, this);
		timerReloj.start();

		// Ajusta la ventana al contenido y la centra respecto al Login
		pack();
		setLocationRelativeTo(padre);
	}

	/**
	 * Método auxiliar para no repetir código al crear los 4 botones principales
	 * 
	 * @param texto    Se inserta el texto que se quiera introducir dentro del
	 *                 boton.
	 * @param textoCol Se define el color del texto
	 * @param bordeCol Se define el color del borde del boton
	 */
	private JButton crearBotonMenu(String texto, Color textoCol, Color bordeCol) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setPreferredSize(new Dimension(220, 85));
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBackground(Color.WHITE);
		btn.setForeground(textoCol);
		btn.setBorder(new LineBorder(bordeCol, 1, true));
		return btn;
	}

	/**
	 * Libera los recursos asociados a esta ventana de diálogo.
	 * <p>
	 * Esta sobrescritura del método original asegura que el temporizador del reloj
	 * ({@code timerReloj}) se detenga correctamente antes de cerrar la ventana,
	 * evitando fugas de memoria o hilos de ejecución huérfanos.
	 * </p>
	 */
	@Override
	public void dispose() {
		if (timerReloj != null && timerReloj.isRunning()) {
			timerReloj.stop();
		}
		super.dispose();
	}

	/**
	 * Gestiona las acciones disparadas por los componentes de la interfaz.
	 * <p>
	 * Este método centraliza la lógica de control para:
	 * <ul>
	 * <li>La navegación hacia las ventanas de gestión (Alta, Baja, Consulta).</li>
	 * <li>El cierre de la sesión administrativa.</li>
	 * <li>La actualización periódica de la etiqueta del pie de página (footer)
	 * mediante el evento del temporizador.</li>
	 * </ul>
	 * </p>
	 * * @param e El objeto {@code ActionEvent} generado por el componente (botón o
	 * timer) que ha activado la acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Acción para abrir la ventana de inserción de registros
		if (e.getSource() == btnAlta) {
			VAlta alta = new VAlta(this, true);
			alta.setVisible(true);
		}
		// Acción para abrir la ventana de borrado de registros
		else if (e.getSource() == btnBaja) {
			VBaja baja = new VBaja(this, true);
			baja.setVisible(true);
		}
		// Acción para abrir la ventana de visualización y búsqueda
		else if (e.getSource() == btnConsultar) {
			VConsultar consultar = new VConsultar(this, true);
			consultar.setVisible(true);
		}
		// Acción para modificar (Pendiente de implementación)
		else if (e.getSource() == btnModificar) {
			VModificar modificar = new VModificar(this, true);
			modificar.setVisible(true);
		}
		// Finaliza la sesión actual del administrador
		else if (e.getSource() == btnCerrar) {
			dispose();
		}
		// Actualización automática del reloj en el footer
		else if (e.getSource() == timerReloj) {
			String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			lblFooter.setText("Sesión de Administrador activa  |  " + hora + "  |  " + LocalDate.now().getYear());
		}
	}
}
