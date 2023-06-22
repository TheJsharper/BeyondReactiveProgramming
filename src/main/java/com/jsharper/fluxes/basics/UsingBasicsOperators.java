package com.jsharper.fluxes.basics;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.jsharper.utils.COUNT;
import com.jsharper.utils.CountryLocal;
import com.jsharper.utils.CustomSubscriber;
import com.jsharper.utils.ListOfPerson;
import com.jsharper.utils.Person;
import com.jsharper.utils.Utils;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class UsingBasicsOperators {

	public static void main(String[] args) {
		UsingBasicsOperators bo = new UsingBasicsOperators();

		bo.just("Hello", "world!!").subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		Flux<Integer> multipleSubscription = bo.multipleSubscription(1, 2, 3, 4);

		multipleSubscription.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		Predicate<Integer> even = (number) -> number % 2 == 0;

		multipleSubscription.filter(even).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		bo.fromIterable(Arrays.asList("a", "b", "c")).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		List<String> byCountry = Utils.getByCountryStr(COUNT.TEN);

		bo.fromIterable(byCountry).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		ListOfPerson listOfPerson = Utils.getListOfPerson(COUNT.TEN);

		bo.fromPersonIterable(listOfPerson).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		String[] array = Utils.getByCountryStr(COUNT.TEN).toArray(String[]::new);

		bo.fromArray(array).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		bo.fromStream(Utils.getByCountryLocal(COUNT.TEN).stream()).subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		Supplier<Stream<? extends CountryLocal>> values = () -> Utils.getByCountryLocal(COUNT.TEN).stream();

		bo.fromStream(values).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		bo.range(3, 10).subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		bo.range(3, 10).map(i -> Utils.getInstance().name().fullName()).subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		bo.range(3, 10).log().map(i -> Utils.getInstance().name().fullName()).log().subscribe(Utils.onNext(),
				Utils.onError(), Utils.onComplete());

		AtomicReference<Subscription> atomicReference = new AtomicReference<>();

		Flux<String> source = Flux.just("Hello", "World");

		source.log();

		Subscriber<String> subscriberStr = bo.getSubscriberStr(atomicReference);

		bo.subscripeWith(source, subscriberStr);

		Flux<Integer> range = bo.range(1, 10).log();

		AtomicReference<Subscription> ref = new AtomicReference<Subscription>();

		CustomSubscriber<Integer> customSubscriber = new CustomSubscriber<Integer>(ref);

		range.subscribeWith(customSubscriber);

		Utils.sleep(Duration.ofSeconds(5));

		atomicReference.get().request(2);

		ref.get().request(2);

		Utils.sleep(Duration.ofSeconds(5));

		ref.get().request(2);

		atomicReference.get().request(2);

		ref.get().cancel();

		ref.get().request(1);

		ref.get().cancel();

		ref.get().request(2);

		Utils.sleep(Duration.ofSeconds(5));

		System.out.println("-----------------------Starting testing interval(Duration)Interval---------------------");

		bo.interval(Duration.ofSeconds(5)).log().subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

		Utils.sleep(Duration.ofSeconds(35));

		System.out.println("-----------------------End testing interval (Duration)Interval---------------------");

		System.out.println(
				"-----------------------Starting testing interval startDelay(Duration)(Duration)Interval---------------------");

		bo.interval(Duration.ofSeconds(1), Duration.ofSeconds(5)).log().subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		Utils.sleep(Duration.ofSeconds(36));

		System.out.println(
				"-----------------------End testing interval startDelay(Duration) (Duration)Interval---------------------");

		System.out.println(
				"-----------------------Starting testing interval (Duration)Interval Interval with own scheduler paralell---------------------");

		Scheduler newParalell = Schedulers.newParallel("Interval with own scheduler paralell");

		bo.interval(Duration.ofSeconds(1), newParalell).log().subscribe(Utils.onNext(), Utils.onError(),
				Utils.onComplete());

		Utils.sleep(Duration.ofSeconds(36));
		System.out.println(
				"-----------------------End testing interval (Duration)Interval Interval with own scheduler paralell---------------------");
		newParalell.dispose();

		System.out.println(
				"-----------------------Starting testing interval (Duration)Interval Interval with own scheduler newSingle---------------------");

		Scheduler newSingle = Schedulers.newSingle("Interval with own scheduler new Single");

		bo.interval(Duration.ofSeconds(1), Duration.ofSeconds(2), newSingle).log().subscribe(Utils.onNext(),
				Utils.onError(), Utils.onComplete());

		Utils.sleep(Duration.ofSeconds(36));
		System.out.println(
				"-----------------------End testing interval (Duration)Interval Interval with own scheduler newSingle---------------------");
		newSingle.dispose();
	}

	public Flux<Long> interval(Duration d) {
		return Flux.interval(d);
	}

	public Flux<Long> interval(Duration delayStart, Duration d) {
		return Flux.interval(delayStart, d);
	}

	public Flux<Long> interval(Duration d, Scheduler s) {
		return Flux.interval(d, s);
	}

	public Flux<Long> interval(Duration delayStart, Duration d, Scheduler s) {
		return Flux.interval(delayStart, d, s);
	}

	public Subscriber<String> getSubscriberStr(AtomicReference<Subscription> atomicReference) {
		return new Subscriber<String>() {

			@Override
			public void onSubscribe(Subscription s) {
				atomicReference.set(s);
			}

			@Override
			public void onNext(String t) {
				System.out.println("Inline Next:" + t);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println(t.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("============COmpleted=====");

			}
		};

	}

	public <E> Subscriber<E> subscripeWith(Flux<E> source, Subscriber<E> subscriber) {

		return source.subscribeWith(subscriber);
	}

	public Flux<Integer> range(int start, int end) {

		return Flux.range(start, end);
	}

	public Flux<CountryLocal> fromStream(Stream<CountryLocal> values) {
		return Flux.fromStream(values);
	}

	public Flux<CountryLocal> fromStream(Supplier<Stream<? extends CountryLocal>> values) {
		return Flux.fromStream(values);
	}

	public Flux<String> fromIterable(Iterable<String> values) {
		return Flux.fromIterable(values);
	}

	public Flux<String> fromArray(String[] values) {
		return Flux.fromArray(values);
	}

	public Flux<Person> fromPersonIterable(Iterable<Person> values) {
		return Flux.fromIterable(values);
	}

	public Flux<Integer> multipleSubscription(Integer... values) {
		return Flux.just(values);
	}

	public Flux<String> just(String... values) {
		return Flux.just(values);
	}

}
