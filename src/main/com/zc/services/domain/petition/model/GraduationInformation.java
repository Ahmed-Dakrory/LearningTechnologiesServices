/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.survey.model.Concentration;

/**
 * @author omnya
 *
 */
@NamedQueries({
	
	@NamedQuery(
	name = "GraduationInformation.getFormByStudentID",
	query = "from GraduationInformation f where f.student.id = :id ORDER BY f.id DESC"
	),
	@NamedQuery(
	name = "GraduationInformation.getFormByStudentIDAndSemesterAndYear",
	query = "from GraduationInformation f where f.student.id = :id "
			+ "AND f.year = :year AND f.semester = :semester ORDER BY f.id DESC"
	),
	 @NamedQuery(name="GraduationInformation.getAll",
     query="SELECT f FROM GraduationInformation f ORDER BY f.id DESC"
     )
	,
	 @NamedQuery(name="GraduationInformation.getById",
    query="from GraduationInformation f where f.id = :id"
    ),
	 
	 @NamedQuery(name="GraduationInformation.getFormBySemesterAndYear",
	    query="from GraduationInformation f where "
			+ "f.year = :year AND f.semester = :semester ORDER BY f.id DESC"
	    )
	
})


@Entity
@Table(name = "graduation_information")
public class GraduationInformation {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="STUDENT_ID")
	private Student	student;
	
	@ManyToOne
	@JoinColumn(name="MAJOR")
	private Majors major;
	
	@ManyToOne
	@JoinColumn(name="MINOR")
	private Majors minor;

	@Column(name = "NID")
	private String NID;
	
	@Column(name = "BIRTH_PLACE")
	private String  birthPlace;
	
	@Column(name = "NATIONALITY")
	private String  nationality;
	
	@Column(name = "PHONE")
	private String  phone;
	
	@Column(name = "MOBILE")
	private String  mobile;
	
	@Column(name = "EMERGENCY_CONTACT_NAME")
	private String  emegencyConatactName;
	
	
	@Column(name = "EMERGENCY_RELATIONSHIP_WITH_GRADUATE")
	private String  emegencyRelationship;
	
	@Column(name = "EMERGENCY_MOBILE_NO")
	private String  emegencyMobile;
	
	@ManyToOne
	@JoinColumn(name="CONCENTRATION")
	private Concentration  concentration;
	
	
	@Column(name = "ATTEND_CERMONY")
	private Boolean  attend;
	
	@Column(name = "HEIGHT")
	private Integer  hight;
	
	@Column(name = "SIZE")
	private String  size;
	
	@Column(name = "SEMESTER")
	private Integer semester;
	
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar date;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ARABIC_NAME")
	private String arabicName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "UPDATE_DATE")
	private Calendar updateDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Majors getMajor() {
		return major;
	}

	public void setMajor(Majors major) {
		this.major = major;
	}

	public Majors getMinor() {
		return minor;
	}

	public void setMinor(Majors minor) {
		this.minor = minor;
	}


	public String getNID() {
		return NID;
	}

	public void setNID(String nID) {
		NID = nID;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmegencyConatactName() {
		return emegencyConatactName;
	}

	public void setEmegencyConatactName(String emegencyConatactName) {
		this.emegencyConatactName = emegencyConatactName;
	}

	public String getEmegencyRelationship() {
		return emegencyRelationship;
	}

	public void setEmegencyRelationship(String emegencyRelationship) {
		this.emegencyRelationship = emegencyRelationship;
	}

	public String getEmegencyMobile() {
		return emegencyMobile;
	}

	public void setEmegencyMobile(String emegencyMobile) {
		this.emegencyMobile = emegencyMobile;
	}

	public Concentration getConcentration() {
		return concentration;
	}

	public void setConcentration(Concentration concentration) {
		this.concentration = concentration;
	}

	public Boolean getAttend() {
		return attend;
	}

	public void setAttend(Boolean attend) {
		this.attend = attend;
	}

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
