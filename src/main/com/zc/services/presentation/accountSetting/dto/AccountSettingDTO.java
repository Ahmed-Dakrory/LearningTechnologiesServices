/**
 * 
 */
package main.com.zc.services.presentation.accountSetting.dto;



public class AccountSettingDTO {

	private Integer id;
	private Integer instructorId;
	private boolean notifyMe;
	private int everyDay;
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private boolean instructorAccount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Integer instructorId) {
		this.instructorId = instructorId;
	}
	
	public void setNotifyMe(boolean notifyMe) {
		this.notifyMe = notifyMe;
	}
	public int getEveryDay() {
		return everyDay;
	}
	public void setEveryDay(int everyDay) {
		this.everyDay = everyDay;
	}
	public boolean getNotifyMe() {
		return notifyMe;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean getInstructorAccount() {
		return instructorAccount;
	}
	public void setInstructorAccount(boolean instructorAccount) {
		this.instructorAccount = instructorAccount;
	}
}
