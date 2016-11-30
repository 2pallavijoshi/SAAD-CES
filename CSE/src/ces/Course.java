package ces;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private int courseId;
	private String courseName;
	private String courseDescription;
	private List<Semester> semester;

	private int totalCapacity = 0;
	private int occupiedCount = 0;
	private List<Integer> prerequisite;

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public void addCourseCapacity(Integer capacity) {
		totalCapacity = totalCapacity + capacity;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void allocateSeat() {
		// System.out.println("Allocating 1 seat to course - " + courseId);
		occupiedCount++;
	}

	public boolean isAvailable() {
		return getAvailableCapacity() > 0 ? true : false;
	}

	public Integer getAvailableCapacity() {
		return getTotalCapacity() - occupiedCount;
	}

	public Course(int courseId, String courseName, String description, List<Semester> semester) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.semester = semester;
		this.courseDescription = description;
	}

	public void addPrerequisite(Integer preReqCourseId) {
		if (prerequisite == null) {
			prerequisite = new ArrayList<Integer>();
		}
		prerequisite.add(preReqCourseId);
	}

	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public List<Semester> getSemester() {
		return semester;
	}

	public List<Integer> getPrerequisites() {
		if (prerequisite == null) {
			prerequisite = new ArrayList<Integer>();
		}
		return prerequisite;
	}

}
