package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Cancion;
import modelo.Genero;

/**
 * @author Nora Yakoubi
 */

class TestCancion {

	private Cancion cancion;

	@BeforeEach
	void setUp() {
		cancion = new Cancion();
	}

	@Test
	void testConstructorVacio() {
		assertNotNull(cancion);
		assertEquals(0, cancion.getId());
		assertNull(cancion.getNombre());
		assertNull(cancion.getGenero());
		assertEquals(0, cancion.getIdAlbum());
	}

	@Test
	void testConstructorConParametros() {
		Genero genero = Genero.POP; // ajusta a tu enum
		Cancion c = new Cancion(1, "MiCancion", genero);

		assertEquals(1, c.getId());
		assertEquals("MiCancion", c.getNombre());
		assertEquals(genero, c.getGenero());
	}

	@Test
	void tesGetId() {
		cancion.setId(10);
		assertEquals(10, cancion.getId());
	}

	@Test
	void testGetNombre() {
		cancion.setNombre("CancionTest");
		assertEquals("CancionTest", cancion.getNombre());
	}

	@Test
	void testGetGenero() {
		Genero genero = Genero.ROCK; // ajusta a tu enum
		cancion.setGenero(genero);
		assertEquals(genero, cancion.getGenero());
	}

	@Test
	void testGetIdAlbum() {
		cancion.setIdAlbum(5);
		assertEquals(5, cancion.getIdAlbum());
	}

	@Test
	void testToString() {
		cancion.setId(1);
		cancion.setNombre("MiCancion");
		cancion.setGenero(Genero.POP);

		assertEquals("Cancion [id=1, nombre=MiCancion, genero=" + Genero.POP + "]", cancion.toString());
	}

	@Test
	void testToStringNull() {
		assertEquals("Cancion [id=0, nombre=null, genero=null]", cancion.toString());
	}
}