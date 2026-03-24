package exception;

import javax.swing.JOptionPane;

/**
 * Excepción personalizada para manejar errores durante el proceso de inicio de sesión.
 * Esta excepción se lanza cuando ocurre un error relacionado con el login, como
 * credenciales incorrectas o problemas de autenticación.
 */
public class AltaException extends Exception {

    /** Versión de la clase para la serialización */
    private static final long serialVersionUID = 1L;

    /** Mensaje de error asociado a la excepción */
    private String msg;

    /**
     * Constructor que inicializa la excepción con un mensaje de error.
     * 
     * @param msg El mensaje que describe el error
     */
    public AltaException(String msg) {
        this.msg = msg;
    }

    /**
     * Muestra el mensaje de error en una ventana emergente (JOptionPane).
     * Este método presenta el mensaje de la excepción al usuario.
     */
    public void visualizarMsg() {
        JOptionPane.showMessageDialog(null, this.msg, "ERROR AL DAR ALTA", JOptionPane.ERROR_MESSAGE);
    }
}