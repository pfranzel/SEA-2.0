package de.telekom.sea2.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

public class PersonsRepository {

	private DBConnect dbconn;
	private String sql;

	public PersonsRepository(DBConnect conn) {
		this.dbconn = conn;
	}

	public boolean create(Person p) {
		try {
			DBConnect dbconn = new DBConnect();
//			Statement statement = dbconn.getConnection().createStatement();

			PreparedStatement preparedStatement = dbconn.getConnection()
					.prepareStatement("INSERT INTO persons (id, salutation, firstname, lastname) VALUES ( ?, ?, ?, ?)");
			preparedStatement.setLong(1, p.getId());
			preparedStatement.setByte(2, p.getSalutation().toByte());
			preparedStatement.setString(3, p.getFirstname());
			preparedStatement.setString(4, p.getLastname());
			preparedStatement.execute();
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	public boolean update(Person p) {
		return false;
	}

	public boolean delete(Person p) {
		long id = p.getId();
		delete(id);
		return false;
	}

	public boolean deleteAll() {
		sql = "truncate table persons";
		try {
			DBConnect dbconn = new DBConnect();
			dbconn.getConnection().prepareStatement(sql).executeQuery();
			System.out.println("Table persons truncated!");
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public boolean delete(long id) {
		return false;
	}

	public Person get(long id) {

		return null;
	}

	public long getSize() {

		sql = "select count(*) AS total FROM persons";

		try {
			DBConnect dbconn = new DBConnect();
			ResultSet resultSet = dbconn.getConnection().prepareStatement(sql).executeQuery();
			resultSet.next();
			System.out.println("Size: " + resultSet.getInt("total"));
		} catch (Exception e) {
			System.out.println("getSize Exception: " + e);
		}
		return 0;
	}

	public ArrayList<Person> getAll() {

		sql = "select * from persons";

		try {
			DBConnect dbconn = new DBConnect();
			ResultSet resultSet = dbconn.getConnection().prepareStatement(sql).executeQuery();
			System.out.println("ID\tSalutation\tFirstname\tLastname");
			while (resultSet.next()) {
				System.out.print(resultSet.getInt(1) + "\t");
				System.out.print(Salutation.fromByte(resultSet.getByte(2)) + "\t\t");
//				System.out.print(resultSet.getInt(2) + "\t\t");
				System.out.print(resultSet.getString(3) + "\t\t");
				System.out.println(resultSet.getString(4) + " ");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
}
