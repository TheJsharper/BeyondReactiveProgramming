package com.jsharper;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.jsharper.mono.UsingMonoZip;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
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

		Mono<String> map = zip.zip(Mono.just(value), Mono.just("Hello World")).map((t) -> t.getT1());

		StepVerifier.create(map).expectNext(value).expectComplete().verify();
	}

	@Test
	public void zipTuple3() {
		String value = Utils.getInstance().country().name();

		String thirdValue = "testing";

		Mono<String> map = zip.zip(Mono.just(value), Mono.just(""), Mono.just(thirdValue)).map((t) -> t.getT3());

		StepVerifier.create(map).expectNext(thirdValue).expectComplete().verify();
	}

	@Test
	public void zipTuple4() {
		String value = Utils.getInstance().country().name();

		String thirdValue = "testing";

		Mono<String> map = zip.zip(Mono.just(value), Mono.just(""), Mono.just(thirdValue), Mono.empty())
				.map((t) -> t.getT4());

		StepVerifier.create(map).expectComplete().verify();
	}

	@Test
	public void zipTuple5() {
		String value = Utils.getInstance().country().name();

		String thirdValue = "testing";

		Mono<String> delay = Mono.delay(Duration.ofSeconds(5)).doOnCancel(() -> System.err.println("CANCELLED"))
				.map((vale) -> "Mono will be cancelled").cancelOn(Schedulers.immediate())
				.or(Mono.error(new RuntimeException("Mono found Problem")));

		Mono<String> map = zip.zip(Mono.just(value), Mono.just(""), Mono.just(thirdValue), Mono.just("just one"), delay)
				.map((t) -> t.getT3());

		StepVerifier.create(map).expectError().verify();
	}

	@Test
	public void zipTuple6() {
		String value = Utils.getInstance().country().name();

		String thirdValue = "testing";

		String testingValue = "-----Data for uptream----";

		Mono<String> delay = Mono.delay(Duration.ofSeconds(5)).doOnCancel(() -> System.err.println("CANCELLED"))
				.map((vale) -> testingValue).doOnSuccess((String succesValue) -> System.out
						.println("\u001B[32m Data was successfully deliveried: " + succesValue + "\u001B[0m"));

		Mono<String> map = zip.zip(Mono.just(value), Mono.just(""), Mono.just(thirdValue), Mono.just("just one"), delay,
				Mono.just("just another")).map((t) -> t.getT5());

		StepVerifier.create(map).expectNext(testingValue).expectComplete().verify();
	}

	@Test
	public void zipTuple7() {
		String value = Utils.getInstance().country().name();

		String thirdValue = "testing";

		Mono<String> error = Mono.just("Try to do somthing").onErrorComplete(Throwable.class)
				.doOnError((e) -> true, (e) -> {
				}).or(Mono.error(new RuntimeException("UpppSI"))).onErrorResume((e) -> Mono.just("Fallback Mono"));

		Mono<String> map = zip.zip(error, Mono.just(value), Mono.just(""), Mono.just(thirdValue), Mono.just(""),
				Mono.just("ONE"), Mono.just("TWO")).map((t) -> t.getT1());

		StepVerifier.create(map).expectNext("Try to do somthing").expectComplete().verify();
	}
	@Test
	public void zipTuple8() {
		String value = Utils.getInstance().country().name();
		
		String thirdValue = "testing";
		
		Mono<String> error = Mono.just("Try to do somthing").onErrorComplete(Throwable.class)
				.doOnError((e) -> true, (e) -> {
				}).or(Mono.error(new RuntimeException("UpppSI"))).onErrorResume((e) -> Mono.just("Fallback Mono"));
		
		Mono<String> map = zip.zip(error, Mono.just(value), Mono.just(""), Mono.just(thirdValue), Mono.just(""),
				Mono.just("ONE"), Mono.just("TWO"), Mono.justOrEmpty("")).map((t) -> t.getT8());
		
		StepVerifier.create(map).expectNext("").expectComplete().verify();
	}
}
