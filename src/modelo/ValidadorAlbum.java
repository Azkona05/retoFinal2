package modelo;

	public class ValidadorAlbum {

	    public boolean nombreValido(Album album) {
	        return album != null
	                && album.getNombre() != null
	                && !album.getNombre().trim().isEmpty();
	    }
	}

