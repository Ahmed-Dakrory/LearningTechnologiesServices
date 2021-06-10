package main.com.zc.service.instructor_all_survey_ques;


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
	
	
	@NamedQuery(name="instructor_all_survey_ques.getAll",
		     query="SELECT c FROM instructor_all_survey_ques c "
		     )
	,
	@NamedQuery(name="instructor_all_survey_ques.getById",
	query = "from instructor_all_survey_ques d where d.id = :id "
			)
	,
	@NamedQuery(name="instructor_all_survey_ques.getAllByYearAndSemestar",
	query = "from instructor_all_survey_ques d where d.year = :year and d.semester = :semester order by d.category"
			)
	,
	@NamedQuery(name="instructor_all_survey_ques.getAllByYearAndSemestarAndCategoryAndMidtermOrFinal",
	query = "from instructor_all_survey_ques d where d.year = :year and d.semester = :semester and d.category = :category and d.mode = :mode order by id"
			)
	
	,
	@NamedQuery(name="instructor_all_survey_ques.getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal",
	query = "from instructor_all_survey_ques d where d.year = :year and d.semester = :semester and d.mode = :mode group by d.category"
			)
	
	,
	@NamedQuery(name="instructor_all_survey_ques.getAllByYearAndSemestarAndMidtermOrFinalAndType",
	query = "from instructor_all_survey_ques d where d.year = :year and d.semester = :semester and d.mode = :mode and d.type = :type"
			)
		
	
})

@Entity
@Table(name = "instructor_all_survey_ques")
public class instructor_all_survey_ques {
	

	public static Integer MODE_MIDTERM = 0;
	public static Integer MODE_FINAL = 1;
	
	public static Integer TYPE_CHOOSE = 0;
	public static Integer TYPE_COMMENT = 1;
	public static Integer TYPE_SELECT = 2;
	
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
	

	@Column(name = "categoryName")
	private String categoryName;
	

	@Column(name = "mode")
	private Integer mode;
	

	@Column(name = "type")
	private Integer type;
	

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

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	



	
	
}
