/**
 * 
 */
package main.com.zc.service.filesOfLibraries;

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
public class filesOfLibrariesRepositoryImpl implements filesOfLibrariesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public filesOfLibraries addfilesOfLibraries(filesOfLibraries data) {
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
	public List<filesOfLibraries> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("filesOfLibraries.getAll");

				 @SuppressWarnings("unchecked")
				List<filesOfLibraries> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(filesOfLibraries data)throws Exception {
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
	public filesOfLibraries getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("filesOfLibraries.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<filesOfLibraries> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	
	@Override
	public List<filesOfLibraries> getByYearAndSemester(String year, String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("filesOfLibraries.getByYearAndSemester")
				.setString("year", year).setString("semester", semester);

		 @SuppressWarnings("unchecked")
		 List<filesOfLibraries> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}


	



	
	


}
