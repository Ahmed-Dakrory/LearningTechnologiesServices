/**
 * 
 */
package main.com.zc.services.domain.data.service.repository;

import java.util.List;

import main.com.zc.services.domain.data.model.Data;
import main.com.zc.services.domain.data.model.IDataRepository;
import main.com.zc.services.domain.survey.model.Concentration;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Omnya
 * 
 */
@Repository("IDataRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DataRepository implements IDataRepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	

	@Override
	public List<Data> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Data.getAll");

			@SuppressWarnings("unchecked")
			List<Data> results = query.list();
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
	public boolean delete(int id) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(id);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	

	@Override
	public int add(Data data) {
		try{
			sessionFactory.getCurrentSession().save(data);
			return data.getId();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(Data data) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(data);
			tx1.commit();
			session.close();
			return data.getId();
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public Data getByMail(String mail) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Data.getByMail").setString("mail", mail);

			@SuppressWarnings("unchecked")
			List<Data> results = query.list();
			
				if(results.size()>0) {

					return results.get(0);
				}else {
					return null;
				}
			
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;
			
	}
	}}

