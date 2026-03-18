package main;

import java.util.List;

import controlador.DaoImplementacion;
import controlador.InterfazDao;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
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
}
