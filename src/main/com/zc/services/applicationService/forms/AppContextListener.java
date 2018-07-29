/**
 * 
 */
package main.com.zc.services.applicationService.forms;

/**
 * @author omnya
 *
 */
import java.util.TimerTask;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import main.com.zc.services.domain.person.model.IStudentRepository;



@ManagedBean
@ApplicationScoped
public class AppContextListener implements ServletContextListener {


	IStudentRepository rep;
	@ManagedProperty("#{IStudentRepository}")
	
	private IStudentRepository facade;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		System.out.println("HelloWorld Listener has been shutdown");
//		 
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
	}

	class VodTimerTask extends TimerTask {

		@Override
		public void run() {
		
		}
	}

	public IStudentRepository getFacade() {
		return facade;
	}

	public void setFacade(IStudentRepository facade) {
		this.facade = facade;
	}



}