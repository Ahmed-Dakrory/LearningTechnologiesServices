/**
 * 
 */
package main.com.zc.services.presentation.dashboard.facade.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.dashboard.IDashboardAppService;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.dashboard.dto.AcademicPetitionDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.AddDropDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.ChangeMajorDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.CourseRepeatDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.DashboardElement;
import main.com.zc.services.presentation.dashboard.dto.IncompleteGradeDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.JuniorTADashboardElement;
import main.com.zc.services.presentation.dashboard.dto.OverloadRequestDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.ReadmissionDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.course_replacement_formDashboardElement;
import main.com.zc.services.presentation.dashboard.facade.IDashboardFacade;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacement;
import main.com.zc.shared.appService.IPersonGetDataAppService;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("dashboardFacadeImpl")
public class DashboardFacadeImpl implements IDashboardFacade {

	@Autowired
	IDashboardAppService dashboardAppServ;
	
	@Autowired
	IPersonGetDataAppService personAPPService;
	


	
	 
	@Override
	public List<DashboardElement> fillAdminDashboardElements()
	{
		List<DashboardElement> elements = null;
		
		//get the user type
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userMail = authentication.getName();
		
		if(userMail.equals(Constants.DEAN_OF_STRATEGIC))
		{
			elements = new ArrayList<DashboardElement>();
			
			fillAdminDashboardStratgic(elements);
		}else if(userMail.equals(Constants.DEAN_OF_ACADEMIC))
		{
			elements = new ArrayList<DashboardElement>();
			
			fillAdminDashboardAcademic(elements);
		}
		
		return elements;
	}
	
