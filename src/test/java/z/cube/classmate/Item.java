package java.z.cube.classmate;

public class Item<A,B> {

	private A a;
	private B b;
	
	public Item(A a, B b) {
		super();
		this.a = a;
		this.b = b;
	}
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	
}
