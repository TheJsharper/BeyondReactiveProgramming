package com.jsharper;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingMonoFromTest {
	private UsingMonoFrom monoFrom;

	public UsingMonoFromTest() {
		monoFrom = new UsingMonoFrom();
	}

	@Test
	public void fromClazz() {
		Mono<String> from = this.monoFrom.from(Mono.just("Test"));
		StepVerifier.create(from).expectNext("Test").expectComplete().verify();
	}
}
