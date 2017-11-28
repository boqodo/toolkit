package java.z.cube.podam;

public class Address {
	private String local;

	
	public Address() {
	}

	public Address(String local) {
		this.local = local;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public String toString() {
		return "Address [local=" + local + "]";
	}
	
}