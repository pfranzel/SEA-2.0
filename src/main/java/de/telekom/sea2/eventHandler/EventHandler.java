package de.telekom.sea2.eventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.GUI;

public class EventHandler implements ActionListener {
	public PersonsRepository personRepo;

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
//		System.out.println("Handler --> " + personRepo);
	}

	public void actionPerformed(ActionEvent event) {

		String string = "";
////////////////// Add /////////////////
		if (event.getSource() == (GUI.addB)) {
			string = String.format("addB pressed");
			System.out.println(string);

////////////////// CHANGE /////////////////
		} else if (event.getSource() == GUI.changeB) {
			string = String.format("changeB pressed");
			System.out.println(string);

////////////////// SEARCH by ID /////////////////
		} else if (event.getSource() == GUI.searchB) {
			string = String.format("searchB pressed");
			System.out.println(string);
			long id = -1;
			try {
				id = Long.parseLong(GUI.id.getText());
				GUI.messageL.setText("Searched for ID: " + id);
				if (personRepo.getPerson(id).getId() == id) {
					GUI.createTable(personRepo.getPerson(id));
				} else {
					throw new NullPointerException("Person with ID: " + id + " does not exit in the list");
				}
			} catch (NullPointerException e) {
				GUI.messageL.setText("Person with ID: " + id + " is not existing in the list");
			} catch (Exception e) {
				GUI.messageL.setText("Wrong input - please enter an numeric value");
				JOptionPane.showMessageDialog(null, "Wrong input - please enter an numeric value");
				// e.printStackTrace();
				throw new NumberFormatException("Wrong input - please enter an numeric value");
			}
////////////////// LIST ALL /////////////////
		} else if (event.getSource() == GUI.listB) {
			string = String.format("listB pressed");
			System.out.println(string);
			try {
				GUI.createTable(personRepo.getAll());
				GUI.messageL.setText("GetAll successful");
			} catch (Exception e) {
				GUI.messageL.setText("Something went wrong - please check");
				e.printStackTrace();
			}

////////////////// Delete by ID /////////////////
		} else if (event.getSource() == GUI.deleteB) {
			string = String.format("deleteB pressed");
			System.out.println(string);
			long id = -1;
			try {
				id = Long.parseLong(GUI.id.getText());
				if (personRepo.getPerson(id).getId() == id) {
				personRepo.delete(id);
				GUI.messageL.setText("Deleted ID: " + id);
				GUI.createTable(personRepo.getAll());
				} else {
					throw new NullPointerException("Person with ID: " + id + " not deleted as not existing in the list");
				}
			} catch (NullPointerException e) {
				GUI.messageL.setText("Person with ID: " + id + " not deleted as not existing in the list");
			} catch (Exception e) {
				GUI.messageL.setText("Wrong input - please enter an numeric value");
				e.printStackTrace();
			}

////////////////// Delete All /////////////////
		} else if (event.getSource() == GUI.deleteAllB) {
			string = String.format("deleteAllB pressed");
			System.out.println(string);
			try {
				personRepo.deleteAll();
				GUI.messageL.setText("Table persons truncated");
			} catch (Exception e) {
				GUI.messageL.setText("Something went wrong");
				e.printStackTrace();
			}
			GUI.listB.doClick();

////////////////// LOAD/GERNATE TEST DATA /////////////////
		} else if (event.getSource() == GUI.genTestB) {
			string = String.format("genTestB pressed");
			System.out.println(string);
			inputTestdata("Rainer", "Deißler", Salutation.MR);
			inputTestdata("Peter", "Lustig", Salutation.MR);
			inputTestdata("Agata", "Rubin", Salutation.MRS);
			inputTestdata("Peter", "Franzel", Salutation.MR);
			inputTestdata("Magda", "Franzel", Salutation.MRS);
			inputTestdata("Hans", "Wurst", Salutation.MR);
			inputTestdata("Donald", "Duck", Salutation.OTHER);
			GUI.messageL.setText("Testdata Loaded");
			GUI.listB.doClick();

////////////////// QUIT /////////////////
		} else if (event.getSource() == GUI.quitB) {
			string = String.format("quitB pressed");
			System.out.println(string + " - quitting!");
			System.exit(0);
		}
//		JOptionPane.showMessageDialog(null, string);
	}

	private void inputTestdata(String firstname, String lastname, Salutation salutation) {
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
//			inputPerson();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
