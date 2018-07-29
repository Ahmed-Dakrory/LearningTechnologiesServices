/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.facade;

import java.util.List;

import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;

/**
 * @author Omnya Alaa
 *
 */
public interface IEditDailyAttendanceFacade {

	public void sendEmailByEditingDailyAtt(List<DailyAttPetDto> daos);
}
