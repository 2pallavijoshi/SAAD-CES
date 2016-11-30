package ces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CourseCatalog {

	private final static CourseCatalog INSTANCE = new CourseCatalog();
	private HashMap<Integer, Course> catalog;

	private CourseCatalog() {
		catalog = new HashMap<>();
	}

	public static CourseCatalog getInstance() {
		return INSTANCE;
	}

	// Get course by course id
	public Course getCourse(Integer courseId) {
		return catalog.get(courseId);
	}

	public HashMap<Integer, Course> getAllCourses() {
		return catalog;
	}

	public void createCourse(int id, String name, List<Semester> semester) {
		catalog.put(id, new Course(id, name, null, semester));
	}

	/**
	 * Use this method to add prerequisites and then update the course in the
	 * catalog
	 * 
	 * @param id
	 * @param course
	 */
	public void upateCourse(int id, Course course) {
		catalog.put(id, course);
	}

	public void removeCourse(int courseId) {
		// TODO - Remove course pre-conditions like if the course has
		// pre-requisite or associated with the record
		catalog.remove(courseId);
	}

	/**
	 * Returns the list of courses for requested semester
	 *
	 * @param semester
	 * @return List of Courses
	 */
	public List<Course> getCourses(String semester) {
		Set<Integer> courseIdKeys = catalog.keySet();
		List<Course> coursesBySem = new ArrayList<Course>();
		for (Integer courseId : courseIdKeys) {
			Course course = catalog.get(courseId);
			if (course == null) {
				continue;
			}
			for (Semester sem : course.getSemester()) {
				if (sem.getSession().equalsIgnoreCase(semester)) {
					coursesBySem.add(course);
				}
			}
		}
		return coursesBySem;
	}

}
