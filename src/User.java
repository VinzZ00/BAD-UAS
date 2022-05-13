
public class User {

	private String staffId, staffName, staffPhone, staffAddress, staffRole, supervisorId, staffUsername, staffPassword;

	public User(String staffId, String staffName, String staffPhone, String staffAddress, String staffRole,
			String supervisorId, String staffUsername, String staffPassword) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffPhone = staffPhone;
		this.staffAddress = staffAddress;
		this.staffRole = staffRole;
		this.supervisorId = supervisorId;
		this.staffUsername = staffUsername;
		this.staffPassword = staffPassword;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

	public String getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(String staffRole) {
		this.staffRole = staffRole;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getStaffUsername() {
		return staffUsername;
	}

	public void setStaffUsername(String staffUsername) {
		this.staffUsername = staffUsername;
	}

	public String getStaffPassword() {
		return staffPassword;
	}

	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}
	
	

}
