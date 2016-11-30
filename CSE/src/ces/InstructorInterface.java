package ces;

public interface InstructorInterface {
	public void teachCourse(Course course);
	
	public void manageCourse(Course course);
	
	public Record createAcademicRecord(Student student, Course course);
}
