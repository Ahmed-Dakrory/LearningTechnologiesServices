/**
 * 
 */
package main.com.zc.services.presentation.users.facade;

import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
public interface IGetLoggedInStudentDataFacade {
	public PersonDataDTO getPersonByPersonMail(String mail);
	public PersonDataDTO getPersonByFileNo(Integer id);
}
