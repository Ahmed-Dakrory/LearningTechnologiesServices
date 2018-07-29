/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Omnya Alaa
 *
 */
public class SurveyDTO {
	private String name;
	private Calendar date;
	private String description;
	private int id;
	private String friendlyDate;
	public SurveyDTO(String name, Calendar date, String description, int id) {
		super();
		this.name = name;
		this.date = date;
		this.description = description;
		this.id = id;
	}
	public SurveyDTO(String name, Calendar date, String description) {
		super();
		this.name = name;
		this.date = date;
		this.description = description;
	}
	
	public SurveyDTO() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
}
