package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.telekom.sea2.event.Events;
import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

public class PersonsRepository {

	private long id;
	private Connection mydbconn;

// Event-Listener & Events

	public Events event = new Events();

// End Event-Listener & Events
	
	public PersonsRepository(Connection mydbconn) {
		this.mydbconn = mydbconn;
	}

	public boolean create(Person p) throws SQLException {
		
		id = getSize();
		String sql = "SELECT id FROM persons ORDER BY id DESC LIMIT 1";
		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()) {
			resultSet.next();
			System.out.println("lastId: " + resultSet.getInt("id"));
			id = resultSet.getInt("id") + 1;
		} catch (Exception e) {
			System.out.println("It seems you are the first in the list - beginning with ID 0 ");
			id = 0;
			event.sendErrEvent(e);
		}

		sql = "INSERT INTO persons (id, salutation, firstname, lastname) VALUES ( ?, ?, ?, ?)";
		try (PreparedStatement insert = mydbconn.prepareStatement(sql)) {
			p.setId(id);
			insert.setLong(1, p.getId());
			insert.setByte(2, p.getSalutation().toByte());
			insert.setString(3, p.getFirstname());
			insert.setString(4, p.getLastname());
			insert.execute();
			event.sendAddEvent();
			insert.close();
			id++;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			event.sendErrEvent(e);
		}
		return false;
	}

	public boolean update(Person p) throws SQLException {

		String sql = "UPDATE persons SET salutation=?, firstname=?, lastname=? WHERE id=?";
		try (PreparedStatement update = mydbconn.prepareStatement(sql)) {
			update.setLong(4, p.getId());
			update.setByte(1, p.getSalutation().toByte());
			update.setString(2, p.getFirstname());
			update.setString(3, p.getLastname());
			update.execute();
			event.sendUpdateEvent(p.getId());
			update.close();
		} catch (Exception e) {
			System.out.println("Delete failed: " + e);
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			event.sendErrEvent(e);
		}
		return false;
	}

	public boolean delete(long id) throws SQLException {
		String sql;
		sql = "DELETE FROM persons WHERE id=?";

		if (get(id) == null) {
			return false;
		}
		try (PreparedStatement delete = mydbconn.prepareStatement(sql)) {
			delete.setLong(1, id);
			delete.execute();
			event.sendRemoveEvent();
			delete.close();
			return true;
		} catch (Exception e) {
			System.out.println("Delete failed: " + e);
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			event.sendErrEvent(e);
		}
		return false;
	}

	public boolean delete(Person p) throws SQLException {
		long id = p.getId();
		delete(id);
		return false;
	}

	public boolean deleteAll() {

		String sql = "TRUNCATE TABLE persons";

		try {
			mydbconn.prepareStatement(sql).executeQuery();
			event.sendClearEvent();
		} catch (Exception e) {
			System.out.println(e);
			event.sendErrEvent(e);
		}
		return true;
	}

	public Person get(long id) throws SQLException {

		String sql = "SELECT * FROM persons WHERE id = " + id;

		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()) {
			while (resultSet.next()) {
				Person p = new Person(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
						Salutation.fromByte(resultSet.getByte(2)));
				event.sendGetEvent();
				return p;
			}
		} catch (Exception e) {
			System.out.println("get(long id) Exception: " + e);
			event.sendErrEvent(e);
		}
		return null;
	}

	public long getSize() {

		String sql = "SELECT COUNT(*) AS TOTAL FROM persons";

		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()) {
			resultSet.next();
//			System.out.println("Size: " + resultSet.getInt("total"));
			event.sendGetEvent();
			return resultSet.getInt("total");
		} catch (SQLException e) {
			System.out.println("getSize() Exception: " + e);
			event.sendErrEvent(e);
		}
		return 0;
	}

	public ArrayList<Person> getAll() throws SQLException {

		String sql = "SELECT * FROM persons";
		try (ResultSet resultSet = mydbconn.prepareStatement(sql).executeQuery()) {
			ArrayList<Person> plist = new ArrayList<Person>();
			while (resultSet.next()) {
				plist.add(new Person(resultSet.getLong(1), resultSet.getString(3), resultSet.getString(4),
						Salutation.fromByte(resultSet.getByte(2))));
			}
			event.sendGetEvent();
			return plist;
		} catch (Exception e) {
			System.out.println(e);
			event.sendErrEvent(e);
		}
		return null;
	}

}
