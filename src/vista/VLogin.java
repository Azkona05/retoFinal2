package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

public class VLogin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private JButton btnComprobar;
	private JButton btnCancelar;

	public VLogin(VPrincipal padre, boolean modal) {
		super(padre, modal);

		setTitle("Iniciar sesión");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(520, 460);
		setLocationRelativeTo(padre);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(52, 120, 246);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(210, 214, 220);

		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(fondoVentana);
		setContentPane(contentPane);

		JPanel panelTarjeta = new JPanel();
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setLayout(new BoxLayout(panelTarjeta, BoxLayout.Y_AXIS));
		panelTarjeta.setPreferredSize(new Dimension(390, 370));
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(220, 224, 230), 1, true),
				BorderFactory.createEmptyBorder(25, 30, 25, 30)));

		JLabel lblIcono = new JLabel();
		lblIcono.setAlignmentX(CENTER_ALIGNMENT);

		ImageIcon icono = new ImageIcon("src/resources/iconoPersonaEditado.png");
		if (icono.getIconWidth() > 0) {
			lblIcono.setIcon(icono);
		} else {
			lblIcono.setText("Usuario");
			lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 20));
			lblIcono.setForeground(colorPrimario);
		}

		JLabel lblTitulo = new JLabel("Iniciar sesión");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Introduce tu usuario y contraseña");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(fondoTarjeta);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUsuario.setForeground(colorTexto);

		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsuario.setPreferredSize(new Dimension(180, 32));
		txtUsuario.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));

		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblContrasena.setForeground(colorTexto);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField.setPreferredSize(new Dimension(180, 32));
		passwordField.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(lblUsuario, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(txtUsuario, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(lblContrasena, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(passwordField, gbc);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		panelBotones.setBackground(fondoTarjeta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnCancelar.setPreferredSize(new Dimension(120, 36));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(240, 240, 240));
		btnCancelar.setForeground(colorTexto);
		btnCancelar.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnCancelar.addActionListener(this);

		btnComprobar = new JButton("Entrar");
		btnComprobar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnComprobar.setPreferredSize(new Dimension(120, 36));
		btnComprobar.setFocusPainted(false);
		btnComprobar.setBackground(colorPrimario);
		btnComprobar.setForeground(Color.WHITE);
		btnComprobar.setBorder(new LineBorder(colorPrimario, 1, true));
		btnComprobar.addActionListener(this);

		panelBotones.add(btnCancelar);
		panelBotones.add(btnComprobar);

		panelTarjeta.add(lblIcono);
		panelTarjeta.add(Box.createVerticalStrut(10));
		panelTarjeta.add(lblTitulo);
		panelTarjeta.add(Box.createVerticalStrut(6));
		panelTarjeta.add(lblSubtitulo);
		panelTarjeta.add(Box.createVerticalStrut(20));
		panelTarjeta.add(panelFormulario);
		panelTarjeta.add(Box.createVerticalStrut(18));
		panelTarjeta.add(panelBotones);

		contentPane.add(panelTarjeta);

		getRootPane().setDefaultButton(btnComprobar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			dispose();
		}
		if (e.getSource() == btnComprobar) {
			Usuario usuario = new Usuario();
			usuario.setNombre(txtUsuario.getText().trim());
			usuario.setClave(new String(passwordField.getPassword()));

			try {
				Principal.login(usuario);
				dispose();
				VMenuAdmin vM = new VMenuAdmin(this, true);
				vM.setVisible(true);
			} catch (LoginException e1) {
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
				passwordField.setText("");
				passwordField.requestFocus();
			}
		}
	}
}