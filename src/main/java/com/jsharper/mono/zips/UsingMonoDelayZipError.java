package com.jsharper.mono.zips;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.github.javafaker.Country;
import com.jsharper.utils.COUNT;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuple6;
import reactor.util.function.Tuple7;
import reactor.util.function.Tuple8;

public class UsingMonoDelayZipError {

	public static void main(String[] args) {
		UsingMonoDelayZipError zip = new UsingMonoDelayZipError();

		Function<Object[], String> combiner = (values) -> {
			Arrays.asList(values).forEach(System.out::println);
			return "Hello World";
		};
		List<Country> byCountry = Utils.getByCountry(COUNT.TEN);
		byCountry.forEach((f) -> {
			String name = f.capital();
			System.out.println("-->" + name + " ==  " + f.name());
		});
		Mono<Integer> delay = Mono.error(() -> new RuntimeException("TESTing")).delayElement(Duration.ofMinutes(2))
				.map((v) -> 1);
		zip.zipDelayError(combiner, delay).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Mono.just("first"), Mono.just("second")).subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		zip.zipDelayError(Mono.just("first"), Mono.just("second"), Mono.just("third")).subscribe(Utils.onNext(),
				Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Utils.getMono(), Utils.getMono(), Utils.getMono(), Utils.getMono()).flatMap((t) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(t.getT1()).append(",").append(t.getT2()).append(",").append(t.getT3()).append(",")
					.append(t.getT4());
			return Mono.just(sb.toString());
		}).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Utils.getMono(), Utils.getMono(), Utils.getMono(), Mono.error(new RuntimeException("Upsi")),
				Utils.getMono()).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Utils.getMono(), Utils.getMono(), Utils.getMono(), Mono.empty(), Utils.getMono(),
				Utils.getMono()).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Utils.getMono(), Utils.getMono(), Utils.getMono(), Mono.empty(), Utils.getMono(),
				Utils.getMono(), Utils.getMono()).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		zip.zipDelayError(Utils.getMono(), Utils.getMono(), Utils.getMono(), Mono.just("ONE"), Utils.getMono(),
				Utils.getMono(), Utils.getMono(), Utils.getMono())
				.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
		
	}

	public Mono<String> zipDelayError(Function<Object[], String> combiner, Mono<Integer> source) {
		return Mono.zipDelayError(combiner, source);
	}

	public Mono<String> zipDelayError(List<Mono<String>> monos, Function<Object[], String> combiner) {
		return Mono.zipDelayError(monos, combiner);
	}

	public Mono<Tuple2<String, String>> zipDelayError(Mono<String> mono1, Mono<String> mono2) {
		return Mono.zipDelayError(mono1, mono2);
	}

	public Mono<Tuple3<String, String, String>> zipDelayError(Mono<String> mono1, Mono<String> mono2,
			Mono<String> mono3) {
		return Mono.zipDelayError(mono1, mono2, mono3);
	}

	public Mono<Tuple4<String, String, String, String>> zipDelayError(Mono<String> mono1, Mono<String> mono2,
			Mono<String> mono3, Mono<String> mono4) {
		return Mono.zipDelayError(mono1, mono2, mono3, mono4);
	}

	public Mono<Tuple5<String, String, String, String, String>> zipDelayError(Mono<String> mono1, Mono<String> mono2,
			Mono<String> mono3, Mono<String> mono4, Mono<String> mono5) {
		return Mono.zipDelayError(mono1, mono2, mono3, mono4, mono5);
	}

	public Mono<Tuple6<String, String, String, String, String, String>> zipDelayError(Mono<String> mono1,
			Mono<String> mono2, Mono<String> mono3, Mono<String> mono4, Mono<String> mono5, Mono<String> mono6) {
		return Mono.zip(mono1, mono2, mono3, mono4, mono5, mono6);
	}

	public Mono<Tuple7<String, String, String, String, String, String, String>> zipDelayError(Mono<String> mono1,
			Mono<String> mono2, Mono<String> mono3, Mono<String> mono4, Mono<String> mono5, Mono<String> mono6,
			Mono<String> mono7) {
		return Mono.zipDelayError(mono1, mono2, mono3, mono4, mono5, mono6, mono7);
	}

	public Mono<Tuple8<String, String, String, String, String, String, String, String>> zipDelayError(
			Mono<String> mono1, Mono<String> mono2, Mono<String> mono3, Mono<String> mono4, Mono<String> mono5,
			Mono<String> mono6, Mono<String> mono7, Mono<String> mono8) {
		return Mono.zipDelayError(mono1, mono2, mono3, mono4, mono5, mono6, mono7, mono8);
	}

}
