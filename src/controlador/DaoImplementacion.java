package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.CallableStatement;

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
	final String LOGIN = "SELECT * FROM USUARIO WHERE NOM = ? AND CONTRASENIA = ?";
	
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

}
