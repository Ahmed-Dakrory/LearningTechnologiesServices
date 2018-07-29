/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.service;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.IWeekFeedbackRepository;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.WeekFeedback;

/**
 * @author omnya
 *
 */
@Repository("IWeekFeedbackRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WeekFeedbackRepositoryImpl implements IWeekFeedbackRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public List<WeekFeedback> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getAll");

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
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
	public List<WeekFeedback> getByCategoryID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByCategoryID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByQuestID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByQuestID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByStudentIDAndCourseID(Integer id, Integer courseID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByStudentIDAndCourseID").setInteger("id", id).setInteger("courseID", courseID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByStudentIDAndCourseIDAndInstructor").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("insID", insID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedback getByStudentIDAndCourseIDAndInstructorAndQuesID(Integer id, Integer courseID, Integer insID,
			Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByStudentIDAndCourseIDAndInstructorAndQuesID").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("insID", insID).setInteger("quesID",quesID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedback getByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID, Integer courseId) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByQuestIDAndStudentIDAndCourseID").setInteger("quesID", quesID).setInteger("stID", stID)
					.setInteger("courseId", courseId);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedback getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("WeekFeedback.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedback add(WeekFeedback form) {
		try{
			WeekFeedback ans=new WeekFeedback();
			form.setSubmissionDate(Calendar.getInstance());
			if(form.getInstructor()!=null){
	         List<WeekFeedback> anslst=getByCourseIDAndInsIDAndStudentIdAndWeek
	        		 (form.getCourse().getId(), form.getInstructor().getId(),form.getStudent().getId(), form.getWeek().getId());
	         if(anslst!=null)
	         {
	        	 if(anslst.size()>0)
	        	 {
	        		 boolean exist=false;
	        		 for(int i=0;i<anslst.size();i++)
	        		 {
	        			 if(anslst.get(i).getQuestion().getId().equals(form.getQuestion().getId()))
	        				 exist=true;
	        			 break;
	        			
	        		 }
	        		 if(exist)
	        		 { ans.setSelections(form.getSelections());
	     				 update(ans); 
	        		 }
	     				else 
	     					 sessionFactory.getCurrentSession().save(form);
	        	 }
	        	 else 
	        		 sessionFactory.getCurrentSession().save(form);
	         }
	         else 
	         { sessionFactory.getCurrentSession().save(form);
				}
			}
			
			
			return form;
		}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedback update(WeekFeedback form) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(form);
			tx1.commit();
			session.close();
			return form;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(WeekFeedback form) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(form.getId()));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public WeekFeedback getByQuestIDAndStudentID(Integer id, Integer stID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("WeekFeedback.getByQuestIDAndStudentID").setInteger("id", id).setInteger("stID",stID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getAnswresByQuestionIDAndCourseIDAndInstructorID(Integer courseID, Integer insID,
			Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getAnswresByQuestionIDAndCourseIDAndInstructorID").setInteger("courseID", courseID)
					.setInteger("insID", insID)
					.setInteger("quesID", quesID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public List<WeekFeedback> getAnswresByQuesIDAndCourseID(Integer courseID, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getAnswresByQuesIDAndCourseID").setInteger("courseID", courseID)
					.setInteger("quesID", quesID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting comments");
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<WeekFeedback> getByCourseID(Integer courseID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByCourseID").setInteger("courseId", courseID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting comments");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByCourseIDAndInsID(Integer courseId, Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByCourseIDAndInsID").setInteger("courseId", courseId).setInteger("insID", insID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting comments");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByQuestionIDAndCourseIDAndInsIDAndStudentId(Integer questionID, Integer courseID,
			Integer insID, Integer studentID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByQuestionIDAndCourseIDAndInsIDAndStudentId")
					.setInteger("questionID", questionID).setInteger("courseID", courseID).setInteger("insID", insID).setInteger("studentID", studentID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting comments");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByCourseIDAndInsIDAndStudentIdAndWeek(
			 Integer courseID, Integer insID,
			Integer studentID, Integer week) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByCourseIDAndInsIDAndStudentIdAndWeek")
					.setInteger("courseID", courseID)
					.setInteger("insID", insID).setInteger("studentID",studentID).setInteger("week",week);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getByCourseIDAndWeek(Integer courseID, Integer week) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getByCourseIDAndWeek")
					.setInteger("courseID", courseID).setInteger("week",week);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getDistinctStudentByCourseIDandWeekAndIns(Integer courseID, Integer week,Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getDistinctStudentByCourseIDandWeekAndIns")
					.setInteger("courseID", courseID).setInteger("week",week).setInteger("insID",insID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<WeekFeedback> getDistinctStudentByCourseIDandWeekAndInsAndQuesAndSelection(Integer courseID,
			Integer week, Integer insID, Integer quesId, Integer selectionID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getDistinctStudentByCourseIDandWeekAndInsAndQuesAndSelection")
					.setInteger("courseID", courseID).setInteger("week",week).setInteger("insID",insID).setInteger("quesId", quesId)
					.setInteger("selectionID", selectionID);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Integer getTotalNumOfParticipatedStudents(Integer week) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WeekFeedback.getTotalNumOfParticipatedStudents")
					.setInteger("week", week);

			@SuppressWarnings("unchecked")
			List<WeekFeedback> results = query.list();
			return results.size();
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
