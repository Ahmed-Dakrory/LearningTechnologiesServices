/**
 * 
 */
package main.com.zc.service.instructor_survey_ans;

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
public class instructor_survey_ansRepositoryImpl implements instructor_survey_ansRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public instructor_survey_ans addinstructor_survey_ans(instructor_survey_ans data) {
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
	public List<instructor_survey_ans> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getAll");

				 @SuppressWarnings("unchecked")
				List<instructor_survey_ans> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(instructor_survey_ans data)throws Exception {
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
	public instructor_survey_ans getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}




	@Override
	public List<instructor_survey_ans> getAllByCourse(int courseId) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getAllByCourse")
				 .setInteger("courseId",courseId);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<instructor_survey_ans> getAllByCourseAndInstructor(int courseId, int instructorId) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getAllByCourseAndInstructor")
				 .setInteger("courseId",courseId)
				 .setInteger("instructorId", instructorId);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<instructor_survey_ans> getAllByCourseAndInstructorAndStudent(int courseId, int instructorId, int studentId) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getAllByCourseAndInstructorAndStudent")
				 .setInteger("courseId",courseId)
				 .setInteger("instructorId", instructorId)
				 .setInteger("studentId", studentId);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public instructor_survey_ans getAllByCourseAndInstructorAndStudentAndQuestion(int courseId, int instructorId,
			int studentId, int questionId) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_survey_ans.getAllByCourseAndInstructorAndStudentAndQuestion")
				 .setInteger("courseId",courseId)
				 .setInteger("instructorId", instructorId)
				 .setInteger("studentId", studentId)
		 .setInteger("questionId", questionId);

		 @SuppressWarnings("unchecked")
		List<instructor_survey_ans> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	



	
	


}
