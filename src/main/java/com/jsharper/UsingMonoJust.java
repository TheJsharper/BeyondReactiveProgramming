package com.jsharper;

import java.util.function.Consumer;

import reactor.core.publisher.Mono;

public class UsingMonoJust {

	public static void main(String[] args) {
		UsingMonoJust that = new UsingMonoJust();
		that.getMonoOf();
		that.getMonoExcepctionOf();
		
	}

	public Mono<Integer> getMonoOf(Integer value) {
		return Mono.just(value);
	}

	public Mono<Integer> getMonoExcepctionOf(Integer value) {
		return getMonoOf(value).map((v) -> v / 0);
	}

	private void getMonoOf() {
		this.getMonoOf(20).subscribe(this.onNext(), this.onError(), this.onCompleted());
	}

	private void getMonoExcepctionOf() {
		this.getMonoExcepctionOf(20).subscribe(this.onNext(), this.onError(), this.onCompleted());
	}

	private Consumer<? super Integer> onNext() {
		return (Integer value) -> System.out.println("OnNext: " + value);
	}

	private Consumer<? super Throwable> onError() {
		return (Throwable e) -> System.err.println(e.getMessage());
	}

	private Runnable onCompleted() {
		return () -> System.out.println("Completed");
	}

}
