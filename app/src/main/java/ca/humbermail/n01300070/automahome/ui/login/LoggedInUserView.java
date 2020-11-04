package ca.humbermail.n01300070.automahome.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
	
	// Data fields that may be accessible to the UI
	private final String emailAddress;
	private final String firstName;
	private final String lastName;
	
	LoggedInUserView(String emailAddress, String firstName, String lastName) {
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	String getEmailAddress() {
		return emailAddress;
	}
	
	String getFirstName() {
		return firstName;
	}
	
	String getLastName() {
		return lastName;
	}
	
	String getFullName() {
		return firstName + " " + lastName;
	}
}