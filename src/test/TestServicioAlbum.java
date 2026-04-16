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

class TestServicioAlbum {

	private ValidadorAlbum validatorMock;
	private ServicioAlbum service;
	private Album album;

	@BeforeEach
	void setUp() {
		validatorMock = Mockito.mock(ValidadorAlbum.class);
		service = new ServicioAlbum(validatorMock);

		album = new Album();
		album.setNombre("Abbey Road");
	}

	@Test
	void testGuardarAlbumNombreValido() {
		when(validatorMock.nombreValido(album)).thenReturn(true);

		boolean resultado = service.guardarAlbum(album);

		assertTrue(resultado);
		verify(validatorMock).nombreValido(album);
	}

	@Test
	void testGuardarAlbumNombreInvalido() {
		when(validatorMock.nombreValido(album)).thenReturn(false);

		boolean resultado = service.guardarAlbum(album);

		assertFalse(resultado);
		verify(validatorMock).nombreValido(album);
	}

	@Test
	void testGuardarAlbumNulo() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.guardarAlbum(null));

		assertEquals("El álbum no puede ser nulo", ex.getMessage());
		verifyNoInteractions(validatorMock);
	}
}
