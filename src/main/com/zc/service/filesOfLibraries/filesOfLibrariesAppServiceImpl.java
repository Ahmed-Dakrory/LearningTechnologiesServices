/**
 * 
 */
package main.com.zc.service.filesOfLibraries;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("filesOfLibrariesFacadeImpl")
public class filesOfLibrariesAppServiceImpl implements IfilesOfLibrariesAppService{

	@Autowired
	filesOfLibrariesRepository filesOfLibrariesDataRepository;
	
	
	@Override
	public List<filesOfLibraries> getAll() {
		try{
			List<filesOfLibraries> course=filesOfLibrariesDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public filesOfLibraries addfilesOfLibraries(filesOfLibraries data) {
		try{
			filesOfLibrariesDataRepository.addfilesOfLibraries(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(filesOfLibraries data)throws Exception {
		// TODO Auto-generated method stub
		try{
			filesOfLibrariesDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public filesOfLibraries getById(int id) {
		// TODO Auto-generated method stub
		try{
			filesOfLibraries so=filesOfLibrariesDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}








	@Override
	public List<filesOfLibraries> getByYearAndSemester(String year, String semester) {
		try{
			List<filesOfLibraries> course=filesOfLibrariesDataRepository.getByYearAndSemester(year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


