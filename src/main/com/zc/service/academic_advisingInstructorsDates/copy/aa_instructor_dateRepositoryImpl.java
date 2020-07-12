/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorsDates.copy;

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
public class aa_instructor_dateRepositoryImpl implements aa_instructor_dateRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public aa_instructor_date addaa_instructor_date(aa_instructor_date data) {
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
	public List<aa_instructor_date> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor_date.getAll");

				 @SuppressWarnings("unchecked")
				List<aa_instructor_date> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(aa_instructor_date data)throws Exception {
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
	public aa_instructor_date getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor_date.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<aa_instructor_date> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<aa_instructor_date> getAllByYearAndSemester(int year,  String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor_date.getAllByYearAndSemester")
				 .setString("semester",semester)
				 .setInteger("year", year);

		 @SuppressWarnings("unchecked")
		List<aa_instructor_date> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<aa_instructor_date> getByInstructorIdAndYearAndSemester(int id, int year, String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor_date.getByInstructorIdAndYearAndSemester")
				 .setString("semester",semester)
				 .setInteger("year", year)
				 .setInteger("id", id);

		 @SuppressWarnings("unchecked")
		List<aa_instructor_date> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}


	



	
	


}
