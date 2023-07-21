package com.jsharper.fluxes.creation;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.reactivestreams.Subscription;

import com.github.javafaker.Faker;
import com.jsharper.utils.COUNT;
import com.jsharper.utils.CountryLocal;
import com.jsharper.utils.DefaultSubscriber;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;

public class UsingCreate {

	public static void main(String[] args) {

		UsingCreate create = new UsingCreate();

		create.createSimple(Utils.getByCountryStr(COUNT.TEN))
				.subscribe(new DefaultSubscriber<Object>("Testing-1", new AtomicReference<Subscription>(), 10L));

		create.create(COUNT.TEN)
				.subscribe(new DefaultSubscriber<Object>("Testing-2", new AtomicReference<Subscription>(), 10L));

		create.createWithExceptionOffset(COUNT.TEN, 9, new RuntimeException("Opps"))
				.subscribe(new DefaultSubscriber<Object>("Testing-3", new AtomicReference<Subscription>(), 10L));

		Utils.sleep(Duration.ofSeconds(3));

	}

	public Flux<String> createSimple(List<String> source) {
		return Flux.create((s) -> {
			source.forEach((value) -> s.next(value));
			s.complete();
		});
	}

	public Flux<CountryLocal> create(COUNT count) {

		if (count.getValue() <= 0)
			return Flux.empty();

		Faker instance = Utils.getInstance();

		return Flux.create((s) -> {
			IntStream.range(0, count.getValue()).boxed().forEach((value) -> {
				s.next(new CountryLocal(instance));
			});
			s.complete();
		});

	}

	public Flux<CountryLocal> createWithExceptionOffset(COUNT count, int offsetException, Throwable kind) {
		Faker instance = Utils.getInstance();

		if (count.getValue() < offsetException)
			return Flux.empty();

		return Flux.create((s) -> {

			for (int i = 0; i < count.getValue(); i++) {
				if (i == offsetException)
					s.error(kind);
				s.next(new CountryLocal(instance));
			}
			s.complete();
		});

	}

}
