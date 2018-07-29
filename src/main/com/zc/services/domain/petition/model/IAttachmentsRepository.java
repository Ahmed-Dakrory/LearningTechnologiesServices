/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author momen
 *
 */
public interface IAttachmentsRepository {

	public Attachments add(Attachments attachments);

	public boolean remove(int id);

	public Attachments update(Attachments attachments);

	public List<Attachments> getAll();

	public Attachments getById(int id);
}
