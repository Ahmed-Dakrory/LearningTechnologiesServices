/**
 * 
 */
package main.com.zc.services.domain.service.repository.heads;





import java.util.List;
import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.model.heads.HeadsRepository;
import main.com.zc.services.domain.model.heads.IHeadsAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("headsFacadeImpl")
public class HeadsAppServiceImpl implements IHeadsAppService{

	@Autowired
	HeadsRepository headsRepository;
	
	
	@Override
	public List<Heads> getAll() {
		try{
			List<Heads> so=headsRepository.getAll();
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Heads> getByEmployeeId(int id) {
		// TODO Auto-generated method stub
				try{
					List<Heads> so=headsRepository.getByEmployeId(id);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public Heads addHead(Heads so) {
		try{
			Heads so2=headsRepository.addHead(so);
			return so2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(Heads so) {
		// TODO Auto-generated method stub
		try{
			headsRepository.delete(so);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public Heads getById(int id) {
		// TODO Auto-generated method stub
				try{
					Heads so=headsRepository.getById(id);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public List<Heads> getByAllNotHidden() {
		try{
			List<Heads> so=headsRepository.getByAllNotHidden();
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Heads getByType(int type) {
		// TODO Auto-generated method stub
				try{
					Heads so=headsRepository.getByType(type);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}


	
}
		
		

	
		
	


