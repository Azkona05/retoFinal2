package controlador;

import java.util.List;

import exception.LoginException;

import java.sql.SQLException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import java.util.ArrayList;

import exception.AltaException;
import exception.LoginException;
import modelo.Artista;
import modelo.Usuario;

public interface InterfazDao {

	 public void login(Usuario usuario) throws LoginException;
	 public Object[][] devolverArtistas(Artista a)throws LoginException;
	 public List<Album> devolverAlbumes() throws LoginException; 
	 public List<Cancion> devolverCanciones(int idAlbum) throws LoginException;
	 public Object[][] devolverCancionesArtista(Artista a);
	public boolean eliminarAlbum(int idAlbum) throws SQLException;
	public boolean eliminarArtista(int idArtista) throws SQLException;
	public boolean eliminarCancion(int idCancion) throws SQLException;

	 public boolean altaArtista(Artista artista) throws AltaException;//necesito hcer alta de artista,album,cancion
	 public ArrayList<Integer>ides() throws AltaException;
	 public ArrayList<String>nomArti() throws AltaException;
	
	
}
