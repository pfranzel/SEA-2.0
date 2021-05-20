package de.telekom.sea2.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

public class PersonsRepository {

	private DBConnect dbconn;
	private String sql;
	private int id;
	private int lastId;

	public PersonsRepository(DBConnect conn) {
		this.dbconn = conn;
	}

	public boolean create(Person p) {

		try {
			
			DBConnect dbconn = new DBConnect();
			ResultSet resultSet = dbconn.getConnection().prepareStatement("SELECT id FROM persons ORDER BY id DESC LIMIT 1").executeQuery();
			resultSet.next();
			System.out.println("lastId: " + resultSet.getInt("id"));
		} catch (Exception e) {
			System.out.println("It seams you are the first in the list - beginning with ID 0 ");
			lastId++;
		}

		System.out.println("lastIdffff: " + lastId);
		try {

			DBConnect dbconn = new DBConnect();
			PreparedStatement preparedStatement = dbconn.getConnection()
					.prepareStatement("INSERT INTO persons (id, salutation, firstname, lastname) VALUES ( ?, ?, ?, ?)");
			p.setId(id);
			preparedStatement.setLong(1, p.getId());
			preparedStatement.setByte(2, p.getSalutation().toByte());
			preparedStatement.setString(3, p.getFirstname());
			preparedStatement.setString(4, p.getLastname());
			preparedStatement.execute();
			preparedStatement.close();
			id++;
		} catch (Exception e) {
			System.out.println("Error: " + e);
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
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public boolean delete(long id) {
		return false;
	}

	public Person get(long id) {
		sql = "SELECT * FROM persons WHERE id = " + id;
		try {
			DBConnect dbconn = new DBConnect();
			ResultSet resultSet = dbconn.getConnection().prepareStatement(sql).executeQuery();
			System.out.println();
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
			System.out.println();
			System.out.println("ID\tSalutation\tFirstname\tLastname");
			while (resultSet.next()) {
				System.out.print(resultSet.getInt(1) + "\t");
				System.out.print(Salutation.fromByte(resultSet.getByte(2)) + "\t\t");
				System.out.print(resultSet.getString(3) + "\t\t");
				System.out.println(resultSet.getString(4) + " ");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
}
