/**
 * 
 */
package main.com.zc.services.presentation.elections.bean;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import main.com.zc.services.domain.elections.model.ElectionCandidate;
import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.services.presentation.elections.facade.IElectionFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class ElectionResults {

	PieChartModel preisdentModel = new PieChartModel();
	PieChartModel viceModel = new PieChartModel();
	PieChartModel actPresidentModel = new PieChartModel();
	PieChartModel serPresidentModel = new PieChartModel();
	PieChartModel acadPresidentModel = new PieChartModel();
	PieChartModel actModel = new PieChartModel();
	PieChartModel servModel = new PieChartModel();
	PieChartModel acadModel = new PieChartModel();
	
	private List<BaseDTO> presidentList;
	private List<BaseDTO> viceList;
	private List<BaseDTO> committeeActivtiesPresidentList;
	private List<BaseDTO> committeeServicesPresidentList;
	private List<BaseDTO> committeeAcademicPresidentList;
	
	@ManagedProperty("#{electionFacadeImpl}")
   	private IElectionFacade electionFacade; 
 
 
@PostConstruct
public void init()
{ 
	presidentList=new ArrayList<BaseDTO>();
	presidentList=electionFacade.getCandidateByID(1);
	for(int i=0;i<presidentList.size();i++)
	{
		List<ElectionResultDTO> presdLstDTo=electionFacade.resultsForPresident(presidentList.get(i).getId());
		//TODO Un-comment this in general case
		//preisdentModel.set(presidentList.get(i).getName()+" , "+presdLstDTo.size()+" Votes", presdLstDTo.size());
        //TODO comment this in general case
		preisdentModel.set(presidentList.get(i).getName(), presdLstDTo.size());
		
	}
	
	viceList=new ArrayList<BaseDTO>();
	viceList=electionFacade.getCandidateByID(2);
	for(int i=0;i<viceList.size();i++)
	{
		List<ElectionResultDTO> viceLstDTo=electionFacade.resultsForVice(viceList.get(i).getId());
		viceModel.set(viceList.get(i).getName()+" , "+viceLstDTo.size()+" Votes", viceLstDTo.size());
		
	}
	
	committeeActivtiesPresidentList=new ArrayList<BaseDTO>();
	committeeActivtiesPresidentList=electionFacade.getCandidateByID(6);
	for(int i=0;i<committeeActivtiesPresidentList.size();i++)
	{
		List<ElectionResultDTO> actvLstDTo=electionFacade.resultsForAct(committeeActivtiesPresidentList.get(i).getId());
		//TODO Un-comment this in general case
		//actPresidentModel.set(committeeActivtiesPresidentList.get(i).getName()+" , "+actvLstDTo.size()+" Votes", actvLstDTo.size());
		 //TODO comment this in general case
		actPresidentModel.set(committeeActivtiesPresidentList.get(i).getName(), actvLstDTo.size());
	}
	
	committeeServicesPresidentList=new ArrayList<BaseDTO>();
	committeeServicesPresidentList=electionFacade.getCandidateByID(7);
	for(int i=0;i<committeeServicesPresidentList.size();i++)
	{
		List<ElectionResultDTO> servLstDTo=electionFacade.resultsForService(committeeServicesPresidentList.get(i).getId());
		//TODO Un-comment this in general case
		//serPresidentModel.set(committeeServicesPresidentList.get(i).getName()+" , "+servLstDTo.size()+" Votes", servLstDTo.size());
		//TODO comment this in general case
		serPresidentModel.set(committeeServicesPresidentList.get(i).getName(), servLstDTo.size());
		
	}
	
	committeeAcademicPresidentList=new ArrayList<BaseDTO>();
	committeeAcademicPresidentList=electionFacade.getCandidateByID(8);
	for(int i=0;i<committeeAcademicPresidentList.size();i++)
	{
		List<ElectionResultDTO> acadLstDTo=electionFacade.resultsForAcad(committeeAcademicPresidentList.get(i).getId());
		acadPresidentModel.set(committeeAcademicPresidentList.get(i).getName()+" , "+acadLstDTo.size()+" Votes", acadLstDTo.size());
		
	}
	
	List<BaseDTO> activtiesPersons=new ArrayList<BaseDTO>();
	activtiesPersons=electionFacade.getCandidateByID(3);
	for(int i=0;i<activtiesPersons.size();i++)
	{
		List<BaseDTO> person=new ArrayList<BaseDTO>();
		//1-get all activties String
		List<BaseDTO> activtiesResults=new ArrayList<BaseDTO>();
		activtiesResults=electionFacade.getAllActivtiesResults();
		for(int j=0;j<activtiesResults.size();j++)
		{
			   if(activtiesResults.get(j).getName()!=null)
			   {
			   String[] data = activtiesResults.get(j).getName().split("\\|");
			   for(int k=0;k<data.length;k++)
			   {
				   if(Integer.toString(activtiesPersons.get(i).getId()).equals(data[k]))
				   {
					   person.add(new BaseDTO(activtiesPersons.get(i).getId()));
				   }
			   }
			   }
			   //else {}
		}
		
		//Get name by ID
		actModel.set(activtiesPersons.get(i).getName()+","+ +person.size()+" Votes", person.size());
	}
	
	List<BaseDTO> servicesPerson=new ArrayList<BaseDTO>();
	servicesPerson=electionFacade.getCandidateByID(4);
	for(int i=0;i<servicesPerson.size();i++)
	{
		List<BaseDTO> person=new ArrayList<BaseDTO>();
		//1-get all activties String
		List<BaseDTO> servicesResults=new ArrayList<BaseDTO>();
		servicesResults=electionFacade.getAllServicesResults();
		for(int j=0;j<servicesResults.size();j++)
		{
			if(servicesResults.get(j).getName()!=null)
			{
			   String[] data = servicesResults.get(j).getName().split("\\|");
			   for(int k=0;k<data.length;k++)
			   {
				   if(Integer.toString(servicesPerson.get(i).getId()).equals(data[k]))
				   {
					   person.add(new BaseDTO(servicesPerson.get(i).getId()));
				   }
			   }
			}
			else {}
		}
		
		//Get name by ID
		servModel.set(servicesPerson.get(i).getName()+","+ +person.size()+" Votes", person.size());
	}
	
	
	List<BaseDTO> acadPerson=new ArrayList<BaseDTO>();
	acadPerson=electionFacade.getCandidateByID(5);
	for(int i=0;i<acadPerson.size();i++)
	{
		List<BaseDTO> person=new ArrayList<BaseDTO>();
		//1-get all activties String
		List<BaseDTO> acadResults=new ArrayList<BaseDTO>();
		acadResults=electionFacade.getAllAcademicResults();
		for(int j=0;j<acadResults.size();j++)
		{
			if(acadResults.get(j).getName()!=null)
			{
			   String[] data = acadResults.get(j).getName().split("\\|");
			   for(int k=0;k<data.length;k++)
			   {
				   if(Integer.toString(acadPerson.get(i).getId()).equals(data[k]))
				   {
					   person.add(new BaseDTO(acadPerson.get(i).getId()));
				   }
			   }
			}
		}
		
		//Get name by ID
		acadModel.set(acadPerson.get(i).getName()+","+ +person.size()+" Votes", person.size());
	}
	
	
	}



public String navigateToViceResults()
{
	return "/pages/elections/viceResults.xhtml?faces-redirect=true;";
	}
public IElectionFacade getElectionFacade() {
	return electionFacade;
}


public void setElectionFacade(IElectionFacade electionFacade) {
	this.electionFacade = electionFacade;
}




public PieChartModel getPreisdentModel() {
	return preisdentModel;
}




public void setPreisdentModel(PieChartModel preisdentModel) {
	this.preisdentModel = preisdentModel;
}




public PieChartModel getViceModel() {
	return viceModel;
}




public void setViceModel(PieChartModel viceModel) {
	this.viceModel = viceModel;
}








public PieChartModel getActPresidentModel() {
	return actPresidentModel;
}




public void setActPresidentModel(PieChartModel actPresidentModel) {
	this.actPresidentModel = actPresidentModel;
}




public PieChartModel getSerPresidentModel() {
	return serPresidentModel;
}




public void setSerPresidentModel(PieChartModel serPresidentModel) {
	this.serPresidentModel = serPresidentModel;
}




public PieChartModel getAcadPresidentModel() {
	return acadPresidentModel;
}




public void setAcadPresidentModel(PieChartModel acadPresidentModel) {
	this.acadPresidentModel = acadPresidentModel;
}




public List<BaseDTO> getViceList() {
	return viceList;
}




public void setViceList(List<BaseDTO> viceList) {
	this.viceList = viceList;
}




public PieChartModel getActModel() {
	return actModel;
}




public void setActModel(PieChartModel actModel) {
	this.actModel = actModel;
}




public PieChartModel getServModel() {
	return servModel;
}




public void setServModel(PieChartModel servModel) {
	this.servModel = servModel;
}




public PieChartModel getAcadModel() {
	return acadModel;
}




public void setAcadModel(PieChartModel acadModel) {
	this.acadModel = acadModel;
}

}
