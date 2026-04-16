package controlador;

import java.util.List;

import exception.LoginException;

import java.sql.SQLException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import java.util.ArrayList;

import exception.AltaException;
import java.util.Map;
import modelo.Usuario;

public interface InterfazDao {

	//METODOS COMUNES
	public void login(Usuario usuario) throws LoginException;
	
	// AN

	public Object[][] devolverArtistas(Artista a) throws LoginException;

	public List<Cancion> devolverCanciones(int idAlbum) throws LoginException;

	public Object[][] devolverCancionesArtista(Artista a);

	public Object[][] devolverAlbumesT() throws LoginException;

	public List<Artista> obtenerTodosLosArtistasCompletos() throws LoginException;

	public void forzarGuardadoXML();

	public List<Album> devolverAlbumes() throws LoginException;

	public Object[][] devolverCanciones() throws LoginException;

	// NORA

	public boolean eliminarAlbum(int idAlbum) throws SQLException;

	public boolean eliminarArtista(int idArtista) throws SQLException;

	public boolean eliminarCancion(int idCancion) throws SQLException;

	// RICARDO

	public boolean altaArtista(Artista artista) throws AltaException;

	public ArrayList<Integer> ides() throws AltaException;

	public ArrayList<String> nomArti() throws AltaException;
		
	public Map<Integer, Artista> listarArtTabla(Artista arti) throws AltaException;

	public boolean altaAlbum(Album album, int idArtista) throws AltaException;

	public boolean altaCancion(Cancion cancion, int idAlbum) throws AltaException;

	public boolean existeIdAlbum(int id) throws AltaException;

	public boolean existeIdCancion(int id) throws AltaException;
	
	public boolean existeCancionEnAlbum(String nombreCancion, int idAlbum) throws AltaException;
	
	public Map<Integer, Album> listarAlbumesPorArtista(int idArtista) throws AltaException;
	
	//JONAN
	
	public boolean modificarArtista(int id, String nombre, String tipo) throws SQLException;

	public boolean modificarAlbum(int id, String nombre, int idArtista) throws SQLException;

	public boolean modificarCancion(int id, String nombre, String genero, int idAlbum) throws SQLException;
	
	public Artista buscarArtista(int id);
	
	public Album buscarAlbum(int id);
	
	public Cancion buscarCancion(int id);

	public String tipoAlbum(int idAlbum);

}
