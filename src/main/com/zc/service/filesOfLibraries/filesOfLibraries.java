package main.com.zc.service.filesOfLibraries;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.petition.model.Attachments;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="filesOfLibraries.getAll",
		     query="SELECT c FROM filesOfLibraries c "
		     )
	,
	@NamedQuery(name="filesOfLibraries.getById",
	query = "from filesOfLibraries d where d.id = :id "
			)
	,
	@NamedQuery(name="filesOfLibraries.getByYearAndSemester",
	query = "from filesOfLibraries d where d.year = :year and d.semester = :semester "
			)
	
	
	
})
 
@Entity
@Table(name = "filesOfLibraries")
public class filesOfLibraries {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	

	@Column(name = "year")
	private String year;
	

	@Column(name = "semester")
	private String semester;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attachment")
	private Attachments attachment;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public Attachments getAttachment() {
		return attachment;
	}


	public void setAttachment(Attachments attachment) {
		this.attachment = attachment;
	}
	
	




	

	
	
}
