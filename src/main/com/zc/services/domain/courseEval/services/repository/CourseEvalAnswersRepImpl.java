/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.courseEval.model.CourseEvalAnswers;
import main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Repository("ICourseEvalAnswersRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseEvalAnswersRepImpl implements ICourseEvalAnswersRep {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	@Override
	public List<CourseEvalAnswers> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getAll");

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getByCategoryID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByCategoryID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getByQuestID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByQuestID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("CourseEvalAnswers.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseEvalAnswers add(CourseEvalAnswers form) {
		try{
			CourseEvalAnswers ans=new CourseEvalAnswers();
			form.setSubmissionDate(Calendar.getInstance());
			if(form.getInstructor()!=null){
	          ans=getByStudentIDAndCourseIDAndInstructorAndQuesID(form.getStudent().getId(), form.getCourse().getId(), 
					form.getInstructor().getId(), form.getQuestion().getId());
			}
			else 
			{
				 ans=getByQuestIDAndStudentIDAndCourseID(form.getQuestion().getId(),form.getStudent().getId(),form.getCourse().getId());
			}
			if(ans!=null)
			{

				System.out.println("Ahmed Dakrory: update :");
				ans.setSelections(form.getSelections());
				ans.setComment(form.getComment());
				update(ans);
			}
			else {
				System.out.println("Ahmed Dakrory: New :"+String.valueOf(form.getStudent().getId()));
				System.out.println("Ahmed Dakrory: New :"+String.valueOf(form.getCourse().getId()));
				System.out.println("Ahmed Dakrory: New :"+String.valueOf(form.getQuestion().getId()));
				
			sessionFactory.getCurrentSession().save(form);
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
	public CourseEvalAnswers update(CourseEvalAnswers form) {
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
	public boolean delete(CourseEvalAnswers form) {
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
	public CourseEvalAnswers getByQuestIDAndStudentID(Integer id, Integer stID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("CourseEvalAnswers.getByQuestIDAndStudentID").setInteger("id", id).setInteger("stID",stID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseEvalAnswers> getByStudentIDAndCourseID(Integer id,
			Integer courseID ,Integer type) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByStudentIDAndCourseID").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("type",type);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getByStudentIDAndCourseIDAndInstructor(
			Integer id, Integer courseID, Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructor").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("insID", insID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers getByStudentIDAndCourseIDAndInstructorAndQuesID(
			Integer id, Integer courseID, Integer insID, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructorAndQuesID").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("insID", insID).setInteger("quesID",quesID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getListByStudentIDAndCourseIDAndInstructorAndQuesID(
			Integer id, Integer courseID, Integer insID, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructorAndQuesID").setInteger("id", id).setInteger("courseID", courseID)
					.setInteger("insID", insID).setInteger("quesID",quesID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers getByQuestIDAndStudentIDAndCourseID(
			Integer quesID, Integer stID, Integer courseId) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByQuestIDAndStudentIDAndCourseID").setInteger("quesID", quesID).setInteger("stID", stID)
					.setInteger("courseId", courseId);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getAnswresByQuestionIDAndCourseIDAndInstructorID(
			Integer courseID, Integer insID, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getAnswresByQuestionIDAndCourseIDAndInstructorID").setInteger("courseID", courseID)
					.setInteger("insID", insID)
					.setInteger("quesID", quesID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getCommentsByCategoryIDAndCourseIDAndInstructorID(
			Integer courseID, Integer insID, Integer categoryID) {
		
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getCommentsByCategoryIDAndCourseIDAndInstructorID").setInteger("courseID", courseID)
					.setInteger("insID", insID)
					.setInteger("categoryID", categoryID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getAnswresByQuesIDAndCourseID (
			Integer courseID, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getAnswresByQuesIDAndCourseID").setInteger("courseID", courseID)
					.setInteger("quesID", quesID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getCommentsByCategoryIDAndCourseID(
			Integer courseID , Integer categoryID) {
		
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getCommentsByCategoryIDAndCourseID").setInteger("courseID", courseID)
						.setInteger("categoryID", categoryID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<Student> getTotalStudentsOfCourse(Integer courseID,Integer surveyID) {
		

		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getDistintStudents").setInteger("courseID", courseID).setInteger("surveyID", surveyID);

			@SuppressWarnings("unchecked")
			List<Student> results = query.list();
			
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting distinct students of course evaluation");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseEvalAnswers> getByCourseID(Integer courseId) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByCourseID").setInteger("courseId", courseId);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public List<CourseEvalAnswers> getByCourseIDAndInsID(Integer courseId,
			Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByCourseIDAndInsID").setInteger("courseId", courseId).setInteger("insID", insID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting comments");
				ex.printStackTrace();
				return null;
			}
	}

	/*
	 * (Ahmed Dakrory)
	 * @see main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep#deleteAllcourseData(main.com.zc.services.domain.courseEval.model.CourseEvalAnswers)
	 */
	@Override
	public List<CourseEvalAnswers> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByQuestionIDAndCourseIDAndInsIDAndStudentId")
					.setInteger("questionID", questionID).setInteger("courseID", courseID).setInteger("insID", insID).setInteger("studentID", studentID);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers addInst(CourseEvalAnswers form) {
		try {			
				
			sessionFactory.getCurrentSession().save(form);
			
			return form;		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<CourseEvalAnswers> getAllByQuestIDAndStudentIDAndCourseID(
			Integer quesID, Integer stID, Integer courseId) {
		
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalAnswers.getByQuestIDAndStudentIDAndCourseID").setInteger("quesID", quesID).setInteger("stID", stID)
					.setInteger("courseId", courseId);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers getByStudentIDAndCourseIDAndInstructorAndQuesIDAndAnsID(
			Integer student, int course, Integer ins, Integer ques, Integer ans) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery
					("CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructorAndQuesIDAndAnsID").
					setInteger("student", student).setInteger("course", course)
					.setInteger("ins", ins).setInteger("ques", ques).setInteger("ans", ans);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public CourseEvalAnswers getByQuestionIDAndCourseIDAndStudentIdAndAns(
			Integer ques, int course, Integer student, Integer ans) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery
					("CourseEvalAnswers.getByQuestionIDAndCourseIDAndStudentIdAndAns").
					setInteger("student", student).setInteger("course", course)
					.setInteger("ques", ques).setInteger("ans", ans);

			@SuppressWarnings("unchecked")
			List<CourseEvalAnswers> results = query.list();
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
	public boolean removeDuplicatesFromCourses() {
		try{
			String sql="DELETE x FROM `attendance-sys`.course_eval_answers x JOIN `attendance-sys`.course_eval_answers y ON y.course_id= x.course_id AND y.ques_id = x.ques_id	AND y.student_id = x.student_id	AND y.selection = x.selection AND y.instructor_id is NULL AND y.id < x.id";

			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.executeUpdate();
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("error in deleting");
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeDuplicatesFromIns() {
		try{
			String sql = "DELETE x FROM `attendance-sys`.course_eval_answers x JOIN `attendance-sys`.course_eval_answers y "
						+"ON y.course_id= x.course_id AND y.ques_id = x.ques_id AND y.student_id = x.student_id AND y.selection = x.selection"
						+"AND y.instructor_id = x.instructor_id"
						+"AND y.id < x.id";
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.executeUpdate();
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("error in deleting");
			ex.printStackTrace();
			return false;
		}
	}
/*
 * (Ahmed Dakrory)
 * Delete All Last Submitted Data to save it again this due to the structure of the database
 * @see main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep#deleteAllcourseData(main.com.zc.services.domain.courseEval.model.CourseEvalAnswers)
 */
	
	
	@Override
	public boolean deleteAllcourseDataNew(CourseEvalAnswersDTO form) {
		try {
			
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			List<CourseEvalAnswers>list=new ArrayList<CourseEvalAnswers>();
			if(form.getInstructor()!=null){
				list=getListByStudentIDAndCourseIDAndInstructorAndQuesID(form.getStudent().getId(), form.getCourse().getId(), form.getInstructor().getId(), form.getQuestion().getId());
			}else{
				list=getAllByQuestIDAndStudentIDAndCourseID(form.getQuestion().getId(), form.getStudent().getId(), form.getCourse().getId());
			}
			System.out.println("Ahmed Dakrory: Size of List = "+String.valueOf(list.size()));
			for(int i=0;i<list.size();i++){
				session.delete(list.get(i));

				
			}
			tx1.commit();
			session.close();
			
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
}
