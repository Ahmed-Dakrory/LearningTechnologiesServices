/**
 * 
 */
package main.com.zc.service.decleration_of_track;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class decleration_of_trackRepositoryImpl implements decleration_of_trackRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public decleration_of_track adddecleration_of_track(decleration_of_track data) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(data);
			tx1.commit();
			session.close(); 
			return data; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<decleration_of_track> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("decleration_of_track.getAll");

				 @SuppressWarnings("unchecked")
				List<decleration_of_track> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(decleration_of_track data)throws Exception {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public decleration_of_track getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("decleration_of_track.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<decleration_of_track> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public List<decleration_of_track> getAllByYearAndSemestar(int year, int semestar) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("decleration_of_track.getAllByYearAndSemestar")
				 .setInteger("year",year)
				 .setInteger("semester", semestar);

		 @SuppressWarnings("unchecked")
		List<decleration_of_track> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<decleration_of_track> getAllByYearAndSemestarAndStudent(int year, int semestar, int studentId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("decleration_of_track.getAllByYearAndSemestarAndStudent")
				 .setInteger("year",year)
				 .setInteger("semester", semestar)
				 .setInteger("studentId", studentId);

		 @SuppressWarnings("unchecked")
		List<decleration_of_track> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}


	

	


}
