package com.jsharper.fluxes.assigments.stock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.github.javafaker.Faker;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;

public class StockPrice {

	public static void main(String[] args) {
		StockPrice sp = new StockPrice();

		CountDownLatch latch = new CountDownLatch(1);

		Flux<Integer> price = sp.getPrice();

		price.subscribeWith(sp.getSubscriber(latch));

		try {

			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Flux<Integer> getPrice() {
		AtomicInteger atomicInteger = new AtomicInteger(100);
		Faker instance = Utils.getInstance();
		return Flux.interval(Duration.ofSeconds(1))
				.map(i -> atomicInteger.getAndAccumulate(instance.random().nextInt(-5, 5), (a, b) -> a + b));
	}

	private Subscriber<Integer> getSubscriber(CountDownLatch latch) {
		return new Subscriber<Integer>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(Long.MAX_VALUE);

			}

			@Override
			public void onNext(Integer price) {
				System.out.println(LocalDateTime.now() + " " + " : Price: " + price);
				if (price > 110 || price < 90)
					latch.countDown();

			}

			@Override
			public void onError(Throwable t) {
				latch.countDown();

			}

			@Override
			public void onComplete() {
				latch.countDown();

			}
		};
	}
}
