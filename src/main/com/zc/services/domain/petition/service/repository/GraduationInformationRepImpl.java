/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.domain.petition.model.GraduationInformation;
import main.com.zc.services.domain.petition.model.IGraduationInformationRep;

/**
 * @author omnya
 *
 */
@Repository("IGraduationInformationRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GraduationInformationRepImpl implements IGraduationInformationRep{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public GraduationInformation add(GraduationInformation form) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sessionFactory.getCurrentSession().save(form);
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
	public boolean remove(GraduationInformation form) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		    sess.delete(form);
		     tx.commit();
		     return true;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		    return false;
		 }
		 finally {
		     sess.close();
		     
		 }
	}

	@Override
	public GraduationInformation update(GraduationInformation feedback) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.update(feedback);
		     tx.commit();
		     return feedback;
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
	public List<GraduationInformation> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("GraduationInformation.getAll");

			@SuppressWarnings("unchecked")
			List<GraduationInformation> results = query.list();
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
	public List<GraduationInformation> getFormByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("GraduationInformation.getFormByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<GraduationInformation> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public GraduationInformation getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("GraduationInformation.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<GraduationInformation> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public GraduationInformation getFormByStudentIDAndSemesterAndYear(Integer studentID, Integer year,
			Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("GraduationInformation.getFormByStudentIDAndSemesterAndYear").setInteger("id", studentID).setInteger("year", year)
					.setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<GraduationInformation> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<GraduationInformation> getFormBySemesterAndYear(Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("GraduationInformation.getFormBySemesterAndYear").setInteger("year", year)
					.setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<GraduationInformation> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
