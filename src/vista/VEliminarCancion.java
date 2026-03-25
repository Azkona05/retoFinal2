package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Principal;

public class VEliminarCancion extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton btnEliminarCancion;
	private JButton btnVolver;

	/**
	 * Create the dialog.
	 */
	public VEliminarCancion(VBaja padre, boolean modal) {
		super(padre);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Introduce el ID de la cancion a eliminar:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel.setBounds(82, 40, 272, 13);
			contentPanel.add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setBounds(171, 71, 96, 19);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			btnEliminarCancion = new JButton("ELIMINAR");
			btnEliminarCancion.addActionListener(this);
			btnEliminarCancion.setBounds(181, 100, 85, 21);
			contentPanel.add(btnEliminarCancion);
		}
		{
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(this);
			btnVolver.setBounds(341, 232, 85, 21);
			contentPanel.add(btnVolver);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnVolver)) {
			this.dispose();
		} else if (e.getSource().equals(btnEliminarCancion)) {

			int idCancion = Integer.parseInt(textField.getText());

			try {
				boolean eliminado = Principal.eliminarCancion(idCancion);

				if (eliminado) {
					JOptionPane.showMessageDialog(this, "Canción eliminada correctamente");
					this.dispose(); // opcional: cierra ventana
				} else {
					JOptionPane.showMessageDialog(this, "No existe esa canción");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al eliminar");
			}
		}

	}

}
