package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import exception.LoginException;
import main.Principal;
import modelo.Usuario;

/**
 * Clase que representa la ventana de login de la aplicación.
 * Permite al usuario introducir su nombre de usuario y contraseña para autenticarse.
 * Implementa {@link ActionListener} para manejar los eventos de los botones.
 * 
 * Se pueden realizar las siguientes acciones:
 *   Autenticarse mediante el botón "Comprobar".
 *   Cancelar y cerrar la ventana mediante el botón "Cancelar" o la tecla Escape.
 * 
 * 
 * Al presionar Enter, se activa el botón "Comprobar" por defecto.
 * 
 * @author An Azkona, Ander Arilla, Maleck Benigno, Nora Yakoubi
 */
public class VLogin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel lblUsuario, lblContra;
	private JPasswordField passwordField;
	private JButton btnComprobar;
	private JButton btnCancelar;

	/**
	 * Constructor de la ventana de login.
	 */
	public VLogin(VPrincipal padre, boolean modal) {
		super(padre,modal);
		this.setModal(modal);
		setTitle("Iniciar Sesion");
		setBounds(100, 100, 705, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);

		lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsuario.setBounds(119, 109, 147, 29);
		contentPane.add(lblUsuario);

		lblContra = new JLabel("Contraseña: ");
		lblContra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblContra.setBounds(119, 174, 147, 29);
		contentPane.add(lblContra);

		btnComprobar = new JButton("Comprobar");
		btnComprobar.addActionListener(this);
		btnComprobar.setBounds(413, 288, 122, 29);
		contentPane.add(btnComprobar);
		btnComprobar.setBackground(Color.WHITE);
		btnComprobar.setBorder(new LineBorder(Color.GREEN));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(159, 288, 112, 29);
		contentPane.add(btnCancelar);
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBorder(new LineBorder(Color.RED));

		txtUsuario = new JTextField();
		txtUsuario.setBounds(360, 109, 175, 29);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(360, 174, 175, 29);
		contentPane.add(passwordField);

		JLabel lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon("src\\resources\\iconoPersonaEditado.png"));
		lblImagen.setBounds(319, 11, 67, 87);
		contentPane.add(lblImagen);

		// ✅ Enter activa btnComprobar
		getRootPane().setDefaultButton(btnComprobar);

		// ✅ Escape cierra el diálogo (como btnCancelar)
		InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getRootPane().getActionMap();

		im.put(KeyStroke.getKeyStroke("ESCAPE"), "cancelar");
		am.put("cancelar", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra el diálogo
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnComprobar)) {
			this.dispose();
			comprobar();
		} else if (e.getSource().equals(btnCancelar)) {
		
		}
	}

	public void comprobar() {
		Usuario usuario = new Usuario();
		usuario.setNombre(txtUsuario.getText());
		usuario.setClave(new String(passwordField.getPassword()));
		try {
			Principal.login(usuario);
			VMenuAdmin menu = new VMenuAdmin(this, true);
			menu.setVisible(true);
		} catch (LoginException e) {
			e.visualizarMsg();
		}
	}
}