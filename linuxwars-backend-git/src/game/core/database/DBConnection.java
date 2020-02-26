package game.core.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBConnection {
	
    private Connection db_con;
    private DatabaseMetaData db_dbm;
    private Statement db_statement;
	
    private String host, user, password, port;
	
    /**
    * <i>Constructor for {@link DBConnection}</i>
    * <pre>    public DBConnection({@link String} host, {@link Integer} port, {@link String} user, {@link String} password)</pre>
    * <p>
    * @param host ip adresse without port
    * @param port port for the database (default: 3306)
    * @param user username of the user that has access read/write to the database
    * @param password password for given user
     * @throws SQLException 
    */
    public DBConnection(String host, String port, String user, String password) throws SQLException{
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        connect();
    }
	
	/**
	 * <i>Method to connect the {@link DBConnection} to the database</i>
	 * <pre>    public {@link Boolean} connect()</pre>
	 * <p>
	 * @return Returns true if the connection was successful and false if not
	 * @throws SQLException 
	 * @see {@link DBConnection#disconnect}
	 */
	public void connect() throws SQLException{
		db_con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port, this.user, this.password);
		db_con.setCatalog("browsergame");
		db_dbm = db_con.getMetaData();
		db_statement = db_con.createStatement();
	}
	
	/**
	 * <i>Method to disconnect the {@link DBConnection} from the database</i>
	 * <pre>    public {@link Boolean} disconnect()</pre>
	 * <p>
	 * @return Returns true if the connection was successfuly closed and false if not
	 * @see DBConnection#connect
	 */
	public boolean disconnect() {
		try {
			db_statement.close();
			db_con.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * <i>Executes a sql command and returns a results</i>
	 * <pre>    public {@link ArrayList}<{@link HashMap}<{@link String}, {@link String}>> executeResults({@link String} sql, {@link Boolean} error)</pre>
	 * <p>
	 * @param sql command in form of a {@link String}
	 * @param error true if you want to printStackTrace() and false if not
	 * @return returns a {@link ArrayList} containing  a {@link HashMap} per row with the column names as key and the column value as value
	 * @throws SQLException 
	 */
	public ArrayList<HashMap<String, String>> executeResults(String sql) throws SQLException {
		
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		ResultSet res = db_statement.executeQuery(sql);
		ResultSetMetaData rsmd = res.getMetaData();
		while(res.next()) {
			HashMap<String, String> tmp = new HashMap<String, String>();
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				tmp.put(rsmd.getColumnName(i), res.getString(i));
			}
			result.add(tmp);
		}
		return result;
	}
	
	/**
	 * <i>Executes a sql command</i>
	 * <pre>    public {@link Boolean} execute({@link String} sql, {@link Boolean} error)</pre>
	 * <p>
	 * @param sql command in form of a {@link String}
	 * @param error true if you want to printStackTrace() and false if not
	 * @return returns a {@link boolean} - true if successfull and false if not
	 * @throws SQLException 
	 */
	public void execute(String sql) throws SQLException {
		db_statement.executeUpdate(sql);
	}
	
	/**<i>Checks if table exists in a database</i>
	 * <pre>    public {@link Boolean} tableExists({@link String} table, {@link String} database)</pre>
	 * <p>
	 * @param table name in form of a {@link String}
	 * @param database name in form of a {@link String}
	 * @return a {@link boolean} that is true, when the table exists and false if not
	 */
	public boolean tableExists(String table, String database) {
		try {
			ResultSet result = db_dbm.getTables(database, null, "test", null);
			if(result.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
}
