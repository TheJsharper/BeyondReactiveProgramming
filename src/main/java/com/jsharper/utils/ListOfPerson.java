package com.jsharper.utils;

import java.util.Iterator;
import java.util.List;

public class ListOfPerson implements Iterable<Person> {

	private List<Person> persons;

	public ListOfPerson(List<Person> p) {
		persons = p;
	}

	public List<Person> getPersons() {
		return persons;
	}

	@Override
	public Iterator<Person> iterator() {
		return persons.iterator();
	}

}
