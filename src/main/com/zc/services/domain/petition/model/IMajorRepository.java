/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IMajorRepository {

	public Majors add(Majors major);

	public boolean remove(int id);

	public Majors update(Majors major);

	public List<Majors> getAll();
	public List<Majors> getAllOLdNew();

	public Majors getById(int id);
	
	public List<Majors> getByInsID(int id);
	
}
