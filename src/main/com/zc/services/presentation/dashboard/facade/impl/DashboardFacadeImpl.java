/**
 * 
 */
package main.com.zc.services.presentation.dashboard.facade.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.dashboard.IDashboardAppService;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.dashboard.dto.AcademicPetitionDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.AddDropDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.ChangeConcentrationDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.ChangeMajorDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.CourseRepeatDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.DashboardElement;
import main.com.zc.services.presentation.dashboard.dto.IncompleteGradeDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.JuniorTADashboardElement;
import main.com.zc.services.presentation.dashboard.dto.OverloadRequestDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.ReadmissionDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.change_grade_petitionDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.course_replacement_formDashboardElement;
import main.com.zc.services.presentation.dashboard.dto.gap_formDashboardElement;
import main.com.zc.services.presentation.dashboard.facade.IDashboardFacade;
import main.com.zc.services.presentation.forms.change_grade_petition.change_grade_petition;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacement;
import main.com.zc.services.presentation.forms.gap_form.gap_form;
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
				 System.out.println("Stratigic");
				 fillDeanDashboardStrategic(elements,userMail);
				 fillInstructorDashboard(elements, userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.REGISTRAR_HEAD_EMAIL)) {
				 System.out.println("Addmission Head");
				 fillAddmissionHeadDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.Financial_DEP)) {
				 System.out.println("Finance");
				 fillAddmissionFinanceDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.ADMISSION_DEPT)) {
				 System.out.println("Admission Dep");
				 fillAdmissionDepartmentDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.DEAN_OF_ACADEMIC)) {
				 System.out.println("Dean of Academic");
				 fillDeanDashboardAcademic(elements,userMail);
				 fillInstructorDashboard(elements, userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.ADMISSION_HEAD_EMAIL)) {
				 System.out.println("Admission Head");
				 fillAdmissionHeadDashboardAcademic(elements,userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.PROVOST)) {
				 System.out.println("PRovost");
				 fillProvostDashboard(elements, userMail);
				 fillInstructorDashboard(elements, userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.LTS_SYSTEM_ADMIN)) {
				 System.out.println("LT adim");
				 fillAdminDashboard(elements);
			 }else if(userMail.equalsIgnoreCase(Constants.ACCREDITION_ENG_DEP)) {
				 System.out.println("Accred Eng");
				 fillAccredEngDashboard(elements);
				 fillInstructorDashboard(elements, userMail);
			 }else if(userMail.equalsIgnoreCase(Constants.ACCREDITION_SCI_DEP)) {
				 System.out.println("Accred sci");
				 fillAccredSciDashboard(elements);
				 fillInstructorDashboard(elements, userMail);
			 }else {
				 System.out.println("Instructor");
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
		elements.add(new AddDropDashboardElement(count.toString(),"Accredition Of Science"));
		
		//changeMajor
				Integer changMajorForms =  dashboardAppServ.getInstructorChangeMajorPending(mail);
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms;
				
				
				elements.add(new ChangeMajorDashboardElement(count.toString(),"Dean Of Strategics"));
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getDeanReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				count += dashboardAppServ.getInstructorReadmissionPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString(),"Dean Of Strategics"));
		
				//course_replacement_form
				Integer course_replacement_formForms = dashboardAppServ.getDeanCourseReplacementFormsPending();
				count = 0;
				if(course_replacement_formForms != null)
					count = course_replacement_formForms;
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Dean Of Strategics"));
				
				
				
				//gap_form
				Integer gapForms = dashboardAppServ.getDeanGapFormsPending();
				count = 0;
				if(gapForms != null)
					count = gapForms;
				elements.add(new gap_formDashboardElement(count.toString(),"Dean Of Strategics"));
				
				
				//change_grade_petition
				Integer change_grade_petition = dashboardAppServ.getDeanchange_grade_petitionFormsPending();
				count = 0;
				if(change_grade_petition != null)
					count = change_grade_petition;
				elements.add(new change_grade_petitionDashboardElement(count.toString(),"Dean Of Strategics"));
		
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getDeanOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Dean Of Strategics"));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Dean Of Strategics"));
		
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getDeanIncompleteGradePending();
		count += dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString(),"Dean Of Strategics"));
		
		/**
		 * @author Omnya
		 *
		 *Junior TA dashboard 
		 */
		count = dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString(),"Dean Of Strategics"));
		
	}
	
	
	private void fillAccredEngDashboard(List<DashboardElement> elements)
	{
		
		Integer count=0;
		//course replacement
				Integer formsCR =  dashboardAppServ.getAccreditionEngHeadCourseReplacementPending();
				count = 0;
				if(formsCR != null)
					count = formsCR;
				
				
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Accredition Of Engineering"));
				
	}
	
	
	private void fillAccredSciDashboard(List<DashboardElement> elements)
	{
		
		Integer count=0;
		//course replacement
				Integer formsCR =  dashboardAppServ.getAccreditionSciHeadCourseReplacementPending();
				count = 0;
				if(formsCR != null)
					count = formsCR;
				
				
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Accredition Of Science"));
				
		
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
		elements.add(new AddDropDashboardElement(count.toString(),"Dean Of Strategics"));
		
		//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getDeanChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				count += dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString(),"Dean Of Strategics"));
				
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getDeanReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				count += dashboardAppServ.getInstructorReadmissionPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString(),"Dean Of Strategics"));
		
				
				//course_replacement_form
				List<course_replacement_formForm> course_replacement_formForms = dashboardAppServ.getDeancourse_replacement_formPending();
				count = 0;
				if(course_replacement_formForms != null)
					count = course_replacement_formForms.size();
				count += dashboardAppServ.getInstructorcourse_replacement_formPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Dean Of Strategics"));
				
				
				//gap_form
				List<gap_form> gap_forms = dashboardAppServ.getDeangap_FromPending();
				count = 0;
				if(gap_forms != null)
					count = gap_forms.size();
				count += dashboardAppServ.getInstructorGap_formPending(Constants.DEAN_OF_STRATEGIC_ID,mail);
				elements.add(new gap_formDashboardElement(count.toString(),"Dean Of Strategics"));
				
				//gap_form
				List<change_grade_petition> change_grade_petition = dashboardAppServ.getDeanchange_grade_petitionPending();
				count = 0;
				if(change_grade_petition != null)
					count = change_grade_petition.size();
				count += dashboardAppServ.getInstructorchange_grade_petition(mail);
				elements.add(new change_grade_petitionDashboardElement(count.toString(),"Dean Of Strategics"));
		
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getDeanOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Dean Of Strategics"));
		
		//course repeat
		count = dashboardAppServ.getDeanCourseRepeatPending();
		count += dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Dean Of Strategics"));
		
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getDeanIncompleteGradePending();
		count += dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString(),"Dean Of Strategics"));
		
		/**
		 * @author Omnya
		 *
		 *Junior TA dashboard 
		 */
		count = dashboardAppServ.getDeanJuniorTAPending();
		count += dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString(),"Dean Of Strategics"));
		
	}
	private void fillDeanDashboardAcademic(List<DashboardElement> elements,String mail)
	{
	
		//petition
			//Integer	count = dashboardAppServ.getInstructorAcademicPetitionsPending(mail);	
			//elements.add(new AcademicPetitionDashboardElement(count.toString()));
				
				//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
				elements.add(new AddDropDashboardElement(count.toString(),"Dean Of Academic"));
				
				//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getDeanChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				count += dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString(),"Dean Of Academic"));
				
				
				
				
				//overload
				count = dashboardAppServ.getInstructorOverloadRequestPending(mail);
				elements.add(new OverloadRequestDashboardElement(count.toString(),"Dean Of Academic"));
				
				//course repeat
				count = dashboardAppServ.getDeanCourseRepeatPending();
				count += dashboardAppServ.getInstructorCourseRepeatPending(mail);
				elements.add(new CourseRepeatDashboardElement(count.toString(),"Dean Of Academic"));
				
				
				/**
				 * @author Omnya
				 *
				 *Incomplete grade dashboard 
				 */
				count = dashboardAppServ.getInstructorIncompleteGradePending(mail);
				elements.add(new IncompleteGradeDashboardElement(count.toString(),"Dean Of Academic"));
				
				/**
				 * @author Omnya
				 *
				 *Junior TA dashboard 
				 */
				count = dashboardAppServ.getDeanJuniorTAPending();
				count += dashboardAppServ.getInstructorJuniorTAPending(mail);
				elements.add(new JuniorTADashboardElement(count.toString(),"Dean Of Academic"));
			
	}
	
	
	private void fillAdmissionHeadDashboardAcademic(List<DashboardElement> elements,String mail)
	{
	
		//petition
			//Integer	count = dashboardAppServ.getInstructorAcademicPetitionsPending(mail);	
			//elements.add(new AcademicPetitionDashboardElement(count.toString()));
				
		
				//readmission
				Integer count = dashboardAppServ.getInstructorReadmissionPending(Constants.ADMISSION_HEAD_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString(),"Admission Head"));
				
				
				
			
				
			
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
		
		elements.add(new ChangeMajorDashboardElement(count.toString(),"Finance"));
		
		//gap_form
		List<gap_form> gapForms = dashboardAppServ.getFinanceDepartmentgap_formPending();
		count = 0;
		if(changMajorForms != null)
			count = changMajorForms.size();
		
		elements.add(new gap_formDashboardElement(count.toString(),"Finance"));
						
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
		
		Integer count = 0;
		//changeMajor
			List<ChangeConcentration> ChangeConcentrationForms = dashboardAppServ.getAdmissionHeadChangeConcentrationPending();
			count = 0;
			if(ChangeConcentrationForms != null)
				count = ChangeConcentrationForms.size();
			
			elements.add(new ChangeConcentrationDashboardElement(count.toString(),"Head of Addmission"));
		
		
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
		
		elements.add(new AddDropDashboardElement(count.toString(),"Registrar"));
		
		//changeMajor
				List<ChangeMajorForm> changMajorForms = dashboardAppServ.getAdmissionDepartmentChangeMajorPending();
				count = 0;
				if(changMajorForms != null)
					count = changMajorForms.size();
				
				elements.add(new ChangeMajorDashboardElement(count.toString(),"Registrar"));
				
				//ChangeConcentration
				List<ChangeConcentration> changConcentrationForms = dashboardAppServ.getAdmissionDepartmentChangeConcentrationPending();
				count = 0;
				if(changConcentrationForms != null)
					count = changConcentrationForms.size();
				
				elements.add(new ChangeConcentrationDashboardElement(count.toString(),"Registrar"));
				
				//readmission
				List<ReadmissionForm> readmissionForms = dashboardAppServ.getAdmissionDepartmentReadmissionPending();
				count = 0;
				if(readmissionForms != null)
					count = readmissionForms.size();
				
				elements.add(new ReadmissionDashboardElement(count.toString(),"Registrar"));
				
				
				//course_replacement_form
				Integer course_replacement_formForms = dashboardAppServ.getAdmissionDepartmentCourseReplacementFormsAuditing();
				Integer course_replacement_formForms2 = dashboardAppServ.getAdmissionDepartmentCourseReplacementFormsPending();
				count = 0;
				if(course_replacement_formForms != null)
					count = course_replacement_formForms+course_replacement_formForms2;
				
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Registrar"));
				
				
				//course_replacement_form
				Integer gapform = dashboardAppServ.getAdmissionDepartmentGapFormsAuditing();
				Integer gapform2 = dashboardAppServ.getAdmissionDepartmentGapFormsPending();
				count = 0;
				if(gapform != null)
					count = gapform+gapform2;
				
				elements.add(new gap_formDashboardElement(count.toString(),"Registrar"));
				
				
				Integer change_grade_petition = dashboardAppServ.getAdmissionDepartmentchange_grade_petitionPending();
				count = 0;
				if(gapform != null)
					count = change_grade_petition;
				
				elements.add(new change_grade_petitionDashboardElement(count.toString(),"Registrar"));
				
				
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getAdmissionDepartmentOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Registrar"));
		
		//course repeat
		count = dashboardAppServ.getAdmissionDepartmentCourseRepeatPending();
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Registrar"));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getAdmissionDepartmentIncompleteGradePending();
		elements.add(new IncompleteGradeDashboardElement(count.toString(),"Registrar"));
	}
	
	private void fillProvostDashboard(List<DashboardElement> elements,String mail)
	{
		//petition
		//Integer count =  dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString(),"Provost"));
		
		//changeMajor
				count = dashboardAppServ.getInstructorChangeMajorPending(mail);
				elements.add(new ChangeMajorDashboardElement(count.toString(),"Provost"));
				
				
				//readmission
				count = dashboardAppServ.getInstructorReadmissionPending(Constants.PROVOST_ID,mail);
				elements.add(new ReadmissionDashboardElement(count.toString(),"Provost"));
				
				
				//course_replacement_form
				count = dashboardAppServ.getInstructorcourse_replacement_formPending(Constants.PROVOST_ID,mail);
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Provost"));
				
				
		//overload
		List<OverloadRequest> overloadRequestForms = dashboardAppServ.getProvostOverloadRequestPending();
		count = 0;
		if(overloadRequestForms != null)
			count = overloadRequestForms.size();
		count += dashboardAppServ.getInstructorOverloadRequestPending(mail);
		
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Provost"));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Provost"));
	}
	
	private void fillInstructorDashboard(List<DashboardElement> elements,String mail)
	{
		//petition
		//Integer count =  dashboardAppServ.getInstructorAcademicPetitionsPending(mail);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getInstructorAddDropPending(mail);
		elements.add(new AddDropDashboardElement(count.toString(),"Instructor"));
		
		//changeMajor
		count = dashboardAppServ.getInstructorChangeMajorPending(mail);
		elements.add(new ChangeMajorDashboardElement(count.toString(),"Instructor"));
		
		
		//ChangeConcentration
		count = dashboardAppServ.getInstructorChangeConcentrationPending(mail);
		elements.add(new ChangeConcentrationDashboardElement(count.toString(),"Instructor"));
				
		//courseReplacement
		count = dashboardAppServ.getInstructorCourseReplacementForms( mail);
		elements.add(new course_replacement_formDashboardElement(count.toString(),"Instructor"));
		
		//gap form
		count = dashboardAppServ.getInstructorGapForms( mail);
		elements.add(new gap_formDashboardElement(count.toString(),"Instructor"));
		
		
		//change_grade_petition
		count = dashboardAppServ.getInstructorchange_grade_petition( mail);
		elements.add(new change_grade_petitionDashboardElement(count.toString(),"Instructor"));
				
		
		//overload
		count = dashboardAppServ.getInstructorOverloadRequestPending(mail);
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Instructor"));
		
		//course repeat
		count = dashboardAppServ.getInstructorCourseRepeatPending(mail);
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Instructor"));
		
		/**
		 * @author Omnya
		 *
		 *Incomplete grade dashboard 
		 */
		count = dashboardAppServ.getInstructorIncompleteGradePending(mail);
		elements.add(new IncompleteGradeDashboardElement(count.toString(),"Instructor"));
		
		
		/**
		 * @author Omnya
		 *
		 *Junior TA Dashboard
		 */
		count = dashboardAppServ.getInstructorJuniorTAPending(mail);
		elements.add(new JuniorTADashboardElement(count.toString(),"Instructor"));
	
	}

	private void fillAdminDashboard(List<DashboardElement> elements)
	{
		//petition
		Integer count =  dashboardAppServ.getAdminAcademicPetitionsPending();
		Integer all = count + dashboardAppServ.getAdminAcademicPetitionsOld();
		elements.add(new AcademicPetitionDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
		//addDrop
		count = dashboardAppServ.getAdminAddDropPending();
		all = count + dashboardAppServ.getAdminAddDropOld();
		elements.add(new AddDropDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
		//changeMajor
				count = dashboardAppServ.getAdminChangeMajorPending();
				all = count + dashboardAppServ.getAdminChangeMajorOld();
				elements.add(new ChangeMajorDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
				
				//readmission
				count = dashboardAppServ.getAdminReadmissionPending();
				all = count + dashboardAppServ.getAdminReadmissionOld();
				elements.add(new ReadmissionDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
				
				//course_replacement_form
				count = dashboardAppServ.getAdmincourse_replacement_formPending();
				all = count + dashboardAppServ.getAdmincourse_replacement_formOld();
				elements.add(new course_replacement_formDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
				
				
				//gap_form
				count = dashboardAppServ.getAdminGap_formPending();
				all = count + dashboardAppServ.getAdminGap_formOld();
				elements.add(new gap_formDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
				
				//change_grade_petition
				count = dashboardAppServ.getAdminchange_grade_petitionPending();
				all = count + dashboardAppServ.getAdminchange_grade_petitionOld();
				elements.add(new gap_formDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
		//overload
		count = dashboardAppServ.getAdminOverloadRequestPending();
		all = count + dashboardAppServ.getAdminOverloadRequestOld();
		elements.add(new OverloadRequestDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
		//course repeat
		count = dashboardAppServ.getAdminCourseRepeatPending();
		all = count + dashboardAppServ.getAdminCourseRepeatOld();
		elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
		
		//Incomplete grade
				count = dashboardAppServ.getAdminIncompleteGradePending();
				all = count + dashboardAppServ.getAdminIncompleteGradeOld();
				elements.add(new IncompleteGradeDashboardElement(count.toString() + "/" +all.toString(),"Adminstrator Account"));
				
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
				elements.add(new AddDropDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
				//changeMajor
				count = dashboardAppServ.getAdminChangeMajorPending();
				all = count + dashboardAppServ.getAdminChangeMajorOld();
				elements.add(new ChangeMajorDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
				//readmission
				count = dashboardAppServ.getAdminReadmissionPending();
				all = count + dashboardAppServ.getAdminReadmissionOld();
				elements.add(new ReadmissionDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
				
				//course_replacement_form
				count = dashboardAppServ.getAdmincourse_replacement_formPending();
				all = count + dashboardAppServ.getAdmincourse_replacement_formOld();
				elements.add(new course_replacement_formDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
				
				//course_replacement_form
				count = dashboardAppServ.getAdminGap_formPending();
				all = count + dashboardAppServ.getAdminGap_formOld();
				elements.add(new gap_formDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
				
				//overload
				count = dashboardAppServ.getAdminOverloadRequestPending();
				all = count + dashboardAppServ.getAdminOverloadRequestOld();
				elements.add(new OverloadRequestDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
//				//course repeat
//				count = dashboardAppServ.getAdminCourseRepeatPending();
//				all = count + dashboardAppServ.getAdminCourseRepeatOld();
//				elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString()));
//				
				//Incomplete grade
						count = dashboardAppServ.getAdminIncompleteGradePending();
						all = count + dashboardAppServ.getAdminIncompleteGradeOld();
						elements.add(new IncompleteGradeDashboardElement(count.toString() + "/" +all.toString(),"Admin Strategic"));
				
	}
	private void fillAdminDashboardAcademic(List<DashboardElement> elements)
	{
			
		
				//course repeat
			Integer	count = dashboardAppServ.getAdminCourseRepeatPending();
			Integer	all = count + dashboardAppServ.getAdminCourseRepeatOld();
				elements.add(new CourseRepeatDashboardElement(count.toString() + "/" +all.toString(),"Admin Academic"));
				
			
	}
	
	private void fillStudentDashboard(List<DashboardElement> elements,Integer studentId)
	{
		//petition
		//Integer count =  dashboardAppServ.getStudentAcademicPetitionsPending(studentId);
		//elements.add(new AcademicPetitionDashboardElement(count.toString()));
		
		//addDrop
		Integer count = dashboardAppServ.getStudentAddDropPending(studentId);
		elements.add(new AddDropDashboardElement(count.toString(),"Student"));
		
		//changeMajor
		count = dashboardAppServ.getStudentChangeMajorPending(studentId);
		elements.add(new ChangeMajorDashboardElement(count.toString(),"Student"));
		
		
		//changeMajor
		count = dashboardAppServ.getStudentChangeConcentrationPending(studentId);
		elements.add(new ChangeConcentrationDashboardElement(count.toString(),"Student"));
				
				//readmission
				count = dashboardAppServ.getStudentReadmissionPending(studentId);
				elements.add(new ReadmissionDashboardElement(count.toString(),"Student"));
		
				
				//course_replacement_form
				count = dashboardAppServ.getStudentCourseReplacementForms(studentId);
				elements.add(new course_replacement_formDashboardElement(count.toString(),"Student"));
				
				//gap_form
				count = dashboardAppServ.getStudentGapForms(studentId);
				elements.add(new gap_formDashboardElement(count.toString(),"Student"));
				
				
				//change_grade_petition
				count = dashboardAppServ.getStudentchange_grade_petition(studentId);
				elements.add(new change_grade_petitionDashboardElement(count.toString(),"Student"));
		
				
				
		//overload
		count = dashboardAppServ.getStudentOverloadRequestPending(studentId);
		elements.add(new OverloadRequestDashboardElement(count.toString(),"Student"));
		
		//course repeat
		count = dashboardAppServ.getStudentCourseRepeatPending(studentId);
		elements.add(new CourseRepeatDashboardElement(count.toString(),"Student"));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getStudentIncompleteGradePending(studentId);
		elements.add(new IncompleteGradeDashboardElement(count.toString(),"Student"));
		
		/**
		 * @author Omnya
		 *
		 */
		count = dashboardAppServ.getStudentJuniorTAPending(studentId);
		elements.add(new JuniorTADashboardElement(count.toString(),"Student"));
		
		
		
	}

}
