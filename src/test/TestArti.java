package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Tipo;

/**
 * @author Ricardo Soza
 * 
 * 
 */
class TestArti {

    private Artista artista;
    private ArrayList<Album> albumes;
    private ArrayList<Cancion> canciones;

    @BeforeEach
    void setUp() {
        albumes = new ArrayList<>();
        canciones = new ArrayList<>();
        artista = new Artista();
    }

    // assertTrue - Verificar que un artista tiene lista de álbumes vacía al inicio
     
    @Test
    void testListaAlbumesVaciaAlInicio() {
        // assertTrue: verifica que la lista de álbumes está vacía
        assertTrue(artista.getListaAlbumes() == null || artista.getListaAlbumes().isEmpty(), 
                "La lista de álbumes debería estar vacía o ser null al inicio");
        
        // Inicializamos la lista
        artista.setListaAlbumes(new ArrayList<>());
        assertTrue(artista.getListaAlbumes().isEmpty(), 
                "La lista de álbumes debería estar vacía después de inicializarla");
    }

    // assertFalse - Verificar que después de añadir un álbum la lista no está vacía
     
    @Test
    void testListaAlbumesNoVaciaDespuesDeAnadir() {
        artista.setListaAlbumes(new ArrayList<>());
        Album album = new Album();
        artista.getListaAlbumes().add(album);
        
        // assertFalse: verificar que la lista NO está vacía
        assertFalse(artista.getListaAlbumes().isEmpty(), 
                "La lista de álbumes no debería estar vacía después de añadir un álbum");
    }

    // assertEquals - Verificar los getters y setters
     
    @Test
    void testGettersYSetters() {
        artista.setId(1);
        artista.setNombre("Bad Bunny");
        artista.setTipo(Tipo.SOLO);
        
        assertEquals(1, artista.getId(), "El ID debería ser 1");
        assertEquals("Bad Bunny", artista.getNombre(), "El nombre debería ser 'Bad Bunny'");
        assertEquals(Tipo.SOLO, artista.getTipo(), "El tipo debería ser SOLO");
    }

    //assertNull - Verificar que la lista de canciones es null al inicio

    @Test
    void testListaCancionesNullInicialmente() {
        // assertNull: verificar que la lista de canciones es null 
        assertNull(artista.getListaCanciones(), 
                "La lista de canciones debería ser null al inicio");
    }

    // assertNotNull - Verificar que después de setear, la lista de canciones no es null
    @Test
    void testListaCancionesNotNullDespuesDeSetter() {
        artista.setListaCanciones(new ArrayList<>());
        
        // assertNotNull: verificar que la lista NO es null después de asignarla
        assertNotNull(artista.getListaCanciones(), 
                "La lista de canciones no debería ser null después de usar setListaCanciones");
    }

    //assertSame - Verificar que la lista de álbumes es la misma que se asignó
     
    @Test
    void testMismaListaAlbumes() {
        ArrayList<Album> nuevaLista = new ArrayList<>();
        nuevaLista.add(new Album());
        nuevaLista.add(new Album());
        
        artista.setListaAlbumes(nuevaLista);
        
        // assertSame: verificar sea lo mismo
        assertSame(nuevaLista, artista.getListaAlbumes(), 
                "La lista de álbumes debería ser el mismo objeto que se asignó");
    }

    //assertNotSame - Verificar que dos artistas diferentes sean distintos
     
    @Test
    void testArtistasDiferentes() {
        Artista artista1 = new Artista();
        Artista artista2 = new Artista();
        
        artista1.setId(1);
        artista2.setId(2);
        
        // assertNotSame: verifica que son diferentes
        assertNotSame(artista1, artista2, 
                "Dos instancias diferentes de Artista deberían ser objetos distintos");
        
        // También verificamos que los IDs son diferentes
        assertNotSame(artista1.getId(), artista2.getId(), 
                "Los IDs de artistas diferentes deberían ser distintos");
    }

    // fail - Verificar el constructor con parámetros
     
    @Test
    void testConstructorConParametros() {
        try {
            ArrayList<Album> listaAlbumesTest = new ArrayList<>();
            ArrayList<Cancion> listaCancionesTest = new ArrayList<>();
            
            listaAlbumesTest.add(new Album());
            listaCancionesTest.add(new Cancion());
            
            Artista artistaCompleto = new Artista(5, "Queen", Tipo.GRUPO, null, listaAlbumesTest, listaCancionesTest);
            
            assertEquals(5, artistaCompleto.getId());
            assertEquals("Queen", artistaCompleto.getNombre());
            assertEquals(Tipo.GRUPO, artistaCompleto.getTipo());
            assertSame(listaAlbumesTest, artistaCompleto.getListaAlbumes());
            assertSame(listaCancionesTest, artistaCompleto.getListaCanciones());
            
        } catch (Exception e) {
            // fail: si ocurre cualquier excepción, el test falla
            fail("El constructor con parámetros no debería lanzar excepción: " + e.getMessage());
        }
    }
}