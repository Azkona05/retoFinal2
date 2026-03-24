package main;

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

