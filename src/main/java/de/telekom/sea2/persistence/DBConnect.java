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
	private Connection connection;

	public Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		if (this.dbms.equals("mariadb")) {
			connection = DriverManager.getConnection(
					"jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/" + this.db,
					connectionProps);
			return connection;
		}

		System.out.println("getConnection: Not connected");
		return null;
	}

	public void close() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
}