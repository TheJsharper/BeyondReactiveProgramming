package com.jsharper.flux;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.jsharper.fluxes.creation.CombineLatest;
import com.jsharper.utils.COUNT;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

public class UsingFluxCombineLatest {
	private CombineLatest combineLatest;

	public UsingFluxCombineLatest() {
		combineLatest = new CombineLatest();
	}

	@Test
	public void combineLatestCb() {

		Function<Object[], String> combiner = (Object[] values) -> {
			return "testing: " + values[0];
		};
		List<String> listOfStringCountries = Utils.getByCountryStr(COUNT.HUNDRED);

		Flux<String> fluxOfCountries = Utils.getByCountries(listOfStringCountries);

		Flux<String> combineLatestWithCb = this.combineLatest.combineLatest(combiner, fluxOfCountries);

		FirstStep<String> create = StepVerifier.create(combineLatestWithCb);

		listOfStringCountries.forEach((items) -> create.expectNext("testing: " + items));

		create.expectComplete().verify();

	}

	@Test
	public void combineLatestCbWIthPrefetch() {

		Function<Object[], String> combiner = (Object[] values) -> "testing: " + values[0];

		List<String> listOfStringCountries = Utils.getByCountryStr(COUNT.HUNDRED);

		Flux<String> fluxOfCountries = Utils.getByCountries(listOfStringCountries);

		int givenPrefetch = -1;

		Flux<String> combineLatestWithCb = this.combineLatest.combineLatest(combiner, givenPrefetch, fluxOfCountries);

		int prefetch = fluxOfCountries.getPrefetch();

		assertEquals(prefetch, givenPrefetch);

		FirstStep<String> create = StepVerifier.create(combineLatestWithCb);

		listOfStringCountries.forEach((items) -> create.expectNext("testing: " + items));

		create.expectComplete().verify();

	}

	@Test
	public void combineLatestCbWIthBiFnc() {

		List<String> alphabet = Lists.newArrayList("A", "B", "C", "D");

		List<String> reverseAlphabet = Lists.newArrayList("Z", "Y", "X", "W", "V");

		Flux<String> fluxOfAlphabet = Flux.fromIterable(alphabet).delayElements(Duration.ofSeconds(3));

		Flux<String> fluxOfReverseAlphabet = Flux.fromIterable(reverseAlphabet).delayElements(Duration.ofSeconds(1));

		Flux<String> combineLatestWithCb = this.combineLatest.combineLatest(fluxOfAlphabet, fluxOfReverseAlphabet,
				(a, r) -> a + r);

		FirstStep<String> create = StepVerifier.create(combineLatestWithCb);

		create.expectNext("AY", "AX", "AW", "AV", "BV", "CV", "DV").expectComplete().verify();

	}

	@Test
	public void combineLatestCbWIthBiFnc2() {

		List<String> alphabet = Lists.newArrayList("A", "B", "C", "D");

		List<String> alphabet2 = Lists.newArrayList("A", "B", "C", "D");

		List<String> reverseAlphabet = Lists.newArrayList("Z", "Y", "X", "W", "V");

		Flux<String> fluxOfAlphabet = Flux.fromIterable(alphabet).delayElements(Duration.ofSeconds(4));

		Flux<String> fluxOfReverseAlphabet = Flux.fromIterable(reverseAlphabet).delayElements(Duration.ofSeconds(3));

		Flux<String> fluxOfReverseAlphabet2 = Flux.fromIterable(alphabet2).delayElements(Duration.ofSeconds(1));

		Flux<String> combineLatestWithCb = this.combineLatest.combineLatest(fluxOfAlphabet, fluxOfReverseAlphabet,
				fluxOfReverseAlphabet2, (values) -> Lists.newArrayList(values).stream().map((value) -> value.toString())
						.collect(Collectors.joining()));

		FirstStep<String> create = StepVerifier.create(combineLatestWithCb);

		create.expectNext("AZC", "AZD", "AYD", "BYD", "BXD", "BWD", "CWD", "CVD", "DVD").expectComplete().verify();

	}

	@Test
	public void combineLatestCbWIthBiFnc4() {

		List<String> alphabet = Lists.newArrayList("A", "B", "C", "D");

		List<String> alphabet2 = Lists.newArrayList("A", "B", "C", "D");

		List<String> numbers = Lists.newArrayList("1", "2", "3", "4");

		List<String> reverseAlphabet = Lists.newArrayList("Z", "Y", "X", "W", "V");

		Flux<String> fluxOfAlphabet = Flux.fromIterable(alphabet).delayElements(Duration.ofSeconds(7));

		Flux<String> fluxOfReverseAlphabet = Flux.fromIterable(reverseAlphabet).delayElements(Duration.ofSeconds(5));

		Flux<String> fluxOfReverseAlphabet2 = Flux.fromIterable(alphabet2).delayElements(Duration.ofSeconds(3));

		Flux<String> fluxOfReverseAlphabet3 = Flux.fromIterable(numbers).delayElements(Duration.ofSeconds(1));

		Flux<String> combineLatestWithCb = this.combineLatest.combineLatest(fluxOfAlphabet, fluxOfReverseAlphabet,
				fluxOfReverseAlphabet2, fluxOfReverseAlphabet3, (values) -> Lists.newArrayList(values).stream()
						.map((value) -> value.toString()).collect(Collectors.joining()));

		FirstStep<String> create = StepVerifier.create(combineLatestWithCb);

		create.expectNext("AZB4", "AZC4", "AYC4", "AYD4", "BYD4", "BXD4", "BWD4", "CWD4", "CVD4", "DVD4")
				.expectComplete().verify();

	}
}
