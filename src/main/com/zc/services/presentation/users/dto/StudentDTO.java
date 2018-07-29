/**
 * 
 */
package main.com.zc.services.presentation.users.dto;

/**
 * @author omnya
 *
 */
public class StudentDTO {

	private String name;
	private String mail;
	private Integer id;
	private Integer facultyId;
	private String phone;
	private StudentProfileDTO studentProfileDTO;
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public StudentProfileDTO getStudentProfileDTO() {
		return studentProfileDTO;
	}
	public void setStudentProfileDTO(StudentProfileDTO studentProfileDTO) {
		this.studentProfileDTO = studentProfileDTO;
	}

	
}
