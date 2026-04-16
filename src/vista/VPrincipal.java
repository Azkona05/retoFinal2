package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author An Azkona, Nora Yakoubi, Ricardo Soza, Jon Ander Varela 
 * */
public class VPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnInicioSesion;
	private JButton btnAcceder;
	
	public VPrincipal() {

		// Configuración básica de la ventana
		setTitle("Tartanga Music"); // Título de la parte superior
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar la ventana, se para el proceso
		setSize(900, 550); // Dimensiones de la ventana
		setLocationRelativeTo(null); // Centra la ventana en la pantalla
		setResizable(false); // Evita que el usuario cambie el tamaño
		// Cambia el icono de la ventana por una imagen propia
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		// Definición de la paleta de colores (estética Dark Mode)
		Color fondo = new Color(33, 37, 41); // Gris casi negro
		Color header = new Color(45, 49, 54); // Gris intermedio
		Color naranjaPalo = new Color(244, 162, 97); // Color de acento para botones
		Color texto = new Color(248, 249, 250); // Blanco roto
		Color gris = new Color(173, 181, 189); // Gris claro para textos secundarios

		// Creamos el contenedor principal con un diseño de bordes (Norte, Sur, Este,
		// Oeste, Centro)
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(fondo);
		setContentPane(contentPane);

		// --- PANEL SUPERIOR (HEADER) ---
		JPanel panelTop = new JPanel(new BorderLayout());
		panelTop.setBackground(header);
		panelTop.setBorder(new EmptyBorder(15, 25, 15, 25)); // Margen interno (padding)

		JLabel titulo = new JLabel("Tartanga Music");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setForeground(texto);

		btnInicioSesion = new JButton("Iniciar sesión");
		estiloBotonPrimario(btnInicioSesion, naranjaPalo); // Aplicamos estilo personalizado
		btnInicioSesion.setPreferredSize(new Dimension(150, 40));
		btnInicioSesion.addActionListener(this); // Escucha si se hace clic aquí

		panelTop.add(titulo, BorderLayout.WEST); // Título a la izquierda
		panelTop.add(btnInicioSesion, BorderLayout.EAST); // Botón a la derecha

		contentPane.add(panelTop, BorderLayout.NORTH); // Añadimos el header arriba del todo

		// --- PANEL CENTRAL (CUERPO) ---
		// GridBagLayout se usa aquí para que el "bloque" de contenido quede centrado
		// matemáticamente
		JPanel centro = new JPanel(new GridBagLayout());
		centro.setBackground(fondo);

		// Este bloque apila los elementos verticalmente (Eje Y)
		JPanel bloque = new JPanel();
		bloque.setLayout(new BoxLayout(bloque, BoxLayout.Y_AXIS));
		bloque.setBackground(fondo);

		// Icono o espacio para el logo central
		JLabel icono = new JLabel();
		icono.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 80));
		icono.setForeground(naranjaPalo);
		icono.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontal en el BoxLayout

		JLabel bienvenida = new JLabel("¡Hola!");
		bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 30));
		bienvenida.setForeground(texto);
		bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Textos descriptivos
		JLabel linea1 = new JLabel("Toda tu música y comunidad en un solo lugar.");
		JLabel linea2 = new JLabel("Explora, gestiona y disfruta de tus artistas.");
		JLabel linea3 = new JLabel("");

		JLabel[] lineas = { linea1, linea2, linea3 };

		// Bucle para dar formato a todas las líneas de texto de una vez
		for (JLabel l : lineas) {
			l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			l.setForeground(gris);
			l.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontal en el BoxLayout
		}

		btnAcceder = new JButton("Entrar");
		estiloBotonPrimario(btnAcceder, naranjaPalo);
		btnAcceder.setPreferredSize(new Dimension(180, 45));
		btnAcceder.setMaximumSize(new Dimension(180, 45));
		btnAcceder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAcceder.addActionListener(this);

		// Añadimos los componentes al bloque vertical con separadores 
		bloque.add(icono);
		bloque.add(Box.createVerticalStrut(15)); // Espacio de 15px de altura
		bloque.add(bienvenida);
		bloque.add(Box.createVerticalStrut(20));
		bloque.add(linea1);
		bloque.add(Box.createVerticalStrut(5));
		bloque.add(linea2);
		bloque.add(Box.createVerticalStrut(5));
		bloque.add(linea3);
		bloque.add(Box.createVerticalStrut(30));
		bloque.add(btnAcceder);

		centro.add(bloque); // Añadimos el bloque al contenedor GridBag
		contentPane.add(centro, BorderLayout.CENTER); // Todo al centro de la ventana

		// --- PANEL INFERIOR (FOOTER) ---
		JPanel footer = new JPanel(new BorderLayout());
		footer.setBackground(header);
		footer.setBorder(new EmptyBorder(10, 20, 10, 20));

		JLabel autores = new JLabel("An Azkona · Nora Yakoubi · Ricardo Soza · Jon Ander Varela");
		autores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		autores.setForeground(gris);

		JLabel curso = new JLabel("Reto Final · 1º DAM · 2026");
		curso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		curso.setForeground(gris);
		curso.setHorizontalAlignment(SwingConstants.RIGHT);

		footer.add(autores, BorderLayout.WEST); // Autores a la izquierda
		footer.add(curso, BorderLayout.EAST); // Curso a la derecha

		contentPane.add(footer, BorderLayout.SOUTH); // Añadimos el footer abajo del todo
	}

	/**
	 * Método para dar un aspecto moderno a los botones
	 * 
	 * @param b     El botón a modificar
	 * @param color El color de fondo deseado
	 */
	private void estiloBotonPrimario(JButton b, Color color) {
		b.setBackground(color);
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Segoe UI", Font.BOLD, 14));
		b.setFocusPainted(false); // Quita el cerco de selección
		b.setBorderPainted(false); // Quita el borde por defecto
		b.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el ratón a "mano" al pasar por encima
	}

	/**
	 * Gestiona los eventos de acción producidos por los componentes de la interfaz.
	 * 
	 * Este método detecta si el usuario ha pulsado el botón de "Iniciar sesión" o el 
	 * botón "Entrar". En ambos casos, procede a instanciar la ventana de acceso (VLogin), 
	 * la hace visible al usuario y libera los recursos de la ventana principal actual.
	 * 
	 * * @param e El evento de acción disparado (ActionEvent), que contiene la información 
	 * sobre qué componente (botón) ha sido activado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Comprobamos si el origen del evento es alguno de los botones de navegación
		if(e.getSource() == btnInicioSesion || e.getSource() == btnAcceder) {
			
			// Instanciamos la ventana de Login pasando la ventana actual como padre (modal)
			VLogin login = new VLogin(this, true);
			
			// Hacemos visible la nueva ventana para el usuario
			login.setVisible(true);
			
			// Cerramos y liberamos la memoria de la ventana principal (VPrincipal)
			this.dispose();
		}
	}
}