package run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ces.Course;
import ces.CourseCatalog;
import ces.Grade;
import ces.Instructor;
import ces.Record;
import ces.Student;
import ces.jdbc.JdbcConnector;
import ces.registration.RegistrationManager;
import ces.registration.RegistrationRecord;
import ces.registration.RegistrationRequest;
import ces.util.CSVReader;
import ces.util.Constants;

public class CourseEnrollmentSystem {

	private static final String ASSIGNMENT_N = "N";

	private static final String ASSIGNMENT_Y = "Y";

	// TODO - No requirements on budget yet
	private Double budget = 0.0;

	// The upcoming semester is designated. In initial mode,
	// your system should always begin with cycle 1.
	private int semesterCode = 1;

	HashMap<Integer, Student> students;
	HashMap<Integer, Instructor> instructors;
	List<Instructor> instructorsList;

	CourseCatalog catalog;
	RegistrationManager registrationManager;

	public CourseEnrollmentSystem() {
	}

	public static void main(String[] args) {
		CourseEnrollmentSystem cms = new CourseEnrollmentSystem();
		cms.init();
	}

	public void enrollStudent(Student student) {
		// TODO Auto-generated method stub

	}

	public void disEnrollStudent(Student student) {
		// TODO Auto-generated method stub
	}

	public void createCourseCatalog() {
		catalog = CourseCatalog.getInstance();
	}

	public void hireInstructor(Instructor instructor, Course course) {
		// TODO Auto-generated method stub

	}

