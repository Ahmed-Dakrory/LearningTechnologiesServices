/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.List;
import main.com.zc.services.domain.courseEval.model.IScaleSelectionsRep;
import main.com.zc.services.domain.courseEval.model.ScaleSelections;
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
@Repository("IScaleSelectionsRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ScaleSelectionsRepImpl implements IScaleSelectionsRep {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<ScaleSelections> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ScaleSelections.getAll");

			@SuppressWarnings("unchecked")
			List<ScaleSelections> results = query.list();
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
	public ScaleSelections getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ScaleSelections.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ScaleSelections> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public ScaleSelections add(ScaleSelections form) {
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
	public ScaleSelections update(ScaleSelections form) {
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
	public Boolean delete(ScaleSelections form) {
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
	public List<ScaleSelections> getByScaleTypeId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ScaleSelections.getByScaleTypeId").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ScaleSelections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ScaleSelections> getByScaleTypeIDAndSelType(Integer id, Integer type) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ScaleSelections.getByScaleTypeIDAndSelType").setInteger("id", id).setInteger("type", type);

			@SuppressWarnings("unchecked")
			List<ScaleSelections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
