package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import modelo.Album;
import modelo.ValidadorAlbum;
import modelo.ServicioAlbum;

// Clase de test para ServicioAlbum
class TestServicioAlbum {

	// Mock del validador (simula su comportamiento)
	private ValidadorAlbum validatorMock;

	// Clase que queremos probar
	private ServicioAlbum service;

	// Objeto de prueba
	private Album album;

	// Se ejecuta antes de cada test
	@BeforeEach
	void setUp() {
		// Creamos un mock del validador
		validatorMock = Mockito.mock(ValidadorAlbum.class);

		// Inyectamos el mock en el servicio
		service = new ServicioAlbum(validatorMock);

		// Creamos un álbum de prueba
		album = new Album();
		album.setNombre("Abbey Road");
	}

	// Test: el nombre del álbum es válido
	@Test
	void testGuardarAlbumNombreValido() {
		// Simulamos que el validador devuelve true
		when(validatorMock.nombreValido(album)).thenReturn(true);

		// Ejecutamos el método a probar
		boolean resultado = service.guardarAlbum(album);

		// Comprobamos que devuelve true
		assertTrue(resultado);

		// Verificamos que se llamó al validador
		verify(validatorMock).nombreValido(album);
	}

	// Test: el nombre del álbum es inválido
	@Test
	void testGuardarAlbumNombreInvalido() {
		// Simulamos que el validador devuelve false
		when(validatorMock.nombreValido(album)).thenReturn(false);

		// Ejecutamos el método
		boolean resultado = service.guardarAlbum(album);

		// Comprobamos que devuelve false
		assertFalse(resultado);

		// Verificamos que se llamó al validador
		verify(validatorMock).nombreValido(album);
	}

	// Test: se pasa un álbum nulo
	@Test
	void testGuardarAlbumNulo() {
		// Esperamos que se lance una excepción
		IllegalArgumentException ex = assertThrows(
			IllegalArgumentException.class,
			() -> service.guardarAlbum(null)
		);

		// Verificamos el mensaje de la excepción
		assertEquals("El álbum no puede ser nulo", ex.getMessage());

		// Verificamos que NO se llamó al validador
		verifyNoInteractions(validatorMock);
	}
}