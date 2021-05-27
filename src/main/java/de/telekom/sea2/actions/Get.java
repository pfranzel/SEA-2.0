package de.telekom.sea2.actions;

import java.sql.SQLException;
import java.util.List;

import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

public class Get {
	
	public void getAllPerson(PersonsRepository personRepo) {
		List<Person> personlist = null;
		try {
			personlist = personRepo.getAll();
//			System.out.println("getAllPerson: --> " + personRepo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!personlist.isEmpty()) {
//			System.out.println("###########################################################");
//			System.out.println("#   \tID \tSalu \tFirstname \tLastname");
//			System.out.println("#----------------------------------------------------------");
			for (Person item : personlist) {
//				System.out.println("#\t" + item.getId() + " \t" + item.getSalutation() + "\t" + item.getFirstname()
//						+ "\t\t" + item.getLastname());
			}
		} else {
			System.out.println("The current Table has no entries!");
		}
	}
	
	public Person getById(PersonsRepository personRepo, long id) {
//		String input;
//		System.out.println("Please enter ID to show: ");
//		input = scanner.nextLine();
//		input = "1";
//		int id = Integer.parseInt(input);

		try {
			Person p = personRepo.get(id);
			if (p.getFirstname() != null) {
				System.out.println("###########################################################");
				System.out.println("#   \tID \tSalu \tFirstname \tLastname");
				System.out.println("#----------------------------------------------------------");
				System.out.println("#\t" + p.getId() + " \t" + p.getSalutation() + "\t" + p.getFirstname() + "\t\t"
						+ p.getLastname());
			}
			return p;
		} catch (Exception e) {
			System.out.println("###########################################################");
			System.out.println("#Your requested ID - " + id + " - does not exist! \n# please check!");
			// getById();
		}
		return null;
	}
}
