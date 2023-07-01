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
		Long requestCount = 10L;
		Flux<Integer> price = stockPrice.getPrice().take(requestCount, false);

		StepVerifier.create(price).expectSubscription().expectNextCount(requestCount).expectComplete().verify();
	}

}
