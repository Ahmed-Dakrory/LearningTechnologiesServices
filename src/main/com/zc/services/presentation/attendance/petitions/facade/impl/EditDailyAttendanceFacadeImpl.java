/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.petitions.service.IEditTimeOfDailyAttAppService;
import main.com.zc.services.applicationService.attendance.petitions.service.ISendEditDailyAttMailAppService;
import main.com.zc.services.applicationService.shared.service.IGetStudentByFileNoAppService;
import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.petitions.facade.IEditDailyAttendanceFacade;
import main.com.zc.services.presentation.attendance.shared.dto.TimeDailyAttDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class EditDailyAttendanceFacadeImpl implements IEditDailyAttendanceFacade{

	@Autowired
	IEditTimeOfDailyAttAppService editDailtAttAppService;
	@Autowired
	IGetStudentByFileNoAppService getStudentAppService;
	@Autowired
	ISendEditDailyAttMailAppService sendEditMailAppService;
	@Override
	public void sendEmailByEditingDailyAtt(List<DailyAttPetDto> daos) {
		
		
		for(int i=0;i<daos.size();i++)
		{
			PersonDataDTO personDao=getStudentAppService.getStudentDataByFileNo(daos.get(i).getFileNo());
			
		/*	if(personDao.getFileNo()==201302064)
			{
				System.out.println();	*/
			
			String mail=personDao.getEmail();
			for(int j=0;j<daos.get(i).getDates().size();j++)
			{
			
				try{
				TimeDailyAttDTO updatedDao=editDailtAttAppService.editTimesExcusesToAttendance(daos.get(i),daos.get(i).getDates().get(j));
				//send email to edit att
				
				boolean sendStatus=sendEditMailAppService.sendMailByEditedAtt(mail,updatedDao.getDate());
				if(sendStatus)
				{
					editDailtAttAppService.editSendMailStatusToAttendance(daos.get(i),daos.get(i).getDates().get(j));
					System.out.println("----------- Done sening of editing att mail----------");
					System.out.println("----------- Done sening of editing att mail status----------");
				}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}}
		//}
		
		
	}

}
