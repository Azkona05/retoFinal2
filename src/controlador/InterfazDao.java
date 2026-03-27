package controlador;

import java.util.ArrayList;
import java.util.Map;

import exception.AltaException;
import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Usuario;

public interface InterfazDao {

	public void login(Usuario usuario) throws LoginException;

	public boolean altaArtista(Artista artista) throws AltaException;

	public ArrayList<Integer> ides() throws AltaException;

	public ArrayList<String> nomArti() throws AltaException;
	 public Map<Integer, Album> listarAlbumesPorArtista(int idArtista) throws AltaException;

	public Map<Integer, Artista> listarArtTabla(Artista arti) throws AltaException;

	public boolean altaAlbum(Album album, int idArtista) throws AltaException;

	public boolean altaCancion(Cancion cancion, int idAlbum) throws AltaException;

	public boolean existeIdAlbum(int id) throws AltaException;

	public boolean existeIdCancion(int id) throws AltaException;
	public boolean existeCancionEnAlbum(String nombreCancion, int idAlbum) throws AltaException;

}
