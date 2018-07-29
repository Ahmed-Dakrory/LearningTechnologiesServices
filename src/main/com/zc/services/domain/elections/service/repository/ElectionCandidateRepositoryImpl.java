/**
 * 
 */
package main.com.zc.services.domain.elections.service.repository;

import java.util.List;

import main.com.zc.services.domain.elections.model.ElectionCandidate;
import main.com.zc.services.domain.elections.model.IElectionCandidateRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Repository
@Transactional
public class ElectionCandidateRepositoryImpl implements IElectionCandidateRepository {

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	
	@Override
	public List<ElectionCandidate> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCandidate.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCandidate> results=query.list();
		   return results;
	}

	@Override
	public List<ElectionCandidate> getAllByType(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCandidate.getCandidateByType").setInteger("id", id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCandidate> results=query.list();
		   return results;
	}

	@Override
	public ElectionCandidate getByID(int id) {
		try{
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCandidate.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCandidate> results=query.list();
		   return results.get(0);
		}
		catch(Exception ex)
		{
			return null;
		}
	}

}
