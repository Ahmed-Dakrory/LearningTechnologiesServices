/**
 * 
 */
package main.com.zc.service.courseClo;

import java.util.List;

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
public class course_cloRepositoryImpl implements course_cloRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public course_clo addcourse_clo(course_clo data) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(data);
			tx1.commit();
			session.close(); 
			return data; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<course_clo> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("course_clo.getAll");

				 @SuppressWarnings("unchecked")
				List<course_clo> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(course_clo data)throws Exception {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public course_clo getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("course_clo.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<course_clo> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<course_clo> getAllByYearAndSemestar(String year, String semestar) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("course_clo.getAllByYearAndSemestar")
				 .setString("year",year)
				 .setString("semestar", semestar);

		 @SuppressWarnings("unchecked")
		List<course_clo> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public course_clo getAllByYearAndSemestarAndCourseCode(String year, String semestar, String courseCode) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("course_clo.getAllByYearAndSemestarAndCourseCode")
				 .setString("year",year)
				 .setString("semestar", semestar)
				 .setString("course_code", courseCode);
		 
		 @SuppressWarnings("unchecked")
		List<course_clo> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	



	
	


}
