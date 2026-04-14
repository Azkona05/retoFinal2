package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import java.util.Map;

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
	
	public static Object[][] devolverAlbumesT() throws LoginException {
		return dao.devolverAlbumesT();
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

	public static boolean existeCancionEnAlbum(String nombreCancion, int idAlbum) throws AltaException {
		return dao.existeCancionEnAlbum(nombreCancion, idAlbum);
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
	
	public static Object[][] devolverCanciones() throws LoginException {
		return dao.devolverCanciones();
	}
	public static Map<Integer, Artista> listarArti(Artista arti) throws AltaException {
		return dao.listarArtTabla(arti);
	}

	public static boolean altaAlbum(Album album, int idArtista) throws AltaException {
		return dao.altaAlbum(album, idArtista);
	}

	public static boolean altaCancion(Cancion cancion, int idAlbum) throws AltaException {
		return dao.altaCancion(cancion, idAlbum);
	}

	public static boolean existeIdAlbum(int id) throws AltaException {
		return dao.existeIdAlbum(id);
	}

	public static boolean existeIdCancion(int id) throws AltaException {
		return dao.existeIdCancion(id);
	}

}
