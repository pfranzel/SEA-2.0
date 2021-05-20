package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
	final private String userName = "seauser";
	final private String password = "seapass";
	final private String dbms = "mariadb";
	final private String serverName = "localhost";
	final private String portNumber = "3306";
	final private String db = "seadb";

	public Connection getConnection() throws SQLException {
		System.out.println("getConnection: Start");
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		if (this.dbms.equals("mariadb")) {
			connection = DriverManager.getConnection(
					"jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/" + this.db, connectionProps);

			System.out.println("getConnection: Connected; End");
			return connection;

		}
		System.out.println("getConnection: Not connected; End");
		return null;
	}
	
//	public Connection createStatement(String sql) {
//		System.out.println("createStatement: Start");
//		
//		return null;
//}

}
