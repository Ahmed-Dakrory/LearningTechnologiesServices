/**
 * 
 */
package main.com.zc.services.domain.person.service.repository;

import java.util.Date;
import java.util.List;

import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Omnya Alaa
 *
 */
@Repository("IEmployeeRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IEmployeeRepositoryImpl implements IEmployeeRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;

	@Override
	public Employee add(Employee instructor) {
		sessionFactory.getCurrentSession().save(instructor);
		return instructor;
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
	public Employee update(Employee instructor) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(instructor);
		tx1.commit();
		session.close();
		return instructor;
	}

	@Override
	public List<Employee> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Employee.getAll");

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results;
	}
	@Override
	public List<Employee> getAllEmp() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Employee.getAllEmp");

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results;
	}

	@Override
	public Employee getById(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Employee.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results.get(0);
	}

	@Override
	public Employee getByName(String name) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Employee.getByName").setString("name", name);

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results.get(0);
	}

	@Override
	public Employee getByMail(String mail) {
	try{
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Employee.getByMail").setString("mail", mail.toLowerCase());

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results.get(0);
	}
	catch(Exception ex){
		System.out.println(ex.toString());
		return null;
	}
	}
	@Override
	public void updateLastNotificationDate(Date last,Integer id) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		Query query = session.createSQLQuery("UPDATE mail_setting SET LAST_NOTIFICATION =:last where ID =:id ").setDate("last", last).setInteger("id", id);
		query.executeUpdate();
		tx1.commit();
		session.close();
		}

	@Override
	public List<Employee> getAllTas() {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Employee.getAllTas");

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	
	}

	@Override
	public List<Employee> getByType(Integer typeID) {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Employee.getByType").setInteger("type", typeID);

		@SuppressWarnings("unchecked")
		List<Employee> results = query.list();
		return results;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
