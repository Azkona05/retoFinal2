package vista;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * @author Jon Ander
 * */
public class VModificar extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton btnArtista, btnAlbum, btnCancion, btnVolver;

    public VModificar(VMenuAdmin padre, boolean modal) {
        super(padre, modal);
        setTitle("Modificar");
        setResizable(false);

        Color fondo  = new Color(245, 247, 250);
        Color tarjeta = Color.WHITE;
        Color texto  = new Color(40, 40, 40);
        Color borde  = new Color(220, 224, 230);
        Color acento = new Color(244, 162, 97);

        JPanel contentPane = new JPanel(new BorderLayout(20, 20));
        contentPane.setBackground(fondo);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(tarjeta);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borde, 1, true),
                new EmptyBorder(20, 20, 20, 20)));
        contentPane.add(panel);

        JPanel cabecera = new JPanel();
        cabecera.setBackground(tarjeta);
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));

        JLabel t1 = new JLabel("Administrador");
        t1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        t1.setForeground(acento);
        t1.setAlignmentX(CENTER_ALIGNMENT);

        JLabel t2 = new JLabel("Modificar datos");
        t2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        t2.setForeground(texto);
        t2.setAlignmentX(CENTER_ALIGNMENT);

        JLabel t3 = new JLabel("Selecciona qué quieres modificar");
        t3.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        t3.setAlignmentX(CENTER_ALIGNMENT);

        cabecera.add(t1);
        cabecera.add(Box.createVerticalStrut(8));
        cabecera.add(t2);
        cabecera.add(Box.createVerticalStrut(8));
        cabecera.add(t3);
        panel.add(cabecera, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(1, 3, 18, 18));
        botones.setBackground(tarjeta);
        botones.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnArtista = crearBoton("Artista", borde, texto);
        btnAlbum   = crearBoton("Álbum",   borde, texto);
        btnCancion = crearBoton("Canción",  borde, texto);

        botones.add(btnArtista);
        botones.add(btnAlbum);
        botones.add(btnCancion);
        panel.add(botones, BorderLayout.CENTER);

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(this);
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.setBackground(tarjeta);
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(padre);
    }

    private JButton crearBoton(String txt, Color borde, Color texto) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setPreferredSize(new Dimension(150, 80));
        b.setBackground(Color.WHITE);
        b.setForeground(texto);
        b.setBorder(new LineBorder(borde, 1, true));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVolver)  { dispose(); }
        if (e.getSource() == btnArtista) { new VModificarArtista(this, true).setVisible(true); }
        if (e.getSource() == btnAlbum)   { new VModificarAlbum(this, true).setVisible(true); }
        if (e.getSource() == btnCancion) { new VModificarCancion(this, true).setVisible(true); }
    }
}