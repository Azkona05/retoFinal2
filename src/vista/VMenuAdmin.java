package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class VMenuAdmin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnBaja, btnAlta, btnModificar, btnConsultar, btnCerrar;;
	private JLabel lblFooter;
	private Timer timerReloj;

	public VMenuAdmin(VLogin padre, boolean modal) {
		super(padre, modal);
		setTitle("Panel de Administración - Tartanga Music");
		setSize(600, 450);
		setLocationRelativeTo(padre);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		setContentPane(contentPane);

		// --- CABECERA ---
		JPanel headerPanel = new JPanel(new GridLayout(2, 1));
		JLabel lblTitulo = new JLabel("GESTIÓN DE LOS DATOS", JLabel.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

		JLabel lblSubtitulo = new JLabel("Seleccione una operación para continuar", JLabel.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(Color.GRAY);

		headerPanel.add(lblTitulo);
		headerPanel.add(lblSubtitulo);
		contentPane.add(headerPanel, BorderLayout.NORTH);

		JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 20, 20));

		btnAlta = crearBotonMenu("DAR DE ALTA", "Añadir nuevos artistas");
		btnBaja = crearBotonMenu("DAR DE BAJA", "Eliminar registros");
		btnModificar = crearBotonMenu("MODIFICAR", "Editar información");
		btnConsultar = crearBotonMenu("CONSULTAR", "Ver lista completa");

		panelAcciones.add(btnAlta);
		panelAcciones.add(btnBaja);
		panelAcciones.add(btnModificar);
		panelAcciones.add(btnConsultar);

		contentPane.add(panelAcciones, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new BorderLayout()); 
		panelInferior.setBorder(new javax.swing.border.EmptyBorder(5, 15, 5, 15));

		lblFooter = new JLabel("Sesión activa...", JLabel.CENTER);
		panelInferior.add(lblFooter, BorderLayout.CENTER);

		btnCerrar = new JButton("Cerrar Sesión");
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(this);
		panelInferior.add(btnCerrar, BorderLayout.EAST);

		contentPane.add(panelInferior, BorderLayout.SOUTH);

		timerReloj = new Timer(1000, this);
		timerReloj.start();
	}

	private JButton crearBotonMenu(String titulo, String toolTip) {
		JButton btn = new JButton(titulo);
		btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btn.setToolTipText(toolTip);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void dispose() {
		if (timerReloj != null && timerReloj.isRunning()) {
			timerReloj.stop();
			System.out.println("Timer detenido correctamente.");
		}
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAlta)) {
			VAlta alta = new VAlta(this, true);
			alta.setVisible(true);
		} else if (e.getSource().equals(btnBaja)) {
			VBaja baja = new VBaja(this, true);
			baja.setVisible(true);
		} else if (e.getSource().equals(btnConsultar)) {
			VConsultar consultar = new VConsultar(this, true);
			consultar.setVisible(true);
		} else if (e.getSource().equals(timerReloj)) {
			String hora = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
			lblFooter.setText("Sesión de Administrador activa  |  " + hora + "  |  2026");
		} else if (e.getSource().equals(btnCerrar)) {
			this.dispose();
		}
	}
}