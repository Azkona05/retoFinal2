package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class VPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnInicioSesion;
	private JButton btnAcceder;

	public VPrincipal() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 600, 400);
	    setLocationRelativeTo(null); // Centra la ventana en la pantalla

	    contentPane = new JPanel(new BorderLayout());
	    setContentPane(contentPane);

	    // --- PARTE SUPERIOR (Header) ---
	    JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    btnInicioSesion = new JButton("Iniciar Sesión");
	    btnInicioSesion.setFocusPainted(false);
	    panelSuperior.add(btnInicioSesion);
	    contentPane.add(panelSuperior, BorderLayout.NORTH);

	    // --- PARTE CENTRAL (Cuerpo) ---
	    JPanel panelCentral = new JPanel(new GridBagLayout()); // Para centrar todo
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0; 
	    gbc.insets = new Insets(10, 0, 10, 0);

	    JLabel lblLogo = new JLabel(new ImageIcon("src/resources/logo_grande.png")); // Si tienes logo
	    JLabel lblBienvenida = new JLabel("Tartanga Music");
	    lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
	    
	    JLabel lblSubtitulo = new JLabel("");
	    lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
	    lblSubtitulo.setForeground(Color.GRAY);

	    panelCentral.add(lblLogo, gbc);
	    gbc.gridy = 1;
	    panelCentral.add(lblBienvenida, gbc);
	    gbc.gridy = 2;
	    panelCentral.add(lblSubtitulo, gbc);

	    contentPane.add(panelCentral, BorderLayout.CENTER);

	    // --- PARTE INFERIOR (Footer) ---
	    JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelInferior.setBorder(new EtchedBorder());
	    JLabel lblEstado = new JLabel("An Azkona | Nora Yakoubi | Ricardo Soza | Jon Ander Varela || 2026 || Reto Final - 1º DAM");
	    lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
	    panelInferior.add(lblEstado);
	    contentPane.add(panelInferior, BorderLayout.SOUTH);
	    
	    btnInicioSesion.addActionListener(this);
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
