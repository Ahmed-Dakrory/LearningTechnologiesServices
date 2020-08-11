/**
 * 
 */
package main.com.zc.service.academic_advising_instructor;

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
public class aa_instructorRepositoryImpl implements aa_instructorRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public aa_instructor addaa_instructor(aa_instructor data) {
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
	public List<aa_instructor> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor.getAll");

				 @SuppressWarnings("unchecked")
				List<aa_instructor> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(aa_instructor data)throws Exception {
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
	public aa_instructor getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<aa_instructor> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}



	@Override
	public aa_instructor getByMail(String mail) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("aa_instructor.getByMail")
				 .setString("mail", mail);

		 @SuppressWarnings("unchecked")
		List<aa_instructor> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}


	



	
	


}
