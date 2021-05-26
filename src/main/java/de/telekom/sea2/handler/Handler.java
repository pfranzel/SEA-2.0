package de.telekom.sea2.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.telekom.sea2.actions.Get;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.GUI;

public class Handler implements ActionListener {
	public PersonsRepository personRepo;

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
//		System.out.println("Handler --> " + personRepo);
	}

	public void actionPerformed(ActionEvent event) {

		String string = "";
		
		if (event.getSource() == (GUI.addB)) {
			string = String.format("addB was pressed");
			System.out.println(string);

		} else if (event.getSource() == GUI.changeB) {
			string = String.format("changeB was pressed");
			System.out.println(string);

		} else if (event.getSource() == GUI.searchB) {
			string = String.format("searchB was pressed");
			System.out.println(string);

		} else if (event.getSource() == GUI.listB) {
			string = String.format("listB was pressed");
			System.out.println(string);
			Get getall = new Get();
			getall.getAllPerson(personRepo);

		} else if (event.getSource() == GUI.deleteB) {
			string = String.format("deleteB was pressed");
			System.out.println(string);

		} else if (event.getSource() == GUI.quitB) {
			string = String.format("quitB was pressed");
			System.out.println(string + " - quitting!");
			System.exit(0);
		}
//		JOptionPane.showMessageDialog(null, string);
	}

}
