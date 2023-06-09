package com.jsharper.mono.from;

import java.util.concurrent.CompletableFuture;

import org.reactivestreams.Publisher;

import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;

public class UsingMonoFrom {

	public static void main(String[] args) {
		UsingMonoFrom from = new UsingMonoFrom();

		from.from(Mono.just("Test")).subscribe(Utils.onNext());

		from.fromCallable("test").subscribe(Utils.onNext());

		from.fromRunnable("test").subscribe(Utils.onNext());

		from.fromCompletionStage("test").subscribe(Utils.onNext());

		from.fromCompletionStageOfSupplier("test").subscribe(Utils.onNext());

		from.fromDirect(Mono.just("test")).subscribe(Utils.onNext());

		from.fromFuture(CompletableFuture.supplyAsync(() -> "test")).subscribe(Utils.onNext());

		from.fromFuturePreventCancellable(CompletableFuture.supplyAsync(() -> "test"), true).subscribe(Utils.onNext());

		from.fromFutureOfSupplierCompletableFuture("test").subscribe(Utils.onNext());

		from.fromFutureOfSupplierCompletableFutureCancellable("test", false).subscribe(Utils.onNext());

	}

	public Mono<String> from(Publisher<String> publisher) {
		return Mono.from(publisher);
	}

	public Mono<String> fromCallable(String value) {
		return Mono.fromCallable(() -> value);
	}

	public Mono<String> fromRunnable(String value) {
		return Mono.fromRunnable(() -> System.out.println(value));
	}

	public Mono<String> fromCompletionStage(String value) {
		return Mono.fromCompletionStage(CompletableFuture.completedFuture(value));
	}

	public Mono<String> fromCompletionStageOfSupplier(String value) {
		return Mono.fromCompletionStage(() -> CompletableFuture.completedFuture(value));
	}

	public Mono<String> fromDirect(Publisher<String> direct) {
		return Mono.fromDirect(direct);
	}

	public Mono<String> fromFuture(CompletableFuture<String> completableFuture) {
		return Mono.fromFuture(completableFuture);
	}

	public Mono<String> fromFuturePreventCancellable(CompletableFuture<String> completableFuture, boolean cancel) {
		return Mono.fromFuture(completableFuture, cancel);
	}

	public Mono<String> fromFutureOfSupplierCompletableFuture(String value) {
		return Mono.fromFuture(() -> CompletableFuture.completedFuture(value));
	}

	public Mono<String> fromFutureOfSupplierCompletableFutureCancellable(String value, boolean cancel) {
		return Mono.fromFuture(() -> CompletableFuture.completedFuture(value), cancel);
	}

	public Mono<String> fromSupplier(String value) {
		return Mono.fromSupplier(() -> value);
	}

}
