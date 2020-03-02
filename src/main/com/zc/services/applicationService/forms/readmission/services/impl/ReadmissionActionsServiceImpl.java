/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IReadmissionActionsService;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 * @author Eman
 */
@Service
public class ReadmissionActionsServiceImpl implements IReadmissionActionsService{

	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
	ReadmissionAssembler assem=new ReadmissionAssembler();
	@Autowired
	IReadmissionFormRep rep;
	@Autowired
	IStudentProfileService studentProfileService;
	
	@Override
	public ReadmissionDTO getByID(Integer id) {
		ReadmissionDTO dto=new ReadmissionDTO();
		try{
			ReadmissionForm form=rep.getById(id);
			List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(form.getId(),FormTypesEnum.READMISSION.getValue());
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
			dto=assem.toDTO(form);
			dto.getStudent().setStudentProfileDTO(studentProfileService.getCurrentPRofileByStudentID(form.getStudent().getId()));
			dto.setActionDTO(actionsDTO);
		   
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		try{
		List<Employee> ins=insRep.getAll();
		for(int i=0;i<ins.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(ins.get(i).getId());
			dto.setName(ins.get(i).getName());
			dto.setMail(ins.get(i).getMail());
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			
		}
		return dtos;
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		try
		{
			// there is no old action
			if(dto.getId()==null)
			{
				PetitionsActions action=new PetitionsActions();
				action.setActionType(dto.getActionType());
				action.setComment(dto.getComment());
				action.setDate(dto.getDate());
				action.setFormType(FormTypesEnum.READMISSION);
				Employee ins=new Employee();
				ins.setId(instructorID);
				action.setInstructor(ins);
				action.setPetitionID(dto.getPetitionID());
				actionRep.add(action);
			}
			else // there is no old action
			{
				PetitionsActions action=actionRep.getById(dto.getId());
				action.setActionType(dto.getActionType());
				action.setComment(dto.getComment());
				action.setDate(dto.getDate());
				action.setFormType(FormTypesEnum.READMISSION);
				Employee ins=new Employee();
				ins.setId(instructorID);
				action.setInstructor(ins);
				action.setPetitionID(dto.getPetitionID());
				actionRep.update(action);
			}
		}
		catch(Exception ex)
		{
			
		}
		
		
	}

	@Override
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto) {
try{
			
			ReadmissionForm form=assem.toEntity(dto);
			ReadmissionForm oldForm=rep.getById(dto.getId());
			String history=oldForm.getForwardHistory();
			if(history!=null)
			{
			history+=","+dto.getForwardFromIns().getId()+"-"+dto.getForwardTOIns().getId();
			}
			else 
			{	history=dto.getForwardFromIns().getId()+"-"+dto.getForwardTOIns().getId();
			}
			
			form.setForwardHistory(history);
			
			form=rep.update(form);
			dto=assem.toDTO(form);
		    InstructorDTO toIns=new InstructorDTO();
		    toIns.setId(form.getForwardTOIns().getId());
		    dto.setForwardTOIns(toIns);
		    InstructorDTO fromIns=new InstructorDTO();
		    fromIns.setId(form.getForwardFromIns().getId());
		    dto.setForwardFromIns(fromIns);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto) {
		try
		{
			ReadmissionForm form=assem.toEntity(dto);
			// First update actions list
			if(dto.getActionDTO()!=null)
			{
				for(int i=0;i<dto.getActionDTO().size();i++)
				{
					if(dto.getActionDTO().get(i).getId()!=null)
					{
						//update
						PetitionsActions action=actionRep.getById(dto.getActionDTO().get(i).getId());
						action.setActionType(dto.getActionDTO().get(i).getActionType());
						action.setComment(dto.getActionDTO().get(i).getComment());
                        action.setDate(dto.getActionDTO().get(i).getDate());
                        action.setFormType(dto.getActionDTO().get(i).getFormType());
                        
                        actionRep.update(action);
                        
                        
						
					}
					else
						//add
						{
						PetitionsActions action=new  PetitionsActions();
						action.setActionType(dto.getActionDTO().get(i).getActionType());
						action.setComment(dto.getActionDTO().get(i).getComment());
                        action.setDate(dto.getActionDTO().get(i).getDate());
                        action.setFormType(dto.getActionDTO().get(i).getFormType());
                        Employee actionIns=new Employee();
                        actionIns.setId(dto.getActionDTO().get(i).getInstructorID());
                        action.setInstructor(actionIns);
                        action.setPetitionID(dto.getId());
                        
                        actionRep.add(action);
						}
						
				}
			}
			// Second update the petition itself
            
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JReadmissionForm",form.getId());
			}	
			return assem.toDTO(form);
			}
		
		
		
		catch(Exception ex)
		{
			System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ReadmissionDTO update(ReadmissionDTO dto) {
		// TODO Auto-generated method stub
		try{
			ReadmissionForm form=assem.toEntity(dto);
			form=rep.update(form);
			return assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	



}
