package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.CallableStatement;

import exception.LoginException;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Genero;
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
	// SQL
	final String BUSCAR_ARTISTA = "SELECT * FROM ARTISTA";
	final String BUSCAR_ALBUM = "SELECT * FROM ALBUM";
	final String BUSCAR_CANCIONES_POR_ALBUM = "SELECT ID_C, NOMBRE, GENERO, ID_AL FROM CANCION WHERE ID_AL = ?";
	final String BUSCAR_CANCIONES_ARTISTA = "SELECT c.ID_C, c.NOMBRE, c.GENERO, al.NOMBRE AS nombre_album "
			+ "FROM CANCION c " + "JOIN ALBUM al ON c.ID_AL = al.ID_AL " + "WHERE al.ID_A = ?";

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

	@Override
	public Object[][] devolverArtistas(Artista a) throws LoginException {
		List<Object[]> listaArtistas = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(BUSCAR_ARTISTA);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getString("id_a"), rs.getString("nombre"), rs.getString("tipo"), null };
				listaArtistas.add(fila);
			}

			return listaArtistas.toArray(new Object[0][0]);

		} catch (SQLException e) {
			throw new LoginException("Problemas en la BDs");
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Album> devolverAlbumes() throws LoginException {
		Album al;
		List<Album> albumes = new ArrayList<Album>();
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(BUSCAR_ALBUM);
			rs = stmt.executeQuery();
			while (rs.next()) {
				al = new Album();
				al.setId(rs.getInt(1));
				al.setNombre(rs.getString(2));
				al.setIdArtista(rs.getInt("id_a"));
				albumes.add(al);
			}
		} catch (SQLException e) {
			throw new LoginException("Problemas en la BDs");
		} finally {
			try {
				rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return albumes;
	}

	@Override
	public List<Cancion> devolverCanciones(int idAlbum) throws LoginException {
		List<Cancion> canciones = new ArrayList<>();
		ResultSet rs = null;
		openConnection();

		try {
			stmt = con.prepareStatement(BUSCAR_CANCIONES_POR_ALBUM);
			stmt.setInt(1, idAlbum);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Cancion cancion = new Cancion();
				cancion.setId(rs.getInt("ID_C"));
				cancion.setNombre(rs.getString("NOMBRE"));

				String generoTexto = rs.getString("GENERO");
				if (generoTexto != null && !generoTexto.trim().isEmpty()) {
					cancion.setGenero(Genero.valueOf(generoTexto.toUpperCase()));
				}
				cancion.setIdAlbum(rs.getInt("ID_AL"));
				canciones.add(cancion);
			}
		} catch (SQLException e) {
			throw new LoginException("Error al consultar las canciones en la BD: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return canciones;
	}

	@Override
	public Object[][] devolverCancionesArtista(Artista a) {
		List<Object[]> listaCanciones = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(BUSCAR_CANCIONES_ARTISTA);
			stmt.setInt(1, a.getId());

			rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getInt("ID_C"), rs.getString("NOMBRE"), rs.getString("GENERO"),
						rs.getString("nombre_album") };
				listaCanciones.add(fila);
			}

			return listaCanciones.toArray(new Object[0][0]);

		} catch (SQLException e) {
			System.out.println("Error al buscar las canciones del artista: " + e.getMessage());
			return new Object[0][0];
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
