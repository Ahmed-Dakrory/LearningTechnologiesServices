/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

/**
 * @author Omnya Alaa
 *
 */
public class RatedQuestionsFormDTO {
	private int id;
	private int sectionId;
	private String quesForm;
	private int quesFormId;
	private int selectedAns;
	private String ansString;
	private int courseInsId;
	private int courseId;
	
	
	
	
	public RatedQuestionsFormDTO(int quesFormId, int selectedAns, int courseId) {
		super();
		this.quesFormId = quesFormId;
		this.selectedAns = selectedAns;
		this.courseId = courseId;
	}

	public RatedQuestionsFormDTO(int quesFormId, String ansString, int courseId) {
		super();
		this.quesFormId = quesFormId;
		this.ansString = ansString;
		this.courseId = courseId;
	}

	public RatedQuestionsFormDTO(String quesForm, String ansString) {
		super();
		this.quesForm = quesForm;
		this.ansString = ansString;
	}
	public RatedQuestionsFormDTO(int quesFormId, int selectedAns) {
		super();
		this.quesFormId = quesFormId;
		this.selectedAns = selectedAns;
	}
	public RatedQuestionsFormDTO(int id, int sectionId, String quesForm,
			int selectedAns) {
		super();
		this.id = id;
		this.sectionId = sectionId;
		this.quesForm = quesForm;
		this.selectedAns = selectedAns;
	}
	public RatedQuestionsFormDTO(int id, int sectionId, String quesForm) {
		super();
		this.id = id;
		this.sectionId = sectionId;
		this.quesForm = quesForm;
	}
	public RatedQuestionsFormDTO() {
		super();
	}
	public RatedQuestionsFormDTO(int sectionId, String quesForm) {
		super();
		this.sectionId = sectionId;
		this.quesForm = quesForm;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public String getQuesForm() {
		return quesForm;
	}
	public void setQuesForm(String quesForm) {
		this.quesForm = quesForm;
	}
	public int getSelectedAns() {
		return selectedAns;
	}
	public void setSelectedAns(int selectedAns) {
		this.selectedAns = selectedAns;
	}
	public int getQuesFormId() {
		return quesFormId;
	}
	public void setQuesFormId(int quesFormId) {
		this.quesFormId = quesFormId;
	}
	public String getAnsString() {
		return ansString;
	}
	public void setAnsString(String ansString) {
		this.ansString = ansString;
	}
	public int getCourseInsId() {
		return courseInsId;
	}
	public void setCourseInsId(int courseInsId) {
		this.courseInsId = courseInsId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	

}
