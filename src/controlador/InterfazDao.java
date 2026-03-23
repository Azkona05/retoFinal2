package controlador;

import java.sql.SQLException;

import exception.LoginException;
import modelo.Usuario;

public interface InterfazDao {

	public void login(Usuario usuario) throws LoginException;
	public boolean eliminarAlbum(int idAlbum) throws SQLException;
	public boolean eliminarArtista(int idArtista) throws SQLException;
	public boolean eliminarCancion(int idCancion) throws SQLException;

}