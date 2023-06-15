package com.jsharper.fluxes.creation;

import static com.jsharper.utils.Utils.getFluxes;
import static com.jsharper.utils.Utils.getListOfFlux;
import static com.jsharper.utils.Utils.getValuesSimpleString;
import static com.jsharper.utils.Utils.onComplete;
import static com.jsharper.utils.Utils.onError;
import static com.jsharper.utils.Utils.onNext;

import java.text.MessageFormat;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import com.jsharper.utils.COUNT;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class CombineLatest {

	public static void main(String[] args) {
		CombineLatest that = new CombineLatest();
		Function<Object[], String> combiner = (Object[] values) -> {
			return "testing: " + values[0];
		};

		that.combineLatest(combiner, getListOfFlux()).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(combiner, 3, getListOfFlux()).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getListOfFlux(), getListOfFlux(), (a, b) -> {
			return MessageFormat.format("FROM  Flux {0} ----- FROM second  {1} ", a, b);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getListOfFlux(), getListOfFlux(), getListOfFlux(), (Object[] values) -> {

			String message = getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getListOfFlux(), getListOfFlux(), getListOfFlux(), getListOfFlux(), (Object[] values) -> {

			String message = getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getListOfFlux(), getListOfFlux(), getListOfFlux(), getListOfFlux(), getListOfFlux(),
				(Object[] values) -> {

					String message = getValuesSimpleString(values);
					return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
				}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getListOfFlux(), getListOfFlux(), getListOfFlux(), getListOfFlux(), getListOfFlux(),
				getListOfFlux(), (Object[] values) -> {

					String message = getValuesSimpleString(values);
					return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
				}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getFluxes(COUNT.TEN), (Object[] values) -> {

			String message = getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(getFluxes(COUNT.HUNDRED), 50, (Object[] values) -> {

			String message = getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", message);
		}).log()
		.publishOn(Schedulers.parallel()).
		doOnNext((v) -> System.out.println("doOnNext: " + v)).subscribe(onNext(), onError(), onComplete());

	}

	public Flux<String> combineLatest(Function<Object[], String> combiner, Publisher<String> sources) {
		return Flux.combineLatest(combiner, sources);
	}

	public Flux<String> combineLatest(Function<Object[], String> combiner, int prefetch, Publisher<String> sources) {
		return Flux.combineLatest(combiner, prefetch, sources);
	}

	public Flux<String> combineLatest(Publisher<String> sources1, Publisher<String> sources2,
			BiFunction<String, String, String> combinator) {
		return Flux.combineLatest(sources1, sources2, combinator);
	}

	public Flux<String> combineLatest(Publisher<String> sources1, Publisher<String> sources2,
			Publisher<String> sources3, Function<Object[], String> combinator) {
		return Flux.combineLatest(sources1, sources2, sources3, combinator);
	}

	public Flux<String> combineLatest(Publisher<String> sources1, Publisher<String> sources2,
			Publisher<String> sources3, Publisher<String> sources4, Function<Object[], String> combinator) {
		return Flux.combineLatest(sources1, sources2, sources3, sources4, combinator);
	}

	public Flux<String> combineLatest(Publisher<String> sources1, Publisher<String> sources2,
			Publisher<String> sources3, Publisher<String> sources4, Publisher<String> sources5,
			Function<Object[], String> combinator) {
		return Flux.combineLatest(sources1, sources2, sources3, sources4, sources5, combinator);
	}

	public Flux<String> combineLatest(Publisher<String> sources1, Publisher<String> sources2,
			Publisher<String> sources3, Publisher<String> sources4, Publisher<String> sources5,
			Publisher<String> sources6, Function<Object[], String> combinator) {
		return Flux.combineLatest(sources1, sources2, sources3, sources4, sources5, sources6, combinator);
	}

	public Flux<String> combineLatest(Iterable<Publisher<String>> sources, Function<Object[], String> combinator) {
		return Flux.combineLatest(sources, combinator);
	}

	public Flux<String> combineLatest(Iterable<Publisher<String>> sources, int prefetch,
			Function<Object[], String> combinator) {

		return Flux.combineLatest(sources, prefetch, combinator);
	}

}
