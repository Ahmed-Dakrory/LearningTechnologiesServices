/**
 * 
 */
package main.com.zc.services.domain.stuff.teachAsis.attendance.service.impl;

import java.util.List;
import main.com.zc.services.domain.stuff.teachAsis.attendance.model.ITARepository;
import main.com.zc.services.domain.stuff.teachAsis.attendance.model.TA;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author omnya
 *
 */
@Repository
public class TARepositoryImpl implements ITARepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	@Override
	public TA add(TA ta) {
		try{
			
			 return (TA) sessionFactory.getCurrentSession().save(ta); 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public TA getByID(int id) {
		try{
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("TA.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<TA> results=query.list();
		   return results.get(0);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TA> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("TA.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<TA> results=query.list();
		   return results;
	}

	@Override
	public TA getByFacultyID(String facultyID) {
		try{
			 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("TA.getByFacultyID").setString("facultyID",facultyID);

		        
			   @SuppressWarnings("unchecked")
			List<TA> results=query.list();
			   return results.get(0);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

}
