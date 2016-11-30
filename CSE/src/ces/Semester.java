package ces;

public class Semester {
	private String session;
	
	public String getSession() {
		return session;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	private int year;
	
	public Semester(String session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "Semester [session=" + session + ", year=" + year + "]";
	}
	
}
