package com.jsharper.utils;

public class Person {

	private String firstName;
	private String lastName;

	public Person(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person() {
		this.firstName = Utils.getInstance().name().firstName();
		this.lastName = Utils.getInstance().name().lastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	

}
