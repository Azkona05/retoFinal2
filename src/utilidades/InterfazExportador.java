package utilidades;

import java.util.List;

import modelo.Artista;

public interface InterfazExportador {

	void exportarArtistas(List<Artista> lista, String ruta);
}
