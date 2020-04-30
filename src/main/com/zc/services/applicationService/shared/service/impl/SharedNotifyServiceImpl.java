/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IChangeConcentrationRep;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.ITAJuniorProgramRep;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.emails.model.MailJob;
import main.com.zc.services.presentation.forms.emails.model.MailJobDetail;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SharedNotifyServiceImpl implements ISharedNotifyService {

	@Autowired
	ICoursePetitionRep coursePetitionRep;
	@Autowired
	IEmployeeRepository instructorRepository;
	@Autowired
	IAddDropFormRepository addDropFormRepository;
	@Autowired
	IChangeMajorFormRep changeMajorFormRep;
	@Autowired
	IReadmissionFormRep readmissionFormRep;
	@Autowired
	IOverloadRequestRep overloadRequestRep;
	@Autowired
	IRepeatCourseFormRep courseRepeatRep;
	@Autowired
	IIncompleteGradeRep incompleteGradeRep;
	@Autowired
	ITAJuniorProgramRep juniorTaRep;
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    IPetitionsActionsRep petitionActionRep;
    @Autowired
    IChangeConcentrationRep changeConcenRep;
	@Override
	public void notifayNextStepOwner(CoursePetitionDTO dto) {
		try {
			CoursePetition coursePetition = coursePetitionRep.getById(dto
					.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = coursePetition.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			// will get list of actions 
			List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.ACADEMICPETITION.getValue());
			//Loop on actions 
			String status="";
			for(int i=0;i<actions.size();i++)
			{ 
				//String =action type + instructor name + date +<br/>
				     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				     String date = sdf.format(actions.get(i).getDate().getTime());
				     if(actions.get(i).getInstructor()!=null)
				status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
				     else 
				     {
				    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
				    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
				    	 }
				    	 else 
				    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
				    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
					    	 }
				    		 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.PROCEED)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.DECLINE))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
						    	 }
				     }
			}
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (coursePetition.getStep().equals(PetitionStepsEnum.AUDITING)) {
				// Notify ADmission dept
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(coursePetition.getPerson().getData()
						.getMail());
				studentDTO.setName(coursePetition.getPerson().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
						+ coursePetition.getId() + " is : <br/> Auditing By Admission Department.";
			}else
			if (coursePetition.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				instructor = coursePetition.getCourse().getCourseCoordinator();
			} else if (coursePetition.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(coursePetition.getPerson().getData()
						.getMail());
				studentDTO.setName(coursePetition.getPerson().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
							+ coursePetition.getId() + " is : <br/> Under Review";
				else
				studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
						+ coursePetition.getId() + " is : <br/> "+status;

				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ coursePetition.getCourse().getCourseCoordinator()
									.getName();
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ coursePetition.getCourse().getCourseCoordinator()
									.getName();
				}*/
				studentContent += "<br/> The next step is the Admission Aproval";
				
			} else if (coursePetition.getStep().equals(PetitionStepsEnum.DEAN)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(coursePetition.getPerson().getData()
						.getMail());
				studentDTO.setName(coursePetition.getPerson().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
						+ coursePetition.getId() + " is : <br/> Under Review";
                else 
                	studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
							+ coursePetition.getId() + " is : <br/> "+status;
			/*	if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					
					
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Admission Head approval";
			} else if (coursePetition.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(coursePetition.getPerson().getData()
						.getMail());
				studentDTO.setName(coursePetition.getPerson().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
						+ coursePetition.getId() + " is : <br/> Under view";
                else 
                	studentContent = "We would like to inform you that the current Status of your Academic Petition with ID:"
    						+ coursePetition.getId() + " is : <br/> "+status;
    				
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_PROCESSING)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_PROCESSING;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_PROCESSING)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
			} else if (coursePetition.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(coursePetition.getPerson().getData()
						.getMail());
				studentDTO.setName(coursePetition.getPerson().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that your Academic Petition with ID:"
						+ coursePetition.getId() + " <br/> is under view";
                else 
                	studentContent = "We would like to inform you that your Academic Petition with ID:"
    						+ coursePetition.getId() + " <br/> is "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
			}
			title = "New Academic Petition " + coursePetition.getId();
			studentTitle = "Academic Petition " + coursePetition.getId();
			content = "We would like to inform you that you have a new Academic Petition with ID:"
					+ coursePetition.getId()
					+ " needs an action.";
			//Student Detail
			content += "<br/> <br/> Student ID: "
					+ coursePetition.getPerson().getFileNo()
					+ "<br/> Student Name: "
					+ coursePetition.getPerson().getData()
							.getNameInEnglish()
			+ "<br/> Course Name: "
			+ coursePetition.getCourse().getName();
			if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void notifayNextStepOwner(DropAddFormDTO dto) {
		try {
			DropAddForm dropAddForm = addDropFormRepository
					.getById(dto.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
		/*	String status = dropAddForm.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			
			// will get list of actions 
						List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.DROPADD.getValue());
						//Loop on actions 
						String status="";
						for(int i=0;i<actions.size();i++)
						{
							//String =action type + instructor name + date +<br/>
							     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							     String date = sdf.format(actions.get(i).getDate().getTime());
							     if(actions.get(i).getInstructor()!=null)
							status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
							     else 
							     {
							    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
							    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
							    	 }
							    	 else 
							    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
							    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
								    	 {
								    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
								    	 }
							     }
						}
						
						
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (dropAddForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				
				if(dto.getDroppedCourseIns()==null)	
				instructor = dropAddForm.getMajor().getHeadOfMajorId();
				else
				instructor = dropAddForm.getDroppedCourseIns();
			} else if (dropAddForm.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO
						.setMail(dropAddForm.getStudent().getData().getMail());
				studentDTO.setName(dropAddForm.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
							+ dropAddForm.getId() + " is : <br/> Under Reveiw";
				else
				studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
						+ dropAddForm.getId() + " is : <br/> "+status;
			/*	if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					
					//TODO Uncomment it in phase one / two
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ dropAddForm.getMajor().getHeadOfMajorId()
									.getName();
					//TODO Comment it in phase one / two
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ dropAddForm.getDroppedCourse().getName();
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
			
					//TODO Uncomment it in phase one / two
						studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ dropAddForm.getMajor().getHeadOfMajorId()
									.getName();
					
					//TODO Comment it in phase one / two
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ dropAddForm.getDroppedCourseIns()
									.getName();
					
				}*/
				studentContent += "<br/> The next step is the Admission Approval";
			} else if (dropAddForm.getStep().equals(PetitionStepsEnum.DEAN)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO
						.setMail(dropAddForm.getStudent().getData().getMail());
				studentDTO.setName(dropAddForm.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
						+ dropAddForm.getId() + " is : <br/> Under Review";
				else 
					studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
							+ dropAddForm.getId() + " is : <br/> "+status;
					
				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Admission Head approval";
			} else if (dropAddForm.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO
						.setMail(dropAddForm.getStudent().getData().getMail());
				studentDTO.setName(dropAddForm.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
						+ dropAddForm.getId() + " is : <br/> Under Review";
				else
					studentContent = "We would like to inform you that the current Status of your Drop Petition with ID:"
							+ dropAddForm.getId() + " is : <br/> "+status;
					
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
			} else if (dropAddForm.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO
						.setMail(dropAddForm.getStudent().getData().getMail());
				studentDTO.setName(dropAddForm.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that your Drop Petition with ID:"
						+ dropAddForm.getId() + "  <br/> ";
				studentContent = "We would like to inform you that your Drop Petition with ID:"
						+ dropAddForm.getId() + "  <br/> "+status;
				
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT
							+ " <br/> Please visit the admission office to complete your registration plan";
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
				}
			title = "New Drop Petition " + dropAddForm.getId();
			studentTitle = "Drop Petition " + dropAddForm.getId();
			content = "We would like to inform you that you have a new Drop Petition with ID:"
					+ dropAddForm.getId() + " needs an action.";
			
			content += "<br/><br/> Student ID: "
					+ dropAddForm.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ dropAddForm.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Major Name: "
			+ dropAddForm.getMajor().getMajorName();
		if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
			
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void notifayNextStepOwner(ChangeMajorDTO dto) {

		try {
			ChangeMajorForm changeMajorForm = changeMajorFormRep.getById(dto
					.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = changeMajorForm.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			// will get list of actions 
			List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.CHANGEMAJOR.getValue());
			//Loop on actions 
			String status="";
			for(int i=0;i<actions.size();i++)
			{
				//String =action type + instructor name + date +<br/>
				     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				     String date = sdf.format(actions.get(i).getDate().getTime());
				     if(actions.get(i).getInstructor()!=null)
				status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
				     else 
				     {
				    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
				    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
				    	 }
				    	 else 
				    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
				    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
					    	 }
				     }
			}
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			title = "New Change Major and/or Specialization "
					+ changeMajorForm.getId();
			studentTitle = "Change Major and/or Specialization "
					+ changeMajorForm.getId();
			content = "We would like to inform you that you have a new Change Major and/or Specialization  Petition with ID:"
					+ changeMajorForm.getId()
					+ " needs an action.";
			
			content += "<br/><br/> Student ID: "
					+ changeMajorForm.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ changeMajorForm.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Major Name: "
			+ changeMajorForm.getCurMajor().getMajorName()+"<br/>";
			if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
		
			if (changeMajorForm.getStep()
					.equals(PetitionStepsEnum.UNDER_REVIEW)) {
				
				// Notify Instructor
				if (changeMajorForm.getNewMajor() != null) {
						instructor = changeMajorForm.getNewMajor()
							.getHeadOfMajorId();

					Employee instructorold = changeMajorForm.getCurMajor()
							.getHeadOfMajorId();
					
					String titleold = "Change Major and/or Specialization "
							+ changeMajorForm.getId();
					List<String> oldRecipent = new ArrayList<String>();
					oldRecipent.add(instructorold.getMail());
					SendMailThread sendMailThreadold = new SendMailThread(
							oldRecipent, instructorold.getName(), content,
							titleold);
					/*sendMailThreadold.start();*/
				} else {
					instructor = changeMajorForm.getCurMajor()
							.getHeadOfMajorId();

				}
			}  else if (changeMajorForm.getStep().equals(
					PetitionStepsEnum.UNDER_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that the current Status of your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " is : <br/> "+status;
			/*	if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
			}else if (changeMajorForm.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.DEAN_OF_ACADEMIC);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that the current Status of your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " is : <br/> ";
				/*String insname = "";
				if (changeMajorForm.getNewMajor() != null) {
					insname = changeMajorForm.getNewMajor().getHeadOfMajorId()
							.getName();
				} else {
					insname = changeMajorForm.getCurMajor().getHeadOfMajorId()
							.getName();
				}
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " " + insname;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " " + insname;
				}*/
				
				studentContent += "<br/> The next step is the Dean of Academic approval";
			}else if (changeMajorForm.getStep().equals(
					PetitionStepsEnum.DEAN_OF_ACADIMICS)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.Financial_DEP);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that the current Status of your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " is : <br/> ";
				/*String insname = "";
				if (changeMajorForm.getNewMajor() != null) {
					insname = changeMajorForm.getNewMajor().getHeadOfMajorId()
							.getName();
				} else {
					insname = changeMajorForm.getCurMajor().getHeadOfMajorId()
							.getName();
				}
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " " + insname;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " " + insname;
				}*/
				
				studentContent += "<br/> The next step is the Student Finance Department approval";
			}/* else if (changeMajorForm.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());
				studentTitle = "Change Major and/or Specialization "
						+ changeMajorForm.getId();

				studentContent = "We would like to inform you that the current Status of your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " is : <br/> "+status;
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}
				studentContent += "<br/> The next step is the Admission Head approval";
			} */else if (changeMajorForm.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that the current Status of your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " is : <br/> "+status;
			/*	if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
			} else if (changeMajorForm.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				System.out.println("SendData");
				if (changeMajorForm.getNewMajor() != null
						&& changeMajorForm
								.getStatus()
								.contains(
										Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					instructor = changeMajorForm.getCurMajor()
							.getHeadOfMajorId();
					content = "We would like to inform you that the Major of Student "
							+ changeMajorForm.getStudent().getData()
									.getNameInEnglish()
							+ " has been Changed  from "
							+ changeMajorForm.getCurMajor().getMajorName()
							+ " to "
							+ changeMajorForm.getNewMajor().getMajorName();
					title = "Change Major and/or Specialization "
							+ changeMajorForm.getId();
				}
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(changeMajorForm.getStudent().getData()
						.getMail());
				studentDTO.setName(changeMajorForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that your Change Major Petition with ID:"
						+ changeMajorForm.getId() + " <br/>"+status;
			/*	if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
				}
			
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void notifayNextStepOwner(OverloadRequestDTO dto) {
		try {
			OverloadRequest overLoadRequest = overloadRequestRep.getById(dto
					.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = overLoadRequest.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			// will get list of actions 
						List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
						//Loop on actions 
						String status="";
						for(int i=0;i<actions.size();i++)
						{
							//String =action type + instructor name + date +<br/>
							     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							     String date = sdf.format(actions.get(i).getDate().getTime());
							     if(actions.get(i).getInstructor()!=null)
							status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
							     else 
							     {
							    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
							    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
							    	 }
							    	 else 
							    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
							    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
								    	 {
								    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
								    	 }
							     }
						}
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (overLoadRequest.getStep()
					.equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				instructor = overLoadRequest.getMajor().getHeadOfMajorId();
			} else if (overLoadRequest.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.DEAN_OF_STRATEGIC);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
                
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
						+ overLoadRequest.getId() + " is : <br/> Waiting Action From Registrar";
				else 
					studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
							+ overLoadRequest.getId() + " is : <br/> "+status;
				
				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ overLoadRequest.getMajor().getHeadOfMajorId()
									.getName();
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ overLoadRequest.getMajor().getHeadOfMajorId()
									.getName();
				}*/
				studentContent += "<br/> The next step is the Dean approval";
			} else if (overLoadRequest.getStep().equals(PetitionStepsEnum.DEAN)
					&& (overLoadRequest.getProvostRequired() == null || !overLoadRequest
							.getProvostRequired())) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
						+ overLoadRequest.getId() + " is : <br/> Waiting Action From Registrar";
				else 
					studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
							+ overLoadRequest.getId() + " is : <br/> "+status;
				
			/*	if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Final Discision";
			} else if (overLoadRequest.getStep().equals(PetitionStepsEnum.DEAN)
					&& (overLoadRequest.getProvostRequired() != null && overLoadRequest
							.getProvostRequired())) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository.getByMail(Constants.PROVOST);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
						+ overLoadRequest.getId() + " is : <br/> Waiting Action From Registrar";
				else 
					studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
							+ overLoadRequest.getId() + " is : <br/> "+status;
				
			/*	if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Provost approval";
			} else if (overLoadRequest.getStep().equals(
					PetitionStepsEnum.PROVOST)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
						+ overLoadRequest.getId() + " is : <br/> Waiting Action From Registrar";
				else 
					studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
							+ overLoadRequest.getId() + " is : <br/> "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_PROVOST)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_PROVOST;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_PROVOST)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_PROVOST;
				}*/
				studentContent += "<br/> The next step is the final discision";

			} else if (overLoadRequest.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
						+ overLoadRequest.getId()
						+ " is : <br/> Waiting Action From Registrar";
				else 

					studentContent = "We would like to inform you that the current Status of your OverLoad Request with ID:"
							+ overLoadRequest.getId()
							+ " is : <br/> "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the final discision";
			} else if (overLoadRequest.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(overLoadRequest.getStudent().getData()
						.getMail());
				studentDTO.setName(overLoadRequest.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that your OverLoad Request with ID:"
						+ overLoadRequest.getId() + " <br/> Has been Finished";
				else 
					studentContent = "We would like to inform you that your OverLoad Request with ID:"
							+ overLoadRequest.getId() + " <br/> "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
	}
			title = "New OverLoad Request " + overLoadRequest.getId();
			studentTitle = "OverLoad Request " + overLoadRequest.getId();
			content = "We would like to inform you that you have a new OverLoad Request with ID:"
					+ overLoadRequest.getId()
					+ " needs an action.";
			content += "<br/><br/> Student ID: "
					+ overLoadRequest.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ overLoadRequest.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Major Name: "
			+ overLoadRequest.getMajor().getMajorName()+"<br/>";
			if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
		
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author omnya.alaa
	 *
	 */
	@Override
	public void notifayNextStepOwner(CourseRepeatDTO dto) {
		try {
			RepeatCourseForm repeat = courseRepeatRep.getById(dto.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = repeat.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			
			
			// will get list of actions 
			List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.REPEATECOURSE.getValue());
			//Loop on actions 
			String status="";
			for(int i=0;i<actions.size();i++)
			{
				//String =action type + instructor name + date +<br/>
				     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				     String date = sdf.format(actions.get(i).getDate().getTime());
				     if(actions.get(i).getInstructor()!=null)
				status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
				     else 
				     {
				    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
				    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
				    	 }
				    	 else 
				    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
				    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
					    	 }
				    		 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.PROCEED)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.DECLINE))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
						    	 }
				     }
			}
			
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (repeat.getStep().equals(PetitionStepsEnum.AUDITING)) {
				// Notify ADmission dept
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(repeat.getStudent().getData()
						.getMail());
				studentDTO.setName(repeat.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Course Repeat Form with ID:"
						+ repeat.getId() + " is : <br/> Auditing By Admission Department.";
			}else
			if (repeat.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				//instructor = repeat.getCourse().getCourseCoordinator();
				//TODO 
				//edited as requested from Salma Mohsen , to skip instructor step and set Dean only
				/*
				instructor = repeat.getMajor().getHeadOfMajorId();
				} else if (repeat.getStep().equals(PetitionStepsEnum.INSTRUCTOR)) {
				*/
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.DEAN_OF_ACADEMIC);
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(repeat.getStudent().getData().getMail());
				studentDTO.setName(repeat.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Course Repeat Form with ID:"
						+ repeat.getId() + " is : <br/> Under view";
				else 
					studentContent = "We would like to inform you that the current Status of your Course Repeat Form with ID:"
							+ repeat.getId() + " is : <br/> "+status;
					
				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ repeat.getCourse().getCourseCoordinator()
									.getName();
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ repeat.getCourse().getCourseCoordinator()
									.getName();
				}*/
				studentContent += "<br/> The next step is the Dean approval";
			} else if (repeat.getStep().equals(PetitionStepsEnum.DEAN)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);

			// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(repeat.getStudent().getData().getMail());
				studentDTO.setName(repeat.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Course Repeat Form with ID:"
						+ repeat.getId() + " is : <br/> Under view";
				else 
					studentContent = "We would like to inform you that the current Status of your Course Repeat Form with ID:"
							+ repeat.getId() + " is : <br/> "+status;
				
				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Admission Head approval";
			} else if (repeat.getStep()
					.equals(PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(repeat.getStudent().getData().getMail());
				studentDTO.setName(repeat.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that the current Status of your Course Repeat with ID:"
						+ repeat.getId() + " is : <br/> Under view";
				else 
				studentContent = "We would like to inform you that the current Status of your Course Repeat with ID:"
						+ repeat.getId() + " is : <br/> "+status;
				
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
				} else if (repeat.getStep()
					.equals(PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(repeat.getStudent().getData().getMail());
				studentDTO.setName(repeat.getStudent().getData()
						.getNameInEnglish());

				if(actions.size()==0)
				studentContent = "We would like to inform you that your Course Repeat with ID:"
						+ repeat.getId() + " <br/> Under view";
				else 
					studentContent = "We would like to inform you that your Course Repeat with ID:"
							+ repeat.getId() + " <br/> "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
			}
			title = "New Course Repeat Form " + repeat.getId();
			studentTitle = "Course Repeat Form" + repeat.getId();
			content = "We would like to inform you that you have a new Course Repeat Form with ID:"
					+ repeat.getId()
					+ " needs an action.";
			content += "<br/><br/> Student ID: "
					+ repeat.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ repeat.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Major Name: "
			+ repeat.getMajor().getMajorName()+"<br/>";	
			if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
		
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
				/*	instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				*/	instructorMailRecipent.add(Constants.ADMISSION_DEPT);
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void notifayAtDate(CoursePetition coursePetition,
			Employee instructor) throws Exception {

		/*String status = coursePetition.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		// will get list of actions 
				List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(coursePetition.getId(),FormTypesEnum.ACADEMICPETITION.getValue());
				//Loop on actions 
				String status="";
				for(int i=0;i<actions.size();i++)
				{
					//String =action type + instructor name + date +<br/>
					     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					     String date = sdf.format(actions.get(i).getDate().getTime());
					     if(actions.get(i).getInstructor()!=null)
					status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
					     else 
					     {
					    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
					    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
					    	 }
					    	 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
						    	 }
					    		 else 
						    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.PROCEED)||
						    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.DECLINE))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
							    	 }
					     }
				}
		String title = "Academic Petition Reminder " + coursePetition.getId();
		String content = "We would like to inform you that you have a new Academic Petition with ID:"
				+ coursePetition.getId()
				+ " needs an action.";
		//Student Detail
		content += "<br/> <br/> Student ID: "
				+ coursePetition.getPerson().getFileNo()
				+ "<br/> Student Name: "
				+ coursePetition.getPerson().getData()
						.getNameInEnglish()
		+ "<br/> Course Name: "
		+ coursePetition.getCourse().getName();
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;

		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent, "CoursePetition",
				coursePetition.getId(), coursePetition.getInsNotifyDate());

	}

	private void createMailJob(String recipientName, String title, String content,
			List<String> instructorMailRecipent, String formName,
			Integer formId, Date notifyAt) throws Exception {
		MailJobDetail mailJobDetail = new MailJobDetail();
		mailJobDetail.setTitle(title);
		mailJobDetail.setContent(content);
		mailJobDetail.setBeanName("mailJob");
		mailJobDetail.setRecipentList(instructorMailRecipent);
		mailJobDetail.setJobClass(MailJob.class);
		mailJobDetail.setName("J" + formName + formId);
		mailJobDetail.setGroup("J" + formName);
		mailJobDetail.setFormId(formId);
		mailJobDetail.setFormName(formName);
		mailJobDetail.setNotifyAt(notifyAt);
		Scheduler scheduler = schedulerFactoryBean.getObject();
		scheduler.addJob(mailJobDetail, true);
		// Trigger the job to run At, once
		SimpleTriggerBean trigger = new SimpleTriggerBean();
		trigger.setName("TR" + formName + formId);
		trigger.setGroup("TR" + formName);
		// Tell quartz to schedule the job
		trigger.setStartDelay(20000);
		trigger.setStartTime(notifyAt);
		trigger.setRepeatCount(0);
		trigger.setRepeatInterval(0);
		trigger.setNextFireTime(notifyAt);
		trigger.setJobDetail(mailJobDetail);
		trigger.setJobName("J" + formName + formId);
		trigger.setJobGroup("J" + formName);
		Trigger oldTrig = scheduler.getTrigger("TR" + formName + formId, "TR"
				+ formName);
		if (oldTrig != null) {
			scheduler.rescheduleJob("TR" + formName + formId, "TR" + formName,
					trigger);
		} else {
			scheduler.scheduleJob(trigger);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(CoursePetitionDTO detailedForm, String name)
			throws Exception {
		CoursePetition coursePetition = coursePetitionRep.getById(detailedForm
				.getId());
		coursePetition.setInsNotifyDate(detailedForm.getNotifyAt());
		coursePetitionRep.update(coursePetition);
		notifayAtDate(coursePetition, instructorRepository.getByMail(name));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(DropAddFormDTO detailedForm, String name)
			throws Exception {
		DropAddForm dropAddForm = addDropFormRepository.getById(detailedForm
				.getId());
		dropAddForm.setInsNotifyDate(detailedForm.getNotifyAt());
		addDropFormRepository.update(dropAddForm);
		notifayAtDate(dropAddForm, instructorRepository.getByMail(name));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(ChangeMajorDTO detailedForm, String name)
			throws Exception {
		ChangeMajorForm changeMajorForm = changeMajorFormRep
				.getById(detailedForm.getId());
		changeMajorForm.setInsNotifyDate(detailedForm.getNotifyAt());
		changeMajorFormRep.update(changeMajorForm);
		notifayAtDate(changeMajorForm, instructorRepository.getByMail(name));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(OverloadRequestDTO detailedForm, String name)
			throws Exception {
		OverloadRequest overloadRequest = overloadRequestRep
				.getById(detailedForm.getId());
		overloadRequest.setInsNotifyDate(detailedForm.getNotifyAt());
		overloadRequestRep.update(overloadRequest);
		notifayAtDate(overloadRequest, instructorRepository.getByMail(name));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(CourseRepeatDTO detailedForm, String name)
			throws Exception {
		RepeatCourseForm repeatCourseForm = courseRepeatRep
				.getById(detailedForm.getId());
		repeatCourseForm.setInsNotifyDate(detailedForm.getNotifyAt());
		courseRepeatRep.update(repeatCourseForm);
		notifayAtDate(repeatCourseForm, instructorRepository.getByMail(name));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStatus(String formType, Integer formId, boolean status) {
		Date now = new Date();
		//test if the now is after the time to send the notification to update the database
		//this is to defer between admin notify next step owner and user remind me services
		
		if (formType.equals("CoursePetition")) {
			CoursePetition coursePetition = coursePetitionRep.getById(formId);
			if(coursePetition.getInsNotifyDate().getTime() <= now.getTime())
			{
				coursePetition.setInsSendMail(status);
				coursePetitionRep.update(coursePetition);
			}
		} else if (formType.equals("DropAddForm")) {
			DropAddForm addForm = addDropFormRepository.getById(formId);
			if(addForm.getInsNotifyDate().getTime() <= now.getTime())
			{
				addForm.setInsSendMail(status);
				addDropFormRepository.update(addForm);
			}
			
		} else if (formType.equals("ChangeMajorForm")) {
			ChangeMajorForm changeMajorForm = changeMajorFormRep
					.getById(formId);
			if(changeMajorForm.getInsNotifyDate().getTime() <= now.getTime())
			{
				changeMajorForm.setInsSendMail(status);
				changeMajorFormRep.update(changeMajorForm);
			}
		} else if (formType.equals("OverloadRequest")) {
			OverloadRequest overloadRequest = overloadRequestRep
					.getById(formId);
			if(overloadRequest.getInsNotifyDate().getTime() <= now.getTime())
			{
				overloadRequest.setInsSendMail(status);
				overloadRequestRep.update(overloadRequest);
			}
		} else if (formType.equals("RepeatCourseForm")) {
			RepeatCourseForm repeatCourseForm = courseRepeatRep.getById(formId);
			if(repeatCourseForm.getInsNotifyDate().getTime() <= now.getTime())
			{
				repeatCourseForm.setInsSendMail(status);
				courseRepeatRep.update(repeatCourseForm);
			}
		}

	}

	@Override
	public void notifayAtDate(CoursePetition coursePetition) {
		Employee instructor = null;
		if (coursePetition.getStep().equals(PetitionStepsEnum.AUDITING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		else if (coursePetition.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = coursePetition.getCourse().getCourseCoordinator();
		} else if (coursePetition.getStep()
				.equals(PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_STRATEGIC);
		} else if (coursePetition.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (coursePetition.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		
		try {
			notifayAtDate(coursePetition, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifayAtDate(DropAddForm addForm) {
		Employee instructor = null;
		if (addForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = addForm.getMajor().getHeadOfMajorId();
		} else if (addForm.getStep().equals(PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_STRATEGIC);
		} else if (addForm.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (addForm.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		try {
			notifayAtDate(addForm, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifayAtDate(ChangeMajorForm changeMajorForm) {
		Employee instructor = null;
		if (changeMajorForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			// Notify Instructor
			if (changeMajorForm.getNewMajor() != null) {
				instructor = changeMajorForm.getNewMajor().getHeadOfMajorId();
			} else {
				instructor = changeMajorForm.getCurMajor().getHeadOfMajorId();
			}
		} else if (changeMajorForm.getStep().equals(
				PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_STRATEGIC);
		} else if (changeMajorForm.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (changeMajorForm.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		try {
			notifayAtDate(changeMajorForm, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifayAtDate(OverloadRequest overloadRequest) {
		Employee instructor = null;
		if (overloadRequest.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = overloadRequest.getMajor().getHeadOfMajorId();
		} else if (overloadRequest.getStep().equals(
				PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_STRATEGIC);
		} else if (overloadRequest.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (overloadRequest.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		try {
			notifayAtDate(overloadRequest, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifayAtDate(RepeatCourseForm repeatCourseForm) {
		Employee instructor = null;
		if (repeatCourseForm.getStep().equals(PetitionStepsEnum.AUDITING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}else if (repeatCourseForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = repeatCourseForm.getMajor().getHeadOfMajorId();
		} else if (repeatCourseForm.getStep().equals(
				PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_ACADEMIC);
		} else if (repeatCourseForm.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (repeatCourseForm.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		try {
			notifayAtDate(repeatCourseForm, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void notifayAtDate(DropAddForm dropAddForm, Employee instructor)
			throws Exception {

		/*String status = dropAddForm.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		// will get list of actions 
				List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dropAddForm.getId(),FormTypesEnum.DROPADD.getValue());
				//Loop on actions 
				String status="";
				for(int i=0;i<actions.size();i++)
				{
					//String =action type + instructor name + date +<br/>
					     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					     String date = sdf.format(actions.get(i).getDate().getTime());
					     if(actions.get(i).getInstructor()!=null)
					status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
					     else 
					     {
					    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
					    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
					    	 }
					    	 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
						    	 }
					     }
				}

		String title = "Drop Petition Reminder " + dropAddForm.getId();
		String content  = "We would like to inform you that you have a  Drop Petition with ID:"
				+ dropAddForm.getId() + " needs an action.";
		//Student Detail
		content += "<br/><br/> Student ID: "
				+ dropAddForm.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ dropAddForm.getStudent().getData()
						.getNameInEnglish()
		+ "<br/> Major Name: "
		+ dropAddForm.getMajor().getMajorName()+"<br/>";
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;

		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent, "DropAddForm",
				dropAddForm.getId(), dropAddForm.getInsNotifyDate());

	}

	
	public void notifayAtDate(ChangeMajorForm changeMajorForm,
			Employee instructor) throws Exception {
		// will get list of actions 
				List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(changeMajorForm.getId(),FormTypesEnum.CHANGEMAJOR.getValue());
				//Loop on actions 
				String status="";
				for(int i=0;i<actions.size();i++)
				{
					//String =action type + instructor name + date +<br/>
					     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					     String date = sdf.format(actions.get(i).getDate().getTime());
					     if(actions.get(i).getInstructor()!=null)
					status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
					     else 
					     {
					    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
					    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
					    	 }
					    	 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
						    	 }
					     }
				}
	/*	String status = changeMajorForm.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		
		String title = "Change Major and/or Specialization Petition Reminder "
				+ changeMajorForm.getId();
		String content = "We would like to inform you that you have a  Change Major and/or Specialization Petition with ID:"
				+ changeMajorForm.getId() + " needs an action.";
		//Student Detail
		content += "<br/><br/> Student ID: "
				+ changeMajorForm.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ changeMajorForm.getStudent().getData()
						.getNameInEnglish()
		+ "<br/> Major Name: "
		+ changeMajorForm.getCurMajor().getMajorName()+"<br/>";
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;
		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent,
				"ChangeMajorForm", changeMajorForm.getId(),
				changeMajorForm.getInsNotifyDate());

	}

	
	public void notifayAtDate(OverloadRequest overloadRequest,
			Employee instructor) throws Exception {

		/*String status = overloadRequest.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		// will get list of actions 
		List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(overloadRequest.getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
		//Loop on actions 
		String status="";
		for(int i=0;i<actions.size();i++)
		{
			//String =action type + instructor name + date +<br/>
			     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			     String date = sdf.format(actions.get(i).getDate().getTime());
			     if(actions.get(i).getInstructor()!=null)
			status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
			     else 
			     {
			    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
			    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
			    	 {
			    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
			    	 }
			    	 else 
			    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
			    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
				    	 }
			     }
		}
		
		String title = "OverLoad Request Reminder " + overloadRequest.getId();
		String content = "We would like to inform you that you have a  OverLoad Request with ID:"
				+ overloadRequest.getId() + " needs an action.";
		//Student Detail
		content += "<br/>Student Details: <br/> Student ID: "
				+ overloadRequest.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ overloadRequest.getStudent().getData()
						.getNameInEnglish()
		+ "<br/> Major Name: "
		+ overloadRequest.getMajor().getMajorName()+"<br/>";
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;
		
		List<String> instructorMailRecipent = new ArrayList<String>();

		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent,
				"OverloadRequest", overloadRequest.getId(),
				overloadRequest.getInsNotifyDate());

	}

	
	public void notifayAtDate(RepeatCourseForm repeatCourseForm,
			Employee instructor) throws Exception {

		/*String status = repeatCourseForm.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		// will get list of actions 
		List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(repeatCourseForm.getId(),FormTypesEnum.REPEATECOURSE.getValue());
		//Loop on actions 
		String status="";
		for(int i=0;i<actions.size();i++)
		{
			//String =action type + instructor name + date +<br/>
			     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			     String date = sdf.format(actions.get(i).getDate().getTime());
			     if(actions.get(i).getInstructor()!=null)
			status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
			     else 
			     {
			    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
			    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
			    	 {
			    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
			    	 }
			    	 else 
			    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
			    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
				    	 }
			    		 else 
				    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.PROCEED)||
				    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.DECLINE))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
					    	 }
			     }
		}
		String title = "Repeat Course Form Reminder "
				+ repeatCourseForm.getId();
		String content = "We would like to inform you that you have a  Repeat Course Form with ID:"
				+ repeatCourseForm.getId() + " needs an action.";
		//Student Detail
		content += "<br/>Student Details: <br/> Student ID: "
				+ repeatCourseForm.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ repeatCourseForm.getStudent().getData()
						.getNameInEnglish()
		+ "<br/> Course Name: "
		+ repeatCourseForm.getCourse().getName()+"<br/>";
		
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;
		
		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent,
				"RepeatCourseForm", repeatCourseForm.getId(),
				repeatCourseForm.getInsNotifyDate());

	}

	@Override
	public void removeJobFromScheduler(String jobName, Integer Id) {
		try {
			schedulerFactoryBean.getObject().deleteJob("J" + jobName + Id,
					"J" + jobName);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifayNextStepOwner(IncompleteGradeDTO dto) {
		try {
			IncompleteGrade incompleteGrade = incompleteGradeRep.getById(dto.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = incompleteGrade.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			// will get list of actions 
						List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(incompleteGrade.getId(),FormTypesEnum.INCOMPLETEGRADE.getValue());
						//Loop on actions 
						String status="";
						for(int i=0;i<actions.size();i++)
						{
							//String =action type + instructor name + date +<br/>
							     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							     String date = sdf.format(actions.get(i).getDate().getTime());
							     if(actions.get(i).getInstructor()!=null)
							status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
							     else 
							     {
							    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
							    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
							    	 }
							    	 else 
							    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
							    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
								    	 {
								    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
								    	 }
							     }
						}
						
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (incompleteGrade.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				instructor = incompleteGrade.getInstructor();
				//TODO update scenario removing program advisor step
		/*	} else if (incompleteGrade.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR_OF_COURSE)) {
				// Notify PA
				instructor = incompleteGrade.getMajor().getHeadOfMajorId();*/
				/*// Notify Dean
				instructor = instructorRepository.getByMail(Constants.DEAN_OF_STRATEGIC);
				content = "We would like to inform you that you have a new incomplete grade form with ID:"
						+ incompleteGrade.getId()
						+ " needs an action <br/> Petition Status : <br/>"
						+ status;
				title = "New Incomplete Grade Form " + incompleteGrade.getId();*/
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(incompleteGrade.getStudent().getData()
						.getMail());
				studentDTO.setName(incompleteGrade.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
							+ incompleteGrade.getId() + " is : <br/> Under view";

				else 
				studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
						+ incompleteGrade.getId() + " is : <br/> "+status;

				/*if(incompleteGrade.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ incompleteGrade.getInstructor()
									.getName();
				} else if (status.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ incompleteGrade.getInstructor()
									.getName();
				}*/
				//studentContent += "<br/> The next step is the program advisor approval";
				studentContent += "<br/> The next step is the Dean of Strategic Enrollment Management";
			} 
			//TODO updating scenario and removeing program advisor step
			
			/*else if (incompleteGrade.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {*/
						else if (incompleteGrade.getStep().equals(
								PetitionStepsEnum.INSTRUCTOR_OF_COURSE)) {
				// Notify Dean
				instructor = instructorRepository.getByMail(Constants.DEAN_OF_STRATEGIC);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(incompleteGrade.getStudent().getData()
						.getMail());
				studentDTO.setName(incompleteGrade.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
							+ incompleteGrade.getId() + " is : <br/> Under view";

				else
				studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
						+ incompleteGrade.getId() + " is : <br/>"+status;

				/*if(incompleteGrade.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ incompleteGrade.getMajor().getHeadOfMajorId().getName();
				} else if (status.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ incompleteGrade.getMajor().getHeadOfMajorId().getName();
				}*/
				studentContent += "<br/> The next step is the dean approval";
			}
			else if (incompleteGrade.getStep().equals(PetitionStepsEnum.DEAN)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(incompleteGrade.getStudent().getData()
						.getMail());
				studentDTO.setName(incompleteGrade.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
                	studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
    						+ incompleteGrade.getId() + " is : <br/> Under view";
                else
				studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
						+ incompleteGrade.getId() + " is : <br/> "+status;
				/*if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the admission head approval";
			} else if (incompleteGrade.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(incompleteGrade.getStudent().getData()
						.getMail());
				studentDTO.setName(incompleteGrade.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
							+ incompleteGrade.getId() + " is : <br/> Under view";
				else studentContent = "We would like to inform you that the current Status of your incomplete grade form with ID:"
						+ incompleteGrade.getId() + " is : <br/> "+status;
				/*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD;
				}*/
				studentContent += "<br/> The next step is the Admission Department approval";
			} else if (incompleteGrade.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(incompleteGrade.getStudent().getData()
						.getMail());
				studentDTO.setName(incompleteGrade.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that your incomplete grade form with ID:"
						+ incompleteGrade.getId() + " <br/> "+status;
                else
                	studentContent = "We would like to inform you that your incomplete grade form with ID:"
    						+ incompleteGrade.getId() + " <br/> "+status;
    				
                /*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
			}
			title = "Incomplete Grade Form " + incompleteGrade.getId();
			studentTitle = "Incomplete Grade Form " + incompleteGrade.getId();
			content = "We would like to inform you that you have a new incomplete grade form with ID:"
					+ incompleteGrade.getId()
					+ " needs an action.";
			//Student Detail
			content += "<br/><br/> Student ID: "
					+ incompleteGrade.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ incompleteGrade.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Course Name: "
			+ incompleteGrade.getCourse().getName()+"<br/>";
			
			//Student Detail
			/*if(status!=null && !status.equals(""))*/
			content +="<br/><br/> Petition Status :"+ status;
	
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}




	
	public void notifayAtDate(IncompleteGrade incompleteGrade,
			Employee instructor) throws Exception {
		/*String status = incompleteGrade.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		
		String title = "Incomplete Grade Form Reminder " + incompleteGrade.getId();
		String content = "We would like to inform you that you have a  incomplete grade form with ID:"
				+ incompleteGrade.getId() + " needs an action.";
		
		//Student Detail
		content += "<br/><br/> Student ID: "
				+ incompleteGrade.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ incompleteGrade.getStudent().getData()
						.getNameInEnglish()
		+ "<br/> Course Name: "
		+ incompleteGrade.getCourse().getName()+"<br/>";
	
		//
		// will get list of actions 
		List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(incompleteGrade.getId(),FormTypesEnum.INCOMPLETEGRADE.getValue());
		//Loop on actions 
		String status="";
		for(int i=0;i<actions.size();i++)
		{
			//String =action type + instructor name + date +<br/>
			     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			     String date = sdf.format(actions.get(i).getDate().getTime());
			     if(actions.get(i).getInstructor()!=null)
			status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
			     else 
			     {
			    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
			    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
			    	 {
			    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
			    	 }
			    	 else 
			    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
			    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
				    	 }
			     }
		}
		content +="<br/><br/> Petition Status :"+ status;
		
		//
		List<String> instructorMailRecipent = new ArrayList<String>();

		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent, "IncompleteGrade",
				incompleteGrade.getId(), incompleteGrade.getInsNotifyDate());
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(IncompleteGradeDTO incompleteGrade, String name)
			throws Exception {
		IncompleteGrade incomplete = incompleteGradeRep
				.getById(incompleteGrade.getId());
		incomplete.setInsNotifyDate(incompleteGrade.getNotifyAt());
		incompleteGradeRep.update(incomplete);
		notifayAtDate(incomplete, instructorRepository.getByMail(name));
		
	}

	@Override
	public void notifayAtDate(IncompleteGrade incompleteGrade) {
		Employee instructor = null;
		if (incompleteGrade.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = instructorRepository.getById(incompleteGrade.getInstructor().getId());
		} else if (incompleteGrade.getStep()
				.equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_STRATEGIC);
		}
		//TODO updating scenario and removing program advisor step
		/*else if (incompleteGrade.getStep()
				.equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)) {
			instructor = instructorRepository.getById(incompleteGrade.getMajor().getHeadOfMajorId().getId());
		} */
		else if (incompleteGrade.getStep().equals(PetitionStepsEnum.DEAN)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (incompleteGrade.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		
		try {
			notifayAtDate(incompleteGrade, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void notifayNextStepOwner(TAJuniorProgramDTO dto) {
		try {
			TAJuniorProgram juniorTAForm = juniorTaRep.getById(dto.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
		
			// will get list of actions 
						List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(juniorTAForm.getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
						//Loop on actions 
						String status="";
						for(int i=0;i<actions.size();i++)
						{
							
							     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							     String date = sdf.format(actions.get(i).getDate().getTime());
							     if(actions.get(i).getInstructor()!=null)
							status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
							     else 
							     {
							    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
							    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
							    	 }
							    	 else 
							    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
							    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
								    	 {
								    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
								    	 }
							     }
						}
						
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if (juniorTAForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify Instructor
				instructor = juniorTAForm.getCourse().getCourseCoordinator();
				//TODO update scenario removing program advisor step
			} else if (juniorTAForm.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {
				// Notify PA
				instructor = juniorTAForm.getMajor().getHeadOfMajorId();
				/*// Notify Dean
				instructor = instructorRepository.getByMail(Constants.DEAN_OF_STRATEGIC);
				content = "We would like to inform you that you have a new incomplete grade form with ID:"
						+ incompleteGrade.getId()
						+ " needs an action <br/> Petition Status : <br/>"
						+ status;
				title = "New Incomplete Grade Form " + incompleteGrade.getId();*/
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(juniorTAForm.getStudent().getData()
						.getMail());
				studentDTO.setName(juniorTAForm.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your Junior TA Request with ID:"
							+ juniorTAForm.getId() + " is : <br/> Under view";

				else 
				studentContent = "We would like to inform you that the current Status of your Junior TA Request with ID:"
						+ juniorTAForm.getId() + " is : <br/> "+status;

				/*if(incompleteGrade.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ incompleteGrade.getInstructor()
									.getName();
				} else if (status.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ incompleteGrade.getInstructor()
									.getName();
				}*/
				//studentContent += "<br/> The next step is the program advisor approval";
				studentContent += "<br/> The next step is the Program advisor approval";
			} 
			//TODO updating scenario and removeing program advisor step
			
			/*else if (incompleteGrade.getStep().equals(
					PetitionStepsEnum.INSTRUCTOR)) {*/
						else if (juniorTAForm.getStep().equals(
								PetitionStepsEnum.PA)) {
				// Notify Dean
				instructor = instructorRepository.getByMail(Constants.DEAN_OF_ACADEMIC);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(juniorTAForm.getStudent().getData()
						.getMail());
				studentDTO.setName(juniorTAForm.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current Status of your Junior TA Request with ID:"
							+ juniorTAForm.getId() + " is : <br/> Under view";

				else
				studentContent = "We would like to inform you that the current Status of your Junior Ta Request with ID:"
						+ juniorTAForm.getId() + " is : <br/>"+status;

				/*if(incompleteGrade.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " "
							+ incompleteGrade.getMajor().getHeadOfMajorId().getName();
				} else if (status.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " "
							+ incompleteGrade.getMajor().getHeadOfMajorId().getName();
				}*/
				studentContent += "<br/> The next step is the Dean of Academic Affairs approval";
			}
 else if (juniorTAForm.getStep().equals(
					PetitionStepsEnum.DEAN)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(juniorTAForm.getStudent().getData()
						.getMail());
				studentDTO.setName(juniorTAForm.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that your Junior TA Request with ID:"
						+ juniorTAForm.getId() + " <br/> "+status;
                else
                	studentContent = "We would like to inform you that your Junior TA Request with ID:"
    						+ juniorTAForm.getId() + " <br/> "+status;
    				
                /*if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
			}
			title = "Junior TA Request " + juniorTAForm.getId();
			studentTitle = "Junior TA Request " + juniorTAForm.getId();
			content = "We would like to inform you that you have a new Junior TA Request with ID:"
					+ juniorTAForm.getId()
					+ " needs an action.";
			//Student Detail
			content += "<br/><br/> Student ID: "
					+ juniorTAForm.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ juniorTAForm.getStudent().getData()
							.getNameInEnglish()
			+ "<br/> Course Name: "
			+ juniorTAForm.getCourse().getName()+"<br/>";
			
			//Student Detail
			/*if(status!=null && !status.equals(""))*/
			content +="<br/><br/> Petition Status :"+ status;
	
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					//instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	@Override
	public void notifayAtDate(TAJuniorProgram juniorTAForm,
			Employee instructor) throws Exception {
		/*
		 * String status = incompleteGrade.getStatus()
		 * .replace(Constants.PETITION_STATUS_UNDER_REVIEW, "") .replace("\n",
		 * "<br/>");
		 */

		String title = "Junior TA Request Reminder "
				+ juniorTAForm.getId();
		String content = "We would like to inform you that you have a Junior TA Request with ID:"
				+ juniorTAForm.getId() + " needs an action.";

		// Student Detail
		content += "<br/><br/> Student ID: "
				+ juniorTAForm.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ juniorTAForm.getStudent().getData().getNameInEnglish()
				+ "<br/> Course Name: " + juniorTAForm.getCourse().getName()
				+ "<br/> Major Name: " + juniorTAForm.getMajor().getMajorName()
				+ "<br/>";

		//
		// will get list of actions
		List<PetitionsActions> actions = petitionActionRep
				.getByPetitionIDAndForm(juniorTAForm.getId(),
						FormTypesEnum.JUNIORTAPROGRAM.getValue());
		// Loop on actions
		String status = "";
		for (int i = 0; i < actions.size(); i++) {
			// String =action type + instructor name + date +<br/>
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(actions.get(i).getDate().getTime());
			if (actions.get(i).getInstructor() != null)
				status += actions.get(i).getActionType().getName() + " By "
						+ actions.get(i).getInstructor().getName() + " (Date :"
						+ date + ")" + "<br/>";
			else {
				if (actions.get(i).getActionType()
						.equals(PetitionActionTypeEnum.Admission_Approved)
						|| actions
								.get(i)
								.getActionType()
								.equals(PetitionActionTypeEnum.Admission_Refused)) {
					status += actions.get(i).getActionType().getName() + " By "
							+ "Admission Head " + "(Date :" + date + ")"
							+ "<br/>";
				} else if (actions.get(i).getActionType()
						.equals(PetitionActionTypeEnum.Mark_As_Done_Approving)
						|| actions
								.get(i)
								.getActionType()
								.equals(PetitionActionTypeEnum.Mark_As_Done_Refusing)) {
					status += actions.get(i).getActionType().getName() + " By "
							+ "Registrar " + "(Date :" + date + ")" + "<br/>";
				}
			}
		}
		content += "<br/><br/> Petition Status :" + status;

		//
		List<String> instructorMailRecipent = new ArrayList<String>();

		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(), title, content,
				instructorMailRecipent, "TAJuniorProgram",
				juniorTAForm.getId(), juniorTAForm.getInsNotifyDate());
		
	}

	@Override
	public void notifyAt(TAJuniorProgramDTO detailedForm, String name)
			throws Exception {
		TAJuniorProgram juniorTA = juniorTaRep
				.getById(detailedForm.getId());
		juniorTA.setInsNotifyDate(detailedForm.getNotifyAt());
		juniorTaRep.update(juniorTA);
		notifayAtDate(juniorTA, instructorRepository.getByMail(name));
		
	}

	@Override
	public void notifayAtDate(TAJuniorProgram juniorTAForm) {
		Employee instructor = null;
		if (juniorTAForm.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = instructorRepository.getById(juniorTAForm.getCourse().getCourseCoordinator().getId());
		} else if (juniorTAForm.getStep()
				.equals(PetitionStepsEnum.INSTRUCTOR)) {
			instructor = instructorRepository.getById(juniorTAForm.getMajor().getHeadOfMajorId().getId());
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_ACADEMIC);
		}
		else if (juniorTAForm.getStep()
				.equals(PetitionStepsEnum.PA)) {
			instructor = instructorRepository
					.getByMail(Constants.DEAN_OF_ACADEMIC);
		}
		
		
		try {
			notifayAtDate(juniorTAForm, instructor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void notifayNextStepOwner(ChangeConcentrationDTO dto) {
		try {
			ChangeConcentration form = changeConcenRep.getById(dto.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
		
			// will get list of actions 
						List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm( form.getId(),FormTypesEnum.CHANGECONCENTRATION.getValue());
						//Loop on actions 
						String status="";
						for(int i=0;i<actions.size();i++)
						{
							
							     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							     String date = sdf.format(actions.get(i).getDate().getTime());
							     if(actions.get(i).getInstructor()!=null)
							    	 status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
							     else 
							     {
							    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
							    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							    	 {
							    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
							    	 }
							    	 else 
							    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
							    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
								    	 {
								    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
								    	 }
							     }
						}
						
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			if ( form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
				// Notify PA
				instructor =  form.getMajor().getHeadOfMajorId();
				status="Under Review";
			} 
			else if (form.getStep().equals(PetitionStepsEnum.PA)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_HEAD);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(form.getStudent().getData()
						.getMail());
				studentDTO.setName(form.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
                	studentContent = "We would like to inform you that the current status for change of your concentration request with ID:"
    						+ form.getId() + " is : <br/> Under view";
                else
				studentContent = "We would like to inform you that the current status for change of your concentration request with ID:"
						+ form.getId() + " is : <br/> "+status;
			
				studentContent += "<br/> The next step is the admission head approval";
			} else if (form.getStep().equals(
					PetitionStepsEnum.ADMISSION_PROCESSING)) {
				// Notify ADMISSION_DEPT
				instructor = instructorRepository
						.getByMail(Constants.ADMISSION_DEPT);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(form.getStudent().getData()
						.getMail());
				studentDTO.setName(form.getStudent().getData()
						.getNameInEnglish());
				if(actions.size()==0)
					studentContent = "We would like to inform you that the current status for change of your concentration request with ID:"
							+ form.getId() + " is : <br/> Under view";
				else studentContent = "We would like to inform you that the current status for change of your concentration request with ID:"
						+ form.getId() + " is : <br/> "+status;
			
				studentContent += "<br/> The next step is the Admission Department finalizing";
			} else if (form.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(form.getStudent().getData()
						.getMail());
				studentDTO.setName(form.getStudent().getData()
						.getNameInEnglish());
                if(actions.size()==0)
				studentContent = "We would like to inform you that your request for changing your concentration with ID:"
						+ form.getId() + " <br/> "+status;
                else
                	studentContent = "We would like to inform you that your request for changing your concentration  with ID:"
    						+ form.getId() + " <br/> "+status;
    		
			}
			title = "Change of Concentration(ID:"+  form.getId()+")";
			studentTitle = "Change of Concentration Request " +  form.getId();
			content = "We would like to inform you that you have a new change of concentration request with ID:"
					+  form.getId()
					+ " needs an action.";
			//Student Detail
			content += "<br/><br/><b>Student ID:</b> "
					+  form.getStudent().getFileNo()
					+ "<br/><b>Student Name:</b> "
					+  form.getStudent().getData()
							.getNameInEnglish()
			+ "<br/><b>Major Name: </b>"
			+  form.getMajor().getMajorName()+"<br/>"
			+ "<b>Current Concentration : </b>"
			+  form.getCurrentConcen().getName()+"<br/>"
			+ "<b>New Concentration : </b>"
			+  form.getNewConcen().getName()+"<br/>";
			
			//Student Detail
			/*if(status!=null && !status.equals(""))*/
			content +="<b>Petition Status : </b>"+ status;
	
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					//instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
		
	}

	@Override
	public void notifayAtDate(ChangeConcentration petition, Employee instructor) throws Exception {
		// will get list of actions
		List<PetitionsActions> actions = petitionActionRep.getByPetitionIDAndForm(petition.getId(),
				FormTypesEnum.CHANGECONCENTRATION.getValue());
		// Loop on actions
		String status = "";
		for (int i = 0; i < actions.size(); i++) {
			// String =action type + instructor name + date +<br/>
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(actions.get(i).getDate().getTime());
			if (actions.get(i).getInstructor() != null){
				if(actions.get(i).getActionType()!=null)
				status += actions.get(i).getActionType().getName() + " By " + actions.get(i).getInstructor().getName()
						+ " (Date :" + date + ")" + "<br/>";
			}
			else {
				if (actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)
						|| actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused)) {
					status += actions.get(i).getActionType().getName() + " By " + "Admission Head " + "(Date :" + date
							+ ")" + "<br/>";
				} else if (actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)
						|| actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing)) {
					status += actions.get(i).getActionType().getName() + " By " + "Registrar " + "(Date :" + date + ")"
							+ "<br/>";
				}
			}
		}
		String title = "Change of concentration reminder (ID:" + petition.getId()+")";
		String content = "We would like to inform you that you have a change of concentration request with id :"
				+ petition.getId() + " needs an action.";
		// Student Detail
		content += "<br/><br/><b>Student ID: </b>" + petition.getStudent().getFileNo() + "<br/><b> Student Name: </b>"
				+ petition.getStudent().getData().getNameInEnglish() + "<br/> <b>Major Name: </b>"
				+ petition.getMajor().getMajorName() + "<br/>"
				+ "<b>Current concentration: </b>"+ petition.getCurrentConcen().getName() + "<br/>"
				+ "<b>New concentration: </b>"+ petition.getNewConcen().getName();
		if (status != null && !status.equals(""))
			content += "<br/><b>Petition Status : </b>" + status;
		else
			content += "<br/><b>Petition Status : </b>" + "Under Review";
		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(), title, content, instructorMailRecipent, "ChangeConcentration",
				petition.getId(), petition.getInsNotifyDate());
		
	}

	@Override
	public void notifayAtDate(ChangeConcentrationDTO petition, String name) throws Exception {
		ChangeConcentration changeConcen = changeConcenRep.getById(petition
				.getId());
		changeConcen.setInsNotifyDate(petition.getNotifyAt());
		changeConcenRep.update(changeConcen);
		notifayAtDate(changeConcen, instructorRepository.getByMail(name));

		
	}

	@Override
	public void notifayAtDate(ChangeConcentration changeOfConcen) {
		Employee instructor = null;
		if (changeOfConcen.getStep().equals(PetitionStepsEnum.UNDER_REVIEW)) {
			instructor = changeOfConcen.getMajor().getHeadOfMajorId();
		} else if (changeOfConcen.getStep()
				.equals(PetitionStepsEnum.PA)) {
		
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_HEAD);
		} else if (changeOfConcen.getStep().equals(
				PetitionStepsEnum.ADMISSION_PROCESSING)) {
			instructor = instructorRepository
					.getByMail(Constants.ADMISSION_DEPT);
		}
		
		try {
			notifayAtDate(changeOfConcen, instructor);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void notifyAt(ChangeConcentrationDTO detailedForm, String name) throws Exception {
		ChangeConcentration change = changeConcenRep.getById(detailedForm
				.getId());
		change.setInsNotifyDate(detailedForm.getNotifyAt());
		changeConcenRep.update(change);
		notifayAtDate(change, instructorRepository.getByMail(name));
	
	}

	@Override
	public void notifayNextStepOwner(ReadmissionDTO dto) {

		try {
			ReadmissionForm readmissionForm = readmissionFormRep.getById(dto
					.getId());
			Employee instructor = null;
			String content = "";
			String title = "";
			StudentDTO studentDTO = null;
			String studentContent = "";
			String studentTitle = "";
			/*String status = readmissionForm.getStatus()
					.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
					.replace("\n", "<br/>");*/
			// will get list of actions 
			List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(dto.getId(),FormTypesEnum.READMISSION.getValue());
			//Loop on actions 
			String status="";
			for(int i=0;i<actions.size();i++)
			{
				//String =action type + instructor name + date +<br/>
				     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				     String date = sdf.format(actions.get(i).getDate().getTime());
				     if(actions.get(i).getInstructor()!=null)
				status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
				     else 
				     {
				    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
				    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
				    	 {
				    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
				    	 }
				    	 else 
				    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
				    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Department " +"(Date :"+date+")"+"<br/>";
					    	 }
				     }
			}
			
			List<String> instructorMailRecipent = new ArrayList<String>();
			List<String> studentMailRecipent = new ArrayList<String>();
			title = "New Readmission "
					+ readmissionForm.getId();
			studentTitle = "Readmission "
					+ readmissionForm.getId();
			content = "We would like to inform you that you have a new Readmission   Petition with ID:"
					+ readmissionForm.getId()
					+ " needs an action.";
			
			content += "<br/><br/> Student ID: "
					+ readmissionForm.getStudent().getFileNo()
					+ "<br/> Student Name: "
					+ readmissionForm.getStudent().getData()
							.getNameInEnglish()
			+ "<br/>";
			if(status!=null && !status.equals(""))
			content +="<br/><br/> Petition Status :"+ status;
		System.out.println("Dakrory:OKYa");
			if (readmissionForm.getStep()
					.equals(PetitionStepsEnum.UNDER_REVIEW)) {
					instructor = instructorRepository
							.getByMail(Constants.ADMISSION_DEPT);

					studentContent = "We would like to inform you that your Readmission Petition with ID:"
							+ readmissionForm.getId() + " <br/>"+ "Waiting Registrar Action";
					String titleold = "Readmission  "
							+ readmissionForm.getId();
					List<String> oldRecipent = new ArrayList<String>();
					// Notify Student
					studentDTO = new StudentDTO(); 
					studentDTO.setMail(readmissionForm.getStudent().getData()
							.getMail());
					studentDTO.setName(readmissionForm.getStudent().getData()
							.getNameInEnglish());
					
					oldRecipent.add(studentDTO.getMail());
					SendMailThread sendMailThreadold = new SendMailThread(
							oldRecipent, studentDTO.getName(), studentContent,
							titleold);

					sendMailThreadold.start();
					
					SendMailThread sendMailThreadnew = new SendMailThread(
							oldRecipent, instructor.getName(), content,
							titleold);
					sendMailThreadnew.start();
				
			} else if (readmissionForm.getStep().equals(
					PetitionStepsEnum.DEAN_OF_ACADIMICS)) {
				// Notify DEAN
				instructor = instructorRepository
						.getByMail(Constants.DEAN_OF_ACADEMIC);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(readmissionForm.getStudent().getData()
						.getMail());
				studentDTO.setName(readmissionForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that the current Status of your Readmission Petition with ID:"
						+ readmissionForm.getId() + " is : <br/> ";
				/*String insname = "";
				if (readmissionForm.getNewMajor() != null) {
					insname = readmissionForm.getNewMajor().getHeadOfMajorId()
							.getName();
				} else {
					insname = readmissionForm.getCurMajor().getHeadOfMajorId()
							.getName();
				}
				if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_INS
							+ " " + insname;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_INS)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_INS
							+ " " + insname;
				}*/
				
				studentContent += "<br/> The next step is the Dean of Academics approval";
			} else if (readmissionForm.getStep().equals(PetitionStepsEnum.DEAN)) {
				// Notify ADMISSION_HEAD
				instructor = instructorRepository
						.getByMail(Constants.DEAN_OF_STRATEGIC);

				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(readmissionForm.getStudent().getData()
						.getMail());
				studentDTO.setName(readmissionForm.getStudent().getData()
						.getNameInEnglish());
				studentTitle = "Readmission "
						+ readmissionForm.getId();

				studentContent = "We would like to inform you that the current Status of your Readmission Petition with ID:"
						+ readmissionForm.getId() + " is : <br/> "+status;
			/*	if (status.contains(Constants.PETITION_STATUS_APPROVED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEAN;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEAN)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEAN;
				}*/
				studentContent += "<br/> The next step is the Dean of Strategic approval";
			} else if (readmissionForm.getStep().equals(
					PetitionStepsEnum.ADMISSION_DEPT)) {
				
				// Notify Student
				studentDTO = new StudentDTO();
				studentDTO.setMail(readmissionForm.getStudent().getData()
						.getMail());
				studentDTO.setName(readmissionForm.getStudent().getData()
						.getNameInEnglish());

				studentContent = "We would like to inform you that your Readmission Petition with ID:"
						+ readmissionForm.getId() + " <br/>"+status;
			/*	if (status
						.contains(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT;
				} else if (status
						.contains(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					studentContent += Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT;
				}*/
				}
			
			if (instructor != null) {
				if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
					instructorMailRecipent.add(instructor.getMail());
					instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
					instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
					instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
					instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
				} else {
					instructorMailRecipent.add(instructor.getMail());
				}
				SendMailThread sendMailThread = new SendMailThread(
						instructorMailRecipent, instructor.getName(), content,
						title);
				sendMailThread.start();
			}
			if (studentDTO != null) {
				studentMailRecipent.add(studentDTO.getMail());
				SendMailThread sendMailThread = new SendMailThread(
						studentMailRecipent, studentDTO.getName(),
						studentContent, studentTitle);
				sendMailThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void notifyAt(ReadmissionDTO detailedForm, String name) throws Exception {
		// TODO Auto-generated method stub
		ReadmissionForm readmissionForm = readmissionFormRep
				.getById(detailedForm.getId());
		readmissionForm.setInsNotifyDate(detailedForm.getNotifyAt());
		readmissionFormRep.update(readmissionForm);
		notifayAtDate(readmissionForm, instructorRepository.getByMail(name));
	}

	
	public void notifayAtDate(ReadmissionForm readmissionForm,
			Employee instructor) throws Exception {
		// will get list of actions 
				List<PetitionsActions> actions=petitionActionRep.getByPetitionIDAndForm(readmissionForm.getId(),FormTypesEnum.READMISSION.getValue());
				//Loop on actions 
				String status="";
				for(int i=0;i<actions.size();i++)
				{
					//String =action type + instructor name + date +<br/>
					     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					     String date = sdf.format(actions.get(i).getDate().getTime());
					     if(actions.get(i).getInstructor()!=null)
					status+=actions.get(i).getActionType().getName()+" By "+actions.get(i).getInstructor().getName() +" (Date :"+date+")"+"<br/>";
					     else 
					     {
					    	 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
					    			 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
					    	 {
					    		 status+=actions.get(i).getActionType().getName()+" By "+"Admission Head " +"(Date :"+date+")"+"<br/>";
					    	 }
					    	 else 
					    		 if(actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
					    				 actions.get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
						    	 {
						    		 status+=actions.get(i).getActionType().getName()+" By "+"Registrar " +"(Date :"+date+")"+"<br/>";
						    	 }
					     }
				}
	/*	String status = readmissionForm.getStatus()
				.replace(Constants.PETITION_STATUS_UNDER_REVIEW, "")
				.replace("\n", "<br/>");*/
		
		String title = "Readmission Petition Reminder "
				+ readmissionForm.getId();
		String content = "We would like to inform you that you have a  Readmission Petition with ID:"
				+ readmissionForm.getId() + " needs an action.";
		//Student Detail
		content += "<br/><br/> Student ID: "
				+ readmissionForm.getStudent().getFileNo()
				+ "<br/> Student Name: "
				+ readmissionForm.getStudent().getData()
						.getNameInEnglish()
		+ "<br/>";
		if(status!=null && !status.equals(""))
		content +="<br/><br/> Petition Status :"+ status;
		List<String> instructorMailRecipent = new ArrayList<String>();
		if (instructor.getMail().equals(Constants.ADMISSION_DEPT)) {
			instructorMailRecipent.add(instructor.getMail());
			instructorMailRecipent.add("raramzy@zewailcity.edu.eg");
			instructorMailRecipent.add("htharwat@zewailcity.edu.eg");
			instructorMailRecipent.add("dmohy@zewailcity.edu.eg");
			instructorMailRecipent.add("smohsen@zewailcity.edu.eg");
		} else {
			instructorMailRecipent.add(instructor.getMail());
		}

		createMailJob(instructor.getName(),title, content, instructorMailRecipent,
				"ReadmissionForm", readmissionForm.getId(),
				readmissionForm.getInsNotifyDate());

	}

}