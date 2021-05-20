package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnect {
//	final private String DRIVER = "org.mariadb.jdbc.Driver";
//	final private String URL = "jdbc:mariadb://localhost:3306/seadb?user=seauser&password=seapass";
	final private String userName = "seauser";
	final private String password = "seapass";
	final private String dbms = "mariadb";
	final private String serverName = "localhost";
	final private String portNumber = "3306";

	public Connection getConnection() throws SQLException {

		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		if (this.dbms.equals("mariadb")) {
			connection = DriverManager.getConnection(
					"jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/", connectionProps);

			System.out.println("Connected to database");
			return connection;

		}
		System.out.println("I was in getConnection");

		return null;
//		try {
//			Class.forName(DRIVER);
//			Connection connection = DriverManager.getConnection(URL);
//
//			Statement statement = connection.createStatement();
//
//		} catch (Exception e) {
//			System.out.println("Somthing went wrong setting up the connection!" + e);
//		}
//		return null;
	}

}
