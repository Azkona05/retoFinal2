package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import exception.LoginException;
import main.Principal;
import modelo.Album;
import modelo.Artista;

public class VConsultar extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<Album> cbAlbumes;
	private List<Album> albumes;

	public VConsultar(VMenuAdmin padre, boolean modal) {
		super(padre);
		this.setModal(modal);
		setTitle("Lista de Artistas");
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		String[] columnNames = { "ID", "Nombre", "Tipo", "Albumes" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3;
			}
		};
		table = new JTable(model);
		table.setRowHeight(30);

		cbAlbumes = new JComboBox<Album>();
		try {
			albumes = Principal.devolverAlbumes();
			for (Album a : albumes) {
				cbAlbumes.addItem(a);
			}
		} catch (LoginException e) {
			e.printStackTrace();
		}

		cbAlbumes.addActionListener(this);
		TableColumn statusColumn = table.getColumnModel().getColumn(3);
		statusColumn.setCellEditor(new DefaultCellEditor(new JComboBox<Album>()) {
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				@SuppressWarnings("unchecked")
				JComboBox<Album> combo = (JComboBox<Album>) super.getTableCellEditorComponent(table, value, isSelected,
						row, column);
				combo.removeAllItems();
				int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());
				if (albumes != null) {
					for (Album a : albumes) {
						if (a.getIdArtista() == idArtistaFila) {
							combo.addItem(a);
						}
					}
				}
				return combo;
			}
		});
		statusColumn.setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JComboBox<Album> renderCombo = new JComboBox<>();
				int idArtistaFila = Integer.parseInt(table.getValueAt(row, 0).toString());
				if (albumes != null) {
					for (Album a : albumes) {
						if (a.getIdArtista() == idArtistaFila) {
							renderCombo.addItem(a);
						}
					}
				}
				if (value != null) {
					renderCombo.setSelectedItem(value);
				} else {
					renderCombo.setSelectedIndex(-1);
				}
				return renderCombo;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int column = table.columnAtPoint(e.getPoint());
					if (row != -1 && column != 3) {
						int idArtista = Integer.parseInt(table.getValueAt(row, 0).toString());
						String nombreArtista = table.getValueAt(row, 1).toString();

						System.out.println("Has hecho doble clic en el artista: " + nombreArtista);

						VConsultaAlbum vCA = new VConsultaAlbum(VConsultar.this, true, idArtista);
						vCA.setVisible(true);
					}
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		try {
			cargarDatos();
		} catch (LoginException e1) {
			e1.printStackTrace();
		}
	}

	private void cargarDatos() throws LoginException {
		Artista a = new Artista();
		Object[][] datos = Principal.devolverArtistas(a);
		actualizarDatos(datos);
	}

	private void actualizarDatos(Object[][] datos) {
		model.setRowCount(0);
		for (Object[] fila : datos) {
			Object[] filaCompleta = new Object[4];
			filaCompleta[0] = fila[0];
			filaCompleta[1] = fila[1];
			filaCompleta[2] = fila[2];
			filaCompleta[3] = null;
			model.addRow(filaCompleta);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == cbAlbumes) {
//			Album albumSeleccionado = (Album) cbAlbumes.getSelectedItem();
//			
//			if (albumSeleccionado != null) {
//				if (table.isEditing()) {
//					table.getCellEditor().stopCellEditing();
//				}
//				VConsultaAlbum vCA = new VConsultaAlbum(this, true, albumSeleccionado);
//				vCA.setVisible(true);
//			}
//		}
	}
}