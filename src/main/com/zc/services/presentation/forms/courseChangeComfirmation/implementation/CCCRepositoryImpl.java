/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation.implementation;

import java.util.List;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.CCC;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.CCCRepository;

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
public class CCCRepositoryImpl implements CCCRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public CCC addCCC(CCC so) {
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
	public List<CCC> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getAll");

				 @SuppressWarnings("unchecked")
				List<CCC> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(CCC note) {
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
	public List<CCC> getByStudentId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getByStudentId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CCC> results=query.list();
		   return results;
	}
	
	@Override
	public List<CCC> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getAllForStepAndMajorId").setInteger("id",id).setInteger("step", step);

		 @SuppressWarnings("unchecked")
		List<CCC> results=query.list();
		   return results;
	}
	
	@Override
	public List<CCC> getByMajorId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getByMajorId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CCC> results=query.list();
		   return results;
	}
	
	@Override
	public CCC getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CCC> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<CCC> getAllForStepAndType(int type, int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getAllForStepAndType").setInteger("type",type).setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<CCC> results=query.list();
				   return results;
	}

	@Override
	public List<CCC> getAllForStep(int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CCC.getAllForStep").setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<CCC> results=query.list();
				   return results;
	}

}
