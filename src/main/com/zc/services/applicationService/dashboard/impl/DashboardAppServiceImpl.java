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
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.changeMajor.services.IAdminChangeMajorService;
import main.com.zc.services.applicationService.forms.changeMajor.services.IStudentChangeMajorService;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmincourse_replacement_formService;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IStudentcourse_replacement_formService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdminService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionDeptService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionHeadService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeInsService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeStudentService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdminOverloadRequestService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IStudentOverloadRequestService;
import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IAdminReadmissionService;
import main.com.zc.services.applicationService.forms.readmission.services.IStudentReadmissionService;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.IJuniorTAServiceInstructor;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ITAJuniorProgramServiceStudent;
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
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacement;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacementRepository;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;
import main.com.zc.services.presentation.forms.gap_form.gap_form;
import main.com.zc.services.presentation.forms.gap_form.gap_formRepository;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
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
	IChangeConcentrationRep changeConcentrationRep;
	
	
	@Autowired
	IMajorRepository majorRep;
	
	@Autowired
	IEmployeeRepository emplRep;

	@Autowired
	courseReplacementRepository courseReplacementRep;
	

	@Autowired
	gap_formRepository gap_formRep;

	@Autowired
	IOverloadRequestRep overloadRequestRep;
	

	@Autowired
	IReadmissionFormRep readmissionRep;
	

	@Autowired
	Icourse_replacement_formFormRep course_replacement_formRep;
	
	@Autowired
	IAdmissionAdminAcademicPetService adminAcademicPetAppServ;
	@Autowired
	IAdminAddDropFormService adminAddDropAppServ;
	@Autowired
	IAdminChangeMajorService adminChangeMajorAppServ;

	@Autowired
	IAdminReadmissionService adminReadmissionAppServ;
	
	

	@Autowired
	IAdmincourse_replacement_formService admincourse_replacement_formAppServ;
	
	
	@Autowired
	IAdminOverloadRequestService adminOverloadRequestAppServ;
	
	@Autowired
	IStudentAcademicPetitionService studentAcademicPetAppServ;
	@Autowired
	IStudentAddDropFormServices studentAddDropAppServ;
	@Autowired
	IStudentChangeMajorService studentChangeMajorAppServ;
	@Autowired
	IStudentReadmissionService studentReadmissionAppServ;
	

	@Autowired
	IStudentcourse_replacement_formService studentcourse_replacement_formAppServ;
	
	
	
	
	
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
	

	@Autowired
	IPetitionsActionsRep actionRep;
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

	

	
	
	
	
	
	@Override
	public Integer getAdminReadmissionPending() {
		List<ReadmissionDTO> forms = adminReadmissionAppServ.getPendingPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public Integer getAdminReadmissionOld() {
		List<ReadmissionDTO> forms = adminReadmissionAppServ.getArchievedPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public Integer getInstructorReadmissionPending(Integer employeId,String mail) {
		
		if(employeId.equals(Constants.ADMISSION_DEPT_ID)) {
			List<ReadmissionForm> data =getPendingFormsOfAdmission();
			return Integer.valueOf(data.size());
		}else if(employeId.equals(Constants.DEAN_OF_ACADEMIC_ID)) {
			List<ReadmissionDTO> data = getPendingFormsOfDeanOfAcademic();
			return Integer.valueOf(data.size()); 
		}else if(employeId.equals(Constants.DEAN_OF_STRATEGIC_ID)) {
			List<ReadmissionDTO> data = getPendingFormsOfDean();
			return Integer.valueOf(data.size());
		}
		
		//if not found
		return 0;
	}

    ReadmissionAssembler assem=new ReadmissionAssembler();
public List<ReadmissionDTO> getPendingFormsOfDean() {
		
		List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
		try{
			List<ReadmissionForm> allForms=readmissionRep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
					List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
					if(actions!=null)
					{
						for(int a=0;a<actions.size();a++)
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setId(actions.get(a).getId());
							actionDTO.setComment(actions.get(a).getComment());
							actionDTO.setDate(actions.get(a).getDate());
							actionDTO.setFormType(actions.get(a).getFormType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
							actionDTO.setPetitionID(actions.get(a).getPetitionID());
							actionDTO.setActionType(actions.get(a).getActionType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
							actionsDTO.add(actionDTO);
						}
					}
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
					List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
					if(actions!=null)
					{
						for(int a=0;a<actions.size();a++)
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setId(actions.get(a).getId());
							actionDTO.setComment(actions.get(a).getComment());
							actionDTO.setDate(actions.get(a).getDate());
							actionDTO.setFormType(actions.get(a).getFormType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
							actionDTO.setPetitionID(actions.get(a).getPetitionID());
							actionDTO.setActionType(actions.get(a).getActionType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
							actionsDTO.add(actionDTO);
						}
					}
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
		}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}


public List<ReadmissionDTO> getPendingFormsOfDeanOfAcademic() {
	
	List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
	try{
		List<ReadmissionForm> allForms=readmissionRep.getAll();
	for(int i=0;i<allForms.size();i++)
	{
		
		if(allForms.get(i).getPerformed()==null)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
			}
		}
		else 
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
			}
		}
	}
	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return filterdDTO;
}

	

public List<ReadmissionForm> getPendingFormsOfAdmission() {
	
	List<ReadmissionForm> filterdDTO=new ArrayList<ReadmissionForm>();
	try{
		List<ReadmissionForm> allForms=readmissionRep.getAll();
	for(int i=0;i<allForms.size();i++)
	{
		if(allForms.get(i).getPerformed()==null)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(allForms.get(i));
			}
		}
		else if(allForms.get(i).getPerformed()!=true)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(allForms.get(i));
			}
		}
	}
	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return filterdDTO;
}


	@Override
	public Integer getStudentReadmissionPending(Integer studentId) {
		List<ReadmissionDTO> forms = studentReadmissionAppServ.getPendingPetitionsOfstuent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public List<ReadmissionForm> getDeanReadmissionPending() {
		List<ReadmissionForm> forms = readmissionRep.getDeanPendingReadmissionForm(true);
		return forms;
	}

	@Override
	public List<ReadmissionForm> getAdmissionHeadReadmissionPending() {
		List<ReadmissionForm> forms = readmissionRep.getAdmissionHeadPendingReadmissionForm(true);
		return forms;
	}

	@Override
	public List<ReadmissionForm> getAdmissionDepartmentReadmissionPending() {
		List<ReadmissionForm> forms = getPendingFormsOfAdmission();
		return forms;
	}

	
	
	
	
	
	
	//course_replacement_form

	@Override
	public Integer getAdmincourse_replacement_formPending() {
		List<course_replacement_formDTO> forms = admincourse_replacement_formAppServ.getPendingPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public Integer getAdmincourse_replacement_formOld() {
		List<course_replacement_formDTO> forms = admincourse_replacement_formAppServ.getArchievedPetitionsOfstuent();
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public Integer getInstructorcourse_replacement_formPending(Integer employeId,String mail) {
		
		if(employeId.equals(Constants.ADMISSION_DEPT_ID)) {
			List<course_replacement_formForm> data =getPendingFormsOfAdmissionCRF();
			return Integer.valueOf(data.size());
		}else if(employeId.equals(Constants.DEAN_OF_ACADEMIC_ID)) {
			List<course_replacement_formDTO> data = getPendingFormsOfDeanOfAcademicCRF();
			return Integer.valueOf(data.size()); 
		}else if(employeId.equals(Constants.DEAN_OF_STRATEGIC_ID)) {
			List<course_replacement_formDTO> data = getPendingFormsOfDeanCRF();
			return Integer.valueOf(data.size());
		}
		
		//if not found
		return 0;
	}

    course_replacement_formAssembler assem2=new course_replacement_formAssembler();

    public List<course_replacement_formDTO> getPendingFormsOfDeanCRF() {
			
			List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
			try{
				List<course_replacement_formForm> allForms=course_replacement_formRep.getAll();
			for(int i=0;i<allForms.size();i++)
			{
				
				if(allForms.get(i).getPerformed()==null)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
					{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
						List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
						if(actions!=null)
						{
							for(int a=0;a<actions.size();a++)
							{
								PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
								actionDTO.setId(actions.get(a).getId());
								actionDTO.setComment(actions.get(a).getComment());
								actionDTO.setDate(actions.get(a).getDate());
								actionDTO.setFormType(actions.get(a).getFormType());
								if(actions.get(a).getInstructor()!=null)
								actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
								actionDTO.setPetitionID(actions.get(a).getPetitionID());
								actionDTO.setActionType(actions.get(a).getActionType());
								if(actions.get(a).getInstructor()!=null)
								actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
								actionsDTO.add(actionDTO);
							}
						}
						course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
					     filterdDTO.add(dto);
					}
				}
				else if(allForms.get(i).getPerformed()!=true)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
					{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
						List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
						if(actions!=null)
						{
							for(int a=0;a<actions.size();a++)
							{
								PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
								actionDTO.setId(actions.get(a).getId());
								actionDTO.setComment(actions.get(a).getComment());
								actionDTO.setDate(actions.get(a).getDate());
								actionDTO.setFormType(actions.get(a).getFormType());
								if(actions.get(a).getInstructor()!=null)
								actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
								actionDTO.setPetitionID(actions.get(a).getPetitionID());
								actionDTO.setActionType(actions.get(a).getActionType());
								if(actions.get(a).getInstructor()!=null)
								actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
								actionsDTO.add(actionDTO);
							}
						}
						course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
					     filterdDTO.add(dto);
					}
				}
			}
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return filterdDTO;
		}
	
	public List<course_replacement_formDTO> getPendingFormsOfDeanOfAcademicCRF() {
		
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
			List<course_replacement_formForm> allForms=course_replacement_formRep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
					List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
					if(actions!=null)
					{
						for(int a=0;a<actions.size();a++)
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setId(actions.get(a).getId());
							actionDTO.setComment(actions.get(a).getComment());
							actionDTO.setDate(actions.get(a).getDate());
							actionDTO.setFormType(actions.get(a).getFormType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
							actionDTO.setPetitionID(actions.get(a).getPetitionID());
							actionDTO.setActionType(actions.get(a).getActionType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
							actionsDTO.add(actionDTO);
						}
					}
					course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
					List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
					if(actions!=null)
					{
						for(int a=0;a<actions.size();a++)
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setId(actions.get(a).getId());
							actionDTO.setComment(actions.get(a).getComment());
							actionDTO.setDate(actions.get(a).getDate());
							actionDTO.setFormType(actions.get(a).getFormType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
							actionDTO.setPetitionID(actions.get(a).getPetitionID());
							actionDTO.setActionType(actions.get(a).getActionType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
							actionsDTO.add(actionDTO);
						}
					}
					course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
		}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}
	
		
	
	public List<course_replacement_formForm> getPendingFormsOfAdmissionCRF() {
	
	List<course_replacement_formForm> filterdDTO=new ArrayList<course_replacement_formForm>();
	try{
		List<course_replacement_formForm> allForms=course_replacement_formRep.getAll();
	for(int i=0;i<allForms.size();i++)
	{
		if(allForms.get(i).getPerformed()==null)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(allForms.get(i));
			}
		}
		else if(allForms.get(i).getPerformed()!=true)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				course_replacement_formDTO dto=assem2.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(allForms.get(i));
			}
		}
	}
	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return filterdDTO;
}


	@Override
	public Integer getStudentcourse_replacement_formPending(Integer studentId) {
		List<course_replacement_formDTO> forms = studentcourse_replacement_formAppServ.getPendingPetitionsOfstuent(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;

	}

	@Override
	public List<course_replacement_formForm> getDeancourse_replacement_formPending() {
		List<course_replacement_formForm> forms = course_replacement_formRep.getDeanPendingcourse_replacement_formForm(true);
		return forms;
	}

	

	@Override
	public List<course_replacement_formForm> getAdmissionDepartmentcourse_replacement_formPending() {
		List<course_replacement_formForm> forms = getPendingFormsOfAdmissionCRF();
		return forms;
	}

	@Override
	public Integer getInstructorHeadcourse_replacement_formPending(String mail) {
		
		List<PendingPetitionCountObject> data = course_replacement_formRep.getInstructorPendingcourse_replacement_formPetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		//if not found
		return 0;
	}

	@Override
	public Integer getDeanCourseReplacementFormsPending() {
		// TODO Auto-generated method stub
		List<courseReplacement> pendingFormsList = courseReplacementRep.getAllForStep(courseReplacement.STEP_DeanOfStratigicEnrollment);

		return pendingFormsList.size();
	}

	@Override
	public Integer getAdmissionDepartmentCourseReplacementFormsPending() {
		// TODO Auto-generated method stub
		List<courseReplacement> pendingFormsList = courseReplacementRep.getAllForStep(courseReplacement.STEP_Registerar);
		
		
		return pendingFormsList.size();
	}

	@Override
	public Integer getAdmissionDepartmentCourseReplacementFormsAuditing() {
		List<courseReplacement> auditingFormList = courseReplacementRep.getAllForStep(courseReplacement.STEP_AUDITING);
		
		return auditingFormList.size();
	}

	@Override
	public Integer getInstructorCourseReplacementForms( String mail) {
		// TODO Auto-generated method stub
		//this mean the head of major
		Employee empl = emplRep.getByMail(mail);
		List<Majors> majorDetails=majorRep.getByInsID(empl.getId());
		for(int i=0;i<majorDetails.size();i++) {
			if(majorDetails.get(i).getHeadOfMajorId().getMail().equals(mail)){
				List<courseReplacement> pendingFormsList = courseReplacementRep.getAllForStepAndMajorId(majorDetails.get(i).getId(), courseReplacement.STEP_MajorHead);
				return pendingFormsList.size();
			}
		}
		return 0;
		
	}

	@Override
	public Integer getStudentCourseReplacementForms(Integer studentId) {
		// TODO Auto-generated method stub
		List<courseReplacement> all =new ArrayList<>();

		List<courseReplacement> courseChangeComfirmations = courseReplacementRep.getByStudentId(studentId);
		for(int i=0;i<courseChangeComfirmations.size();i++) {
			courseReplacement cR= courseChangeComfirmations.get(i);
			if(cR.getAction()==courseReplacement.STATE_INPROCESS) {
				all.add(cR);
			}
		}
		return all.size();
	}

	@Override
	public Integer getAccreditionEngHeadCourseReplacementPending() {
			
				List<courseReplacement> pendingFormsList = courseReplacementRep.getAllForStepAndType(courseReplacement.TYPE_ENGINEERING, courseReplacement.STEP_DirectorOfAccredition);
				return pendingFormsList.size();
			
	}

	@Override
	public Integer getAccreditionSciHeadCourseReplacementPending() {
		// TODO Auto-generated method stub
		List<courseReplacement> pendingFormsList = courseReplacementRep.getAllForStepAndType(courseReplacement.TYPE_SCIENCE, courseReplacement.STEP_DirectorOfAccredition);
		return pendingFormsList.size();
	}

	

	

	@Override
	public List<ChangeConcentration> getAdmissionDepartmentChangeConcentrationPending() {
		List<ChangeConcentration> forms = changeConcentrationRep.getAdDeptPendingNoReminder();
		return forms;
	}

	@Override
	public List<ChangeConcentration> getDeanChangeConcentrationPending() {
		List<ChangeConcentration> forms = changeConcentrationRep.getDeanOfAcademicPending(1);
		return forms;
	}

	

	@Override
	public Integer getInstructorChangeConcentrationPending(String mail) {
		List<PendingPetitionCountObject> data = changeConcentrationRep.getInstructorPendingChangMajorPetition(true);
		for (PendingPetitionCountObject pendingPetitionCountObject : data) 
			if(pendingPetitionCountObject.getInstructor().getMail().equalsIgnoreCase(mail))
				return Integer.valueOf(pendingPetitionCountObject.getPetionCount().toString());
		
		//if not found
		return 0;
		
	}

	@Override
	public Integer getStudentChangeConcentrationPending(Integer studentId) {
		List<ChangeConcentration> forms = changeConcentrationRep.getPendingByStudentId(studentId);
		if(forms != null)
			return forms.size();
		else
			return 0;
	}

	@Override
	public List<ChangeConcentration> getAdmissionHeadChangeConcentrationPending() {
		List<ChangeConcentration> forms = changeConcentrationRep.getAdHeadPending(1);
		return forms;
	}

	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////  GAP form
	@Override
	public List<gap_form> getDeangap_FromPending() {
		// TODO Auto-generated method stub
				List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_DeanOfStratigicEnrollment);

				return pendingFormsList;
	}

	@Override
	public Integer getDeanGapFormsPending() {
		// TODO Auto-generated method stub
		List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_DeanOfStratigicEnrollment);

		return pendingFormsList.size();
	}

	@Override
	public List<gap_form> getAdmissionDepartmentgap_formPending() {
		// TODO Auto-generated method stub
				List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_Registerar);
				
				
				return pendingFormsList;
	}

	@Override
	public Integer getAdmissionDepartmentGapFormsPending() {
		// TODO Auto-generated method stub
				List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_Registerar);
				
				
				return pendingFormsList.size();
	}

	@Override
	public Integer getAdmissionDepartmentGapFormsAuditing() {
		List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_AUDITING);
		
		
		return pendingFormsList.size();
	}

	@Override
	public Integer getAdminGap_formPending() {
			List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_Registerar);
			if(pendingFormsList==null) pendingFormsList = new ArrayList<gap_form>();
			List<gap_form> newList = gap_formRep.getAllForStep(gap_form.STEP_AUDITING);
			if(newList==null) newList = new ArrayList<gap_form>();
			pendingFormsList.addAll(newList);
			
			newList = gap_formRep.getAllForStep(gap_form.STEP_DeanOfStratigicEnrollment);
			if(newList==null) newList = new ArrayList<gap_form>();
			pendingFormsList.addAll(newList);

			
			newList = gap_formRep.getAllForStep(gap_form.STEP_Finance);
			if(newList==null) newList = new ArrayList<gap_form>();
			pendingFormsList.addAll(newList);
			
			newList = gap_formRep.getAllForStep(gap_form.STEP_MajorHead);
			if(newList==null) newList = new ArrayList<gap_form>();
			pendingFormsList.addAll(newList);
			
			
		
		return pendingFormsList.size();
	}

	@Override
	public Integer getAdminGap_formOld() {
		List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_Finished);
		
		
		
	
	return pendingFormsList.size();
	}

	@Override
	public Integer getInstructorGap_formPending(Integer employId, String mail) {
		if(employId.equals(Constants.ADMISSION_DEPT_ID)) {
			List<gap_form> data =getAdmissionDepartmentgap_formPending();
			return Integer.valueOf(data.size());
		}else if(employId.equals(Constants.Financial_DEP_ID)) {
			List<gap_form> data =getFinanceDepartmentgap_formPending();
			return Integer.valueOf(data.size()); 
		}else if(employId.equals(Constants.DEAN_OF_STRATEGIC_ID)) {
			List<gap_form> data = gap_formRep.getAllForStep(gap_form.STEP_DeanOfStratigicEnrollment);
			return Integer.valueOf(data.size());
		}
		
		//if not found
		return 0;
	}

	@Override
	public Integer getInstructorGapForms(String mail) {
		// TODO Auto-generated method stub
				//this mean the head of major
				Employee empl = emplRep.getByMail(mail);
				List<Majors> majorDetails=majorRep.getByInsID(empl.getId());
				for(int i=0;i<majorDetails.size();i++) {
					if(majorDetails.get(i).getHeadOfMajorId().getMail().equals(mail)){
						List<gap_form> pendingFormsList = gap_formRep.getAllForStepAndMajorId(majorDetails.get(i).getId(), gap_form.STEP_MajorHead);
						System.out.println("Major: "+String.valueOf(pendingFormsList.size()));
						return pendingFormsList.size();
					}
				}
				return 0;
	}

	@Override
	public Integer getStudentGap_formPending(Integer studentId) {
		List<gap_form> pendingFormsList = gap_formRep.getByStudentId(studentId);
		if(pendingFormsList==null) pendingFormsList = new ArrayList<gap_form>();
		List<gap_form> newList = new ArrayList<gap_form>();
		
		for(int i=0;i<pendingFormsList.size();i++) {
			if(!pendingFormsList.get(i).getFormStep().equals(gap_form.STEP_Finished)) {
				newList.add(pendingFormsList.get(i));
			}
		}
		
		
		
		
	
	return newList.size();

	}

	@Override
	public Integer getStudentGapForms(Integer studentId) {
		List<gap_form> pendingFormsList = gap_formRep.getByStudentId(studentId);
		
		
		
		
	
	return pendingFormsList.size();
	}

	@Override
	public List<gap_form> getFinanceDepartmentgap_formPending() {
		List<gap_form> pendingFormsList = gap_formRep.getAllForStep(gap_form.STEP_Finance);
		
		
		
		
		return pendingFormsList;
	}

	

	
}
