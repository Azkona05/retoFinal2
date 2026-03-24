package controlador;

import java.util.ArrayList;

import exception.AltaException;
import exception.LoginException;
import modelo.Artista;
import modelo.Usuario;

public interface InterfazDao {

	 public void login(Usuario usuario) throws LoginException;
	 public boolean altaArtista(Artista artista) throws AltaException;//necesito hcer alta de artista,album,cancion
	 public ArrayList<Integer>ides() throws AltaException;
	 public ArrayList<String>nomArti() throws AltaException;
	
	
}
