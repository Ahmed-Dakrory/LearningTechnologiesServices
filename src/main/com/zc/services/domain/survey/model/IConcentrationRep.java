/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IConcentrationRep {

	public Concentration add(Concentration form);
	public boolean remove(Integer id);
	public Concentration update(Concentration form);
	public List<Concentration> getAll();
	public Concentration getById(Integer id);
	public Concentration getByName(String name);
	public List<Concentration> getByParentID(Integer id);
	public boolean hide(Integer id);
}
