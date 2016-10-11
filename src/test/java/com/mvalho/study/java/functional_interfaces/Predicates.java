package com.mvalho.study.java.functional_interfaces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.mvalho.study.java.method_constructor_references.Person;
import com.mvalho.study.java.method_constructor_references.PersonFactory;

/**
 * Predicates are functional interfaces for boolean operations,
 * when you create a Predicate you must pass as it constructor a check operator.
 * 
 * @author marceloca
 */
public class Predicates {
	
	@Test
	public void testPredicateForStringsValue() {
		Predicate<String> lenghtGreaterThan0 = (string) -> string.length() > 0;
		
		assertTrue(lenghtGreaterThan0.test("foo"));
		assertFalse(lenghtGreaterThan0.negate().test("foo"));
		
		Predicate<String> isNullOrEmpty = (string) -> string == null || string.isEmpty();
		String empty = "";
		String stringNull = null;
		String blank = "     ";
		String filled = "Sting";
		
		assertTrue(isNullOrEmpty.test(empty));
		assertTrue(isNullOrEmpty.test(stringNull));
		assertFalse(isNullOrEmpty.test(blank));
		assertFalse(isNullOrEmpty.test(filled));
	}
	
	@Test
	public void testPredicateForPerson() {
		Predicate<Person> personLastNameStartsWith = person -> person.getLastName().startsWith("C");
		
		PersonFactory<Person> personFactory = Person::new;
		Person foo = personFactory.create("foo", "boo");
		Person cee = personFactory.create("cee", "taa");
		Person mar = personFactory.create("mar", "Carv");
		Person viv = personFactory.create("viv", "carv");
		
		assertFalse(personLastNameStartsWith.test(foo));
		assertFalse(personLastNameStartsWith.test(cee));
		assertTrue(personLastNameStartsWith.test(mar));
		assertFalse(personLastNameStartsWith.test(viv));
	}
	
	@Test
	public void testPredicateForPerson_isFilled() {
		Predicate<Person> isFilled = person -> person != null && !StringUtils.isBlank(person.getFirstName()) && !StringUtils.isBlank(person.getLastName());
		
		PersonFactory<Person> personFactory = Person::new;
		Person foo = personFactory.create("foo", "boo");
		Person cee = personFactory.create("cee", "");
		Person mar = personFactory.create("", "Carv");
		Person viv = personFactory.create("", "");
		Person nullPerson = null;
		
		assertTrue(isFilled.test(foo));
		assertFalse(isFilled.test(cee));
		assertFalse(isFilled.test(mar));
		assertFalse(isFilled.test(viv));
		assertFalse(isFilled.test(nullPerson));
	}
}
