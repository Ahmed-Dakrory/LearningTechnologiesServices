/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services;
import java.util.List;

import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IFormsStatusAppService {
	public FormsStatusDTO add(FormsStatusDTO form);

	public boolean remove(FormsStatusDTO dto);

	public FormsStatusDTO update(FormsStatusDTO form);

	public List<FormsStatusDTO> getAll();

	public FormsStatusDTO getById(Integer id);
	public FormsStatusDTO addLevelsToForm(FormsStatusDTO dto) ;
	public FormsStatusDTO updateYearAndSemesters(FormsStatusDTO dto);
}
