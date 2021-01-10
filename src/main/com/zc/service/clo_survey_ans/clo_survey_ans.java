package main.com.zc.service.clo_survey_ans;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.service.courseClo.course_clo;
import main.com.zc.services.domain.person.model.Student;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="clo_survey_ans.getAll",
		     query="SELECT c FROM clo_survey_ans c "
		     )
	,
	@NamedQuery(name="clo_survey_ans.getById",
	query = "from clo_survey_ans d where d.id = :id "
			)
	,
	@NamedQuery(name="clo_survey_ans.getByStudentIdAndCourseId",
	query = "from clo_survey_ans d where d.studentId.id = :idStudent and d.courseCloId.id = :courseId"
			)

	,
	@NamedQuery(name="clo_survey_ans.getAllByCourseId",
	query = "from clo_survey_ans d where d.courseCloId.id = :courseId"
			)
	
	,
	@NamedQuery(name="clo_survey_ans.getAllByCourseIdGroupByStudent",
	query = "from clo_survey_ans d where d.courseCloId.id = :courseId group by d.studentId.id"
			)
		
	
})

@Entity
@Table(name = "clo_survey_ans")
public class clo_survey_ans {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	

	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	
	@ManyToOne
	@JoinColumn(name = "courseCloId")
	private course_clo courseCloId;
	

	@Column(name = "clo1")
	private Integer clo1;
	

	@Column(name = "clo2")
	private Integer clo2;

	

	@Column(name = "clo3")
	private Integer clo3;

	

	@Column(name = "clo4")
	private Integer clo4;

	

	@Column(name = "clo5")
	private Integer clo5;

	

	@Column(name = "clo6")
	private Integer clo6;

	

	@Column(name = "clo7")
	private Integer clo7;

	
	

	@Column(name = "clo8")
	private Integer clo8;

	

	@Column(name = "clo9")
	private Integer clo9;

	@Column(name = "clo10")
	private Integer clo10;
	

	@Column(name = "clo11")
	private Integer clo11;
	

	@Column(name = "clo12")
	private Integer clo12;

	

	@Column(name = "clo13")
	private Integer clo13;

	

	@Column(name = "clo14")
	private Integer clo14;

	

	@Column(name = "clo15")
	private Integer clo15;

	

	@Column(name = "clo16")
	private Integer clo16;

	

	@Column(name = "clo17")
	private Integer clo17;

	
	

	@Column(name = "clo18")
	private Integer clo18;

	

	@Column(name = "clo19")
	private Integer clo19;

	@Column(name = "clo20")
	private Integer clo20;
	

	@Column(name = "date")
	private Date date;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Student getStudentId() {
		return studentId;
	}


	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}


	public course_clo getCourseCloId() {
		return courseCloId;
	}


	public void setCourseCloId(course_clo courseCloId) {
		this.courseCloId = courseCloId;
	}


	public Integer getClo1() {
		return clo1;
	}


	public void setClo1(Integer clo1) {
		this.clo1 = clo1;
	}


	public Integer getClo2() {
		return clo2;
	}


	public void setClo2(Integer clo2) {
		this.clo2 = clo2;
	}


	public Integer getClo3() {
		return clo3;
	}


	public void setClo3(Integer clo3) {
		this.clo3 = clo3;
	}


	public Integer getClo4() {
		return clo4;
	}


	public void setClo4(Integer clo4) {
		this.clo4 = clo4;
	}


	public Integer getClo5() {
		return clo5;
	}


	public void setClo5(Integer clo5) {
		this.clo5 = clo5;
	}


	public Integer getClo6() {
		return clo6;
	}


	public void setClo6(Integer clo6) {
		this.clo6 = clo6;
	}


	public Integer getClo7() {
		return clo7;
	}


	public void setClo7(Integer clo7) {
		this.clo7 = clo7;
	}


	public Integer getClo8() {
		return clo8;
	}


	public void setClo8(Integer clo8) {
		this.clo8 = clo8;
	}


	public Integer getClo9() {
		return clo9;
	}


	public void setClo9(Integer clo9) {
		this.clo9 = clo9;
	}


	public Integer getClo10() {
		return clo10;
	}


	public void setClo10(Integer clo10) {
		this.clo10 = clo10;
	}


	public Integer getClo11() {
		return clo11;
	}


	public void setClo11(Integer clo11) {
		this.clo11 = clo11;
	}


	public Integer getClo12() {
		return clo12;
	}


	public void setClo12(Integer clo12) {
		this.clo12 = clo12;
	}


	public Integer getClo13() {
		return clo13;
	}


	public void setClo13(Integer clo13) {
		this.clo13 = clo13;
	}


	public Integer getClo14() {
		return clo14;
	}


	public void setClo14(Integer clo14) {
		this.clo14 = clo14;
	}


	public Integer getClo15() {
		return clo15;
	}


	public void setClo15(Integer clo15) {
		this.clo15 = clo15;
	}


	public Integer getClo16() {
		return clo16;
	}


	public void setClo16(Integer clo16) {
		this.clo16 = clo16;
	}


	public Integer getClo17() {
		return clo17;
	}


	public void setClo17(Integer clo17) {
		this.clo17 = clo17;
	}


	public Integer getClo18() {
		return clo18;
	}


	public void setClo18(Integer clo18) {
		this.clo18 = clo18;
	}


	public Integer getClo19() {
		return clo19;
	}


	public void setClo19(Integer clo19) {
		this.clo19 = clo19;
	}


	public Integer getClo20() {
		return clo20;
	}


	public void setClo20(Integer clo20) {
		this.clo20 = clo20;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}



	
	
}
