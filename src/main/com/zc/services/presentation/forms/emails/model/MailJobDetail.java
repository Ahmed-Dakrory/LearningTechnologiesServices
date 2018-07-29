/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.model;

import java.util.Date;
import java.util.List;

import main.com.zc.services.domain.person.model.IEmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.stereotype.Component;

@Component
public class MailJobDetail extends JobDetailBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IEmployeeRepository iInstructorRepository;
	
	private List<String> recipentList;
	private String content;
	private String title;
	private String name;
	private Integer formId;
	private String formName;
	private Date notifyAt;
	public List<String> getRecipentList() {
		return recipentList;
	}
	public void setRecipentList(List<String> recipentList) {
		this.recipentList = recipentList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String contetn) {
		this.content = contetn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public Date getNotifyAt() {
		return notifyAt;
	}
	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}
	

}