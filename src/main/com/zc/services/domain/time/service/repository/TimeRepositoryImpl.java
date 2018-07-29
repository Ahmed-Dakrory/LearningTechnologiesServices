/**
 * 
 */
package main.com.zc.services.domain.time.service.repository;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.shared.IConstants;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;

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
@Repository("ITimeRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TimeRepositoryImpl implements ITimeRepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;

	
	@Autowired
	IConstants cont;
	@Override
	public Time getById(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getTimeByID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<Time> getAll() {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
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
	public Time update(Time time) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(time);
		tx1.commit();
		session.close();
		 return time;
	}

	@Override
	public int add(Time time) {
		sessionFactory.getCurrentSession().save(time); 
		 return time.getId();
	}

	@Override
	public Time getTimeByPersonID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getTimeByPersonId").setInteger("personID",id);

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<Time> getAllByStatus(String status) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllByAttendanceStatus").setString("attendanceStatus",status);

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}

	@Override
	public List<Time> getAllByStatusAndDate(String status, Calendar date) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllByAttendanceStatusAndDate").setString("attendanceStatus",status).setCalendar("date",date);

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}

	@Override
	public Time getTimeByFileNoAndDate(int fileNo, Calendar date) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getTimeByFileNoAndDate").setInteger("fileNo",fileNo).setCalendar("date",date);

        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<Time> getAllAttendanceByFileNo(int fileNo) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllAttendanceByFileNo").setInteger("fileNo", fileNo);

        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}

	@Override
	public List<Time> getAllAttendanceByFileNoAndStatus(int fileNo,
			String status) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllAttendanceByFileNoAndStatus").setInteger("fileNo", fileNo).setString("status",status);

        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}

	@Override
	public List<Time> getAllByAttendanceDate(Calendar date) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllByAttendanceDate").setCalendar("date", date);

        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}

	@Override
	public Time getAllByAttendanceFileNoAndDate(Calendar date, int fileNo) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllByAttendanceFileNoAndDate").setInteger("fileNo", fileNo).setCalendar("date", date);

        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<Time> getAllByExcuseStatus(String excuseStatus) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Time.getAllByExcuseStatus").setString("excuseStatus",excuseStatus);

	        
		   @SuppressWarnings("unchecked")
		List<Time> results=query.list();
		   return results;
	}
	
	}


