package com.mvalho.study.java.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class ParallelStreamsTests {	
	private List<String> values;
	
	@Before
	public void setUp() {
		int max = 1000000;
		values = new ArrayList<>(max);
		
		/**
		 * This replace the classic <pre>for(int i = 0; i < max; i++){};</pre> block statement.
		 */
		IntStream.range(0, max).forEach(i -> {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		});
	}
	
	@Test
	public void usingStreamsSequentialSort() {
		//FIXME Change this for Java 8 Clock class.
		long t0 = System.nanoTime();
		
		long count = values.stream().sorted().count();
		System.out.println(count);
		
		long t1 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("sequential sort took: %d ms", millis));
	}
	
	@Test
	public void usingParallelStreamsSequentialSort() {
		//FIXME Change this for Java 8 Clock class.
		long t0 = System.nanoTime();
		
		long count = values.parallelStream().sorted().count();
		System.out.println(count);
		
		long t1 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("parallel sort took: %d ms", millis));
	}
}
