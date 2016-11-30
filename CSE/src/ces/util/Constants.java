package ces.util;

public final class Constants {

	public static final String COMMAND = "$main: ";
	
	public static final String COMMAND_ROASTER = "$roster selection >";

	// Roaster commands
	public static final String DISPLAY = "display";
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	
	public static final String INSTRUCTOR_ALREADY_SELECTED = "instructor already selected to teach a course";


	// System commands
	public static final String DISPLAY_REQUEST = "display_requests";
	public static final String DISPLAY_RECORDS = "display_records";
	public static final String DISPLAY_SEATS = "display_seats";
	
	public static final String ADD_RECORD = "add_record";
	public static final String ADD_SEATS = "add_seats";
	public static final String CHECK_REQUEST = "check_request";
	
	public static final String QUIT = "quit";
	
	// System output messages
	public static final String REQ_INVALID_PREREQ = "> student is missing one or more prerequisites";
	public static final String REQ_INVALID_ALREADY_TAKEN  = "> student has already taken the course with a grade of C or higher";
	public static final String REQ_INVALID_NO_SEATS = "> no remaining seats available for the course at this time";
	
	public static final String REQ_VALID = "> request is valid";
	
	public static final String COMMAND_STOP_MSG = "> stopping the command loop";
	
	public static final String UNKNOWN_COMMAND = "> command not supported";
	
}
