package com.jsharper.flux;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.jsharper.fluxes.basics.UsingBasicsOperators;
import com.jsharper.utils.COUNT;
import com.jsharper.utils.CountryLocal;
import com.jsharper.utils.ListOfPerson;
import com.jsharper.utils.Person;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.Step;

public class UsingBasicOperatorsTests {

	UsingBasicsOperators basicsOperators;

	public UsingBasicOperatorsTests() {
		basicsOperators = new UsingBasicsOperators();
	}

	@Test
	public void just() {
		List<String> byCountry = Utils.getByCountryStr(COUNT.TEN);

		String[] array = byCountry.toArray(String[]::new);

		Flux<String> just = basicsOperators.just(array);

		StepVerifier.create(just).expectNext(array).expectComplete().verify();
	}

	@Test
	public void multipleSubscription() {
		Stream<Integer> boxed = IntStream.rangeClosed(0, 10).boxed();

		Integer[] array = boxed.collect(Collectors.toList()).toArray(Integer[]::new);

		Flux<Integer> multipleSubscription = basicsOperators.multipleSubscription(array);

		StepVerifier.create(multipleSubscription).expectNext(array).expectComplete().verify();

		StepVerifier.create(multipleSubscription.filter((number) -> number % 2 == 0)).expectNext(0, 2, 4, 6, 8, 10)
				.expectComplete().verify();

	}

	@Test
	public void fromPersonIterable() {
		ListOfPerson listOfPerson = Utils.getListOfPerson(COUNT.TEN);

		Person[] array = listOfPerson.getPersons().toArray(Person[]::new);

		Flux<Person> fromPersonIterable = basicsOperators.fromPersonIterable(listOfPerson);

		StepVerifier.create(fromPersonIterable).expectNext(array).expectComplete().verify();
	}

	@Test
	public void fromArray() {
		List<String> byCountryStr = Utils.getByCountryStr(COUNT.TEN);

		String[] array = byCountryStr.toArray(String[]::new);

		Flux<String> fromArray = basicsOperators.fromArray(array);

		StepVerifier.create(fromArray).expectNext(array).expectComplete().verify();

	}

	@Test
	public void fromIterable() {
		List<String> byCountryStr = Utils.getByCountryStr(COUNT.TEN);

		String[] array = byCountryStr.toArray(String[]::new);

		Flux<String> fromArray = basicsOperators.fromIterable(byCountryStr);

		StepVerifier.create(fromArray).expectNext(array).expectComplete().verify();

	}

	@Test
	public void fromStream() {
		List<CountryLocal> byCountryLocal = Utils.getByCountryLocal(COUNT.TEN);

		Stream<CountryLocal> stream = byCountryLocal.stream();

		CountryLocal[] array = byCountryLocal.toArray(CountryLocal[]::new);

		Flux<CountryLocal> fromStream = basicsOperators.fromStream(stream);

		StepVerifier.create(fromStream).expectNext(array).expectComplete().verify();

	}

	@Test
	public void fromStreamSupplier() {
		List<CountryLocal> byCountryLocal = Utils.getByCountryLocal(COUNT.TEN);

		Stream<CountryLocal> stream = byCountryLocal.stream();

		CountryLocal[] array = byCountryLocal.toArray(CountryLocal[]::new);

		Flux<CountryLocal> fromStream = basicsOperators.fromStream(() -> stream);

		StepVerifier.create(fromStream).expectNext(array).expectComplete().verify();

	}

	@Test
	public void range() {
		List<Integer> collect = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		
		Flux<Integer> range = basicsOperators.range(0, 10);
		
		StepVerifier.create(range).expectNext(collect.toArray(Integer[]::new)).expectComplete().verify();

	}

}
