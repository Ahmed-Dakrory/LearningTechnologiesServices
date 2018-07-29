/**
 * 
 */
package main.com.zc.shared;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import main.com.zc.shared.presentation.dto.AttachmentDTO;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;

/**
 * @author Momen
 * 
 */
public class AttachmentDownloaderHelper 
{
	public static void createHTTPDownlodFileResponse(AttachmentDTO attachment)
	{
		if(attachment == null || attachment.getContents().length == 0)
			JavaScriptMessagesHandler.RegisterErrorMessage("Attachment Error", "No Attachment To Download !");
		else
		{
			String fileName = attachment.getName();
			byte[] fileData = attachment.getContents();
			
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset(); 
		    ec.setResponseContentType("application/octet-stream");
		    //ec.setResponseHeader("Content-Type", "application/" + FilenameUtils.getExtension(fileName));
		    ec.setResponseHeader("Content-Length", String.valueOf(fileData.length));
	        ec.setResponseHeader("Content-Disposition", "inline; filename=\""+fileName+"\"");
		    try {
				ec.getResponseOutputStream().write(fileData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    fc.responseComplete();
		}
	}
}
