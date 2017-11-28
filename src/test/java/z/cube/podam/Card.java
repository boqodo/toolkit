package java.z.cube.podam;

public class Card {
	private int id;
	private Address address;

	
	public Card() {
	}

	public Card(int id, Address address) {
		this.id = id;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", address=" + address + "]";
	}
}