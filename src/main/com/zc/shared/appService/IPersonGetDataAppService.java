/**
 * 
 */
package main.com.zc.shared.appService;

import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author Omnya Alaa
 *
 */

public interface IPersonGetDataAppService {
public PersonDataDTO getPersonByPersonMail(String mail);
public PersonDataDTO getPersonByFileNo(Integer id);

}
