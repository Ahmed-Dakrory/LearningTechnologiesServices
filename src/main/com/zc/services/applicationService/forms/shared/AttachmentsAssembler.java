/**
 * 
 */
package main.com.zc.services.applicationService.forms.shared;

import org.hibernate.Hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author momen
 *
 */
public class AttachmentsAssembler {

	public AttachmentDTO toDTO(Attachments attachments)
	{

		if(attachments == null)
			return null;
		
		Blob contents = attachments.getContents();
		byte[] data = new byte[0];
		try 
		{
			InputStream inputStream = contents.getBinaryStream();
		    int expected = inputStream.available();
		    data = new byte[expected];
		    int length = inputStream.read(data);
		    if(length != expected)
		            throw new IOException("Could not read full input");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AttachmentDTO dto=new AttachmentDTO(attachments.getId(),attachments.getName(),data);
		return dto;
	}
	
	public Attachments toEntity(AttachmentDTO dto)
	{
		if(dto == null)
			return null;
		
		Attachments entity=new Attachments();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		Blob bb = Hibernate.createBlob(dto.getContents());
		entity.setContents(bb);
		
		return entity;
	}
	
}
