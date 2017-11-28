package java.z.cube.rxjava;

import org.junit.Test;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class RxjavaTest {

	@Test
	public void testName() throws Exception {
//		String[] s = { "a", "b" };
//		Observable.from(s).subscribe(new Action1<String>() {
//
//			@Override
//			public void call(String s) {
//				System.out.println("Hello " + s + "!");
//			}
//		});

		System.out.println("main:" + Thread.currentThread().getId());
		Observable.just(19, 28, 23).map(new Func1<Integer, String>() {
			public String call(Integer t) {
				System.out.println("map:" + Thread.currentThread().getId());
				return t + "x";
			};
		}).doOnNext(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println("doOnNext call:" + Thread.currentThread().getId());
				System.out.println(t);
			}
		}).doOnNext(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println("doOnNext2 call:" + Thread.currentThread().getId());
				System.out.println(t);
			}
		}).subscribeOn(Schedulers.newThread()).subscribe(new Action1<String>() {

			@Override
			public void call(String t) {
				System.out.println("subscribe call:" + Thread.currentThread().getId());
				System.out.println(t);
			}
		});
		System.out.println("main:" + Thread.currentThread().getId());
		Thread.sleep(100000);
	}
}
