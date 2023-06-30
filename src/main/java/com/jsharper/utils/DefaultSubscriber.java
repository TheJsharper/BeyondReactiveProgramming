package com.jsharper.utils;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T> {

	private String name = "Anymous";

	private AtomicReference<Subscription> ref;

	private Long requestCount = Long.MAX_VALUE;

	public DefaultSubscriber(String name, AtomicReference<Subscription> ref, Long request) {
		this.name = name;
		this.ref = ref;
		this.requestCount = request;
	}

	public DefaultSubscriber(AtomicReference<Subscription> ref, Long request) {
		this.ref = ref;
		this.requestCount = request;
	}

	public DefaultSubscriber(AtomicReference<Subscription> ref) {
		this.ref = ref;
	}

	@Override
	public void onSubscribe(Subscription s) {
		s.request(requestCount);
		ref.set(s);

	}

	@Override
	public void onNext(T t) {
		LocalDateTime now = LocalDateTime.now();

		System.out.format("Default name: %s  subscriber time: %s received: %s\n", this.name, now.toString(), t);

	}

	@Override
	public void onError(Throwable t) {
		LocalDateTime now = LocalDateTime.now();
		System.err.format("Default name: %s  subscriber time: $´%s error: %s\n", name, now.toString(), t.getMessage());

	}

	@Override
	public void onComplete() {
		LocalDateTime now = LocalDateTime.now();
		System.out.format("Default name: %s  subscriber tine: %s %s\n", name, now.toString(), "  completed");

	}

	public String getName() {
		return name;
	}

	public AtomicReference<Subscription> getRef() {
		return ref;
	}

	public Long getRequestCount() {
		return requestCount;
	}

}
