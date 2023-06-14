package com.jsharper.fluxes.creation;

import static com.jsharper.utils.Utils.getByCountries;
import static com.jsharper.utils.Utils.getByCountryStr;
import static com.jsharper.utils.Utils.onComplete;
import static com.jsharper.utils.Utils.onError;
import static com.jsharper.utils.Utils.onNext;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;

import com.google.common.collect.Lists;
import com.jsharper.utils.COUNT;

import reactor.core.publisher.Flux;

public class CombineLatest {

	public static void main(String[] args) {
		CombineLatest that = new CombineLatest();
		Function<Object[], String> combiner = (Object[] values) -> {
			return "testing: " + values[0];
		};

		that.combineLatest(combiner, that.getListOfFlux()).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(combiner, 3, that.getListOfFlux()).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getListOfFlux(), that.getListOfFlux(), (a, b) -> {
			return MessageFormat.format("FROM first Flux {0} ----- FROM second  {1} ", a, b);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(), (Object[] values) -> {

			String vv = that.getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(),
				(Object[] values) -> {

					String vv = that.getValuesSimpleString(values);
					return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
				}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(),
				that.getListOfFlux(), (Object[] values) -> {

					String vv = that.getValuesSimpleString(values);
					return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
				}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(), that.getListOfFlux(),
				that.getListOfFlux(), that.getListOfFlux(), (Object[] values) -> {

					String vv = that.getValuesSimpleString(values);
					return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
				}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getFluxes(COUNT.TEN), (Object[] values) -> {

			String vv = that.getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
		}).subscribe(onNext(), onError(), onComplete());

		that.combineLatest(that.getFluxes(COUNT.HUNDRED), 50, (Object[] values) -> {

			String vv = that.getValuesSimpleString(values);
			return MessageFormat.format("FROM  Flux ------ {0} -----  ", vv);
		}).subscribe(onNext(), onError(), onComplete());

	}

	private Flux<String> getListOfFlux() {
		List<String> byCountry = getByCountryStr(COUNT.TEN);

		Flux<String> fromIterable = getByCountries(byCountry);

		return fromIterable;
	}

	private List<Publisher<String>> getFluxes(COUNT count) {
		return IntStream.rangeClosed(1, count.getValue()).mapToObj((value) -> getListOfFlux())
				.collect(Collectors.toList());
	}

	private String getValuesSimpleString(Object[] values) {
		return Lists.newArrayList(values).stream().map((v) -> v.toString()).collect(Collectors.joining(","));
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
