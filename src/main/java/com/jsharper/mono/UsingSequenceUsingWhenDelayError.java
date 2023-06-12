package com.jsharper.mono;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;

import com.github.javafaker.Faker;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UsingSequenceUsingWhenDelayError {

	public static void main(String[] args) {
		UsingSequenceUsingWhenDelayError that = new UsingSequenceUsingWhenDelayError();

		List<String> items = that.getList();

		that.sequenceEqual(Flux.fromIterable(items), Flux.fromIterable(items)).subscribe(Utils.onNext(),
				Utils.onError(), Utils.onComplete());

	}

	private List<String> getList() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map((vlaue) -> faker.name().fullName()).collect(Collectors.toList());
	}

	public Mono<Boolean> sequenceEqual(Publisher<String> first, Publisher<String> second) {

		return Mono.sequenceEqual(first, second);
	}

	public Mono<Boolean> sequenceEqual(Publisher<String> first, Publisher<String> second,
			BiPredicate<String, String> isEqual) {

		return Mono.sequenceEqual(first, second, isEqual);
	}

	public Mono<Boolean> sequenceEqual(Publisher<String> first, Publisher<String> second,
			BiPredicate<String, String> isEqual, int preFetch) {

		return Mono.sequenceEqual(first, second, isEqual, preFetch);
	}

	public Mono<String> using(Callable<Integer> ressource, Function<Integer, Mono<String>> sourceSupplier,
			Consumer<Integer> cleanUp) {

		return Mono.using(ressource, sourceSupplier, cleanUp);
	}

	public Mono<String> using(Callable<Integer> ressource, Function<Integer, Mono<String>> sourceSupplier,
			Consumer<Integer> cleanUp, boolean eager) {

		return Mono.using(ressource, sourceSupplier, cleanUp, eager);
	}

	public Mono<String> usingWhen(Publisher<Integer> resourceSupplier,
			Function<Integer, ? extends Mono<String>> resourceClosure,
			Function<Integer, ? extends Publisher<?>> asyncCleanup) {

		return Mono.usingWhen(resourceSupplier, resourceClosure, asyncCleanup);
	}

	public Mono<String> usingWhen(
			Publisher<Integer> ressource, 
			Function<Integer, Mono<String>> ressourceClosure,
			Function<Integer, Publisher<Integer>> asyncComplete,
			BiFunction<Integer, Throwable, Publisher<Integer>> asyncError,
			Function<Integer, Publisher<Integer>> asyncCancel) {

		return Mono.usingWhen(ressource, ressourceClosure, asyncComplete, asyncError, asyncCancel);
	}

	public Mono<Void> when(Publisher<Integer> sources) {

		return Mono.when(sources);
	}

	public Mono<Void> whenDelayError(List<Publisher<String>> sources) {

		return Mono.whenDelayError(sources);
	}

	public Mono<Void> whenDelayError(Publisher<?>... sources) {

		return Mono.whenDelayError(sources);
	}

}
