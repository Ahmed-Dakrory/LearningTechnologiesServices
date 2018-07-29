/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "FormsStatus.getAll", query = "SELECT d FROM FormsStatus d ORDER BY d.id DESC"),
	@NamedQuery(name = "FormsStatus.getById", query = "from FormsStatus d where d.id = :id")
	
})
@Entity
@Table(name = "forms_status")
public class FormsStatus {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="FORM_TYPE_NAME")
	private String formTypeName;
	
	@Column(name="STATUS")
	private FormsStatusEnum status;

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

	public String getFormTypeName() {
		return formTypeName;
	}

	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}

	public FormsStatusEnum getStatus() {
		return status;
	}

	public void setStatus(FormsStatusEnum status) {
		this.status = status;
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
