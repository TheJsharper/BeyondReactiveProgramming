package com.jsharper.mono.creation;

import java.time.Duration;
import java.util.concurrent.Executor;

import com.jsharper.utils.Utils;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.*;

public class Create {

	public static void main(String[] args) {

		Create that = new Create();
		that.createNext("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createError("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createComplete("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.createDefer("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.deferContextual("HeY").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		that.delay(Duration.ofSeconds(4)).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
		
		Scheduler s = Schedulers.fromExecutor(new Executor() {
			
			@Override
			public void execute(Runnable command) {
				System.out.println("HEEEEE");
				command.run();
				
			}
		});
		that.delayWithScheduler(Duration.ofSeconds(4), s).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private Mono<String> createNext(String value) {
		return Mono.create((sink) -> sink.success(value));

	}

	private Mono<String> createError(String value) {

		return Mono.create((sink) -> {
			sink.error(new RuntimeException("ups!!"));
			sink.success(value);// nope

		});

	}

	private Mono<String> createComplete(String value) {

		return Mono.create((sink) -> {
			sink.success();
			sink.success(value);// nope

		});

	}

	private Mono<String> createDefer(String value) {
		return Mono.defer(() -> Mono.just(value));

	}

	private Mono<String> deferContextual(String value) {
		return Mono.deferContextual((c) -> Mono.just(value));

	}

	private Mono<Long> delay(Duration duration) {
		System.out.println("-----");
		return Mono.delay(duration);

	}

	private Mono<Long> delayWithScheduler(Duration duration, Scheduler s) {

		return Mono.delay(duration);

	}

}
