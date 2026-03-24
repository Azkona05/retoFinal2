package main;

import java.util.List;
import java.sql.SQLException;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import java.util.ArrayList;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.AltaException;
import exception.LoginException;
import modelo.Artista;
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

	public static Object[][] devolverArtistas(Artista a) throws LoginException {
		return dao.devolverArtistas(a);
	}

	public static List<Album> devolverAlbumes() throws LoginException{
		return dao.devolverAlbumes();
	}
	
	public static List<Cancion> devolverCanciones(int idAlbum) throws LoginException{
		return dao.devolverCanciones(idAlbum);
	}

	public static Object[][] devolverCancionesArtista(Artista a) throws LoginException{
		return dao.devolverCancionesArtista(a);
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


	public static boolean alta(Artista artista) throws AltaException {
				return dao.altaArtista(artista);

	}
//	public static Map<Integer,Artista>leerDatosArtista()throws AltaException{
//		return dao.leerDatosArtista();
//	}
	
	public static ArrayList<Integer>leerIds() throws AltaException{
		return dao.ides();
		
	}
	public static ArrayList<String>leerNombreArti() throws AltaException{
		return dao.nomArti();
	}
}


