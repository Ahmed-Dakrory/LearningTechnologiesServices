/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.survey.model.ISurveySectionRepository;
import main.com.zc.services.domain.survey.model.SurveySections;

/**
 * @author omnya
 *
 */
@Repository("ISurveySectionRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SurveySectionRepositoryImpl implements ISurveySectionRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public SurveySections add(SurveySections SurveySections) {
		try{
			sessionFactory.getCurrentSession().save(SurveySections);
			return SurveySections;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public SurveySections update(SurveySections survey) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(survey);
			tx1.commit();
			session.close();
			return survey;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public SurveySections getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("SurveySections.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SurveySections> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getAll");

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SurveySections> getBySectionId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getBySectionId").
					setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SurveySections> getBySurveyId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getBySurveyId").
					setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(SurveySections survey) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(survey);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public SurveySections getBySectionIdAndSurveyID(Integer survey, Integer section) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getBySectionIdAndSurveyID").
					setInteger("id", survey).setInteger("sectionID", section);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SurveySections> getGeneralBySurveyId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getGeneralBySurveyId").
					setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SurveySections> getCourseSectionsBySurveyID(Integer surveyId,Integer courseID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SurveySections.getCourseSectionsBySurveyID").
					setInteger("courseID", courseID).setInteger("surveyId", surveyId);

			@SuppressWarnings("unchecked")
			List<SurveySections> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

}
