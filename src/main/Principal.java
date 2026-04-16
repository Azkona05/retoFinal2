package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.AltaException;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Usuario;
import vista.VPrincipal;

public class Principal {

	private static InterfazDao dao = new DaoImplementacion();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			VPrincipal vPrincipal = new VPrincipal();
			vPrincipal.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void login(Usuario usuario) throws LoginException {
		dao.login(usuario);
	}

	// AN
	public static Object[][] devolverArtistas(Artista a) throws LoginException {
		return dao.devolverArtistas(a);
	}

	// AN
	public static List<Album> devolverAlbumes() throws LoginException {
		return dao.devolverAlbumes();
	}

	// AN
	public static List<Cancion> devolverCanciones(int idAlbum) throws LoginException {
		return dao.devolverCanciones(idAlbum);
	}

	// AN
	public static Object[][] devolverCancionesArtista(Artista a) throws LoginException {
		return dao.devolverCancionesArtista(a);
	}

	// AN
	public static Object[][] devolverAlbumesT() throws LoginException {
		return dao.devolverAlbumesT();
	}

	// NORA
	public static boolean eliminarCancion(int idC) throws SQLException {
		boolean resultado = dao.eliminarCancion(idC);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// NORA
	public static boolean eliminarArtista(int idArt) throws SQLException {
		boolean resultado = dao.eliminarArtista(idArt);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// NORA
	public static boolean eliminarAlbum(int idAlb) throws SQLException {
		boolean resultado = dao.eliminarAlbum(idAlb);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// RICARDO
	public static boolean alta(Artista artista) throws AltaException {
		boolean resultado = dao.altaArtista(artista);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// RICARDO
	public static boolean existeCancionEnAlbum(String nombreCancion, int idAlbum) throws AltaException {
		return dao.existeCancionEnAlbum(nombreCancion, idAlbum);
	}
//	public static Map<Integer,Artista>leerDatosArtista()throws AltaException{
//		return dao.leerDatosArtista();
//	}

	// RICARDO
	public static ArrayList<Integer> leerIds() throws AltaException {
		return dao.ides();

	}

	// RICARDO
	public static ArrayList<String> leerNombreArti() throws AltaException {
		return dao.nomArti();
	}

	// RICARDO
	public static List<Artista> obtenerTodosLosArtistasCompletos() throws LoginException {
		return dao.obtenerTodosLosArtistasCompletos();

	}

	// AN
	public static Object[][] devolverCanciones() throws LoginException {
		return dao.devolverCanciones();
	}

	// RICARDO
	public static Map<Integer, Artista> listarArti(Artista arti) throws AltaException {
		return dao.listarArtTabla(arti);
	}

	// RICARDO
	public static boolean altaAlbum(Album album, int idArtista) throws AltaException {
		boolean resultado = dao.altaAlbum(album, idArtista);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// RICARDO
	public static boolean altaCancion(Cancion cancion, int idAlbum) throws AltaException {
		boolean resultado = dao.altaCancion(cancion, idAlbum);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// RICARDO
	public static boolean existeIdAlbum(int id) throws AltaException {
		return dao.existeIdAlbum(id);
	}

	// RICARDO
	public static boolean existeIdCancion(int id) throws AltaException {
		return dao.existeIdCancion(id);
	}

	// JON ANDER
	public static boolean modificarArtista(int id, String nombre, String tipo) throws SQLException {
		boolean resultado = dao.modificarArtista(id, nombre, tipo);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// JON ANDER
	public static boolean modificarAlbum(int id, String nombre, int idArtista) throws SQLException {
		boolean resultado = dao.modificarAlbum(id, nombre, idArtista);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// JON ANDER
	public static boolean modificarCancion(int id, String nombre, String genero, int idAlbum) throws SQLException {
		boolean resultado = dao.modificarCancion(id, nombre, genero, idAlbum);
		if (resultado) {
			dao.forzarGuardadoXML();
		}
		return resultado;
	}

	// JON ANDER
	public static String tipoAlbum(int idAlbum) throws SQLException {
		return dao.tipoAlbum(idAlbum);
	}
}
