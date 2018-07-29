/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.services.domain.petition.model.IAttachmentsRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author momen
 *
 */
@Repository("IAttachmentsRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AttachmentsRepositoryImpl implements IAttachmentsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public Attachments add(Attachments attachments) {
		try{
		sessionFactory.getCurrentSession().save(attachments);
		return attachments;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean remove(int id) {
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
	public Attachments update(Attachments attachments) {
	try{
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(attachments);
		tx1.commit();
		session.close();
		return attachments;
	}
	catch(Exception ex)
	{
		
		ex.printStackTrace();
		return null;
	}
	}

	@Override
	public List<Attachments> getAll() {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Attachments.getAll");

		@SuppressWarnings("unchecked")
		List<Attachments> results = query.list();
		return results;
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting Attachments ");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Attachments getById(int id) {
		try{
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Attachments.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Attachments> results = query.list();
		return results.get(0);
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
		
	}

}
