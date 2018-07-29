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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "DeclarationOfConcentration.getAll", query = "SELECT d FROM DeclarationOfConcentration d ORDER BY d.id DESC"),
	@NamedQuery(name = "DeclarationOfConcentration.getById", query = "from DeclarationOfConcentration d where d.id = :id "),
	@NamedQuery(name = "DeclarationOfConcentration.getByStudentID", query = "from DeclarationOfConcentration d where d.student.id = :id"),
	@NamedQuery(name = "DeclarationOfConcentration.getByConcentrationID", query = "from DeclarationOfConcentration d where d.concentartion = :id"),
	@NamedQuery(name = "DeclarationOfConcentration.getAllByYearAndSemester", query = "from DeclarationOfConcentration d where d.year = :year AND  d.semester = :semester"),
	@NamedQuery(name = "DeclarationOfConcentration.getAllByConcentrationIDAndYearAndSemester", query = "from DeclarationOfConcentration d where d.concentartion = :id And d.year = :year AND  d.semester = :semester"),
	@NamedQuery(name = "DeclarationOfConcentration.getByStudentIDAndYearAndSemester", query = "from DeclarationOfConcentration d where d.year = :year AND  d.semester = :semester AND d.student.id = :id")
	
	
	
	})
@Entity
@Table(name = "declaration_of_concentration")
public class DeclarationOfConcentration {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="MOBILE")
	private String mobile;
	
	
	@ManyToOne
	@JoinColumn(name = "CONCENTRATION")
	private Concentration concentartion;

	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;

	@Column(name="YEAR")
	private Integer year;
	
	@Column(name="SEMESTER")
	private SemesterEnum semester;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Concentration getConcentartion() {
		return concentartion;
	}

	public void setConcentartion(Concentration concentartion) {
		this.concentartion = concentartion;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}


	
	
	
}
