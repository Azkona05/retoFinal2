package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

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

import exception.AltaException;
import main.Principal;
import modelo.Album;
import modelo.Artista;

public class VAlbum extends JDialog implements ActionListener {
	/**
	 * @author Ricardo Soza
	 */

	private static final long serialVersionUID = 1L;

	private JTable tablaArtis;
	private JButton anadirButton;
	private JButton volverButton;
	private JTextField textFieldIdArtista;
	private JTextField textFieldNombreArtista;
	private JTextField textFieldNomAlbum;
	private JTextField textFieldNumCanci;
	private int idArtistaSeleccionado;
	private String nombreArtistaSeleccionado;
	
	/**
     * 
     * @param modal Si es modal, bloquea la interacción con la ventana padre mientras está abierto.
     */

	public VAlbum(boolean modal) {
		setModal(modal);
		setTitle("Alta de álbum");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);// Cuando se cierra la ventana, se cierra solo la ventana y
		// no termian el programa.
		setResizable(false);// evita que el usuario redimencione la ventana (no le cambie el tamaño)
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo-png.png")));// Establece una
																										// imagen
																										// siguiendo la
																										// ruta asignada

		// Elige los colores en base a los numeros
		Color fondoVentana = new Color(245, 247, 250);
		Color fondoTarjeta = Color.WHITE;
		Color naranjaPalo = new Color(244, 162, 97);
		Color colorTexto = new Color(40, 40, 40);
		Color colorSecundario = new Color(120, 120, 120);
		Color colorBorde = new Color(220, 224, 230);
		Color colorSeleccion = new Color(232, 242, 252);

