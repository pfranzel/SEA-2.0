package de.telekom.sea2;

import java.sql.Connection;

import de.telekom.sea2.persistence.DBConnect;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.Menu;

class SeminarApp {

	private PersonsRepository personRepo;

	public void run(String[] args) {

		System.out.println(this.getClass().getName() + ": Start");

		try (Menu menu = new Menu()) {

			DBConnect dbconn = new DBConnect();
			Connection mydbconn = dbconn.getConnection();
			menu.open();
			personRepo = new PersonsRepository(mydbconn);
			menu.setRepository(personRepo);
			menu.keepAsking();
			dbconn.close();
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}