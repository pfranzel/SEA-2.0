package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

public class PersonsRepository {

	private long id = getSize();
//	private DBConnect dbconn;
	private Connection mydbconn;

	public PersonsRepository(Connection mydbconn) {
		this.mydbconn = mydbconn;
	}

	public boolean create(Person p) {

		String sql = "SELECT id FROM persons ORDER BY id DESC LIMIT 1";
		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()){
			resultSet.next();
			System.out.println("lastId: " + resultSet.getInt("id"));
			id = resultSet.getInt("id") + 1;
		} catch (Exception e) {
			System.out.println("It seems you are the first in the list - beginning with ID 0 ");
			id = 0;
		}

		sql = "INSERT INTO persons (id, salutation, firstname, lastname) VALUES ( ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = mydbconn.prepareStatement(sql)){
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
		
		String sql = "UPDATE persons SET salutation=?, firstname=?, lastname=? WHERE id=?";
		try (PreparedStatement preparedStatement = mydbconn.prepareStatement(sql)){
			preparedStatement.setLong(4, p.getId());
			preparedStatement.setByte(1, p.getSalutation().toByte());
			preparedStatement.setString(2, p.getFirstname());
			preparedStatement.setString(3, p.getLastname());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			System.out.println("Delete failed: " + e);
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			}
		
		return false;
	}

	public boolean delete(long id) {
		String sql;
		sql = "DELETE FROM persons WHERE id=?";

		if (get(id) == null) {
			return false;
		}
		try (PreparedStatement delete = mydbconn.prepareStatement(sql)) {
			delete.setLong(1, id);
			delete.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Delete failed: " + e);
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
}
		return false;
	}

	public boolean delete(Person p) {
		long id = p.getId();
		delete(id);
		return false;
	}

	public boolean deleteAll() {
		
		String sql = "TRUNCATE TABLE persons";
		
		try {
			mydbconn.prepareStatement(sql).executeQuery();
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public Person get(long id) {

		String sql = "SELECT * FROM persons WHERE id = " + id;

		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()){
			while (resultSet.next()) {
				Person p = new Person(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
						Salutation.fromByte(resultSet.getByte(2)));
				return p;
			}
		} catch (Exception e) {
			System.out.println("get(long id) Exception: " + e);
		}
		return null;
	}

	public long getSize() {

		String sql = "select count(*) AS total FROM persons";

		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()){
			resultSet.next();
			System.out.println("Size: " + resultSet.getInt("total"));
		} catch (Exception e) {
			System.out.println("getSize() Exception: " + e);
		}
		return 0;
	}

	public ArrayList<Person> getAll() {

		String sql = "select * from persons";
		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()){
			ArrayList<Person> plist = new ArrayList<Person>();
			while (resultSet.next()) {
				plist.add(new Person(resultSet.getLong(1), resultSet.getString(3), resultSet.getString(4),
						Salutation.fromByte(resultSet.getByte(2))));
			}
			return plist;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
