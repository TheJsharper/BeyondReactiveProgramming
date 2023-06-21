package com.jsharper.utils;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;

import com.github.javafaker.Country;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	public static Faker getInstance(Locale locale) {
		return Faker.instance(locale);
	}

	public static List<Country> getByCountry(COUNT count) {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, count.getValue()).boxed().map((value) -> faker.country())
				.collect(Collectors.toList());

	}

	public static List<String> getByCountryStr(COUNT count) {
		return getByCountry(count).stream().map(Country::name).collect(Collectors.toList());
	}

	public static Flux<String> getByCountries(List<String> countries) {
		return Flux.fromIterable(countries);
	}

	public static Mono<String> getMono() {
		return Mono.just(Faker.instance().country().name());
	}

	public static Flux<String> getListOfFlux() {
		List<String> byCountry = getByCountryStr(COUNT.TEN);

		Flux<String> fromIterable = getByCountries(byCountry);

		return fromIterable;
	}

	public static List<Publisher<String>> getFluxes(COUNT count) {
		return IntStream.rangeClosed(1, count.getValue()).mapToObj((value) -> getListOfFlux())
				.collect(Collectors.toList());
	}

	public static String getValuesSimpleString(Object[] values) {
		return Lists.newArrayList(values).stream().map((v) -> v.toString()).collect(Collectors.joining(","));
	}

	public static ListOfPerson getListOfPerson(COUNT count) {

		List<Person> map = IntStream.range(0, count.getValue()).boxed().map((value) -> new Person())
				.collect(Collectors.toList());

		return new ListOfPerson(map);
	}
}