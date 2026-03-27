package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import modelo.Cancion;
import modelo.Genero;

public class VAltaCancionAlbum extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JButton btnAnadir, btnCancelar;
    private JTable tablaArtistas;
    private JTable tablaAlbumes;
    private JScrollPane scrollArtistas;
    private JScrollPane scrollAlbumes;
    private JTextField textFieldNombreCancion;
    private JComboBox<Genero> comboBoxGenero;
    private JLabel lblIdGenerado;
    private JTextField textFieldIdAlbum;
    private JTextField textFieldNombreAlbum;
    private JTextField textFieldIdArtista;
    private JTextField textFieldNombreArtista;
    
    private int idArtistaSeleccionado = 0;
    private int idAlbumSeleccionado = 0;
    private Set<Integer> idsGenerados = new HashSet<>();
    private int idCancionActual;

    public VAltaCancionAlbum(boolean modal) {
        setModal(modal);
        setBounds(100, 100, 900, 650);
        setTitle("Añadir Canción a Álbum");
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("AÑADIR CANCIÓN A ÁLBUM");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(350, 10, 300, 30);
        contentPanel.add(lblTitulo);
        
        // Tabla de artistas
        JLabel lblArtistas = new JLabel("ARTISTAS");
        lblArtistas.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblArtistas.setBounds(20, 45, 100, 20);
        contentPanel.add(lblArtistas);
        
        try {
            cargarTablaArtistas();
        } catch (AltaException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar artistas: " + e.getMessage());
        }
        
        // Tabla de álbumes
        JLabel lblAlbumes = new JLabel("ÁLBUMES DEL ARTISTA SELECCIONADO");
        lblAlbumes.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAlbumes.setBounds(20, 235, 250, 20);
        contentPanel.add(lblAlbumes);
        
        presentarTablaAlbumes();
        
        // Panel de información del álbum seleccionado
        JPanel panelAlbum = new JPanel();
        panelAlbum.setBounds(20, 380, 850, 70);
        panelAlbum.setLayout(null);
        panelAlbum.setBorder(javax.swing.BorderFactory.createTitledBorder("Álbum Seleccionado"));
        contentPanel.add(panelAlbum);
        
        JLabel lblIdAlbum = new JLabel("ID Álbum:");
        lblIdAlbum.setBounds(10, 30, 80, 25);
        panelAlbum.add(lblIdAlbum);
        
        textFieldIdAlbum = new JTextField();
        textFieldIdAlbum.setBounds(90, 30, 80, 25);
        textFieldIdAlbum.setEditable(false);
        panelAlbum.add(textFieldIdAlbum);
        
        JLabel lblNombreAlbum = new JLabel("Nombre:");
        lblNombreAlbum.setBounds(180, 30, 60, 25);
        panelAlbum.add(lblNombreAlbum);
        
        textFieldNombreAlbum = new JTextField();
        textFieldNombreAlbum.setBounds(240, 30, 200, 25);
        textFieldNombreAlbum.setEditable(false);
        panelAlbum.add(textFieldNombreAlbum);
        
        JLabel lblArtistaAlbum = new JLabel("Artista:");
        lblArtistaAlbum.setBounds(450, 30, 60, 25);
        panelAlbum.add(lblArtistaAlbum);
        
        textFieldIdArtista = new JTextField();
        textFieldIdArtista.setBounds(510, 30, 50, 25);
        textFieldIdArtista.setEditable(false);
        panelAlbum.add(textFieldIdArtista);
        
        textFieldNombreArtista = new JTextField();
        textFieldNombreArtista.setBounds(565, 30, 150, 25);
        textFieldNombreArtista.setEditable(false);
        panelAlbum.add(textFieldNombreArtista);
        
        // Panel de datos de la canción
        JPanel panelCancion = new JPanel();
        panelCancion.setBounds(20, 470, 850, 100);
        panelCancion.setLayout(null);
        panelCancion.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Canción"));
        contentPanel.add(panelCancion);
        
        JLabel lblId = new JLabel("ID generado:");
        lblId.setBounds(10, 30, 100, 25);
        panelCancion.add(lblId);
        
        lblIdGenerado = new JLabel("");
        lblIdGenerado.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblIdGenerado.setBounds(110, 30, 80, 25);
        panelCancion.add(lblIdGenerado);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(200, 30, 80, 25);
        panelCancion.add(lblNombre);
        
        textFieldNombreCancion = new JTextField();
        textFieldNombreCancion.setBounds(280, 30, 200, 25);
        panelCancion.add(textFieldNombreCancion);
        
        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(500, 30, 60, 25);
        panelCancion.add(lblGenero);
        
        comboBoxGenero = new JComboBox<>(Genero.values());
        comboBoxGenero.setBounds(560, 30, 150, 25);
        panelCancion.add(comboBoxGenero);
        
        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        btnAnadir = new JButton("Añadir Canción");
        btnAnadir.addActionListener(this);
        buttonPane.add(btnAnadir);
        getRootPane().setDefaultButton(btnAnadir);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        buttonPane.add(btnCancelar);
        
        // Generar primer ID
        generarNuevoId();
    }
    
    private void cargarTablaArtistas() throws AltaException {
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
        
        tablaArtistas = new JTable(model);
        tablaArtistas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaArtistas.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaArtistas.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        tablaArtistas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaArtistas.getSelectedRow();
                if (fila != -1) {
                    idArtistaSeleccionado = (int) tablaArtistas.getValueAt(fila, 0);
                    String nombreArtista = (String) tablaArtistas.getValueAt(fila, 1);
                    cargarAlbumesPorArtista(idArtistaSeleccionado, nombreArtista);
                }
            }
        });
        
        scrollArtistas = new JScrollPane(tablaArtistas);
        scrollArtistas.setBounds(20, 70, 850, 150);
        contentPanel.add(scrollArtistas);
    }
    
    // ✅ MÉTODO MODIFICADO - Usa DAO en lugar de conexión directa
    private void cargarAlbumesPorArtista(int idArtista, String nombreArtista) {
        try {
            Map<Integer, Album> albumesMap = Principal.listarAlbumesPorArtista(idArtista);
            
            DefaultTableModel model = (DefaultTableModel) tablaAlbumes.getModel();
            model.setRowCount(0); // Limpiar tabla
            
            if (albumesMap != null && !albumesMap.isEmpty()) {
                for (Map.Entry<Integer, Album> entry : albumesMap.entrySet()) {
                    Album album = entry.getValue();
                    Object[] fila = {
                        album.getId(),
                        album.getNombre(),
                        idArtista,
                        nombreArtista
                    };
                    model.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "El artista " + nombreArtista + " no tiene álbumes.\nPrimero debes crear un álbum para este artista.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (AltaException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar álbumes: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void presentarTablaAlbumes() {
        String[] columnasNombre = {"ID_ALBUM", "NOMBRE_ALBUM", "ID_ARTISTA", "ARTISTA"};
        DefaultTableModel model = new DefaultTableModel(null, columnasNombre);
        
        tablaAlbumes = new JTable(model);
        tablaAlbumes.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaAlbumes.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaAlbumes.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaAlbumes.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        tablaAlbumes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaAlbumes.getSelectedRow();
                if (fila != -1) {
                    idAlbumSeleccionado = (int) tablaAlbumes.getValueAt(fila, 0);
                    String nombreAlbum = (String) tablaAlbumes.getValueAt(fila, 1);
                    int idArtista = (int) tablaAlbumes.getValueAt(fila, 2);
                    String nombreArtista = (String) tablaAlbumes.getValueAt(fila, 3);
                    
                    textFieldIdAlbum.setText(String.valueOf(idAlbumSeleccionado));
                    textFieldNombreAlbum.setText(nombreAlbum);
                    textFieldIdArtista.setText(String.valueOf(idArtista));
                    textFieldNombreArtista.setText(nombreArtista);
                }
            }
        });
        
        scrollAlbumes = new JScrollPane(tablaAlbumes);
        scrollAlbumes.setBounds(20, 260, 850, 100);
        contentPanel.add(scrollAlbumes);
    }
    
    private void generarNuevoId() {
        int id;
        do {
            id = (int) (Math.random() * 9999) + 1;
        } while (idsGenerados.contains(id) || idYaExisteEnBD(id));
        
        idCancionActual = id;
        idsGenerados.add(idCancionActual);
        lblIdGenerado.setText(String.valueOf(idCancionActual));
    }
    
    private boolean idYaExisteEnBD(int id) {
        try {
            return Principal.existeIdCancion(id);
        } catch (AltaException e) {
            return false;
        }
    }
    
    private boolean validarCampos() {
        if (idAlbumSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un álbum de la tabla.", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String nombreCancion = textFieldNombreCancion.getText().trim();
        if (nombreCancion.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, introduce el nombre de la canción.", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            textFieldNombreCancion.requestFocus();
            return false;
        }
        
        try {
            if (Principal.existeCancionEnAlbum(nombreCancion, idAlbumSeleccionado)) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe una canción con el nombre '" + nombreCancion + 
                    "' en el álbum seleccionado.\nPor favor, elige otro nombre.", 
                    "Nombre duplicado", JOptionPane.WARNING_MESSAGE);
                textFieldNombreCancion.requestFocus();
                return false;
            }
        } catch (AltaException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al comprobar nombre: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void anadirCancion() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            String nombreCancion = textFieldNombreCancion.getText().trim();
            Genero genero = (Genero) comboBoxGenero.getSelectedItem();
            
            Cancion cancion = new Cancion();
            cancion.setId(idCancionActual);
            cancion.setNombre(nombreCancion);
            cancion.setGenero(genero);
            
            boolean exito = Principal.altaCancion(cancion, idAlbumSeleccionado);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "¡Canción añadida correctamente!\n\n" +
                    "ID: " + idCancionActual + "\n" +
                    "Canción: " + nombreCancion + "\n" +
                    "Género: " + genero + "\n" +
                    "Álbum: " + textFieldNombreAlbum.getText(), 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                textFieldNombreCancion.setText("");
                comboBoxGenero.setSelectedIndex(0);
                generarNuevoId();
                
                int respuesta = JOptionPane.showConfirmDialog(this, 
                    "¿Quieres añadir otra canción al mismo álbum?", 
                    "Continuar", JOptionPane.YES_NO_OPTION);
                    
                if (respuesta != JOptionPane.YES_OPTION) {
                    this.dispose();
                }
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar la canción.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (AltaException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            this.dispose();
        } else if (e.getSource() == btnAnadir) {
            anadirCancion();
        }
    }
}