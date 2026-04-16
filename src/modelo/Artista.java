package modelo;

import java.util.ArrayList;

public class Artista {

	private int id;
	private String nombre;
	private Tipo tipo; 
	private String imagen;
	private ArrayList<Album> listaAlbumes;
	private ArrayList<Cancion> listaCanciones;

	public Artista() {
	}

	public Artista(int id, String nombre, Tipo tipo, String imagen, ArrayList<Album> listaAlbumes,
			ArrayList<Cancion> listaCanciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.imagen = imagen;
		this.listaAlbumes = listaAlbumes;
		this.setListaCanciones(listaCanciones);
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
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public ArrayList<Album> getListaAlbumes() {
		return listaAlbumes;
	}

	public void setListaAlbumes(ArrayList<Album> listaAlbumes) {
		this.listaAlbumes = listaAlbumes;
	}
	

	public ArrayList<Cancion> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(ArrayList<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}

	
	public String toString() {
		return "Artista [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", listaAlbumes=" + listaAlbumes
				+ ", listaCanciones=" + listaCanciones + "]";
	}



}
