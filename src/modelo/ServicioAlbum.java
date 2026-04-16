package modelo;

public class ServicioAlbum {

	private ValidadorAlbum validator;

	public ServicioAlbum(ValidadorAlbum validator) {
		this.validator = validator;
	}

	public boolean guardarAlbum(Album album) {
		if (album == null) {
			throw new IllegalArgumentException("El álbum no puede ser nulo");
		}

		if (!validator.nombreValido(album)) {
			return false;
		}
		return true;
	}
}
