/**
 * 
 */
package main.com.zc.services.domain.model.heads;

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

	@NamedQuery(name = "Heads.getAll", query = "SELECT m FROM Heads m"),
	@NamedQuery(name = "Heads.getById", query = "from Heads m where m.id = :id"),
	@NamedQuery(name = "Heads.getByType", query = "from Heads m where m.type = :type"),
	@NamedQuery(name = "Heads.getByAllHidden", query = "from Heads m where m.hidden!=1"),
	@NamedQuery(name = "Heads.getByEmployeeID", query = "from Heads m where m.headPersonId = :id"),
	
	 })

@Entity
@Table(name = "heads")
public class Heads {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	
	
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HEAD")
	
	  private  Employee headPersonId;

	@Column(name="HIDDEN")
	private Boolean hidden;
	
	//For Accredetion or director or admission office 
	/*
	 * 0 for the head of the Zewail city university
	 * 1 for the addmision office manager
	 * 2 for Accredition for Engineering Departments
	 * 3 for Accredition for Science Departments
	 * 4 for The Dean of students affairs
	 */
	@Column(name="departmentType")
    private Integer type;
    
	public Heads() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Employee getHeadPersonId() {
		return headPersonId;
	}

	public void setHeadPersonId(Employee headPersonId) {
		this.headPersonId = headPersonId;
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