		JPanel contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBackground(fondoVentana);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// Este es para dar efecto de tarjeta flotante sobre el fondo
		JPanel panelTarjeta = new JPanel(new BorderLayout(0, 20));
		panelTarjeta.setBackground(fondoTarjeta);
		panelTarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(colorBorde, 1, true), // color,grosor y
																										// que las
																										// esquinas
																										// esten
																										// redondeadas
				new EmptyBorder(20, 20, 20, 20)));// deje espacios en blanco

		contentPane.add(panelTarjeta, BorderLayout.CENTER);

		JPanel panelCabecera = new JPanel(new BorderLayout(0, 8));
		panelCabecera.setBackground(fondoTarjeta);

		JLabel lblTitulo = new JLabel("Alta de álbum");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(colorTexto);

		JLabel lblSubtitulo = new JLabel("Selecciona un artista e introduce los datos del álbum");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(colorSecundario);

		panelCabecera.add(lblTitulo, BorderLayout.NORTH);
		panelCabecera.add(lblSubtitulo, BorderLayout.SOUTH);

		panelTarjeta.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel(new BorderLayout(0, 20));
		panelCentro.setBackground(fondoTarjeta);

		String[] columnasNombre = { "ID", "NOMBRE", "TIPO" };
		DefaultTableModel model = new DefaultTableModel(null, columnasNombre) {
			//Esto es una clase anónima (se crea y se usa solo aqui) que extiende DefaultTableModel para personalizar su comportamiento. Se crea la clase  al abrir las llaves
			
			
			private static final long serialVersionUID = 1L;

			//Este método decide si una celda específica puede ser editada por el usuario.
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;//ninguna es editable
			}
		};
		
		//Obtiene todos los artistas desde la base de datos y recorre el mapa y añade cada artista como una fila a la tabla

		try {
			Map<Integer, Artista> artiMap = Principal.listarArti(null);

			if (artiMap != null && !artiMap.isEmpty()) {
				for (Map.Entry<Integer, Artista> entry : artiMap.entrySet()) {
					Artista artista = entry.getValue();
					Object[] fila = { artista.getId(), artista.getNombre(), artista.getTipo().name() };
					model.addRow(fila);
				}
			}
		} catch (AltaException e) {
			JOptionPane.showMessageDialog(this, "No se ha podido cargar los artistas: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		tablaArtis = new JTable(model);
		tablaArtis.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaArtis.setRowHeight(30);  // Altura de cada fila (30px)
		tablaArtis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila seleccionable
		tablaArtis.setShowVerticalLines(false);//no muestra lineas verticales
		tablaArtis.setGridColor(new Color(235, 235, 235));  // Color de líneas de la cuadrícula
		tablaArtis.setSelectionBackground(colorSeleccion);  // Fondo de fila seleccionada
		tablaArtis.setSelectionForeground(Color.BLACK);  // Texto de fila seleccionada
		tablaArtis.setFillsViewportHeight(true);  // Ocupa toda la altura disponible

		tablaArtis.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tablaArtis.getTableHeader().setBackground(Color.WHITE);
		tablaArtis.getTableHeader().setForeground(colorTexto);
		tablaArtis.getTableHeader().setReorderingAllowed(false);//no reordenar columnas

		tablaArtis.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablaArtis.getColumnModel().getColumn(1).setPreferredWidth(240);
		tablaArtis.getColumnModel().getColumn(2).setPreferredWidth(120);

		tablaArtis.getSelectionModel().addListSelectionListener(e -> {//getSelectionModel obtiene el objeto que controla la selección de la tabla ,addListSelectionListener si alguna fila esta seleccionada
			if (!e.getValueIsAdjusting()) {//getValueIsAdjusting True si sigue seleccionando, False si ya termino de seleccionar (dejo de clikear)
				int filaSeleccionada = tablaArtis.getSelectedRow();//getSelectedRow indice de la tabla seleccionada
				if (filaSeleccionada != -1) {
					idArtistaSeleccionado = Integer.parseInt(tablaArtis.getValueAt(filaSeleccionada, 0).toString());//getValueAt(id, columna)
					nombreArtistaSeleccionado = tablaArtis.getValueAt(filaSeleccionada, 1).toString();//getValueAt(id, columna)
					textFieldIdArtista.setText(String.valueOf(idArtistaSeleccionado));
					textFieldNombreArtista.setText(nombreArtistaSeleccionado);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(tablaArtis);
		scrollPane.setBorder(new LineBorder(colorBorde, 1, true));//LineBorder(color,grosor.esquinas redondeadas)
		//scrollPane.getViewport().setBackground(Color.WHITE);
		tablaArtis.setPreferredScrollableViewportSize(new Dimension(560, 150));

		JPanel panelTabla = new JPanel(new BorderLayout(0, 10));
		panelTabla.setBackground(fondoTarjeta);

		JLabel lblSeleccion = new JLabel("Selecciona un artista de la tabla");
		lblSeleccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSeleccion.setForeground(colorTexto);

		panelTabla.add(lblSeleccion, BorderLayout.NORTH);
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		panelCentro.add(panelTabla, BorderLayout.NORTH);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(fondoTarjeta);

		GridBagConstraints gbc = new GridBagConstraints();  // es un layout mas flexible que permite controlar con mas
															// precision el tamaño			
		gbc.insets = new Insets(8, 8, 8, 8); // Margen de 8px alrededor de cada componente
		gbc.anchor = GridBagConstraints.WEST;// Alinear a la izquierda 
		gbc.fill = GridBagConstraints.HORIZONTAL;// Expandir horizontalmente

		JLabel lblIdArtista = new JLabel("ID artista");
		lblIdArtista.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblIdArtista.setForeground(colorTexto);

		textFieldIdArtista = new JTextField();
		textFieldIdArtista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldIdArtista.setEditable(false);
		textFieldIdArtista.setPreferredSize(new Dimension(220, 34));
		textFieldIdArtista.setBorder(BorderFactory
				.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true), new EmptyBorder(5, 8, 5, 8)));

		JLabel lblNombreArtista = new JLabel("Nombre artista");
		lblNombreArtista.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombreArtista.setForeground(colorTexto);

		textFieldNombreArtista = new JTextField();
		textFieldNombreArtista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNombreArtista.setEditable(false);
		textFieldNombreArtista.setPreferredSize(new Dimension(220, 34));
		textFieldNombreArtista.setBorder(BorderFactory
				.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true), new EmptyBorder(5, 8, 5, 8)));

		JLabel lblNomAlbum = new JLabel("Nombre del álbum");
		lblNomAlbum.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNomAlbum.setForeground(colorTexto);

		textFieldNomAlbum = new JTextField();
		textFieldNomAlbum.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNomAlbum.setPreferredSize(new Dimension(220, 34));
		textFieldNomAlbum.setBorder(BorderFactory
				.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true), new EmptyBorder(5, 8, 5, 8)));

		JLabel lblNumCanciones = new JLabel("Número de canciones");
		lblNumCanciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumCanciones.setForeground(colorTexto);

		textFieldNumCanci = new JTextField();
		textFieldNumCanci.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textFieldNumCanci.setPreferredSize(new Dimension(220, 34));
		textFieldNumCanci.setBorder(BorderFactory
				.createCompoundBorder(new LineBorder(new Color(210, 214, 220), 1, true), new EmptyBorder(5, 8, 5, 8)));

		gbc.gridx = 0;// columna 0
		gbc.gridy = 0;//fila 0
		panelFormulario.add(lblIdArtista, gbc);

		gbc.gridx = 1;//columna 1
		gbc.gridy = 0;//fila 0
		panelFormulario.add(textFieldIdArtista, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(lblNombreArtista, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(textFieldNombreArtista, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(lblNomAlbum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(textFieldNomAlbum, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(lblNumCanciones, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(textFieldNumCanci, gbc);

		panelCentro.add(panelFormulario, BorderLayout.CENTER);

		panelTarjeta.add(panelCentro, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelInferior.setBackground(fondoTarjeta);

		volverButton = new JButton("Volver");
		volverButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		volverButton.setFocusPainted(false);
		volverButton.setBackground(new Color(240, 240, 240));
		volverButton.setForeground(colorTexto);
		volverButton.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		volverButton.addActionListener(this);

		anadirButton = new JButton("Añadir álbum");
		anadirButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		anadirButton.setFocusPainted(false);
		anadirButton.setBorderPainted(false);
		anadirButton.setOpaque(true);
		anadirButton.setBackground(naranjaPalo);
		anadirButton.setForeground(Color.WHITE);
		anadirButton.addActionListener(this);

		panelInferior.add(volverButton);
		panelInferior.add(anadirButton);

		panelTarjeta.add(panelInferior, BorderLayout.SOUTH);

		getRootPane().setDefaultButton(anadirButton);

		pack();// se ajusta el tamaño de la ventana al tamaño de los componentes
			   // (Jlabels,botones...)
		setLocationRelativeTo(null); // pone la ventena encima de la anterior o padre (centrada)
	}

	/**
	 *@param e  hace referencia al ActionEvent y dependiendo de la accion que
	 *           realize el usuario hará algo
	 *    */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == volverButton) {
			dispose();

		} else if (e.getSource() == anadirButton) {
			try {
				if (textFieldIdArtista.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, selecciona un artista de la tabla", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String nombreAlbum = textFieldNomAlbum.getText().trim();
				if (nombreAlbum.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, introduce el nombre del álbum", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String numCancionesStr = textFieldNumCanci.getText().trim();
				if (numCancionesStr.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, introduce el número de canciones", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int numCanciones;
				try {
					numCanciones = Integer.parseInt(numCancionesStr);
					if (numCanciones <= 0) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "El número de canciones debe ser un número positivo", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int idAlbum;
				do {
					idAlbum = (int) (Math.random() * 9000) + 1000;
				} while (Principal.existeIdAlbum(idAlbum));

				Album album = new Album();
				album.setId(idAlbum);
				album.setNombre(nombreAlbum);

				boolean exito = Principal.altaAlbum(album, idArtistaSeleccionado);

				if (exito) {
					JOptionPane.showMessageDialog(this,
							"Álbum creado con ID: " + idAlbum + "\nAhora añade las canciones", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);

					setVisible(false);

					VAlbumCanci ventanaCanciones = new VAlbumCanci(idAlbum, numCanciones, nombreAlbum);
					ventanaCanciones.addWindowListener(new WindowAdapter() { //addWindowListener es un oyente que se activa cuando ocurren eventos en la nueva ventana,WindowAdapter si pusiera windowlistener tendria que poner todos sus metodos
						@Override
						public void windowClosed(WindowEvent e) {//windowClose se cierra esta ventana cunado se cierra la ventana hija
							dispose();
						}
					});
					ventanaCanciones.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(this, "Error al crear el álbum", "Error", JOptionPane.ERROR_MESSAGE);
				}

			} catch (AltaException ex) {
				JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}