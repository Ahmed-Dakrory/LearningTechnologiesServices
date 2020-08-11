/**
 * 
 */
package main.com.zc.service.academic_advising_instructor;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iaa_instructorAppService {

	public List<aa_instructor> getAll();
	public aa_instructor getByMail(String mail);
	public aa_instructor addaa_instructor(aa_instructor data);
	public aa_instructor getById(int id);
	public boolean delete(aa_instructor data)throws Exception;
}
