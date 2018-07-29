/**
 * 
 */
package main.com.zc.services.domain.elections.model;

import java.util.List;


/**
 * @author omnya
 *
 */
public interface IElectionsResultsRepository {

	public ElectionResults addResult(ElectionResults result);
	public ElectionResults findByID(int studentID);
	public List<ElectionResults> getAll();
	public List<ElectionResults> getByPresidentID(int id);
	public List<ElectionResults> getByViceID(int id);
	/*public List<ElectionResults> getByActvID(int id);
	public List<ElectionResults> getByServiceID(int id);
	public List<ElectionResults> getByAcadID(int id);*/
	public List<ElectionResults> getByActvPresidentID(int id);
	public List<ElectionResults> getByServicePresidentID(int id);
	public List<ElectionResults> getByAcadPresidentID(int id);
}
