/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;

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
@Repository("IPetitionsActionsRep")
@Transactional
public class PetitionsActionsRepImpl implements IPetitionsActionsRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public PetitionsActions add(PetitionsActions action) {
		try{
			sessionFactory.getCurrentSession().save(action);
			System.out.println("Data:dakrory: "+action.getActionType().getID());
			return action;
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
	public PetitionsActions update(PetitionsActions action) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(action);
			tx1.commit();
			session.close();
			return action;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public PetitionsActions getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("PetitionsActions.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<PetitionsActions> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<PetitionsActions> getByPetitionIDAndForm(Integer id,
			Integer formType) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("PetitionsActions.getByPetitionIDAndForm").setInteger("id", id).setInteger("formType", formType);

			@SuppressWarnings("unchecked")
			List<PetitionsActions> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of petition id");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<PetitionsActions> getByPetitionIDAndFormAndIns(Integer id,
			Integer formType, Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("PetitionsActions.getByPetitionIDAndFormAndIns").setInteger("id", id).setInteger("formType", formType)
					.setInteger("insID", insID);

			@SuppressWarnings("unchecked")
			List<PetitionsActions> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of petition id");
				ex.printStackTrace();
				return null;
			}
	}

}
