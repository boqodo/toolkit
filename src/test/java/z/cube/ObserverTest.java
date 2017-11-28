package java.z.cube;

import java.util.Observable;
import java.util.Observer;

public class ObserverTest {
	static class XObservable extends Observable{
		@Override
		public synchronized void setChanged() {
			super.setChanged();
		}
	}
	public static void main(String[] args) throws Exception {
		XObservable xob=new XObservable();
	
		Observer os=new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println(arg);
			}
		};
		xob.addObserver(os);
		
		xob.setChanged();
		
		if(xob.hasChanged()){
			xob.notifyObservers("change");
		}
		//Thread.sleep(10000);
	}
}
