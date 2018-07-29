/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;

import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class GraduationInformationDTO {
	private Integer id;
	
	private StudentDTO	student;
	
	private MajorDTO major=new MajorDTO();
	
	private MajorDTO minor=new MajorDTO();

	private String nID;
	
	private String  birthPlace;
	
	private String  nationality;
	
	private String  phone;
	
	private String  mobile;
	
	private String  emegencyConatactName;
		
	private String  emegencyRelationship;
	
	private String  emegencyMobile;
	
	private BaseDTO  concentration=new BaseDTO();
		
	private Boolean  attend;
	
	private Integer  hight;
	
	private String  size;

	private Integer semester;
	
	private Integer year;
	
	private Calendar date;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String arabicName;
	
	private String address;
	
	private Calendar updateDate;
	
	@SuppressWarnings("unused")
	private String friendlySubmissionDate;
	@SuppressWarnings("unused")
	private String friendlyUpdateDate;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public MajorDTO getMinor() {
		return minor;
	}

	public void setMinor(MajorDTO minor) {
		this.minor = minor;
	}


	public String getnID() {
		return nID;
	}

	public void setnID(String nID) {
		this.nID = nID;
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

	public BaseDTO getConcentration() {
		return concentration;
	}

	public void setConcentration(BaseDTO concentration) {
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

	public String getFriendlySubmissionDate() {
		if(getDate()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getDate().getTime());
			    return strDate;
			}
			
			else return "";
	}

	public void setFriendlySubmissionDate(String friendlySubmissionDate) {
		this.friendlySubmissionDate = friendlySubmissionDate;
	}

	public String getFriendlyUpdateDate() {
		if(getUpdateDate()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getUpdateDate().getTime());
			    return strDate;
			}
			
			else return "";
	}

	public void setFriendlyUpdateDate(String friendlyUpdateDate) {
		this.friendlyUpdateDate = friendlyUpdateDate;
	}

	
	
}
