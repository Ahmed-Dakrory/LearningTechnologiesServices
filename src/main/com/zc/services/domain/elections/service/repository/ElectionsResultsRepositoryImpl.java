/**
 * 
 */
package main.com.zc.services.domain.elections.service.repository;

import java.util.List;

import main.com.zc.services.domain.elections.model.ElectionCandidate;
import main.com.zc.services.domain.elections.model.ElectionResults;
import main.com.zc.services.domain.elections.model.IElectionsResultsRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
@Transactional
public class ElectionsResultsRepositoryImpl implements IElectionsResultsRepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	@Override
	 public ElectionResults addResult(ElectionResults result)  {
		try{
		
		 return (ElectionResults) sessionFactory.getCurrentSession().save(result); 
		}
		catch(Exception ex)
		{
			System.out.println(">>>>>>>>>>");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ElectionResults findByID(int studentID) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByID").setInteger("id",studentID);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results.get(0);
		
	}

	@Override
	public List<ElectionResults> getAll() {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByPresidentID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByPresidentID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByViceID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByViceID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	/*@Override
	public List<ElectionResults> getByActvID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByAvtvID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByServiceID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByServiceID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByAcadID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByAcadID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}*/

	@Override
	public List<ElectionResults> getByActvPresidentID(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByActvPresidentID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByServicePresidentID(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByServicePresidentID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionResults> getByAcadPresidentID(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionResults.getByAcadPresidentID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionResults> results=query.list();
		   return results;
	}
	
	
}
