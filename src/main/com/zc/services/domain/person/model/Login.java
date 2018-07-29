/**
 * 
 */
package main.com.zc.services.domain.person.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 *
 */

@NamedQueries({
	@NamedQuery(
	name = "Login.getLoginByPersonId",
	query = "from Login l where l.person.id = :personId"
	),
	@NamedQuery(
	name = "Login.getLoginByFileNo",
	query = "from Login l where l.person.fileNo = :fileNo"
	),
	 @NamedQuery(name="Login.getAll",
     query="SELECT l FROM Login l"
     )
	,
	 @NamedQuery(name="Login.getById",
    query="from Login l where l.id = :id"
    )
})
@Entity
@Table(name = "login")
public class Login {
	
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PASSWORD")
	private String password;
	
	@OneToOne
	private Student person;

	
	public Login(){}
	
	public Login(String name, String password, Student person) {
		super();
		this.name = name;
		this.password = password;
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Student getPerson() {
		return person;
	}

	public void setPerson(Student person) {
		this.person = person;
	}
}
