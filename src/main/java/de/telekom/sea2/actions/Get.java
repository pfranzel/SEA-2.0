package de.telekom.sea2.actions;

import java.sql.SQLException;
import java.util.ArrayList;

import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

public class Get {
	
	public ArrayList<Person> getAllPerson(PersonsRepository personRepo) {
		
		try {
			ArrayList<Person> plist;
			plist = personRepo.getAll();
			return plist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Person getById(PersonsRepository personRepo, long id) {
//		String input;
//		System.out.println("Please enter ID to show: ");
//		input = scanner.nextLine();
//		input = "1";
//		int id = Integer.parseInt(input);

		try {
			Person p = personRepo.get(id);
/*			if (p.getFirstname() != null) {
				System.out.println("###########################################################");
				System.out.println("#   \tID \tSalu \tFirstname \tLastname");
				System.out.println("#----------------------------------------------------------");
				System.out.println("#\t" + p.getId() + " \t" + p.getSalutation() + "\t" + p.getFirstname() + "\t\t"
						+ p.getLastname());
			} */
			return p;
		} catch (Exception e) {
			System.out.println("###########################################################");
			System.out.println("#Your requested ID - " + id + " - does not exist! \n# please check!");
			// getById();
		}
		return null;
	}
}