	@Override
	public List<DashboardElement> fillDashboardElements()
	{
		List<DashboardElement> elements = new ArrayList<DashboardElement>();
		
		//get the user type
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userMail = authentication.getName();
		
		if(userMail.startsWith("S-") || userMail.startsWith("s-")||StringUtils.isNumeric(userMail.substring(0, 4)))
		{
			//student email
			PersonDataDTO personData = personAPPService.getPersonByPersonMail(userMail);
			if(personData != null)
				fillStudentDashboard(elements,personData.getId());
		}
		else
		{ 
			
			 //user email
			 if(userMail.equalsIgnoreCase(Constants.DEAN_OF_STRATEGIC)) {
				 fillDeanDashboardStrategic(elements,userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.ADMISSION_HEAD)) {
				 fillAddmissionHeadDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.Financial_DEP)) {
				 fillAddmissionFinanceDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.ADMISSION_DEPT)) {
				 fillAdmissionDepartmentDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.DEAN_OF_ACADEMIC)) {
				 fillDeanDashboardAcademic(elements,userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.PROVOST)) {
				 fillProvostDashboard(elements, userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.LTS_SYSTEM_ADMIN)) {
				 fillAdminDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.ACCREDITION_ENG_DEP)) {
				 fillAccredEngDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.ACCREDITION_SCI_DEP)) {
				 fillAccredSciDashboard(elements);
			 }else {
				 fillInstructorDashboard(elements, userMail);
			 }
			
		}
		
		return elements;
	}
	
	private void fillDeanDashboardStrategic(List<DashboardElement> elements,String mail)
	{
		//petition
		//List<CoursePetition> courseForms = dashboardAppServ.getDeanAcademicPetitionsPending();
		//Integer count = 0;
		//if(courseForms != null)
		//	count = courseForms.size();
		//count += dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		List<DropAddForm> dropAddForms = dashboardAppServ.getDeanAddDropPending();
		Integer count = 0;
		if(dropAddForms != null)
			count = dropAddForms.size();
		count += dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				Integer changMajorForms =  dashboardAppServ.getInstructorChangeMajorPending(mail);
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms;
				
				
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getDeanReadmissionPending();
				count = 0;
				if(changMajorForms != null)
					count = readmissionForms.size();
				count += dashboardAppServ.getInstructorReadmissionPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString()));
		
				//course_replacement_form
				Integer course_replacement_formForms = dashboardAppServ.getDeanCourseReplacementFormsPending();
				count = 0;
				if(changMajorForms != null)
					count = course_replacement_formForms;
				elements.add(new course_replacement_formDashboardElement(count.toString()));
		
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getDeanOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getDeanIncompleteGradePending();
		count += dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 *Junior TA dashboard 
		 */
		count = dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString()));
		
	}
	
	
	private void fillAccredEngDashboard(List<DashboardElement> elements)
	{
		
		Integer count=0;
		//course replacement
				Integer formsCR =  dashboardAppServ.getAccreditionEngHeadCourseReplacementPending();
				count = 0;
				if(formsCR != null)
					count = formsCR;
				
				
				elements.add(new course_replacement_formDashboardElement(count.toString()));
				
	}
	
	
	private void fillAccredSciDashboard(List<DashboardElement> elements)
	{
		
		Integer count=0;
		//course replacement
				Integer formsCR =  dashboardAppServ.getAccreditionSciHeadCourseReplacementPending();
				count = 0;
				if(formsCR != null)
					count = formsCR;
				
				
				elements.add(new course_replacement_formDashboardElement(count.toString()));
				
		
	}
	

	private void fillDeanDashboard(List<DashboardElement> elements,String mail)
	{
		//petition
		//List<CoursePetition> courseForms = dashboardAppServ.getDeanAcademicPetitionsPending();
		//Integer count = 0;
		//if(courseForms != null)
		//	count = courseForms.size();
		//count += dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		List<DropAddForm> dropAddForms = dashboardAppServ.getDeanAddDropPending();
		Integer count = 0;
		if(dropAddForms != null)
			count = dropAddForms.size();
		count += dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getDeanChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				count += dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getDeanReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				count += dashboardAppServ.getInstructorReadmissionPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString()));
		
				
				//course_replacement_form
				List<course_replacement_formForm> course_replacement_formForms = dashboardAppServ.getDeancourse_replacement_formPending();
				count = 0;
				if(course_replacement_formForms != null)
					count = course_replacement_formForms.size();
				count += dashboardAppServ.getInstructorcourse_replacement_formPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new course_replacement_formDashboardElement(count.toString()));
		
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getDeanOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getDeanCourseRepeatPending();
		count += dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getDeanIncompleteGradePending();
		count += dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 *Junior TA dashboard 
		 */
		count = dashboardAppServ.getDeanJuniorTAPending();
		count += dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString()));
		
	}
	private void fillDeanDashboardAcademic(List<DashboardElement> elements,String mail)
	{
	
		//petition
			//Integer	count = dashboardAppServ.getInstructorAcademicPetitionsPending(mail);	
			//elements.add(new AcademicPetitionDashboardElement(count.toString()));
				
				//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
				elements.add(new AddDropDashboardElement(count.toString()));
				
				//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getDeanChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				count += dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				//readmission
				count = dashboardAppServ.getInstructorReadmissionPending(Constants.DEAN_OF_ACADEMIC_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString()));
				
				
				
				
				//overload
				count = dashboardAppServ.getInstructorOverloadRequestPending(mail);
				elements.add(new OverloadRequestDashboardElement(count.toString()));
				
				//course repeat
				count = dashboardAppServ.getDeanCourseRepeatPending();
				count += dashboardAppServ.getInstructorCourseRepeatPending(mail);
				elements.add(new CourseRepeatDashboardElement(count.toString()));
				
				
				/**
				 * @author Omnya
				 *
				 *Incomplete grade dashboard 
				 */
				count = dashboardAppServ.getInstructorIncompleteGradePending(mail);
				elements.add(new IncompleteGradeDashboardElement(count.toString()));
				
				/**
				 * @author Omnya
				 *
				 *Junior TA dashboard 
				 */
				count = dashboardAppServ.getDeanJuniorTAPending();
				count += dashboardAppServ.getInstructorJuniorTAPending(mail);
				elements.add(new JuniorTADashboardElement(count.toString()));
			
	}
	
	private void fillAddmissionFinanceDashboard(List<DashboardElement> elements) {
		//petition
				//List<CoursePetition> courseForms = dashboardAppServ.getAdmissionHeadAcademicPetitionsPending();
				//Integer count = 0;
				//if(courseForms != null)
				//	count = courseForms.size();
				
				//elements.add(new AcademicPetitionDashboardElement(count.toString()));
				
				//addDrop
				/*List<DropAddForm> dropAddForms = dashboardAppServ.getAdmissionHeadAddDropPending();
				Integer count = 0;
				if(dropAddForms != null)
					count = dropAddForms.size();
				
				elements.add(new AddDropDashboardElement(count.toString()));
				*/
				//changeMajor
						List<ChangeMajorForm> changMajorForms = dashboardAppServ.getAdmissionHeadChangeMajorPending();
						Integer count = 0;
						if(changMajorForms != null)
							count = changMajorForms.size();
						
						elements.add(new ChangeMajorDashboardElement(count.toString()));
						
						//readmission
						/*List<ReadmissionForm> readmissionForms = dashboardAppServ.getAdmissionHeadReadmissionPending();
						count = 0;
						if(readmissionForms != null)
							count = readmissionForms.size();
						
						elements.add(new ReadmissionDashboardElement(count.toString()));*/
				
				//overload
				/*List<OverloadRequest> overloadRequestForms = dashboardAppServ.getAdmissionHeadOverloadRequestPending();
				count = 0;
				if(overloadRequestForms != null)
					count = overloadRequestForms.size();
				
				elements.add(new OverloadRequestDashboardElement(count.toString()));
				
				//course repeat
				count = dashboardAppServ.getAdmissionHeadCourseRepeatPending();
				elements.add(new CourseRepeatDashboardElement(count.toString()));
				*/
				
				/**
				 * @author Omnya
				 *
				 *Incomplete grade dashboard 
				 */
				/*count = dashboardAppServ.getAdmissionHeadIncompleteGradePending();
				elements.add(new IncompleteGradeDashboardElement(count.toString()));*/
	}
	
	private void fillAddmissionHeadDashboard(List<DashboardElement> elements)
	{
		//petition
		//List<CoursePetition> courseForms = dashboardAppServ.getAdmissionHeadAcademicPetitionsPending();
		//Integer count = 0;
		//if(courseForms != null)
		//	count = courseForms.size();
		
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		/*List<DropAddForm> dropAddForms = dashboardAppServ.getAdmissionHeadAddDropPending();
		Integer count = 0;
		if(dropAddForms != null)
			count = dropAddForms.size();
		
		elements.add(new AddDropDashboardElement(count.toString()));*/
		
		//changeMajor
			/*	List<ChangeMajorForm> changMajorForms = dashboardAppServ.getAdmissionHeadChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				
				elements.add(new ChangeMajorDashboardElement(count.toString()));*/
				
				//readmission
				/*List<ReadmissionForm> readmissionForms = dashboardAppServ.getAdmissionHeadReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				
				elements.add(new ReadmissionDashboardElement(count.toString()));*/
		
		//overload
		/*List<OverloadRequest> overloadRequestForms = dashboardAppServ.getAdmissionHeadOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getAdmissionHeadCourseRepeatPending();
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		*/
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		/*count = dashboardAppServ.getAdmissionHeadIncompleteGradePending();
		elements.add(new IncompleteGradeDashboardElement(count.toString()));*/
	}
	
	private void fillAdmissionDepartmentDashboard(List<DashboardElement> elements)
	{
		//petition
		//List<CoursePetition> courseForms = dashboardAppServ.getAdmissionDepartmentAcademicPetitionsPending();
		//Integer count = 0;
		//if(courseForms != null)
		//	count = courseForms.size();
		
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		List<DropAddForm> dropAddForms = dashboardAppServ.getAdmissionDepartmentAddDropPending();
		Integer count = 0;
		if(dropAddForms != null)
			count = dropAddForms.size();
		
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getAdmissionDepartmentChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				
				
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getAdmissionDepartmentReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				
				elements.add(new ReadmissionDashboardElement(count.toString()));
				
				
				//course_replacement_form
				Integer course_replacement_formForms = dashboardAppServ.getAdmissionDepartmentCourseReplacementFormsAuditing();
				Integer course_replacement_formForms2 = dashboardAppServ.getAdmissionDepartmentCourseReplacementFormsPending();
				count = 0;
				if(course_replacement_formForms != null)
					count = course_replacement_formForms+course_replacement_formForms2;
				
				elements.add(new course_replacement_formDashboardElement(count.toString()));
				
				
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getAdmissionDepartmentOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getAdmissionDepartmentCourseRepeatPending();
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getAdmissionDepartmentIncompleteGradePending();
		elements.add(new IncompleteGradeDashboardElement(count.toString()));
	}
	
	private void fillProvostDashboard(List<DashboardElement> elements,String mail)
	{
		//petition
		//Integer count =  dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				count = dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				
				//readmission
				count = dashboardAppServ.getInstructorReadmissionPending(Constants.PROVOST_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString()));
				
				
				//course_replacement_form
				count = dashboardAppServ.getInstructorcourse_replacement_formPending(Constants.PROVOST_ID,mail);
				elements.add(new course_replacement_formDashboardElement(count.toString()));
				
				
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getProvostOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString()));
	}
	
	private void fillInstructorDashboard(List<DashboardElement> elements,String mail)
	{
		//petition
		//Integer count =  dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				count = dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				//courseReplacement
				count = dashboardAppServ.getInstructorCourseReplacementForms( mail);
				elements.add(new course_replacement_formDashboardElement(count.toString()));
		
		
		//overload
		count = dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString()));
		
		
		/**
		 * @author Omnya
		 *
		 *Junior TA Dashboard
		 */
		count = dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString()));
	
	}

	private void fillAdminDashboard(List<DashboardElement> elements)
	{
		//petition
		Integer count =  dashboardAppServ.getAdminAcademicPetitionsPending();
		Integer all = count + dashboardAppServ.getAdminAcademicPetitionsOld();
		elements.add(new AcademicPetitionDashboardElement(count.toString() + "/" +all.toString()));
		
		//addDrop
		count = dashboardAppServ.getAdminAddDropPending();
		all = count + dashboardAppServ.getAdminAddDropOld();
		elements.add(new AddDropDashboardElement(count.toString() + "/" +all.toString()));
		
		//changeMajor
				count = dashboardAppServ.getAdminChangeMajorPending();
				all = count + dashboardAppServ.getAdminChangeMajorOld();
				elements.add(new ChangeMajorDashboardElement(count.toString() + "/" +all.toString()));
				
				//readmission
				count = dashboardAppServ.getAdminReadmissionPending();
				all = count + dashboardAppServ.getAdminReadmissionOld();
				elements.add(new ReadmissionDashboardElement(count.toString() + "/" +all.toString()));
		
				
				//course_replacement_form
				count = dashboardAppServ.getAdmincourse_replacement_formPending();
				all = count + dashboardAppServ.getAdmincourse_replacement_formOld();
				elements.add(new course_replacement_formDashboardElement(count.toString() + "/" +all.toString()));
		
		//overload
		count = dashboardAppServ.getAdminOverloadRequestPending();
		all = count + dashboardAppServ.getAdminOverloadRequestOld();
		elements.add(new OverloadRequestDashboardElement(count.toString() + "/" +all.toString()));
		
		//course repeat
		count = dashboardAppServ.getAdminCourseRepeatPending();
		all = count + dashboardAppServ.getAdminCourseRepeatOld();
		elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString()));
		
		//Incomplete grade
				count = dashboardAppServ.getAdminIncompleteGradePending();
				all = count + dashboardAppServ.getAdminIncompleteGradeOld();
				elements.add(new IncompleteGradeDashboardElement(count.toString() + "/" +all.toString()));
				
	}
	private void fillAdminDashboardStratgic(List<DashboardElement> elements)
	{
		//petition
				//Integer count =  dashboardAppServ.getAdminAcademicPetitionsPending();
				//Integer all = count + dashboardAppServ.getAdminAcademicPetitionsOld();
				//elements.add(new AcademicPetitionDashboardElement(count.toString() + "/" +all.toString()));
				
				//addDrop
				Integer count = dashboardAppServ.getAdminAddDropPending();
				Integer all = count + dashboardAppServ.getAdminAddDropOld();
				elements.add(new AddDropDashboardElement(count.toString() + "/" +all.toString()));
				
				//changeMajor
				count = dashboardAppServ.getAdminChangeMajorPending();
				all = count + dashboardAppServ.getAdminChangeMajorOld();
				elements.add(new ChangeMajorDashboardElement(count.toString() + "/" +all.toString()));
				
				//readmission
				count = dashboardAppServ.getAdminReadmissionPending();
				all = count + dashboardAppServ.getAdminReadmissionOld();
				elements.add(new ReadmissionDashboardElement(count.toString() + "/" +all.toString()));
				
				
				//course_replacement_form
				count = dashboardAppServ.getAdmincourse_replacement_formPending();
				all = count + dashboardAppServ.getAdmincourse_replacement_formOld();
				elements.add(new course_replacement_formDashboardElement(count.toString() + "/" +all.toString()));
				
				
				//overload
				count = dashboardAppServ.getAdminOverloadRequestPending();
				all = count + dashboardAppServ.getAdminOverloadRequestOld();
				elements.add(new OverloadRequestDashboardElement(count.toString() + "/" +all.toString()));
				
//				//course repeat
//				count = dashboardAppServ.getAdminCourseRepeatPending();
//				all = count + dashboardAppServ.getAdminCourseRepeatOld();
//				elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString()));
//				
				//Incomplete grade
						count = dashboardAppServ.getAdminIncompleteGradePending();
						all = count + dashboardAppServ.getAdminIncompleteGradeOld();
						elements.add(new IncompleteGradeDashboardElement(count.toString() + "/" +all.toString()));
				
	}
	private void fillAdminDashboardAcademic(List<DashboardElement> elements)
	{
			
		
				//course repeat
			Integer	count = dashboardAppServ.getAdminCourseRepeatPending();
			Integer	all = count + dashboardAppServ.getAdminCourseRepeatOld();
				elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString()));
				
			
	}
	
	private void fillStudentDashboard(List<DashboardElement> elements,Integer studentId)
	{
		//petition
		//Integer count =  dashboardAppServ.getStudentAcademicPetitionsPending(studentId);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getStudentAddDropPending(studentId);
		elements.add(new AddDropDashboardElement(count.toString()));
		
		//changeMajor
				count = dashboardAppServ.getStudentChangeMajorPending(studentId);
				elements.add(new ChangeMajorDashboardElement(count.toString()));
				
				//readmission
				count = dashboardAppServ.getStudentReadmissionPending(studentId);
				elements.add(new ReadmissionDashboardElement(count.toString()));
		
				
				//course_replacement_form
				count = dashboardAppServ.getStudentCourseReplacementForms(studentId);
				elements.add(new course_replacement_formDashboardElement(count.toString()));
		
				
				
		//overload
		count = dashboardAppServ.getStudentOverloadRequestPending(studentId);
		elements.add(new OverloadRequestDashboardElement(count.toString()));
		
		//course repeat
		count = dashboardAppServ.getStudentCourseRepeatPending(studentId);
		elements.add(new CourseRepeatDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getStudentIncompleteGradePending(studentId);
		elements.add(new IncompleteGradeDashboardElement(count.toString()));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getStudentJuniorTAPending(studentId);
		elements.add(new JuniorTADashboardElement(count.toString()));
		
		
		
	}

}
