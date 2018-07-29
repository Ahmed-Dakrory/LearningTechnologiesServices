/**
 * 
 */
package main.com.zc.services.presentation.forms.shared.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public class PetitionsActionsDTO {


	private Integer id;
		
	private Integer petitionID;
	
	private PetitionActionTypeEnum actionType;
		
	private FormTypesEnum formType;
		
	private String comment;
		
	private Calendar date;
	private String friendlyDate;
	private String instructorName;
	private Integer instructorID;

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

	public Integer getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(Integer instructorID) {
		this.instructorID = instructorID;
	}

	public String getFriendlyDate() {
		if(getDate()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getDate().getTime());
			    return strDate;
			}
			
			else return "";
	}

	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	

	
	
}
