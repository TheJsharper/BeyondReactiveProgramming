package com.jsharper.fluxes.creation;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.jsharper.utils.DefaultSubscriber;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;

public class UsingCreate {

	public static void main(String[] args) {
		
		//CountDownLatch latch = new CountDownLatch(1);
	
		AtomicReference<Subscription> ref = new AtomicReference<>();
		Subscriber<Object> defaultSubscriber = new DefaultSubscriber<Object>("Testing", ref, 20L);
		Flux.create((s)->{
			s.next("!");
			s.next("Hello");
			s.next("World");
			s.complete();
		}).subscribe(defaultSubscriber);
		Utils.sleep(Duration.ofSeconds(3));
		

	}

}
