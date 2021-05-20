package de.telekom.sea2;

import de.telekom.sea2.persistence.DBConnect;

class SeminarApp {
	public void run(String[] args) {

		try {
			DBConnect dbconn = new DBConnect();
			dbconn.getConnection();
			System.out.println(dbconn);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("I was in SeminarApp");

	}
}