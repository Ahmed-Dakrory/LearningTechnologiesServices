/**
 * 
 */
package main.com.zc.services.domain.data.service.repository;

import java.util.List;

import main.com.zc.services.domain.data.model.IMailSettingsRepository;
import main.com.zc.services.domain.data.model.MailSetting;

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
@Repository("IMailSettingsRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MailSettingsRepositoryImpl implements IMailSettingsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public MailSetting add(MailSetting mailSetting) {
		try{
		sessionFactory.getCurrentSession().save(mailSetting);
		return mailSetting;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean remove(MailSetting mailSetting) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(mailSetting);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public MailSetting update(MailSetting mailSetting) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(mailSetting);
		tx1.commit();
		session.close();
		return mailSetting;
	}

	@Override
	public List<MailSetting> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("MailSetting.getAll");

		@SuppressWarnings("unchecked")
		List<MailSetting> results = query.list();
		return results;
	}

	@Override
	public MailSetting getById(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("MailSetting.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<MailSetting> results = query.list();
		return results.get(0);
	}

}
