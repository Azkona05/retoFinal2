package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.AltaException;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import vista.VPrincipal;

public class Principal {

	private static InterfazDao dao = new DaoImplementacion();

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
			VPrincipal vPrincipal = new VPrincipal();
			vPrincipal.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void login(modelo.Usuario usuario) throws LoginException {
		dao.login(usuario);
	}

	public static Object[][] devolverArtistas(Artista a) throws LoginException {
		return dao.devolverArtistas(a);
	}

	public static List<Album> devolverAlbumes() throws LoginException {
		return dao.devolverAlbumes();
	}

	public static List<Cancion> devolverCanciones(int idAlbum) throws LoginException {
		return dao.devolverCanciones(idAlbum);
	}

	public static Object[][] devolverCancionesArtista(Artista a) throws LoginException {
		return dao.devolverCancionesArtista(a);
	}

	public static boolean eliminarCancion(int idC) throws SQLException {
		boolean resultado = dao.eliminarCancion(idC);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	public static boolean eliminarArtista(int idArt) throws SQLException {
		boolean resultado = dao.eliminarArtista(idArt);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	public static boolean eliminarAlbum(int idAlb) throws SQLException {
		boolean resultado = dao.eliminarAlbum(idAlb);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	public static boolean alta(Artista artista) throws AltaException {
		boolean resultado = dao.altaArtista(artista);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}
//	public static Map<Integer,Artista>leerDatosArtista()throws AltaException{
//		return dao.leerDatosArtista();
//	}

	public static ArrayList<Integer> leerIds() throws AltaException {
		return dao.ides();

	}

	public static ArrayList<String> leerNombreArti() throws AltaException {
		return dao.nomArti();
	}

	public static List<Artista> obtenerTodosLosArtistasCompletos() throws LoginException {
		return dao.obtenerTodosLosArtistasCompletos();

	}
}
