/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author momen
 *
 */
@NamedQueries({

	@NamedQuery(name = "Attachments.getAll", query = "SELECT a FROM Attachments a"),
	@NamedQuery(name = "Attachments.getById", query = "from Attachments a where a.id = :id"),
	 })

@Entity
@Table(name = "attachments")
public class Attachments {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	
	@Column(name="NAME")
	private String Name;
	
	
	
	@Column(name="CONTENTS")
	private Blob Contents;

	public Attachments() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Blob getContents() {
		return Contents;
	}

	public void setContents(Blob contents) {
		Contents = contents;
	}
	
	
}
