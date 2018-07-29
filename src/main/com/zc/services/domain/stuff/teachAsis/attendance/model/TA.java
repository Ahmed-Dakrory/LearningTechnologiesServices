/**
 * 
 */
package main.com.zc.services.domain.stuff.teachAsis.attendance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({
	
	 @NamedQuery(name="TA.getAll",
     query="SELECT ta FROM TA ta"
     )
	,
	 @NamedQuery(name="TA.getById",
    query="from TA ta where ta.id = :id"
    ),
    @NamedQuery(name="TA.getByFacultyID",
    query="from TA ta where ta.facultyID = :facultyID"
    )
})
@Entity
@Table(name = "tas")
public class TA {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DEPARTMENT")
	private String dept;

	@Column(name = "FACULTY_ID")
	private String facultyID;
	public TA() {
		super();
	}


	public TA(String name, String dept) {
		super();
		this.name = name;
		this.dept = dept;
	}

	
	public TA(String name, String dept, String facultyID) {
		super();
		this.name = name;
		this.dept = dept;
		this.facultyID = facultyID;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getFacultyID() {
		return facultyID;
	}


	public void setFacultyID(String facultyID) {
		this.facultyID = facultyID;
	}
	
		

}
