/**
 * 
 */
package main.com.zc.services.presentation.survey.config.dto;

import java.util.Date;

/**
 * @author omnya
 *
 */
public class SurveyDTO {

	private Integer id;
	private String name;
	private Date date;
	private Boolean active;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	
}
