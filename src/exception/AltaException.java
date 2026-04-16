package exception;

import javax.swing.JOptionPane;


public class AltaException extends Exception {

   
    private static final long serialVersionUID = 1L;


    private String msg;

    public AltaException(String msg) {
    	super(msg);
		this.msg = msg;
    }

  
    public void visualizarMsg() {
        JOptionPane.showMessageDialog(null, this.msg, "ERROR AL DAR ALTA", JOptionPane.ERROR_MESSAGE);
    }
}