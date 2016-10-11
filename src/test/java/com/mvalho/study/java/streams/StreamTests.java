package com.mvalho.study.java.streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

public class StreamTests {
	private List<String> stringCollection;
	
	@Before
	public void setUp() {
		stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
	}
	
	@Test
	public void usingStreamFilter() {
		stringCollection
				.stream()
					.filter(s -> s.startsWith("a"))
					.forEach(System.out::println);
	}
	
	@Test
	public void usingStreamFilterWithCustomPredicate() {
		Predicate<String> startsWithD = string -> string.startsWith("d");
		
		stringCollection
				.stream()
					.filter(startsWithD)
					.forEach(System.out::println);
	}
	
	@Test
	public void usingStreamFilterWithCustomPredicateAndConsumer() {
		Predicate<String> startsWithB = string -> string.startsWith("b");
		Consumer<String> printString = System.out::println;
		
		stringCollection
				.stream()
					.filter(startsWithB)
					.forEach(printString);
	}
	
	@Test
	public void usingStreamSortedWithCustomPredicateAndConsumer() {
		Predicate<String> startsWithA = string -> string.startsWith("a");
		Consumer<String> printString = System.out::println;
		
		stringCollection
				.stream()
					.sorted()
					.filter(startsWithA)
					.forEach(printString);
	}
	
	@Test
	public void usingStreamMapWithCustomConsumerAndComparator() {
		Consumer<String> printString = System.out::println;
		Comparator<String> comparator = (a,b) -> b.compareTo(a);
		
		stringCollection
				.stream()
					.map(String::toUpperCase)
					.sorted(comparator)
					.forEach(printString);
	}
	
	@Test
	public void usingStreamAnyMatchWithCustomPredicate() {
		Predicate<String> predicate = s -> s.startsWith("a");
		
		boolean anyMatchWithA = stringCollection.stream().anyMatch(predicate);
		
		assertTrue(anyMatchWithA);
	}
	
	@Test
	public void usingStreamAllMatchWithCustomPredicate() {
		Predicate<String> predicate = s -> s.startsWith("a");
		
		boolean allMatchWithA = stringCollection.stream().allMatch(predicate);
		
		assertFalse(allMatchWithA);
	}
	
	@Test
	public void usingStreamNoneMatchWithCustomPredicate() {
		Predicate<String> predicate = s -> s.startsWith("z");
		
		boolean noneMatchWithA = stringCollection.stream().noneMatch(predicate);
		
		assertTrue(noneMatchWithA);
	}
	
	@Test
	public void usingStreamCountWithCustomPredicate() {
		Predicate<String> predicate = s -> s.startsWith("b");
		long startsWithBExpected = 3;
		long startsWithB = stringCollection.stream().filter(predicate).count();
		
		assertEquals(startsWithBExpected, startsWithB);
	}
	
	@Test
	public void usingStreamReduce() {
		/**
		 * In the Binary Operation ((s1, s2) -> s1 + "#" + s2), s1 is always the result of the last operation.
		 * For instance, if the array of string contains: "val1", "val2", "val3" and "val4"; 
		 * For the first iteration: s1 is empty and s2 is "val1", the result is s1 = "val1";
		 * For the second iteration: s1 is "val1" and s2 is "val2", the result is s1 = "val1#val2";
		 * For the third iteration: s1 is "val1#val2" and s2 is "val3", the result is s1 = "val1#val2#val3";
		 * For the forth iteration: s1 is "val1#val2#val3" and s2 is "val4", the result is s1 = "val1#val2#val3#val4";
		 */
		Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);
	}
	
	@Test
	public void usingStreamReduceWithCustomBinaryOperatorAndConsumer() {
		BinaryOperator<String> accumulator = (s1, s2) -> s1 + "#" + s2;
		Consumer<String> consumer = System.out::println;
		
		Optional<String> reduced = stringCollection.stream().sorted().reduce(accumulator);
		reduced.ifPresent(consumer);
	}
	
	@Test
	public void usingStreamReduceInverseSortedWithCustomBinaryOperatorAndConsumer() {
		BinaryOperator<String> accumulator = (s1, s2) -> s1 + "#" + s2;
		Consumer<String> consumer = System.out::println;
		Comparator<String> comparator = (a, b) -> b.compareTo(a);
		
		Optional<String> reduced = stringCollection.stream().sorted(comparator).reduce(accumulator);
		reduced.ifPresent(consumer);
	}
}
