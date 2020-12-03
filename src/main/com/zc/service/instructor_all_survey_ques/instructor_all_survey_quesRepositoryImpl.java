/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques;

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
public class instructor_all_survey_quesRepositoryImpl implements instructor_all_survey_quesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public instructor_all_survey_ques addinstructor_all_survey_ques(instructor_all_survey_ques data) {
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
	public List<instructor_all_survey_ques> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getAll");

				 @SuppressWarnings("unchecked")
				List<instructor_all_survey_ques> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(instructor_all_survey_ques data)throws Exception {
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
	public instructor_all_survey_ques getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestar(int year, int semestar) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getAllByYearAndSemestar")
				 .setInteger("year",year)
				 .setInteger("semester", semestar);

		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(int year, int semestar, int category,int mode) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getAllByYearAndSemestarAndCategoryAndMidtermOrFinal")
				 .setInteger("year",year)
				 .setInteger("semester", semestar)
				 .setInteger("category", category)
				 .setInteger("mode", mode);
		 
		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	
	@Override
	public List<instructor_all_survey_ques> getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(int year, int semestar, int mode) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal")
				 .setInteger("year",year)
				 .setInteger("semester", semestar)
				 .setInteger("mode", mode);
		 
		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndMidtermOrFinalAndType(int year,
			int semestar, int mode, int type) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques.getAllByYearAndSemestarAndMidtermOrFinalAndType")
				 .setInteger("year",year)
				 .setInteger("semester", semestar)
				 .setInteger("mode", mode)
				 .setInteger("type",type);
		 
		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	



	
	


}
