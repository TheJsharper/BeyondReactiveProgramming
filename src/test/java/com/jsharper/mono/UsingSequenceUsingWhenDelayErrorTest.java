package com.jsharper.mono;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingSequenceUsingWhenDelayErrorTest {

	private UsingSequenceUsingWhenDelayError sequenceUsingWhenDelayError;

	public UsingSequenceUsingWhenDelayErrorTest() {
		sequenceUsingWhenDelayError = new UsingSequenceUsingWhenDelayError();
	}

	private List<String> getList() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map((vlaue) -> faker.name().fullName()).collect(Collectors.toList());
	}

	private List<Publisher<String>> getMonos() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map(value -> Mono.just(faker.name().fullName()))
				.collect(Collectors.toList());
	}

	@Test
	public void sequenceEqual() {
		List<String> itemsFirst = getList();

		List<String> itemsSecond = getList();

		Mono<Boolean> sequenceEqualSingle = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsFirst));

		StepVerifier.create(sequenceEqualSingle).expectNext(true).expectComplete().verify();

		Mono<Boolean> sequenceEqualTwice = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsSecond));

		StepVerifier.create(sequenceEqualTwice).expectNext(false).expectComplete().verify();

	}

	@Test
	public void sequenceEqualPredicate() {
		List<String> itemsFirst = List.of(new String[] { "Hello", "Hola", "Hallo" });
		List<String> itemsSecond = List.of(new String[] { "Hallo", "Hello", "Hola" });

		BiPredicate<String, String> predicate = (first, second) -> first.startsWith("H") && second.startsWith("H");

		Mono<Boolean> sequenceEqualSingle = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsSecond), predicate);

		StepVerifier.create(sequenceEqualSingle).expectNext(true).expectComplete().verify();

		itemsFirst = getList();

		itemsSecond = Lists.reverse(itemsFirst);

		BiPredicate<String, String> unequals = (first, second) -> !first.equals(second);

		Mono<Boolean> sequenceEqualTwice = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsSecond), unequals);

		StepVerifier.create(sequenceEqualTwice).expectNext(true).expectComplete().verify();

	}

	@Test
	public void sequenceEqualPredicatePrefetching() {
		List<String> itemsFirst = List.of(new String[] { "Hello", "Hola", "Hallo" });
		List<String> itemsSecond = List.of(new String[] { "Hallo", "Hello", "Hola" });

		BiPredicate<String, String> predicate = (first, second) -> first.startsWith("H") && second.startsWith("H");

		Mono<Boolean> sequenceEqualSingle = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsSecond), predicate, 3);

		StepVerifier.create(sequenceEqualSingle).expectNext(true).expectComplete().verify();

		itemsFirst = getList();

		itemsSecond = Lists.reverse(itemsFirst);

		BiPredicate<String, String> unequals = (first, second) -> !first.equals(second);

		Mono<Boolean> sequenceEqualTwice = sequenceUsingWhenDelayError.sequenceEqual(Flux.fromIterable(itemsFirst),
				Flux.fromIterable(itemsSecond), unequals, 3);

		StepVerifier.create(sequenceEqualTwice).expectNext(true).expectComplete().verify();

	}

	@Test
	public void using() {

		Callable<Integer> cb = () -> {
			return 1;
		};

		Function<Integer, Mono<String>> source = (value) -> {
			return Mono.just("Hello World" + value);
		};
		Consumer<Integer> cleanUp = (value) -> {
			System.out.println("up value " + value);

		};
		Mono<String> using = sequenceUsingWhenDelayError.using(cb, source, cleanUp);

		StepVerifier.create(using).expectNext("Hello World1").expectComplete().verify();

		StepVerifier.create(using).expectNext("Hello World1").thenCancel().verify();
	}

	@Test
	public void usingEager() {

		Callable<Integer> cb = () -> {
			return 1;
		};

		Function<Integer, Mono<String>> source = (value) -> {
			return Mono.just("Hello World" + value);
		};
		Consumer<Integer> cleanUp = (value) -> {
			System.out.println("up value " + value);

		};
		Mono<String> using = sequenceUsingWhenDelayError.using(cb, source, cleanUp, false);

		StepVerifier.create(using).expectNext("Hello World1").expectComplete().verify();

		StepVerifier.create(using).expectNext("Hello World1").thenCancel().verify();
	}

	@Test
	public void usingWhen() {

		List<Integer> s = IntStream.range(1, 10).boxed().toList();

		Publisher<Integer> p = Flux.fromIterable(s);

		Function<Integer, Mono<String>> source = (value) -> {
			return Mono.just("Hello World" + value);
		};

		Function<Integer, Mono<String>> asynCleanUp = (value) -> {
			return Mono.just("Hello World" + value);
		};

		Mono<String> usingWhen = sequenceUsingWhenDelayError.usingWhen(p, source, asynCleanUp);

		StepVerifier.create(usingWhen).expectNext("Hello World1").expectComplete().verify();

		StepVerifier.create(usingWhen).expectNext("Hello World1").thenCancel().verify();
	}

	@Test
	public void usingWhenWithCbs() {

		List<Integer> s = IntStream.range(1, 10).boxed().toList();

		Publisher<Integer> p = Flux.fromIterable(s);

		Function<Integer, Mono<String>> source = (value) -> {
			return Mono.just("Hello World" + value);
		};

		Function<Integer, Publisher<Integer>> asynComplete = (value) -> {
			return Mono.just(value);
		};

		BiFunction<Integer, Throwable, Publisher<Integer>> asynError = (Integer value, Throwable err) -> {
			return Mono.just(value);
		};
		Function<Integer, Publisher<Integer>> asynCancel = (value) -> {
			return Mono.just(value);
		};

		Mono<String> usingWhen = sequenceUsingWhenDelayError.usingWhen(p, source, asynComplete, asynError, asynCancel);

		StepVerifier.create(usingWhen).expectNext("Hello World1").expectComplete().verify();

		StepVerifier.create(usingWhen).expectNext("Hello World1").thenCancel().verify();

	}

	@Test
	public void when() {

		List<Integer> s = IntStream.range(1, 10).boxed().toList();

		Flux<Integer> p = Flux.fromIterable(s);

		Mono<Void> when = sequenceUsingWhenDelayError.when(p);

		StepVerifier.create(when).expectComplete().verify();

	}

	@Test
	public void whenDelayError() {

		List<Integer> s = IntStream.range(1, 10).boxed().toList();

		Flux<Integer> p = Flux.fromIterable(s);

		Mono<Void> when = sequenceUsingWhenDelayError.whenDelayError(p);

		StepVerifier.create(when).expectComplete().verify();

		Flux<Integer> errorFlux = Flux.error(new RuntimeException("Opsi"));

		StepVerifier.create(errorFlux).expectError(RuntimeException.class).verify();

	}

	@Test
	public void whenDelayErrorOfList() {

		List<Publisher<String>> monos = getMonos();

		Mono<Void> when = sequenceUsingWhenDelayError.whenDelayError(monos);

		StepVerifier.create(when).expectComplete().verify();

		Flux<Integer> errorFlux = Flux.error(new RuntimeException("Opsi"));

		StepVerifier.create(errorFlux).expectError(RuntimeException.class).verify();

	}
}
