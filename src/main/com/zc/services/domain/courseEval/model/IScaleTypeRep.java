/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface IScaleTypeRep {

	public List<ScaleType> getAll();
	public ScaleType getById(Integer id);
	public ScaleType add(ScaleType form) ;
	public ScaleType update(ScaleType form) ;
	public Boolean delete(ScaleType form) ;
	public ScaleType getByName(String name);
	
}
