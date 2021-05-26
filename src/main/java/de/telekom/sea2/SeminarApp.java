package de.telekom.sea2;

import java.sql.Connection;

import de.telekom.sea2.persistence.DBConnect;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.GUI;

class SeminarApp {

	protected PersonsRepository personRepo;

	public void run(String[] args) {

		System.out.println(this.getClass().getName() + ": Start");

		try (GUI gui = new GUI()) {
			DBConnect dbconn = new DBConnect();
			Connection mydbconn = dbconn.getConnection();
			personRepo = new PersonsRepository(mydbconn);
			gui.setRepository(personRepo);
			
			// dbconn.close();     // needs to be implemented... 
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public PersonsRepository getPersonRepo() {
		return personRepo;
	}
}