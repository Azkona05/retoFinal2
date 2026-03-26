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

public class VBaja extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnCancion;
	private JButton btnAlbum;
	private JButton bntArtista;
	private JButton bntVolver;

	public VBaja(VMenuAdmin padre, boolean modal) {
		super(padre, modal);

		setTitle("Dar de baja");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(220, 53, 69);
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

		JLabel lblTitulo = new JLabel("Eliminar elementos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Selecciona qué tipo de elemento quieres dar de baja");
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

		bntArtista = new JButton("Artista");
		bntArtista.setFont(new Font("Segoe UI", Font.BOLD, 16));
		bntArtista.setPreferredSize(new Dimension(150, 80));
		bntArtista.setFocusPainted(false);
		bntArtista.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bntArtista.setBackground(Color.WHITE);
		bntArtista.setForeground(colorTexto);
		bntArtista.setBorder(new LineBorder(colorBorde, 1, true));
		bntArtista.addActionListener(this);

		btnCancion = new JButton("Canción");
		btnCancion.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancion.setPreferredSize(new Dimension(150, 80));
		btnCancion.setFocusPainted(false);
		btnCancion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancion.setBackground(Color.WHITE);
		btnCancion.setForeground(colorTexto);
		btnCancion.setBorder(new LineBorder(colorBorde, 1, true));
		btnCancion.addActionListener(this);

		btnAlbum = new JButton("Álbum");
		btnAlbum.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAlbum.setPreferredSize(new Dimension(150, 80));
		btnAlbum.setFocusPainted(false);
		btnAlbum.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAlbum.setBackground(Color.WHITE);
		btnAlbum.setForeground(colorTexto);
		btnAlbum.setBorder(new LineBorder(colorBorde, 1, true));
		btnAlbum.addActionListener(this);

		panelBotones.add(bntArtista);
		panelBotones.add(btnCancion);
		panelBotones.add(btnAlbum);

		panelTarjeta.add(panelBotones, BorderLayout.CENTER);

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

		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancion)) {
			VEliminarCancion vCancion = new VEliminarCancion(this, true);
			vCancion.setVisible(true);
		} else if (e.getSource().equals(btnAlbum)) {
			VEliminarAlbum vAlbum = new VEliminarAlbum(this, true);
			vAlbum.setVisible(true);
		} else if (e.getSource().equals(bntArtista)) {
			VEliminarArtista vArtista = new VEliminarArtista(this, true);
			vArtista.setVisible(true);
		} else if (e.getSource().equals(bntVolver)) {
			dispose();
		}
	}
}