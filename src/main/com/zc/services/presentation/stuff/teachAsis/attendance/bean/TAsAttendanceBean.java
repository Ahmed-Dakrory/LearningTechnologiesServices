/**
 * 
 */
package main.com.zc.services.presentation.stuff.teachAsis.attendance.bean;

import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import main.com.zc.services.applicationService.stuff.teachAsis.attendance.service.ITAAppService;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class TAsAttendanceBean {

	private String name;
	
	@ManagedProperty("#{TAAppServiceImpl}")
   	private ITAAppService taAppService; 
	@PostConstruct
	public void init()
	{
		name="test";
		try {
			taAppService.parseFile("resources/upload/tas/Faculty_15-10-2014 to 19-11-2014.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ITAAppService getTaAppService() {
		return taAppService;
	}

	public void setTaAppService(ITAAppService taAppService) {
		this.taAppService = taAppService;
	}
	
}
