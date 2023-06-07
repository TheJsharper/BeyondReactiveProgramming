package com.jsharper;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;

public class UsingMonoFromSupplier {

	public static void main(String[] args) {
		UsingMonoFromSupplier fromSupplier = new UsingMonoFromSupplier();
		fromSupplier.getNames();
		fromSupplier.findUserById(1).subscribe(Utils.onNext());
		fromSupplier.findUserById(2).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
		fromSupplier.findUserById(100).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		fromSupplier.getFrromSupplier().subscribe(Utils.onNext());
	}

	private void getNames() {
		IntStream.range(0, 10).boxed().map((value) -> Utils.getInstance().name().fullName())
				.collect(Collectors.toList()).forEach((value) -> {
					System.out.println(value);
				});
	}

	public Mono<String> getFrromSupplier() {
		return Mono.fromSupplier(() -> getSimpleName());
	}

	private String getSimpleName() {
		return Utils.getInstance().name().fullName();
	}

	public Mono<String> findUserById(int id) {
		if (id == 1)
			return Mono.just(Utils.getInstance().name().firstName());
		else if (id == 2)
			return Mono.empty();
		else
			return Mono.error(new RuntimeException("NO FOUND USER ID " + id));
	}

}
