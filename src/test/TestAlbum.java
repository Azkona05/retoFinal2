package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Album;
import modelo.Cancion;

/**
 * @author An Azkona
 */

class TestAlbum {
	private Album album;
	private ArrayList<Cancion> canciones;

	@BeforeEach
	void setUp() {
		canciones = new ArrayList<>();
		album = new Album();
	}

	@Test
	void testConstructorVacio() {
		assertNotNull(album);
		assertEquals(0, album.getId());
		assertNull(album.getNombre());
		assertNull(album.getCanciones());
		assertEquals(0, album.getIdArtista());
	}

	@Test
	void testConstructorConParametros() {
		Album album2 = new Album(1, "Mi Album", canciones);

		assertEquals(1, album2.getId());
		assertEquals("Mi Album", album2.getNombre());
		assertEquals(canciones, album2.getCanciones());
	}

	@Test
	void tesGetId() {
		album.setId(10);
		assertEquals(10, album.getId());
	}

	@Test
	void testValidacionNombreNoNulo() {
		assertThrows(IllegalArgumentException.class, () -> {
			album.setNombre(null);
		}, "El nombre del álbum no puede ser nulo");
	}
	
	@Test
	void testToString() {
		album.setNombre("Pop 2025");
		assertEquals("Pop 2025", album.toString());
	}

	@Test
	void testGetCanciones() {
		album.setCanciones(canciones);
		assertEquals(canciones, album.getCanciones());
	}

	@Test
	void testGetIdArtista() {
		album.setIdArtista(25);
		assertEquals(25, album.getIdArtista());
	}

	@Test
	void testToStringNull() {
		assertNull(album.toString());
	}

	@Test
	void testTieneCanciones() {
		assertFalse(album.tieneCanciones(), "El álbum no tiene canciones");

		ArrayList<Cancion> lista = new ArrayList<>();
		lista.add(new Cancion());
		album.setCanciones(lista);

		assertTrue(album.tieneCanciones(), "El álbum sí tiene canciones");
	}

	@Test
	void testEsNombreValido() {
		assertThrows(IllegalArgumentException.class, () -> {
			album.setNombre(null);
		});
		assertFalse(album.esNombreValido(), "El nombre es null");

		album.setNombre("   ");
		assertFalse(album.esNombreValido(), "El nombre solo tiene espacios");

		album.setNombre("Abbey Road");
		assertTrue(album.esNombreValido(), "Nombre valido");
	}
}