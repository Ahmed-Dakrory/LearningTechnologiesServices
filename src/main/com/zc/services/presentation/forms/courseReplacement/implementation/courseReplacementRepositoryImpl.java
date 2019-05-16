/**
 * 
 */
package main.com.zc.services.presentation.forms.courseReplacement.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.presentation.forms.courseReplacement.courseReplacement;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacementRepository;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class courseReplacementRepositoryImpl implements courseReplacementRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public courseReplacement addcourseReplacement(courseReplacement so) {
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
	public List<courseReplacement> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getAll");

				 @SuppressWarnings("unchecked")
				List<courseReplacement> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(courseReplacement note) {
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
	public List<courseReplacement> getByStudentId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getByStudentId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<courseReplacement> results=query.list();
		   return results;
	}
	
	@Override
	public List<courseReplacement> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getAllForStepAndMajorId").setInteger("id",id).setInteger("step", step);

		 @SuppressWarnings("unchecked")
		List<courseReplacement> results=query.list();
		   return results;
	}
	
	@Override
	public List<courseReplacement> getByMajorId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getByMajorId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<courseReplacement> results=query.list();
		   return results;
	}
	
	@Override
	public courseReplacement getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<courseReplacement> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<courseReplacement> getAllForStepAndType(int type, int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getAllForStepAndType").setInteger("type",type).setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<courseReplacement> results=query.list();
				   return results;
	}

	@Override
	public List<courseReplacement> getAllForStep(int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("courseReplacement.getAllForStep").setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<courseReplacement> results=query.list();
				   return results;
	}

}
