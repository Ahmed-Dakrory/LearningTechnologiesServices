/**
 * 
 */
package main.com.zc.services.domain.feedback.model;

import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.services.domain.petition.model.Majors;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa 
 * 
 *
 */
@NamedQueries({
	
	@NamedQuery(
	name = "Feedback.getFeedbackPersonID",
	query = "from Feedback f where f.student.id = :id ORDER BY f.id DESC"
	),
	 @NamedQuery(name="Feedback.getAll",
     query="SELECT f FROM Feedback f ORDER BY f.id DESC"
     )
	,
	 @NamedQuery(name="Feedback.getById",
    query="from Feedback f where f.id = :id"
    )
	,
	 @NamedQuery(name="Feedback.getByCategId",
   query="from Feedback f where f.category.id = :id ORDER BY f.id DESC"
   
   )
	,
	 @NamedQuery(name="Feedback.getByPersonIdAndStep",
  query="from Feedback f where f.student.id = :id AND f.step = :step ORDER BY f.id DESC"
  
  )
	,
	 @NamedQuery(name="Feedback.getByCategoryAndStep",
 query="from Feedback f where f.category.id = :id AND f.step = :step ORDER BY f.id DESC"
 
 )
,
	 @NamedQuery(name="Feedback.getByMajorAndStep",
query="from Feedback f where f.major.id = :id AND f.step = :step ORDER BY f.id DESC"

)
	,
	 @NamedQuery(name="Feedback.getByMajorHeadAndStep",
query="from Feedback f where f.major.headOfMajorId.id = :id AND f.step = :step ORDER BY f.id DESC"

)
	,
	 @NamedQuery(name="Feedback.getPendingByMajorHead",
query="from Feedback f where f.major.headOfMajorId.id= :id AND(f.step = 8 OR f.step = 9) ORDER BY f.id DESC" 

)
	,
	 @NamedQuery(name="Feedback.getOldByMajorHead",
query="from Feedback f where f.major.headOfMajorId.id= :id AND f.performed= 1 ORDER BY f.id DESC"

)
	,
	 @NamedQuery(name="Feedback.getByCategoryHeadAndStep",
query="from Feedback f where f.category.categoryHeadID = :id AND f.step = :step"

)	
	,
	 @NamedQuery(name="Feedback.getPendingByCategoryHead",
query="from Feedback f where f.category.categoryHeadID= :id AND(f.step = 8 OR f.step = 9) ORDER BY f.id DESC"

)
	,
	 @NamedQuery(name="Feedback.getOldByCategoryHead",
query="from Feedback f where f.category.categoryHeadID= :id AND f.performed= 1 ORDER BY f.id DESC"

)
	
	,
	 @NamedQuery(name="Feedback.getByStep",
query="from Feedback f where f.step = :step ORDER BY f.id DESC"


),
	 @NamedQuery(name="Feedback.getHandlerOld",
query="from Feedback f where (f.step = 9 or f.step = 10) or f.performed= 1 ORDER BY f.id DESC"


)
	,
	 @NamedQuery(name="Feedback.getoldByStudentID",
query="from Feedback f where f.student.id = :id and f.performed=1 ORDER BY f.id DESC")
})
@Entity
@Table(name = "feedback")
public class Feedback {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="FEEDBACK_FORM")
	private String feedbackForm;
	
	@Column(name="SUBMITTED_DATE")
	private Calendar submittedDate;
	
	@Column(name="TITLE")
	private String title;
	

	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private Student	student;
	
	
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID")
	private FeedbackCategory category;
	
	@Column(name="STEP")
	private PetitionStepsEnum step;
	
	@ManyToOne
	@JoinColumn(name="MAJOR")
	private Majors major;
	
	@Column(name="PERFORMED")	
	private Boolean performed;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	

	public Feedback() {
		super();
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeedbackForm() {
		return feedbackForm;
	}

	public void setFeedbackForm(String feedbackForm) {
		this.feedbackForm = feedbackForm;
	}


	public Calendar getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
	}






	public FeedbackCategory getCategory() {
		return category;
	}



	public void setCategory(FeedbackCategory category) {
		this.category = category;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}






	public Student getStudent() {
		return student;
	}



	public void setStudent(Student student) {
		this.student = student;
	}



	public PetitionStepsEnum getStep() {
		return step;
	}



	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}



	public Majors getMajor() {
		return major;
	}



	public void setMajor(Majors major) {
		this.major = major;
	}



	public Boolean getPerformed() {
		return performed;
	}



	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}



	public Attachments getAttachments() {
		return attachments;
	}



	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}

	
}
