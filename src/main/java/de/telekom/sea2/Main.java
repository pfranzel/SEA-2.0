package de.telekom.sea2;

public class Main {

	public static void main(String[] args) {
		System.out.println("Main: Start");
		SeminarApp seminarApp = new SeminarApp();
		seminarApp.run(args);
		
		System.out.println("Main: End");
	}
}