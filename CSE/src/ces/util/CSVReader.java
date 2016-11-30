package ces.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ces.Course;
import ces.CourseCatalog;
import ces.Grade;
import ces.Instructor;
import ces.Record;
import ces.Semester;
import ces.Student;
import ces.jdbc.JdbcConnector;
import ces.registration.RegistrationRecord;

public class CSVReader {

	// private static final String path = ".";
	private static final String path = "/Users/pajoshi/Documents/Pallavi_Personal/OMSCS6310/assignment-8/workspace/CSE/testcase_1";
	private static String csvDelimiter = ",";

	public CSVReader() {
	}

	// Returns Students with loaded records.
	public HashMap<Integer, Student> loadRecords(HashMap<Integer, Student> students) {
		if (students == null || students.isEmpty()) {
			System.out.println("Error: Please load students to process students records.");
		}
		String line;
		String recordsCsv = path + "/records.csv";
		try (BufferedReader br = new BufferedReader(new FileReader(recordsCsv))) {
			while ((line = br.readLine()) != null) {
				String[] csvRecord = line.split(csvDelimiter);
				Student student = null;
				student = students.get(Integer.parseInt(csvRecord[0]));
				List<Record> records = student.getAllRecords();
				records.add(new Record(Integer.parseInt(csvRecord[0]), Integer.parseInt(csvRecord[1]),
						Integer.parseInt(csvRecord[2]), csvRecord[3], Grade.valueOf(csvRecord[4])));
				student.setRecords(records);
				students.put(student.getUid(), student);
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load records.csv file.");
			e.printStackTrace();
		}
		return students;
	}

	public CourseCatalog loadCoursesInCatalog(CourseCatalog catalog) {
		String courseCsv = path + "/courses.csv";

		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(courseCsv))) {
			while ((line = br.readLine()) != null) {
				String[] courseListing = line.split(csvDelimiter);
				List<Semester> semesters = new ArrayList<>();

				if (courseListing.length > 2) {
					int i = 2;
					while (i < courseListing.length) {
						semesters.add(new Semester(courseListing[i]));
						i++;
					}
				}
				catalog.createCourse(Integer.parseInt(courseListing[0]), courseListing[1], semesters);
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load courses.csv file.");
			e.printStackTrace();
		}
		return catalog;
	}

	public void loadPrerequisite() {
		CourseCatalog catalog = CourseCatalog.getInstance();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/prereqs.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] prereqLine = line.split(csvDelimiter);
				Course course = catalog.getCourse(Integer.parseInt(prereqLine[1]));
				course.addPrerequisite(Integer.parseInt(prereqLine[0]));
				catalog.upateCourse(course.getCourseId(), course);
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load prereqs.csv file.");
			e.printStackTrace();
		}
	}

	// Old Implementation - Might want to delete this method.
	public void loadCourseAssignments() {
		CourseCatalog catalog = CourseCatalog.getInstance();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/assignments_1.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] prereqLine = line.split(csvDelimiter);
				Course course = catalog.getCourse(Integer.parseInt(prereqLine[1]));
				course.addCourseCapacity(Integer.parseInt(prereqLine[2]));
				catalog.upateCourse(course.getCourseId(), course);
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load assignments.csv file.");
			e.printStackTrace();
		}
	}

	public void loadAssignments(int semesterCode) {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/assignments_1.csv"))) {
			int index = 0;
			while ((line = br.readLine()) != null) {
				String[] prereqLine = line.split(csvDelimiter);
				StringBuffer insetAssignmentSqlsb = new StringBuffer(
						"INSERT INTO course_enrollment_system.assignments(instructor_id,course_id,capacity,semester_code, display_index) VALUES (");
				insetAssignmentSqlsb.append(prereqLine[0]).append(", ");
				insetAssignmentSqlsb.append(prereqLine[1]).append(", ");
				insetAssignmentSqlsb.append(prereqLine[2]).append(", ");
				insetAssignmentSqlsb.append(semesterCode).append(", ");
				insetAssignmentSqlsb.append(index++).append(");");

				// 1. Instructor_id
				// 2. Course_id
				// 3. Capacity
				// 4. semester code
				JdbcConnector mysqlConn = new JdbcConnector();
				try {
					System.out.println(insetAssignmentSqlsb.toString());
					mysqlConn.execute(insetAssignmentSqlsb.toString());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					System.out.println("Error while saving assignments in code - " + semesterCode);
				}
				
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load assignments.csv file.");
			e.printStackTrace();
		}
	}

	public List<RegistrationRecord> loadRequests(HashMap<Integer, Student> students) {
		List<RegistrationRecord> records = new ArrayList<>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/requests_1.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] requests = line.split(csvDelimiter);
				Student student = students.get(Integer.parseInt(requests[0]));
				records.add(student.registerCourse(Integer.parseInt(requests[1])));
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load students.csv file.");
			e.printStackTrace();
		}
		return records;
	}

	public HashMap<Integer, Student> loadStudents() {
		HashMap<Integer, Student> students = new HashMap<Integer, Student>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/students.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] studentLine = line.split(csvDelimiter);
				Student student = new Student(Integer.parseInt(studentLine[0]), studentLine[1], studentLine[2],
						studentLine[3]);
				students.put(student.getUid(), student);
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to load students.csv file.");
			e.printStackTrace();
		}
		return students;
	}

	public HashMap<Integer, Instructor> loadInstructor() {
		HashMap<Integer, Instructor> instructors = new HashMap<Integer, Instructor>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/instructors.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] instructorLine = line.split(csvDelimiter);
				Instructor instructor = new Instructor(Integer.parseInt(instructorLine[0]), instructorLine[1],
						instructorLine[2], instructorLine[3]);
				instructors.put(instructor.getUid(), instructor);
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error: Failed to load instructors.csv file.");
			e.printStackTrace();
		}
		return instructors;
	}

	public List<Instructor> loadInstructorList() {
		List<Instructor> instructors = new ArrayList<Instructor>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path + "/instructors.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] instructorLine = line.split(csvDelimiter);
				Instructor instructor = new Instructor(Integer.parseInt(instructorLine[0]), instructorLine[1],
						instructorLine[2], instructorLine[3]);
				instructors.add(instructor);
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error: Failed to load instructors.csv file.");
			e.printStackTrace();
		}
		return instructors;
	}

}
