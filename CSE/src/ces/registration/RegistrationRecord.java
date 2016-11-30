package ces.registration;

public class RegistrationRecord {
	@Override
	public String toString() {
		return "RegistrationRecord [studentId=" + studentId + ", courseId=" + courseId + ", request=" + request
				+ ", reason=" + reason + "]";
	}

	private Integer studentId;

	private Integer courseId;

	private RegistrationRequest request;

	private String reason;

	public RegistrationRecord(Integer studentId, Integer courseId, RegistrationRequest request) {
		this.studentId = studentId;
		this.courseId = courseId;
		this.request = request;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public RegistrationRequest getRequest() {
		return request;
	}

	public String getReason() {
		return reason;
	}

}