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

import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author Omnya Alaa
 *
 */
@ManagedBean
@ApplicationScoped
public class ShowImageHelperInstructor extends  HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	@Autowired
	IEmployeeRepository empRep;
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
		Employee emp=	empRep.getById(Integer.parseInt(request.getParameter("path")));
	byte[] image=	emp.getImage();
	
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
