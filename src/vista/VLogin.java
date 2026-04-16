package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import exception.LoginException;
import main.Principal;
import modelo.Usuario;

/**
 * Ventana de diálogo para el inicio de sesión de usuarios. Permite la entrada
 * de credenciales y su validación mediante la lógica de la aplicación.
 * 
 * @author An Azkona, Nora Yakoubi, Ricardo Soza, Jon Ander Varela 
 * 
 */
public class VLogin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Componentes de la interfaz que requieren acceso global en la clase
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private JButton btnComprobar;
	private JButton btnCancelar;

	/**
	 * Constructor de la ventana VLogin. Configura el diseño visual, los colores y
	 * los componentes del formulario.
	 * 
	 * @param padre La ventana JFrame desde la que se invoca este diálogo.
	 * @param modal Booleano que indica si el diálogo debe bloquear la ventana
	 *              padre.
	 */
	public VLogin(VPrincipal padre, boolean modal) {
		// Llama al constructor de JDialog
		super(padre, modal);

		// Configuración básica de la ventana emergente
		setTitle("Iniciar sesión");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(520, 460);
		setLocationRelativeTo(padre); // Aparece centrada respecto a la ventana principal
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		// Definición de la paleta de colores para mantener coherencia visual
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color naranjaPalo = new Color(244, 162, 97);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(210, 214, 220);

		// Panel de contenido principal usando GridBagLayout para centrar la "tarjeta"
		// de login
		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(fondoVentana);
		setContentPane(contentPane);

		// Panel que contiene el formulario (diseño tipo tarjeta/card)
		JPanel panelTarjeta = new JPanel();
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setLayout(new BoxLayout(panelTarjeta, BoxLayout.Y_AXIS)); // Apila elementos verticalmente
		panelTarjeta.setPreferredSize(new Dimension(390, 370));
		// Borde compuesto: una línea gris exterior y un margen interno (padding) de
		// 25-30px
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(220, 224, 230), 1, true),
				BorderFactory.createEmptyBorder(25, 30, 25, 30)));

		// Configuración del icono de usuario
		JLabel lblIcono = new JLabel();
		lblIcono.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icono = new ImageIcon("src/img/iconoPersonaEditado.png");

		// Si la imagen existe la pone, si no, pone un texto alternativo
		if (icono.getIconWidth() > 0) {
			lblIcono.setIcon(icono);
		} else {
			lblIcono.setText("Usuario");
			lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 20));
			lblIcono.setForeground(naranjaPalo);
		}

		// Textos de cabecera del login
		JLabel lblTitulo = new JLabel("Iniciar sesión");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Introduce tu usuario y contraseña");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		// Sub-panel para el formulario de entrada (etiquetas y campos)
		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(fondoTarjeta);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8); // Separación entre celdas del formulario
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Etiqueta y campo de texto para el Usuario
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUsuario.setForeground(colorTexto);

		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsuario.setPreferredSize(new Dimension(180, 32));
		txtUsuario.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));

		// Etiqueta y campo para la Contraseña
		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblContrasena.setForeground(colorTexto);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField.setPreferredSize(new Dimension(180, 32));
		passwordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));

		// Posicionamiento de los elementos en el grid del formulario
		gbc.gridx = 0; // Columna 0 para etiquetas
		gbc.gridy = 0; // Fila 0 para Usuario
		panelFormulario.add(lblUsuario, gbc);
		gbc.gridx = 1; // Columna 1 para campos de entrada
		gbc.gridy = 0; // Fila 0 para Usuario
		panelFormulario.add(txtUsuario, gbc);
		gbc.gridx = 0; // Columna 0 para etiquetas
		gbc.gridy = 1; // Fila 1 para Contraseña
		panelFormulario.add(lblContrasena, gbc);
		gbc.gridx = 1; // Columna 1 para campos de entrada
		gbc.gridy = 1; // Fila 1 para Contraseña
		panelFormulario.add(passwordField, gbc); // Agrega el campo de contraseña al formulario

		// Panel para los botones inferiores (Cancelar y Entrar)
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		panelBotones.setBackground(fondoTarjeta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fuente del boton
		btnCancelar.setPreferredSize(new Dimension(120, 36)); // Tamaño del boton
		btnCancelar.setFocusPainted(false); // Elimina el borde de enfoque al hacer clic
		btnCancelar.setBackground(new Color(240, 240, 240)); // Color de fondo del botón
		btnCancelar.setForeground(colorTexto); // Color del texto del botón
		btnCancelar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true)); // Borde del botón
		btnCancelar.addActionListener(this);

		btnComprobar = new JButton("Entrar");
		btnComprobar.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fuente del boton
		btnComprobar.setPreferredSize(new Dimension(120, 36)); // Tamaño del boton
		btnComprobar.setFocusPainted(false); // Elimina el borde de enfoque al hacer clic
		btnComprobar.setBackground(naranjaPalo); // Color de fondo del botón
		btnComprobar.setForeground(Color.WHITE); // Color del texto del botón
		btnComprobar.setOpaque(true); // Necesario para que el color de fondo se muestre en algunos sistemas
		btnComprobar.setContentAreaFilled(true); // Asegura que el área del botón se rellene con el color de fondo
		btnComprobar.setBorderPainted(false); // Elimina el borde del botón
		btnComprobar.addActionListener(this);
		
		panelBotones.add(btnCancelar);
		panelBotones.add(btnComprobar);

		// Ensamblaje final de todos los elementos en la "tarjeta"
		panelTarjeta.add(lblIcono);
		panelTarjeta.add(Box.createVerticalStrut(10)); // Espaciado vertical
		panelTarjeta.add(lblTitulo);
		panelTarjeta.add(Box.createVerticalStrut(6));
		panelTarjeta.add(lblSubtitulo);
		panelTarjeta.add(Box.createVerticalStrut(20));
		panelTarjeta.add(panelFormulario);
		panelTarjeta.add(Box.createVerticalStrut(18));
		panelTarjeta.add(panelBotones);

		// Se añade la tarjeta al panel de contenido
		contentPane.add(panelTarjeta);

		// Hace que el botón "Entrar" se active al pulsar la tecla Enter
		getRootPane().setDefaultButton(btnComprobar);
	}

	/**
	 * Procesa los eventos de acción de los botones.
	 * <ul>
	 * <li>Si se pulsa "Cancelar": Cierra el diálogo.</li>
	 * <li>Si se pulsa "Entrar": Captura los datos, intenta validar el acceso y abre
	 * el menú de administración si los datos son correctos.</li>
	 * </ul>
	 * * @param e El evento de acción capturado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Acción para el botón cancelar
		if (e.getSource() == btnCancelar) {
			dispose();
		}

		// Acción para el botón entrar (validación)
		if (e.getSource() == btnComprobar) {
			// Crea un objeto modelo para transportar los datos
			Usuario usuario = new Usuario();
			usuario.setNombre(txtUsuario.getText().trim()); // .trim() quita espacios accidentales
			usuario.setClave(new String(passwordField.getPassword())); // Convierte char[] a String

			try {
				// Llama al método estático login de la clase Principal para validar las
				// credenciales
				Principal.login(usuario);

				// Si no lanza excepción, el login es exitoso
				dispose(); // Cierra el login
				VMenuAdmin vM = new VMenuAdmin(this, true); // Abre menú admin
				vM.setVisible(true);
				// Después de cerrar el menú admin, se vuelve a mostrar el login para permitir
			} catch (LoginException e1) {
				// Captura el error de credenciales incorrectas
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de acceso",
						JOptionPane.ERROR_MESSAGE);
				passwordField.setText(""); // Limpia la clave
				passwordField.requestFocus(); // Pone el foco para reintentar
			}
		}
	}
}