/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.service;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorAdminFacade;
import main.com.zc.services.presentation.forms.emails.service.impl.CheckNewEmails;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class SendEmailsBean {


	@ManagedProperty("#{TestService}")
	private String name;
	
	@PostConstruct
	public void init()
	{
		
		//service.parse();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	
	
}
