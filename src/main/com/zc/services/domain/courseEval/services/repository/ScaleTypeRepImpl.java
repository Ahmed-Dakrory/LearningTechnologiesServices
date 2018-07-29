/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.List;
import main.com.zc.services.domain.courseEval.model.IScaleTypeRep;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

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
@Repository("IScaleTypeRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ScaleTypeRepImpl implements IScaleTypeRep{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<ScaleType> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ScaleType.getAll");

			@SuppressWarnings("unchecked")
			List<ScaleType> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public ScaleType getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ScaleType.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ScaleType> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public ScaleType add(ScaleType form) {
		try{
			sessionFactory.getCurrentSession().saveOrUpdate(form);
			return form;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public ScaleType update(ScaleType form) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.update(form);
		     tx.commit();
		     return form;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		 return null;
		 }
		 finally {
		     sess.close();
		     
		 }
	}

	@Override
	public Boolean delete(ScaleType form) {
		Session session = sessionFactory.openSession();
		Transaction tx1 =null;
		try {
			
			 tx1 = session.beginTransaction();
			session.delete(form);
			tx1.commit();
			
			return true;
		} catch (Exception ex) {
			 if (tx1!=null) tx1.rollback();
			    ex.printStackTrace();
			 return null;
		}
		 finally {
			 session.close();
		     
		 }
	}

	@Override
	public ScaleType getByName(String name) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ScaleType.getScaleByName").setString("name", name);

			@SuppressWarnings("unchecked")
			List<ScaleType> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
