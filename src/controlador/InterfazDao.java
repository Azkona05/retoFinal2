package controlador;

import java.util.List;

import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Usuario;

public interface InterfazDao {

	 public void login(Usuario usuario) throws LoginException;
	 public Object[][] devolverArtistas(Artista a)throws LoginException;
	 public List<Album> devolverAlbumes() throws LoginException; 
	 public List<Cancion> devolverCanciones(int idAlbum) throws LoginException;
	 public Object[][] devolverCancionesArtista(Artista a);
}
