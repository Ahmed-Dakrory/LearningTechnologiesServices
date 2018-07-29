/**
 * 
 */
package main.com.zc.services.applicationService.dashboard.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.dashboard.IDashboardAppService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdminService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionDeptService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionHeadService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatInsService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatStudentService;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionAdminAcademicPetService;
import main.com.zc.services.applicationService.forms.academicPetition.services.IStudentAcademicPetitionService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdminAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IStudentAddDropFormServices;
import main.com.zc.services.applicationService.forms.changeMajor.services.IAdminChangeMajorService;
import main.com.zc.services.applicationService.forms.changeMajor.services.IStudentChangeMajorService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdminService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionDeptService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionHeadService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeInsService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeStudentService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdminOverloadRequestService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IStudentOverloadRequestService;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.IJuniorTAServiceInstructor;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ITAJuniorProgramServiceStudent;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Momen
 * 
 */
@Service("IDashboardAppService")
public class DashboardAppServiceImpl implements IDashboardAppService 
{

	@Autowired
	ICoursePetitionRep coursePetitionRep;
	
	@Autowired
	IAddDropFormRepository addDropRep;
	
	@Autowired
	IChangeMajorFormRep changeMajorRep;
	
	@Autowired
	IOverloadRequestRep overloadRequestRep;
	
	@Autowired
	IAdmissionAdminAcademicPetService adminAcademicPetAppServ;
	@Autowired
	IAdminAddDropFormService adminAddDropAppServ;
	@Autowired
	IAdminChangeMajorService adminChangeMajorAppServ;
	@Autowired
	IAdminOverloadRequestService adminOverloadRequestAppServ;
	
	@Autowired
	IStudentAcademicPetitionService studentAcademicPetAppServ;
	@Autowired
	IStudentAddDropFormServices studentAddDropAppServ;
	@Autowired
	IStudentChangeMajorService studentChangeMajorAppServ;
	@Autowired
	IStudentOverloadRequestService studentOverloadRequestAppServ;
	
	@Autowired
	IEmployeeRepository instructorRep;
	
	@Autowired
	ICourseRepeatInsService instructorCourseRepeatServ;
	@Autowired
	ICourseRepeatStudentService strudentCourseRepeatServ;
	@Autowired
	ICourseRepeatAdmissionDeptService admissionDeptCourseRepeatServ;
	@Autowired
	ICourseRepeatAdmissionHeadService admissionHeadCourseRepeatServ;
	@Autowired
	ICourseRepeatAdminService adminCourseRepeatServ;
	
	
	/**
	 * @author Omnya
	 * 
	 */
	
	@Autowired
	IIncompleteGradeStudentService studentIncompleteGradeService;
	@Autowired
	IIncompleteGradeAdmissionDeptService registrarIncompleteGradeService;
	@Autowired
	IIncompleteGradeAdmissionHeadService admissionHIncompleteGradeService;
	@Autowired
	IIncompleteGradeInsService instructorIncompleteGradeService;
	@Autowired
	IIncompleteGradeAdminService adminIncompleteGradeService;
	@Autowired
	IJuniorTAServiceInstructor instructorJuniorTAService;
	@Autowired
	ITAJuniorProgramServiceStudent studentJuniorTAService;
	@Override
	public List<CoursePetition> getDeanAcademicPetitionsPending() 
	{
		List<CoursePetition> courses = coursePetitionRep.getDeanPendingCoursePetition(true);
		return courses;
	}
	
	@Override
	public List<DropAddForm> getDeanAddDropPending() 
	{
		List<DropAddForm> forms = addDropRep.getDeanPendingDropAddForm(true);
		return forms;
	}
	
	@Override
	public List<ChangeMajorForm> getDeanChangeMajorPending() 
	{
		List<ChangeMajorForm> forms = changeMajorRep.getDeanPendingChangeMajorForm(true);
		return forms;
	}
	
	@Override
	public List<OverloadRequest> getDeanOverloadRequestPending() 
	{
		List<OverloadRequest> overloads = overloadRequestRep.getDeanPendingOverloadRequest(true);
		return overloads;
	}
	
