package de.telekom.sea2.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

public class PersonsRepository {

	private String sql;
	private int id;
	private DBConnect dbconn;

//	 DBConnect dbconn = new DBConnect();

	public PersonsRepository(DBConnect conn) {
		this.dbconn = conn;
	}

	public boolean create(Person p) {

		try {

			ResultSet resultSet = dbconn.getConnection()
					.prepareStatement("SELECT id FROM persons ORDER BY id DESC LIMIT 1").executeQuery();
			resultSet.next();
			System.out.println("lastId: " + resultSet.getInt("id"));
		} catch (Exception e) {
			System.out.println("It seams you are the first in the list - beginning with ID 0 ");
		}

		try {

//			DBConnect dbconn = new DBConnect();

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

	public boolean delete(long id) {
		sql = "DELETE FROM persons WHERE id=?";

		try {
			System.out.println("IDDDDDD:" + get(id));
//			if (get(id) == null) {
//				System.out.println("ID " + id + " does not exist");
//			} else {
			PreparedStatement delete = dbconn.getConnection().prepareStatement(sql);
			delete.setLong(1, id);
			delete.execute();
			return true;
//			}
		} catch (Exception e) {
			System.out.println("Delete failed: " + e);
		}
		return false;
	}

	public boolean delete(Person p) {
		long id = p.getId();
		delete(id);
		return false;
	}

	public boolean deleteAll() {
		sql = "TRUNCATE TABLE persons";
		try {
			dbconn.getConnection().prepareStatement(sql).executeQuery();
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public Person get(long id) {

		sql = "SELECT * FROM persons WHERE id = " + id;

		try {
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
			ResultSet resultSet = dbconn.getConnection().prepareStatement(sql).executeQuery();
			ArrayList<Person> personlist = new ArrayList<Person>();
			while (resultSet.next()) {
				personlist.add(new Person(
						resultSet.getLong(1), 
						resultSet.getString(3), 
						resultSet.getString(4),
						Salutation.fromByte(resultSet.getByte(2))));
			}
			dbconn.close();
			return personlist;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
