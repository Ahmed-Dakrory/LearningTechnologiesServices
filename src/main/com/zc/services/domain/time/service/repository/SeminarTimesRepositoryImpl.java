/**
 * 
 */
package main.com.zc.services.domain.time.service.repository;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.shared.IConstants;
import main.com.zc.services.domain.time.model.ISeminarTimesRepository;
import main.com.zc.services.domain.time.model.SeminarTimes;

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
@Repository("ISeminarTimesRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SeminarTimesRepositoryImpl implements ISeminarTimesRepository {
	@Autowired
	private SessionFactory sessionFactory;
	Session session;

	
	@Autowired
	IConstants cont;
	
	@Override
	public int add(SeminarTimes time) {
		sessionFactory.getCurrentSession().save(time); 
		 return time.getId();
	}

	@Override
	public SeminarTimes update(SeminarTimes time) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(time);
		tx1.commit();
		session.close();
		 return time;
	}

	@Override
	public SeminarTimes getById(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getSeminarTimesByID").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<SeminarTimes> getAll() {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results;
	}

	@Override
	public boolean remove(int id) {
		try
		{
			sessionFactory.getCurrentSession().delete(id); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return true;
	}

	@Override
	public SeminarTimes getSeminarTimesByPersonID(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getSeminarTimesByPersonId").setInteger("personID",id);

	        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<SeminarTimes> getAllByStatus(String status) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getAllByAttendanceStatus").setString("attendanceStatus",status);

        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results;
	}

	@Override
	public List<SeminarTimes> getAllByStatusAndDate(String status, Calendar date) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getAllByAttendanceStatusAndDate").setString("attendanceStatus",status).setCalendar("date",date);

	        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results;
	}

	@Override
	public SeminarTimes getSeminarTimesByFileNoAndDate(int fileNo, Calendar date) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getSeminarTimesByFileNoAndDate").setInteger("fileNo",fileNo).setCalendar("date",date);

        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<SeminarTimes> getAllAttendanceByFileNo(int fileNo) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SeminarTimes.getAllAttendanceByFileNo").setInteger("fileNo", fileNo);

        
		   @SuppressWarnings("unchecked")
		List<SeminarTimes> results=query.list();
		   return results;
	}

}
