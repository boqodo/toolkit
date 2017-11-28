package java.pattern;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

interface Function {
	Object execute(String str);
}

class TriggerFunction implements Function {

	@Override
	public Object execute(String str) {
		return "trigger:" + str;
	}

}

class HandlerFunction implements Function {
	@Override
	public Object execute(String str) {

		return "handler:" + str;
	}
}

class ResultFunction implements Function {
	@Override
	public Object execute(String str) {

		return "result:" + str;
	}
}

class Promise {
	private String name;
	private List<Object> result;

	private Function trigger;
	private Function handler;

	public Promise(String name) {
		this.name = name;
		System.out.println("Promise:" + name);
	}

	public Promise then(Function function) {
		if (function instanceof HandlerFunction) {
			then((HandlerFunction) function);
		} else if (function instanceof TriggerFunction) {
			then((TriggerFunction) function);
		}
		return this;
	}

	private Promise then(HandlerFunction handler) {
		this.handler = handler;
		return this;
	}

	private Promise then(TriggerFunction trigger) {
		this.trigger = trigger;
		return this;
	}

	public void done(ResultFunction rfun) {
		Object res = this.trigger.execute(name);
		if (CollectionUtils.isEmpty(result)) {
			result = new ArrayList<Object>(8);
		}
		result.add(res);
		res = this.handler.execute(name);
		result.add(res);
		System.out.println(rfun.execute(result.toString()));
	}
}

public class PromiseTest {

	public static Promise when(String a) {
		return new Promise(a);
	}
	@Test
	public final void otest(){
		String name="resource";
		List<Object> result=new ArrayList<Object>(8);
		TriggerFunction trigger=new TriggerFunction();
		Object res=trigger.execute(name);
		result.add(res);
		HandlerFunction handler=new HandlerFunction();
		res=handler.execute(name);
		result.add(res);
		ResultFunction rfun=new ResultFunction();
		System.out.println(rfun.execute(result.toString()));
	}

	@Test
	public final void test() {
		when("resource").then(new TriggerFunction()).then(new HandlerFunction()).done(new ResultFunction());
	}
}
