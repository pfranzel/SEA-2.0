package de.telekom.sea2.ui;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

public class Menu implements Closeable {

	private PersonsRepository personRepo;
	private String result;
	private Scanner scanner;
	private boolean addActive = true;

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
	}
	
	public void open() {
		scanner = new Scanner(System.in);
	}
	
	private String inputMenu() {
		return scanner.nextLine();
		// private - Scanner for User Input
	}
	
	public void keepAsking() {
		// public - hällt die Schleife bis zum Abbruch am Leben
		do {
			showMenu();
			result = inputMenu();
			checkMenu(result);
		} while (!result.equals("q"));
	}
	
	private void showMenu() {
		// private - Show choices and exit/continue
		System.out.println();
		System.out.println("************ Menu ************* ");
		System.out.println("*                             * ");
		if (addActive == true)
			System.out.println("* (1) Add Person              *");
		System.out.println("* (2) Delete by ID            *");
		System.out.println("* (3) List Persons            *");
		System.out.println("* (4) Truncate Table (delAll) *");
		System.out.println("* (5) Search by ID            *");
		System.out.println("*                             *");
		System.out.println("* (9) Generate Testdata       *");
		System.out.println("*                             *");
		System.out.println("* (q) Quit                    *");
		System.out.println("*                             *");
		System.out.println("*******************************");
//		keepAsking(result);
	}

	private void checkMenu(String input) {
		// private - case evaluation
		switch (input) {
		case "1":
			if (addActive == false) {
				System.out.println("Error: Wrong input - please repeat");
				break;
			}
			inputPerson();
			break;
		case "2":
			removePerson();
			break;
		case "3":
			getAllPerson();
			break;
		case "4":
			removeAll();
			break;
		case "5":
			getById();
			break;
		case "9":
			genTestData();
			break;
		case "q":
			break;
		default:
			System.out.println("\n U_ERROR: Wrong input - please repeat \n");
		}
	}

	private void genTestData() {
		inputTestdata("Rainer", "Deißler", Salutation.MR);
		inputTestdata("Peter", "Lustig", Salutation.MR);
		inputTestdata("Agata", "Rubin", Salutation.MRS);
		inputTestdata("Peter", "Franzel", Salutation.MR);
	}
	
	private void inputTestdata(String firstname, String lastname, Salutation salutation) {
		// private - Scanner for new person

		System.out.println();

		try {
			if (firstname.isEmpty()) {
				throw new NullPointerException("Firstname cannot be blank.");
			} else if (lastname.isEmpty()) {
				throw new NullPointerException("Lastname cannot be blank.");
			} else {
				Person p = new Person(firstname, lastname, salutation);
				personRepo.create(p);
				System.out.println("You added \"" + salutation + " " + firstname + " " + lastname + "\" to the list");
			}
		} catch (NullPointerException re) {
			System.out.println("Something went wrong - " + re.getMessage());
			inputPerson();
		}
	}
	
	private void inputPerson() {
		// private - Scanner for new person
		String firstname;
		String lastname;
		Salutation salutation = null;

		System.out.println();
		System.out.println("Add a new person:");
		System.out.println("Please enter the form (m/f/d): ");
		try {
			salutation = Salutation.fromString(scanner.nextLine());
		} catch (IllegalArgumentException e) {
			System.out.println("Form not valid - please use format as <f/m/d>");
//			inputPerson();
		}
		System.out.println("Please enter the firstname: ");
		firstname = scanner.nextLine();
		System.out.println("Please enter the lastame: ");
		lastname = scanner.nextLine();

		try {
			if (firstname.isEmpty()) {
				throw new NullPointerException("Firstname cannot be blank.");
			} else if (lastname.isEmpty()) {
				throw new NullPointerException("Lastname cannot be blank.");
			} else {
				System.out.println("I was Heeeeeeeerrrrrreeeee!!!!!");
				Person p = new Person(firstname, lastname, salutation);
				personRepo.create(p);
				System.out.println("You added \"" + salutation + " " + firstname + " " + lastname + "\" to the list");
			}
		} catch (NullPointerException npe) {
			System.out.println("Something went wrong - " + npe.getMessage());
			inputPerson();
		}
	}
	
	private void removePerson() {
		System.out.println();
		getAllPerson();
		System.out.println();
		System.out.println("Please enter ID to delete: ");
		int id = scanner.nextInt();
		boolean success = personRepo.delete(id);
		if (success) {
			System.out.println();
			System.out.println("--> ID: \"" + id + "\" deleted successful!");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("Deletion of ID: " + id + " not successful");
		}
		scanner.nextLine();
	}
	
	private void removeAll() {
		// private -list all configured/read persons
		personRepo.deleteAll();
	}

	private void getAllPerson() {
/*		// private -list all configured/read persons
		if (personRepo.size() == 0) {
			System.out.println("You have an empty list!");
		} else {
			int i = 0;
			int j = 0;
			while (i < personRepo.size()) {
				if (personRepo.get(j) != null) {
					Person p = (Person) personRepo.get(j);
					System.out.println("---> ID: " + p.getId() + " - Element: " + j + ".) " + p.getSalutation() + " - "
							+ p.getFirstname() + " " + p.getLastname());
				} else {
					i--;
				}
				i++;
				j++;
			}
		}*/
		personRepo.getAll();
	} 
	
	
	private void getById() {
		// private - Scanner for new person
		String input;
		System.out.println("Please enter ID to show: ");
		input = scanner.nextLine();
		int id = Integer.parseInt(input);

		try {
			Person person = (Person) personRepo.get(id);
			System.out.println("You selected - " + id + ": " + person.getFirstname() + " " + person.getLastname());
		} catch (Exception e) {
			System.out.println("Your requested id does not exist - please check!");
			getById();
		} /*
			 * finally { System.out.println("Finally..."); }
			 */
	}



//	public void setRepo(PersonsRepository repo) {
//		this.repo = repo; // public - gibt die Administration-DB dem Menü bekannt
//	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
