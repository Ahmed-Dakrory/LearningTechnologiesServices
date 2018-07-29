/**
 * 
 */
package main.com.zc.services.domain.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya
 * 
 */

@NamedQueries({
	
@NamedQuery(name = "Data.getAll", query = "SELECT d FROM Data d"),
@NamedQuery(name = "Data.getByMail", query = "SELECT d FROM Data d where LOWER(d.mail) = :mail")//update Like to = by ALAA

	})
@Entity
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "E_PERSON_NAME")
	private String NameInEnglish;

	@Column(name = "A_PERSON_NAME")
	private String NameInArabic;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "MAIL")
	private String mail;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name="IMAGE")
	@Lob
	private byte[] studentImage;
	
	/*
	 * @OneToOne(mappedBy = "data", cascade = CascadeType.ALL) private Person
	 * person;
	 */

	/*
	 * public Person getPerson() { return person; }
	 * 
	 * public void setPerson(Person person) { this.person = person; }
	 */
	public Data(String nameInEnglish, String nameInArabic, String mail) {
		super();
		NameInEnglish = nameInEnglish;
		NameInArabic = nameInArabic;
		this.mail = mail;
	}

	public Data(String nameInEnglish, String nameInArabic, String gender,
			String mail, String phone, String address) {
		super();
		NameInEnglish = nameInEnglish;
		NameInArabic = nameInArabic;
		this.gender = gender;
		this.mail = mail;
		this.phone = phone;
		this.address = address;
	}

	public Data() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameInEnglish() {
		return NameInEnglish;
	}

	public void setNameInEnglish(String nameInEnglish) {
		NameInEnglish = nameInEnglish;
	}

	public String getNameInArabic() {
		return NameInArabic;
	}

	public void setNameInArabic(String nameInArabic) {
		NameInArabic = nameInArabic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public byte[] getStudentImage() {
		return studentImage;
	}

	public void setStudentImage(byte[] studentImage) {
		this.studentImage = studentImage;
	}

}
