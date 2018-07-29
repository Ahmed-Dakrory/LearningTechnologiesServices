/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface IScaleSelectionsRep {

	public List<ScaleSelections> getAll();
	public ScaleSelections getById(Integer id);
	public List<ScaleSelections> getByScaleTypeId(Integer id);
	public List<ScaleSelections> getByScaleTypeIDAndSelType(Integer id,Integer type);
	public ScaleSelections add(ScaleSelections form) ;
	public ScaleSelections update(ScaleSelections form) ;
	public Boolean delete(ScaleSelections form) ;
	
	
}
