/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.facade;

import java.io.IOException;
import java.util.List;

import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;

/**
 * @author Omnya Alaa
 *
 */
public interface IParseDailyAttPetFileFacade {

	public List<DailyAttPetDto> parsePetFile(String fileName) throws IOException;
}
