package java.z.cube;


import org.jdeferred.AlwaysCallback;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.impl.DeferredObject;
import org.junit.Test;

public class JDeferredTest {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void test(){
		Deferred deferred = new DeferredObject();
		org.jdeferred.Promise promise = deferred.promise();
		promise.done(new DoneCallback<String>() {
			public void onDone(String result) {
				System.out.println("done:"+result);
			}
		}).fail(new FailCallback<Integer>() {
			@Override
			public void onFail(Integer result) {
				System.out.println("fail:"+result);
			}
		}).progress(new ProgressCallback<Integer>() {
			@Override
			public void onProgress(Integer progress) {
				System.out.println("progress:"+progress+"%");
			}
		}).always(new AlwaysCallback<String,Integer>() {
			@Override
			public void onAlways(State state, String resolved, Integer rejected) {
				System.out.println("always:"+state);
			}
		});
		for (int i = 1; i <= 10; i++) {
			deferred.notify(i*10);
		}
		//deferred.reject(Integer.MAX_VALUE);
		deferred.resolve("ok");
	}
}

