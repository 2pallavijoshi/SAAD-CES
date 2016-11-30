package ces.registration;

import java.util.ArrayList;
import java.util.List;

public class RegistrationManager {

	private final static RegistrationManager INSTANCE = new RegistrationManager();

	private List<RegistrationRecord> registrations;

	public static RegistrationManager getInstance() {
		return INSTANCE;
	}

	public void addRegistrationRecord(RegistrationRecord record) {
		if (registrations == null) {
			registrations = new ArrayList<RegistrationRecord>();
		}
		registrations.add(record);
	}

	public List<RegistrationRecord> getAllRecords() {
		return registrations;
	}
}
