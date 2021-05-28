package de.telekom.sea2.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import de.telekom.sea2.eventHandler.EventHandler;
import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

public class GUI extends JFrame implements Closeable, ActionListener {

	
	
	public PersonsRepository personRepo;
	public EventHandler eventHandler;
	public static JTextField id;
	public static String message;
	public static JLabel messageL;
	public static JButton addB;
	public static JButton changeB;
	public static JButton searchB;
	public static JButton listB;
	public static JButton deleteB;
	public static JButton deleteAllB;
	public static JButton genTestB;
	public static JButton quitB;
	
	private static JFrame frame;
	private static JPanel panel1;
	private static JPanel panel2;
	private static JPanel panel3;
	private static JPanel panel4;
	private static String[] columnNames = { "ID", "Salutation", "Firstname", "Lastname" };
	static DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	
	private static JTable table1 = new JTable(model);
    
	private boolean addActive = true;

	public GUI(PersonsRepository personRepo) throws Exception {
		super("MY First Gui / Seminar-Administration");

		frame = new JFrame();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();

		addB = new JButton("Add Person");
		changeB = new JButton("Change Person");
		searchB = new JButton("Search Person");
		listB = new JButton("List all Person");
		deleteB = new JButton("Delete Person");
		deleteAllB = new JButton("Delete All");
		genTestB = new JButton("Generate Testdata");
		quitB = new JButton("Quit");

		messageL = new JLabel("Total Number persons: " + personRepo.getSize());
		panel2.add(messageL);

		panel1.setLayout(new GridLayout(0, 1));
		panel2.setLayout(new GridLayout(0, 1));
//		panel1.setSize(200, 200);
		panel1.add(addB);
		if (addActive == true)
			addB.setVisible(true);
		else
			addB.setVisible(false);
		panel1.add(changeB);
		panel1.add(searchB);
		panel1.add(listB);
		panel1.add(deleteB);
		panel1.add(deleteAllB);
		panel1.add(genTestB);
		panel1.add(quitB);

		id = new JTextField(20);
		id.setBounds(120, 20, 20, 20);
		id.setName("ID");
		
		id.setToolTipText("Enter the ID here");
		panel2.add(id);

		messageL = new JLabel("Message: " + message);
		panel4.add(messageL);

		panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel4.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		frame.setSize(1000, 500);
		frame.setTitle(getTitle());
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel4, BorderLayout.SOUTH);
//		pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		model.setRowCount(0);
		table1.setModel(model);
		model.addRow(columnNames);
		
////////////
	
		JScrollPane scrollPane = new JScrollPane(panel3);
	//	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	//	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.add(table1, BorderLayout.EAST);
		frame.add(scrollPane, BorderLayout.EAST);
		panel3.setName("myname");
		panel3.add(table1);
	
///////////
		
		table1.setFillsViewportHeight(true);
		table1.setLayout(new BorderLayout());
		table1.add(table1.getTableHeader(), BorderLayout.PAGE_START);
		table1.setCellSelectionEnabled(false);
		
///////////
		
		eventHandler = new EventHandler();
		eventHandler.setRepository(personRepo);

		addB.addActionListener(eventHandler);
		changeB.addActionListener(eventHandler);
		searchB.addActionListener(eventHandler);
		listB.addActionListener(eventHandler);
		deleteB.addActionListener(eventHandler);
		deleteAllB.addActionListener(eventHandler);
		quitB.addActionListener(eventHandler);
		genTestB.addActionListener(eventHandler);
		id.addActionListener(eventHandler);		
	}

	public static void createTable(Person p) throws ClassNotFoundException, SQLException {

		model.setRowCount(1);

		if (p == null) {
		} else if (p != null) {
			Object[] obj = { p.getId(), p.getSalutation(), p.getFirstname(), p.getLastname() };
			model.addRow(obj);
		}
	}

	public static void createTable(ArrayList<Person> plist) throws ClassNotFoundException, SQLException {

		model.setRowCount(1);

		if (plist == null) {
			table1.setModel(model);
			model.addRow(columnNames);
		} else if (plist != null) {
			for (Person pItem : plist) {
				long id = pItem.getId();
				Salutation salutation = pItem.getSalutation();
				String firstname = pItem.getFirstname();
				String lastname = pItem.getLastname();
				Object[] data = { id, salutation, firstname, lastname };
				model.addRow(data);
			}
		}
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
