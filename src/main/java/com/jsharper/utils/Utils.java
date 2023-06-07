package com.jsharper.utils;

import java.util.function.Consumer;

import com.github.javafaker.Faker;

public class Utils {

	public static <T> Consumer<T> onNext() {
		return (T value) -> System.out.println("Received: " + value);
	}

	public static Consumer<Throwable> onError() {
		return (Throwable e) -> System.err.println("ERROR: " + e.getMessage());
	}

	public static Runnable onComplete() {
		return () -> System.out.println("Completed");
	}

	public static Faker getInstance() {
		return Faker.instance();
	}
}
