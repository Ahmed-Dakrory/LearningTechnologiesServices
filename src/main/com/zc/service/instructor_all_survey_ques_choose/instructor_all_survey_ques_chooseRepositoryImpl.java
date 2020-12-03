/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques_choose;

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
public class instructor_all_survey_ques_chooseRepositoryImpl implements instructor_all_survey_ques_chooseRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public instructor_all_survey_ques_choose addinstructor_all_survey_ques_choose(instructor_all_survey_ques_choose data) {
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
	public List<instructor_all_survey_ques_choose> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques_choose.getAll");

				 @SuppressWarnings("unchecked")
				List<instructor_all_survey_ques_choose> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(instructor_all_survey_ques_choose data)throws Exception {
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
	public instructor_all_survey_ques_choose getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques_choose.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques_choose> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<instructor_all_survey_ques_choose> getAllByQuesId(int quesId) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_all_survey_ques_choose.getAllByQuesId")
				 .setInteger("quesId",quesId);

		 @SuppressWarnings("unchecked")
		List<instructor_all_survey_ques_choose> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	



	
	


}
