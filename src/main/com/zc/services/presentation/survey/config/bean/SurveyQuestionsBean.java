/**
 * 
 */
package main.com.zc.services.presentation.survey.config.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.shared.facade.ICouresFacade;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveySectionsDTO;
import main.com.zc.services.presentation.survey.config.facade.ISurveyFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class SurveyQuestionsBean {

	private List<SurveySectionsDTO> surveySections;
	private List<SectionsDTO> sections ;
	@ManagedProperty("#{ISurveyFacade}")
	private ISurveyFacade facade;
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade coursesFacade;
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade quesFacade;
	private Integer surveyID;
	private SectionsDTO processSectioDTO;
	private CourseEvalQuestionsDTO addedQuestion;
	private CourseEvalQuestionsDTO processedQues;
	private SectionsDTO addedSection;
	private List<CoursesDTO> coursesLst;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	
	private List<ScaleTypeDTO> scales;
	private ScaleTypeDTO selectedScaleType;
	
	@ManagedProperty("#{ICouresFacade}")
	ICouresFacade courseFacade;
	
	private FormsStatusDTO form;

	@PostConstruct
	public void init(){
		 form=formStatus.getById(12);
		 selectedScaleType=new ScaleTypeDTO();
		coursesLst=coursesFacade.getAllCoursesBySemesterAndYear(form.getSemester().getId(), form.getYear());
		scales=facade.getScales();
		addedSection=new SectionsDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try{
			sections=new ArrayList<SectionsDTO>();
			surveyID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(surveyID!=null){
				fillSections(surveyID);
			}
			}
			catch(Exception ex){
				 try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("/LearningTechnologiesServices/pages/secured/survey/surveyConfig/surveys.xhtml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
		
	}
	public void fillSections(Integer sid)
	{
		sections=new ArrayList<SectionsDTO>();
		surveySections=facade.getSectionsBySurveyID( sid);
		for(SurveySectionsDTO dto:surveySections){
			SectionsDTO section=dto.getSection();
			section.setQuestions(quesFacade.getBySectionID(section.getId()));
			sections.add(section);
			
		}
	}
	public void preAddSection(){
		addedSection=new SectionsDTO();
		

	}
	
	
	
	public void preAddQuestion(SectionsDTO section){
		   addedQuestion=new CourseEvalQuestionsDTO();
		   setProcessSectioDTO(section);
		  	}
	public void addQuestion(){
		SectionsDTO section=new SectionsDTO();
		section.setId(getProcessSectioDTO().getId());
		getAddedQuestion().setSectionDTO(section);
		//TODO 
		//call question facade
		// add question
		CourseEvalQuestionsDTO dto=quesFacade.add(addedQuestion);
		//if(sectionID==null) setSectionID(sectionID);
		 if(dto!=null)
		 {
			 if(dto.getId()!=null)
				 {
				 //questions=quesFacade.getBySectionID(getProcessSectioDTO().getId());
				  fillSections(getSurveyID());
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions added successfully");
				 }
			 else 
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be added!");
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be added!");
		
	}
	public void delete(CourseEvalQuestionsDTO ques){
		if(ques.getScaleType().getId()==null)
			ques.setScaleType(null);
		boolean b=quesFacade.delete(ques);
		 if(b)
		 {
			
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions deleted successfully");
				 // questions=quesFacade.getBySectionID(getProcessSectioDTO().getId());
				 fillSections(getSurveyID());
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be deleted!");
		
	}
	
	public void preAddScaleType(CourseEvalQuestionsDTO ques,SectionsDTO section){
		processedQues=null;
		setProcessedQues(ques);
		 getProcessedQues().setEditMode(true);
		 setProcessSectioDTO(section);
		
		 
	}
	public void addSelection(){
		if(!getProcessedQues().getScaleMode()){
		ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
		selection.setName(getProcessedQues().getSelectionName());
		selection.setType(ScaleSelectionTypeEnum.getByid(getProcessedQues().getSelectionType()));
		selection.setScaleType(getProcessedQues().getScaleType());
		getProcessedQues().getScaleType().getSelections().add(selection);
		}
		//else 
		quesFacade.add(getProcessedQues());
		fillSections(getSurveyID());
		for(CourseEvalQuestionsDTO ques:getProcessSectioDTO().getQuestions())
		{
			ques.setEditMode(false);
		}
		getProcessedQues().setSelectionName(null);
	}
public void deleteSelection(ScaleSelectionsDTO selection){
		
		boolean b=quesFacade.deleteSelection(selection);
		 if(b)
		 {
			
				 //JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions deleted successfully");
				  fillSections(getSurveyID());
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Selection is failed to be deleted!");
		
	}

	public void addSection() {
		addedSection.setDate(new Date());
		
		if(addedSection.getCourse().getId()==0)
			addedSection.setCourse(null);
		
		addedSection = facade.addSection(getAddedSection());
		if (addedSection != null) {
			{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Section was added successfully");
			facade.addSurveySection(getSurveyID(),getAddedSection().getId());
			fillSections(getSurveyID());
			}
		} else
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be added!");
	}
	public void deleteSection(SectionsDTO section) {
		boolean relation=facade.deleteSurvey(getSurveyID(), section.getId());
		
		if(relation)
			{boolean b= facade.deleteSection(section.getId());
		if (b) {
			{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Section was deleted successfully");
			fillSections(getSurveyID());
			}
		} else
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be deleted!");
			}
		 else
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be deleted!");
	}

	public void uploadFile(FileUploadEvent event)
	{
		 InputStream inputStream = null;
		 try {
			inputStream=event.getFile().getInputstream();
			List<SectionsDTO> dataList=parseFile(inputStream);
			
			for(SectionsDTO sectionDTO:dataList){
				if(sectionDTO.getQuestions().size()>0){
				sectionDTO.setDate(new Date());
				SectionsDTO newsectionDTO=facade.addSection(sectionDTO);
				if(newsectionDTO!=null)
				{
					facade.addSurveySection(getSurveyID(),newsectionDTO.getId());
					//TODO 
					//call question facade
					// add question
					for(CourseEvalQuestionsDTO ques:sectionDTO.getQuestions())
					{
						ques.setSectionDTO(newsectionDTO);
						quesFacade.add(ques);
					}
				}
			}}
			
		/*	
			
			System.out.println(dataList.size());
		
			
			for(SectionsDTO dto:dataList)
			{
				System.out.println("Section name: "+dto.getName());
				System.out.println("Section title: "+dto.getTitle());
				System.out.println("Section Course: "+dto.getCourse().getName());
				for(CourseEvalQuestionsDTO ques:dto.getQuestions())
				{
					System.out.println("Question: "+ques.getText());
					System.out.println("Scale: "+ques.getScaleType().getId());
				}
			}*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	public List<SectionsDTO> parseFile(InputStream input){
		List<SectionsDTO> dataList = new ArrayList<SectionsDTO>();
		
		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(input);
		

		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		// Iterate through each rows one by one

		Iterator<Row> rowIterator = sheet.iterator();
		  CoursesDTO course=new CoursesDTO();
		  SectionsDTO section=new SectionsDTO();
		  List<CourseEvalQuestionsDTO> quesLst=new ArrayList<CourseEvalQuestionsDTO>();
		  String sectionName = null;
		  String sectionTitle = null;
		  String sectionScale = null;
		  ScaleTypeDTO dto=null;
		while (rowIterator.hasNext()) {
			
			Row row = rowIterator.next();
			if(row.getRowNum()==0 ){
				   continue; //just skip the rows if row number is 0
				  }
			// For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			
			
		
				int count = 0;
				
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
			
				
				
          
          
				if (count == 0) { // Course
					
					switch (cell.getCellType()) {
				
				
					case Cell.CELL_TYPE_STRING:
					{
						
						section.setQuestions(quesLst);
						dataList.add(section);
						course=courseFacade.getcourseByNameAndSemesterAndYear(cell.getStringCellValue(),form.getSemester().getId(),form.getYear());
						section=new SectionsDTO();
						section.setCourse(course);
						quesLst=new ArrayList<CourseEvalQuestionsDTO>();
						
						
					}
					
					}
				}

				

				else if (count ==1) {// Questions
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						{
							CourseEvalQuestionsDTO ques=new CourseEvalQuestionsDTO();
							ques.setText(cell.getStringCellValue());
							ques.setScaleType(getSelectedScaleType());
							quesLst.add(ques);
							section.setName(sectionName);
							section.setTitle(sectionTitle);

						}
						break;
					}
				}
				

				else if (count ==2) {// Section name
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						{
						sectionName=cell.getStringCellValue();
						section.setName(sectionName);
						
							
						}
						break;
					}
				}

				else if (count ==3) {// section title
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						{
							sectionTitle=cell.getStringCellValue();
							section.setTitle(sectionTitle);
						}
						break;
					}
				}
				else if (count ==4) {// scale
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						{
							sectionScale=cell.getStringCellValue();
							try{
							 dto=facade.getByName(sectionScale);
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
							
						}
						break;
					}
				}
				

				count++;	
		}
			
			
		}
			
		
		//For the last course only
		section.setName(sectionName);
		section.setTitle(sectionTitle);
		
		//To add scale 
		for(CourseEvalQuestionsDTO ques:quesLst){
			ques.setScaleType(dto);
		}
		section.setQuestions(quesLst);
		dataList.add(section);
	
		
		input.close();
		dataList.remove(0);

				for(SectionsDTO obj:dataList)
				{
					for(CourseEvalQuestionsDTO ques:obj.getQuestions())
					{
						ques.setScaleType(dto);
					}
				}
	return dataList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	public List<SurveySectionsDTO> getSurveySections() {
		return surveySections;
	}

	public void setSurveySections(List<SurveySectionsDTO> surveySections) {
		this.surveySections = surveySections;
	}

	public List<SectionsDTO> getSections() {
		return sections;
	}

	public void setSections(List<SectionsDTO> sections) {
		this.sections = sections;
	}



	public ISurveyFacade getFacade() {
		return facade;
	}

	public void setFacade(ISurveyFacade facade) {
		this.facade = facade;
	}

	public ICourseEvalQuestionsFacade getQuesFacade() {
		return quesFacade;
	}

	public void setQuesFacade(ICourseEvalQuestionsFacade quesFacade) {
		this.quesFacade = quesFacade;
	}

	public Integer getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(Integer surveyID) {
		this.surveyID = surveyID;
	}
	public SectionsDTO getProcessSectioDTO() {
		return processSectioDTO;
	}
	public void setProcessSectioDTO(SectionsDTO processSectioDTO) {
		this.processSectioDTO = processSectioDTO;
	}
	public CourseEvalQuestionsDTO getAddedQuestion() {
		return addedQuestion;
	}
	public void setAddedQuestion(CourseEvalQuestionsDTO addedQuestion) {
		this.addedQuestion = addedQuestion;
	}
	public CourseEvalQuestionsDTO getProcessedQues() {
		return processedQues;
	}
	public void setProcessedQues(CourseEvalQuestionsDTO processedQues) {
		this.processedQues = processedQues;
	}
	public SectionsDTO getAddedSection() {
		return addedSection;
	}
	public void setAddedSection(SectionsDTO addedSection) {
		this.addedSection = addedSection;
	}

	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public IStudentAcademicPetFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(IStudentAcademicPetFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	public List<ScaleTypeDTO> getScales() {
		return scales;
	}
	public void setScales(List<ScaleTypeDTO> scales) {
		this.scales = scales;
	}
	public ICouresFacade getCourseFacade() {
		return courseFacade;
	}
	public void setCourseFacade(ICouresFacade courseFacade) {
		this.courseFacade = courseFacade;
	}
	public ScaleTypeDTO getSelectedScaleType() {
		return selectedScaleType;
	}
	public void setSelectedScaleType(ScaleTypeDTO selectedScaleType) {
		this.selectedScaleType = selectedScaleType;
	}
	public FormsStatusDTO getForm() {
		return form;
	}
	public void setForm(FormsStatusDTO form) {
		this.form = form;
	}

	
	
}
