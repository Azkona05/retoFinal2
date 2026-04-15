package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import exception.LoginException;
import main.Principal;
import modelo.Artista;

public class VEliminarArtista extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;
	private JButton btnEliminarArtista;
	private JButton btnVolver;
	private JLabel lblInfo;

	public VEliminarArtista(VBaja padre, boolean modal) {
		super(padre, modal);

		setTitle("Eliminar artista");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(220, 53, 69);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);
		Color colorSeleccion = new Color(232, 242, 252);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		JPanel panelCabecera = new JPanel(new BorderLayout(0, 8));
		panelCabecera.setBackground(fondoTarjeta);

		JLabel lblTitulo = new JLabel("Eliminar artista");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		lblInfo = new JLabel("Selecciona un artista de la tabla");
		lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblInfo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblInfo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		String[] columnas = { "ID", "Nombre", "Tipo" };

		model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(235, 235, 235));
		table.setSelectionBackground(colorSeleccion);
		table.setSelectionForeground(Color.BLACK);
		table.setFillsViewportHeight(true);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(colorTexto);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.getColumnModel().getColumn(2).setPreferredWidth(160);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));

		panelTarjeta.add(scrollPane, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setBackground(fondoTarjeta);

		JLabel lblAviso = new JLabel("Se eliminará el artista seleccionado");
		lblAviso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblAviso.setForeground(colorSecundario);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelBotones.setBackground(fondoTarjeta);

		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBackground(new Color(240, 240, 240));
		btnVolver.setForeground(colorTexto);
		btnVolver.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnVolver.addActionListener(this);

		btnEliminarArtista = new JButton("Eliminar seleccionado");
		btnEliminarArtista.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminarArtista.setFocusPainted(false);
		btnEliminarArtista.setBorderPainted(false);
		btnEliminarArtista.setOpaque(true);
		btnEliminarArtista.setBackground(colorPrimario);
		btnEliminarArtista.setForeground(Color.WHITE);
		btnEliminarArtista.addActionListener(this);

		panelBotones.add(btnVolver);
		panelBotones.add(btnEliminarArtista);

		panelInferior.add(lblAviso, BorderLayout.WEST);
		panelInferior.add(panelBotones, BorderLayout.EAST);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		try {
			Artista a = new Artista();
			Object[][] datos = Principal.devolverArtistas(a);

			model.setRowCount(0);

			for (Object[] fila : datos) {
				model.addRow(fila);
			}

			lblInfo.setText("Artistas cargados: " + model.getRowCount());

		} catch (LoginException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar los artistas");
		}

		pack();
		setLocationRelativeTo(padre);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnVolver)) {
			dispose();
		}

		else if (e.getSource().equals(btnEliminarArtista)) {
			int filaSeleccionada = table.getSelectedRow();

			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona un artista de la tabla");
				return;
			}

			int confirmacion = JOptionPane.showConfirmDialog(this,
					"¿Seguro que quieres eliminar el artista seleccionado?", "Confirmar eliminación",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			int idArtista = Integer.parseInt(model.getValueAt(filaSeleccionada, 0).toString());

			try {
				boolean eliminado = Principal.eliminarArtista(idArtista);

				if (eliminado) {
					model.removeRow(filaSeleccionada);
					lblInfo.setText("Artistas cargados: " + model.getRowCount());
					JOptionPane.showMessageDialog(this, "Artista eliminado correctamente");
				} else {
					JOptionPane.showMessageDialog(this, "No existe ese artista");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al eliminar");
			}
		}
	}
}