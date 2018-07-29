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
import main.com.zc.services.domain.petition.model.FormsStatus;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "FormsSettings.getAll", query = "SELECT d FROM FormsSettings d"),
	@NamedQuery(name = "FormsSettings.getById", query = "from FormsSettings d where d.id = :id "),
	@NamedQuery(name = "FormsSettings.getByFormId", query = "from FormsSettings d where d.formID.id = :id "),
	@NamedQuery(name = "FormsSettings.getByFormIdAndLevelId", query = "from FormsSettings d where d.formID.id = :id and d.levelID = :levelID")

	
	})
@Entity
@Table(name = "forms_settings")
public class FormsSettings {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	
	@ManyToOne
	@JoinColumn(name = "FORM_ID")
	private FormsStatus formID;
	
	@Column(name="LEVEL")
	private Integer levelID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public FormsStatus getFormID() {
		return formID;
	}

	public void setFormID(FormsStatus formID) {
		this.formID = formID;
	}

	public Integer getLevelID() {
		return levelID;
	}

	public void setLevelID(Integer levelID) {
		this.levelID = levelID;
	}
	
	
	
}
