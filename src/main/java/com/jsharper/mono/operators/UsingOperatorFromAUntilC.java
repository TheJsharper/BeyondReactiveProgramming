package com.jsharper.mono.operators;

import static com.jsharper.utils.Utils.getByCountry;
import static com.jsharper.utils.Utils.onComplete;
import static com.jsharper.utils.Utils.onError;
import static com.jsharper.utils.Utils.onNext;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;

import com.jsharper.utils.COUNT;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public class UsingOperatorFromAUntilC {

	public static void main(String[] args) {

		UsingOperatorFromAUntilC that = new UsingOperatorFromAUntilC();

		that.and(Mono.just("Hello "), Flux.just("World!")).subscribe(onNext(), onError(), onComplete());

		that.as(Mono.just("Hello "), (Mono<String> hello) -> {
			return Mono.just(hello.block() + " World");
		}).subscribe(onNext(), onError(), onComplete());

		System.out.println("Block " + that.block(Mono.just("Testing")));

		System.out.println("Block " + that.block(Mono.empty()));

		System.out.println("Block " + that.block(Mono.just("Testing"), Duration.ofSeconds(3)));

		System.out.println("Block " + that.blockOptional(Mono.empty()));

		System.out.println("Block " + that.blockOptional(Mono.just("Testing"), Duration.ofSeconds(3)));

		Parent p = new Child();
		that.cast(Mono.just(p), Child.class).subscribe(onNext(), onError(), onComplete());

		Mono<String> source = Mono.just("Hallo");

		Mono<String> cache = that.cache(source);

		cache.subscribe(onNext(), onError(), onComplete());

		Mono<String> cache2 = that.cache(Mono.error(new RuntimeException("UUUFFFFF")), Duration.ofSeconds(3));

		cache2.onErrorResume((e) -> Mono.just(" World")).subscribe(onNext(), onError(), onComplete());

		cache2.subscribe(onNext(), onError(), onComplete());

		Scheduler boundedElastic = Schedulers.newSingle("Single");

		Disposable subscribe = that.cancelOn(Mono.just("For cancellation"), boundedElastic).log()
				.doOnEach((v) -> System.out.println("forEach: " + v))
				.doOnCancel(() -> System.err.println("Cancellation ocurred"))
				.doOnSuccess((value) -> System.out.println("Success :" + value)).cancelOn(boundedElastic)
				.doOnNext((value) -> System.out.println("Next: " + value)).cache(Duration.ofMillis(2000))
				.doFirst(() -> System.out.println("Cancellation ocurred")).subscribe(onNext(), onError(), onComplete());
		subscribe.dispose();

		that.checkpoint(Mono.just("------------Checkpoint------")).map((v) -> Mono.error(new RuntimeException("hey")))
				.doOnError((e) -> System.err.println("============== " + e.getMessage() + " =================="))
				.subscribe(onNext(), onError(), onComplete());

		List<String> map = getByCountry(COUNT.TEN).stream().map((country) -> country.name())
				.collect(Collectors.toList());
		that.concatWith(Mono.just("FIRST ELEMENT"), Flux.fromIterable(map)).subscribe(onNext(), onError(),
				onComplete());

		try {
			Thread.sleep(Duration.ofSeconds(7));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Mono<Void> and(Mono<String> mono, Publisher<String> and) {
		return mono.and(and);
	}

	public Mono<String> as(Mono<String> source, Function<Mono<String>, Mono<String>> transformer) {
		return source.as(transformer);
	}

	public String block(Mono<String> source) {
		return source.block();
	}

	public String block(Mono<String> source, Duration duration) {
		return source.block(duration);
	}

	public Optional<String> blockOptional(Mono<String> source) {
		return source.blockOptional();
	}

	public Optional<String> blockOptional(Mono<String> source, Duration duration) {
		return source.blockOptional(duration);
	}

	public Mono<Child> cast(Mono<Parent> source, Class<Child> clazz) {
		return source.cast(clazz);
	}

	public Mono<String> cache(Mono<String> source) {
		return source.cache();
	}

	public Mono<String> cache(Mono<String> source, Duration duration) {
		return source.cache(duration);
	}

	public Mono<String> cache(Mono<String> source, Duration duration, Scheduler s) {
		return source.cache(duration, s);
	}

	public Mono<String> cache(Mono<String> source, Function<String, Duration> ttlForValue,
			Function<Throwable, Duration> ttlForError, Supplier<Duration> ttlForEmpty) {
		return source.cache(ttlForValue, ttlForError, ttlForEmpty);
	}

	public Mono<String> cache(Mono<String> source, Function<String, Duration> ttlForValue,
			Function<Throwable, Duration> ttlForError, Supplier<Duration> ttlForEmpty, Scheduler s) {
		return source.cache(ttlForValue, ttlForError, ttlForEmpty, s);
	}

	public Mono<String> cacheInvalidateIf(Mono<String> source, Predicate<String> invalidationPredicate) {
		return source.cacheInvalidateIf(invalidationPredicate);
	}

	public Mono<String> cacheInvalidateWhen(Mono<String> source,
			Function<String, Mono<Void>> invalidationTriggerGenerator) {
		return source.cacheInvalidateWhen(invalidationTriggerGenerator);
	}

	public Mono<String> cacheInvalidateWhen(Mono<String> source,
			Function<String, Mono<Void>> invalidationTriggerGenerator, Consumer<String> onInvalidate) {
		return source.cacheInvalidateWhen(invalidationTriggerGenerator, onInvalidate);
	}

	public Mono<String> cancelOn(Mono<String> source, Scheduler scheduler) {
		return source.cancelOn(scheduler);
	}

	public Mono<String> checkpoint(Mono<String> source) {
		return source.checkpoint();
	}

	public Mono<String> checkpoint(Mono<String> source, String description) {
		return source.checkpoint(description);
	}

	public Mono<String> checkpoint(Mono<String> source, @Nullable String description, boolean forceStackTrace) {
		return source.checkpoint(description, forceStackTrace);
	}

	public Flux<String> concatWith(Mono<String> source, Publisher<String> other) {
		return source.concatWith(other);
	}

	public Mono<String> contextCapture(Mono<String> source) {
		return source.contextCapture();
	}

	public Mono<String> contextWrite(Mono<String> source, ContextView contextToAppend) {
		return source.contextWrite(contextToAppend);
	}

	public final Mono<String> contextWrite(Mono<String> source, Function<Context, Context> contextModifier) {
		return source.contextWrite(contextModifier);
	}

}