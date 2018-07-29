/**
 * 
 */
package main.com.zc.services.presentation.elections.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.mapping.Array;

import main.com.zc.services.presentation.elections.dto.ElectionCandidatesDTO;
import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.services.presentation.elections.facade.IElectionFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@SessionScoped
public class ElectionBean {

	private String studentID;
	private String studentCode;
	private List<ElectionCandidatesDTO> presidentList;
	private List<ElectionCandidatesDTO> viceList;
	private List<ElectionCandidatesDTO> committeeActivtiesList;
	private List<ElectionCandidatesDTO> committeeActivtiesPresidentList;
	private List<ElectionCandidatesDTO> committeeServicesList;
	private List<ElectionCandidatesDTO> committeeServicesPresidentList;
	private List<ElectionCandidatesDTO> committeeAcademicList;
	private List<ElectionCandidatesDTO> committeeAcademicListPresident;
	private List<String> names;
	private int selectedPresident;
	private int selectedVice;
	//private int selectedCommitteeActivties;
	private Integer[]  selectedCommitteeActivties;  
	private int selectedCommitteeActivtiesPresident;
	//private int selectedCommitteeServices;
	private Integer[]selectedCommitteeServices;
	private int selectedCommitteeServicesPresident;	
	//private int selectedCommitteeAcademic;
	private Integer[]selectedCommitteeAcademic;
	private int selectedCommitteeAcademicPresident;
	private int totalNoOFVotes;
	
	
	@ManagedProperty("#{electionFacadeImpl}")
	   	private IElectionFacade electionFacade; 
	 
	 
	@PostConstruct
	public void init()
	{
		names=new ArrayList<String>();
		names.add("First");
		names.add("second");
		clearSelections();
		setStudentCode("");
		setStudentID("");
		presidentList=new ArrayList<ElectionCandidatesDTO>();
		List<BaseDTO> tempPresidentLst=new ArrayList<>();
		tempPresidentLst=electionFacade.getCandidateByID(1);
		for(int i=0;i<tempPresidentLst.size();i++)
		{
			presidentList.add(new ElectionCandidatesDTO(tempPresidentLst.get(i).getId(),
					tempPresidentLst.get(i).getName(),tempPresidentLst.get(i).getFileNo(), tempPresidentLst.get(i).getImg()));
		}
		
		
		/*presidentList.add(new BaseDTO(1, "President 1"));
		presidentList.add(new BaseDTO(2, "President 2"));
		presidentList.add(new BaseDTO(3, "President 3"));
		*/
		List<BaseDTO> tempViceList=new ArrayList<>();
		tempViceList=electionFacade.getCandidateByID(2);
		
		viceList=new ArrayList<ElectionCandidatesDTO>();
		
		
		for(int i=0;i<tempViceList.size();i++)
		{
			viceList.add(new ElectionCandidatesDTO(tempViceList.get(i).getId(), tempViceList.get(i).getName(), tempViceList.get(i).getFileNo(), tempViceList.get(i).getImg()));
		}
		/*viceList.add(new BaseDTO(1, "Vice 1"));
		viceList.add(new BaseDTO(2, "Vice 2"));
		viceList.add(new BaseDTO(3, "Vice 3"));*/
		
		List<BaseDTO> tempCommitteeActivtiesList=new ArrayList<>();
		tempCommitteeActivtiesList=electionFacade.getCandidateByID(3);
		
		committeeActivtiesList=new ArrayList<ElectionCandidatesDTO>();
		
		for(int i=0;i<tempCommitteeActivtiesList.size();i++)
		{
			committeeActivtiesList.add(new ElectionCandidatesDTO(tempCommitteeActivtiesList.get(i).getId(), tempCommitteeActivtiesList.get(i).getName(),
					tempCommitteeActivtiesList.get(i).getFileNo(), tempCommitteeActivtiesList.get(i).getImg()));
		}
		committeeActivtiesList.size();
		/*committeeActivtiesList.add(new BaseDTO(1, "Activties 1"));
		committeeActivtiesList.add(new BaseDTO(2, "Activties 2"));
		committeeActivtiesList.add(new BaseDTO(3, "Activties 3"));*/
		List<BaseDTO> tempCommitteeServicesList=new ArrayList<>();
		committeeServicesList=new ArrayList<ElectionCandidatesDTO>();
		tempCommitteeServicesList=electionFacade.getCandidateByID(4);
		for(int i=0;i<tempCommitteeServicesList.size();i++)
		{
			committeeServicesList.add(new ElectionCandidatesDTO(tempCommitteeServicesList.get(i).getId(), 
					tempCommitteeServicesList.get(i).getName(), tempCommitteeServicesList.get(i).getFileNo(), tempCommitteeServicesList.get(i).getImg()));
		}
		committeeServicesList.size();
		/*committeeServicesList.add(new BaseDTO(1, "Service 1"));
		committeeServicesList.add(new BaseDTO(2, "Service 2"));
		committeeServicesList.add(new BaseDTO(3, "Service 3"));*/
		
		committeeAcademicList=new ArrayList<ElectionCandidatesDTO>();
		List<BaseDTO> tempCommitteeAcademicList=new ArrayList<>();
		tempCommitteeAcademicList=electionFacade.getCandidateByID(5);
		for(int i=0;i<tempCommitteeAcademicList.size();i++)
		{
			committeeAcademicList.add(new ElectionCandidatesDTO(tempCommitteeAcademicList.get(i).getId(), 
					tempCommitteeAcademicList.get(i).getName(), tempCommitteeAcademicList.get(i).getFileNo(), tempCommitteeAcademicList.get(i).getImg()));
		}
		
		/*committeeAcademicList.add(new BaseDTO(1, "Academic 1"));
		committeeAcademicList.add(new BaseDTO(2, "Academic 2"));
		committeeAcademicList.add(new BaseDTO(3, "Academic 3"));*/
		
		committeeActivtiesPresidentList=new ArrayList<ElectionCandidatesDTO>();
		List<BaseDTO> tempCommitteeActivtiesPresidentList=new ArrayList<>();
		tempCommitteeActivtiesPresidentList=electionFacade.getCandidateByID(6);
		for(int i=0;i<tempCommitteeActivtiesPresidentList.size();i++)
		{
			committeeActivtiesPresidentList.add(new ElectionCandidatesDTO(tempCommitteeActivtiesPresidentList.get(i).getId(),
					tempCommitteeActivtiesPresidentList.get(i).getName(), tempCommitteeActivtiesPresidentList.get(i).getFileNo(),
					tempCommitteeActivtiesPresidentList.get(i).getImg()));
		}
		//Generate the codes for one time 
		//electionFacade.generateElectionCodes();
		
		committeeServicesPresidentList=new ArrayList<ElectionCandidatesDTO>();
		List<BaseDTO> tempCommitteeServicesPresidentList=new ArrayList<>();
		tempCommitteeServicesPresidentList=electionFacade.getCandidateByID(7);
		for(int i=0;i<tempCommitteeServicesPresidentList.size();i++)
		{
			committeeServicesPresidentList.add(new ElectionCandidatesDTO(tempCommitteeServicesPresidentList.get(i).getId(),
					tempCommitteeServicesPresidentList.get(i).getName(), tempCommitteeServicesPresidentList.get(i).getFileNo(), tempCommitteeServicesPresidentList.get(i).getImg()));
		}
		
		
		committeeAcademicListPresident=new ArrayList<ElectionCandidatesDTO>();
		List<BaseDTO> tempCommitteeAcademicListPresident=new ArrayList<>();
		tempCommitteeAcademicListPresident=electionFacade.getCandidateByID(8);
		for(int i=0;i<tempCommitteeAcademicListPresident.size();i++)
		{
			committeeAcademicListPresident.add(new ElectionCandidatesDTO(tempCommitteeAcademicListPresident.get(i).getId(),
					tempCommitteeAcademicListPresident.get(i).getName(), tempCommitteeAcademicListPresident.get(i).getFileNo(), tempCommitteeAcademicListPresident.get(i).getImg()));
			
		}
		
	    //TODO comment this in general case
		//{
		setSelectedPresident(1);
		setSelectedCommitteeActivtiesPresident(51);
		setSelectedCommitteeServicesPresident(42);
		//}
		//TODO un-comment in case of sending emails
		 //Send the emails with codes once 
		// electionFacade.sendEmailsWithCodes();
		
	}
	public String navigateToVotingPage()
	{
		//TODO 
	    //Check on file No
		
		try{
			Integer studentIDInt=Integer.parseInt(getStudentID());
			boolean isRightFileNo=electionFacade.isRightFileNo(studentIDInt);
			if(isRightFileNo)
			{
				//Check on code
				boolean matchCodeWithID=electionFacade.isRightCodeWithFileNo(studentIDInt,getStudentCode());
				if(matchCodeWithID)
				{
		boolean votedBefore=electionFacade.votedBefore(studentIDInt);
		if(votedBefore)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Voted Before", ""));
			
			
			return"";
		}
		else 
		{
			clearSelections();
			return"/pages/elections/elections.xhtml?faces-redirect=true;";
		}
				}
				else 
				{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Invalid Code", ""));
					
					
					return"";
				}
			}
			else 
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Such ID", ""));
				return "";
			}
	}
		catch(Exception ex)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error in ID", ""));
			return "";
		}
	}
	public void clearSelections()
	{
		setSelectedPresident(0);
		setSelectedVice(0);
		setSelectedCommitteeServices(null);
		setSelectedCommitteeActivties(null);
		setSelectedCommitteeAcademic(null);
		setSelectedCommitteeAcademicPresident(0);
		setSelectedCommitteeActivtiesPresident(0);
		setSelectedCommitteeServicesPresident(0);
	    //TODO comment this in general case
		//{
		setSelectedPresident(1);
		setSelectedCommitteeActivtiesPresident(51);
		setSelectedCommitteeServicesPresident(42);
		//}
	}
	public String submitVote()
	{
		
		if(getSelectedCommitteeActivties().length>4||getSelectedCommitteeAcademic().length>4||getSelectedCommitteeServices().length>4)
		{
			
			//1 & 2& 3
			 if(getSelectedCommitteeActivties().length>4&&getSelectedCommitteeAcademic().length>4&&getSelectedCommitteeServices().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Activities, Academic and Services committee members should not exceed 4 members.", ""));	
			}
			 
			//1 & 2
			 else if(getSelectedCommitteeActivties().length>4&&getSelectedCommitteeAcademic().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Activities and Academic committee members should not exceed 4 members.", ""));	
			}
		
			//1 & 3
			 else if(getSelectedCommitteeActivties().length>4&&getSelectedCommitteeServices().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Activities and Services committee members should not exceed 4 members.", ""));	
			}
			//2 & 3
			 else if(getSelectedCommitteeAcademic().length>4&&getSelectedCommitteeServices().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Academic and Services committee members should not exceed 4 members.", ""));	
			}
			//1
			 else if(getSelectedCommitteeActivties().length>4)
			{
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Activities committee members should not exceed 4 members.", ""));
			}
			//2
			 else if(getSelectedCommitteeAcademic().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Academic committee members should not exceed 4 members.", ""));
			}
			//3
			 else if(getSelectedCommitteeServices().length>4)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Services committee members should not exceed 4 members.", ""));	
			}
			
			
		
			
			
			/*if(getSelectedCommitteeActivties().length>3)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Activities committee members should not exceed 3 members.", ""));
			}
			else if(getSelectedCommitteeAcademic().length>3)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Academic committee members should not exceed 3 members.", ""));
			}
			else if(getSelectedCommitteeServices().length>3)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Services committee members should not exceed 3 members.", ""));			}*/
			return "";
		}
		
		else 
		
		{
		try
		{
		System.out.println("President > "+ getSelectedPresident());
		System.out.println("Vice > "+ getSelectedVice());
		System.out.println("Act president > "+ getSelectedCommitteeActivtiesPresident());
		System.out.println("Service president> "+ getSelectedCommitteeServicesPresident());
		System.out.println("Academic president> "+ getSelectedCommitteeAcademicPresident());
		
		
		for(int i=0;i<getSelectedCommitteeActivties().length;i++)
		{
			System.out.println("Activties > "+i+1 +" : "+ getSelectedCommitteeActivties()[i]);
		}
		for(int i=0;i<getSelectedCommitteeServices().length;i++)
		{
			System.out.println("sevices > "+i+1 +" : "+ getSelectedCommitteeServices()[i]);
		}
		for(int i=0;i<getSelectedCommitteeAcademic().length;i++)
		{
			System.out.println("Academic > "+i+1 +" : "+ getSelectedCommitteeAcademic()[i]);
		}
		ElectionResultDTO vote=new ElectionResultDTO();
		Integer studentIDInt=Integer.parseInt(getStudentID());
		vote.setStudentID(studentIDInt);
		
		vote.setPresident(new BaseDTO(getSelectedPresident()));
		/*//For Loop on the list
		for(int i=0;i<viceList.size();i++)
		{
			if(viceList.get(i).isSelected())
			{
				
			}
		}*/
		vote.setVice(new BaseDTO(getSelectedVice()));
		List<BaseDTO>activitiesLst=new ArrayList<BaseDTO>();
		for(int i=0;i<getSelectedCommitteeActivties().length;i++)
		{
			activitiesLst.add(new BaseDTO(getSelectedCommitteeActivties()[i]));
		}
		//List<BaseDTO> activtiesTemp=new ArrayList<BaseDTO>();
		/*for(int i=0;i<getCommitteeActivtiesList().size();i++)
		{
			if(getCommitteeActivtiesList().get(i).isSelected())
			{
				activtiesTemp.add(new BaseDTO(getCommitteeActivtiesList().get(i).getId(), getCommitteeActivtiesList().get(i).getName()));
			}
		}*/
		vote.setActivities(activitiesLst);
		
		List<BaseDTO>servicesLst=new ArrayList<BaseDTO>();
		for(int i=0;i<getSelectedCommitteeServices().length;i++)
		{
			servicesLst.add(new BaseDTO(getSelectedCommitteeServices()[i]));
		}
		/*List<BaseDTO> servicesTemp=new ArrayList<BaseDTO>();
		for(int i=0;i<getCommitteeServicesList().size();i++)
		{
			if(getCommitteeServicesList().get(i).isSelected())
			{
				servicesTemp.add(new BaseDTO(getCommitteeServicesList().get(i).getId(), getCommitteeServicesList().get(i).getName()));
			}
		}*/
		
		vote.setServices(servicesLst);
		
		List<BaseDTO>academicLst=new ArrayList<BaseDTO>();
		for(int i=0;i<getSelectedCommitteeAcademic().length;i++)
		{
			academicLst.add(new BaseDTO(getSelectedCommitteeAcademic()[i]));
		}
		/*List<BaseDTO> acadTemp=new ArrayList<BaseDTO>();
		for(int i=0;i<getCommitteeAcademicList().size();i++)
		{
			if(getCommitteeAcademicList().get(i).isSelected())
			{
				acadTemp.add(new BaseDTO(getCommitteeAcademicList().get(i).getId(), getCommitteeAcademicList().get(i).getName()));
			}
		}*/
		
		
		vote.setAcademic(academicLst);
		
		vote.setActivitiesPresident(new BaseDTO(getSelectedCommitteeActivtiesPresident()));
		vote.setServicesPresident(new BaseDTO(getSelectedCommitteeServicesPresident()));
		vote.setAcademicPresident(new BaseDTO(getSelectedCommitteeAcademicPresident()));
		//vote.setServices(new BaseDTO(getSelectedCommitteeServices()));
		//vote.setAcademic(new BaseDTO(getSelectedCommitteeAcademic()));
		
		boolean added=electionFacade.addVote(vote);
		if(added)
		{
			// Send Email to the student with his voting 
			electionFacade.sendEmailByVoting(vote);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Thank You for voting", ""));
			setStudentID("");
			return "/pages/elections/thankUpage.xhtml?faces-redirect=true;";
		}
		else 
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"UnExpected Error", ""));
			return "";
		}			
		
		}
		catch(Exception ex)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error in ID", ""));
			return"";
		}
		}
		
	}
	
	public String navigateToVerifyPage()
	{
		clearSelections();
		setStudentID("");
		setStudentCode("");
		return "/pages/elections/verify.xhtml?faces-redirect=true;";
	}
	
	public void getNoOfVotes()
	{
		//totalNoOFVotes=electionFacade.totalNoOfVotes();
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public List<ElectionCandidatesDTO> getPresidentList() {
		return presidentList;
	}
	public void setPresidentList(List<ElectionCandidatesDTO> presidentList) {
		this.presidentList = presidentList;
	}
	public List<ElectionCandidatesDTO> getViceList() {
		return viceList;
	}
	public void setViceList(List<ElectionCandidatesDTO> viceList) {
		this.viceList = viceList;
	}
	public List<ElectionCandidatesDTO> getCommitteeActivtiesList() {
		return committeeActivtiesList;
	}
	public void setCommitteeActivtiesList(List<ElectionCandidatesDTO> committeeActivtiesList) {
		this.committeeActivtiesList = committeeActivtiesList;
	}
	public List<ElectionCandidatesDTO> getCommitteeServicesList() {
		return committeeServicesList;
	}
	public void setCommitteeServicesList(List<ElectionCandidatesDTO> committeeServicesList) {
		this.committeeServicesList = committeeServicesList;
	}
	public List<ElectionCandidatesDTO> getCommitteeAcademicList() {
		return committeeAcademicList;
	}
	public void setCommitteeAcademicList(List<ElectionCandidatesDTO> committeeAcademicList) {
		this.committeeAcademicList = committeeAcademicList;
	}
	public int getSelectedPresident() {
		return selectedPresident;
	}
	public void setSelectedPresident(int selectedPresident) {
		this.selectedPresident = selectedPresident;
	}
	public int getSelectedVice() {
		return selectedVice;
	}
	public void setSelectedVice(int selectedVice) {
		this.selectedVice = selectedVice;
	}
	
	public IElectionFacade getElectionFacade() {
		return electionFacade;
	}
	public void setElectionFacade(IElectionFacade electionFacade) {
		this.electionFacade = electionFacade;
	}
	public int getTotalNoOFVotes() {
		return electionFacade.totalNoOfVotes();
	}
	public void setTotalNoOFVotes(int totalNoOFVotes) {
		this.totalNoOFVotes = totalNoOFVotes;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public List<ElectionCandidatesDTO> getCommitteeActivtiesPresidentList() {
		return committeeActivtiesPresidentList;
	}
	public void setCommitteeActivtiesPresidentList(
			List<ElectionCandidatesDTO> committeeActivtiesPresidentList) {
		this.committeeActivtiesPresidentList = committeeActivtiesPresidentList;
	}
	public List<ElectionCandidatesDTO> getCommitteeServicesPresidentList() {
		return committeeServicesPresidentList;
	}
	public void setCommitteeServicesPresidentList(
			List<ElectionCandidatesDTO> committeeServicesPresidentList) {
		this.committeeServicesPresidentList = committeeServicesPresidentList;
	}
	public List<ElectionCandidatesDTO> getCommitteeAcademicListPresident() {
		return committeeAcademicListPresident;
	}
	public void setCommitteeAcademicListPresident(
			List<ElectionCandidatesDTO> committeeAcademicListPresident) {
		this.committeeAcademicListPresident = committeeAcademicListPresident;
	}
	public int getSelectedCommitteeActivtiesPresident() {
		return selectedCommitteeActivtiesPresident;
	}
	public void setSelectedCommitteeActivtiesPresident(
			int selectedCommitteeActivtiesPresident) {
		this.selectedCommitteeActivtiesPresident = selectedCommitteeActivtiesPresident;
	}
	public Integer[] getSelectedCommitteeActivties() {
		return selectedCommitteeActivties;
	}
	public void setSelectedCommitteeActivties(Integer[] selectedCommitteeActivties) {
		this.selectedCommitteeActivties = selectedCommitteeActivties;
	}
	public Integer[] getSelectedCommitteeServices() {
		return selectedCommitteeServices;
	}
	public void setSelectedCommitteeServices(Integer[] selectedCommitteeServices) {
		this.selectedCommitteeServices = selectedCommitteeServices;
	}
	public int getSelectedCommitteeServicesPresident() {
		return selectedCommitteeServicesPresident;
	}
	public void setSelectedCommitteeServicesPresident(
			int selectedCommitteeServicesPresident) {
		this.selectedCommitteeServicesPresident = selectedCommitteeServicesPresident;
	}
	public Integer[] getSelectedCommitteeAcademic() {
		return selectedCommitteeAcademic;
	}
	public void setSelectedCommitteeAcademic(Integer[] selectedCommitteeAcademic) {
		this.selectedCommitteeAcademic = selectedCommitteeAcademic;
	}
	public int getSelectedCommitteeAcademicPresident() {
		return selectedCommitteeAcademicPresident;
	}
	public void setSelectedCommitteeAcademicPresident(
			int selectedCommitteeAcademicPresident) {
		this.selectedCommitteeAcademicPresident = selectedCommitteeAcademicPresident;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	
	
	
}
