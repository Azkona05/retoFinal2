package main;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.LoginException;
import vista.VPrincipal;

public class Principal {

	private static InterfazDao dao = new DaoImplementacion();
	
	public static void main(String[] args) {
		VPrincipal vPrincipal = new VPrincipal();
		vPrincipal.setVisible(true);
	}
	
	public static void login(modelo.Usuario usuario) throws LoginException {
		dao.login(usuario);
	}
}
