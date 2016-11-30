package ces;

public class Record {

	private static int idCounter = 0;
	private int id;
	private int studentId;
	private int courseId;
	private int instructorId;
	private String comments;
	private Grade grade;

	public int getCourseId() {
		return courseId;
	}

	public String getComments() {
		return comments;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public Grade getGrade() {
		return grade;
	}

	public int getStudentId() {
		return studentId;
	}

	public int getId() {
		return id;
	}

	public Record(int studentId, int courseId, int instructorId, String comments, Grade grade) {
		super();
		idCounter++;
		this.id = idCounter;
		this.studentId = studentId;
		this.courseId = courseId;
		this.instructorId = instructorId;
		this.comments = comments;
		this.grade = grade;
	}

	public boolean isRetakeAvailable() {
		if (Grade.D.equals(grade) || Grade.F.equals(grade)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean courseCompletedSuccessfully() {
		if (Grade.A.equals(grade) || Grade.B.equals(grade) || Grade.C.equals(grade) || Grade.D.equals(grade)) {
			return true;
		} else {
			return false;
		}
	}

	public void updateRecord(int id, Record record) {
		// TODO
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", courseId=" + courseId + ", instructorId=" + instructorId + ", comments="
				+ comments + ", grade=" + grade + "]";
	}

}
