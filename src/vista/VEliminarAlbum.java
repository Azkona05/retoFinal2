package vista;

// Importaciones de Swing y AWT para la interfaz gráfica
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

// Excepción personalizada del proyecto
import exception.LoginException;

// Clase principal donde están los métodos de lógica
import main.Principal;
/**
 * @author Nora Yakoubi
 */
public class VEliminarAlbum extends JDialog implements ActionListener {

	// Serial version para Swing
	private static final long serialVersionUID = 1L;

	// Componentes de la interfaz
	private JTable table; // tabla donde se muestran los álbumes
	private DefaultTableModel model; // modelo de datos de la tabla
	private JButton btnEliminarAlbum; // botón eliminar
	private JButton btnVolver; // botón volver
	private JLabel lblInfo; // label informativo (cantidad de álbumes)

	/**
	 * Constructor de la ventana VEliminarAlbum.
	 * Inicializa la interfaz gráfica que muestra una tabla con los álbumes disponibles
	 * y permite al usuario seleccionar y eliminar uno de ellos.
	 * También carga los datos desde la lógica de la aplicación.
	 * 
	 * @param padre Ventana padre desde la que se abre este diálogo.
	 * @param modal Indica si la ventana es modal (bloquea la interacción con otras ventanas).
	 */
	// Constructor de la ventana modal
	public VEliminarAlbum(VBaja padre, boolean modal) {
		super(padre, modal);

		setTitle("Eliminar álbum"); // título ventana
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		// icono de la ventana
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));

		// Colores del diseño
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color colorPrimario = new Color(220, 53, 69); // rojo eliminar
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);
		Color colorSeleccion = new Color(232, 242, 252);

		// Panel principal
		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// Panel tipo tarjeta (contenedor principal visual)
		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(colorBorde, 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		// Panel cabecera con título e info
		JPanel panelCabecera = new JPanel(new BorderLayout(0, 8));
		panelCabecera.setBackground(fondoTarjeta);

		JLabel lblTitulo = new JLabel("Eliminar álbum");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		// Label informativo dinámico
		lblInfo = new JLabel("Selecciona un álbum de la tabla");
		lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblInfo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblInfo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		// Columnas de la tabla
		String[] columnas = { "ID Album", "Título", "ID Artista"};

		// Modelo de tabla no editable
		model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // evita edición manual
			}
		};

		// Creación de la tabla
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // solo una fila
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(235, 235, 235));
		table.setSelectionBackground(colorSeleccion);
		table.setSelectionForeground(Color.BLACK);
		table.setFillsViewportHeight(true);

		// Estilo del encabezado
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(colorTexto);
		table.getTableHeader().setReorderingAllowed(false);

		// Ajuste de columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);

		// Scroll para la tabla
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);
		table.setPreferredScrollableViewportSize(new Dimension(560, 240));

		panelTarjeta.add(scrollPane, BorderLayout.CENTER);

		// Panel inferior con botones
		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setBackground(fondoTarjeta);

		JLabel lblAviso = new JLabel("Se eliminará el álbum seleccionado");
		lblAviso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblAviso.setForeground(colorSecundario);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelBotones.setBackground(fondoTarjeta);

		// Botón volver
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBackground(new Color(240, 240, 240));
		btnVolver.setForeground(colorTexto);
		btnVolver.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		btnVolver.addActionListener(this);

		// Botón eliminar álbum seleccionado
		btnEliminarAlbum = new JButton("Eliminar seleccionado");
		btnEliminarAlbum.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminarAlbum.setFocusPainted(false);
		btnEliminarAlbum.setBorderPainted(false);
		btnEliminarAlbum.setOpaque(true);
		btnEliminarAlbum.setBackground(colorPrimario);
		btnEliminarAlbum.setForeground(Color.WHITE);
		btnEliminarAlbum.addActionListener(this);

		panelBotones.add(btnVolver);
		panelBotones.add(btnEliminarAlbum);

		panelInferior.add(lblAviso, BorderLayout.WEST);
		panelInferior.add(panelBotones, BorderLayout.EAST);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		// Carga de datos desde la lógica principal
		try {
			Object[][] datos = Principal.devolverAlbumesT();

			model.setRowCount(0); // limpia tabla

			// rellena la tabla
			for (Object[] fila : datos) {
				model.addRow(fila);
			}

			lblInfo.setText("Álbumes cargados: " + model.getRowCount());

		} catch (LoginException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar los álbumes");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Revisa el método que devuelve los álbumes");
		}

		pack();
		setLocationRelativeTo(padre);
	}

	/**
	 * Gestiona los eventos producidos por la interacción del usuario con los botones.
	 * Permite volver a la ventana anterior o eliminar el álbum seleccionado de la tabla,
	 * realizando validaciones y mostrando mensajes de confirmación o error.
	 * 
	 * @param e Evento de acción generado al hacer clic en un botón.
	 */
	// Manejo de eventos de botones
	@Override
	public void actionPerformed(ActionEvent e) {

		// Volver a la ventana anterior
		if (e.getSource().equals(btnVolver)) {
			dispose();
		}

		// Eliminar álbum seleccionado
		else if (e.getSource().equals(btnEliminarAlbum)) {

			int filaSeleccionada = table.getSelectedRow();

			// validación: si no hay selección
			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona un álbum de la tabla");
				return;
			}

			// confirmación del usuario
			int confirmacion = JOptionPane.showConfirmDialog(
					this,
					"¿Seguro que quieres eliminar el álbum seleccionado?",
					"Confirmar eliminación",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			// obtener id del álbum seleccionado
			int idAlbum = Integer.parseInt(model.getValueAt(filaSeleccionada, 0).toString());

			try {
				// llamada a la lógica para eliminar
				boolean eliminado = Principal.eliminarAlbum(idAlbum);

				if (eliminado) {
					model.removeRow(filaSeleccionada); // elimina de la tabla
					lblInfo.setText("Álbumes cargados: " + model.getRowCount());
					JOptionPane.showMessageDialog(this, "Álbum eliminado correctamente");
				} else {
					JOptionPane.showMessageDialog(this, "No existe ese álbum");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al eliminar");
			}
		}
	}
}