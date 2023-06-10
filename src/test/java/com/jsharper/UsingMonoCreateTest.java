package com.jsharper;

import java.time.Duration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.jsharper.mono.creation.UsingMonoCreate;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class UsingMonoCreateTest {

	private UsingMonoCreate create;

	public UsingMonoCreateTest() {
		create = new UsingMonoCreate();
	}

	@Test
	public void createNext() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> createNext = create.createNext(value);

		StepVerifier.create(createNext).expectNext(value).expectComplete().verify();
	}

	@Test
	public void createError() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> createError = create.createError(value);

		StepVerifier.create(createError).expectError(RuntimeException.class).verify();
	}

	@Test
	public void createComplete() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> createComplete = create.createComplete(value);

		StepVerifier.create(createComplete).expectComplete().verify();
	}

	@Test
	public void createDefer() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> createComplete = create.createDefer(value);

		StepVerifier.create(createComplete).expectNext(value).expectComplete().verify();
	}

	@Test
	public void deferContextual() {
		String value = Utils.getInstance().name().fullName();

		Mono<String> createComplete = create.createDefer(value);

		StepVerifier.create(createComplete).expectNext(value).expectComplete().verify();
	}

	@Test
	public void delay() {
		Duration ofSeconds = Duration.ofSeconds(4);

		Mono<Long> createComplete = create.delay(ofSeconds);

		StepVerifier.create(createComplete).expectTimeout(ofSeconds).verify();

		StepVerifier.create(createComplete).expectNext(0L).expectComplete().verify();
	}

	@Test
	public void delayWithScheduler() {
		
		ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(2);

		ex.execute(() -> System.out.println("Hello World"));

		Scheduler s = Schedulers.fromExecutor(ex);		
		
		Duration ofSeconds = Duration.ofSeconds(4);

		Mono<Long> createComplete = create.delayWithScheduler(ofSeconds, s);

		StepVerifier.create(createComplete).expectTimeout(ofSeconds).verify();

		StepVerifier.create(createComplete).expectNext(0L).expectComplete().verify();	
		
		
		try {
			if (!ex.awaitTermination(5, TimeUnit.SECONDS)) {
				ex.shutdownNow();
				ex.close();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
