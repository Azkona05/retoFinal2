package modelo;

public class Cancion {

	private int id;
	private String nombre;
	private Genero genero;
	
	public Cancion() {
		
	}
	
	public Cancion(int id, String nombre, Genero genero) {
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Genero getGenero() {
		return genero;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public String toString() {
		return "Cancion [id=" + id + ", nombre=" + nombre + ", genero=" + genero + "]";
	}
	
	
}
