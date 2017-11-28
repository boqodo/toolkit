package java.pattern;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventObserver {

	private String name;

	public EventObserver(String name) {
		this.name = name;
	}

	@Subscribe
	public void onMessage(String message) {
		System.out.println(name + ":[String]" + message);
	}
	@Subscribe
	public void onMessage(Integer i){
		System.out.println(name + ":[Integer]" + i);
	}

	public static void main(String[] args) {
		//eventHandler(new EventBus());
		
		Executor executor=Executors.newFixedThreadPool(5);
		eventHandler(new AsyncEventBus(executor));
	}

	private static void eventHandler(EventBus bus) {
		EventObserver a = new EventObserver("A");
		bus.register(a);
		EventObserver b = new EventObserver("B");
		bus.register(b);

		bus.post("x");
		bus.unregister(a);
		bus.post(22);
	}
}