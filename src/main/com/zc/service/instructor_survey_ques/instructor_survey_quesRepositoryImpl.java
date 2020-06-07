/**
 * 
 */
package main.com.zc.service.instructor_survey_ques;

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
public class instructor_survey_quesRepositoryImpl implements instructor_survey_quesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public instructor_survey_ques addinstructor_survey_ques(instructor_survey_ques data) {
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
	public List<instructor_survey_ques> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ques.getAll");

				 @SuppressWarnings("unchecked")
				List<instructor_survey_ques> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(instructor_survey_ques data)throws Exception {
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
	public instructor_survey_ques getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ques.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<instructor_survey_ques> getAllByYearAndSemestar(int year, int semestar) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ques.getAllByYearAndSemestar")
				 .setInteger("year",year)
				 .setInteger("semester", semestar);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<instructor_survey_ques> getAllByYearAndSemestarAndCategory(int year, int semestar, int category) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ques.getAllByYearAndSemestarAndCategory")
				 .setInteger("year",year)
				 .setInteger("semester", semestar)
				 .setInteger("category", category);
		 
		 @SuppressWarnings("unchecked")
		List<instructor_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	



	
	


}
