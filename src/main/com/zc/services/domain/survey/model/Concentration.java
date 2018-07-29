/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.petition.model.Majors;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "Concentration.getAll", query = "SELECT d FROM Concentration d"),
	@NamedQuery(name = "Concentration.getById", query = "from Concentration d where d.id = :id "),
	@NamedQuery(name = "Concentration.getByParentID", query = "from Concentration d where d.parent = :id ")

	
	})
@Entity
@Table(name = "concentration")
public class Concentration {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="NAME")
	private String name;

	/*@Column(name="PARENT")
	private Integer parent;*/

	@ManyToOne
	@JoinColumn(name = "PARENT")
	private Majors parent;
	
	
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


	public Majors getParent() {
		return parent;
	}


	public void setParent(Majors parent) {
		this.parent = parent;
	}


/*	public Integer getParent() {
		return parent;
	}


	public void setParent(Integer parent) {
		this.parent = parent;
	}*/
	
	
}
