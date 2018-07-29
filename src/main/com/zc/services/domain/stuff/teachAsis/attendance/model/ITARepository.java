/**
 * 
 */
package main.com.zc.services.domain.stuff.teachAsis.attendance.model;

import java.util.List;

import main.com.zc.services.domain.stuff.teachAsis.attendance.model.TA;

/**
 * @author omnya
 *
 */
public interface ITARepository {
	public TA add(TA ta);
	public TA getByID(int id);
	public List<TA> getAll();
	public TA getByFacultyID(String facultyID);
	

}
