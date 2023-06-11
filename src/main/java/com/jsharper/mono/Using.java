package com.jsharper.mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;

import com.github.javafaker.Faker;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Using {

	public static void main(String[] args) {
		Using that = new Using();
		List<String> items = that.getList();
		that.getMono(Flux.fromIterable(items), Flux.fromIterable(items)).subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());
	}

	private List<String> getList() {
		Faker faker = Utils.getInstance();
		return IntStream.range(0, 10).boxed().map((vlaue) -> faker.name().fullName()).collect(Collectors.toList());
	}

	public Mono<Boolean> getMono(Publisher<String> first, Publisher<String> second) {

		return Mono.sequenceEqual(first, second);
	}

}
