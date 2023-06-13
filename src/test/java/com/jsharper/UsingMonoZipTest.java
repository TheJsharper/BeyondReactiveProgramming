package com.jsharper;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.jsharper.mono.UsingMonoZip;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UsingMonoZipTest {
	private UsingMonoZip zip;

	public UsingMonoZipTest() {
		zip = new UsingMonoZip();
	}

	@Test
	public void zip() {
		String value = Utils.getInstance().country().name();
		Function<Object[], String> combiner = (values) -> {
			Arrays.asList(values).forEach(System.out::println);
			return value;
		};
		Mono<String> zip2 = zip.zip(combiner, Mono.just(1));
		StepVerifier.create(zip2).expectNext(value).expectComplete().verify();
	}

	@Test
	public void zipTuple2() {
		String value = Utils.getInstance().country().name();

		Mono<String> map = zip.zip(Mono.just(value), Mono.just("")).map((t) -> t.getT1());
		StepVerifier.create(map).expectNext(value).expectComplete().verify();
	}
}
