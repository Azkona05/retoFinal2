package modelo;

public class Usuario {

	private String nombre;
	private String clave;
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String contrasenia) {
		this.nombre = nombre;
		this.clave = contrasenia;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setClave(String contrasenia) {
		this.clave = contrasenia;
	}
	
	public String toString() {
		return "Usuario: " + nombre;
	}
	
	
}

