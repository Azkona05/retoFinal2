package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnInicioSesion;
	private JButton btnAcceder;

	public VPrincipal() {

		setTitle("Tartanga Music");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 550);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		Color fondo = new Color(245, 247, 250);
		Color header = new Color(255, 255, 255);
		Color azul = new Color(52, 120, 246);
		Color texto = new Color(40, 40, 40);
		Color gris = new Color(120, 120, 120);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(fondo);
		setContentPane(contentPane);

		JPanel panelTop = new JPanel(new BorderLayout());
		panelTop.setBackground(header);
		panelTop.setBorder(new EmptyBorder(15, 25, 15, 25));

		JLabel titulo = new JLabel("Tartanga Music");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setForeground(texto);

		btnInicioSesion = new JButton("Iniciar sesión");
		estiloBotonPrimario(btnInicioSesion, azul);
		btnInicioSesion.setPreferredSize(new Dimension(150, 40));
		btnInicioSesion.addActionListener(this);

		panelTop.add(titulo, BorderLayout.WEST);
		panelTop.add(btnInicioSesion, BorderLayout.EAST);

		contentPane.add(panelTop, BorderLayout.NORTH);

		JPanel centro = new JPanel(new GridBagLayout());
		centro.setBackground(fondo);

		JPanel bloque = new JPanel();
		bloque.setLayout(new BoxLayout(bloque, BoxLayout.Y_AXIS));
		bloque.setBackground(fondo);

		JLabel icono = new JLabel("♪");
		icono.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 80));
		icono.setForeground(azul);
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel bienvenida = new JLabel("Bienvenido a Tartanga Music");
		bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 30));
		bienvenida.setForeground(texto);
		bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel linea1 = new JLabel("Gestiona música, usuarios y contenido");
		JLabel linea2 = new JLabel("de forma sencilla.");
		JLabel linea3 = new JLabel("Accede con tu cuenta para continuar.");

		JLabel[] lineas = { linea1, linea2, linea3 };

		for (JLabel l : lineas) {
			l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			l.setForeground(gris);
			l.setAlignmentX(Component.CENTER_ALIGNMENT);
		}

		btnAcceder = new JButton("Acceder");
		estiloBotonPrimario(btnAcceder, azul);
		btnAcceder.setPreferredSize(new Dimension(180, 45));
		btnAcceder.setMaximumSize(new Dimension(180, 45));
		btnAcceder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAcceder.addActionListener(this);

		bloque.add(icono);
		bloque.add(Box.createVerticalStrut(15));
		bloque.add(bienvenida);
		bloque.add(Box.createVerticalStrut(20));
		bloque.add(linea1);
		bloque.add(Box.createVerticalStrut(5));
		bloque.add(linea2);
		bloque.add(Box.createVerticalStrut(5));
		bloque.add(linea3);
		bloque.add(Box.createVerticalStrut(30));
		bloque.add(btnAcceder);

		centro.add(bloque);

		contentPane.add(centro, BorderLayout.CENTER);

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

		footer.add(autores, BorderLayout.WEST);
		footer.add(curso, BorderLayout.EAST);

		contentPane.add(footer, BorderLayout.SOUTH);
	}

	private void estiloBotonPrimario(JButton b, Color color) {
		b.setBackground(color);
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Segoe UI", Font.BOLD, 14));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnInicioSesion || e.getSource() == btnAcceder) {
			VLogin login = new VLogin(this, true);
			login.setVisible(true);
			this.dispose();
		}
	}
}