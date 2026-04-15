package modelo;

import java.util.ArrayList;

public class Album {

	private int id;
	private String nombre;
	private ArrayList<Cancion> canciones;
	private int idArtista;

	public Album() {
		super();
	}

	public Album(int id, String nombre, ArrayList<Cancion> canciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.canciones = canciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre del álbum no puede ser nulo");
		}
		this.nombre = nombre;
	}

	public String toString() {
		return nombre;
	}

	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}

	public int getIdArtista() {
		return idArtista;
	}

	public void setIdArtista(int idArtista) {
		this.idArtista = idArtista;
	}
	
	public boolean tieneCanciones() {
	    return this.canciones != null && !this.canciones.isEmpty();
	}

	public boolean esNombreValido() {
	    return this.nombre != null && !this.nombre.trim().isEmpty();
	}

}
