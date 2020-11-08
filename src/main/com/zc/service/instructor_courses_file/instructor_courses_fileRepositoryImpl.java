/**
 * 
 */
package main.com.zc.service.instructor_courses_file;

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
public class instructor_courses_fileRepositoryImpl implements instructor_courses_fileRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public instructor_courses_file addinstructor_courses_file(instructor_courses_file data) {
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
	public List<instructor_courses_file> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_courses_file.getAll");

				 @SuppressWarnings("unchecked")
				List<instructor_courses_file> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(instructor_courses_file data)throws Exception {
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
	public instructor_courses_file getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_courses_file.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<instructor_courses_file> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}






	@Override
	public List<instructor_courses_file> getAllByInstructorEmailAndYearAndSemester(String email, String year,
			String semester) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("instructor_courses_file.getAllByInstructorEmailAndYearAndSemester")
				 .setString("semester",semester)
				 .setString("year", year)
				 .setString("email", email);

		 @SuppressWarnings("unchecked")
		List<instructor_courses_file> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}



	



	
	


}
