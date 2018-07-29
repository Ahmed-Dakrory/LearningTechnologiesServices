/**
 * 
 */
package main.com.zc.services.domain.person.service.repository;

import java.util.List;

import main.com.zc.services.domain.person.model.ILoginRepository;
import main.com.zc.services.domain.person.model.Login;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Omnya Alaa 
 *
 */
@Repository("ILoginRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginRepositoryImpl implements ILoginRepository {

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session;

	
	@Override
	public int add(Login login) {
		sessionFactory.getCurrentSession().save(login); 
		 return login.getId();
	}

	@Override
	public boolean remove(int id) {
		try
		{
			sessionFactory.getCurrentSession().delete(id); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return true;
	}

	@Override
	public Login update(Login login) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(login);
		tx1.commit();
		session.close();
		 return login;
	}

	@Override
	public List<Login> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Login.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<Login> results=query.list();
		   return results;
	}

	@Override
	public Login getLoginByPersonId( int personId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Login.getLoginByPersonId").setInteger("personId",personId);

	        
		   @SuppressWarnings("unchecked")
		List<Login> results=query.list();
		   return results.get(0);
	}

	@Override
	public Login getLoginByFileNo(int fileNo) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Login.getLoginByFileNo").setInteger("fileNo",fileNo);

	        
		   @SuppressWarnings("unchecked")
		List<Login> results=query.list();
		   return results.get(0);
	}
	

	@Override
	public Login getById(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Login.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<Login> results=query.list();
		   return results.get(0);
	}

}
