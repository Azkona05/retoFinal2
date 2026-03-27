package main;

import java.util.ArrayList;
import java.util.Map;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.AltaException;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Tipo;
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

	public static boolean alta(Artista artista) throws AltaException {
		return dao.altaArtista(artista);

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
	

	public static Map<Integer, Album> listarAlbumesPorArtista(int idArtista) throws AltaException {
	    return dao.listarAlbumesPorArtista(idArtista);
	}

	public static ArrayList<String> leerNombreArti() throws AltaException {
		return dao.nomArti();
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
