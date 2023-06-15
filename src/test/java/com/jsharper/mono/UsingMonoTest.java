package com.jsharper.mono;

import org.junit.jupiter.api.Test;

import com.jsharper.UsingMonoJust;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingMonoTest {

	private UsingMonoJust monoJust;

	public UsingMonoTest() {
		this.monoJust = new UsingMonoJust();
	}

	@Test
	public void monoVerifySimpleValueNext() {
		int value = 10;
		Mono<Integer> source = monoJust.getMonoOf(value);
		StepVerifier.create(source).expectNext(value)

				.expectComplete().verify();
	}
	@Test
	public void monoVerifySimpleError() {
		Integer value = 10;
		Mono<Integer> source = monoJust.getMonoExcepctionOf(value);
		StepVerifier.create(source).expectError()
		
		.verify();
	}
}
