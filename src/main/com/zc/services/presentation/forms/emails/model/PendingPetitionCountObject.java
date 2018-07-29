/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.model;

import java.io.Serializable;

import main.com.zc.services.domain.person.model.Employee;


public class PendingPetitionCountObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Employee instructor;
	private Long petionCount;
	
	public PendingPetitionCountObject(Employee instructor, Long petionCount) {
		super();
		this.instructor = instructor;
		this.petionCount = petionCount;
	}
	public Employee getInstructor() {
		return instructor;
	}
	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}
	public Long getPetionCount() {
		return petionCount;
	}
	public void setPetionCount(Long petionCount) {
		this.petionCount = petionCount;
	}

}