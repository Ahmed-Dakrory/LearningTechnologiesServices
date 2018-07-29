/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

/**
 * @author Omnya Alaa
 *
 */
public class TextResultsDTO {
private int id;
private String text;
public TextResultsDTO() {
	super();
}
public TextResultsDTO(int id, String text) {
	super();
	this.id = id;
	this.text = text;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}

}