	private void init() {
		// Clean up activity
		new JdbcConnector().cleanup();

		CSVReader reader = new CSVReader();
		createCourseCatalog();
		registrationManager = RegistrationManager.getInstance();

		students = reader.loadStudents();
		instructors = reader.loadInstructor();
		students = reader.loadRecords(students);

		reader.loadCoursesInCatalog(catalog);
		reader.loadPrerequisite();
		// reader.loadCourseAssignments();
		System.out.println("Using init mode::");
		reader.loadAssignments(semesterCode);

		List<RegistrationRecord> records = reader.loadRequests(students);

		int validRequests = 0;
		int invalidPrereq = 0;
		int invalidTaken = 0;
		int invalidNoSeats = 0;
		for (RegistrationRecord registrationRecord : records) {
			if (registrationRecord.getRequest().equals(RegistrationRequest.APPROVED)) {
				validRequests++;
			} else if (registrationRecord.getRequest().equals(RegistrationRequest.DENIED)
					& registrationRecord.getReason().equalsIgnoreCase(Constants.REQ_INVALID_PREREQ)) {
				invalidPrereq++;
			} else if (registrationRecord.getRequest().equals(RegistrationRequest.DENIED)
					& registrationRecord.getReason().equalsIgnoreCase(Constants.REQ_INVALID_ALREADY_TAKEN)) {
				invalidTaken++;
			} else if (registrationRecord.getRequest().equals(RegistrationRequest.DENIED)
					& registrationRecord.getReason().equalsIgnoreCase(Constants.REQ_INVALID_NO_SEATS)) {
				invalidNoSeats++;
			}
		}

		System.out.println(records.size());
		System.out.println(validRequests);
		System.out.println(invalidPrereq);
		System.out.println(invalidTaken);
		System.out.println(invalidNoSeats);

		instructorsList = reader.loadInstructorList();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.print(Constants.COMMAND_ROASTER);
				String input = br.readLine();

				// Control flow command
				if (Constants.QUIT.equals(input)) {
					System.out.println(Constants.COMMAND_STOP_MSG);
					System.exit(0);
				}

				// Display-based commands
				if (Constants.DISPLAY.equals(input)) {
					display();
				} else if (input.isEmpty()) {
					// Do nothing
				} else if (input.contains(",")) {
					String[] inputArgs = input.split(",");
					if (Constants.ADD.equals(inputArgs[0])) {
						hireInstructor(Integer.parseInt(inputArgs[1]));
					} else if (Constants.DELETE.equals(inputArgs[0])) {
						deleteHiredInstructor(Integer.parseInt(inputArgs[1]));
					} else {
						// Unknown
						System.out.println(Constants.UNKNOWN_COMMAND);
					}
				} else {
					// Unknown
					System.out.println(Constants.UNKNOWN_COMMAND);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteHiredInstructor(int index) {
		int semester_code = 1;
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;
		try {
			conn = connector.getConnection();
			java.sql.Statement stmt = conn.createStatement();

			String selectInstructorIdSql = "Select id, instructor_id from assignments "
					+ "where semester_code = 1 and display_index = " + index + ";";
			ResultSet selectInstructorIdRs = stmt.executeQuery(selectInstructorIdSql);
			int instructorId = 0;
			int id = 0;
			if (selectInstructorIdRs.next()) {
				instructorId = selectInstructorIdRs.getInt("instructor_id");
				id = selectInstructorIdRs.getInt("id");
			}
			System.out.println("Instructor id is - " + instructorId);

			PreparedStatement updatMap = conn.prepareStatement("UPDATE assignments SET assigned = 'N' WHERE id = ?");
			updatMap.setInt(1, id);
			System.out.println("execute upate - " + updatMap.executeUpdate());
			updatMap.close();

			PreparedStatement deleteMap = conn.prepareStatement(
					"Delete from semester_instructor_map where semester_code = ? and instructor_id = ?");
			deleteMap.setInt(1, semester_code);
			deleteMap.setInt(2, instructorId);
			System.out.println("execute delete - " + deleteMap.executeUpdate());
			deleteMap.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connector.closeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void hireInstructor(int index) {

		// TODO Auto-generated method stub
		System.out.println("Hire instructor called...");
		// get current semester code
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;
		try {
			conn = connector.getConnection();
			java.sql.Statement stmt = conn.createStatement();

			String selectInstructorIdSql = "Select id, instructor_id from assignments "
					+ "where semester_code = 1 and display_index = " + index + ";";

			ResultSet selectInstructorIdRs = stmt.executeQuery(selectInstructorIdSql);
			int instructorId = 0;
			int id = 0;
			if (selectInstructorIdRs.next()) {
				instructorId = selectInstructorIdRs.getInt("instructor_id");
				id = selectInstructorIdRs.getInt("id");
			}
			System.out.println("Instructor id is - " + instructorId);

			String sql = "SELECT * From semester_instructor_map where semester_code = 1 and instructor_id = "
					+ instructorId + ";";
			System.out.println("SQL - " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println(Constants.INSTRUCTOR_ALREADY_SELECTED);
			} else {
				System.out.println("Instructor not selected yet...");
				PreparedStatement insertMap = conn.prepareStatement(
						"INSERT INTO semester_instructor_map(semester_code, instructor_id) VALUES (?,?);");
				insertMap.setInt(1, 1);
				insertMap.setInt(2, instructorId);
				System.out.println("execute insert - " + insertMap.executeUpdate());
				insertMap.close();

				PreparedStatement updatMap = conn
						.prepareStatement("UPDATE assignments SET assigned = 'Y' WHERE id = ?");
				updatMap.setInt(1, id);
				System.out.println("execute upate - " + updatMap.executeUpdate());
				updatMap.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connector.closeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Get the record from UI
		// Hire!!!

	}

	private void checkRequest(String[] inputArgs) {
		if (inputArgs.length != 3 || inputArgs[1] == null || inputArgs[2] == null) {
			System.out.println("Please use \"check_request,<Student id>,<Course id>\" command.");
			return;
		}
		Student student = students.get(Integer.parseInt(inputArgs[1]));
		RegistrationRecord record = student.registerCourse(Integer.parseInt(inputArgs[2]));
		System.out.println(record.getReason());

	}

	private void addSeats(String[] inputArgs) {
		if (inputArgs.length != 3 || inputArgs[1] == null || inputArgs[2] == null) {
			System.out.println("Please use \"add_seats,<Course id>,<Number of seats>\" command.");
			return;
		}

		try {
			Course course = catalog.getCourse(Integer.parseInt(inputArgs[1]));
			course.addCourseCapacity(Integer.parseInt(inputArgs[2]));
			catalog.upateCourse(course.getCourseId(), course);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addRecords(String[] inputArgs) {
		if (inputArgs.length == 5 || inputArgs[1] == null || inputArgs[2] == null || inputArgs[3] == null
				|| inputArgs[4] == null) {
			System.out.println(
					"Please use \"add_records,<Student id>,<Course id>,<Instructor id>,<comments>,<grade>\" command.");
			return;
		}
		Student student = students.get(Integer.parseInt(inputArgs[1]));
		List<Record> records = student.getAllRecords();
		records.add(new Record(Integer.parseInt(inputArgs[1]), Integer.parseInt(inputArgs[2]),
				Integer.parseInt(inputArgs[3]), inputArgs[4], Grade.valueOf(inputArgs[5])));
		student.setRecords(records);
		students.put(student.getUid(), student);
	}

	private void displaySeats() {
		for (Integer courseId : catalog.getAllCourses().keySet()) {
			Course course = catalog.getCourse(courseId);
			System.out.println(
					course.getCourseId() + ", " + course.getCourseName() + ", " + course.getAvailableCapacity());
		}
		;

	}

	private void displayRecords() {
		Set<Integer> studentIdKeys = students.keySet();
		List<Record> allRecords = new ArrayList<>();
		for (Integer studentId : studentIdKeys) {
			if (students.get(studentId) != null && students.get(studentId).getAllRecords().size() > 0) {
				allRecords.addAll(students.get(studentId).getAllRecords());
			}
		}
		Collections.sort(allRecords, new RecordComparator());
		for (Record record : allRecords) {
			System.out.println(record.getStudentId() + ", " + record.getCourseId() + ", " + record.getInstructorId()
					+ ", " + record.getComments() + ", " + record.getGrade().name());
		}
	}

	private void display() {
		try {
			displayAssignment(ASSIGNMENT_Y);
			displayAssignment(ASSIGNMENT_N);
		} catch (SQLException e) {
			System.out.println("error in display() method.");
			e.printStackTrace();
		}
	}

	private void displayAssignment(String assigned) throws SQLException {
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;
		try {
			conn = connector.getConnection();
			java.sql.Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					"Select * from assignments where semester_code = 1 and assigned = '" + assigned + "'");

			if (ASSIGNMENT_Y.equals(assigned)) {
				System.out.println("% --- selected ---");

			} else {
				System.out.println("% --- unselected ---");

			}
			while (result.next()) {
				System.out.println(result.getInt("display_index") + ": " + result.getInt("instructor_id") + ", "
						+ result.getInt("course_id") + ", " + result.getInt("capacity"));
			}
			result.close();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error while extracting selectd assignments.");
			e.printStackTrace();
		} finally {
			connector.closeConnection(conn);
		}

	}

	private class RecordComparator implements Comparator<Record> {
		@Override
		public int compare(Record a, Record b) {
			return a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1;
		}
	}
}
