/**
 * 
 */
package main.com.zc.services.presentation.forms.gap_form.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.presentation.forms.gap_form.gap_form;
import main.com.zc.services.presentation.forms.gap_form.gap_formRepository;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class gap_formRepositoryImpl implements gap_formRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public gap_form addgap_form(gap_form so) {
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
	public List<gap_form> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getAll");

				 @SuppressWarnings("unchecked")
				List<gap_form> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(gap_form note) {
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
	public List<gap_form> getByStudentId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getByStudentId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<gap_form> results=query.list();
		   return results;
	}
	
	@Override
	public List<gap_form> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getAllForStepAndMajorId").setInteger("id",id).setInteger("step", step);

		 @SuppressWarnings("unchecked")
		List<gap_form> results=query.list();
		   return results;
	}
	
	@Override
	public List<gap_form> getByMajorId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getByMajorId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<gap_form> results=query.list();
		   return results;
	}
	
	@Override
	public gap_form getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<gap_form> results=query.list();
		   return results.get(0);
	}


	@Override
	public List<gap_form> getAllForStep(int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getAllForStep").setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<gap_form> results=query.list();
				   return results;
	}

	@Override
	public List<gap_form> getAllRefused() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("gap_form.getAllRefused");

		 @SuppressWarnings("unchecked")
		List<gap_form> results=query.list();
		   return results;
	}

}
