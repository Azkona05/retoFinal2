package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VAlta extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton buttonArtista;
	private JButton btnMusica;
	private JButton btnAlbum;
	private JButton btnVolver;
	private VMenuAdmin vMenuAdmin;

	/**
	 * Launch the application.
	 *
	 * public static void main(String[] args) { try { VAlta dialog = new VAlta();
	 * dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * /** Create the dialog.
	 * 
	 * @param vMenuAdmin2
	 * @param vMenuAdmin
	 */
	public VAlta(VMenuAdmin padre, boolean modal) {
		
		super(padre,modal);
		this.vMenuAdmin=padre;
		//setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblLabel = new JLabel("¿Qué quieres dar de alta?");
		lblLabel.setForeground(new Color(0, 0, 0));
		lblLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLabel.setBounds(10, 21, 416, 31);
		contentPanel.add(lblLabel);

		buttonArtista = new JButton("Artista");
		buttonArtista.addActionListener(this);
		buttonArtista.setBounds(10, 93, 121, 47);
		contentPanel.add(buttonArtista);

		btnMusica = new JButton("Música");
		btnMusica.addActionListener(this);
		btnMusica.setBounds(151, 93, 121, 47);
		contentPanel.add(btnMusica);

		btnAlbum = new JButton("Albúm");
		btnAlbum.addActionListener(this);
		btnAlbum.setBounds(292, 93, 121, 47);
		contentPanel.add(btnAlbum);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(this);
		btnVolver.setBounds(292, 232, 121, 21);
		contentPanel.add(btnVolver);
		//this.setAlwaysOnTop(true);// esto es porque la ventana alta se ponia por la cara detras de eclipse
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnVolver) {
			this.dispose();
			//this.setVisible(false);
			//vMenuAdmin.setVisible(true);
			

		} else if (e.getSource() == buttonArtista) {
			VArtista vArti = new VArtista(true);
			vArti.setVisible(true);
			this.dispose();
			
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
