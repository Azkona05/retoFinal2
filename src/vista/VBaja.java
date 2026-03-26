package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VBaja extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnCancion;
	private JButton btnAlbum;
	private JButton bntArtista;
	private JButton bntVolver;

	/**
	 * Create the dialog.
	 * 
	 * @param vMenuAdmin
	 */
	public VBaja(VMenuAdmin padre, boolean modal) {
		super(padre);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Elige que quieres eliminar:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(109, 34, 203, 25);
		contentPanel.add(lblNewLabel);

		bntArtista = new JButton("Artista");
		bntArtista.addActionListener(this);
		bntArtista.setBounds(25, 117, 85, 21);
		contentPanel.add(bntArtista);

		btnCancion = new JButton("Cancion");
		btnCancion.addActionListener(this);
		btnCancion.setBounds(165, 117, 85, 21);
		contentPanel.add(btnCancion);

		btnAlbum = new JButton("Album");
		btnAlbum.addActionListener(this);
		btnAlbum.setBounds(316, 117, 85, 21);
		contentPanel.add(btnAlbum);

		bntVolver = new JButton("Volver");
		bntVolver.addActionListener(this);
		bntVolver.setBounds(341, 222, 85, 21);
		contentPanel.add(bntVolver);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
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
			this.dispose();
		}

	}
}
