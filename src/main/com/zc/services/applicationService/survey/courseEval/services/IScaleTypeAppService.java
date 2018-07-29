/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services;

import java.util.List;

import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IScaleTypeAppService {

	public List<ScaleTypeDTO> getAll();
	public ScaleTypeDTO getById(Integer id);
	public ScaleTypeDTO add(ScaleTypeDTO form) ;
	public ScaleTypeDTO update(ScaleTypeDTO form) ;
	public Boolean delete(ScaleTypeDTO form) ;
	
	
}
