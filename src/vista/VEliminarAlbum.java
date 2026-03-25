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

public class VEliminarAlbum extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton btnEliminarAlbum;
	private JButton btnVolver;

	/**
	 * Create the dialog.
	 */
	public VEliminarAlbum(VBaja padre, boolean modal) {
		super(padre);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Introduce el ID del album a eliminar:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel.setBounds(86, 69, 264, 13);
			contentPanel.add(lblNewLabel);
		}
		
		textField = new JTextField();
		textField.setBounds(156, 99, 96, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(this);
		btnVolver.setBounds(341, 222, 85, 21);
		contentPanel.add(btnVolver);
		
		btnEliminarAlbum = new JButton("ELIMINAR");
		btnEliminarAlbum.addActionListener(this);
		btnEliminarAlbum.setBounds(166, 128, 85, 21);
		contentPanel.add(btnEliminarAlbum);
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
		}else if (e.getSource().equals(btnEliminarAlbum)) {

			int idAlbum = Integer.parseInt(textField.getText());

			try {
				boolean eliminado = Principal.eliminarAlbum(idAlbum);

				if (eliminado) {
					JOptionPane.showMessageDialog(this, "Álbum eliminado correctamente");
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "No existe ese álbum");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al eliminar");
			}
		}
				
		
	}
}
