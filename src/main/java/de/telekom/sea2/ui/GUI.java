package de.telekom.sea2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import de.telekom.sea2.handler.Handler;
import de.telekom.sea2.persistence.PersonsRepository;

public class GUI extends JFrame implements Closeable, ActionListener {

	public PersonsRepository personRepo;
	public Handler handler;
	private static JLabel label;
	private static JFrame frame;
	private static JPanel panel1;
	private static JPanel panel2;
	private static JPanel panel3;
	public static JButton addB;
	public static JButton changeB;
	public static JButton searchB;
	public static JButton listB;
	public static JButton deleteB;
	public static JButton quitB;
	private boolean addActive = true;

	public void setRepository(PersonsRepository repo) {
		personRepo = repo;
//		System.out.println("GUI --> " + personRepo);
		handler.setRepository(personRepo);
	}

	public GUI() {
		super("MY First Gui / Seminar-Administration");
		setLayout(new FlowLayout());
		setVisible(true);

		frame = new JFrame();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		addB = new JButton("Add Person");
		changeB = new JButton("Change Person");
		searchB = new JButton("Search Person");
		listB = new JButton("List all Person");
		deleteB = new JButton("Delete Person");
		quitB = new JButton("Quit");

		String[] columnNames = { "ID", "Salutation", "Firstname", "Lastname" };

		Object[][] data = { { "1", "Mrs.", "Kathy", "Smith" }, { "2", "Mr.", "Hello", "World" },
				{ "3", "Mrs.", "Sarah", "Loose" }, { "4", "Mrs.", "Sarah", "Loose" }, { "5", "Mrs.", "Sarah", "Loose" },
				{ "6", "Mr.", "Lasse", "Kent" }, };
		
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setLayout(new BorderLayout());
		table.add(table.getTableHeader(), BorderLayout.PAGE_START);
		table.setCellSelectionEnabled(false);
//		table.add(table, BorderLayout.CENTER);
//		table.setVisible(true);

//		label = new JLabel("Number of clicks: 0");
//		panel2.add(label);

		panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel1.setLayout(new GridLayout(0, 1));
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

		// table.setBounds(300, 20, 170, 25);
		panel3.add(table);
		panel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JTextField userText = new JTextField(20);
		userText.setBounds(120, 20, 20, 20);
		userText.setName("ID");
		panel2.add(userText);

		frame.setSize(1350, 500);
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel3, BorderLayout.EAST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		pack();
		frame.setVisible(true);

		handler = new Handler();
//		handler.setRepository(personRepo);

		addB.addActionListener(handler);
		changeB.addActionListener(handler);
		searchB.addActionListener(handler);
		listB.addActionListener(handler);
		deleteB.addActionListener(handler);
		quitB.addActionListener(handler);

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
