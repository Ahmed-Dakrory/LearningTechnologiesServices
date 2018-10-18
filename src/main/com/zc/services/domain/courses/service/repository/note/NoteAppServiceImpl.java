/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.note;





import java.util.List;
import main.com.zc.services.domain.courses.model.note.INoteAppService;
import main.com.zc.services.domain.courses.model.note.Note;
import main.com.zc.services.domain.courses.model.note.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("NoteFacadeImpl")
public class NoteAppServiceImpl implements INoteAppService{

	@Autowired
	NoteRepository noteRepository;
	
	
	@Override
	public List<Note> getAll() {
		try{
			List<Note> note=noteRepository.getAll();
			
			return note;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Note> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<Note> note=noteRepository.getByCourseId(id);
					
					return note;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public Note addNote(Note note) {
		try{
			Note note2=noteRepository.addNote(note);
			return note2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(Note so) {
		// TODO Auto-generated method stub
		try{
			noteRepository.delete(so);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public Note getById(int id) {
		// TODO Auto-generated method stub
		try{
			Note note=noteRepository.getById(id);
			
			return note;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	
	
}
		
		

	
		
	


