package java.z.cube.retroift;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.OkHttpClient;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitTest {
	
	private boolean mIsDataMissing;
	
	@Test
	public void test6(){
		Observable.range(0, 10).filter(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer t) {
				return t%2==0;
			}
		})
		.subscribe(new Subscriber<Integer>() {

			@Override
			public void onCompleted() {
				mIsDataMissing = true;
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}
		});
		
		System.out.println("mIsDataMissing:"+mIsDataMissing);
	}
	
	@Test
	public void test4(){
		Observable.just(1, 2, 3).map(new Func1<Integer, String>() {

			@Override
			public String call(Integer t) {
				System.out.println("1:"+t);
				return t+"s";
			}
		}).map(new Func1<String, String>() {
			@Override
			public String call(String t) {
				System.out.println("2:"+t);
				return "s"+t;
			}
		}).filter(new Func1<String, Boolean>() {
			@Override
			public Boolean call(String t) {
				return t.contains("1");
			}
		}).last(new Func1<String, Boolean>() {
			public Boolean call(String t) {
				return t.endsWith("s");
			};
		})
		.subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println("end:"+t);
			}
		});
	}
	
	@Test
	public void test3() {
		
		
		Observable.just(1, 2, 3).doOnNext(new Action1<Integer>() {
			@Override
			public void call(Integer item) {
				if (item > 1) {
					throw new RuntimeException("Item exceeds maximum value");
				}
			}
		})
//		.onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
//			@Override
//			public Observable<? extends Integer> call(Throwable t) {
//				System.out.println("error:"+t.getMessage());
//				return Observable.just(-1);
//			}
//		})
		.onExceptionResumeNext(Observable.just(-1))
//		.onErrorResumeNext(Observable.just(-1))
//		.onErrorReturn(new Func1<Throwable, Integer>() {
//			@Override
//			public Integer call(Throwable t) {
//				return -1;
//			}
//		})
		.map(new Func1<Integer, Integer>() {
			@Override
			public Integer call(Integer t) {
				System.out.println("map t:"+t);
				int r = t-1;
//				if(r<0){
//					throw new RuntimeException("不能小于0");
//				}
				return r;
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onNext(Integer item) {
				System.out.println("Next: " + item);
			}

			@Override
			public void onError(Throwable error) {
				System.err.println("Error: " + error.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Sequence complete.");
			}
		});
	}

	@Test
	public void test2() throws Exception {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"localhost", 8888));
		OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.client(client)
				.addCallAdapterFactory(
						RxJavaCallAdapterFactory.createWithScheduler(Schedulers
								.immediate()))
				.addConverterFactory(JacksonConverterFactory.create()).build();

		final GitHubService service = retrofit.create(GitHubService.class);
		service.listUsersRx()
				.flatMap(new Func1<List<User>, Observable<? extends User>>() {
					@Override
					public Observable<? extends User> call(List<User> t) {
						return Observable.from(t);
					}
				}).filter(new Func1<User, Boolean>() {
					@Override
					public Boolean call(User t) {
						return StringUtils.startsWith(t.getLogin(), "m");
					}
				})
				.concatMap(new Func1<User, Observable<? extends List<Repo>>>() {

					@Override
					public Observable<? extends List<Repo>> call(User t) {
						System.out.println("user:" + t.getLogin());
						return service.listReposRx(t.getLogin());
					}
				}).flatMap(new Func1<List<Repo>, Observable<? extends Repo>>() {

					@Override
					public Observable<? extends Repo> call(List<Repo> t) {
						return Observable.from(t);
					}
				}).subscribeOn(Schedulers.newThread())
				.subscribe(new Action1<Repo>() {
					@Override
					public void call(Repo t) {
						System.out.println("Thread id:"
								+ Thread.currentThread().getId() + "t:" + t);
					}
				});

		Thread.currentThread().sleep(10000l);
	}

	@Test
	public void test1() throws Exception {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.addCallAdapterFactory(
						RxJavaCallAdapterFactory.createWithScheduler(Schedulers
								.immediate()))
				.addConverterFactory(JacksonConverterFactory.create()).build();

		GitHubService service = retrofit.create(GitHubService.class);
		Observable<List<Repo>> repsObs = service.listReposRx("octocat");
		repsObs.flatMap(new Func1<List<Repo>, Observable<? extends Repo>>() {

			@Override
			public Observable<? extends Repo> call(List<Repo> t) {
				return Observable.from(t);
			}
		}).subscribeOn(Schedulers.newThread()).subscribe(new Action1<Repo>() {
			@Override
			public void call(Repo t) {
				System.out.println("Thread id:"
						+ Thread.currentThread().getId() + "t:" + t);
			}
		});

		Thread.currentThread().sleep(10000l);
	}

	public static void main(String[] args) throws IOException {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.addCallAdapterFactory(new NormalJavaCallAdapterFactory())
				.addConverterFactory(JacksonConverterFactory.create()).build();

		GitHubService service = retrofit.create(GitHubService.class);
		List<Contributor> cs = service.repoContributors("square", "retrofit");
		for (Contributor c : cs) {
			System.out.println(c);
		}

		Call<List<Repo>> repos = service.listRepos("octocat");
		List<Repo> rs = repos.execute().body();
		for (Repo r : rs) {
			System.out.println(r);
		}
	}
}
