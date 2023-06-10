package com.jsharper.mono.first;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;

import com.github.javafaker.Faker;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;

public class UsingMonoFirst {

	public static void main(String[] args) {
		UsingMonoFirst first = new UsingMonoFirst();

		List<Mono<String>> monos = first.getMonos();

		first.firstWithSignal(monos).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.firstWithSignalFromMono(monos.get(5)).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.firstWithValue(monos.get(9)).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.firstWithValue(monos).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.empty().subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.errorWihtSupplier(new IllegalArgumentException("upssi!")).subscribe(Utils.onNext(), Utils.onNext(),
				Utils.onComplete());

		first.error(new RuntimeException("upssi!")).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.ignoreElements(monos.get(0)).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.just("Hello World").subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.justOrEmpty(Optional.empty()).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.justOrEmptyNullable(null).subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());

		first.never().subscribe(Utils.onNext(), Utils.onNext(), Utils.onComplete());
	}

	private List<Mono<String>> getMonos() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map(value -> Mono.just(faker.name().fullName()))
				.collect(Collectors.toList());
	}

	public Mono<String> firstWithSignal(List<Mono<String>> values) {
		return Mono.firstWithSignal(values);

	}

	public Mono<String> firstWithSignalFromMono(Mono<String> value) {
		return Mono.firstWithSignal(value);

	}

	public Mono<String> firstWithValue(Mono<String> value) {
		return Mono.firstWithValue(value);

	}

	public Mono<String> firstWithValue(List<Mono<String>> values) {
		return Mono.firstWithValue(values);

	}

	public Mono<String> empty() {
		return Mono.empty();

	}

	public Mono<String> errorWihtSupplier(Throwable t) {

		return Mono.error(() -> t);

	}

	public Mono<String> error(Throwable t) {
		return Mono.error(t);

	}

	public Mono<String> ignoreElements(Publisher<String> p) {
		return Mono.ignoreElements(p);

	}

	public Mono<String> just(String value) {
		return Mono.just(value);

	}

	public Mono<String> justOrEmpty(Optional<String> value) {
		return Mono.justOrEmpty(value);

	}

	public Mono<String> justOrEmptyNullable(String value) {
		return Mono.justOrEmpty(value);

	}

	public Mono<String> never() {
		return Mono.never();

	}

}
