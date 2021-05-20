package de.telekom.sea2;

import de.telekom.sea2.persistence.DBConnect;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.Menu;

class SeminarApp {

//	private DBConnect dbconn;
	private PersonsRepository personRepo;

	public void run(String[] args) {

		System.out.println(this.getClass().getName() + ": Start");

		try (Menu menu = new Menu()) {

			DBConnect dbconn = new DBConnect();
			menu.open();
			personRepo = new PersonsRepository(dbconn);
			menu.setRepository(personRepo);
			menu.keepAsking();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}