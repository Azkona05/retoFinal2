package main;

import java.sql.SQLException;

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
	public static boolean eliminarCancion(int idC) throws SQLException {
		return dao.eliminarCancion(idC);
	}
	public static boolean eliminarAlbum(int idAlb) throws SQLException {
		return dao.eliminarAlbum(idAlb);
	}

	public static boolean eliminarArtista(int idArt) throws SQLException {
		return dao.eliminarArtista(idArt);
	}

}
