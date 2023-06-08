package com.jsharper;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingMonoFromSupplierTest {

	private UsingMonoFromSupplier fromSupplier;
	
	public UsingMonoFromSupplierTest() {
		fromSupplier = new UsingMonoFromSupplier();
	}

	@Test
	public void fromSuupplier() {
		Mono<String> from = fromSupplier.getFromSupplier();
		StepVerifier.create(from).expectNextCount(1).expectComplete().verify();
	}
	@Test
	public void findUserById1() {
		Mono<String> findUserById = fromSupplier.findUserById(1);
		
		StepVerifier.create(findUserById).expectNextCount(1).expectComplete().verify();
	}
	@Test
	public void findUserById2() {
		Mono<String> findUserById = fromSupplier.findUserById(2);
		
		StepVerifier.create(findUserById).expectComplete().verify();
	}
	@Test
	public void findUserByIdSomeElse() {
		Mono<String> findUserById = fromSupplier.findUserById(200);
		
		StepVerifier.create(findUserById).expectError(RuntimeException.class).verify();
	}
}
