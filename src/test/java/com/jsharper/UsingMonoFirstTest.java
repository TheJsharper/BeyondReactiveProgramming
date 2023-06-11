package com.jsharper;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.jsharper.mono.first.UsingMonoFirst;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingMonoFirstTest {
	private UsingMonoFirst first;

	public UsingMonoFirstTest() {
		first = new UsingMonoFirst();
	}

	private static List<Mono<String>> getMonos() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map(value -> Mono.just(faker.name().fullName()))
				.collect(Collectors.toList());
	}

	private static List<String> getList() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map(value -> faker.name().fullName()).collect(Collectors.toList());
	}

	@Test
	public void firstWithSignal() {
		List<Mono<String>> monos = UsingMonoFirstTest.getMonos();

		String mono = monos.get(0).block();

		Mono<String> firstWithSignal = first.firstWithSignal(monos);

		StepVerifier.create(firstWithSignal).expectNext(mono).expectComplete().verify();
	}

	@Test
	public void firstWithSignalFromMono() {
		List<Mono<String>> monos = UsingMonoFirstTest.getMonos();

		Mono<String> mono = monos.get(0);

		String value = mono.block();

		Mono<String> firstWithSignal = first.firstWithSignalFromMono(mono);

		StepVerifier.create(firstWithSignal).expectNext(value).expectComplete().verify();
	}

	@Test
	public void firstWithValue() {
		List<Mono<String>> monos = UsingMonoFirstTest.getMonos();

		Mono<String> mono = monos.get(0);

		String value = mono.block();

		Mono<String> firstWithSignal = first.firstWithValue(mono);

		StepVerifier.create(firstWithSignal).expectNext(value).expectComplete().verify();
	}

	@Test
	public void firstWithValueList() {
		List<Mono<String>> monos = UsingMonoFirstTest.getMonos();

		Mono<String> mono = monos.get(0);

		String value = mono.block();

		Mono<String> firstWithSignal = first.firstWithValue(monos);

		StepVerifier.create(firstWithSignal).expectNext(value).expectComplete().verify();
	}

	@Test
	public void empty() {

		Mono<String> firstWithSignal = first.empty();

		StepVerifier.create(firstWithSignal).expectComplete().verify();
	}

	@Test
	public void errorWihtSupplier() {

		Mono<String> errorWihtSupplier = first.errorWihtSupplier(new RuntimeException("TOO DANGEROUS!!!"));

		StepVerifier.create(errorWihtSupplier).expectError(RuntimeException.class).verify();
	}

	@Test
	public void error() {

		Mono<String> errorWihtSupplier = first.error(new RuntimeException("TOO DANGEROUS!!!"));

		StepVerifier.create(errorWihtSupplier).expectError(RuntimeException.class).verify();
	}

	@Test
	public void ignoreElements() {
		List<String> list = UsingMonoFirstTest.getList();

		Flux<String> fromIterable = Flux.fromIterable(list);

		Mono<String> ignoreElements = first.ignoreElements(fromIterable);

		StepVerifier.create(ignoreElements).expectComplete().verify();
	}

	@Test
	public void just() {
		List<String> list = UsingMonoFirstTest.getList();

		String value = list.get(0);

		Mono<String> ignoreElements = first.just(value);

		StepVerifier.create(ignoreElements).expectNext(value).expectComplete().verify();
	}

	@Test
	public void justOrEmpty() {
		List<String> list = UsingMonoFirstTest.getList();

		String value = list.get(0);

		Mono<String> justOrEmpty = first.justOrEmpty(Optional.empty());

		StepVerifier.create(justOrEmpty).expectComplete().verify();

		Mono<String> justOrEmptyValue = first.justOrEmpty(Optional.of(value));

		StepVerifier.create(justOrEmptyValue).expectNext(value).expectComplete().verify();
	}

	@Test
	public void justOrEmptyNullable() {
		List<String> list = UsingMonoFirstTest.getList();

		String value = list.get(0);

		Mono<String> justOrEmpty = first.justOrEmptyNullable(null);

		StepVerifier.create(justOrEmpty).expectComplete().verify();

		Mono<String> justOrEmptyNullable = first.justOrEmptyNullable(value);

		StepVerifier.create(justOrEmptyNullable).expectNext(value).expectComplete().verify();
	}

	@Test
	public void never() {

		Mono<String> never = first.never();

		StepVerifier.create(never).expectTimeout(Duration.ofSeconds(5)).verify();
	}

}
