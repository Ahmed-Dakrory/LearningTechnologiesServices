/**
 * 
 */
package main.com.zc.services.domain.elections.service.repository;

import java.util.List;

import main.com.zc.services.domain.elections.model.ElectionCodes;
import main.com.zc.services.domain.elections.model.ElectionResults;
import main.com.zc.services.domain.elections.model.IElectionCodesRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Repository
@Transactional
public class ElectionCodesRepositoryImpl implements IElectionCodesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	@Override
	public List<ElectionCodes> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCodes.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCodes> results=query.list();
		   return results;
	}

	
	@Override
	public ElectionCodes getByFileNo(String id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCodes.getByFileNo").setString("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCodes> results=query.list();
		   return results.get(0);
	}

	@Override
	public ElectionCodes getById(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCodes.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCodes> results=query.list();
		   return results.get(0);
	}


	@Override
	public ElectionCodes addCode(ElectionCodes code) {
		try{
			
			 return (ElectionCodes) sessionFactory.getCurrentSession().save(code); 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public ElectionCodes getByElectionCode(String id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("ElectionCodes.getByElectionCode").setString("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<ElectionCodes> results=query.list();
		  if(results.size()==0)
			  return null;
		  else
		   return results.get(0);
		 
	}


	@Override
	public ElectionCodes update(ElectionCodes code) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(code);
		tx1.commit();
		session.close();
		 return code;
	}

}
