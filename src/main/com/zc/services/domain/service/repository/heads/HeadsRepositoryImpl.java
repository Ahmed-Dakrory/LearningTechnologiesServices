/**
 * 
 */
package main.com.zc.services.domain.service.repository.heads;

import java.util.List;

import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.model.heads.HeadsRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class HeadsRepositoryImpl implements HeadsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<Heads> getByEmployeId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Heads.getByEmployeeID").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Heads> results=query.list();
		   return results;
	}

	@Override
	public Heads addHead(Heads so) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(so);
			tx1.commit();
			session.close(); 
			return so; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Heads> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Heads.getAll");

				 @SuppressWarnings("unchecked")
				List<Heads> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(Heads note) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(note);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Heads getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Heads.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Heads> results=query.list();
		 if(results!=null && results.size()>0) {
			   return results.get(0);
			 }else {
				 return null;
			 }
	}

	@Override
	public List<Heads> getByAllNotHidden() {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Heads.getByAllHidden");

		 @SuppressWarnings("unchecked")
		List<Heads> results=query.list();
		   return results;
	}

	@Override
	public Heads getByType(int type) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Heads.getByType").setInteger("type",type);

				 @SuppressWarnings("unchecked")
				List<Heads> results=query.list();
				 if(results!=null && results.size()>0) {
				   return results.get(0);
				 }else {
					 return null;
				 }
	}

}
