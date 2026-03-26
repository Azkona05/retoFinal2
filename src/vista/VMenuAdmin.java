package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class VMenuAdmin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnAlta;
	private JButton btnBaja;
	private JButton btnModificar;
	private JButton btnConsultar;
	private JButton btnCerrar;
	private JLabel lblFooter;
	private Timer timerReloj;

	public VMenuAdmin(VLogin padre, boolean modal) {
		super(padre, modal);

		setTitle("Panel de Administración - Tartanga Music");
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
		contentPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		setContentPane(contentPane);

		JPanel panelCentral = new JPanel(new BorderLayout(0, 20));
		panelCentral.setBackground(fondoTarjeta);
		panelCentral.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				BorderFactory.createEmptyBorder(25, 25, 25, 25)));

		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(fondoTarjeta);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));

		JLabel lblPanel = new JLabel("Administrador");
		lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPanel.setForeground(colorPrimario);
		lblPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("Gestión de los datos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblTitulo.setForeground(colorTexto);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Seleccione una operación para continuar");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSubtitulo.setForeground(colorSecundario);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

		panelCabecera.add(lblPanel);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblTitulo);
		panelCabecera.add(Box.createVerticalStrut(8));
		panelCabecera.add(lblSubtitulo);

		panelCentral.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 18, 18));
		panelAcciones.setBackground(fondoTarjeta);
		panelAcciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		btnAlta = new JButton("Dar de alta");
		btnAlta.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAlta.setPreferredSize(new Dimension(220, 85));
		btnAlta.setFocusPainted(false);
		btnAlta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAlta.setBackground(Color.WHITE);
		btnAlta.setForeground(colorTexto);
		btnAlta.setBorder(new LineBorder(colorBorde, 1, true));
		btnAlta.addActionListener(this);
		btnAlta.setToolTipText("Añadir nuevos artistas");

		btnBaja = new JButton("Dar de baja");
		btnBaja.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBaja.setPreferredSize(new Dimension(220, 85));
		btnBaja.setFocusPainted(false);
		btnBaja.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBaja.setBackground(Color.WHITE);
		btnBaja.setForeground(colorTexto);
		btnBaja.setBorder(new LineBorder(colorBorde, 1, true));
		btnBaja.addActionListener(this);
		btnBaja.setToolTipText("Eliminar registros");

		btnModificar = new JButton("Modificar");
		btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnModificar.setPreferredSize(new Dimension(220, 85));
		btnModificar.setFocusPainted(false);
		btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnModificar.setBackground(Color.WHITE);
		btnModificar.setForeground(colorTexto);
		btnModificar.setBorder(new LineBorder(colorBorde, 1, true));
		btnModificar.addActionListener(this);
		btnModificar.setToolTipText("Editar información");

		btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnConsultar.setPreferredSize(new Dimension(220, 85));
		btnConsultar.setFocusPainted(false);
		btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConsultar.setBackground(Color.WHITE);
		btnConsultar.setForeground(colorTexto);
		btnConsultar.setBorder(new LineBorder(colorBorde, 1, true));
		btnConsultar.addActionListener(this);
		btnConsultar.setToolTipText("Ver lista completa");

		panelAcciones.add(btnAlta);
		panelAcciones.add(btnBaja);
		panelAcciones.add(btnModificar);
		panelAcciones.add(btnConsultar);

		panelCentral.add(panelAcciones, BorderLayout.CENTER);

		contentPane.add(panelCentral, BorderLayout.CENTER);

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
		btnCerrar.setBackground(colorPrimario);
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setBorderPainted(false);
		btnCerrar.setOpaque(true);
		btnCerrar.addActionListener(this);

		panelInferior.add(lblFooter, BorderLayout.WEST);
		panelInferior.add(btnCerrar, BorderLayout.EAST);

		contentPane.add(panelInferior, BorderLayout.SOUTH);

		timerReloj = new Timer(1000, this);
		timerReloj.start();

		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void dispose() {
		if (timerReloj != null && timerReloj.isRunning()) {
			timerReloj.stop();
		}
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAlta) {
			VAlta alta = new VAlta(this, true);
			alta.setVisible(true);
		} else if (e.getSource() == btnBaja) {
			VBaja baja = new VBaja(this, true);
			baja.setVisible(true);
		} else if (e.getSource() == btnConsultar) {
			VConsultar consultar = new VConsultar(this, true);
			consultar.setVisible(true);
		} else if (e.getSource() == btnModificar) {
			// Aquí abrirías la ventana de modificar cuando la tengas hecha
		} else if (e.getSource() == btnCerrar) {
			dispose();
		} else if (e.getSource() == timerReloj) {
			String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			lblFooter.setText("Sesión de Administrador activa  |  " + hora + "  |  2026");
		}
	}
}
