package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import exception.AltaException;
import main.Principal;
import modelo.Cancion;
import modelo.Genero;

public class VAlbumCanci extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel = new JPanel();
    private JTextField textFieldNombre;
    private JComboBox<Genero> comboBoxGenero;
    private JButton btnAceptar, btnCancelar;
    private JLabel lblInfo, lblIdGenerado;
    
    private int idAlbum;
    private int cancionesPendientes;
    private int cancionesAñadidas;
    private ArrayList<Cancion> canciones;
    private String nombreAlbum;
    private Set<Integer> idsGenerados; // Para evitar IDs repetidos
    private int idActual; // ID generado para la canción actual

    public VAlbumCanci(int idAlbum, int cancionesPendientes, String nombreAlbum) {
        this.idAlbum = idAlbum;
        this.cancionesPendientes = cancionesPendientes;
        this.cancionesAñadidas = 0;
        this.canciones = new ArrayList<>();
        this.nombreAlbum = nombreAlbum;
        this.idsGenerados = new HashSet<>();
        
        setModal(true);
        setBounds(100, 100, 500, 400);
        setTitle("Añadir Canciones - " + nombreAlbum);
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("AÑADIENDO CANCIONES");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(150, 10, 250, 30);
        contentPanel.add(lblTitulo);
        
        // Información del álbum
        JLabel lblAlbum = new JLabel("Álbum: " + nombreAlbum);
        lblAlbum.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAlbum.setBounds(20, 45, 300, 20);
        contentPanel.add(lblAlbum);
        
        JLabel lblIdAlbum = new JLabel("ID Álbum: " + idAlbum);
        lblIdAlbum.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIdAlbum.setBounds(20, 65, 200, 20);
        contentPanel.add(lblIdAlbum);
        
        // Progreso
        lblInfo = new JLabel("Canción " + (cancionesAñadidas + 1) + " de " + cancionesPendientes);
        lblInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblInfo.setBounds(20, 100, 300, 25);
        contentPanel.add(lblInfo);
        
        // ID generado (solo lectura)
        JLabel lblId = new JLabel("ID generado:");
        lblId.setBounds(20, 140, 100, 25);
        contentPanel.add(lblId);
        
        lblIdGenerado = new JLabel("");
        lblIdGenerado.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIdGenerado.setBounds(120, 140, 100, 25);
        contentPanel.add(lblIdGenerado);
        
        // Nombre de la canción
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 180, 100, 25);
        contentPanel.add(lblNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(120, 180, 250, 25);
        contentPanel.add(textFieldNombre);
        
        // Género
        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(20, 220, 100, 25);
        contentPanel.add(lblGenero);
        
        comboBoxGenero = new JComboBox<>(Genero.values());
        comboBoxGenero.setBounds(120, 220, 150, 25);
        contentPanel.add(comboBoxGenero);
        
        // Generar el primer ID aleatorio
        generarNuevoId();
        
        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(this);
        buttonPane.add(btnAceptar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        buttonPane.add(btnCancelar);
    }
    
    /**
     * Genera un ID aleatorio único para la canción
     */
    private void generarNuevoId() {
        int id;
        do {
            // Generar ID entre 1 y 9999
            id = (int) (Math.random() * 9999) + 1;
        } while (idsGenerados.contains(id)); // Repetir hasta que sea único
        
        idActual = id;
        idsGenerados.add(idActual);
        lblIdGenerado.setText(String.valueOf(idActual));
    }
    
    /**
     * Regenera un nuevo ID
     */
    private void regenerarId() {
        int nuevoId;
        do {
            nuevoId = (int) (Math.random() * 9999) + 1;
        } while (idsGenerados.contains(nuevoId));
        
        // Reemplazar el ID en el conjunto
        idsGenerados.remove(idActual);
        idActual = nuevoId;
        idsGenerados.add(idActual);
        lblIdGenerado.setText(String.valueOf(idActual));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            // Confirmar cancelación
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que quieres cancelar?\nSe perderán las canciones añadidas.", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
            }
            
        } else if (e.getSource() == btnAceptar) {
            try {
                String nombre = textFieldNombre.getText().trim();
                Genero genero = (Genero) comboBoxGenero.getSelectedItem();
                
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor, introduce el nombre de la canción", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Comprobar que el ID generado no existe en la BD
                if (Principal.existeIdCancion(idActual)) {
                    JOptionPane.showMessageDialog(this, 
                        "El ID " + idActual + " ya existe en la base de datos.\nSe generará uno nuevo.", 
                        "ID Duplicado", JOptionPane.WARNING_MESSAGE);
                    regenerarId();
                    return;
                }
                
                // Crear la canción
                Cancion cancion = new Cancion();
                cancion.setId(idActual);
                cancion.setNombre(nombre);
                cancion.setGenero(genero);
                
                canciones.add(cancion);
                cancionesAñadidas++;
                
                if (cancionesAñadidas < cancionesPendientes) {
                    // Limpiar campos
                    textFieldNombre.setText("");
                    comboBoxGenero.setSelectedIndex(0);
                    
                    // Generar nuevo ID para la siguiente canción
                    generarNuevoId();
                    
                    // Actualizar etiqueta de progreso
                    lblInfo.setText("Canción " + (cancionesAñadidas + 1) + " de " + cancionesPendientes);
                    
                    JOptionPane.showMessageDialog(this, 
                        "Canción añadida correctamente.\nFaltan " + (cancionesPendientes - cancionesAñadidas) + " canciones.", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    // Todas las canciones añadidas, guardar en BD
                    guardarCanciones();
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error en el formato del ID", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (AltaException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error de base de datos: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Botón opcional para regenerar ID manualmente
     */
  
    
    private void guardarCanciones() {
        try {
            for (Cancion cancion : canciones) {
                boolean exito = Principal.altaCancion(cancion, idAlbum);
                if (!exito) {
                    throw new AltaException("Error al guardar canción: " + cancion.getNombre());
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "¡ÁLBUM COMPLETADO!\n\n" +
                "Álbum: " + nombreAlbum + "\n" +
                "ID Álbum: " + idAlbum + "\n" +
                "Canciones añadidas: " + canciones.size() + "\n\n" +
                "Todas las canciones se han guardado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            
        } catch (AltaException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar canciones: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}