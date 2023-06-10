package com.jsharper.mono.creation;

import java.time.Duration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class UsingMonoCreate {

	public static void main(String[] args) {

		UsingMonoCreate that = new UsingMonoCreate();

		that.createNext("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createError("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createComplete("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createDefer("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.deferContextual("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.delay(Duration.ofSeconds(4)).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(2);

		ex.execute(() -> System.out.println("Hello World"));

		Scheduler s = Schedulers.fromExecutor(ex);

		that.delayWithScheduler(Duration.ofSeconds(4), s).subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		try {
			if (!ex.awaitTermination(5, TimeUnit.SECONDS)) {
				ex.shutdownNow();
				ex.close();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public Mono<String> createNext(String value) {
		return Mono.create((sink) -> sink.success(value));

	}

	public Mono<String> createError(String value) {

		return Mono.create((sink) -> {
			sink.error(new RuntimeException("ups!!"));
			sink.success(value);// nope

		});

	}

	public Mono<String> createComplete(String value) {

		return Mono.create((sink) -> {
			sink.success();
			sink.success(value);// nope

		});

	}

	public Mono<String> createDefer(String value) {
		return Mono.defer(() -> Mono.just(value));

	}

	public Mono<String> deferContextual(String value) {
		return Mono.deferContextual((c) -> Mono.just(value));

	}

	public Mono<Long> delay(Duration duration) {
		return Mono.delay(duration);

	}

	public Mono<Long> delayWithScheduler(Duration duration, Scheduler s) {

		return Mono.delay(duration, s);

	}

}
