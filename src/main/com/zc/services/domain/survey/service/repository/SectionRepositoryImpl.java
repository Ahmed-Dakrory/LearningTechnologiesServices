/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.survey.model.ISectionRepository;
import main.com.zc.services.domain.survey.model.Section;

/**
 * @author omnya
 *
 */
@Repository("ISectionRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SectionRepositoryImpl implements ISectionRepository{

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public Section add(Section section) {
		try{
			sessionFactory.getCurrentSession().save(section);
			return section;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Section update(Section section) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(section);
			tx1.commit();
			session.close();
			return section;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Section getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Section.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Section> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Section> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Section.getAll");

			@SuppressWarnings("unchecked")
			List<Section> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(Section section) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(section);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Section getByIdAndCourseID(Integer id, Integer cID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Section.getByIdAndCourseID").setInteger("id", id).setInteger("cId", cID);

			@SuppressWarnings("unchecked")
			List<Section> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Section getByCourseID(int id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Section.getByCourseID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Section> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
