package com.jsharper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.junit.jupiter.api.Test;

import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

public class UsingMonoFromTest {
	private UsingMonoFrom monoFrom;

	public UsingMonoFromTest() {
		monoFrom = new UsingMonoFrom();
	}

	@Test
	public void fromTest() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.from(Mono.just(value));
		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromCallable() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.fromCallable(value);
		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromRunnable() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.fromRunnable(value);
		StepVerifier.create(from).expectComplete().verify();
	}

	@Test
	public void fromCompletionStageOfSupplier() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.fromCompletionStageOfSupplier(value);
		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromCompletionStage() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.fromCompletionStage(value);
		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromDirect() {
		String value = Utils.getInstance().name().fullName();
		Mono<String> from = this.monoFrom.fromDirect(Mono.just(value));
		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromFuture() {
		String value = Utils.getInstance().name().fullName();

		CompletionStage<String> completedStage = CompletableFuture.completedStage(value);

		Mono<String> from = this.monoFrom.fromFuture(completedStage.toCompletableFuture());

		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromFuturePreventCancellable() {
		String value = Utils.getInstance().name().fullName();

		CompletionStage<String> completedStage = CompletableFuture.completedStage(value);

		Mono<String> from = this.monoFrom.fromFuturePreventCancellable(completedStage.toCompletableFuture(), true);
		VirtualTimeScheduler scheduler = VirtualTimeScheduler.create();
		scheduler.dispose();
		from.cancelOn(scheduler);

		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromFutureOfSupplierCompletableFuture() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> from = this.monoFrom.fromFutureOfSupplierCompletableFuture(value);

		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromFutureOfSupplierCompletableFutureCancellable() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> from = this.monoFrom.fromFutureOfSupplierCompletableFutureCancellable(value, true);

		VirtualTimeScheduler scheduler = VirtualTimeScheduler.create();
		scheduler.dispose();
		from.cancelOn(scheduler);

		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}

	@Test
	public void fromSupplier() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> from = this.monoFrom.fromSupplier(value);

		StepVerifier.create(from).expectNext(value).expectComplete().verify();
	}
}
