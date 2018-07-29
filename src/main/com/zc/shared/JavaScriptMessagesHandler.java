/**
 * 
 */
package main.com.zc.shared;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

/**
 * @author Momen
 * 
 */
public class JavaScriptMessagesHandler 
{
	public static void RegisterNotificationMessage(String title,String message)
	{
		if(title == null)
			title = "";
		
		RegisterMessage("", message, 0);
	}
	
	public static void RegisterWarningMessage(String title,String message)
	{
		if(title == null)
			title = "";
		
		RegisterMessage("", message, 1);
	}
	
	public static void RegisterErrorMessage(String title,String message)
	{
		if(title == null)
			title = "";
		
		RegisterMessage("", message, 2);
	}
	
	private static void RegisterMessage(String title,String message,int Type)
	{
		String script = "";
		switch(Type)
		{
		case 1:
			script = "showWarning(\""+title+"\",\""+message+"\");";
			break;
		case 2:
			script = "showError(\""+title+"\",\""+message+"\");";
			break;
		default:
			script = "showNotice(\""+title+"\",\""+message+"\");";
		}
		
		boolean ajaxCall = RequestContext.getCurrentInstance().isAjaxRequest();
		
		if(ajaxCall)
		{
			RequestContext.getCurrentInstance().execute(script);
		}
		else
		{
			script = "document.addEventListener(\"DOMContentLoaded\", function(event) {"+script+"})";
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    //ec.responseReset(); 
		    try {
				ec.getResponseOutputWriter().write("<script>"+script+"</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //fc.responseComplete();
		}
	}
}
