/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services;

import java.util.List;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IScaleSelectionsAppService {

	public List<ScaleSelectionsDTO> getAll();
	public ScaleSelectionsDTO getById(Integer id);
	public List<ScaleSelectionsDTO> getByScaleTypeId(Integer id);
	public List<ScaleSelectionsDTO> getByScaleTypeIDAndSelType(Integer id,Integer type);
	public List<ScaleSelectionsDTO> getByScaleTypeStrength(Integer id);
	public List<ScaleSelectionsDTO> getByScaleTypeConcern(Integer id);
	public List<ScaleSelectionsDTO> getByScaleTypeMain(Integer id);
	public ScaleSelectionsDTO add(ScaleSelectionsDTO form) ;
	public ScaleSelectionsDTO update(ScaleSelectionsDTO form) ;
	public Boolean delete(ScaleSelectionsDTO form) ;
	
}