	@Override
	public Integer getDeanCourseRepeatPending()
	{
		List<CourseRepeatDTO> forms = instructorCourseRepeatServ.getPendingFormsOfDean();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public List<CoursePetition> getAdmissionHeadAcademicPetitionsPending() 
	{
		List<CoursePetition> courses = coursePetitionRep.getAdmissionHeadPendingCoursePetition(true);
		return courses;
	}
	
	@Override
	public List<DropAddForm> getAdmissionHeadAddDropPending() 
	{
		List<DropAddForm> forms = addDropRep.getAdmissionHeadPendingDropAddForm(true);
		return forms;
	}
	
	@Override
	public List<ChangeMajorForm> getAdmissionHeadChangeMajorPending() 
	{
		List<ChangeMajorForm> forms = changeMajorRep.getAdmissionHeadPendingChangeMajorForm(true);
		return forms;
	}
	
	@Override
	public List<OverloadRequest> getAdmissionHeadOverloadRequestPending() 
	{
		List<OverloadRequest> overloads = overloadRequestRep.getAdmissionHeadPendingOverloadRequest(true);
		return overloads;
	}
	
	@Override
	public Integer getAdmissionHeadCourseRepeatPending()
	{
		List<CourseRepeatDTO> forms = admissionHeadCourseRepeatServ.getPendingFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public List<CoursePetition> getAdmissionDepartmentAcademicPetitionsPending() 
	{
		List<CoursePetition> courses = coursePetitionRep.getPendingAndAuditingAcademicPetitionsAdmissionDept();
		return courses;
	}
	
	@Override
	public List<DropAddForm> getAdmissionDepartmentAddDropPending() 
	{
		List<DropAddForm> forms = addDropRep.getAdmissionDeptPendingDropAddForm(true);
		return forms;
	}
	
	@Override
	public List<ChangeMajorForm> getAdmissionDepartmentChangeMajorPending() 
	{
		List<ChangeMajorForm> forms = changeMajorRep.getAdmissionDeptPendingChangeMajorForm(true);
		return forms;
	}
	
	@Override
	public List<OverloadRequest> getAdmissionDepartmentOverloadRequestPending() 
	{
		List<OverloadRequest> overloads = overloadRequestRep.getPendingAndAuditingOverloadRequestAdmissionDept();
		return overloads;
	}
	
	@Override
	public Integer getAdmissionDepartmentCourseRepeatPending()
	{
		return admissionDeptCourseRepeatServ.getPendingAndAuditingCourseRepeateAdmissionDept();
		
	}
	
	@Override
	public List<OverloadRequest> getProvostOverloadRequestPending() 
	{
		List<OverloadRequest> overloads = overloadRequestRep.getProvostPendingOverloadRequest(true);
		return overloads;
	}
	
	@Override
	public Integer getInstructorAcademicPetitionsPending(String mail) 
	{
		List<PendingPetitionCountObject> data = coursePetitionRep.getInstructorPendingCoursePetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		//if not found
		return 0;
		
	}
	
	@Override
	public Integer getInstructorAddDropPending(String mail) 
	{	
	/*	List<PendingPetitionCountObject> data = addDropRep.getInstructorPendingDropAddPetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		
		return 0;*/
		try
		{
			List<DropAddForm> forms=new ArrayList<DropAddForm>();
			Employee ins=instructorRep.getByMail(mail);
			forms=addDropRep.getInstructorCountPending(ins.getId());
			return forms.size();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Integer getInstructorChangeMajorPending(String mail) 
	{
		List<PendingPetitionCountObject> data = changeMajorRep.getInstructorPendingChangMajorPetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		//if not found
		return 0;
	}
	
	@Override
	public Integer getInstructorOverloadRequestPending(String mail) 
	{	
		List<PendingPetitionCountObject> data = overloadRequestRep.getInstructorPendingOverLoadPetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		//if not found
		return 0;
	}

	@Override
	public Integer getInstructorCourseRepeatPending(String mail)
	{
		Employee inst = instructorRep.getByMail(mail);
		if(inst != null)
		{
			List<CourseRepeatDTO> forms = instructorCourseRepeatServ.getPendingFormsOfInstructor(inst.getId());
			if(forms != null)
				return forms.size();
			else
				return 0;
		}
		else
			return 0;
	}
	
	@Override
	public Integer getAdminAcademicPetitionsPending() 
	{
		List<CoursePetitionDTO> forms = adminAcademicPetAppServ.getPendingPet();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminAcademicPetitionsOld() 
	{
		List<CoursePetitionDTO> forms = adminAcademicPetAppServ.getOldPet();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getAdminAddDropPending() 
	{
		List<DropAddFormDTO> forms = adminAddDropAppServ.getPendingFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminAddDropOld() 
	{
		List<DropAddFormDTO> forms = adminAddDropAppServ.getArchievedFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getAdminChangeMajorPending() 
	{
		List<ChangeMajorDTO> forms = adminChangeMajorAppServ.getPendingPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminChangeMajorOld() 
	{
		List<ChangeMajorDTO> forms = adminChangeMajorAppServ.getArchievedPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getAdminOverloadRequestPending() 
	{
		List<OverloadRequestDTO> forms = adminOverloadRequestAppServ.getPendingForms();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminOverloadRequestOld() 
	{
		List<OverloadRequestDTO> forms = adminOverloadRequestAppServ.getArchievedForms();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminCourseRepeatPending()
	{
		List<CourseRepeatDTO> forms = adminCourseRepeatServ.getPendingFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getAdminCourseRepeatOld()
	{
		List<CourseRepeatDTO> forms = adminCourseRepeatServ.getArchievedFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getStudentAcademicPetitionsPending(Integer studentId)
	{
		List<CoursePetitionDTO> forms = studentAcademicPetAppServ.getPendingPetOfStudent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getStudentAddDropPending(Integer studentId)
	{
		List<DropAddFormDTO> forms = studentAddDropAppServ.getPendingFormsOfStudent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getStudentChangeMajorPending(Integer studentId)
	{
		List<ChangeMajorDTO> forms = studentChangeMajorAppServ.getPendingPetitionsOfstuent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getStudentOverloadRequestPending(Integer studentId)
	{
		List<OverloadRequestDTO> forms = studentOverloadRequestAppServ.getPendingRequestsOfStudent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}
	
	@Override
	public Integer getStudentCourseRepeatPending(Integer studentId)
	{
		List<CourseRepeatDTO> forms = strudentCourseRepeatServ.getPendingPetitionsOfstuent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	
	/**
	 * @author Omnya
	 * 
	 * Incomplete grade in dashboard
	 */
	
	@Override
	public Integer getStudentIncompleteGradePending(Integer studentId) {
		
		List<IncompleteGradeDTO> forms = studentIncompleteGradeService.getPendingPetitionsOfstuent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
		
	}

	
	@Override
	public Integer getAdmissionDepartmentIncompleteGradePending() {
		List<IncompleteGradeDTO> forms = registrarIncompleteGradeService.getPendingPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getAdmissionHeadIncompleteGradePending() {
		List<IncompleteGradeDTO> forms = admissionHIncompleteGradeService.getPendingFormsOfAdmissionHead();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getInstructorIncompleteGradePending(String mail) {
		
		try{
		List<IncompleteGradeDTO> forms = instructorIncompleteGradeService.getPendingFormsOfInstructor(instructorRep.getByMail(mail).getId());
		List<IncompleteGradeDTO> paForms = instructorIncompleteGradeService.getPendingFormsOfPA(instructorRep.getByMail(mail).getId());
		boolean existed=false;
		for(int i=0;i<paForms.size();i++)
		{
			existed=false;
			for(int j=0;j<forms.size();j++)
			{
				if(forms.get(j).getInstructor().getId().equals(paForms.get(i).getMajor().getHeadOfMajor().getId()))
						{
					existed=true;
					break;
					
						}
			}
			if(!existed)
			forms.add(paForms.get(i));
		}
		if(forms != null)
			return forms.size();
		else
			return 0;
		}
		catch(Exception ex){
			ex.toString();
			return 0;
		}
	}

	@Override
	public Integer getDeanIncompleteGradePending() {
		
		try{
		List<IncompleteGradeDTO> forms = instructorIncompleteGradeService.getPendingFormsOfDean();
		if(forms != null)
			return forms.size();
		else
			return 0;
		}
		catch(Exception ex){
			ex.toString();
			return 0;
		}
	}

	@Override
	public Integer getAdminIncompleteGradePending() {
		List<IncompleteGradeDTO> forms = adminIncompleteGradeService.getPendingForms();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getAdminIncompleteGradeOld() {
		List<IncompleteGradeDTO> forms = adminIncompleteGradeService.getArchievedForms();
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public Integer getDeanJuniorTAPending() {
		try{
		List<TAJuniorProgramDTO> forms = instructorJuniorTAService.getPendingByDeanDashboard();
		if(forms != null)
			return forms.size();
		else
			return 0;
		}
		catch(Exception ex){
			ex.toString();
			return 0;
		}
	}

	@Override
	public Integer getInstructorJuniorTAPending(String mail) {
		try{
		// get instructor 
		Employee ins=instructorRep.getByMail(mail);
		// get by course coordinator 
		List<TAJuniorProgramDTO> coordPending=instructorJuniorTAService.getPendingByCoordDashboard(ins.getId());
		// get by PA 
		List<TAJuniorProgramDTO> paPending=instructorJuniorTAService.getPendingByPADashboard(ins.getId());
		
		for(int i=0;i<paPending.size();i++)
		{
			coordPending.add(paPending.get(i));
		}
		// add to list to each other
		return coordPending.size();
		}
		catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer getStudentJuniorTAPending(Integer studentId) {
		try{
			List<TAJuniorProgramDTO> forms = studentJuniorTAService.getPendingRequestsOfStudent(studentId);
			if(forms != null)
				return forms.size();
			else
				return 0;
			}
			catch(Exception ex){
				ex.toString();
				return 0;
			}
	}
	
	
}
