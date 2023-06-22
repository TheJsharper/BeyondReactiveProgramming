package com.jsharper.utils;

import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class CustomSubscriber<T> implements Subscriber<T> {
	AtomicReference<Subscription> ref;

	public CustomSubscriber(AtomicReference<Subscription> ref) {
		this.ref = ref;
	}

	@Override
	public void onSubscribe(Subscription s) {
		ref.set(s);

	}

	@Override
	public void onNext(T t) {
		System.out.println("Custom onNext: " + t);

	}

	@Override
	public void onError(Throwable t) {
		System.err.println(t.getMessage());

	}

	@Override
	public void onComplete() {
		System.out.println("Custom onCompleteds");

	}

	public AtomicReference<Subscription> getRef() {
		return ref;
	}

}
