/**
 * 
 */
package main.com.zc.shared;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@ManagedBean
@ApplicationScoped
public class ShowImageHelper  extends  HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	@Autowired
	IStudentRepository rep;
	@Override
	public void init(){
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}		  
	
	public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		if(request.getParameter("path") != null)
		{
		Student student=	rep.getPersonById(Integer.parseInt(request.getParameter("path")));
	byte[] image=	student.getData().getStudentImage();
	
	InputStream dbStream = null;
	if (image != null) {
		//response.setContentLength(arg0);
		response.setContentLength(image.length);

		response.getOutputStream().write(image);
	}
	
		}

	  
	  }
		  
	  public void destroy()
	  {
	      // do nothing.
	  }

}