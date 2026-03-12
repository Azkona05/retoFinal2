package controlador;

import exception.LoginException;
import modelo.Usuario;

public interface InterfazDao {

	 public void login(Usuario usuario) throws LoginException;
}
