package de.telekom.sea2.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import de.telekom.sea2.handler.Handler;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

public class GUI extends JFrame implements Closeable, ActionListener {

	public PersonsRepository personRepo;
	public Handler handler;
	public static JTextField id;
	public static String message;
	public static JLabel label;
	private static JFrame frame;
	private static JPanel panel1;
	private static JPanel panel2;
	private static JPanel panel3;
	private static JPanel panel4;
	public static JButton addB;
	public static JButton changeB;
	public static JButton searchB;
	public static JButton listB;
	public static JButton deleteB;
	public static JButton quitB;
	public static JTable table;
	private boolean addActive = true;
//	public static Object[][] tabledata = { { "0", "Dummy0", "Dummy0", "Dummy0" }, { "1", "Dummy1", "Dummy1", "Dummy1" }, { "2", "Dummy2", "Dummy2", "Dummy2" } };
	public static Object[][] tabledata;
	
	
	private static String[] columnNames = { "ID", "Salutation", "Firstname", "Lastname" };
	
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
		quitB = new JButton("Quit");

//		String[] columnNames = { "ID", "Salutation", "Firstname", "Lastname" };



		label = new JLabel("Total Number persons: " + personRepo.getSize());
		panel2.add(label);

		panel1.setLayout(new GridLayout(0, 2));
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
		panel1.add(quitB);

		id = new JTextField(20);
		id.setBounds(120, 20, 20, 20);
		id.setName("ID");
		panel2.add(id);

		label = new JLabel("Message: " + message);
		panel4.add(label);

		panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel4.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		frame.setSize(1000, 500);
		frame.setTitle(getTitle());
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
//		pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		handler = new Handler();
		handler.setRepository(personRepo);

		addB.addActionListener(handler);
		changeB.addActionListener(handler);
		searchB.addActionListener(handler);
		listB.addActionListener(handler);
		deleteB.addActionListener(handler);
		quitB.addActionListener(handler);
		id.addActionListener(handler);

	}

	
	public static void createTable(Person p) throws ClassNotFoundException, SQLException {

		Object [][] tabledata = { { "0", "Dummy0", "Dummy0", "Dummy0" },{p.getId(), p.getSalutation(), p.getFirstname(), p.getLastname() }};
		
		if (table == null) {
				table = new JTable(tabledata, columnNames);
		} else {
			recreateTable(p);
		}

		table.setFillsViewportHeight(true);
		table.setLayout(new BorderLayout());
		table.add(table.getTableHeader(), BorderLayout.PAGE_START);
		table.setCellSelectionEnabled(false);
//		table.add(table, BorderLayout.CENTER);
//		table.setVisible(true);
//      table.setBounds(300, 20, 170, 25);
		panel3.add(table);

	}
	public static void recreateTable(Person p) throws ClassNotFoundException, SQLException {
//		DefaultTableModel model = (DefaultTableModel) table.getModel();
//		model.setRowCount(0);
		panel3.remove(table);
		table = null;
		Object [][] tabledata = { { "0", "Dummy0", "Dummy0", "Dummy0" },{p.getId(), p.getSalutation(), p.getFirstname(), p.getLastname() }};
		table = new JTable(tabledata, columnNames);
		
		
		
		table.setFillsViewportHeight(true);
		table.setLayout(new BorderLayout());
		table.add(table.getTableHeader(), BorderLayout.PAGE_START);
		panel3.add(table);
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
