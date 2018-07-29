/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.courseEval.model.CourseEvalAnswers;
import main.com.zc.services.domain.courseEval.model.IInstructorsEvalAnswersRep;
import main.com.zc.services.domain.courseEval.model.InstructorsEvalAnswers;
import org.hibernate.Query;
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
@Repository("IInstructorsEvalAnswersRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InstructorsEvalAnswersRepImpl implements IInstructorsEvalAnswersRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public List<InstructorsEvalAnswers> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("InstructorsEvalAnswers.getAll");

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
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
	public List<InstructorsEvalAnswers> getByCategoryID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByCategoryID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<InstructorsEvalAnswers> getByQuestID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByQuestID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<InstructorsEvalAnswers> getByInsFromID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByInsFromID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<InstructorsEvalAnswers> getByInsTOID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByInsTOID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<InstructorsEvalAnswers> getByFromInsAndTOIns(Integer fromINS, Integer to) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByFromInsAndTOIns").setInteger("fromINS", fromINS).setInteger("to", to);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public InstructorsEvalAnswers getByFromInsAndTOInsAndQuesID(Integer from,
			Integer to, Integer quesID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByFromInsAndTOInsAndQuesID").setInteger("fromINS", from).setInteger("to", to).setInteger("quesID", quesID);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<InstructorsEvalAnswers> getByQuestIDAndInsFromID(Integer ques,
			Integer from) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByQuestIDAndInsFromID").setInteger("ques", ques).setInteger("fromINS", from);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}	}

	@Override
	public List<InstructorsEvalAnswers> getByQuestIDAndInsTOID(Integer ques,
			Integer to) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getByQuestIDAndInsTOID").setInteger("ques", ques).setInteger("to", to);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}	
	}

	@Override
	public InstructorsEvalAnswers getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("InstructorsEvalAnswers.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<InstructorsEvalAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}	
	}

	@Override
	public InstructorsEvalAnswers add(InstructorsEvalAnswers form) {
		/*Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.save(form);
		     tx.commit();
		     return form;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		 return null;
		 }
		 finally {
		     sess.close();
		     
		 }*/
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
			try{
				
				InstructorsEvalAnswers ans=new InstructorsEvalAnswers();
				form.setSubmissionDate(Calendar.getInstance());
				if(form.getTo()!=null&&form.getTo()!=null){
		          ans=getByFromInsAndTOInsAndQuesID(form.getFrom().getId(), form.getTo().getId(),form.getQuestion().getId());
				}
			
				if(ans!=null)
				{
					ans.setSelection(form.getSelection());
					ans.setComment(form.getComment());
					//update(ans);
					sess.save(form);
				}
				else 
				{
					   tx = sess.beginTransaction();
					     sess.save(form);
					     tx.commit();
				}
				return form;
			
			}
			catch (Exception e) {
			     if (tx!=null) tx.rollback();
			    e.printStackTrace();
			 return null;
			 }
			 finally {
			     sess.close();
			     
			 }
	}

	@Override
	public InstructorsEvalAnswers update(InstructorsEvalAnswers form) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.save(form);
		     tx.commit();
		     return form;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		 return null;
		 }
		 finally {
		     sess.close();
		     
		 }
	}

	@Override
	public boolean delete(InstructorsEvalAnswers form) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(form);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		 finally {
			 session.close();
		     
		 }
	}

}
