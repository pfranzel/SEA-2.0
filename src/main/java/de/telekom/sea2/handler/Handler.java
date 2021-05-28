package de.telekom.sea2.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.GUI;
import de.telekom.sea2.ui.Menu;

public class Handler implements ActionListener {
	public PersonsRepository personRepo;

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
//		System.out.println("Handler --> " + personRepo);
	}

	public void actionPerformed(ActionEvent event) {

		String string = "";
			/// Add ///
		if (event.getSource() == (GUI.addB)) {
			string = String.format("addB pressed");
			System.out.println(string);
			
			/// CHANGE ///
		} else if (event.getSource() == GUI.changeB) {
			string = String.format("changeB pressed");
			System.out.println(string);

			/// SEARCH by ID ///
		} else if (event.getSource() == GUI.searchB) {
			string = String.format("searchB pressed");
			System.out.println(string);
			try {
				long id = Long.parseLong(GUI.id.getText());
				GUI.label.setText("Searched for ID: " + id);
				GUI.createTable(personRepo.getPerson(id));

			} catch (Exception e) {
				GUI.label.setText("Wrong input - please enter an numeric value");
		//		e.printStackTrace();
				throw new NumberFormatException("Wrong input - please enter an numeric value");
			}
			
			/// LIST ALL ///
		} else if (event.getSource() == GUI.listB) {
			string = String.format("listB pressed");
			System.out.println(string);
			try {
				GUI.createTable(personRepo.getAll());

			} catch (Exception e) {
//				System.out.println("Wrong input - please enter an numeric value");
				GUI.label.setText("Something went wrong - please check");
				e.printStackTrace();
			}
			
			/// Delete by ID ///
		} else if (event.getSource() == GUI.deleteB) {
			string = String.format("deleteB pressed");
			System.out.println(string);
			try {
				long id = Long.parseLong(GUI.id.getText());
				GUI.label.setText("Deleted ID: " + id);
				personRepo.delete(id);
				GUI.createTable(personRepo.getAll());

			} catch (Exception e) {
				GUI.label.setText("Wrong input - please enter an numeric value");
				e.printStackTrace();
			}
			
			/// Delete All ///
		} else if (event.getSource() == GUI.deleteAllB) {
			string = String.format("deleteAllB pressed");
			System.out.println(string);
			try {
				personRepo.deleteAll();
				GUI.label.setText("Table persons truncated");
			} catch (Exception e) {
				GUI.label.setText("Something went wrong");
				e.printStackTrace();
			}
			
			
			
			if (personRepo.deleteAll()) {
				System.out.println("Table truncated - it is now empty");
			} else {
				System.out.println("Something went wrong, please check if table exist");
			}
			
			/// LOAD/GERNATE TEST DATA ///
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
			GUI.label.setText("Testdata Loaded");
			
			/// QUIT ///
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
