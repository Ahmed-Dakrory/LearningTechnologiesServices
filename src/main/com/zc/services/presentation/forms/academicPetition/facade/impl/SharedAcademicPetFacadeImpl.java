/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * description
 * @author heba
 * @since Feb 12, 2015
 * @lastUpdated  Feb 12, 2015
 */
@Service("SharedAcademicPetFacadeImpl")
public class SharedAcademicPetFacadeImpl implements ISharedAcademicPetFacade{
	@Autowired
	ISharedNotifyService sharedNotifyService ;
	
	@Override
	public void notifayNextStepOwner(CoursePetitionDTO dto) {
		
		sharedNotifyService.notifayNextStepOwner(dto);
	}

	@Override
	public void notifayNextStepOwner(DropAddFormDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
	}

	@Override
	public void notifayNextStepOwner(ChangeMajorDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
	}

	@Override
	public void notifayNextStepOwner(OverloadRequestDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
	}

	/**
	 * @author omnya.alaa
	 *
	 */
	@Override
	public void notifayNextStepOwner(CourseRepeatDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
		
	}

	@Override
	public void notifyAt(CoursePetitionDTO detailedForm, String name) throws Exception {
		sharedNotifyService.notifyAt(detailedForm,  name) ;
		
	}

	@Override
	public void notifyAt(DropAddFormDTO addFormDTO, String name) throws Exception {
		sharedNotifyService.notifyAt(addFormDTO,  name) ;
			
	}

	@Override
	public void notifyAt(ChangeMajorDTO changeMajorDTO, String name) throws Exception {
		sharedNotifyService.notifyAt(changeMajorDTO,  name) ;
			
	}

	@Override
	public void notifyAt(CourseRepeatDTO courseRepeatDTO, String name) throws Exception {
		sharedNotifyService.notifyAt(courseRepeatDTO,  name) ;
			
	}

	@Override
	public void notifyAt(OverloadRequestDTO overloadRequestDTO, String name)throws Exception {
		sharedNotifyService.notifyAt(overloadRequestDTO,  name) ;
			
	}
	/**
	 * @author omnya.alaa
	 *
	 */

	@Override
	public void notifayNextStepOwner(IncompleteGradeDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
		
	}
	/**
	 * @author omnya.alaa
	 *
	 */
	@Override
	public void notifyAt(IncompleteGradeDTO incompleteDto, String name)
			throws Exception {
		sharedNotifyService.notifyAt(incompleteDto, name);
		
	}

	@Override
	public void notifayNextStepOwner(TAJuniorProgramDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
		
	}

	@Override
	public void notifyAt(TAJuniorProgramDTO juniorTADTO, String name)
			throws Exception {
		sharedNotifyService.notifyAt(juniorTADTO, name);
		
	}

	@Override
	public void notifayNextStepOwner(ChangeConcentrationDTO dto) {
		sharedNotifyService.notifayNextStepOwner(dto);
		
	}

	@Override
	public void notifyAt(ChangeConcentrationDTO changeConcenDTO, String name) throws Exception {
		sharedNotifyService.notifyAt(changeConcenDTO,name);
		
	}

	@Override
	public void notifayNextStepOwner(ReadmissionDTO dto) {
		// TODO Auto-generated method stub
		sharedNotifyService.notifayNextStepOwner(dto);
	}

	@Override
	public void notifyAt(ReadmissionDTO readmissionDTO, String name) throws Exception {
		// TODO Auto-generated method stub
		sharedNotifyService.notifyAt(readmissionDTO,name);
		
	}


}
