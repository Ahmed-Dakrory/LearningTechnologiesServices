/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import java.util.List;

import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IMajorAppService {
	public boolean isMajorHead(Integer empID);
	public List<MajorDTO> getAll();
	public MajorDTO getById(int id);
	public List<MajorDTO> getAvailableOnly();
	public MajorDTO addMajor(MajorDTO major);
	public boolean deleteMajor(MajorDTO major);
	public MajorDTO updateMajor(MajorDTO major);
	public boolean deleteCocnentration(BaseDTO con);
	public boolean hideConcentration(Integer id);
	public BaseDTO addConcentration(BaseDTO con);
}
