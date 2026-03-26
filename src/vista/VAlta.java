package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class VAlta extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton buttonArtista;
	private JButton btnMusica;
	private JButton btnAlbum;
	private JButton btnVolver;

	public VAlta(VMenuAdmin padre, boolean modal) {
		super(padre, modal);

		setTitle("Dar de alta");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(52, 120, 246);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);

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

		JLabel lblSeccion = new JLabel("Administrador");
		lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblSeccion.setForeground(colorPrimario);
		lblSeccion.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("Dar de alta");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Selecciona qué tipo de elemento quieres añadir");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		panelCabecera.add(lblSeccion);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelBotones = new JPanel(new GridLayout(1, 3, 18, 18));
		panelBotones.setBackground(fondoTarjeta);
		panelBotones.setBorder(new EmptyBorder(10, 10, 10, 10));

		buttonArtista = new JButton("Artista");
		buttonArtista.setFont(new Font("Segoe UI", Font.BOLD, 16));
		buttonArtista.setPreferredSize(new Dimension(150, 80));
		buttonArtista.setFocusPainted(false);
		buttonArtista.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonArtista.setBackground(Color.WHITE);
		buttonArtista.setForeground(colorTexto);
		buttonArtista.setBorder(new LineBorder(colorBorde, 1, true));
		buttonArtista.addActionListener(this);

		btnMusica = new JButton("Música");
		btnMusica.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnMusica.setPreferredSize(new Dimension(150, 80));
		btnMusica.setFocusPainted(false);
		btnMusica.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnMusica.setBackground(Color.WHITE);
		btnMusica.setForeground(colorTexto);
		btnMusica.setBorder(new LineBorder(colorBorde, 1, true));
		btnMusica.addActionListener(this);

		btnAlbum = new JButton("Álbum");
		btnAlbum.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAlbum.setPreferredSize(new Dimension(150, 80));
		btnAlbum.setFocusPainted(false);
		btnAlbum.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAlbum.setBackground(Color.WHITE);
		btnAlbum.setForeground(colorTexto);
		btnAlbum.setBorder(new LineBorder(colorBorde, 1, true));
		btnAlbum.addActionListener(this);

		panelBotones.add(buttonArtista);
		panelBotones.add(btnMusica);
		panelBotones.add(btnAlbum);

		panelTarjeta.add(panelBotones, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelInferior.setBackground(fondoTarjeta);

		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBackground(new Color(240, 240, 240));
		btnVolver.setForeground(colorTexto);
		btnVolver.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnVolver.addActionListener(this);

		panelInferior.add(btnVolver);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnVolver) {
			dispose();

		} else if (e.getSource() == buttonArtista) {
			VArtista vArti = new VArtista(this, true);
			vArti.setVisible(true);
			dispose();

		} else if (e.getSource() == btnMusica) {
			VAltaCancionAlbum vC = new VAltaCancionAlbum(true);
			vC.setVisible(true);

		} else if (e.getSource() == btnAlbum) {
			VAlbum vAlbu = new VAlbum(true);
			vAlbu.setVisible(true);
			vAlbu.toFront();
		}
	}
}