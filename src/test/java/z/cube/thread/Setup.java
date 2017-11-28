package java.z.cube.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Setup {
	public static void main(String[] args) {
		BlockingQueue q = new ArrayBlockingQueue(10);
		Producer p = new Producer(q);
		Producer p2 = new Producer(q);
		Producer p3 = new Producer(q);
		Producer p4 = new Producer(q);
		Consumer c1 = new Consumer(q);
		Consumer c2 = new Consumer(q);
		new Thread(p).start();
		new Thread(p2).start();
		new Thread(p3).start();
		new Thread(p4).start();
		new Thread(c1).start();
		new Thread(c2).start();
	}
}

class Producer implements Runnable {
	private final BlockingQueue queue;

	Producer(BlockingQueue q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(2000);
				Object o=produce();
				System.out.println("Producer:"+o);
				queue.put(o);
			}
		} catch (InterruptedException ex) {
		}
	}

	Object produce() {
		return 1;
	}
}

class Consumer implements Runnable {
	private final BlockingQueue queue;

	Consumer(BlockingQueue q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(3000);
				consume(queue.take());
			}
		} catch (InterruptedException ex) {
		}
	}

	void consume(Object x) {
		System.out.println(this+"consume"+x);
	}
}
