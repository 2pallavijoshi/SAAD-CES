package ces;

import java.util.ArrayList;
import java.util.List;

import ces.registration.Registration;
import ces.registration.RegistrationRecord;
import ces.util.Constants;

public class Student extends Person {

	private List<Record> records = new ArrayList<>();
	private Status status;
	private List<Course> program;
	private AcademicCounselor counselor;

	public AcademicCounselor getCounselor() {
		return counselor;
	}

	public void setCounselor(AcademicCounselor counselor) {
		this.counselor = counselor;
	}

	public List<Course> getProgram() {
		return program;
	}

	public void setProgram(List<Course> program) {
		this.program = program;
	}


	public Student(int uid, String name, String address, String contactNumber) {
		super(uid, name, address, contactNumber);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Record> getAllRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public RegistrationRecord registerCourse(Integer courseId) {

		CourseCatalog catalog = CourseCatalog.getInstance();

		Course course = catalog.getCourse(courseId);
		if (course == null) {
			System.out.println(String.format("Course id does not exists", courseId));
			return null;
		}
		List<Integer> prerequisites = course.getPrerequisites();

		// 1. Prereq
		if (prerequisiteValidationFailed(prerequisites)) {
			return Registration.deny(getUid(), courseId, Constants.REQ_INVALID_PREREQ);
		}
		// 2. Already Taken
		for (Record record : getAllRecords()) {
			if (record.getCourseId() == courseId) {
				// Check course retake availability
				if (record.isRetakeAvailable()) {
					course.allocateSeat();
					return Registration.approve(getUid(), courseId);
				} else if (record.courseCompletedSuccessfully()) {
					return Registration.deny(getUid(), courseId, Constants.REQ_INVALID_ALREADY_TAKEN);
				}
			}
		}

		// 3. No seats available
		if (course.isAvailable()) {
			course.allocateSeat();
			return Registration.approve(getUid(), courseId);
		} else {
			return Registration.deny(getUid(), courseId, Constants.REQ_INVALID_NO_SEATS);
		}
	}

	public void dropCourse(Integer courseId) {
		// TODO Auto-generated method stub
	}
	
	public void addRecord(int courseId, int instructorId, String comments, Grade grade){
		// todo
	}

	private boolean prerequisiteValidationFailed(List<Integer> prerequisites) {
		if (!prerequisites.isEmpty()) {
			for (Integer prerequisiteId : prerequisites) {
				boolean exists = completedPrerequisite(prerequisiteId);
				if (!exists) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean completedPrerequisite(Integer prerequisiteId) {
		boolean exists = false;
		for (Record record : getAllRecords()) {
			if (record.getCourseId() == prerequisiteId) {
				exists = true;
			}
		}
		return exists;
	}

}
