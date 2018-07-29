/**
 * 
 */
package main.com.zc.services.domain.feedback.model;

import java.util.List;

import main.com.zc.services.domain.shared.exception.DuplicatedRowException;



/**
 * @author Omnya Alaa
 *
 */
public interface IFeedbackRepository {
	public Feedback add(Feedback feedback) throws DuplicatedRowException;
	public boolean remove(Integer id);
	public Feedback update(Feedback feedback);
	public List<Feedback> getAll();
	public List<Feedback> getFeedbackPersonID(Integer personId);
	public Feedback getById(Integer id);
	
	//New version
	public List<Feedback>  getByCategId(Integer id);
	public List<Feedback>  getByPersonIdAndStep(Integer id,Integer step);
	public List<Feedback>  getByCategoryAndStep(Integer id,Integer step);
	public List<Feedback>  getByMajorAndStep(Integer id,Integer step);
	public List<Feedback>  getByMajorHeadAndStep(Integer id,Integer step);
	public List<Feedback>  getByStep(Integer step);
	public List<Feedback> getoldByStudentID(Integer id);
	public List<Feedback>  getByCategoryHeadAndStep(Integer id,Integer step);
	public List<Feedback> getPendingByMajorHead(Integer insID);
	public List<Feedback> getOldByMajorHead(Integer insID);
	public List<Feedback> getHandlerOld();
	public List<Feedback> getPendingByCategoryHead(Integer id);
	public List<Feedback> getOldByCategoryHead(Integer id);
}
