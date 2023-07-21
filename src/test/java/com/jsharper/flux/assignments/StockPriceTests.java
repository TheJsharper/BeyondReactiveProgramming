package com.jsharper.flux.assignments;

import org.junit.jupiter.api.Test;

import com.jsharper.fluxes.assigments.stock.StockPrice;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StockPriceTests {

	private StockPrice stockPrice;

	public StockPriceTests() {
		this.stockPrice = new StockPrice();
	}

	@Test
	public void getPrice() {

		Flux<Integer> price = stockPrice.getPrice().log();

		StepVerifier.create(price).thenConsumeWhile(next -> next <= 120).thenCancel().verify();
	}

	@Test
	public void getPriceBetween() {

		Flux<Integer> price = stockPrice.getPriceBetween(90, 110).log();

		StepVerifier.create(price).thenConsumeWhile(next -> next >= 90 && next <= 110).thenCancel().verify();
	}

}
