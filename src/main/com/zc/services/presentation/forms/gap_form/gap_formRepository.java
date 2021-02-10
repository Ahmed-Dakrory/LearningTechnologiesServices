/**
 * 
 */
package main.com.zc.services.presentation.forms.gap_form;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface gap_formRepository {

	public List<gap_form> getAll();
	public List<gap_form> getAllRefused();
	public gap_form addgap_form(gap_form ccc);
	public List<gap_form> getByStudentId(int id);
	public List<gap_form> getByMajorId(int id);
	public List<gap_form> getAllForStepAndMajorId(int id,int step);
	public List<gap_form> getAllForStep(int step);
	public gap_form getById(int id);
	public boolean delete(gap_form ccc);
}
