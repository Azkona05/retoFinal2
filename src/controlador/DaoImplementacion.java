package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import exception.LoginException;
import modelo.Usuario;

public class DaoImplementacion implements InterfazDao {

	private ResourceBundle configFile;
	/**
	 * 
	 */
	private String urlDB;
	private String userDB;
	private String passwordDB;

	private Connection con;
	private PreparedStatement stmt;
	private CallableStatement cs;
	
	// SQL Login
	final String LOGIN = "SELECT * FROM USUARIO WHERE NOMBRE = ? AND CLAVE = ?";
	
	// ---------------------- ELIMINAR ARTISTA ----------------------

	// Elimina las relaciones del artista con las canciones en la tabla TIENE
	final String DELETE_TIENE_ARTISTA = "DELETE FROM TIENE WHERE ID_A = ?";

	// Elimina todas las canciones de los álbumes que pertenecen a ese artista
	final String DELETE_CANCIONES_ARTISTA = "DELETE FROM CANCION WHERE ID_AL IN (SELECT ID_AL FROM ALBUM WHERE ID_A = ?)";

	// Elimina todos los álbumes del artista
	final String DELETE_ALBUMES_ARTISTA = "DELETE FROM ALBUM WHERE ID_A = ?";

	// Elimina el artista de la tabla ARTISTA
	final String DELETE_ARTISTA = "DELETE FROM ARTISTA WHERE ID_A = ?";


	// ---------------------- ELIMINAR ALBUM ----------------------

	// Elimina las relaciones de las canciones del álbum en la tabla TIENE
	final String DELETE_TIENE_ALBUM = "DELETE FROM TIENE WHERE ID_C IN (SELECT ID_C FROM CANCION WHERE ID_AL = ?)";

	// Elimina todas las canciones que pertenecen al álbum
	final String DELETE_CANCIONES_ALBUM = "DELETE FROM CANCION WHERE ID_AL = ?";

	// Elimina el álbum de la tabla ALBUM
	final String DELETE_ALBUM = "DELETE FROM ALBUM WHERE ID_AL = ?";


	// ---------------------- ELIMINAR CANCION ----------------------

	// Elimina la relación de la canción con los artistas en la tabla TIENE
	final String DELETE_TIENE_CANCION = "DELETE FROM TIENE WHERE ID_C = ?";

	// Elimina la canción de la tabla CANCION
	final String DELETE_CANCION = "DELETE FROM CANCION WHERE ID_C = ?";
	
	public DaoImplementacion() {
		this.configFile = ResourceBundle.getBundle("modelo.configClass");
		this.urlDB = this.configFile.getString("Conn");
		this.userDB = this.configFile.getString("DBUser");
		this.passwordDB = this.configFile.getString("DBPass");

	}

	/**
	 * Establece una conexión con la base de datos utilizando los parámetros
	 * configurados en el constructor.
	 */
	private void openConnection() {

		try {
			con = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol_americano?serverTimezone=Europe/Madrid&useSSL=false", "root",
//				"abcd*1234");
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
		}
	}

	private void closeConnection() throws SQLException {

		if (stmt != null) {
			stmt.close();
		}
		if (con != null) {
			con.close();
		}
		if (cs != null) {
			cs.close();
		}
		if (con != null) {
			con.close();
		}

	}

	/////////NORA
	public boolean eliminarArtista(int idArtista) throws SQLException {

		openConnection();

		try {

			// Elimina relaciones en TIENE
			stmt = con.prepareStatement(DELETE_TIENE_ARTISTA);
			stmt.setInt(1, idArtista);
			stmt.executeUpdate();

			// Elimina canciones de sus álbumes
			stmt = con.prepareStatement(DELETE_CANCIONES_ARTISTA);
			stmt.setInt(1, idArtista);
			stmt.executeUpdate();

			// Elimina álbumes
			stmt = con.prepareStatement(DELETE_ALBUMES_ARTISTA);
			stmt.setInt(1, idArtista);
			stmt.executeUpdate();

			// Elimina artista
			stmt = con.prepareStatement(DELETE_ARTISTA);
			stmt.setInt(1, idArtista);

			int filas = stmt.executeUpdate();

			return filas > 0;

		} finally {
			closeConnection();
		}
	}
	////////NORAAA
	
	public boolean eliminarAlbum(int idAlbum) throws SQLException {

		openConnection();

		try {

			// Elimina relaciones en TIENE
			stmt = con.prepareStatement(DELETE_TIENE_ALBUM);
			stmt.setInt(1, idAlbum);
			stmt.executeUpdate();

			// Elimina canciones del álbum
			stmt = con.prepareStatement(DELETE_CANCIONES_ALBUM);
			stmt.setInt(1, idAlbum);
			stmt.executeUpdate();

			// Elimina álbum
			stmt = con.prepareStatement(DELETE_ALBUM);
			stmt.setInt(1, idAlbum);

			int filas = stmt.executeUpdate();

			return filas > 0;

		} finally {
			closeConnection();
		}
	}
	
	/////////NORA
	
	public boolean eliminarCancion(int idCancion) throws SQLException {

		openConnection();

		try {

			// Elimina relación en TIENE
			stmt = con.prepareStatement(DELETE_TIENE_CANCION);
			stmt.setInt(1, idCancion);
			stmt.executeUpdate();

			// Elimina canción
			stmt = con.prepareStatement(DELETE_CANCION);
			stmt.setInt(1, idCancion);

			int filas = stmt.executeUpdate(); // 🔥 guarda cuántas filas borra

			return filas > 0; // true = se ha borrado, false = no existe

		} finally {
			closeConnection();
		}
	}
	@Override
	public void login(Usuario usuario) throws LoginException {
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(LOGIN);
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getClave());

			rs = stmt.executeQuery();

			if (!rs.next()) {
				throw new LoginException("ERROR, PARAMETROS INCORRECTOS");
			}

		} catch (SQLException e) {
			throw new LoginException("ERROR, SQL ERROR");
		} finally {
			try {
				rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
