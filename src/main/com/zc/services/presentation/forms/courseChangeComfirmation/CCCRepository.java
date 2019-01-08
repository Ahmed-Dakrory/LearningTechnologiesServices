/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface CCCRepository {

	public List<CCC> getAll();
	public CCC addCCC(CCC ccc);
	public List<CCC> getByStudentId(int id);
	public List<CCC> getByMajorId(int id);
	public List<CCC> getAllForStepAndMajorId(int id,int step);
	public List<CCC> getAllForStepAndType(int type,int step);
	public List<CCC> getAllForStep(int step);
	public CCC getById(int id);
	public boolean delete(CCC ccc);
}
