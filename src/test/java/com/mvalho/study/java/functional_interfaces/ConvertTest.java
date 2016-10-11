package com.mvalho.study.java.functional_interfaces;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvertTest {

	@Test
	public void testConvert() {
		/**
		 * Convert is an interface that receives two Type Parameters (or Type Variables)
		 * The first is the type we need to do something.
		 * The second is the type we need to return.
		 */
		Convert<String, Integer> converter = (from) -> Integer.valueOf(from);
		assertTrue(converter.convert("123") instanceof Integer);
		assertEquals(converter.convert("123"), new Integer(123));
	}

	@Test
	public void testConvertUsingMethodReference() {
		/**
		 * Here was used the method reference from Java 8.
		 * We do not have to explicit pass the the type we are going to use in this way.
		 * Compiler knows how to interpreter the reference and "inject" the right value for the 
		 * static field valueOf(...).
		 */
		Convert<String, Integer> converter = Integer::valueOf;
		assertTrue(converter.convert("123") instanceof Integer);
		assertEquals(converter.convert("123"), new Integer(123));
	}
}
