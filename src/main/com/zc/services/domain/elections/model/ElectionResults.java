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
	name = "ElectionResults.getByID",
	query = "from ElectionResults r where r.studentID = :id"
	),
	@NamedQuery(
			name = "ElectionResults.getAll",
			query = "SELECT c FROM ElectionResults c"
			)
	
	,
	@NamedQuery(
			name = "ElectionResults.getByPresidentID",
			query = "from ElectionResults r where r.preidentID.id = :id"
			)
	,
	@NamedQuery(
			name = "ElectionResults.getByViceID",
			query = "from ElectionResults r where r.viceID.id = :id"
			)
	,
	@NamedQuery(
			name = "ElectionResults.getByActvPresidentID",
			query = "from ElectionResults r where r.activitiesPresident.id = :id"
			)
	,
	@NamedQuery(
			name = "ElectionResults.getByServicePresidentID",
			query = "from ElectionResults r where r.servicePresident.id = :id"
			)
	,@NamedQuery(
			name = "ElectionResults.getByAcadPresidentID",
			query = "from ElectionResults r where r.academicPresident.id = :id"
			)
	
})
@Entity
@Table(name = "election_results")

public class ElectionResults {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "STUDENT_ID")
	private Integer studentID;
	
	@ManyToOne
	@JoinColumn(name="PRESIDENT")
	private ElectionCandidate preidentID;
	
	@ManyToOne
	@JoinColumn(name = "VICE")
	private ElectionCandidate viceID;
	
	@ManyToOne
	@JoinColumn(name = "ACTIVTIES_PRESIDENT")
	private ElectionCandidate activitiesPresident;
	
	@ManyToOne
	@JoinColumn(name = "SERVICES_PRESIDENT")
	private ElectionCandidate servicePresident;
	
	@ManyToOne
	@JoinColumn(name = "ACADEMIC_PRESIDENT")
	private ElectionCandidate academicPresident;

	@Column(name="ACTIVITIES")
	private String activties;
	
	@Column(name="SERVICES")
	private String services;
	
	@Column(name="ACADEMIC")
	private String academic;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public ElectionCandidate getPreidentID() {
		return preidentID;
	}

	public void setPreidentID(ElectionCandidate preidentID) {
		this.preidentID = preidentID;
	}

	public ElectionCandidate getViceID() {
		return viceID;
	}

	public void setViceID(ElectionCandidate viceID) {
		this.viceID = viceID;
	}

	public ElectionCandidate getActivitiesPresident() {
		return activitiesPresident;
	}

	public void setActivitiesPresident(ElectionCandidate activitiesPresident) {
		this.activitiesPresident = activitiesPresident;
	}

	public ElectionCandidate getServicePresident() {
		return servicePresident;
	}

	public void setServicePresident(ElectionCandidate servicePresident) {
		this.servicePresident = servicePresident;
	}

	public ElectionCandidate getAcademicPresident() {
		return academicPresident;
	}

	public void setAcademicPresident(ElectionCandidate academicPresident) {
		this.academicPresident = academicPresident;
	}

	public String getActivties() {
		return activties;
	}

	public void setActivties(String activties) {
		this.activties = activties;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getAcademic() {
		return academic;
	}

	public void setAcademic(String academic) {
		this.academic = academic;
	}


	
}
