/**
 * 
 */
package main.com.zc.services.presentation.forms.change_grade_petition.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.presentation.forms.change_grade_petition.change_grade_petition;
import main.com.zc.services.presentation.forms.change_grade_petition.change_grade_petitionRepository;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class change_grade_petitionRepositoryImpl implements change_grade_petitionRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public change_grade_petition addchange_grade_petition(change_grade_petition so) {
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
	public List<change_grade_petition> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getAll");

				 @SuppressWarnings("unchecked")
				List<change_grade_petition> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(change_grade_petition note) {
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
	public List<change_grade_petition> getByStudentId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getByStudentId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<change_grade_petition> results=query.list();
		   return results;
	}
	
	@Override
	public List<change_grade_petition> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getAllForStepAndMajorId").setInteger("id",id).setInteger("step", step);

		 @SuppressWarnings("unchecked")
		List<change_grade_petition> results=query.list();
		   return results;
	}
	
	@Override
	public List<change_grade_petition> getByMajorId(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getByMajorId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<change_grade_petition> results=query.list();
		   return results;
	}
	
	@Override
	public change_grade_petition getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<change_grade_petition> results=query.list();
		   return results.get(0);
	}


	@Override
	public List<change_grade_petition> getAllForStep(int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getAllForStep").setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<change_grade_petition> results=query.list();
				   return results;
	}

	@Override
	public List<change_grade_petition> getAllRefused() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getAllRefused");

		 @SuppressWarnings("unchecked")
		List<change_grade_petition> results=query.list();
		   return results;
	}

	@Override
	public List<change_grade_petition> getAllForStepAndInstructorId(int id, int step) {
		// TODO Auto-generated method stub
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("change_grade_petition.getAllForStepAndInstructorId").setInteger("id",id).setInteger("step", step);

				 @SuppressWarnings("unchecked")
				List<change_grade_petition> results=query.list();
				   return results;
	}

}
