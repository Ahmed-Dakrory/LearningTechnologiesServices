/**
 * 
 */
package main.com.zc.services.domain.elections.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "ElectionCandidate.getCandidateByType",
	query = "from ElectionCandidate c where c.candidateType.id = :id"
	),
	
	 @NamedQuery(name="ElectionCandidate.getAll",
     query="SELECT c FROM ElectionCandidate c"
     )
	,
	 @NamedQuery(name="ElectionCandidate.getById",
    query="from ElectionCandidate c where c.id = :id"
    )
})
@Entity
@Table(name = "election_candidate")
public class ElectionCandidate {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "FACULTY_ID")
	private Integer facultyID;
	
	@Column(name="IMAGE")
	private String image;
	
	@ManyToOne
	@JoinColumn(name="TYPE_ID")
	private CandidatesType candidateType;

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

	public Integer getFacultyID() {
		return facultyID;
	}

	public void setFacultyID(Integer facultyID) {
		this.facultyID = facultyID;
	}

	public CandidatesType getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(CandidatesType candidateType) {
		this.candidateType = candidateType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}
