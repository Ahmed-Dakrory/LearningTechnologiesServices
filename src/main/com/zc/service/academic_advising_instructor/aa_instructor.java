package main.com.zc.service.academic_advising_instructor;


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
	
	
	@NamedQuery(name="aa_instructor.getAll",
		     query="SELECT c FROM aa_instructor c "
		     )
	,
	@NamedQuery(name="aa_instructor.getById",
	query = "from aa_instructor d where d.id = :id "
			)
	,
	@NamedQuery(name="aa_instructor.getByMailAndYearAndSemester",
	query = "from aa_instructor d where d.mail = :mail and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_instructor.getAllByYearAndSemester",
	query = "from aa_instructor d where d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	
	
})
 
@Entity
@Table(name = "taa_instructor")
public class aa_instructor {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	

	@Column(name = "mail")
	private String mail;
	

	
	@Column(name = "phone")
	private String phone;
	
	
	
	@Column(name = "year")
	private String year;
	

	@Column(name = "semester")
	private String semester;


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


	
	
}
