package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import exception.AltaException;
import main.Principal;
import modelo.Album;
import modelo.Artista;

public class VAlbum extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable tablaArtis;
    private JScrollPane jscroll;
    private JButton anadirButton, volverButton;
    private JTextField textFieldIdArtista;      // ID del artista seleccionado
    private JTextField textFieldNombreArtista;   // Nombre del artista seleccionado
    private JTextField textFieldNomAlbum;        // Nombre del álbum
    private JTextField textFieldNumCanci;        // Número de canciones
    private int idArtistaSeleccionado;
    private String nombreArtistaSeleccionado;

    public VAlbum(boolean modal) {
        setModal(modal);
        setBounds(100, 100, 700, 500);
        setTitle("Alta de Álbum");
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("ALTA DE ÁLBUM");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(250, 10, 200, 30);
        contentPanel.add(lblTitulo);
        
        // Tabla de artistas
        try {
            presentarTablaArti();
        } catch (AltaException e) {
            JOptionPane.showMessageDialog(this, "No se ha podido cargar los artistas: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JLabel lblSeleccion = new JLabel("Selecciona un artista de la tabla:");
        lblSeleccion.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSeleccion.setBounds(20, 130, 250, 20);
        contentPanel.add(lblSeleccion);
        
        // Campo para ID del artista (solo lectura)
        JLabel lblIdArtista = new JLabel("ID Artista:");
        lblIdArtista.setBounds(20, 160, 80, 25);
        contentPanel.add(lblIdArtista);
        
        textFieldIdArtista = new JTextField();
        textFieldIdArtista.setBounds(100, 160, 80, 25);
        textFieldIdArtista.setEditable(false);
        contentPanel.add(textFieldIdArtista);
        
        // Campo para nombre del artista (solo lectura)
        JLabel lblNombreArtista = new JLabel("Nombre:");
        lblNombreArtista.setBounds(200, 160, 60, 25);
        contentPanel.add(lblNombreArtista);
        
        textFieldNombreArtista = new JTextField();
        textFieldNombreArtista.setBounds(260, 160, 150, 25);
        textFieldNombreArtista.setEditable(false);
        contentPanel.add(textFieldNombreArtista);
        
        // Campos del álbum
        JLabel lblNomAlbum = new JLabel("Nombre del Álbum:");
        lblNomAlbum.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNomAlbum.setBounds(20, 200, 150, 25);
        contentPanel.add(lblNomAlbum);
        
        textFieldNomAlbum = new JTextField();
        textFieldNomAlbum.setBounds(170, 200, 200, 25);
        contentPanel.add(textFieldNomAlbum);
        
        JLabel lblNumCanciones = new JLabel("Número de Canciones:");
        lblNumCanciones.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNumCanciones.setBounds(20, 240, 150, 25);
        contentPanel.add(lblNumCanciones);
        
        textFieldNumCanci = new JTextField();
        textFieldNumCanci.setBounds(170, 240, 80, 25);
        contentPanel.add(textFieldNumCanci);
        
        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        anadirButton = new JButton("Añadir Álbum");
        anadirButton.addActionListener(this);
        buttonPane.add(anadirButton);
        getRootPane().setDefaultButton(anadirButton);
        
        volverButton = new JButton("Volver");
        volverButton.addActionListener(this);
        buttonPane.add(volverButton);
    }

    private void presentarTablaArti() throws AltaException {
        String[] columnasNombre = {"ID", "NOMBRE", "TIPO"};
        Map<Integer, Artista> artiMap = Principal.listarArti(null);
        
        DefaultTableModel model = new DefaultTableModel(null, columnasNombre);
        
        if (artiMap != null && !artiMap.isEmpty()) {
            for (Map.Entry<Integer, Artista> entry : artiMap.entrySet()) {
                Artista artista = entry.getValue();
                Object[] fila = {
                    artista.getId(),
                    artista.getNombre(),
                    artista.getTipo().name()
                };
                model.addRow(fila);
            }
        }
        
        tablaArtis = new JTable(model);
        
        // Añadir listener para selección de fila
        tablaArtis.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaArtis.getSelectedRow();
                if (filaSeleccionada != -1) {
                    idArtistaSeleccionado = (int) tablaArtis.getValueAt(filaSeleccionada, 0);
                    nombreArtistaSeleccionado = (String) tablaArtis.getValueAt(filaSeleccionada, 1);
                    textFieldIdArtista.setText(String.valueOf(idArtistaSeleccionado));
                    textFieldNombreArtista.setText(nombreArtistaSeleccionado);
                }
            }
        });
        
        jscroll = new JScrollPane(tablaArtis);
        jscroll.setBounds(20, 40, 640, 80);
        contentPanel.add(jscroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volverButton) {
            this.dispose();
            
        } else if (e.getSource() == anadirButton) {
            try {
                // Validar que se haya seleccionado un artista
                if (textFieldIdArtista.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor, selecciona un artista de la tabla", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar nombre del álbum
                String nombreAlbum = textFieldNomAlbum.getText().trim();
                if (nombreAlbum.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor, introduce el nombre del álbum", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar número de canciones
                String numCancionesStr = textFieldNumCanci.getText().trim();
                if (numCancionesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor, introduce el número de canciones", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int numCanciones;
                try {
                    numCanciones = Integer.parseInt(numCancionesStr);
                    if (numCanciones <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "El número de canciones debe ser un número positivo", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Generar ID aleatorio para el álbum (entre 1000 y 9999)
                int idAlbum;
                do {
                    idAlbum = (int) (Math.random() * 9000) + 1000;
                } while (Principal.existeIdAlbum(idAlbum));
                
                // Crear el álbum
                Album album = new Album();
                album.setId(idAlbum);
                album.setNombre(nombreAlbum);
                
                // Guardar el álbum en la base de datos
                boolean exito = Principal.altaAlbum(album, idArtistaSeleccionado);
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Álbum creado con ID: " + idAlbum + "\nAhora añade las canciones", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Abrir ventana para añadir canciones
                    this.setVisible(false);
                    VAlbumCanci ventanaCanciones = new VAlbumCanci(idAlbum, numCanciones, nombreAlbum);
                    ventanaCanciones.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            dispose(); // Cerrar VAlbum al terminar
                        }
                    });
                    ventanaCanciones.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al crear el álbum", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (AltaException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error en la base de datos: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}