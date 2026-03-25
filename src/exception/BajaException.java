package exception;

import javax.swing.JOptionPane;

public class BajaException extends Exception {

	 /** Versión de la clase para la serialización */
    private static final long serialVersionUID = 1L;

    /** Mensaje de error asociado a la excepción */
    private String msg;

    /**
     * Constructor que inicializa la excepción con un mensaje de error.
     * 
     * @param msg El mensaje que describe el error
     */

    /**
     * Muestra el mensaje de error en una ventana emergente (JOptionPane).
     * Este método presenta el mensaje de la excepción al usuario.
     */
    public void visualizarMsg() {
        JOptionPane.showMessageDialog(null, this.msg, "ERROR Al EJEJUTAR LA BAJA", JOptionPane.ERROR_MESSAGE);
    }
}
