package java.pattern;

import java.util.Observable;
import java.util.Observer;

class Good extends Observable {
	private String name;
	private float price;

	public Good(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		this.notifyObservers(price);
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		/* 
		 * 注意 ：在通知 所有观察者之前 
		 * 一定要调用 setChanged()方法来设置被观察者的状态已经被改变， 
		 */
		this.setChanged();
		/* 
		 * notifyObservers 方法在通知完所有吗 观察者 后， 
		 * 会自动调用 clearChanged 方法来清除 被观察者 的状态改变。 
		 */
		this.notifyObservers(price);
	}
}

class Customer implements Observer {
	private String name;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void update(Observable observable, Object arg) {
		if (observable instanceof Good && arg instanceof Float) {
			System.out.println("客户<" + this.name + "> : " + ((Good) observable).getName() + "的价格变了，" + arg + "元了 ！");
		}
	}
}

public class ObserverPattern {
	public static void main(String[] args) {
		Good good = new Good("洗衣粉", 3.5f);
		Customer tom = new Customer("Tom");
		Customer jerry = new Customer("Jerry");
		good.addObserver(tom);
		good.addObserver(jerry);
		good.setPrice(2.5f);
		good.setPrice(3.0f);
	}
}