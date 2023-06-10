package com.jsharper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.jsharper.mono.first.UsingMonoFirst;
import com.jsharper.utils.Utils;

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

	@Test
	public void firstWithSignal() {
		List<Mono<String>> monos = UsingMonoFirstTest.getMonos();		
		
		String mono = monos.get(0).block();

		Mono<String> firstWithSignal = first.firstWithSignal(monos);
		
		StepVerifier.create(firstWithSignal).expectNext(mono).expectComplete().verify();
	}

}
