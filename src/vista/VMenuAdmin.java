package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VMenuAdmin extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBaja, btnAlta, btnModificar, btnConsultar;

	/**
	 * Create the dialog.
	 */
	public VMenuAdmin(VLogin padre, boolean modal) {
		super(padre);
		setModal(modal);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		btnAlta = new JButton("ALTA");
		btnAlta.addActionListener(this);
		btnAlta.setBounds(60, 62, 128, 39);
		contentPanel.add(btnAlta);

		btnBaja = new JButton("BAJA");
		btnBaja.setBounds(256, 62, 128, 39);
		contentPanel.add(btnBaja);

		btnModificar = new JButton("MODIFICAR");
		btnModificar.setBounds(60, 170, 128, 39);
		contentPanel.add(btnModificar);

		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setBounds(256, 170, 128, 39);
		contentPanel.add(btnConsultar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAlta)) {
			VAlta alta = new VAlta(this,true);
			alta.setVisible(true);
			alta.toFront();
			//this.setVisible(false);
			

		} else if (e.getSource().equals(btnBaja)) {

		} else if (e.getSource().equals(btnModificar)) {

		} else if (e.getSource().equals(btnConsultar)) {

		}
	}
}
