package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.CallableStatement;

import exception.AltaException;
import exception.LoginException;
import main.Principal;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Genero;
import modelo.Tipo;
import modelo.Usuario;
import utilidades.ExportadorXML;

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
	// SQL AN
	final String BUSCAR_ARTISTA = "SELECT * FROM ARTISTA";
	final String BUSCAR_ALBUM = "SELECT * FROM ALBUM";
	final String BUSCAR_CANCIONES_POR_ALBUM = "SELECT ID_C, NOMBRE, GENERO, ID_AL FROM CANCION WHERE ID_AL = ?";
//	final String BUSCAR_CANCIONES_ARTISTA = "SELECT c.ID_C, c.NOMBRE, c.GENERO, al.NOMBRE AS nombre_album "
//			+ "FROM CANCION c " + "JOIN ALBUM al ON c.ID_AL = al.ID_AL " + "WHERE al.ID_A = ?";
	final String CALL_BUSCAR_CANCIONES_ARTISTA = "{call obtenerCancionesPorArtista(?)}";
	final String SQL_BUSCAR_ALBUM_ARTISTA = "SELECT ID_AL, NOMBRE FROM ALBUM WHERE ID_A = ?";

	final String BUSCAR_CANCIONES = "SELECT * FROM CANCION";

	// SQL NORA
	// ---------------------- ELIMINAR ARTISTA ----------------------

	// Elimina las relaciones del artista con las canciones en la tabla TIENE
	final String DELETE_TIENE_ARTISTA = "DELETE FROM TIENE WHERE ID_A = ?";

	// Elimina todas las canciones de los álbumes que pertenecen a ese artista
	final String DELETE_CANCIONES_ARTISTA = "DELETE FROM CANCION WHERE ID_AL IN (SELECT ID_AL FROM ALBUM WHERE ID_A = ?)";

	// Elimina todos los álbumes del artista
	final String DELETE_ALBUMES_ARTISTA = "DELETE FROM ALBUM WHERE ID_A = ?";

	// Elimina el artista de la tabla ARTISTA
	final String DELETE_ARTISTA = "DELETE FROM ARTISTA WHERE ID_A = ?";

	// PROCEDURE
	final String CALL_ELIMINAR_ARTISTA = "{call eliminarArtistaCompleto(?)}";

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

	// SQL RICARDO
	final String ALTAARTI = "INSERT INTO ARTISTA (ID_A, NOMBRE, TIPO, IMAGEN) VALUES (?, ?, ?, ?)";
	final String leerArtiId = "SELECT ID_A FROM ARTISTA";
	final String leerArtiNombre = "SELECT NOMBRE FROM ARTISTA";
	// SELECT ID_A FROM ARTISTA WHERE ID_A NOT IN (SELECT ID_A FROM ARTISTA WHERE
	// ID_A=?)";
	final String leerArti = "SELECT * FROM ARTISTA";
	final String ALTAALBUM = "INSERT INTO ALBUM (ID_AL, NOMBRE, ID_A) VALUES (?, ?, ?)";
	final String ALTACANCION = "INSERT INTO CANCION (ID_C, NOMBRE, GENERO, ID_AL) VALUES (?, ?, ?, ?)";
	final String COMPROBAR_ALBUM = "SELECT ID_AL FROM ALBUM WHERE ID_AL = ?";
	final String COMPROBAR_CANCION = "SELECT ID_C FROM CANCION WHERE ID_C = ?";
	final String COMPROBAR_CANCION_EN_ALBUM = "SELECT ID_C FROM CANCION WHERE NOMBRE = ? AND ID_AL = ?";
	final String LISTAR_ALBUMES_POR_ARTISTA = "SELECT ID_AL, NOMBRE FROM ALBUM WHERE ID_A = ?";
	// SELECT ID_A FROM ARTISTA WHERE ID_A NOT IN (SELECT ID_A FROM ARTISTA WHERE
	// ID_A=?)";

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

	///////// NORA
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
	//////// NORAAA

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

	///////// NORA

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

	// NORAA
	public boolean eliminarArtistaProcedure(int idArtista) throws SQLException {

		openConnection();

		try {

			cs = (CallableStatement) con.prepareCall(CALL_ELIMINAR_ARTISTA);
			cs.setInt(1, idArtista);

			cs.executeUpdate();

			return true;

		} finally {
			closeConnection();
		}
	}

	// AN
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// RICARDO
	@Override
	public boolean altaArtista(Artista artista) throws AltaException {
		boolean result = false;
		// ResultSet rs = null;
		
		String nombre = artista.getNombre().toLowerCase().replace(" ", "");
		String imagen = nombre + ".jpg";
		artista.setImagen(imagen);
		
		openConnection();
		try {
			stmt = con.prepareStatement(ALTAARTI);
			stmt.setInt(1, artista.getId());
			stmt.setString(2, artista.getNombre());
			stmt.setString(3, artista.getTipo().name());
	        stmt.setString(4, artista.getImagen());

			int filasAfectadas = stmt.executeUpdate(); // DIFERENCIA BASTA EL EXECUTEUPDATE ES PARA INSERT UPDATE Y
														// DELETE
			if (filasAfectadas > 0) {
				result = true;
				return result;
			}
		} catch (SQLException e) {
			throw new AltaException("ERROR, SQL ERROR");
		} finally {
			try {

				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	// RICARDO
	@Override
	public ArrayList<Integer> ides() throws AltaException {
		ArrayList<Integer> id = new ArrayList<>();

		ResultSet rs = null;
		openConnection();

		try {
			stmt = con.prepareStatement(leerArtiId);

			rs = stmt.executeQuery(); // exequery es para usarlo con consultas
			while (rs.next()) {
				id.add(rs.getInt("ID_A"));
			}
//			if (!rs.next()) {
//				throw new AltaException("ERROR AL LEER DATOS");
//			}

		} catch (SQLException e) {
			throw new AltaException("ERROR, SQL ERROR");
		} finally {
			try {
				rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	// AN
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

	// RICARDO
	@Override
	public ArrayList<String> nomArti() throws AltaException {
		ArrayList<String> artis = new ArrayList<>();
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(leerArtiNombre);

			rs = stmt.executeQuery();
			while (rs.next()) {
				artis.add(rs.getString("NOMBRE"));
			}

		} catch (SQLException e) {
			throw new AltaException("ERROR, SQL ERROR");
		} finally {
			try {

				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return artis;
	}

	// RICARDO
	@Override
	public Map<Integer, Artista> listarArtTabla(Artista arti) throws AltaException {
		Map<Integer, Artista> map = new HashMap<Integer, Artista>();
		ResultSet rs = null;
		Artista artista;
		openConnection();

		try {
			stmt = con.prepareStatement(leerArti);
			rs = stmt.executeQuery();

			while (rs.next()) {
				artista = new Artista();
				artista.setId(rs.getInt("ID_A"));
				artista.setNombre(rs.getString("NOMBRE"));
				artista.setTipo(Tipo.valueOf(rs.getString("TIPO")));
				map.put(artista.getId(), artista);
			}

		} catch (SQLException e) {
			throw new AltaException("ERROR AL LISTAR ARTISTAS: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	// RICARDO
	@Override
	public boolean altaAlbum(Album album, int idArtista) throws AltaException {
		openConnection();
		try {
			stmt = con.prepareStatement(ALTAALBUM);
			stmt.setInt(1, album.getId());
			stmt.setString(2, album.getNombre());
			stmt.setInt(3, idArtista);

			int filasAfectadas = stmt.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			throw new AltaException("ERROR AL DAR DE ALTA ÁLBUM: " + e.getMessage());
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// RICARDO
	@Override
	public boolean altaCancion(Cancion cancion, int idAlbum) throws AltaException {
		openConnection();
		try {
			stmt = con.prepareStatement(ALTACANCION);
			stmt.setInt(1, cancion.getId());
			stmt.setString(2, cancion.getNombre());
			stmt.setString(3, cancion.getGenero().name());
			stmt.setInt(4, idAlbum);

			int filasAfectadas = stmt.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			throw new AltaException("ERROR AL DAR DE ALTA CANCIÓN: " + e.getMessage());
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// RICARDO
	@Override
	public boolean existeIdAlbum(int id) throws AltaException {
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(COMPROBAR_ALBUM);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			return rs.next(); // true si existe

		} catch (SQLException e) {
			throw new AltaException("ERROR AL COMPROBAR ID ÁLBUM: " + e.getMessage());
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
	public boolean existeIdCancion(int id) throws AltaException {
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(COMPROBAR_CANCION);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			return rs.next(); // true si existe

		} catch (SQLException e) {
			throw new AltaException("ERROR AL COMPROBAR ID CANCIÓN: " + e.getMessage());
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

	// RICARDO
	@Override
	public boolean existeCancionEnAlbum(String nombreCancion, int idAlbum) throws AltaException {
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(COMPROBAR_CANCION_EN_ALBUM);
			stmt.setString(1, nombreCancion);
			stmt.setInt(2, idAlbum);
			rs = stmt.executeQuery();

			return rs.next(); // true si existe, false si no existe

		} catch (SQLException e) {
			throw new AltaException("ERROR AL COMPROBAR CANCIÓN EN ÁLBUM: " + e.getMessage());
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

	// AN
	@Override
	public Object[][] devolverCancionesArtista(Artista a) {
		List<Object[]> listaCanciones = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
//			stmt = con.prepareStatement(BUSCAR_CANCIONES_ARTISTA);
//			stmt.setInt(1, a.getId());
//			rs = stmt.executeQuery();
			cs = (CallableStatement) con.prepareCall(CALL_BUSCAR_CANCIONES_ARTISTA);
			cs.setInt(1, a.getId());
			rs = cs.executeQuery();
			while (rs.next()) {
				Object[] fila = { rs.getInt("ID_C"), rs.getString("NOMBRE"), rs.getString("GENERO"),
						rs.getString("nombre_album") };
				listaCanciones.add(fila);
			}

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
		return listaCanciones.toArray(new Object[0][0]);

	}

	// RICARDO
	public ArrayList<Artista> listarArtistas() throws AltaException {
		ArrayList<Artista> artistas = new ArrayList<>();
		ResultSet rs = null;
		openConnection();

		try {
			cs = (CallableStatement) con.prepareCall("{call ListarArtistas()}");
			rs = cs.executeQuery();

			while (rs.next()) {
				Artista artista = new Artista();
				artista.setId(rs.getInt("ID_A"));
				artista.setNombre(rs.getString("NOMBRE"));
				artista.setTipo(Tipo.valueOf(rs.getString("TIPO")));
				artistas.add(artista);
			}

		} catch (SQLException e) {
			throw new AltaException("ERROR AL LISTAR ARTISTAS: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return artistas;
	}

	// AN
	@Override
	public List<Artista> obtenerTodosLosArtistasCompletos() throws LoginException {
		List<Artista> listaArtistas = new ArrayList<>();
		ResultSet rs = null;
		openConnection();

		try {
			stmt = con.prepareStatement(BUSCAR_ARTISTA);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Artista art = new Artista();
				art.setId(rs.getInt("ID_A"));
				art.setNombre(rs.getString("NOMBRE"));
				art.setTipo(Tipo.valueOf(rs.getString("TIPO").toUpperCase()));
				art.setImagen(rs.getString("IMAGEN"));
				art.setListaAlbumes((ArrayList<Album>) cargarAlbumesPorArtista(art.getId()));

				listaArtistas.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("Error al cargar la jerarquía completa: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaArtistas;
	}

	// AN
	private List<Album> cargarAlbumesPorArtista(int idArtista) throws SQLException {
		List<Album> albumes = new ArrayList<>();
		try (PreparedStatement psAlb = con.prepareStatement(SQL_BUSCAR_ALBUM_ARTISTA)) {
			psAlb.setInt(1, idArtista);
			try (ResultSet rsAlb = psAlb.executeQuery()) {
				while (rsAlb.next()) {
					Album alb = new Album();
					alb.setId(rsAlb.getInt("ID_AL"));
					alb.setNombre(rsAlb.getString("NOMBRE"));

					alb.setCanciones((ArrayList<Cancion>) cargarCancionesPorAlbum(alb.getId()));

					albumes.add(alb);
				}
			}
		}
		return albumes;
	}

	// AN
	private List<Cancion> cargarCancionesPorAlbum(int idAlbum) throws SQLException {
		List<Cancion> canciones = new ArrayList<>();
		try (PreparedStatement psCan = con.prepareStatement(BUSCAR_CANCIONES_POR_ALBUM)) {
			psCan.setInt(1, idAlbum);
			try (ResultSet rsCan = psCan.executeQuery()) {
				while (rsCan.next()) {
					Cancion can = new Cancion();
					can.setId(rsCan.getInt("ID_C"));
					can.setNombre(rsCan.getString("NOMBRE"));

					String gen = rsCan.getString("GENERO");
					if (gen != null) {
						can.setGenero(Genero.valueOf(gen.toUpperCase()));
					}
					canciones.add(can);
				}
			}
		}
		return canciones;
	}

	// AN
	public void forzarGuardadoXML() {
		try {
			List<Artista> listaParaExportar = Principal.obtenerTodosLosArtistasCompletos();
			ExportadorXML exportador = new ExportadorXML();
			String ruta = "artistas.xml";
			exportador.exportarArtistas(listaParaExportar, ruta);
			System.out.println("XML sincronizado automáticamente.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// AN
	@Override
	public Object[][] devolverAlbumesT() throws LoginException {
		List<Object[]> listaAlbumes = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(BUSCAR_ALBUM);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getInt("ID_AL"), rs.getString("NOMBRE"), rs.getInt("ID_A") };
				listaAlbumes.add(fila);
			}

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
		return listaAlbumes.toArray(new Object[0][0]);

	}

	// AN
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
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return albumes;
	}

	// AN
	@Override
	public Object[][] devolverCanciones() throws LoginException {
		List<Object[]> listaCanciones = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(BUSCAR_CANCIONES);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] fila = { rs.getInt("ID_C"), rs.getString("NOMBRE"), rs.getString("GENERO"),
						rs.getString("ID_AL") };
				listaCanciones.add(fila);
			}

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
		return listaCanciones.toArray(new Object[0][0]);
	}

	// JONAN

	@Override
	public boolean modificarArtista(int id, String nombre, String tipo) {
		boolean modificado = false;
		try {
			con = DriverManager.getConnection(urlDB, userDB, passwordDB);
			String sql = "UPDATE ARTISTA SET NOMBRE=?, TIPO=? WHERE ID_A=?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nombre);
			stmt.setString(2, tipo);
			stmt.setInt(3, id);
			stmt.executeUpdate();

			modificado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modificado;
	}

	// JONAN
	@Override
	public boolean modificarAlbum(int id, String nombre, int idArtista) {
		boolean modificado = false;
		try {
			con = DriverManager.getConnection(urlDB, userDB, passwordDB);
			String sql = "UPDATE ALBUM SET NOMBRE=?, ID_A=? WHERE ID_AL=?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nombre);
			stmt.setInt(2, idArtista);
			stmt.setInt(3, id);
			stmt.executeUpdate();

			modificado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modificado;
	}

	// JONAN
	@Override
	public boolean modificarCancion(int id, String nombre, String genero, int idAlbum) {
		boolean modificado = false;
		try {
			con = DriverManager.getConnection(urlDB, userDB, passwordDB);

			String sql = "UPDATE CANCION SET NOMBRE=?, GENERO=?, ID_AL=? WHERE ID_C=?";

			stmt = con.prepareStatement(sql);
			stmt.setString(1, nombre);
			stmt.setString(2, genero);
			stmt.setInt(3, idAlbum);
			stmt.setInt(4, id);
			stmt.executeUpdate();

			modificado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modificado;
	}

	// JONAN
	public Artista buscarArtista(int id) {

		Artista artista = null;
		ResultSet rs = null;

		try {
			openConnection();

			String sql = "SELECT * FROM ARTISTA WHERE ID_A = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				artista = new Artista();
				artista.setId(rs.getInt("ID_A"));
				artista.setNombre(rs.getString("NOMBRE"));
				artista.setTipo(Tipo.valueOf(rs.getString("TIPO")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return artista;
	}

	public Album buscarAlbum(int id) {

		Album album = null;
		ResultSet rs = null;

		try {
			openConnection();

			String sql = "SELECT * FROM ALBUM WHERE ID_AL = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				album = new Album();
				album.setId(rs.getInt("ID_AL"));
				album.setNombre(rs.getString("NOMBRE"));
				album.setIdArtista(rs.getInt("ID_A"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return album;
	}

	public Cancion buscarCancion(int id) {

		Cancion cancion = null;
		ResultSet rs = null;

		try {
			openConnection();

			String sql = "SELECT * FROM CANCION WHERE ID_C = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				cancion = new Cancion();
				cancion.setId(rs.getInt("ID_C"));
				cancion.setNombre(rs.getString("NOMBRE"));

				String gen = rs.getString("GENERO");
				if (gen != null) {
					cancion.setGenero(Genero.valueOf(gen));
				}

				cancion.setIdAlbum(rs.getInt("ID_AL"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cancion;
	}

	public String tipoAlbum(int idAlbum) {

		String resultado = "";

		try {

			openConnection();

			String sql = "SELECT TIPOALBUM(?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idAlbum);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				resultado = rs.getString(1);
			}

			rs.close();
			closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	@Override
	public Map<Integer, Album> listarAlbumesPorArtista(int idArtista) throws AltaException {
		Map<Integer, Album> albumes = new HashMap<>();
		ResultSet rs = null;
		openConnection();

		try {
			stmt = con.prepareStatement(LISTAR_ALBUMES_POR_ARTISTA);
			stmt.setInt(1, idArtista);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Album album = new Album();
				album.setId(rs.getInt("ID_AL"));
				album.setNombre(rs.getString("NOMBRE"));
				albumes.put(album.getId(), album);
			}

		} catch (SQLException e) {
			throw new AltaException("ERROR AL LISTAR ÁLBUMES POR ARTISTA: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return albumes;
	}
}
