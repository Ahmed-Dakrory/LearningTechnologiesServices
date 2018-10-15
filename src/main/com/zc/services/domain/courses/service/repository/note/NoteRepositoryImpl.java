/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.note;

import java.util.List;
import main.com.zc.services.domain.courses.model.note.Note;
import main.com.zc.services.domain.courses.model.note.NoteRepository;

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
public class NoteRepositoryImpl implements NoteRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<Note> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Note.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Note> results=query.list();
		   return results;
	}

	@Override
	public Note addNote(Note note) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(note);
			tx1.commit();
			session.close(); 
			return note; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Note> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Note.getAll");

				 @SuppressWarnings("unchecked")
				List<Note> results=query.list();
				   return results;
	}

	

}
