package ces.registration;

import ces.util.Constants;

public class Registration {

	public static RegistrationRecord approve(Integer studentId, Integer courseId) {
		//System.out.println("Approved Course id " + courseId + " :: student id : " + studentId );
		RegistrationRecord validRequest = new RegistrationRecord(studentId, courseId,
				RegistrationRequest.APPROVED);
		validRequest.setReason(Constants.REQ_VALID);
		RegistrationManager.getInstance().addRegistrationRecord(validRequest);
		return validRequest;
	}

	public static RegistrationRecord deny(Integer studentId, Integer courseId, String reason) {
		//System.out.println("Denied Course id " + courseId + " :: student id : " + studentId + " :: " + reason);

		RegistrationRecord invalidRequest = new RegistrationRecord(studentId, courseId,
				RegistrationRequest.DENIED);
		invalidRequest.setReason(reason);
		//RegistrationManager.getInstance().addRegistrationRecord(invalidRequest);
		return invalidRequest;
	}
}