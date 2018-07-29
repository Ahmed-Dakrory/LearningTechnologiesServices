/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionAdminAcademicPetService;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionHeadAcademicPetService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service
public class AdmissionAdminAcademicPetServiceImpl implements IAdmissionAdminAcademicPetService {

	@Autowired
	ICoursePetitionRep rep;
	AcademicPetitionAssembler assem=new AcademicPetitionAssembler();
	@Override
	public List<CoursePetitionDTO> getPendingPet() {
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		try{
		List<CoursePetition> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			CoursePetition pet = allForms.get(i);
			if(pet.getPerformed() == null || pet.getPerformed() != true)
				filterdDTO.add(assem.toDTO(pet));
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	
	}

	@Override
	public List<CoursePetitionDTO> getOldPet() {
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		try{
			
			List<CoursePetition> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				CoursePetition pet = allForms.get(i);
				if(pet.getPerformed() != null && pet.getPerformed() == true)
					filterdDTO.add(assem.toDTO(pet));
			}
			return filterdDTO;
			}
			catch(Exception ex)
			{
				System.out.println("-------Error in getting old petition");
				ex.printStackTrace();
				return filterdDTO;
			}
	}

	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		try{
			CoursePetition form=assem.toEntity(dto);
			form=rep.update(form);
			
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

}
