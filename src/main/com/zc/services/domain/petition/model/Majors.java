/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Employee;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "Majors.getAll", query = "SELECT m FROM Majors m"),
	@NamedQuery(name = "Majors.getById", query = "from Majors m where m.id = :id"),
	@NamedQuery(name = "Majors.getByAllHidden", query = "from Majors m where m.hidden!=1"),
	@NamedQuery(name = "Majors.getByInsID", query = "from Majors m where m.headOfMajorId = :id"),
	
	 })

@Entity
@Table(name = "major")
public class Majors {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	
	@Column(name="NAME")
	private String majorName;
	
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HEAD")
	
	  private  Employee headOfMajorId;

	@Column(name="HIDDEN")
	private Boolean hidden;
	
	//For engineering/science
	@Column(name="TYPE")
    private Integer type;
    
	public Majors() {
		super();
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getMajorName() {
		return majorName;
	}



	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}



	public Employee getHeadOfMajorId() {
		return headOfMajorId;
	}



	public void setHeadOfMajorId(Employee headOfMajorId) {
		this.headOfMajorId = headOfMajorId;
	}



	public Boolean getHidden() {
		return hidden;
	}



	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	
	
	
	
}
