/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.dto;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import org.primefaces.model.chart.PieChartModel;

/**
 * @author omnya
 *
 */
public class CourseEvalQuestionsDTO {


	private Integer id;
	

	private String text;
	
	
	private QuestionsCategory category;

	private String ansText;
	
	private int selection;

	private InstructorDTO instructor;

	private PieChartModel model=new PieChartModel();
	private List<String> comments=new ArrayList<String>();
	private ScaleTypeDTO scaleType=new ScaleTypeDTO();
	private SectionsDTO sectionDTO;
	private boolean editMode;
	private String selectionName;
	private Integer selectionType;//Main , strength , concern
	private Boolean scaleMode;
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public QuestionsCategory getCategory() {
		return category;
	}


	public void setCategory(QuestionsCategory category) {
		this.category = category;
	}


	public int getSelection() {
		return selection;
	}


	public void setSelection(int selection) {
		this.selection = selection;
	}


	public String getAnsText() {
		return ansText;
	}


	public void setAnsText(String ansText) {
		this.ansText = ansText;
	}


	public InstructorDTO getInstructor() {
		return instructor;
	}


	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}


	public PieChartModel getModel() {
		return model;
	}


	public void setModel(PieChartModel model) {
		this.model = model;
	}


	public List<String> getComments() {
		return comments;
	}


	public void setComments(List<String> comments) {
		this.comments = comments;
	}


	public ScaleTypeDTO getScaleType() {
		return scaleType;
	}


	public void setScaleType(ScaleTypeDTO scaleType) {
		this.scaleType = scaleType;
	}


	public SectionsDTO getSectionDTO() {
		return sectionDTO;
	}


	public void setSectionDTO(SectionsDTO sectionDTO) {
		this.sectionDTO = sectionDTO;
	}


	public boolean isEditMode() {
		return editMode;
	}


	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}


	public String getSelectionName() {
		return selectionName;
	}


	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
	}


	public Boolean getScaleMode() {
		return scaleMode;
	}


	public void setScaleMode(Boolean scaleMode) {
		this.scaleMode = scaleMode;
	}


	public Integer getSelectionType() {
		return selectionType;
	}


	public void setSelectionType(Integer selectionType) {
		this.selectionType = selectionType;
	}




	
	
	
}
