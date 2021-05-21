package de.telekom.sea2.ui;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
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
		System.out.println("***************** Menu **************** ");
		System.out.println("*                                     * ");
		if (addActive == true)
			System.out.println("* (1) Add person                      *");
		System.out.println("* (2) Search person by ID             *");
		System.out.println("* (3) List all persons                *");
		System.out.println("* (4) Delete by ID                    *");
		System.out.println("* (5) Delete all (truncate table)     *");
		System.out.println("* (6) Update person by ID             *");
		System.out.println("*                                     *");
		System.out.println("* (9) Generate Testdata               *");
		System.out.println("*                                     *");
		System.out.println("* (q) Quit                            *");
		System.out.println("*                                     *");
		System.out.println("***************************************");
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
			getById();
			break;
		case "3":
			getAllPerson();
			break;
		case "4":
			removePerson();
			break;
		case "5":
			removeAll();
			break;
		case "6":
			updatePerson();
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

	private void updatePerson() {
		String firstname;
		String lastname;
		Salutation salutation = null;
		Person p = null;
		long id ;

		System.out.println();
		
		try {
			System.out.println("Which Person/ID you want to update?");
			System.out.println("Please enter ID: ");
	//		long id = scanner.nextLong();    // shit - newline keeps in the buffer and next nextLine failes...
			id = Long.parseLong(scanner.nextLine());
			 p = personRepo.get(id);
			if (p.getFirstname() != null) {
				System.out.println("###########################################################");
				System.out.println("# You selected:                                           #");
				System.out.println("#                                                         #");
				System.out.println("#   \tID \tSalu \tFirstname \tLastname");
				System.out.println("#----------------------------------------------------------");
				System.out.println("#\t" + p.getId() + " \t" + p.getSalutation() + "\t" + p.getFirstname()
				+ "\t\t" + p.getLastname());
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Format not valid - please use numeric format");
			e.printStackTrace();
		} 

		try {
			System.out.println("Please enter new salutation (m/f/d): ");
			salutation = Salutation.fromString(scanner.nextLine());
			System.out.println("Please enter the firstname: ");
				firstname = scanner.nextLine();
			System.out.println("Please enter the lastame: ");
				lastname = scanner.nextLine();
			if (salutation.toString().isEmpty()) {
				throw new NullPointerException("Salutation cannot be blank.");
			} else if (firstname.isEmpty()) {
				throw new NullPointerException("Firstname cannot be blank.");
			} else if (lastname.isEmpty()) {
				throw new NullPointerException("Lastname cannot be blank.");
			} else {
				p.setFirstname(firstname);
				p.setLastname(lastname);
				p.setSalutation(salutation);			
				personRepo.update(p);
				System.out.println("You added \"" + salutation + " " + firstname + " " + lastname + "\" to the list");
			}
		} catch (Exception npe) {
			System.out.println("Something went wrong - " + npe.getMessage());
			npe.printStackTrace();
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
		long id = scanner.nextLong();

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
		if (personRepo.deleteAll()) {
			System.out.println("Table truncated - it is now empty");
		} else {
			System.out.println("Something went wrong, please check if table exist");
		}
	}

	private void getAllPerson() {
		List<Person> personlist = personRepo.getAll();
		if (!personlist.isEmpty()) {
			System.out.println("###########################################################");
			System.out.println("#   \tID \tSalu \tFirstname \tLastname");
			System.out.println("#----------------------------------------------------------");
			for (Person item : personlist) {
				System.out.println("#\t" + item.getId() + " \t" + item.getSalutation() + "\t" + item.getFirstname()
						+ "\t\t" + item.getLastname());
			}
		} else {
			System.out.println("The current has no entries!");
		}
	}

	private boolean getById() {
		// private - Scanner for new person
		String input;
		System.out.println("Please enter ID to show: ");
		input = scanner.nextLine();
		int id = Integer.parseInt(input);

		try {
			Person p = personRepo.get(id);
			if (p.getFirstname() != null) {
				System.out.println("###########################################################");
				System.out.println("#   \tID \tSalu \tFirstname \tLastname");
				System.out.println("#----------------------------------------------------------");
				System.out.println("#\t" + p.getId() + " \t" + p.getSalutation() + "\t" + p.getFirstname()
				+ "\t\t" + p.getLastname());
			}
			return true;
		} catch (Exception e) {
			System.out.println("###########################################################");
			System.out.println("#Your requested ID - " + id + " - does not exist! \n# please check!");
			// getById();
		}
		return false;
	}

	private void genTestData() {
		inputTestdata("Rainer", "Deißler", Salutation.MR);
		inputTestdata("Peter", "Lustig", Salutation.MR);
		inputTestdata("Agata", "Rubin", Salutation.MRS);
		inputTestdata("Peter", "Franzel", Salutation.MR);
		inputTestdata("Magda", "Franzel", Salutation.MRS);
		inputTestdata("Hans", "Wurst", Salutation.MR);
		inputTestdata("Dagobert", "Duck", Salutation.OTHER);
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

//	public void setRepo(PersonsRepository repo) {
//		this.repo = repo; // public - gibt die Administration-DB dem Menü bekannt
//	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}
}
