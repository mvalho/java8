package com.mvalho.study.java.functional_interfaces;

@FunctionalInterface
public interface Convert<F, T> {
	/**
	 * A functional inteface must contain exactly one abstract method.
	 * @param from
	 * @return
	 */
	T convert(F from);
}
