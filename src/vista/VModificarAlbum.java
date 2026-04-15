package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controlador.DaoImplementacion;
import main.Principal;

public class VModificarAlbum extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	private JTextField txtNombre;
	private JTextField txtIdArtista;

	private JButton btnGuardar;
	private JButton btnVolver;

	private JLabel lblInfo;
	private JLabel lblTipoAlbum;

	private DaoImplementacion dao;

	private int idSeleccionado = -1;

	public VModificarAlbum(JDialog padre, boolean modal) {
		super(padre, modal);

		dao = new DaoImplementacion();

		setTitle("Modificar álbum");
		setResizable(false);

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorTexto = new Color(40, 40, 40);
		Color colorBorde = new Color(220, 224, 230);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		JPanel tarjeta = new JPanel(new BorderLayout(20, 20));
		tarjeta.setBackground(fondoTarjeta);
		tarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(tarjeta);

		JPanel cabecera = new JPanel(new BorderLayout(0, 8));
		cabecera.setBackground(fondoTarjeta);

		JLabel titulo = new JLabel("Modificar álbum");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		titulo.setForeground(colorTexto);

		lblInfo = new JLabel("Selecciona un álbum de la tabla");

		cabecera.add(titulo, BorderLayout.NORTH);
		cabecera.add(lblInfo, BorderLayout.SOUTH);

		tarjeta.add(cabecera, BorderLayout.NORTH);

		String[] columnas = { "ID", "Nombre", "ID Artista" };

		model = new DefaultTableModel(columnas, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500, 220));

		tarjeta.add(scroll, BorderLayout.CENTER);

		JPanel editar = new JPanel(new GridLayout(3, 2, 10, 10));
		editar.setBackground(fondoTarjeta);

		txtNombre = new JTextField();
		txtIdArtista = new JTextField();

		editar.add(new JLabel("Nombre:"));
		editar.add(txtNombre);

		editar.add(new JLabel("ID Artista:"));
		editar.add(txtIdArtista);

		lblTipoAlbum = new JLabel("Tipo de álbum: ");
		editar.add(lblTipoAlbum);

		tarjeta.add(editar, BorderLayout.SOUTH);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.setBackground(fondoTarjeta);

		btnVolver = new JButton("Volver");
		btnGuardar = new JButton("Guardar cambios");

		btnGuardar.setBackground(new Color(244, 162, 97));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFocusPainted(false);
		btnGuardar.setOpaque(true);
		btnGuardar.setBorderPainted(false);

		botones.add(btnVolver);
		botones.add(btnGuardar);

		contentPane.add(botones, BorderLayout.SOUTH);

		btnVolver.addActionListener(this);
		btnGuardar.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int fila = table.getSelectedRow();

				idSeleccionado = Integer.parseInt(model.getValueAt(fila, 0).toString());

				txtNombre.setText(model.getValueAt(fila, 1).toString());

				txtIdArtista.setText(model.getValueAt(fila, 2).toString());

				String tipo = dao.tipoAlbum(idSeleccionado);
				lblTipoAlbum.setText("Tipo de álbum: " + tipo);
			}
		});

		cargarAlbumes();

		pack();
		setLocationRelativeTo(padre);
	}

	private void cargarAlbumes() {
		model.setRowCount(0);
		try {
			Object[][] datos = dao.devolverAlbumesT();
			for (Object[] fila : datos) {
				model.addRow(fila);
			}
			lblInfo.setText("Álbumes cargados: " + model.getRowCount());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar álbumes");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnVolver) {
			dispose();
		}
		if (e.getSource() == btnGuardar) {
			if (idSeleccionado == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona un álbum");
				return;
			}
			try {

				String nombre = txtNombre.getText();
				int idArtista = Integer.parseInt(txtIdArtista.getText());
				boolean ok = Principal.modificarAlbum(idSeleccionado, nombre, idArtista);
				if (ok) {
					JOptionPane.showMessageDialog(this, "Álbum modificado");
					dispose();
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al modificar");
			}
		}
	}
}