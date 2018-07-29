/**
 * 
 */
package main.com.zc.shared.presentation.dto;

/**
 * @author Omnya Alaa
 *
 */
public class LoginStaffDTO {
private int id;
private String name;
private String mail;
private String password;
private int fileNo;
public LoginStaffDTO() {
	super();
}
public LoginStaffDTO(int id, String name, String mail, String password) {
	super();
	this.id = id;
	this.name = name;
	this.mail = mail;
	this.password = password;
}
public LoginStaffDTO(String name, String mail, String password) {
	super();
	this.name = name;
	this.mail = mail;
	this.password = password;
}
public LoginStaffDTO(String mail, String password) {
	super();
	this.mail = mail;
	this.password = password;
}

public LoginStaffDTO(int id, String name, String mail, String password,
		int fileNo) {
	super();
	this.id = id;
	this.name = name;
	this.mail = mail;
	this.password = password;
	this.fileNo = fileNo;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public int getFileNo() {
	return fileNo;
}
public void setFileNo(int fileNo) {
	this.fileNo = fileNo;
}

}
