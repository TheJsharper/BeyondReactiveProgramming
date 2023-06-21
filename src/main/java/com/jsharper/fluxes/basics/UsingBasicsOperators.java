package com.jsharper.fluxes.basics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.jsharper.utils.COUNT;
import com.jsharper.utils.ListOfPerson;
import com.jsharper.utils.Person;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;

public class UsingBasicsOperators {

	public static void main(String[] args) {
		UsingBasicsOperators bo = new UsingBasicsOperators();

		bo.just("Hello", "world!!").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		Flux<Integer> multipleSubscription = bo.multipleSubscription(1, 2, 3, 4);

		multipleSubscription.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		Predicate<Integer> even = (number) -> number % 2 == 0;

		multipleSubscription.filter(even).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		bo.fromIterable(Arrays.asList("a", "b", "c")).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		List<String> byCountry = Utils.getByCountryStr(COUNT.TEN);

		bo.fromIterable(byCountry).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		ListOfPerson listOfPerson = Utils.getListOfPerson(COUNT.TEN);

		bo.fromPersonIterable(listOfPerson).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
	}

	public Flux<String> fromIterable(Iterable<String> values) {
		return Flux.fromIterable(values);
	}

	public Flux<Person> fromPersonIterable(Iterable<Person> values) {
		return Flux.fromIterable(values);
	}

	public Flux<Integer> multipleSubscription(Integer... values) {
		return Flux.just(values);
	}

	public Flux<String> just(String... values) {
		return Flux.just(values);
	}

}
