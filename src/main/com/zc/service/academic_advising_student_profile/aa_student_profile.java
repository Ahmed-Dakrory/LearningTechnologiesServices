package main.com.zc.service.academic_advising_student_profile;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="aa_student_profile.getAll",
		     query="SELECT c FROM aa_student_profile c "
		     )
	,
	@NamedQuery(name="aa_student_profile.getById",
	query = "from aa_student_profile d where d.id = :id "
			)
	,
	@NamedQuery(name="aa_student_profile.getByMailAndYearAndSemester",
	query = "from aa_student_profile d where d.mail = :mail and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_student_profile.getAllByYearAndSemester",
	query = "from aa_student_profile d where d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	
	
})
 
@Entity
@Table(name = "taa_student_profile")
public class aa_student_profile {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	

	@Column(name = "mail")
	private String mail;
	
	
	@Column(name = "zewailcity_id")
	private String zewailcity_id;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	
	@Column(name = "gpa")
	private String gpa;

	
	@Column(name = "major")
	private String major;

	
	@Column(name = "concentration")
	private String concentration;

	
	@Column(name = "minor")
	private String minor;

	
	
	@Column(name = "year")
	private String year;
	

	@Column(name = "semester")
	private String semester;


	
	@Column(name = "studentMessege")
	private String studentMessege;
	

	@Column(name = "instructorMessege")
	private String instructorMessege;
	

	@Column(name = "comments")
	private String comments;
	

	@Column(name = "recommendations")
	private String recommendations;


	@Column(name = "dateStudentLastAction")
	private Date dateStudentLastAction;
	
	

	@Column(name = "datelastComment")
	private Date datelastComment;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getZewailcity_id() {
		return zewailcity_id;
	}


	public void setZewailcity_id(String zewailcity_id) {
		this.zewailcity_id = zewailcity_id;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getGpa() {
		return gpa;
	}


	public void setGpa(String gpa) {
		this.gpa = gpa;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getConcentration() {
		return concentration;
	}


	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}


	public String getMinor() {
		return minor;
	}


	public void setMinor(String minor) {
		this.minor = minor;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStudentMessege() {
		return studentMessege;
	}


	public void setStudentMessege(String studentMessege) {
		this.studentMessege = studentMessege;
	}


	public String getInstructorMessege() {
		return instructorMessege;
	}


	public void setInstructorMessege(String instructorMessege) {
		this.instructorMessege = instructorMessege;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getRecommendations() {
		return recommendations;
	}


	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}


	public Date getDateStudentLastAction() {
		return dateStudentLastAction;
	}


	public void setDateStudentLastAction(Date dateStudentLastAction) {
		this.dateStudentLastAction = dateStudentLastAction;
	}


	public Date getDatelastComment() {
		return datelastComment;
	}


	public void setDatelastComment(Date datelastComment) {
		this.datelastComment = datelastComment;
	}

	
	
	
}
