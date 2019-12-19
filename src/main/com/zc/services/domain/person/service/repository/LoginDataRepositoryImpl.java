/**
 * 
 */
package main.com.zc.services.domain.person.service.repository;

import java.util.List;



import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.LoginData;

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
@Repository("ILoginDataRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginDataRepositoryImpl implements ILoginDataRepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public List<LoginData> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("LoginData.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<LoginData> results=query.list();
		   return results;
	}

	@Override
	public int add(LoginData loginStaff) {
		sessionFactory.getCurrentSession().save(loginStaff); 
		 return loginStaff.getId();
	}

	@Override
	public boolean remove(int id) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(id));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public LoginData update(LoginData loginStaff) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(loginStaff);
		tx1.commit();
		session.close();
		return loginStaff;
	}

	@Override
	public LoginData getById(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("LoginData.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<LoginData> results = query.list();
		return results.get(0);
	}

	@Override
	public LoginData getByMail(String mail) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("LoginData.getByMail").setString("mail", mail.toLowerCase());

		@SuppressWarnings("unchecked")
		List<LoginData> results = query.list();
		if(results.size()<1)
			return null;
		return results.get(0);
	}

}
