package de.telekom.sea2.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import de.telekom.sea2.actions.Get;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.GUI;

public class Handler implements ActionListener {
	public PersonsRepository personRepo;
	Vector columnNames = new Vector();

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
//		System.out.println("Handler --> " + personRepo);
	}

	public void actionPerformed(ActionEvent event) {

		String string = "";
		
///////////////////////////////////////////////////

		if (event.getSource() == (GUI.addB)) {
			string = String.format("addB pressed");
			System.out.println(string);
///////////////////////////////////////////////////

		} else if (event.getSource() == GUI.changeB) {
			string = String.format("changeB pressed");
			System.out.println(string);
///////////////////////////////////////////////////
			
		} else if (event.getSource() == GUI.searchB) {
			string = String.format("searchB pressed");
			System.out.println(string);
			try {
				long id = Long.parseLong(GUI.id.getText());
				GUI.label.setText("Searched for ID: " + id);
				GUI.createTable(personRepo.get(id));

			} catch (Exception e) {
				GUI.label.setText("Wrong input - please enter an numeric value");
				e.printStackTrace();
			} 
///////////////////////////////////////////////////
			
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
///////////////////////////////////////////////////
			
		} else if (event.getSource() == GUI.deleteB) {
			string = String.format("deleteB pressed");
			System.out.println(string);
///////////////////////////////////////////////////
			
		} else if (event.getSource() == GUI.quitB) {
			string = String.format("quitB pressed");
			System.out.println(string + " - quitting!");
			System.exit(0);
		}

//		JOptionPane.showMessageDialog(null, string);
	}

}
