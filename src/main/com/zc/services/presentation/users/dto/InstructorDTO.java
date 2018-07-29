/**
 * 
 */
package main.com.zc.services.presentation.users.dto;

import java.io.ByteArrayInputStream;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @author omnya
 *
 */
public class InstructorDTO {
	private Integer id;
	private String name;
	private String mail;
	private byte[] photo;
	private String title;
	private String phone;
	private String empID;
	private Integer empType;
	private boolean selected;// is used in course evaluation choices of TAs and instructor
	public StreamedContent getImage() {
		  FacesContext context = FacesContext.getCurrentInstance();

	        if (context.getRenderResponse()) {
	            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
	            return new DefaultStreamedContent();
	        }
	        else {
	            // So, browser is requesting the image. Get ID value from actual request param.
	           // String id = context.getExternalContext().getRequestParameterMap().get("id");
	          
	            return new DefaultStreamedContent(new ByteArrayInputStream(photo));
	        }
	}
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public Integer getEmpType() {
		return empType;
	}
	public void setEmpType(Integer empType) {
		this.empType = empType;
	}
	
	
	
}
