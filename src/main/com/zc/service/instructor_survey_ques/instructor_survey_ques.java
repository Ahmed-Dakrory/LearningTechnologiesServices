package main.com.zc.service.instructor_survey_ques;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="instructor_survey_ques.getAll",
		     query="SELECT c FROM instructor_survey_ques c "
		     )
	,
	@NamedQuery(name="instructor_survey_ques.getById",
	query = "from instructor_survey_ques d where d.id = :id "
			)
	,
	@NamedQuery(name="instructor_survey_ques.getAllByYearAndSemestar",
	query = "from instructor_survey_ques d where d.year = :year and d.semester = :semester"
			)
	,
	@NamedQuery(name="instructor_survey_ques.getAllByYearAndSemestarAndCategory",
	query = "from instructor_survey_ques d where d.year = :year and d.semester = :semester and d.category = :category"
			)
		
	
})

@Entity
@Table(name = "instructor_survey_ques")
public class instructor_survey_ques {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "ques")
	private String ques;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "semester")
	private Integer semester;

	@Column(name = "category")
	private Integer category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
	
	



	
	
}
