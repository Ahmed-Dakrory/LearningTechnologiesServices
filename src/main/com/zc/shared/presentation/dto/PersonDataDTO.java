/**
 * 
 */
package main.com.zc.shared.presentation.dto;

/**
 * @author Omnya
 *
 */

public class PersonDataDTO {
public PersonDataDTO() {
		//super();
	}
private int fileNo;
private String nameInEng;
private String nameInAra;
private String email;
private int id;
private Integer levelID;

public PersonDataDTO(String nameInEng, String email, int id) { // for instructors
	super();
	this.nameInEng = nameInEng;
	email = email;
	this.id = id;
}

public PersonDataDTO(int fileNo, String nameInEng, String nameInAra,
		String email) {
	super();
	this.fileNo = fileNo;
	this.nameInEng = nameInEng;
	this.nameInAra = nameInAra;
	email = email;
}

public PersonDataDTO(int fileNo, String nameInEng, String nameInAra) {
	super();
	this.fileNo = fileNo;
	this.nameInEng = nameInEng;
	this.nameInAra = nameInAra;
}

public PersonDataDTO(int fileNo, String nameInEng, String nameInAra, int id) {
	super();
	this.fileNo = fileNo;
	this.nameInEng = nameInEng;
	this.nameInAra = nameInAra;
	this.id = id;
}


public PersonDataDTO(int fileNo, String nameInEng, String nameInAra,
		String email, int id) {
	super();
	this.fileNo = fileNo;
	this.nameInEng = nameInEng;
	this.nameInAra = nameInAra;
	email = email;
	this.id = id;
}

public int getFileNo() {
	return fileNo;
}
public void setFileNo(int fileNo) {
	this.fileNo = fileNo;
}
public String getNameInEng() {
	return nameInEng;
}
public void setNameInEng(String nameInEng) {
	this.nameInEng = nameInEng;
}
public String getNameInAra() {
	return nameInAra;
}
public void setNameInAra(String nameInAra) {
	this.nameInAra = nameInAra;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Integer getLevelID() {
	return levelID;
}

public void setLevelID(Integer levelID) {
	this.levelID = levelID;
}

}
