/**
 * 
 */
package main.com.zc.services.presentation.shared;

import java.util.List;

import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IMajorsFacade {

	public List<MajorDTO> getAll();
	public List<MajorDTO> getAvailableOnly();
	public List<MajorDTO> getMajorsWithConcentrations();
	public MajorDTO addMajor(MajorDTO major);
	public boolean deleteMajor(MajorDTO major);
	public MajorDTO updateMajor(MajorDTO major);
	public boolean deleteCocnentration(BaseDTO con);
	public boolean hideCocnentration(BaseDTO con);
	public BaseDTO addConcentration(BaseDTO major);
	public MajorDTO getById(int id);
}
