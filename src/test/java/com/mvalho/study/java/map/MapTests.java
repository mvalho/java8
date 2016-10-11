package com.mvalho.study.java.map;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class MapTests {
	Map<Integer, String> map;
	
	@Before
	public void setUp() {
		map = new HashMap<>();
		IntStream.range(0, 10).forEach(i -> map.putIfAbsent(i, "val" + i));
	}
	
	@Test
	public void usingMapForEachWithConsumer() {
		BiConsumer<Integer, String> action = (id, val) -> System.out.println(val);
		map.forEach(action);
	}
	
	@Test
	public void usingMapComputeIfPresentIfAbsent() {
		/**
		 * Get the value for position 3 and add a new value that is the concatenation of val+num index;
		 */
		map.computeIfPresent(3, (num, val) -> val+num);
		assertEquals("val33", map.get(3));
		
		/**
		 * Get the value for position 9 and set null to its value, as computeIfPresent only set new values 
		 * if it is not null, the position 9 is removed.
		 */
		map.computeIfPresent(9, (num, val) -> null);
		/**
		 * position 9 was removed
		 */
		assertFalse(map.containsKey(9));
		
		/**
		 * Try to get (...that great big hill of hope... :P) the value in the position 23, and if is missing  
		 * add a new one if it is not null.
		 */
		map.computeIfAbsent(23, num -> "val" + num);
		/**
		 * position 23 was added.
		 */
		assertTrue(map.containsKey(23));
		
		/**
		 * Get the position 3 and try to add a new value.
		 */
		map.computeIfAbsent(3, num -> "bam");
		/**
		 * It not works because position 3 already exists
		 */
		assertNotEquals("bam", map.get(3));
	}
	
	@Test
	public void usingMapRemove() {
		map.computeIfPresent(3, (num, val) -> val+num);
		
		/**
		 * Try to remove key at position 3 with value "val3"
		 */
		map.remove(3, "val3");
		
		/**
		 * Fail to remove...
		 */
		assertNotNull(map.get(3));
		/**
		 * ...cause the string at the position 3 is actually "val33".
		 */
		assertNotEquals("val3", map.get(3));
		
		/**
		 * Now passing the correct key and value...
		 */
		map.remove(3, "val33");
		/**
		 * ...the string at position 3 is removed.
		 */
		assertNull(map.get(3));
	}
	
	@Test
	public void usingMapToGetOrDefault() {
		/**
		 * If the index key is not present in the map, return a predefined string.
		 */
		assertEquals("not found", map.getOrDefault(42, "not found"));
		assertNotEquals("not found", map.getOrDefault(3, "not found"));
		assertEquals("val3", map.getOrDefault(3, "not found"));
	}
	
	@Test
	public void usingMapToMerge() {
		map.computeIfPresent(9, (num, val) -> null);
		/**
		 * In the above line, index 9 was removed, now passing 9 as the key
		 * and knowing that this position was removed the merge method
		 * adds the string value to be merged. In the line bellow "val9" 
		 * is the new value.
		 */
		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		assertEquals("val9", map.get(9));
		
		/**
		 * Now, the merge from "val9" inserted before with the new value "concat" was done. 
		 */
		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		assertEquals("val9concat", map.get(9));		
	}
}
