package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.CallableStatement;

import exception.AltaException;
import exception.LoginException;
import modelo.Artista;
import modelo.Tipo;
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
	final String ALTAARTI="INSERT INTO ARTISTA (ID_A, NOMBRE, TIPO) VALUES (?, ?, ?)";
	final String leerArtiId="SELECT ID_A FROM ARTISTA";
	final String leerArtiNombre="SELECT NOMBRE FROM ARTISTA";
	//SELECT ID_A FROM ARTISTA WHERE ID_A NOT IN (SELECT ID_A FROM ARTISTA WHERE ID_A=?)";
	
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
	public boolean altaArtista(Artista artista) throws AltaException {
		// TODO Auto-generated method stub
		boolean result=false;
		//ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(ALTAARTI);
			stmt.setInt(1, artista.getId());
			stmt.setString(2, artista.getNombre());
			stmt.setString(3, artista.getTipo().name());

			   int filasAfectadas = stmt.executeUpdate();	//DIFERENCIA BASTA EL EXECUTEUODATE ES PARA INSERT UPDATE Y DELETE		
			   if(filasAfectadas>0){
				   result=true;
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

	@Override
	public ArrayList<Integer> ides() throws AltaException {
		// TODO Auto-generated method stub
		ArrayList<Integer> id= new ArrayList<>();
		
		ResultSet rs = null;
		openConnection();
		
		try {
			stmt = con.prepareStatement(leerArtiId);
	

			rs = stmt.executeQuery(); //exequery es para usarlo con consultas
			while(rs.next()){
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

	@Override
	public ArrayList<String> nomArti() throws AltaException {
		// TODO Auto-generated method stub
		ArrayList<String> artis= new ArrayList<>();
		ResultSet rs = null;
		openConnection();
		try {
			stmt = con.prepareStatement(leerArtiNombre);

			rs = stmt.executeQuery();
			while(rs.next()){
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
	
	public ArrayList<Artista> listarArtistas() throws AltaException {
	    ArrayList<Artista> artistas = new ArrayList<>();
	    ResultSet rs = null;
	    openConnection();
	    
	    try {
	        cs = (CallableStatement) con.prepareCall("{call ListarArtistas()}");
	        rs = cs.executeQuery();
	        
	        while(rs.next()) {
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
	            if(rs != null) rs.close();
	            closeConnection();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return artistas;
	}

/*	@Override
	public Map<Integer, Artista> leerDatosArtista() throws AltaException {
		// TODO Auto-generated method stub
		Map<Integer, Artista> ids= new HashMap<Integer, Artista>();
		ResultSet rs = null;
		openConnection();
		Artista arti;
		int cont=1;
		try {
			stmt = con.prepareStatement(leerArtiId);
	

			rs = stmt.executeQuery();
			while(rs.next()){
				arti = new Artista();
				arti.setId(rs.getInt("Id_A"));
				ids.put(cont, arti);
				cont++;
				
			}
			if (!rs.next()) {
				throw new AltaException("ERROR AL LEER DATOS");
			}

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
		
		
		return ids;
	}*/
	
	

}
