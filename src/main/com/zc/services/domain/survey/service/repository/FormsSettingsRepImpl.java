/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;

import main.com.zc.services.domain.survey.model.FormsSettings;
import main.com.zc.services.domain.survey.model.IFormsSettingsRep;

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
@Repository("IFormsSettingsRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FormsSettingsRepImpl implements IFormsSettingsRep{

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public FormsSettings add(FormsSettings form) {
		try{
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
	public boolean remove(Integer id) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(id));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public FormsSettings update(FormsSettings form) {
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
	public List<FormsSettings> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("FormsSettings.getAll");

			@SuppressWarnings("unchecked")
			List<FormsSettings> results = query.list();
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
	public FormsSettings getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("FormsSettings.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<FormsSettings> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}
	

	@Override
	public List<FormsSettings> getByFormId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("FormsSettings.getByFormId").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<FormsSettings> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<FormsSettings> getByFormIdAndLevelId(Integer formID,
			Integer levelID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("FormsSettings.getByFormIdAndLevelId").setInteger("id", formID).setInteger("levelID", levelID);

			@SuppressWarnings("unchecked")
			List<FormsSettings> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
