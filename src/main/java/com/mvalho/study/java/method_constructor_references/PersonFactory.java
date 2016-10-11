package com.mvalho.study.java.method_constructor_references;

public interface PersonFactory<P extends Person> {
	P create(String firstName, String lastName);
}
