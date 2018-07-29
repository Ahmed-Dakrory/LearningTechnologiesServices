/**
 * 
 */
package main.com.zc.services.domain.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author Omnya Alaa
 * 
 */
@NamedQueries({
		@NamedQuery(name = "LoginData.getAll", query = "SELECT l FROM LoginData l"),
		@NamedQuery(name = "LoginData.getById", query = "from LoginData l where l.id = :id"),
		@NamedQuery(name = "LoginData.getByMail", query = "from LoginData l where LOWER(l.mail) = :mail") })//update Like to = by ALAA
@Entity
@Table(name = "login_data")
public class LoginData {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="MAIL")
	private String mail;
	
	@Column(name="PASSWORD")
	private String password;

	
	public LoginData() {
		super();
	}

	public LoginData(String name, String mail, String password) {
		super();
		this.name = name;
		this.mail = mail;
		this.password = password;
	}

	public LoginData(int id, String name, String mail, String password) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password =new  Md5PasswordEncoder().encodePassword(password, mail);
	}
	
}
