/**
 * 
 */
package main.com.zc.services.domain.courses.model.note;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface NoteRepository {

	public List<Note> getAll();
	public List<Note> getByCourseId(int id);
	public Note addNote(Note note);
}
