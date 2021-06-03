package de.telekom.sea2.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

	private Person cut;

	@BeforeEach
	void setup() {
		cut = new Person();
	}

	@Test
	void setFirstname_test() throws Exception {

		// Arrange
		cut.setFirstname("TestFirstname");
		
		// Act
		var result = cut.getFirstname();
		
		//Assert
		assertEquals("TestFirstname", result);	
		assertSame("TestFirstname", result);
	}
	
/*	@Test
	void setFirstname_null_test() throws Exception {

//		fail();
//		Assertions.fail();

		// Arrange
		cut.setFirstname(null);
		
		// Act
		var result = cut.getFirstname();
		
		//Assert
//		assert;	

	} */
	@AfterEach
	void teardown() {
		cut = null;
	}
}
