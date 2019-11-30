/**
 * 
 */
package main.com.zc.service.clo_survey_ans;

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
public class clo_survey_ansRepositoryImpl implements clo_survey_ansRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public clo_survey_ans addclo_survey_ans(clo_survey_ans data) {
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
	public List<clo_survey_ans> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("clo_survey_ans.getAll");

				 @SuppressWarnings("unchecked")
				List<clo_survey_ans> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(clo_survey_ans data)throws Exception {
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
	public clo_survey_ans getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("clo_survey_ans.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<clo_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	

	@Override
	public List<clo_survey_ans> getAllByCourseId(int courseId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("clo_survey_ans.getAllByCourseId")
				 .setInteger("courseId",courseId);

		 @SuppressWarnings("unchecked")
		List<clo_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}
	
	
	@Override
	public clo_survey_ans getByStudentIdAndCourseId(int idStudent, int courseId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("clo_survey_ans.getByStudentIdAndCourseId")
				 .setInteger("idStudent",idStudent)
				 .setInteger("courseId", courseId);

		 @SuppressWarnings("unchecked")
		List<clo_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	



	
	


}
