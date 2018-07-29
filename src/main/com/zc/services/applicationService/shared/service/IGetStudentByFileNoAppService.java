/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IGetStudentByFileNoAppService {

	public PersonDataDTO getStudentDataByFileNo(int fileNo);
}
