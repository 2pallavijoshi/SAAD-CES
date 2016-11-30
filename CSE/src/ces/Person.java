package ces;

public abstract class Person {

	private int uid;
	private String name;
	private String adress;
	private String phoneNumber;

	public Person(int uid, String name, String address, String phoneNumber) {
		this.uid = uid;
		this.name = name;
		this.adress = address;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Person [uid=" + uid + ", name=" + name + ", adress=" + adress + ", phoneNumber=" + phoneNumber + "]";
	}

	public Person(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getAdress() {
		return adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
