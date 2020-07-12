/**
 * 
 */
package main.com.zc.service.academic_advising_student_profile;

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
public class aa_student_profileRepositoryImpl implements aa_student_profileRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public aa_student_profile addaa_student_profile(aa_student_profile data) {
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
	public List<aa_student_profile> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_student_profile.getAll");

				 @SuppressWarnings("unchecked")
				List<aa_student_profile> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(aa_student_profile data)throws Exception {
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
	public aa_student_profile getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_student_profile.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<aa_student_profile> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}




	

	

	

	
	

	@Override
	public List<aa_student_profile> getAllByYearAndSemester(int year,  String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_student_profile.getAllByYearAndSemester")
				 .setString("semester",semester)
				 .setInteger("year", year);

		 @SuppressWarnings("unchecked")
		List<aa_student_profile> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public aa_student_profile getByMailAndYearAndSemester(String mail, int year, String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_student_profile.getByMailAndYearAndSemester")
				 .setString("semester",semester)
				 .setInteger("year", year)
				 .setString("mail", mail);

		 @SuppressWarnings("unchecked")
		List<aa_student_profile> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}


	



	
	


}
