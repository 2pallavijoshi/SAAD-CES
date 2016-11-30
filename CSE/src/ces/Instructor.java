package ces;

public class Instructor extends Person implements InstructorInterface {
	private String competencies;
	private String skills;

	public String getSkills() {
		return skills;
	}
	
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public String getCompetencies() {
		return competencies;
	}
	
	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}
	
	public Instructor(int uid) {
		super(uid);
	}
	
	@Override
	public String toString() {
		return "Instructor [toString()=" + super.toString() + ", getUid()=" + getUid() + ", getName()=" + getName()
		    + ", getAdress()=" + getAdress() + ", getContactNumber()=" + getPhoneNumber() + "]";
	}
	
	public Instructor(int uid, String name, String address, String contactNumber) {
		super(uid, name, address, contactNumber);
	}
	
	@Override
	public void teachCourse(Course course) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void manageCourse(Course course) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Record createAcademicRecord(Student student, Course course) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
