package com.mvalho.study.java.method_constructor_references;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonFactoryTest {

	private static final String PETER = "Peter";
	private static final String PARKER = "Parker";

	@Test
	public void testCreate() {
		/**
		 * Compiler don't ask for a implementation for create factory method,
		 * as it knows by construct reference that the implementation for <i>create</i> method,
		 * is the same as the Person constructor. 
		 */
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create(PETER, PARKER);
		
		assertNotNull(person);
		assertEquals(PETER, person.firstName);
		assertEquals(PARKER, person.lastName);
	}

}
