package main.com.zc.services.presentation.survey.CourseEvalNew.dto;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.domain.survey.model.Section;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.primefaces.model.chart.PieChartModel;

public class CourseEvalInsQuestionsDTO {

	private Integer id;	

	private String text;	
	
	//private QuestionsCategory category;
	private SectionsDTO section;

	private Integer mainSelection;
	
	private List<String> strengthsSelection;
	
	private List<String> concernsSelection;

	private String strengthText;
	
	private String concernText;
	
	private InstructorDTO instructor;

	private PieChartModel model=new PieChartModel();
	private List<PieChartModel> strengthModels = new ArrayList<PieChartModel>();
	private List<PieChartModel> concernModels = new ArrayList<PieChartModel>();
	private String strengthMainSelection;
	private String concernMainSelection;
	
	private List<String> comments=new ArrayList<String>();
	private ScaleTypeDTO scaleType;
	
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

/*
	public QuestionsCategory getCategory() {
		return category;
	}


	public void setCategory(QuestionsCategory category) {
		this.category = category;
	}*/

	public InstructorDTO getInstructor() {
		return instructor;
	}


	public SectionsDTO getSection() {
		return section;
	}


	public void setSection(SectionsDTO section) {
		this.section = section;
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


	public Integer getMainSelection() {
		return mainSelection;
	}


	public void setMainSelection(Integer mainSelection) {
		this.mainSelection = mainSelection;
	}


	public List<String> getStrengthsSelection() {
		return strengthsSelection;
	}


	public void setStrengthsSelection(List<String> strengthsSelection) {
		this.strengthsSelection = strengthsSelection;
	}


	public List<String> getConcernsSelection() {
		return concernsSelection;
	}


	public void setConcernsSelection(List<String> concernsSelection) {
		this.concernsSelection = concernsSelection;
	}


	public String getStrengthText() {
		return strengthText;
	}


	public void setStrengthText(String strengthText) {
		this.strengthText = strengthText;
	}


	public String getConcernText() {
		return concernText;
	}


	public void setConcernText(String concernText) {
		this.concernText = concernText;
	}


	public List<PieChartModel> getStrengthModels() {
		return strengthModels;
	}


	public void setStrengthModels(List<PieChartModel> strengthModels) {
		this.strengthModels = strengthModels;
	}


	public List<PieChartModel> getConcernModels() {
		return concernModels;
	}


	public void setConcernModels(List<PieChartModel> concernModels) {
		this.concernModels = concernModels;
	}


	public String getStrengthMainSelection() {
		return strengthMainSelection;
	}


	public void setStrengthMainSelection(String strengthMainSelection) {
		this.strengthMainSelection = strengthMainSelection;
	}


	public String getConcernMainSelection() {
		return concernMainSelection;
	}


	public void setConcernMainSelection(String concernMainSelection) {
		this.concernMainSelection = concernMainSelection;
	}	
	
}
