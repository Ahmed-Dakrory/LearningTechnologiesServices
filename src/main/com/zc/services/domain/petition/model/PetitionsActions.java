/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	//@NamedQuery(name = "PetitionsActions.getAll", query = "SELECT d FROM PetitionsActions d ORDER BY d.id DESC"),
	@NamedQuery(name = "PetitionsActions.getById", query = "from PetitionsActions d where d.id = :id"),
	@NamedQuery(name = "PetitionsActions.getByPetitionIDAndForm", query = "from PetitionsActions d where d.petitionID = :id and d.formType = :formType ORDER BY d.date ASC"),
	@NamedQuery(name = "PetitionsActions.getByPetitionIDAndFormAndIns", query = "from PetitionsActions d where d.petitionID = :id and d.formType = :formType"
			+ " and d.instructor.id = :insID "),
	/*@NamedQuery(name = "IncompleteGrade.getPendingByInstructor", query = "from IncompleteGrade d where (d.instructor.id = :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldByInstructor", query = "from IncompleteGrade d where d.instructor.id = :id or d.forwardTOIns.id =:id  ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getPendingByPA", query = "from IncompleteGrade d where (d.major.headOfMajorId.id= :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldByPA", query = "from IncompleteGrade d where d.major.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getPendingJob", query = "FROM IncompleteGrade c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL")
*/	})
@Entity
@Table(name = "petitions_actions")
public class PetitionsActions {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name = "PETITION_ID")
	private Integer petitionID;
	
	@Column(name = "ACTION_TYPE")
	private PetitionActionTypeEnum actionType;
	
	
	@Column(name = "FORM_TYPE")
	private FormTypesEnum formType;
	
	
	@Column(name = "COMMENT")
	private String comment;
	
	
	@Column(name = "DATE")
	private Calendar date;
	
	
	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Employee instructor;


	public PetitionsActions() {
		
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPetitionID() {
		return petitionID;
	}


	public void setPetitionID(Integer petitionID) {
		this.petitionID = petitionID;
	}


	public PetitionActionTypeEnum getActionType() {
		return actionType;
	}


	public void setActionType(PetitionActionTypeEnum actionType) {
		this.actionType = actionType;
	}


	public FormTypesEnum getFormType() {
		return formType;
	}


	public void setFormType(FormTypesEnum formType) {
		this.formType = formType;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}


	public Employee getInstructor() {
		return instructor;
	}


	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}
	
	
	
}
