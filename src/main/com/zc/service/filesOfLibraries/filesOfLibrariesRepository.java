/**
 * 
 */
package main.com.zc.service.filesOfLibraries;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface filesOfLibrariesRepository {

	public List<filesOfLibraries> getAll();
	public List<filesOfLibraries> getByYearAndSemester(String year,String semester);
	public filesOfLibraries addfilesOfLibraries(filesOfLibraries data);
	public filesOfLibraries getById(int id);
	public boolean delete(filesOfLibraries data)throws Exception;
}
