/**
 * 
 */
package main.com.zc.services.domain.person.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.MailSetting;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
/**
 * @author Omnya Alaa
 * 
 */
@NamedQueries({
		@NamedQuery(name = "Employee.getAll", query = "SELECT i FROM Employee i where i.type=1"),
		@NamedQuery(name = "Employee.getAllEmp", query = "SELECT i FROM Employee i ORDER BY i.type ASC"),
		@NamedQuery(name = "Employee.getById", query = "from Employee i where i.id = :id"),
		@NamedQuery(name = "Employee.getByName", query = "from Employee i where i.name = :name"),
		@NamedQuery(name = "Employee.getByMail", query = "from Employee i where LOWER(i.mail) LIKE :mail"),
		@NamedQuery(name = "Employee.getAllTas", query = "SELECT i FROM Employee i where i.type=2"),
		@NamedQuery(name = "Employee.getByType", query = "SELECT i FROM Employee i where i.type= :type")})
@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name="MAIL")
	private String mail;
	
	
	@Column(name="TYPE")
	private Integer type; // 0:ins , 1:TA , 2:books Sys
	
	@Column(name="EMP_ID")
	private String empID;
	
	@Column(name="TITLE")
	private String title;
	
	
	@Column(name="MOBILE")
	private String phone;
	
	@Column(name="IMAGE")
	@Lob
	private byte[] image;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor", cascade=CascadeType.ALL)
    private List<Courses_Instructors> courseInstructor=new ArrayList<Courses_Instructors>();
	
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER,optional=false)
    @JoinColumn(name="MAIL_SETTING_ID", nullable=false)
    private MailSetting mailSetting;
    
    public Employee() {
		super();
	}

	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Employee(String name) {
		super();
		this.name = name;
	}

	public Employee(Integer id, String name, String mail,
			List<Courses_Instructors> courseInstructor) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.courseInstructor = courseInstructor;
	}

	public Employee(Integer id, String name, String mail) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
	}

	public Employee(String name, String mail) {
		super();
		this.name = name;
		this.mail = mail;
	}

	public Employee(String name, List<Courses_Instructors> courseInstructor) {
		super();
		this.name = name;
		this.courseInstructor = courseInstructor;
	}

	public Employee(Integer id, String name,
			List<Courses_Instructors> courseInstructor) {
		super();
		this.id = id;
		this.name = name;
		this.courseInstructor = courseInstructor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Courses_Instructors> getCourseInstructor() {
		return courseInstructor;
	}

	public void setCourseInstructor(List<Courses_Instructors> courseInstructor) {
		this.courseInstructor = courseInstructor;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public MailSetting getMailSetting() {
		return mailSetting;
	}

	public void setMailSetting(MailSetting mailSetting) {
		this.mailSetting = mailSetting;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	

}
